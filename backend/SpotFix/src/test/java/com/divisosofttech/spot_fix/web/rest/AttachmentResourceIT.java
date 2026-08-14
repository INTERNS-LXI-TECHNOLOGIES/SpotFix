package com.divisosofttech.spot_fix.web.rest;

import static com.divisosofttech.spot_fix.domain.AttachmentAsserts.*;
import static com.divisosofttech.spot_fix.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import com.divisosofttech.spot_fix.IntegrationTest;
import com.divisosofttech.spot_fix.domain.Attachment;
import com.divisosofttech.spot_fix.domain.Ticket;
import com.divisosofttech.spot_fix.domain.User;
import com.divisosofttech.spot_fix.domain.enumeration.AttachmentType;
import com.divisosofttech.spot_fix.repository.AttachmentRepository;
import com.divisosofttech.spot_fix.repository.EntityManager;
import com.divisosofttech.spot_fix.repository.TicketRepository;
import com.divisosofttech.spot_fix.repository.UserRepository;
import com.divisosofttech.spot_fix.repository.UserRepository;
import com.divisosofttech.spot_fix.service.AttachmentService;
import com.divisosofttech.spot_fix.service.dto.AttachmentDTO;
import com.divisosofttech.spot_fix.service.mapper.AttachmentMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

