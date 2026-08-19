package com.diviso.spot_fix.web.rest;

import static com.diviso.spot_fix.domain.TicketAsserts.*;
import static com.diviso.spot_fix.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.diviso.spot_fix.IntegrationTest;
import com.diviso.spot_fix.domain.Department;
import com.diviso.spot_fix.domain.Location;
import com.diviso.spot_fix.domain.Ticket;
import com.diviso.spot_fix.domain.User;
import com.diviso.spot_fix.domain.Ward;
import com.diviso.spot_fix.domain.enumeration.Priority;
import com.diviso.spot_fix.domain.enumeration.TicketCategory;
import com.diviso.spot_fix.domain.enumeration.TicketStatus;
import com.diviso.spot_fix.domain.enumeration.Visibility;
import com.diviso.spot_fix.repository.TicketRepository;
import com.diviso.spot_fix.repository.UserRepository;
import com.diviso.spot_fix.service.TicketService;
import com.diviso.spot_fix.service.dto.TicketDTO;
import com.diviso.spot_fix.service.mapper.TicketMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
 * Integration tests for the {@link TicketResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TicketResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final TicketStatus DEFAULT_STATUS = TicketStatus.OPEN;
    private static final TicketStatus UPDATED_STATUS = TicketStatus.UNDER_REVIEW;

    private static final Priority DEFAULT_PRIORITY = Priority.LOW;
    private static final Priority UPDATED_PRIORITY = Priority.MEDIUM;

    private static final Visibility DEFAULT_VISIBILITY = Visibility.PUBLIC;
    private static final Visibility UPDATED_VISIBILITY = Visibility.PRIVATE;

    private static final TicketCategory DEFAULT_CATEGORY = TicketCategory.ROAD_DAMAGE;
    private static final TicketCategory UPDATED_CATEGORY = TicketCategory.WATER_SUPPLY;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXPECTED_RESOLUTION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPECTED_RESOLUTION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_RESOLVED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RESOLVED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_AI_SUMMARY = "AAAAAAAAAA";
    private static final String UPDATED_AI_SUMMARY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AI_DUPLICATE = false;
    private static final Boolean UPDATED_AI_DUPLICATE = true;

    private static final Double DEFAULT_DUPLICATE_SCORE = 0D;
    private static final Double UPDATED_DUPLICATE_SCORE = 1D;
    private static final Double SMALLER_DUPLICATE_SCORE = 0D - 1D;

    private static final Double DEFAULT_AI_CONFIDENCE = 0D;
    private static final Double UPDATED_AI_CONFIDENCE = 1D;
    private static final Double SMALLER_AI_CONFIDENCE = 0D - 1D;

    private static final Long DEFAULT_DUPLICATE_TICKET_ID = 1L;
    private static final Long UPDATED_DUPLICATE_TICKET_ID = 2L;
    private static final Long SMALLER_DUPLICATE_TICKET_ID = 1L - 1L;

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Instant DEFAULT_DELETED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/tickets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private TicketRepository ticketRepositoryMock;

    @Autowired
    private TicketMapper ticketMapper;

    @Mock
    private TicketService ticketServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTicketMockMvc;

    private Ticket ticket;

    private Ticket insertedTicket;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ticket createEntity(EntityManager em) {
        Ticket ticket = new Ticket()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .priority(DEFAULT_PRIORITY)
            .visibility(DEFAULT_VISIBILITY)
            .category(DEFAULT_CATEGORY)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .expectedResolutionDate(DEFAULT_EXPECTED_RESOLUTION_DATE)
            .resolvedDate(DEFAULT_RESOLVED_DATE)
            .aiSummary(DEFAULT_AI_SUMMARY)
            .aiDuplicate(DEFAULT_AI_DUPLICATE)
            .duplicateScore(DEFAULT_DUPLICATE_SCORE)
            .aiConfidence(DEFAULT_AI_CONFIDENCE)
            .duplicateTicketId(DEFAULT_DUPLICATE_TICKET_ID)
            .deleted(DEFAULT_DELETED)
            .deletedDate(DEFAULT_DELETED_DATE);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        ticket.setReportedBy(user);
        // Add required entity
        Location location;
        if (TestUtil.findAll(em, Location.class).isEmpty()) {
            location = LocationResourceIT.createEntity();
            em.persist(location);
            em.flush();
        } else {
            location = TestUtil.findAll(em, Location.class).get(0);
        }
        ticket.setLocation(location);
        // Add required entity
        Ward ward;
        if (TestUtil.findAll(em, Ward.class).isEmpty()) {
            ward = WardResourceIT.createEntity();
            em.persist(ward);
            em.flush();
        } else {
            ward = TestUtil.findAll(em, Ward.class).get(0);
        }
        ticket.setWard(ward);
        return ticket;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ticket createUpdatedEntity(EntityManager em) {
        Ticket updatedTicket = new Ticket()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .priority(UPDATED_PRIORITY)
            .visibility(UPDATED_VISIBILITY)
            .category(UPDATED_CATEGORY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .expectedResolutionDate(UPDATED_EXPECTED_RESOLUTION_DATE)
            .resolvedDate(UPDATED_RESOLVED_DATE)
            .aiSummary(UPDATED_AI_SUMMARY)
            .aiDuplicate(UPDATED_AI_DUPLICATE)
            .duplicateScore(UPDATED_DUPLICATE_SCORE)
            .aiConfidence(UPDATED_AI_CONFIDENCE)
            .duplicateTicketId(UPDATED_DUPLICATE_TICKET_ID)
            .deleted(UPDATED_DELETED)
            .deletedDate(UPDATED_DELETED_DATE);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        updatedTicket.setReportedBy(user);
        // Add required entity
        Location location;
        if (TestUtil.findAll(em, Location.class).isEmpty()) {
            location = LocationResourceIT.createUpdatedEntity();
            em.persist(location);
            em.flush();
        } else {
            location = TestUtil.findAll(em, Location.class).get(0);
        }
        updatedTicket.setLocation(location);
        // Add required entity
        Ward ward;
        if (TestUtil.findAll(em, Ward.class).isEmpty()) {
            ward = WardResourceIT.createUpdatedEntity();
            em.persist(ward);
            em.flush();
        } else {
            ward = TestUtil.findAll(em, Ward.class).get(0);
        }
        updatedTicket.setWard(ward);
        return updatedTicket;
    }

    @BeforeEach
    void initTest() {
        ticket = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedTicket != null) {
            ticketRepository.delete(insertedTicket);
            insertedTicket = null;
        }
    }

    @Test
    @Transactional
    void createTicket() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);
        var returnedTicketDTO = om.readValue(
            restTicketMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TicketDTO.class
        );

        // Validate the Ticket in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTicket = ticketMapper.toEntity(returnedTicketDTO);
        assertTicketUpdatableFieldsEquals(returnedTicket, getPersistedTicket(returnedTicket));

        insertedTicket = returnedTicket;
    }

    @Test
    @Transactional
    void createTicketWithExistingId() throws Exception {
        // Create the Ticket with an existing ID
        ticket.setId(1L);
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTicketMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ticket in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticket.setTitle(null);

        // Create the Ticket, which fails.
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        restTicketMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticket.setStatus(null);

        // Create the Ticket, which fails.
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        restTicketMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriorityIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticket.setPriority(null);

        // Create the Ticket, which fails.
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        restTicketMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVisibilityIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticket.setVisibility(null);

        // Create the Ticket, which fails.
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        restTicketMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCategoryIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticket.setCategory(null);

        // Create the Ticket, which fails.
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        restTicketMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticket.setCreatedDate(null);

        // Create the Ticket, which fails.
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        restTicketMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeletedIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticket.setDeleted(null);

        // Create the Ticket, which fails.
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        restTicketMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTickets() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList
        restTicketMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticket.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].visibility").value(hasItem(DEFAULT_VISIBILITY.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].expectedResolutionDate").value(hasItem(DEFAULT_EXPECTED_RESOLUTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].resolvedDate").value(hasItem(DEFAULT_RESOLVED_DATE.toString())))
            .andExpect(jsonPath("$.[*].aiSummary").value(hasItem(DEFAULT_AI_SUMMARY)))
            .andExpect(jsonPath("$.[*].aiDuplicate").value(hasItem(DEFAULT_AI_DUPLICATE)))
            .andExpect(jsonPath("$.[*].duplicateScore").value(hasItem(DEFAULT_DUPLICATE_SCORE)))
            .andExpect(jsonPath("$.[*].aiConfidence").value(hasItem(DEFAULT_AI_CONFIDENCE)))
            .andExpect(jsonPath("$.[*].duplicateTicketId").value(hasItem(DEFAULT_DUPLICATE_TICKET_ID.intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED)))
            .andExpect(jsonPath("$.[*].deletedDate").value(hasItem(DEFAULT_DELETED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTicketsWithEagerRelationshipsIsEnabled() throws Exception {
        when(ticketServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTicketMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(ticketServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTicketsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(ticketServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTicketMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(ticketRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTicket() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get the ticket
        restTicketMockMvc
            .perform(get(ENTITY_API_URL_ID, ticket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ticket.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()))
            .andExpect(jsonPath("$.visibility").value(DEFAULT_VISIBILITY.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.expectedResolutionDate").value(DEFAULT_EXPECTED_RESOLUTION_DATE.toString()))
            .andExpect(jsonPath("$.resolvedDate").value(DEFAULT_RESOLVED_DATE.toString()))
            .andExpect(jsonPath("$.aiSummary").value(DEFAULT_AI_SUMMARY))
            .andExpect(jsonPath("$.aiDuplicate").value(DEFAULT_AI_DUPLICATE))
            .andExpect(jsonPath("$.duplicateScore").value(DEFAULT_DUPLICATE_SCORE))
            .andExpect(jsonPath("$.aiConfidence").value(DEFAULT_AI_CONFIDENCE))
            .andExpect(jsonPath("$.duplicateTicketId").value(DEFAULT_DUPLICATE_TICKET_ID.intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED))
            .andExpect(jsonPath("$.deletedDate").value(DEFAULT_DELETED_DATE.toString()));
    }

    @Test
    @Transactional
    void getTicketsByIdFiltering() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        Long id = ticket.getId();

        defaultTicketFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultTicketFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultTicketFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTicketsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where title equals to
        defaultTicketFiltering("title.equals=" + DEFAULT_TITLE, "title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTicketsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where title in
        defaultTicketFiltering("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE, "title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTicketsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where title is not null
        defaultTicketFiltering("title.specified=true", "title.specified=false");
    }

    @Test
    @Transactional
    void getAllTicketsByTitleContainsSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where title contains
        defaultTicketFiltering("title.contains=" + DEFAULT_TITLE, "title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTicketsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where title does not contain
        defaultTicketFiltering("title.doesNotContain=" + UPDATED_TITLE, "title.doesNotContain=" + DEFAULT_TITLE);
    }

    @Test
    @Transactional
    void getAllTicketsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where status equals to
        defaultTicketFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTicketsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where status in
        defaultTicketFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTicketsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where status is not null
        defaultTicketFiltering("status.specified=true", "status.specified=false");
    }

    @Test
    @Transactional
    void getAllTicketsByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where priority equals to
        defaultTicketFiltering("priority.equals=" + DEFAULT_PRIORITY, "priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllTicketsByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where priority in
        defaultTicketFiltering("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY, "priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllTicketsByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where priority is not null
        defaultTicketFiltering("priority.specified=true", "priority.specified=false");
    }

    @Test
    @Transactional
    void getAllTicketsByVisibilityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where visibility equals to
        defaultTicketFiltering("visibility.equals=" + DEFAULT_VISIBILITY, "visibility.equals=" + UPDATED_VISIBILITY);
    }

    @Test
    @Transactional
    void getAllTicketsByVisibilityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where visibility in
        defaultTicketFiltering("visibility.in=" + DEFAULT_VISIBILITY + "," + UPDATED_VISIBILITY, "visibility.in=" + UPDATED_VISIBILITY);
    }

    @Test
    @Transactional
    void getAllTicketsByVisibilityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where visibility is not null
        defaultTicketFiltering("visibility.specified=true", "visibility.specified=false");
    }

    @Test
    @Transactional
    void getAllTicketsByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where category equals to
        defaultTicketFiltering("category.equals=" + DEFAULT_CATEGORY, "category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllTicketsByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where category in
        defaultTicketFiltering("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY, "category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllTicketsByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where category is not null
        defaultTicketFiltering("category.specified=true", "category.specified=false");
    }

    @Test
    @Transactional
    void getAllTicketsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where createdDate equals to
        defaultTicketFiltering("createdDate.equals=" + DEFAULT_CREATED_DATE, "createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllTicketsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where createdDate in
        defaultTicketFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllTicketsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where createdDate is not null
        defaultTicketFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTicketsByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where updatedDate equals to
        defaultTicketFiltering("updatedDate.equals=" + DEFAULT_UPDATED_DATE, "updatedDate.equals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllTicketsByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where updatedDate in
        defaultTicketFiltering(
            "updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE,
            "updatedDate.in=" + UPDATED_UPDATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllTicketsByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where updatedDate is not null
        defaultTicketFiltering("updatedDate.specified=true", "updatedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTicketsByExpectedResolutionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where expectedResolutionDate equals to
        defaultTicketFiltering(
            "expectedResolutionDate.equals=" + DEFAULT_EXPECTED_RESOLUTION_DATE,
            "expectedResolutionDate.equals=" + UPDATED_EXPECTED_RESOLUTION_DATE
        );
    }

    @Test
    @Transactional
    void getAllTicketsByExpectedResolutionDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where expectedResolutionDate in
        defaultTicketFiltering(
            "expectedResolutionDate.in=" + DEFAULT_EXPECTED_RESOLUTION_DATE + "," + UPDATED_EXPECTED_RESOLUTION_DATE,
            "expectedResolutionDate.in=" + UPDATED_EXPECTED_RESOLUTION_DATE
        );
    }

    @Test
    @Transactional
    void getAllTicketsByExpectedResolutionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where expectedResolutionDate is not null
        defaultTicketFiltering("expectedResolutionDate.specified=true", "expectedResolutionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTicketsByResolvedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where resolvedDate equals to
        defaultTicketFiltering("resolvedDate.equals=" + DEFAULT_RESOLVED_DATE, "resolvedDate.equals=" + UPDATED_RESOLVED_DATE);
    }

    @Test
    @Transactional
    void getAllTicketsByResolvedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where resolvedDate in
        defaultTicketFiltering(
            "resolvedDate.in=" + DEFAULT_RESOLVED_DATE + "," + UPDATED_RESOLVED_DATE,
            "resolvedDate.in=" + UPDATED_RESOLVED_DATE
        );
    }

    @Test
    @Transactional
    void getAllTicketsByResolvedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where resolvedDate is not null
        defaultTicketFiltering("resolvedDate.specified=true", "resolvedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTicketsByAiDuplicateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where aiDuplicate equals to
        defaultTicketFiltering("aiDuplicate.equals=" + DEFAULT_AI_DUPLICATE, "aiDuplicate.equals=" + UPDATED_AI_DUPLICATE);
    }

    @Test
    @Transactional
    void getAllTicketsByAiDuplicateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where aiDuplicate in
        defaultTicketFiltering(
            "aiDuplicate.in=" + DEFAULT_AI_DUPLICATE + "," + UPDATED_AI_DUPLICATE,
            "aiDuplicate.in=" + UPDATED_AI_DUPLICATE
        );
    }

    @Test
    @Transactional
    void getAllTicketsByAiDuplicateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where aiDuplicate is not null
        defaultTicketFiltering("aiDuplicate.specified=true", "aiDuplicate.specified=false");
    }

    @Test
    @Transactional
    void getAllTicketsByDuplicateScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where duplicateScore equals to
        defaultTicketFiltering("duplicateScore.equals=" + DEFAULT_DUPLICATE_SCORE, "duplicateScore.equals=" + UPDATED_DUPLICATE_SCORE);
    }

    @Test
    @Transactional
    void getAllTicketsByDuplicateScoreIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where duplicateScore in
        defaultTicketFiltering(
            "duplicateScore.in=" + DEFAULT_DUPLICATE_SCORE + "," + UPDATED_DUPLICATE_SCORE,
            "duplicateScore.in=" + UPDATED_DUPLICATE_SCORE
        );
    }

    @Test
    @Transactional
    void getAllTicketsByDuplicateScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where duplicateScore is not null
        defaultTicketFiltering("duplicateScore.specified=true", "duplicateScore.specified=false");
    }

    @Test
    @Transactional
    void getAllTicketsByDuplicateScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where duplicateScore is greater than or equal to
        defaultTicketFiltering(
            "duplicateScore.greaterThanOrEqual=" + DEFAULT_DUPLICATE_SCORE,
            "duplicateScore.greaterThanOrEqual=" + (DEFAULT_DUPLICATE_SCORE + 1)
        );
    }

    @Test
    @Transactional
    void getAllTicketsByDuplicateScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where duplicateScore is less than or equal to
        defaultTicketFiltering(
            "duplicateScore.lessThanOrEqual=" + DEFAULT_DUPLICATE_SCORE,
            "duplicateScore.lessThanOrEqual=" + SMALLER_DUPLICATE_SCORE
        );
    }

    @Test
    @Transactional
    void getAllTicketsByDuplicateScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where duplicateScore is less than
        defaultTicketFiltering(
            "duplicateScore.lessThan=" + (DEFAULT_DUPLICATE_SCORE + 1),
            "duplicateScore.lessThan=" + DEFAULT_DUPLICATE_SCORE
        );
    }

    @Test
    @Transactional
    void getAllTicketsByDuplicateScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where duplicateScore is greater than
        defaultTicketFiltering(
            "duplicateScore.greaterThan=" + SMALLER_DUPLICATE_SCORE,
            "duplicateScore.greaterThan=" + DEFAULT_DUPLICATE_SCORE
        );
    }

    @Test
    @Transactional
    void getAllTicketsByAiConfidenceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where aiConfidence equals to
        defaultTicketFiltering("aiConfidence.equals=" + DEFAULT_AI_CONFIDENCE, "aiConfidence.equals=" + UPDATED_AI_CONFIDENCE);
    }

    @Test
    @Transactional
    void getAllTicketsByAiConfidenceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where aiConfidence in
        defaultTicketFiltering(
            "aiConfidence.in=" + DEFAULT_AI_CONFIDENCE + "," + UPDATED_AI_CONFIDENCE,
            "aiConfidence.in=" + UPDATED_AI_CONFIDENCE
        );
    }

    @Test
    @Transactional
    void getAllTicketsByAiConfidenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where aiConfidence is not null
        defaultTicketFiltering("aiConfidence.specified=true", "aiConfidence.specified=false");
    }

    @Test
    @Transactional
    void getAllTicketsByAiConfidenceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where aiConfidence is greater than or equal to
        defaultTicketFiltering(
            "aiConfidence.greaterThanOrEqual=" + DEFAULT_AI_CONFIDENCE,
            "aiConfidence.greaterThanOrEqual=" + (DEFAULT_AI_CONFIDENCE + 1)
        );
    }

    @Test
    @Transactional
    void getAllTicketsByAiConfidenceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where aiConfidence is less than or equal to
        defaultTicketFiltering(
            "aiConfidence.lessThanOrEqual=" + DEFAULT_AI_CONFIDENCE,
            "aiConfidence.lessThanOrEqual=" + SMALLER_AI_CONFIDENCE
        );
    }

    @Test
    @Transactional
    void getAllTicketsByAiConfidenceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where aiConfidence is less than
        defaultTicketFiltering("aiConfidence.lessThan=" + (DEFAULT_AI_CONFIDENCE + 1), "aiConfidence.lessThan=" + DEFAULT_AI_CONFIDENCE);
    }

    @Test
    @Transactional
    void getAllTicketsByAiConfidenceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where aiConfidence is greater than
        defaultTicketFiltering("aiConfidence.greaterThan=" + SMALLER_AI_CONFIDENCE, "aiConfidence.greaterThan=" + DEFAULT_AI_CONFIDENCE);
    }

    @Test
    @Transactional
    void getAllTicketsByDuplicateTicketIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where duplicateTicketId equals to
        defaultTicketFiltering(
            "duplicateTicketId.equals=" + DEFAULT_DUPLICATE_TICKET_ID,
            "duplicateTicketId.equals=" + UPDATED_DUPLICATE_TICKET_ID
        );
    }

    @Test
    @Transactional
    void getAllTicketsByDuplicateTicketIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where duplicateTicketId in
        defaultTicketFiltering(
            "duplicateTicketId.in=" + DEFAULT_DUPLICATE_TICKET_ID + "," + UPDATED_DUPLICATE_TICKET_ID,
            "duplicateTicketId.in=" + UPDATED_DUPLICATE_TICKET_ID
        );
    }

    @Test
    @Transactional
    void getAllTicketsByDuplicateTicketIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where duplicateTicketId is not null
        defaultTicketFiltering("duplicateTicketId.specified=true", "duplicateTicketId.specified=false");
    }

    @Test
    @Transactional
    void getAllTicketsByDuplicateTicketIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where duplicateTicketId is greater than or equal to
        defaultTicketFiltering(
            "duplicateTicketId.greaterThanOrEqual=" + DEFAULT_DUPLICATE_TICKET_ID,
            "duplicateTicketId.greaterThanOrEqual=" + UPDATED_DUPLICATE_TICKET_ID
        );
    }

    @Test
    @Transactional
    void getAllTicketsByDuplicateTicketIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where duplicateTicketId is less than or equal to
        defaultTicketFiltering(
            "duplicateTicketId.lessThanOrEqual=" + DEFAULT_DUPLICATE_TICKET_ID,
            "duplicateTicketId.lessThanOrEqual=" + SMALLER_DUPLICATE_TICKET_ID
        );
    }

    @Test
    @Transactional
    void getAllTicketsByDuplicateTicketIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where duplicateTicketId is less than
        defaultTicketFiltering(
            "duplicateTicketId.lessThan=" + UPDATED_DUPLICATE_TICKET_ID,
            "duplicateTicketId.lessThan=" + DEFAULT_DUPLICATE_TICKET_ID
        );
    }

    @Test
    @Transactional
    void getAllTicketsByDuplicateTicketIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where duplicateTicketId is greater than
        defaultTicketFiltering(
            "duplicateTicketId.greaterThan=" + SMALLER_DUPLICATE_TICKET_ID,
            "duplicateTicketId.greaterThan=" + DEFAULT_DUPLICATE_TICKET_ID
        );
    }

    @Test
    @Transactional
    void getAllTicketsByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where deleted equals to
        defaultTicketFiltering("deleted.equals=" + DEFAULT_DELETED, "deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllTicketsByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where deleted in
        defaultTicketFiltering("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED, "deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllTicketsByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where deleted is not null
        defaultTicketFiltering("deleted.specified=true", "deleted.specified=false");
    }

    @Test
    @Transactional
    void getAllTicketsByDeletedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where deletedDate equals to
        defaultTicketFiltering("deletedDate.equals=" + DEFAULT_DELETED_DATE, "deletedDate.equals=" + UPDATED_DELETED_DATE);
    }

    @Test
    @Transactional
    void getAllTicketsByDeletedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where deletedDate in
        defaultTicketFiltering(
            "deletedDate.in=" + DEFAULT_DELETED_DATE + "," + UPDATED_DELETED_DATE,
            "deletedDate.in=" + UPDATED_DELETED_DATE
        );
    }

    @Test
    @Transactional
    void getAllTicketsByDeletedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList where deletedDate is not null
        defaultTicketFiltering("deletedDate.specified=true", "deletedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTicketsByReportedByIsEqualToSomething() throws Exception {
        User reportedBy;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            ticketRepository.saveAndFlush(ticket);
            reportedBy = UserResourceIT.createEntity();
        } else {
            reportedBy = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(reportedBy);
        em.flush();
        ticket.setReportedBy(reportedBy);
        ticketRepository.saveAndFlush(ticket);
        Long reportedById = reportedBy.getId();
        // Get all the ticketList where reportedBy equals to reportedById
        defaultTicketShouldBeFound("reportedById.equals=" + reportedById);

        // Get all the ticketList where reportedBy equals to (reportedById + 1)
        defaultTicketShouldNotBeFound("reportedById.equals=" + (reportedById + 1));
    }

    @Test
    @Transactional
    void getAllTicketsByLocationIsEqualToSomething() throws Exception {
        Location location;
        if (TestUtil.findAll(em, Location.class).isEmpty()) {
            ticketRepository.saveAndFlush(ticket);
            location = LocationResourceIT.createEntity();
        } else {
            location = TestUtil.findAll(em, Location.class).get(0);
        }
        em.persist(location);
        em.flush();
        ticket.setLocation(location);
        ticketRepository.saveAndFlush(ticket);
        Long locationId = location.getId();
        // Get all the ticketList where location equals to locationId
        defaultTicketShouldBeFound("locationId.equals=" + locationId);

        // Get all the ticketList where location equals to (locationId + 1)
        defaultTicketShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }

    @Test
    @Transactional
    void getAllTicketsByWardIsEqualToSomething() throws Exception {
        Ward ward;
        if (TestUtil.findAll(em, Ward.class).isEmpty()) {
            ticketRepository.saveAndFlush(ticket);
            ward = WardResourceIT.createEntity();
        } else {
            ward = TestUtil.findAll(em, Ward.class).get(0);
        }
        em.persist(ward);
        em.flush();
        ticket.setWard(ward);
        ticketRepository.saveAndFlush(ticket);
        Long wardId = ward.getId();
        // Get all the ticketList where ward equals to wardId
        defaultTicketShouldBeFound("wardId.equals=" + wardId);

        // Get all the ticketList where ward equals to (wardId + 1)
        defaultTicketShouldNotBeFound("wardId.equals=" + (wardId + 1));
    }

    @Test
    @Transactional
    void getAllTicketsByAssignedDepartmentIsEqualToSomething() throws Exception {
        Department assignedDepartment;
        if (TestUtil.findAll(em, Department.class).isEmpty()) {
            ticketRepository.saveAndFlush(ticket);
            assignedDepartment = DepartmentResourceIT.createEntity();
        } else {
            assignedDepartment = TestUtil.findAll(em, Department.class).get(0);
        }
        em.persist(assignedDepartment);
        em.flush();
        ticket.setAssignedDepartment(assignedDepartment);
        ticketRepository.saveAndFlush(ticket);
        Long assignedDepartmentId = assignedDepartment.getId();
        // Get all the ticketList where assignedDepartment equals to assignedDepartmentId
        defaultTicketShouldBeFound("assignedDepartmentId.equals=" + assignedDepartmentId);

        // Get all the ticketList where assignedDepartment equals to (assignedDepartmentId + 1)
        defaultTicketShouldNotBeFound("assignedDepartmentId.equals=" + (assignedDepartmentId + 1));
    }

    private void defaultTicketFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultTicketShouldBeFound(shouldBeFound);
        defaultTicketShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTicketShouldBeFound(String filter) throws Exception {
        restTicketMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticket.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].visibility").value(hasItem(DEFAULT_VISIBILITY.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].expectedResolutionDate").value(hasItem(DEFAULT_EXPECTED_RESOLUTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].resolvedDate").value(hasItem(DEFAULT_RESOLVED_DATE.toString())))
            .andExpect(jsonPath("$.[*].aiSummary").value(hasItem(DEFAULT_AI_SUMMARY)))
            .andExpect(jsonPath("$.[*].aiDuplicate").value(hasItem(DEFAULT_AI_DUPLICATE)))
            .andExpect(jsonPath("$.[*].duplicateScore").value(hasItem(DEFAULT_DUPLICATE_SCORE)))
            .andExpect(jsonPath("$.[*].aiConfidence").value(hasItem(DEFAULT_AI_CONFIDENCE)))
            .andExpect(jsonPath("$.[*].duplicateTicketId").value(hasItem(DEFAULT_DUPLICATE_TICKET_ID.intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED)))
            .andExpect(jsonPath("$.[*].deletedDate").value(hasItem(DEFAULT_DELETED_DATE.toString())));

        // Check, that the count call also returns 1
        restTicketMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTicketShouldNotBeFound(String filter) throws Exception {
        restTicketMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTicketMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTicket() throws Exception {
        // Get the ticket
        restTicketMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTicket() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ticket
        Ticket updatedTicket = ticketRepository.findById(ticket.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTicket are not directly saved in db
        em.detach(updatedTicket);
        updatedTicket
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .priority(UPDATED_PRIORITY)
            .visibility(UPDATED_VISIBILITY)
            .category(UPDATED_CATEGORY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .expectedResolutionDate(UPDATED_EXPECTED_RESOLUTION_DATE)
            .resolvedDate(UPDATED_RESOLVED_DATE)
            .aiSummary(UPDATED_AI_SUMMARY)
            .aiDuplicate(UPDATED_AI_DUPLICATE)
            .duplicateScore(UPDATED_DUPLICATE_SCORE)
            .aiConfidence(UPDATED_AI_CONFIDENCE)
            .duplicateTicketId(UPDATED_DUPLICATE_TICKET_ID)
            .deleted(UPDATED_DELETED)
            .deletedDate(UPDATED_DELETED_DATE);
        TicketDTO ticketDTO = ticketMapper.toDto(updatedTicket);

        restTicketMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ticketDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketDTO))
            )
            .andExpect(status().isOk());

        // Validate the Ticket in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTicketToMatchAllProperties(updatedTicket);
    }

    @Test
    @Transactional
    void putNonExistingTicket() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticket.setId(longCount.incrementAndGet());

        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ticketDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ticket in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTicket() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticket.setId(longCount.incrementAndGet());

        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ticketDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ticket in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTicket() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticket.setId(longCount.incrementAndGet());

        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ticket in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTicketWithPatch() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ticket using partial update
        Ticket partialUpdatedTicket = new Ticket();
        partialUpdatedTicket.setId(ticket.getId());

        partialUpdatedTicket
            .category(UPDATED_CATEGORY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .expectedResolutionDate(UPDATED_EXPECTED_RESOLUTION_DATE)
            .duplicateTicketId(UPDATED_DUPLICATE_TICKET_ID)
            .deleted(UPDATED_DELETED);

        restTicketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTicket.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTicket))
            )
            .andExpect(status().isOk());

        // Validate the Ticket in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTicketUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTicket, ticket), getPersistedTicket(ticket));
    }

    @Test
    @Transactional
    void fullUpdateTicketWithPatch() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ticket using partial update
        Ticket partialUpdatedTicket = new Ticket();
        partialUpdatedTicket.setId(ticket.getId());

        partialUpdatedTicket
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .priority(UPDATED_PRIORITY)
            .visibility(UPDATED_VISIBILITY)
            .category(UPDATED_CATEGORY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .expectedResolutionDate(UPDATED_EXPECTED_RESOLUTION_DATE)
            .resolvedDate(UPDATED_RESOLVED_DATE)
            .aiSummary(UPDATED_AI_SUMMARY)
            .aiDuplicate(UPDATED_AI_DUPLICATE)
            .duplicateScore(UPDATED_DUPLICATE_SCORE)
            .aiConfidence(UPDATED_AI_CONFIDENCE)
            .duplicateTicketId(UPDATED_DUPLICATE_TICKET_ID)
            .deleted(UPDATED_DELETED)
            .deletedDate(UPDATED_DELETED_DATE);

        restTicketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTicket.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTicket))
            )
            .andExpect(status().isOk());

        // Validate the Ticket in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTicketUpdatableFieldsEquals(partialUpdatedTicket, getPersistedTicket(partialUpdatedTicket));
    }

    @Test
    @Transactional
    void patchNonExistingTicket() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticket.setId(longCount.incrementAndGet());

        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ticketDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ticketDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ticket in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTicket() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticket.setId(longCount.incrementAndGet());

        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ticketDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ticket in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTicket() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticket.setId(longCount.incrementAndGet());

        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ticketDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ticket in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTicket() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.saveAndFlush(ticket);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ticket
        restTicketMockMvc
            .perform(delete(ENTITY_API_URL_ID, ticket.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ticketRepository.count();
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

    protected Ticket getPersistedTicket(Ticket ticket) {
        return ticketRepository.findById(ticket.getId()).orElseThrow();
    }

    protected void assertPersistedTicketToMatchAllProperties(Ticket expectedTicket) {
        assertTicketAllPropertiesEquals(expectedTicket, getPersistedTicket(expectedTicket));
    }

    protected void assertPersistedTicketToMatchUpdatableProperties(Ticket expectedTicket) {
        assertTicketAllUpdatablePropertiesEquals(expectedTicket, getPersistedTicket(expectedTicket));
    }
}
