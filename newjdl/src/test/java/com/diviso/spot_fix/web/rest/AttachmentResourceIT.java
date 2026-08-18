package com.diviso.spot_fix.web.rest;

import static com.diviso.spot_fix.domain.AttachmentAsserts.*;
import static com.diviso.spot_fix.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.diviso.spot_fix.IntegrationTest;
import com.diviso.spot_fix.domain.Attachment;
import com.diviso.spot_fix.domain.Ticket;
import com.diviso.spot_fix.domain.User;
import com.diviso.spot_fix.domain.enumeration.AttachmentType;
import com.diviso.spot_fix.repository.AttachmentRepository;
import com.diviso.spot_fix.repository.UserRepository;
import com.diviso.spot_fix.service.AttachmentService;
import com.diviso.spot_fix.service.dto.AttachmentDTO;
import com.diviso.spot_fix.service.mapper.AttachmentMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AttachmentResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AttachmentResourceIT {

    private static final AttachmentType DEFAULT_ATTACHMENT_TYPE = AttachmentType.IMAGE;
    private static final AttachmentType UPDATED_ATTACHMENT_TYPE = AttachmentType.VIDEO;

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FILE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FILE_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_FILE_SIZE = 0L;
    private static final Long UPDATED_FILE_SIZE = 1L;
    private static final Long SMALLER_FILE_SIZE = 0L - 1L;

    private static final String DEFAULT_CHECKSUM = "AAAAAAAAAA";
    private static final String UPDATED_CHECKSUM = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPLOADED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPLOADED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_TRANSCRIPT = "AAAAAAAAAA";
    private static final String UPDATED_TRANSCRIPT = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION_SECONDS = 0;
    private static final Integer UPDATED_DURATION_SECONDS = 1;
    private static final Integer SMALLER_DURATION_SECONDS = 0 - 1;

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/attachments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private AttachmentRepository attachmentRepositoryMock;

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Mock
    private AttachmentService attachmentServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttachmentMockMvc;

    private Attachment attachment;

    private Attachment insertedAttachment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attachment createEntity(EntityManager em) {
        Attachment attachment = new Attachment()
            .attachmentType(DEFAULT_ATTACHMENT_TYPE)
            .fileName(DEFAULT_FILE_NAME)
            .filePath(DEFAULT_FILE_PATH)
            .fileType(DEFAULT_FILE_TYPE)
            .fileSize(DEFAULT_FILE_SIZE)
            .checksum(DEFAULT_CHECKSUM)
            .uploadedDate(DEFAULT_UPLOADED_DATE)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE)
            .transcript(DEFAULT_TRANSCRIPT)
            .durationSeconds(DEFAULT_DURATION_SECONDS)
            .language(DEFAULT_LANGUAGE)
            .deleted(DEFAULT_DELETED)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .deletedDate(DEFAULT_DELETED_DATE);
        // Add required entity
        Ticket ticket;
        if (TestUtil.findAll(em, Ticket.class).isEmpty()) {
            ticket = TicketResourceIT.createEntity(em);
            em.persist(ticket);
            em.flush();
        } else {
            ticket = TestUtil.findAll(em, Ticket.class).get(0);
        }
        attachment.setTicket(ticket);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        attachment.setUploadedBy(user);
        return attachment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attachment createUpdatedEntity(EntityManager em) {
        Attachment updatedAttachment = new Attachment()
            .attachmentType(UPDATED_ATTACHMENT_TYPE)
            .fileName(UPDATED_FILE_NAME)
            .filePath(UPDATED_FILE_PATH)
            .fileType(UPDATED_FILE_TYPE)
            .fileSize(UPDATED_FILE_SIZE)
            .checksum(UPDATED_CHECKSUM)
            .uploadedDate(UPDATED_UPLOADED_DATE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .transcript(UPDATED_TRANSCRIPT)
            .durationSeconds(UPDATED_DURATION_SECONDS)
            .language(UPDATED_LANGUAGE)
            .deleted(UPDATED_DELETED)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deletedDate(UPDATED_DELETED_DATE);
        // Add required entity
        Ticket ticket;
        if (TestUtil.findAll(em, Ticket.class).isEmpty()) {
            ticket = TicketResourceIT.createUpdatedEntity(em);
            em.persist(ticket);
            em.flush();
        } else {
            ticket = TestUtil.findAll(em, Ticket.class).get(0);
        }
        updatedAttachment.setTicket(ticket);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        updatedAttachment.setUploadedBy(user);
        return updatedAttachment;
    }

    @BeforeEach
    void initTest() {
        attachment = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedAttachment != null) {
            attachmentRepository.delete(insertedAttachment);
            insertedAttachment = null;
        }
    }

    @Test
    @Transactional
    void createAttachment() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Attachment
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);
        var returnedAttachmentDTO = om.readValue(
            restAttachmentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(attachmentDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AttachmentDTO.class
        );

        // Validate the Attachment in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAttachment = attachmentMapper.toEntity(returnedAttachmentDTO);
        assertAttachmentUpdatableFieldsEquals(returnedAttachment, getPersistedAttachment(returnedAttachment));

        insertedAttachment = returnedAttachment;
    }

    @Test
    @Transactional
    void createAttachmentWithExistingId() throws Exception {
        // Create the Attachment with an existing ID
        attachment.setId(1L);
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttachmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(attachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attachment in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAttachmentTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        attachment.setAttachmentType(null);

        // Create the Attachment, which fails.
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        restAttachmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(attachmentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFileNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        attachment.setFileName(null);

        // Create the Attachment, which fails.
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        restAttachmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(attachmentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFilePathIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        attachment.setFilePath(null);

        // Create the Attachment, which fails.
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        restAttachmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(attachmentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUploadedDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        attachment.setUploadedDate(null);

        // Create the Attachment, which fails.
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        restAttachmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(attachmentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeletedIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        attachment.setDeleted(null);

        // Create the Attachment, which fails.
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        restAttachmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(attachmentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAttachments() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList
        restAttachmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].attachmentType").value(hasItem(DEFAULT_ATTACHMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH)))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE)))
            .andExpect(jsonPath("$.[*].fileSize").value(hasItem(DEFAULT_FILE_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].checksum").value(hasItem(DEFAULT_CHECKSUM)))
            .andExpect(jsonPath("$.[*].uploadedDate").value(hasItem(DEFAULT_UPLOADED_DATE.toString())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].transcript").value(hasItem(DEFAULT_TRANSCRIPT)))
            .andExpect(jsonPath("$.[*].durationSeconds").value(hasItem(DEFAULT_DURATION_SECONDS)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].deletedDate").value(hasItem(DEFAULT_DELETED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAttachmentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(attachmentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAttachmentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(attachmentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAttachmentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(attachmentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAttachmentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(attachmentRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAttachment() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get the attachment
        restAttachmentMockMvc
            .perform(get(ENTITY_API_URL_ID, attachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attachment.getId().intValue()))
            .andExpect(jsonPath("$.attachmentType").value(DEFAULT_ATTACHMENT_TYPE.toString()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.filePath").value(DEFAULT_FILE_PATH))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE))
            .andExpect(jsonPath("$.fileSize").value(DEFAULT_FILE_SIZE.intValue()))
            .andExpect(jsonPath("$.checksum").value(DEFAULT_CHECKSUM))
            .andExpect(jsonPath("$.uploadedDate").value(DEFAULT_UPLOADED_DATE.toString()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64.getEncoder().encodeToString(DEFAULT_FILE)))
            .andExpect(jsonPath("$.transcript").value(DEFAULT_TRANSCRIPT))
            .andExpect(jsonPath("$.durationSeconds").value(DEFAULT_DURATION_SECONDS))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.deletedDate").value(DEFAULT_DELETED_DATE.toString()));
    }

    @Test
    @Transactional
    void getAttachmentsByIdFiltering() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        Long id = attachment.getId();

        defaultAttachmentFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAttachmentFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAttachmentFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAttachmentsByAttachmentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where attachmentType equals to
        defaultAttachmentFiltering("attachmentType.equals=" + DEFAULT_ATTACHMENT_TYPE, "attachmentType.equals=" + UPDATED_ATTACHMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllAttachmentsByAttachmentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where attachmentType in
        defaultAttachmentFiltering(
            "attachmentType.in=" + DEFAULT_ATTACHMENT_TYPE + "," + UPDATED_ATTACHMENT_TYPE,
            "attachmentType.in=" + UPDATED_ATTACHMENT_TYPE
        );
    }

    @Test
    @Transactional
    void getAllAttachmentsByAttachmentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where attachmentType is not null
        defaultAttachmentFiltering("attachmentType.specified=true", "attachmentType.specified=false");
    }

    @Test
    @Transactional
    void getAllAttachmentsByFileNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where fileName equals to
        defaultAttachmentFiltering("fileName.equals=" + DEFAULT_FILE_NAME, "fileName.equals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllAttachmentsByFileNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where fileName in
        defaultAttachmentFiltering("fileName.in=" + DEFAULT_FILE_NAME + "," + UPDATED_FILE_NAME, "fileName.in=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllAttachmentsByFileNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where fileName is not null
        defaultAttachmentFiltering("fileName.specified=true", "fileName.specified=false");
    }

    @Test
    @Transactional
    void getAllAttachmentsByFileNameContainsSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where fileName contains
        defaultAttachmentFiltering("fileName.contains=" + DEFAULT_FILE_NAME, "fileName.contains=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllAttachmentsByFileNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where fileName does not contain
        defaultAttachmentFiltering("fileName.doesNotContain=" + UPDATED_FILE_NAME, "fileName.doesNotContain=" + DEFAULT_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllAttachmentsByFilePathIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where filePath equals to
        defaultAttachmentFiltering("filePath.equals=" + DEFAULT_FILE_PATH, "filePath.equals=" + UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    void getAllAttachmentsByFilePathIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where filePath in
        defaultAttachmentFiltering("filePath.in=" + DEFAULT_FILE_PATH + "," + UPDATED_FILE_PATH, "filePath.in=" + UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    void getAllAttachmentsByFilePathIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where filePath is not null
        defaultAttachmentFiltering("filePath.specified=true", "filePath.specified=false");
    }

    @Test
    @Transactional
    void getAllAttachmentsByFilePathContainsSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where filePath contains
        defaultAttachmentFiltering("filePath.contains=" + DEFAULT_FILE_PATH, "filePath.contains=" + UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    void getAllAttachmentsByFilePathNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where filePath does not contain
        defaultAttachmentFiltering("filePath.doesNotContain=" + UPDATED_FILE_PATH, "filePath.doesNotContain=" + DEFAULT_FILE_PATH);
    }

    @Test
    @Transactional
    void getAllAttachmentsByFileTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where fileType equals to
        defaultAttachmentFiltering("fileType.equals=" + DEFAULT_FILE_TYPE, "fileType.equals=" + UPDATED_FILE_TYPE);
    }

    @Test
    @Transactional
    void getAllAttachmentsByFileTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where fileType in
        defaultAttachmentFiltering("fileType.in=" + DEFAULT_FILE_TYPE + "," + UPDATED_FILE_TYPE, "fileType.in=" + UPDATED_FILE_TYPE);
    }

    @Test
    @Transactional
    void getAllAttachmentsByFileTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where fileType is not null
        defaultAttachmentFiltering("fileType.specified=true", "fileType.specified=false");
    }

    @Test
    @Transactional
    void getAllAttachmentsByFileTypeContainsSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where fileType contains
        defaultAttachmentFiltering("fileType.contains=" + DEFAULT_FILE_TYPE, "fileType.contains=" + UPDATED_FILE_TYPE);
    }

    @Test
    @Transactional
    void getAllAttachmentsByFileTypeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where fileType does not contain
        defaultAttachmentFiltering("fileType.doesNotContain=" + UPDATED_FILE_TYPE, "fileType.doesNotContain=" + DEFAULT_FILE_TYPE);
    }

    @Test
    @Transactional
    void getAllAttachmentsByFileSizeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where fileSize equals to
        defaultAttachmentFiltering("fileSize.equals=" + DEFAULT_FILE_SIZE, "fileSize.equals=" + UPDATED_FILE_SIZE);
    }

    @Test
    @Transactional
    void getAllAttachmentsByFileSizeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where fileSize in
        defaultAttachmentFiltering("fileSize.in=" + DEFAULT_FILE_SIZE + "," + UPDATED_FILE_SIZE, "fileSize.in=" + UPDATED_FILE_SIZE);
    }

    @Test
    @Transactional
    void getAllAttachmentsByFileSizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where fileSize is not null
        defaultAttachmentFiltering("fileSize.specified=true", "fileSize.specified=false");
    }

    @Test
    @Transactional
    void getAllAttachmentsByFileSizeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where fileSize is greater than or equal to
        defaultAttachmentFiltering("fileSize.greaterThanOrEqual=" + DEFAULT_FILE_SIZE, "fileSize.greaterThanOrEqual=" + UPDATED_FILE_SIZE);
    }

    @Test
    @Transactional
    void getAllAttachmentsByFileSizeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where fileSize is less than or equal to
        defaultAttachmentFiltering("fileSize.lessThanOrEqual=" + DEFAULT_FILE_SIZE, "fileSize.lessThanOrEqual=" + SMALLER_FILE_SIZE);
    }

    @Test
    @Transactional
    void getAllAttachmentsByFileSizeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where fileSize is less than
        defaultAttachmentFiltering("fileSize.lessThan=" + UPDATED_FILE_SIZE, "fileSize.lessThan=" + DEFAULT_FILE_SIZE);
    }

    @Test
    @Transactional
    void getAllAttachmentsByFileSizeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where fileSize is greater than
        defaultAttachmentFiltering("fileSize.greaterThan=" + SMALLER_FILE_SIZE, "fileSize.greaterThan=" + DEFAULT_FILE_SIZE);
    }

    @Test
    @Transactional
    void getAllAttachmentsByChecksumIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where checksum equals to
        defaultAttachmentFiltering("checksum.equals=" + DEFAULT_CHECKSUM, "checksum.equals=" + UPDATED_CHECKSUM);
    }

    @Test
    @Transactional
    void getAllAttachmentsByChecksumIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where checksum in
        defaultAttachmentFiltering("checksum.in=" + DEFAULT_CHECKSUM + "," + UPDATED_CHECKSUM, "checksum.in=" + UPDATED_CHECKSUM);
    }

    @Test
    @Transactional
    void getAllAttachmentsByChecksumIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where checksum is not null
        defaultAttachmentFiltering("checksum.specified=true", "checksum.specified=false");
    }

    @Test
    @Transactional
    void getAllAttachmentsByChecksumContainsSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where checksum contains
        defaultAttachmentFiltering("checksum.contains=" + DEFAULT_CHECKSUM, "checksum.contains=" + UPDATED_CHECKSUM);
    }

    @Test
    @Transactional
    void getAllAttachmentsByChecksumNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where checksum does not contain
        defaultAttachmentFiltering("checksum.doesNotContain=" + UPDATED_CHECKSUM, "checksum.doesNotContain=" + DEFAULT_CHECKSUM);
    }

    @Test
    @Transactional
    void getAllAttachmentsByUploadedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where uploadedDate equals to
        defaultAttachmentFiltering("uploadedDate.equals=" + DEFAULT_UPLOADED_DATE, "uploadedDate.equals=" + UPDATED_UPLOADED_DATE);
    }

    @Test
    @Transactional
    void getAllAttachmentsByUploadedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where uploadedDate in
        defaultAttachmentFiltering(
            "uploadedDate.in=" + DEFAULT_UPLOADED_DATE + "," + UPDATED_UPLOADED_DATE,
            "uploadedDate.in=" + UPDATED_UPLOADED_DATE
        );
    }

    @Test
    @Transactional
    void getAllAttachmentsByUploadedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where uploadedDate is not null
        defaultAttachmentFiltering("uploadedDate.specified=true", "uploadedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAttachmentsByDurationSecondsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where durationSeconds equals to
        defaultAttachmentFiltering(
            "durationSeconds.equals=" + DEFAULT_DURATION_SECONDS,
            "durationSeconds.equals=" + UPDATED_DURATION_SECONDS
        );
    }

    @Test
    @Transactional
    void getAllAttachmentsByDurationSecondsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where durationSeconds in
        defaultAttachmentFiltering(
            "durationSeconds.in=" + DEFAULT_DURATION_SECONDS + "," + UPDATED_DURATION_SECONDS,
            "durationSeconds.in=" + UPDATED_DURATION_SECONDS
        );
    }

    @Test
    @Transactional
    void getAllAttachmentsByDurationSecondsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where durationSeconds is not null
        defaultAttachmentFiltering("durationSeconds.specified=true", "durationSeconds.specified=false");
    }

    @Test
    @Transactional
    void getAllAttachmentsByDurationSecondsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where durationSeconds is greater than or equal to
        defaultAttachmentFiltering(
            "durationSeconds.greaterThanOrEqual=" + DEFAULT_DURATION_SECONDS,
            "durationSeconds.greaterThanOrEqual=" + UPDATED_DURATION_SECONDS
        );
    }

    @Test
    @Transactional
    void getAllAttachmentsByDurationSecondsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where durationSeconds is less than or equal to
        defaultAttachmentFiltering(
            "durationSeconds.lessThanOrEqual=" + DEFAULT_DURATION_SECONDS,
            "durationSeconds.lessThanOrEqual=" + SMALLER_DURATION_SECONDS
        );
    }

    @Test
    @Transactional
    void getAllAttachmentsByDurationSecondsIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where durationSeconds is less than
        defaultAttachmentFiltering(
            "durationSeconds.lessThan=" + UPDATED_DURATION_SECONDS,
            "durationSeconds.lessThan=" + DEFAULT_DURATION_SECONDS
        );
    }

    @Test
    @Transactional
    void getAllAttachmentsByDurationSecondsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where durationSeconds is greater than
        defaultAttachmentFiltering(
            "durationSeconds.greaterThan=" + SMALLER_DURATION_SECONDS,
            "durationSeconds.greaterThan=" + DEFAULT_DURATION_SECONDS
        );
    }

    @Test
    @Transactional
    void getAllAttachmentsByLanguageIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where language equals to
        defaultAttachmentFiltering("language.equals=" + DEFAULT_LANGUAGE, "language.equals=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllAttachmentsByLanguageIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where language in
        defaultAttachmentFiltering("language.in=" + DEFAULT_LANGUAGE + "," + UPDATED_LANGUAGE, "language.in=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllAttachmentsByLanguageIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where language is not null
        defaultAttachmentFiltering("language.specified=true", "language.specified=false");
    }

    @Test
    @Transactional
    void getAllAttachmentsByLanguageContainsSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where language contains
        defaultAttachmentFiltering("language.contains=" + DEFAULT_LANGUAGE, "language.contains=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllAttachmentsByLanguageNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where language does not contain
        defaultAttachmentFiltering("language.doesNotContain=" + UPDATED_LANGUAGE, "language.doesNotContain=" + DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllAttachmentsByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where deleted equals to
        defaultAttachmentFiltering("deleted.equals=" + DEFAULT_DELETED, "deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllAttachmentsByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where deleted in
        defaultAttachmentFiltering("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED, "deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllAttachmentsByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where deleted is not null
        defaultAttachmentFiltering("deleted.specified=true", "deleted.specified=false");
    }

    @Test
    @Transactional
    void getAllAttachmentsByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where updatedDate equals to
        defaultAttachmentFiltering("updatedDate.equals=" + DEFAULT_UPDATED_DATE, "updatedDate.equals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllAttachmentsByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where updatedDate in
        defaultAttachmentFiltering(
            "updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE,
            "updatedDate.in=" + UPDATED_UPDATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllAttachmentsByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where updatedDate is not null
        defaultAttachmentFiltering("updatedDate.specified=true", "updatedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAttachmentsByDeletedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where deletedDate equals to
        defaultAttachmentFiltering("deletedDate.equals=" + DEFAULT_DELETED_DATE, "deletedDate.equals=" + UPDATED_DELETED_DATE);
    }

    @Test
    @Transactional
    void getAllAttachmentsByDeletedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where deletedDate in
        defaultAttachmentFiltering(
            "deletedDate.in=" + DEFAULT_DELETED_DATE + "," + UPDATED_DELETED_DATE,
            "deletedDate.in=" + UPDATED_DELETED_DATE
        );
    }

    @Test
    @Transactional
    void getAllAttachmentsByDeletedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where deletedDate is not null
        defaultAttachmentFiltering("deletedDate.specified=true", "deletedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAttachmentsByTicketIsEqualToSomething() throws Exception {
        Ticket ticket;
        if (TestUtil.findAll(em, Ticket.class).isEmpty()) {
            attachmentRepository.saveAndFlush(attachment);
            ticket = TicketResourceIT.createEntity(em);
        } else {
            ticket = TestUtil.findAll(em, Ticket.class).get(0);
        }
        em.persist(ticket);
        em.flush();
        attachment.setTicket(ticket);
        attachmentRepository.saveAndFlush(attachment);
        Long ticketId = ticket.getId();
        // Get all the attachmentList where ticket equals to ticketId
        defaultAttachmentShouldBeFound("ticketId.equals=" + ticketId);

        // Get all the attachmentList where ticket equals to (ticketId + 1)
        defaultAttachmentShouldNotBeFound("ticketId.equals=" + (ticketId + 1));
    }

    @Test
    @Transactional
    void getAllAttachmentsByUploadedByIsEqualToSomething() throws Exception {
        User uploadedBy;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            attachmentRepository.saveAndFlush(attachment);
            uploadedBy = UserResourceIT.createEntity();
        } else {
            uploadedBy = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(uploadedBy);
        em.flush();
        attachment.setUploadedBy(uploadedBy);
        attachmentRepository.saveAndFlush(attachment);
        Long uploadedById = uploadedBy.getId();
        // Get all the attachmentList where uploadedBy equals to uploadedById
        defaultAttachmentShouldBeFound("uploadedById.equals=" + uploadedById);

        // Get all the attachmentList where uploadedBy equals to (uploadedById + 1)
        defaultAttachmentShouldNotBeFound("uploadedById.equals=" + (uploadedById + 1));
    }

    private void defaultAttachmentFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAttachmentShouldBeFound(shouldBeFound);
        defaultAttachmentShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAttachmentShouldBeFound(String filter) throws Exception {
        restAttachmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].attachmentType").value(hasItem(DEFAULT_ATTACHMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH)))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE)))
            .andExpect(jsonPath("$.[*].fileSize").value(hasItem(DEFAULT_FILE_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].checksum").value(hasItem(DEFAULT_CHECKSUM)))
            .andExpect(jsonPath("$.[*].uploadedDate").value(hasItem(DEFAULT_UPLOADED_DATE.toString())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].transcript").value(hasItem(DEFAULT_TRANSCRIPT)))
            .andExpect(jsonPath("$.[*].durationSeconds").value(hasItem(DEFAULT_DURATION_SECONDS)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].deletedDate").value(hasItem(DEFAULT_DELETED_DATE.toString())));

        // Check, that the count call also returns 1
        restAttachmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAttachmentShouldNotBeFound(String filter) throws Exception {
        restAttachmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAttachmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAttachment() throws Exception {
        // Get the attachment
        restAttachmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAttachment() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the attachment
        Attachment updatedAttachment = attachmentRepository.findById(attachment.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAttachment are not directly saved in db
        em.detach(updatedAttachment);
        updatedAttachment
            .attachmentType(UPDATED_ATTACHMENT_TYPE)
            .fileName(UPDATED_FILE_NAME)
            .filePath(UPDATED_FILE_PATH)
            .fileType(UPDATED_FILE_TYPE)
            .fileSize(UPDATED_FILE_SIZE)
            .checksum(UPDATED_CHECKSUM)
            .uploadedDate(UPDATED_UPLOADED_DATE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .transcript(UPDATED_TRANSCRIPT)
            .durationSeconds(UPDATED_DURATION_SECONDS)
            .language(UPDATED_LANGUAGE)
            .deleted(UPDATED_DELETED)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deletedDate(UPDATED_DELETED_DATE);
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(updatedAttachment);

        restAttachmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attachmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(attachmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Attachment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAttachmentToMatchAllProperties(updatedAttachment);
    }

    @Test
    @Transactional
    void putNonExistingAttachment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attachment.setId(longCount.incrementAndGet());

        // Create the Attachment
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttachmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attachmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(attachmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attachment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttachment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attachment.setId(longCount.incrementAndGet());

        // Create the Attachment
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(attachmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attachment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttachment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attachment.setId(longCount.incrementAndGet());

        // Create the Attachment
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachmentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(attachmentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attachment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttachmentWithPatch() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the attachment using partial update
        Attachment partialUpdatedAttachment = new Attachment();
        partialUpdatedAttachment.setId(attachment.getId());

        partialUpdatedAttachment
            .attachmentType(UPDATED_ATTACHMENT_TYPE)
            .fileName(UPDATED_FILE_NAME)
            .filePath(UPDATED_FILE_PATH)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .transcript(UPDATED_TRANSCRIPT)
            .language(UPDATED_LANGUAGE)
            .deleted(UPDATED_DELETED);

        restAttachmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttachment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAttachment))
            )
            .andExpect(status().isOk());

        // Validate the Attachment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAttachmentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAttachment, attachment),
            getPersistedAttachment(attachment)
        );
    }

    @Test
    @Transactional
    void fullUpdateAttachmentWithPatch() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the attachment using partial update
        Attachment partialUpdatedAttachment = new Attachment();
        partialUpdatedAttachment.setId(attachment.getId());

        partialUpdatedAttachment
            .attachmentType(UPDATED_ATTACHMENT_TYPE)
            .fileName(UPDATED_FILE_NAME)
            .filePath(UPDATED_FILE_PATH)
            .fileType(UPDATED_FILE_TYPE)
            .fileSize(UPDATED_FILE_SIZE)
            .checksum(UPDATED_CHECKSUM)
            .uploadedDate(UPDATED_UPLOADED_DATE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .transcript(UPDATED_TRANSCRIPT)
            .durationSeconds(UPDATED_DURATION_SECONDS)
            .language(UPDATED_LANGUAGE)
            .deleted(UPDATED_DELETED)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deletedDate(UPDATED_DELETED_DATE);

        restAttachmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttachment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAttachment))
            )
            .andExpect(status().isOk());

        // Validate the Attachment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAttachmentUpdatableFieldsEquals(partialUpdatedAttachment, getPersistedAttachment(partialUpdatedAttachment));
    }

    @Test
    @Transactional
    void patchNonExistingAttachment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attachment.setId(longCount.incrementAndGet());

        // Create the Attachment
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttachmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attachmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(attachmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attachment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttachment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attachment.setId(longCount.incrementAndGet());

        // Create the Attachment
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(attachmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attachment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttachment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attachment.setId(longCount.incrementAndGet());

        // Create the Attachment
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachmentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(attachmentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attachment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttachment() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.saveAndFlush(attachment);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the attachment
        restAttachmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, attachment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return attachmentRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Attachment getPersistedAttachment(Attachment attachment) {
        return attachmentRepository.findById(attachment.getId()).orElseThrow();
    }

    protected void assertPersistedAttachmentToMatchAllProperties(Attachment expectedAttachment) {
        assertAttachmentAllPropertiesEquals(expectedAttachment, getPersistedAttachment(expectedAttachment));
    }

    protected void assertPersistedAttachmentToMatchUpdatableProperties(Attachment expectedAttachment) {
        assertAttachmentAllUpdatablePropertiesEquals(expectedAttachment, getPersistedAttachment(expectedAttachment));
    }
}