/**
 * Integration tests for the {@link AttachmentResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
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
    private WebTestClient webTestClient;

    private Attachment attachment;

    private Attachment insertedAttachment;

    @Autowired
    private TicketRepository ticketRepository;

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
            .transcript(DEFAULT_TRANSCRIPT)
            .durationSeconds(DEFAULT_DURATION_SECONDS)
            .language(DEFAULT_LANGUAGE)
            .deleted(DEFAULT_DELETED)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .deletedDate(DEFAULT_DELETED_DATE);
        // Add required entity
        Ticket ticket;
        ticket = em.insert(TicketResourceIT.createEntity(em)).block();
        attachment.setTicket(ticket);
        // Add required entity
        User user = em.insert(UserResourceIT.createEntity()).block();
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
            .transcript(UPDATED_TRANSCRIPT)
            .durationSeconds(UPDATED_DURATION_SECONDS)
            .language(UPDATED_LANGUAGE)
            .deleted(UPDATED_DELETED)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deletedDate(UPDATED_DELETED_DATE);
        // Add required entity
        Ticket ticket;
        ticket = em.insert(TicketResourceIT.createUpdatedEntity(em)).block();
        updatedAttachment.setTicket(ticket);
        // Add required entity
        User user = em.insert(UserResourceIT.createEntity()).block();
        updatedAttachment.setUploadedBy(user);
        return updatedAttachment;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Attachment.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
        TicketResourceIT.deleteEntities(em);
        UserResourceIT.deleteEntities(em);
    }

    @BeforeEach
    void initTest() {
        attachment = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedAttachment != null) {
            attachmentRepository.delete(insertedAttachment).block();
            insertedAttachment = null;
        }
        deleteEntities(em);
        userRepository.deleteAllUserAuthorities().block();
        userRepository.deleteAll().block();
    }

    @Test
    void createAttachment() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Attachment
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);
        var returnedAttachmentDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(attachmentDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(AttachmentDTO.class)
            .returnResult()
            .getResponseBody();

        // Validate the Attachment in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAttachment = attachmentMapper.toEntity(returnedAttachmentDTO);
        assertAttachmentUpdatableFieldsEquals(returnedAttachment, getPersistedAttachment(returnedAttachment));

        insertedAttachment = returnedAttachment;
    }

    @Test
    void createAttachmentWithExistingId() throws Exception {
        // Create the Attachment with an existing ID
        attachment.setId(1L);
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(attachmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Attachment in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkAttachmentTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        attachment.setAttachmentType(null);

        // Create the Attachment, which fails.
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(attachmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkFileNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        attachment.setFileName(null);

        // Create the Attachment, which fails.
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(attachmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkFilePathIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        attachment.setFilePath(null);

        // Create the Attachment, which fails.
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(attachmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkUploadedDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        attachment.setUploadedDate(null);

        // Create the Attachment, which fails.
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(attachmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDeletedIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        attachment.setDeleted(null);

        // Create the Attachment, which fails.
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(attachmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllAttachments() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(attachment.getId().intValue()))
            .jsonPath("$.[*].attachmentType")
            .value(hasItem(DEFAULT_ATTACHMENT_TYPE.toString()))
            .jsonPath("$.[*].fileName")
            .value(hasItem(DEFAULT_FILE_NAME))
            .jsonPath("$.[*].filePath")
            .value(hasItem(DEFAULT_FILE_PATH))
            .jsonPath("$.[*].fileType")
            .value(hasItem(DEFAULT_FILE_TYPE))
            .jsonPath("$.[*].fileSize")
            .value(hasItem(DEFAULT_FILE_SIZE.intValue()))
            .jsonPath("$.[*].checksum")
            .value(hasItem(DEFAULT_CHECKSUM))
            .jsonPath("$.[*].uploadedDate")
            .value(hasItem(DEFAULT_UPLOADED_DATE.toString()))
            .jsonPath("$.[*].transcript")
            .value(hasItem(DEFAULT_TRANSCRIPT))
            .jsonPath("$.[*].durationSeconds")
            .value(hasItem(DEFAULT_DURATION_SECONDS))
            .jsonPath("$.[*].language")
            .value(hasItem(DEFAULT_LANGUAGE))
            .jsonPath("$.[*].deleted")
            .value(hasItem(DEFAULT_DELETED))
            .jsonPath("$.[*].updatedDate")
            .value(hasItem(DEFAULT_UPDATED_DATE.toString()))
            .jsonPath("$.[*].deletedDate")
            .value(hasItem(DEFAULT_DELETED_DATE.toString()));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAttachmentsWithEagerRelationshipsIsEnabled() {
        when(attachmentServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=true").exchange().expectStatus().isOk();

        verify(attachmentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAttachmentsWithEagerRelationshipsIsNotEnabled() {
        when(attachmentServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=false").exchange().expectStatus().isOk();
        verify(attachmentRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    void getAttachment() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get the attachment
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, attachment.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(attachment.getId().intValue()))
            .jsonPath("$.attachmentType")
            .value(is(DEFAULT_ATTACHMENT_TYPE.toString()))
            .jsonPath("$.fileName")
            .value(is(DEFAULT_FILE_NAME))
            .jsonPath("$.filePath")
            .value(is(DEFAULT_FILE_PATH))
            .jsonPath("$.fileType")
            .value(is(DEFAULT_FILE_TYPE))
            .jsonPath("$.fileSize")
            .value(is(DEFAULT_FILE_SIZE.intValue()))
            .jsonPath("$.checksum")
            .value(is(DEFAULT_CHECKSUM))
            .jsonPath("$.uploadedDate")
            .value(is(DEFAULT_UPLOADED_DATE.toString()))
            .jsonPath("$.transcript")
            .value(is(DEFAULT_TRANSCRIPT))
            .jsonPath("$.durationSeconds")
            .value(is(DEFAULT_DURATION_SECONDS))
            .jsonPath("$.language")
            .value(is(DEFAULT_LANGUAGE))
            .jsonPath("$.deleted")
            .value(is(DEFAULT_DELETED))
            .jsonPath("$.updatedDate")
            .value(is(DEFAULT_UPDATED_DATE.toString()))
            .jsonPath("$.deletedDate")
            .value(is(DEFAULT_DELETED_DATE.toString()));
    }

    @Test
    void getAttachmentsByIdFiltering() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        Long id = attachment.getId();

        defaultAttachmentFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAttachmentFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAttachmentFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    void getAllAttachmentsByAttachmentTypeIsEqualToSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where attachmentType equals to
        defaultAttachmentFiltering("attachmentType.equals=" + DEFAULT_ATTACHMENT_TYPE, "attachmentType.equals=" + UPDATED_ATTACHMENT_TYPE);
    }

    @Test
    void getAllAttachmentsByAttachmentTypeIsInShouldWork() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where attachmentType in
        defaultAttachmentFiltering(
            "attachmentType.in=" + DEFAULT_ATTACHMENT_TYPE + "," + UPDATED_ATTACHMENT_TYPE,
            "attachmentType.in=" + UPDATED_ATTACHMENT_TYPE
        );
    }

    @Test
    void getAllAttachmentsByAttachmentTypeIsNullOrNotNull() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where attachmentType is not null
        defaultAttachmentFiltering("attachmentType.specified=true", "attachmentType.specified=false");
    }

    @Test
    void getAllAttachmentsByFileNameIsEqualToSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where fileName equals to
        defaultAttachmentFiltering("fileName.equals=" + DEFAULT_FILE_NAME, "fileName.equals=" + UPDATED_FILE_NAME);
    }

    @Test
    void getAllAttachmentsByFileNameIsInShouldWork() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where fileName in
        defaultAttachmentFiltering("fileName.in=" + DEFAULT_FILE_NAME + "," + UPDATED_FILE_NAME, "fileName.in=" + UPDATED_FILE_NAME);
    }

    @Test
    void getAllAttachmentsByFileNameIsNullOrNotNull() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where fileName is not null
        defaultAttachmentFiltering("fileName.specified=true", "fileName.specified=false");
    }

    @Test
    void getAllAttachmentsByFileNameContainsSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where fileName contains
        defaultAttachmentFiltering("fileName.contains=" + DEFAULT_FILE_NAME, "fileName.contains=" + UPDATED_FILE_NAME);
    }

    @Test
    void getAllAttachmentsByFileNameNotContainsSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where fileName does not contain
        defaultAttachmentFiltering("fileName.doesNotContain=" + UPDATED_FILE_NAME, "fileName.doesNotContain=" + DEFAULT_FILE_NAME);
    }

    @Test
    void getAllAttachmentsByFilePathIsEqualToSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where filePath equals to
        defaultAttachmentFiltering("filePath.equals=" + DEFAULT_FILE_PATH, "filePath.equals=" + UPDATED_FILE_PATH);
    }

    @Test
    void getAllAttachmentsByFilePathIsInShouldWork() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where filePath in
        defaultAttachmentFiltering("filePath.in=" + DEFAULT_FILE_PATH + "," + UPDATED_FILE_PATH, "filePath.in=" + UPDATED_FILE_PATH);
    }

    @Test
    void getAllAttachmentsByFilePathIsNullOrNotNull() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where filePath is not null
        defaultAttachmentFiltering("filePath.specified=true", "filePath.specified=false");
    }

    @Test
    void getAllAttachmentsByFilePathContainsSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where filePath contains
        defaultAttachmentFiltering("filePath.contains=" + DEFAULT_FILE_PATH, "filePath.contains=" + UPDATED_FILE_PATH);
    }

    @Test
    void getAllAttachmentsByFilePathNotContainsSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where filePath does not contain
        defaultAttachmentFiltering("filePath.doesNotContain=" + UPDATED_FILE_PATH, "filePath.doesNotContain=" + DEFAULT_FILE_PATH);
    }

    @Test
    void getAllAttachmentsByFileTypeIsEqualToSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where fileType equals to
        defaultAttachmentFiltering("fileType.equals=" + DEFAULT_FILE_TYPE, "fileType.equals=" + UPDATED_FILE_TYPE);
    }

    @Test
    void getAllAttachmentsByFileTypeIsInShouldWork() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where fileType in
        defaultAttachmentFiltering("fileType.in=" + DEFAULT_FILE_TYPE + "," + UPDATED_FILE_TYPE, "fileType.in=" + UPDATED_FILE_TYPE);
    }

    @Test
    void getAllAttachmentsByFileTypeIsNullOrNotNull() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where fileType is not null
        defaultAttachmentFiltering("fileType.specified=true", "fileType.specified=false");
    }

    @Test
    void getAllAttachmentsByFileTypeContainsSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where fileType contains
        defaultAttachmentFiltering("fileType.contains=" + DEFAULT_FILE_TYPE, "fileType.contains=" + UPDATED_FILE_TYPE);
    }

    @Test
    void getAllAttachmentsByFileTypeNotContainsSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where fileType does not contain
        defaultAttachmentFiltering("fileType.doesNotContain=" + UPDATED_FILE_TYPE, "fileType.doesNotContain=" + DEFAULT_FILE_TYPE);
    }

    @Test
    void getAllAttachmentsByFileSizeIsEqualToSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where fileSize equals to
        defaultAttachmentFiltering("fileSize.equals=" + DEFAULT_FILE_SIZE, "fileSize.equals=" + UPDATED_FILE_SIZE);
    }

    @Test
    void getAllAttachmentsByFileSizeIsInShouldWork() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where fileSize in
        defaultAttachmentFiltering("fileSize.in=" + DEFAULT_FILE_SIZE + "," + UPDATED_FILE_SIZE, "fileSize.in=" + UPDATED_FILE_SIZE);
    }

    @Test
    void getAllAttachmentsByFileSizeIsNullOrNotNull() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where fileSize is not null
        defaultAttachmentFiltering("fileSize.specified=true", "fileSize.specified=false");
    }

    @Test
    void getAllAttachmentsByFileSizeIsGreaterThanOrEqualToSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where fileSize is greater than or equal to
        defaultAttachmentFiltering("fileSize.greaterThanOrEqual=" + DEFAULT_FILE_SIZE, "fileSize.greaterThanOrEqual=" + UPDATED_FILE_SIZE);
    }

    @Test
    void getAllAttachmentsByFileSizeIsLessThanOrEqualToSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where fileSize is less than or equal to
        defaultAttachmentFiltering("fileSize.lessThanOrEqual=" + DEFAULT_FILE_SIZE, "fileSize.lessThanOrEqual=" + SMALLER_FILE_SIZE);
    }

    @Test
    void getAllAttachmentsByFileSizeIsLessThanSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where fileSize is less than
        defaultAttachmentFiltering("fileSize.lessThan=" + UPDATED_FILE_SIZE, "fileSize.lessThan=" + DEFAULT_FILE_SIZE);
    }

    @Test
    void getAllAttachmentsByFileSizeIsGreaterThanSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where fileSize is greater than
        defaultAttachmentFiltering("fileSize.greaterThan=" + SMALLER_FILE_SIZE, "fileSize.greaterThan=" + DEFAULT_FILE_SIZE);
    }

    @Test
    void getAllAttachmentsByChecksumIsEqualToSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where checksum equals to
        defaultAttachmentFiltering("checksum.equals=" + DEFAULT_CHECKSUM, "checksum.equals=" + UPDATED_CHECKSUM);
    }

    @Test
    void getAllAttachmentsByChecksumIsInShouldWork() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where checksum in
        defaultAttachmentFiltering("checksum.in=" + DEFAULT_CHECKSUM + "," + UPDATED_CHECKSUM, "checksum.in=" + UPDATED_CHECKSUM);
    }

    @Test
    void getAllAttachmentsByChecksumIsNullOrNotNull() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where checksum is not null
        defaultAttachmentFiltering("checksum.specified=true", "checksum.specified=false");
    }

    @Test
    void getAllAttachmentsByChecksumContainsSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where checksum contains
        defaultAttachmentFiltering("checksum.contains=" + DEFAULT_CHECKSUM, "checksum.contains=" + UPDATED_CHECKSUM);
    }

    @Test
    void getAllAttachmentsByChecksumNotContainsSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where checksum does not contain
        defaultAttachmentFiltering("checksum.doesNotContain=" + UPDATED_CHECKSUM, "checksum.doesNotContain=" + DEFAULT_CHECKSUM);
    }

    @Test
    void getAllAttachmentsByUploadedDateIsEqualToSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where uploadedDate equals to
        defaultAttachmentFiltering("uploadedDate.equals=" + DEFAULT_UPLOADED_DATE, "uploadedDate.equals=" + UPDATED_UPLOADED_DATE);
    }

    @Test
    void getAllAttachmentsByUploadedDateIsInShouldWork() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where uploadedDate in
        defaultAttachmentFiltering(
            "uploadedDate.in=" + DEFAULT_UPLOADED_DATE + "," + UPDATED_UPLOADED_DATE,
            "uploadedDate.in=" + UPDATED_UPLOADED_DATE
        );
    }

    @Test
    void getAllAttachmentsByUploadedDateIsNullOrNotNull() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where uploadedDate is not null
        defaultAttachmentFiltering("uploadedDate.specified=true", "uploadedDate.specified=false");
    }

    @Test
    void getAllAttachmentsByDurationSecondsIsEqualToSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where durationSeconds equals to
        defaultAttachmentFiltering(
            "durationSeconds.equals=" + DEFAULT_DURATION_SECONDS,
            "durationSeconds.equals=" + UPDATED_DURATION_SECONDS
        );
    }

    @Test
    void getAllAttachmentsByDurationSecondsIsInShouldWork() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where durationSeconds in
        defaultAttachmentFiltering(
            "durationSeconds.in=" + DEFAULT_DURATION_SECONDS + "," + UPDATED_DURATION_SECONDS,
            "durationSeconds.in=" + UPDATED_DURATION_SECONDS
        );
    }

    @Test
    void getAllAttachmentsByDurationSecondsIsNullOrNotNull() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where durationSeconds is not null
        defaultAttachmentFiltering("durationSeconds.specified=true", "durationSeconds.specified=false");
    }

    @Test
    void getAllAttachmentsByDurationSecondsIsGreaterThanOrEqualToSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where durationSeconds is greater than or equal to
        defaultAttachmentFiltering(
            "durationSeconds.greaterThanOrEqual=" + DEFAULT_DURATION_SECONDS,
            "durationSeconds.greaterThanOrEqual=" + UPDATED_DURATION_SECONDS
        );
    }

    @Test
    void getAllAttachmentsByDurationSecondsIsLessThanOrEqualToSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where durationSeconds is less than or equal to
        defaultAttachmentFiltering(
            "durationSeconds.lessThanOrEqual=" + DEFAULT_DURATION_SECONDS,
            "durationSeconds.lessThanOrEqual=" + SMALLER_DURATION_SECONDS
        );
    }

    @Test
    void getAllAttachmentsByDurationSecondsIsLessThanSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where durationSeconds is less than
        defaultAttachmentFiltering(
            "durationSeconds.lessThan=" + UPDATED_DURATION_SECONDS,
            "durationSeconds.lessThan=" + DEFAULT_DURATION_SECONDS
        );
    }

    @Test
    void getAllAttachmentsByDurationSecondsIsGreaterThanSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where durationSeconds is greater than
        defaultAttachmentFiltering(
            "durationSeconds.greaterThan=" + SMALLER_DURATION_SECONDS,
            "durationSeconds.greaterThan=" + DEFAULT_DURATION_SECONDS
        );
    }

    @Test
    void getAllAttachmentsByLanguageIsEqualToSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where language equals to
        defaultAttachmentFiltering("language.equals=" + DEFAULT_LANGUAGE, "language.equals=" + UPDATED_LANGUAGE);
    }

    @Test
    void getAllAttachmentsByLanguageIsInShouldWork() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where language in
        defaultAttachmentFiltering("language.in=" + DEFAULT_LANGUAGE + "," + UPDATED_LANGUAGE, "language.in=" + UPDATED_LANGUAGE);
    }

    @Test
    void getAllAttachmentsByLanguageIsNullOrNotNull() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where language is not null
        defaultAttachmentFiltering("language.specified=true", "language.specified=false");
    }

    @Test
    void getAllAttachmentsByLanguageContainsSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where language contains
        defaultAttachmentFiltering("language.contains=" + DEFAULT_LANGUAGE, "language.contains=" + UPDATED_LANGUAGE);
    }

    @Test
    void getAllAttachmentsByLanguageNotContainsSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where language does not contain
        defaultAttachmentFiltering("language.doesNotContain=" + UPDATED_LANGUAGE, "language.doesNotContain=" + DEFAULT_LANGUAGE);
    }

    @Test
    void getAllAttachmentsByDeletedIsEqualToSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where deleted equals to
        defaultAttachmentFiltering("deleted.equals=" + DEFAULT_DELETED, "deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    void getAllAttachmentsByDeletedIsInShouldWork() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where deleted in
        defaultAttachmentFiltering("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED, "deleted.in=" + UPDATED_DELETED);
    }

    @Test
    void getAllAttachmentsByDeletedIsNullOrNotNull() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where deleted is not null
        defaultAttachmentFiltering("deleted.specified=true", "deleted.specified=false");
    }

    @Test
    void getAllAttachmentsByUpdatedDateIsEqualToSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where updatedDate equals to
        defaultAttachmentFiltering("updatedDate.equals=" + DEFAULT_UPDATED_DATE, "updatedDate.equals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    void getAllAttachmentsByUpdatedDateIsInShouldWork() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where updatedDate in
        defaultAttachmentFiltering(
            "updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE,
            "updatedDate.in=" + UPDATED_UPDATED_DATE
        );
    }

    @Test
    void getAllAttachmentsByUpdatedDateIsNullOrNotNull() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where updatedDate is not null
        defaultAttachmentFiltering("updatedDate.specified=true", "updatedDate.specified=false");
    }

    @Test
    void getAllAttachmentsByDeletedDateIsEqualToSomething() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where deletedDate equals to
        defaultAttachmentFiltering("deletedDate.equals=" + DEFAULT_DELETED_DATE, "deletedDate.equals=" + UPDATED_DELETED_DATE);
    }

    @Test
    void getAllAttachmentsByDeletedDateIsInShouldWork() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where deletedDate in
        defaultAttachmentFiltering(
            "deletedDate.in=" + DEFAULT_DELETED_DATE + "," + UPDATED_DELETED_DATE,
            "deletedDate.in=" + UPDATED_DELETED_DATE
        );
    }

    @Test
    void getAllAttachmentsByDeletedDateIsNullOrNotNull() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        // Get all the attachmentList where deletedDate is not null
        defaultAttachmentFiltering("deletedDate.specified=true", "deletedDate.specified=false");
    }

    @Test
    void getAllAttachmentsByTicketIsEqualToSomething() {
        Ticket ticket = TicketResourceIT.createEntity(em);
        ticketRepository.save(ticket).block();
        Long ticketId = ticket.getId();
        attachment.setTicketId(ticketId);
        insertedAttachment = attachmentRepository.save(attachment).block();
        // Get all the attachmentList where ticket equals to ticketId
        defaultAttachmentShouldBeFound("ticketId.equals=" + ticketId);

        // Get all the attachmentList where ticket equals to (ticketId + 1)
        defaultAttachmentShouldNotBeFound("ticketId.equals=" + (ticketId + 1));
    }

    @Test
    void getAllAttachmentsByUploadedByIsEqualToSomething() {
        User uploadedBy = UserResourceIT.createEntity();
        userRepository.save(uploadedBy).block();
        Long uploadedById = uploadedBy.getId();
        attachment.setUploadedById(uploadedById);
        insertedAttachment = attachmentRepository.save(attachment).block();
        // Get all the attachmentList where uploadedBy equals to uploadedById
        defaultAttachmentShouldBeFound("uploadedById.equals=" + uploadedById);

        // Get all the attachmentList where uploadedBy equals to (uploadedById + 1)
        defaultAttachmentShouldNotBeFound("uploadedById.equals=" + (uploadedById + 1));
    }

    private void defaultAttachmentFiltering(String shouldBeFound, String shouldNotBeFound) {
        defaultAttachmentShouldBeFound(shouldBeFound);
        defaultAttachmentShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAttachmentShouldBeFound(String filter) {
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc&" + filter)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(attachment.getId().intValue()))
            .jsonPath("$.[*].attachmentType")
            .value(hasItem(DEFAULT_ATTACHMENT_TYPE.toString()))
            .jsonPath("$.[*].fileName")
            .value(hasItem(DEFAULT_FILE_NAME))
            .jsonPath("$.[*].filePath")
            .value(hasItem(DEFAULT_FILE_PATH))
            .jsonPath("$.[*].fileType")
            .value(hasItem(DEFAULT_FILE_TYPE))
            .jsonPath("$.[*].fileSize")
            .value(hasItem(DEFAULT_FILE_SIZE.intValue()))
            .jsonPath("$.[*].checksum")
            .value(hasItem(DEFAULT_CHECKSUM))
            .jsonPath("$.[*].uploadedDate")
            .value(hasItem(DEFAULT_UPLOADED_DATE.toString()))
            .jsonPath("$.[*].transcript")
            .value(hasItem(DEFAULT_TRANSCRIPT))
            .jsonPath("$.[*].durationSeconds")
            .value(hasItem(DEFAULT_DURATION_SECONDS))
            .jsonPath("$.[*].language")
            .value(hasItem(DEFAULT_LANGUAGE))
            .jsonPath("$.[*].deleted")
            .value(hasItem(DEFAULT_DELETED))
            .jsonPath("$.[*].updatedDate")
            .value(hasItem(DEFAULT_UPDATED_DATE.toString()))
            .jsonPath("$.[*].deletedDate")
            .value(hasItem(DEFAULT_DELETED_DATE.toString()));

        // Check, that the count call also returns 1
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "/count?sort=id,desc&" + filter)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$")
            .value(is(1));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAttachmentShouldNotBeFound(String filter) {
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc&" + filter)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$")
            .isArray()
            .jsonPath("$")
            .isEmpty();

        // Check, that the count call also returns 0
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "/count?sort=id,desc&" + filter)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$")
            .value(is(0));
    }

    @Test
    void getNonExistingAttachment() {
        // Get the attachment
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingAttachment() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the attachment
        Attachment updatedAttachment = attachmentRepository.findById(attachment.getId()).block();
        updatedAttachment
            .attachmentType(UPDATED_ATTACHMENT_TYPE)
            .fileName(UPDATED_FILE_NAME)
            .filePath(UPDATED_FILE_PATH)
            .fileType(UPDATED_FILE_TYPE)
            .fileSize(UPDATED_FILE_SIZE)
            .checksum(UPDATED_CHECKSUM)
            .uploadedDate(UPDATED_UPLOADED_DATE)
            .transcript(UPDATED_TRANSCRIPT)
            .durationSeconds(UPDATED_DURATION_SECONDS)
            .language(UPDATED_LANGUAGE)
            .deleted(UPDATED_DELETED)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deletedDate(UPDATED_DELETED_DATE);
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(updatedAttachment);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, attachmentDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(attachmentDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Attachment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAttachmentToMatchAllProperties(updatedAttachment);
    }

    @Test
    void putNonExistingAttachment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attachment.setId(longCount.incrementAndGet());

        // Create the Attachment
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, attachmentDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(attachmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Attachment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchAttachment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attachment.setId(longCount.incrementAndGet());

        // Create the Attachment
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(attachmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Attachment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamAttachment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attachment.setId(longCount.incrementAndGet());

        // Create the Attachment
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(attachmentDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Attachment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateAttachmentWithPatch() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the attachment using partial update
        Attachment partialUpdatedAttachment = new Attachment();
        partialUpdatedAttachment.setId(attachment.getId());

        partialUpdatedAttachment
            .attachmentType(UPDATED_ATTACHMENT_TYPE)
            .fileName(UPDATED_FILE_NAME)
            .filePath(UPDATED_FILE_PATH)
            .transcript(UPDATED_TRANSCRIPT)
            .durationSeconds(UPDATED_DURATION_SECONDS)
            .deleted(UPDATED_DELETED)
            .updatedDate(UPDATED_UPDATED_DATE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedAttachment.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedAttachment))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Attachment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAttachmentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAttachment, attachment),
            getPersistedAttachment(attachment)
        );
    }

    @Test
    void fullUpdateAttachmentWithPatch() throws Exception {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

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
            .transcript(UPDATED_TRANSCRIPT)
            .durationSeconds(UPDATED_DURATION_SECONDS)
            .language(UPDATED_LANGUAGE)
            .deleted(UPDATED_DELETED)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deletedDate(UPDATED_DELETED_DATE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedAttachment.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedAttachment))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Attachment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAttachmentUpdatableFieldsEquals(partialUpdatedAttachment, getPersistedAttachment(partialUpdatedAttachment));
    }

    @Test
    void patchNonExistingAttachment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attachment.setId(longCount.incrementAndGet());

        // Create the Attachment
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, attachmentDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(attachmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Attachment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchAttachment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attachment.setId(longCount.incrementAndGet());

        // Create the Attachment
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(attachmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Attachment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamAttachment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attachment.setId(longCount.incrementAndGet());

        // Create the Attachment
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(attachmentDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Attachment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteAttachment() {
        // Initialize the database
        insertedAttachment = attachmentRepository.save(attachment).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the attachment
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, attachment.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return attachmentRepository.count().block();
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
        return attachmentRepository.findById(attachment.getId()).block();
    }

    protected void assertPersistedAttachmentToMatchAllProperties(Attachment expectedAttachment) {
        // Test fails because reactive api returns an empty object instead of null
        // assertAttachmentAllPropertiesEquals(expectedAttachment, getPersistedAttachment(expectedAttachment));
        assertAttachmentUpdatableFieldsEquals(expectedAttachment, getPersistedAttachment(expectedAttachment));
    }

    protected void assertPersistedAttachmentToMatchUpdatableProperties(Attachment expectedAttachment) {
        // Test fails because reactive api returns an empty object instead of null
        // assertAttachmentAllUpdatablePropertiesEquals(expectedAttachment, getPersistedAttachment(expectedAttachment));
        assertAttachmentUpdatableFieldsEquals(expectedAttachment, getPersistedAttachment(expectedAttachment));
    }
}
