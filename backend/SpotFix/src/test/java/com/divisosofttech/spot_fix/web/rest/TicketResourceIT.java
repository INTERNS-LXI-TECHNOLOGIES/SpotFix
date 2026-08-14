package com.divisosofttech.spot_fix.web.rest;

import static com.divisosofttech.spot_fix.domain.TicketAsserts.*;
import static com.divisosofttech.spot_fix.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import com.divisosofttech.spot_fix.IntegrationTest;
import com.divisosofttech.spot_fix.domain.Department;
import com.divisosofttech.spot_fix.domain.Location;
import com.divisosofttech.spot_fix.domain.Ticket;
import com.divisosofttech.spot_fix.domain.User;
import com.divisosofttech.spot_fix.domain.Ward;
import com.divisosofttech.spot_fix.domain.enumeration.Priority;
import com.divisosofttech.spot_fix.domain.enumeration.TicketCategory;
import com.divisosofttech.spot_fix.domain.enumeration.TicketStatus;
import com.divisosofttech.spot_fix.domain.enumeration.Visibility;
import com.divisosofttech.spot_fix.repository.DepartmentRepository;
import com.divisosofttech.spot_fix.repository.EntityManager;
import com.divisosofttech.spot_fix.repository.LocationRepository;
import com.divisosofttech.spot_fix.repository.TicketRepository;
import com.divisosofttech.spot_fix.repository.UserRepository;
import com.divisosofttech.spot_fix.repository.UserRepository;
import com.divisosofttech.spot_fix.repository.WardRepository;
import com.divisosofttech.spot_fix.service.TicketService;
import com.divisosofttech.spot_fix.service.dto.TicketDTO;
import com.divisosofttech.spot_fix.service.mapper.TicketMapper;
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
 * Integration tests for the {@link TicketResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
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
    private WebTestClient webTestClient;

    private Ticket ticket;

    private Ticket insertedTicket;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private WardRepository wardRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

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
        User user = em.insert(UserResourceIT.createEntity()).block();
        ticket.setReportedBy(user);
        // Add required entity
        Location location;
        location = em.insert(LocationResourceIT.createEntity()).block();
        ticket.setLocation(location);
        // Add required entity
        Ward ward;
        ward = em.insert(WardResourceIT.createEntity()).block();
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
        User user = em.insert(UserResourceIT.createEntity()).block();
        updatedTicket.setReportedBy(user);
        // Add required entity
        Location location;
        location = em.insert(LocationResourceIT.createUpdatedEntity()).block();
        updatedTicket.setLocation(location);
        // Add required entity
        Ward ward;
        ward = em.insert(WardResourceIT.createUpdatedEntity()).block();
        updatedTicket.setWard(ward);
        return updatedTicket;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Ticket.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
        UserResourceIT.deleteEntities(em);
        LocationResourceIT.deleteEntities(em);
        WardResourceIT.deleteEntities(em);
    }

    @BeforeEach
    void initTest() {
        ticket = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedTicket != null) {
            ticketRepository.delete(insertedTicket).block();
            insertedTicket = null;
        }
        deleteEntities(em);
        userRepository.deleteAllUserAuthorities().block();
        userRepository.deleteAll().block();
    }

    @Test
    void createTicket() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);
        var returnedTicketDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(TicketDTO.class)
            .returnResult()
            .getResponseBody();

        // Validate the Ticket in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTicket = ticketMapper.toEntity(returnedTicketDTO);
        assertTicketUpdatableFieldsEquals(returnedTicket, getPersistedTicket(returnedTicket));

        insertedTicket = returnedTicket;
    }

    @Test
    void createTicketWithExistingId() throws Exception {
        // Create the Ticket with an existing ID
        ticket.setId(1L);
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Ticket in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkTitleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticket.setTitle(null);

        // Create the Ticket, which fails.
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticket.setStatus(null);

        // Create the Ticket, which fails.
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkPriorityIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticket.setPriority(null);

        // Create the Ticket, which fails.
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkVisibilityIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticket.setVisibility(null);

        // Create the Ticket, which fails.
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkCategoryIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticket.setCategory(null);

        // Create the Ticket, which fails.
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkCreatedDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticket.setCreatedDate(null);

        // Create the Ticket, which fails.
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDeletedIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticket.setDeleted(null);

        // Create the Ticket, which fails.
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllTickets() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList
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
            .value(hasItem(ticket.getId().intValue()))
            .jsonPath("$.[*].title")
            .value(hasItem(DEFAULT_TITLE))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION))
            .jsonPath("$.[*].status")
            .value(hasItem(DEFAULT_STATUS.toString()))
            .jsonPath("$.[*].priority")
            .value(hasItem(DEFAULT_PRIORITY.toString()))
            .jsonPath("$.[*].visibility")
            .value(hasItem(DEFAULT_VISIBILITY.toString()))
            .jsonPath("$.[*].category")
            .value(hasItem(DEFAULT_CATEGORY.toString()))
            .jsonPath("$.[*].createdDate")
            .value(hasItem(DEFAULT_CREATED_DATE.toString()))
            .jsonPath("$.[*].updatedDate")
            .value(hasItem(DEFAULT_UPDATED_DATE.toString()))
            .jsonPath("$.[*].expectedResolutionDate")
            .value(hasItem(DEFAULT_EXPECTED_RESOLUTION_DATE.toString()))
            .jsonPath("$.[*].resolvedDate")
            .value(hasItem(DEFAULT_RESOLVED_DATE.toString()))
            .jsonPath("$.[*].aiSummary")
            .value(hasItem(DEFAULT_AI_SUMMARY))
            .jsonPath("$.[*].aiDuplicate")
            .value(hasItem(DEFAULT_AI_DUPLICATE))
            .jsonPath("$.[*].duplicateScore")
            .value(hasItem(DEFAULT_DUPLICATE_SCORE))
            .jsonPath("$.[*].aiConfidence")
            .value(hasItem(DEFAULT_AI_CONFIDENCE))
            .jsonPath("$.[*].duplicateTicketId")
            .value(hasItem(DEFAULT_DUPLICATE_TICKET_ID.intValue()))
            .jsonPath("$.[*].deleted")
            .value(hasItem(DEFAULT_DELETED))
            .jsonPath("$.[*].deletedDate")
            .value(hasItem(DEFAULT_DELETED_DATE.toString()));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTicketsWithEagerRelationshipsIsEnabled() {
        when(ticketServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=true").exchange().expectStatus().isOk();

        verify(ticketServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTicketsWithEagerRelationshipsIsNotEnabled() {
        when(ticketServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=false").exchange().expectStatus().isOk();
        verify(ticketRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    void getTicket() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get the ticket
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, ticket.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(ticket.getId().intValue()))
            .jsonPath("$.title")
            .value(is(DEFAULT_TITLE))
            .jsonPath("$.description")
            .value(is(DEFAULT_DESCRIPTION))
            .jsonPath("$.status")
            .value(is(DEFAULT_STATUS.toString()))
            .jsonPath("$.priority")
            .value(is(DEFAULT_PRIORITY.toString()))
            .jsonPath("$.visibility")
            .value(is(DEFAULT_VISIBILITY.toString()))
            .jsonPath("$.category")
            .value(is(DEFAULT_CATEGORY.toString()))
            .jsonPath("$.createdDate")
            .value(is(DEFAULT_CREATED_DATE.toString()))
            .jsonPath("$.updatedDate")
            .value(is(DEFAULT_UPDATED_DATE.toString()))
            .jsonPath("$.expectedResolutionDate")
            .value(is(DEFAULT_EXPECTED_RESOLUTION_DATE.toString()))
            .jsonPath("$.resolvedDate")
            .value(is(DEFAULT_RESOLVED_DATE.toString()))
            .jsonPath("$.aiSummary")
            .value(is(DEFAULT_AI_SUMMARY))
            .jsonPath("$.aiDuplicate")
            .value(is(DEFAULT_AI_DUPLICATE))
            .jsonPath("$.duplicateScore")
            .value(is(DEFAULT_DUPLICATE_SCORE))
            .jsonPath("$.aiConfidence")
            .value(is(DEFAULT_AI_CONFIDENCE))
            .jsonPath("$.duplicateTicketId")
            .value(is(DEFAULT_DUPLICATE_TICKET_ID.intValue()))
            .jsonPath("$.deleted")
            .value(is(DEFAULT_DELETED))
            .jsonPath("$.deletedDate")
            .value(is(DEFAULT_DELETED_DATE.toString()));
    }

    @Test
    void getTicketsByIdFiltering() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        Long id = ticket.getId();

        defaultTicketFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultTicketFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultTicketFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    void getAllTicketsByTitleIsEqualToSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where title equals to
        defaultTicketFiltering("title.equals=" + DEFAULT_TITLE, "title.equals=" + UPDATED_TITLE);
    }

    @Test
    void getAllTicketsByTitleIsInShouldWork() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where title in
        defaultTicketFiltering("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE, "title.in=" + UPDATED_TITLE);
    }

    @Test
    void getAllTicketsByTitleIsNullOrNotNull() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where title is not null
        defaultTicketFiltering("title.specified=true", "title.specified=false");
    }

    @Test
    void getAllTicketsByTitleContainsSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where title contains
        defaultTicketFiltering("title.contains=" + DEFAULT_TITLE, "title.contains=" + UPDATED_TITLE);
    }

    @Test
    void getAllTicketsByTitleNotContainsSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where title does not contain
        defaultTicketFiltering("title.doesNotContain=" + UPDATED_TITLE, "title.doesNotContain=" + DEFAULT_TITLE);
    }

    @Test
    void getAllTicketsByStatusIsEqualToSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where status equals to
        defaultTicketFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    void getAllTicketsByStatusIsInShouldWork() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where status in
        defaultTicketFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    void getAllTicketsByStatusIsNullOrNotNull() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where status is not null
        defaultTicketFiltering("status.specified=true", "status.specified=false");
    }

    @Test
    void getAllTicketsByPriorityIsEqualToSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where priority equals to
        defaultTicketFiltering("priority.equals=" + DEFAULT_PRIORITY, "priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    void getAllTicketsByPriorityIsInShouldWork() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where priority in
        defaultTicketFiltering("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY, "priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    void getAllTicketsByPriorityIsNullOrNotNull() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where priority is not null
        defaultTicketFiltering("priority.specified=true", "priority.specified=false");
    }

    @Test
    void getAllTicketsByVisibilityIsEqualToSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where visibility equals to
        defaultTicketFiltering("visibility.equals=" + DEFAULT_VISIBILITY, "visibility.equals=" + UPDATED_VISIBILITY);
    }

    @Test
    void getAllTicketsByVisibilityIsInShouldWork() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where visibility in
        defaultTicketFiltering("visibility.in=" + DEFAULT_VISIBILITY + "," + UPDATED_VISIBILITY, "visibility.in=" + UPDATED_VISIBILITY);
    }

    @Test
    void getAllTicketsByVisibilityIsNullOrNotNull() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where visibility is not null
        defaultTicketFiltering("visibility.specified=true", "visibility.specified=false");
    }

    @Test
    void getAllTicketsByCategoryIsEqualToSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where category equals to
        defaultTicketFiltering("category.equals=" + DEFAULT_CATEGORY, "category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    void getAllTicketsByCategoryIsInShouldWork() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where category in
        defaultTicketFiltering("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY, "category.in=" + UPDATED_CATEGORY);
    }

    @Test
    void getAllTicketsByCategoryIsNullOrNotNull() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where category is not null
        defaultTicketFiltering("category.specified=true", "category.specified=false");
    }

    @Test
    void getAllTicketsByCreatedDateIsEqualToSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where createdDate equals to
        defaultTicketFiltering("createdDate.equals=" + DEFAULT_CREATED_DATE, "createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    void getAllTicketsByCreatedDateIsInShouldWork() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where createdDate in
        defaultTicketFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    void getAllTicketsByCreatedDateIsNullOrNotNull() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where createdDate is not null
        defaultTicketFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    void getAllTicketsByUpdatedDateIsEqualToSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where updatedDate equals to
        defaultTicketFiltering("updatedDate.equals=" + DEFAULT_UPDATED_DATE, "updatedDate.equals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    void getAllTicketsByUpdatedDateIsInShouldWork() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where updatedDate in
        defaultTicketFiltering(
            "updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE,
            "updatedDate.in=" + UPDATED_UPDATED_DATE
        );
    }

    @Test
    void getAllTicketsByUpdatedDateIsNullOrNotNull() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where updatedDate is not null
        defaultTicketFiltering("updatedDate.specified=true", "updatedDate.specified=false");
    }

    @Test
    void getAllTicketsByExpectedResolutionDateIsEqualToSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where expectedResolutionDate equals to
        defaultTicketFiltering(
            "expectedResolutionDate.equals=" + DEFAULT_EXPECTED_RESOLUTION_DATE,
            "expectedResolutionDate.equals=" + UPDATED_EXPECTED_RESOLUTION_DATE
        );
    }

    @Test
    void getAllTicketsByExpectedResolutionDateIsInShouldWork() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where expectedResolutionDate in
        defaultTicketFiltering(
            "expectedResolutionDate.in=" + DEFAULT_EXPECTED_RESOLUTION_DATE + "," + UPDATED_EXPECTED_RESOLUTION_DATE,
            "expectedResolutionDate.in=" + UPDATED_EXPECTED_RESOLUTION_DATE
        );
    }

    @Test
    void getAllTicketsByExpectedResolutionDateIsNullOrNotNull() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where expectedResolutionDate is not null
        defaultTicketFiltering("expectedResolutionDate.specified=true", "expectedResolutionDate.specified=false");
    }

    @Test
    void getAllTicketsByResolvedDateIsEqualToSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where resolvedDate equals to
        defaultTicketFiltering("resolvedDate.equals=" + DEFAULT_RESOLVED_DATE, "resolvedDate.equals=" + UPDATED_RESOLVED_DATE);
    }

    @Test
    void getAllTicketsByResolvedDateIsInShouldWork() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where resolvedDate in
        defaultTicketFiltering(
            "resolvedDate.in=" + DEFAULT_RESOLVED_DATE + "," + UPDATED_RESOLVED_DATE,
            "resolvedDate.in=" + UPDATED_RESOLVED_DATE
        );
    }

    @Test
    void getAllTicketsByResolvedDateIsNullOrNotNull() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where resolvedDate is not null
        defaultTicketFiltering("resolvedDate.specified=true", "resolvedDate.specified=false");
    }

    @Test
    void getAllTicketsByAiDuplicateIsEqualToSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where aiDuplicate equals to
        defaultTicketFiltering("aiDuplicate.equals=" + DEFAULT_AI_DUPLICATE, "aiDuplicate.equals=" + UPDATED_AI_DUPLICATE);
    }

    @Test
    void getAllTicketsByAiDuplicateIsInShouldWork() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where aiDuplicate in
        defaultTicketFiltering(
            "aiDuplicate.in=" + DEFAULT_AI_DUPLICATE + "," + UPDATED_AI_DUPLICATE,
            "aiDuplicate.in=" + UPDATED_AI_DUPLICATE
        );
    }

    @Test
    void getAllTicketsByAiDuplicateIsNullOrNotNull() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where aiDuplicate is not null
        defaultTicketFiltering("aiDuplicate.specified=true", "aiDuplicate.specified=false");
    }

    @Test
    void getAllTicketsByDuplicateScoreIsEqualToSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where duplicateScore equals to
        defaultTicketFiltering("duplicateScore.equals=" + DEFAULT_DUPLICATE_SCORE, "duplicateScore.equals=" + UPDATED_DUPLICATE_SCORE);
    }

    @Test
    void getAllTicketsByDuplicateScoreIsInShouldWork() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where duplicateScore in
        defaultTicketFiltering(
            "duplicateScore.in=" + DEFAULT_DUPLICATE_SCORE + "," + UPDATED_DUPLICATE_SCORE,
            "duplicateScore.in=" + UPDATED_DUPLICATE_SCORE
        );
    }

    @Test
    void getAllTicketsByDuplicateScoreIsNullOrNotNull() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where duplicateScore is not null
        defaultTicketFiltering("duplicateScore.specified=true", "duplicateScore.specified=false");
    }

    @Test
    void getAllTicketsByDuplicateScoreIsGreaterThanOrEqualToSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where duplicateScore is greater than or equal to
        defaultTicketFiltering(
            "duplicateScore.greaterThanOrEqual=" + DEFAULT_DUPLICATE_SCORE,
            "duplicateScore.greaterThanOrEqual=" + (DEFAULT_DUPLICATE_SCORE + 1)
        );
    }

    @Test
    void getAllTicketsByDuplicateScoreIsLessThanOrEqualToSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where duplicateScore is less than or equal to
        defaultTicketFiltering(
            "duplicateScore.lessThanOrEqual=" + DEFAULT_DUPLICATE_SCORE,
            "duplicateScore.lessThanOrEqual=" + SMALLER_DUPLICATE_SCORE
        );
    }

    @Test
    void getAllTicketsByDuplicateScoreIsLessThanSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where duplicateScore is less than
        defaultTicketFiltering(
            "duplicateScore.lessThan=" + (DEFAULT_DUPLICATE_SCORE + 1),
            "duplicateScore.lessThan=" + DEFAULT_DUPLICATE_SCORE
        );
    }

    @Test
    void getAllTicketsByDuplicateScoreIsGreaterThanSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where duplicateScore is greater than
        defaultTicketFiltering(
            "duplicateScore.greaterThan=" + SMALLER_DUPLICATE_SCORE,
            "duplicateScore.greaterThan=" + DEFAULT_DUPLICATE_SCORE
        );
    }

    @Test
    void getAllTicketsByAiConfidenceIsEqualToSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where aiConfidence equals to
        defaultTicketFiltering("aiConfidence.equals=" + DEFAULT_AI_CONFIDENCE, "aiConfidence.equals=" + UPDATED_AI_CONFIDENCE);
    }

    @Test
    void getAllTicketsByAiConfidenceIsInShouldWork() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where aiConfidence in
        defaultTicketFiltering(
            "aiConfidence.in=" + DEFAULT_AI_CONFIDENCE + "," + UPDATED_AI_CONFIDENCE,
            "aiConfidence.in=" + UPDATED_AI_CONFIDENCE
        );
    }

    @Test
    void getAllTicketsByAiConfidenceIsNullOrNotNull() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where aiConfidence is not null
        defaultTicketFiltering("aiConfidence.specified=true", "aiConfidence.specified=false");
    }

    @Test
    void getAllTicketsByAiConfidenceIsGreaterThanOrEqualToSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where aiConfidence is greater than or equal to
        defaultTicketFiltering(
            "aiConfidence.greaterThanOrEqual=" + DEFAULT_AI_CONFIDENCE,
            "aiConfidence.greaterThanOrEqual=" + (DEFAULT_AI_CONFIDENCE + 1)
        );
    }

    @Test
    void getAllTicketsByAiConfidenceIsLessThanOrEqualToSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where aiConfidence is less than or equal to
        defaultTicketFiltering(
            "aiConfidence.lessThanOrEqual=" + DEFAULT_AI_CONFIDENCE,
            "aiConfidence.lessThanOrEqual=" + SMALLER_AI_CONFIDENCE
        );
    }

    @Test
    void getAllTicketsByAiConfidenceIsLessThanSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where aiConfidence is less than
        defaultTicketFiltering("aiConfidence.lessThan=" + (DEFAULT_AI_CONFIDENCE + 1), "aiConfidence.lessThan=" + DEFAULT_AI_CONFIDENCE);
    }

    @Test
    void getAllTicketsByAiConfidenceIsGreaterThanSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where aiConfidence is greater than
        defaultTicketFiltering("aiConfidence.greaterThan=" + SMALLER_AI_CONFIDENCE, "aiConfidence.greaterThan=" + DEFAULT_AI_CONFIDENCE);
    }

    @Test
    void getAllTicketsByDuplicateTicketIdIsEqualToSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where duplicateTicketId equals to
        defaultTicketFiltering(
            "duplicateTicketId.equals=" + DEFAULT_DUPLICATE_TICKET_ID,
            "duplicateTicketId.equals=" + UPDATED_DUPLICATE_TICKET_ID
        );
    }

    @Test
    void getAllTicketsByDuplicateTicketIdIsInShouldWork() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where duplicateTicketId in
        defaultTicketFiltering(
            "duplicateTicketId.in=" + DEFAULT_DUPLICATE_TICKET_ID + "," + UPDATED_DUPLICATE_TICKET_ID,
            "duplicateTicketId.in=" + UPDATED_DUPLICATE_TICKET_ID
        );
    }

    @Test
    void getAllTicketsByDuplicateTicketIdIsNullOrNotNull() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where duplicateTicketId is not null
        defaultTicketFiltering("duplicateTicketId.specified=true", "duplicateTicketId.specified=false");
    }

    @Test
    void getAllTicketsByDuplicateTicketIdIsGreaterThanOrEqualToSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where duplicateTicketId is greater than or equal to
        defaultTicketFiltering(
            "duplicateTicketId.greaterThanOrEqual=" + DEFAULT_DUPLICATE_TICKET_ID,
            "duplicateTicketId.greaterThanOrEqual=" + UPDATED_DUPLICATE_TICKET_ID
        );
    }

    @Test
    void getAllTicketsByDuplicateTicketIdIsLessThanOrEqualToSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where duplicateTicketId is less than or equal to
        defaultTicketFiltering(
            "duplicateTicketId.lessThanOrEqual=" + DEFAULT_DUPLICATE_TICKET_ID,
            "duplicateTicketId.lessThanOrEqual=" + SMALLER_DUPLICATE_TICKET_ID
        );
    }

    @Test
    void getAllTicketsByDuplicateTicketIdIsLessThanSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where duplicateTicketId is less than
        defaultTicketFiltering(
            "duplicateTicketId.lessThan=" + UPDATED_DUPLICATE_TICKET_ID,
            "duplicateTicketId.lessThan=" + DEFAULT_DUPLICATE_TICKET_ID
        );
    }

    @Test
    void getAllTicketsByDuplicateTicketIdIsGreaterThanSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where duplicateTicketId is greater than
        defaultTicketFiltering(
            "duplicateTicketId.greaterThan=" + SMALLER_DUPLICATE_TICKET_ID,
            "duplicateTicketId.greaterThan=" + DEFAULT_DUPLICATE_TICKET_ID
        );
    }

    @Test
    void getAllTicketsByDeletedIsEqualToSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where deleted equals to
        defaultTicketFiltering("deleted.equals=" + DEFAULT_DELETED, "deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    void getAllTicketsByDeletedIsInShouldWork() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where deleted in
        defaultTicketFiltering("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED, "deleted.in=" + UPDATED_DELETED);
    }

    @Test
    void getAllTicketsByDeletedIsNullOrNotNull() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where deleted is not null
        defaultTicketFiltering("deleted.specified=true", "deleted.specified=false");
    }

    @Test
    void getAllTicketsByDeletedDateIsEqualToSomething() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where deletedDate equals to
        defaultTicketFiltering("deletedDate.equals=" + DEFAULT_DELETED_DATE, "deletedDate.equals=" + UPDATED_DELETED_DATE);
    }

    @Test
    void getAllTicketsByDeletedDateIsInShouldWork() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where deletedDate in
        defaultTicketFiltering(
            "deletedDate.in=" + DEFAULT_DELETED_DATE + "," + UPDATED_DELETED_DATE,
            "deletedDate.in=" + UPDATED_DELETED_DATE
        );
    }

    @Test
    void getAllTicketsByDeletedDateIsNullOrNotNull() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        // Get all the ticketList where deletedDate is not null
        defaultTicketFiltering("deletedDate.specified=true", "deletedDate.specified=false");
    }

    @Test
    void getAllTicketsByReportedByIsEqualToSomething() {
        User reportedBy = UserResourceIT.createEntity();
        userRepository.save(reportedBy).block();
        Long reportedById = reportedBy.getId();
        ticket.setReportedById(reportedById);
        insertedTicket = ticketRepository.save(ticket).block();
        // Get all the ticketList where reportedBy equals to reportedById
        defaultTicketShouldBeFound("reportedById.equals=" + reportedById);

        // Get all the ticketList where reportedBy equals to (reportedById + 1)
        defaultTicketShouldNotBeFound("reportedById.equals=" + (reportedById + 1));
    }

    @Test
    void getAllTicketsByLocationIsEqualToSomething() {
        Location location = LocationResourceIT.createEntity();
        locationRepository.save(location).block();
        Long locationId = location.getId();
        ticket.setLocationId(locationId);
        insertedTicket = ticketRepository.save(ticket).block();
        // Get all the ticketList where location equals to locationId
        defaultTicketShouldBeFound("locationId.equals=" + locationId);

        // Get all the ticketList where location equals to (locationId + 1)
        defaultTicketShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }

    @Test
    void getAllTicketsByWardIsEqualToSomething() {
        Ward ward = WardResourceIT.createEntity();
        wardRepository.save(ward).block();
        Long wardId = ward.getId();
        ticket.setWardId(wardId);
        insertedTicket = ticketRepository.save(ticket).block();
        // Get all the ticketList where ward equals to wardId
        defaultTicketShouldBeFound("wardId.equals=" + wardId);

        // Get all the ticketList where ward equals to (wardId + 1)
        defaultTicketShouldNotBeFound("wardId.equals=" + (wardId + 1));
    }

    @Test
    void getAllTicketsByAssignedDepartmentIsEqualToSomething() {
        Department assignedDepartment = DepartmentResourceIT.createEntity();
        departmentRepository.save(assignedDepartment).block();
        Long assignedDepartmentId = assignedDepartment.getId();
        ticket.setAssignedDepartmentId(assignedDepartmentId);
        insertedTicket = ticketRepository.save(ticket).block();
        // Get all the ticketList where assignedDepartment equals to assignedDepartmentId
        defaultTicketShouldBeFound("assignedDepartmentId.equals=" + assignedDepartmentId);

        // Get all the ticketList where assignedDepartment equals to (assignedDepartmentId + 1)
        defaultTicketShouldNotBeFound("assignedDepartmentId.equals=" + (assignedDepartmentId + 1));
    }

    private void defaultTicketFiltering(String shouldBeFound, String shouldNotBeFound) {
        defaultTicketShouldBeFound(shouldBeFound);
        defaultTicketShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTicketShouldBeFound(String filter) {
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
            .value(hasItem(ticket.getId().intValue()))
            .jsonPath("$.[*].title")
            .value(hasItem(DEFAULT_TITLE))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION))
            .jsonPath("$.[*].status")
            .value(hasItem(DEFAULT_STATUS.toString()))
            .jsonPath("$.[*].priority")
            .value(hasItem(DEFAULT_PRIORITY.toString()))
            .jsonPath("$.[*].visibility")
            .value(hasItem(DEFAULT_VISIBILITY.toString()))
            .jsonPath("$.[*].category")
            .value(hasItem(DEFAULT_CATEGORY.toString()))
            .jsonPath("$.[*].createdDate")
            .value(hasItem(DEFAULT_CREATED_DATE.toString()))
            .jsonPath("$.[*].updatedDate")
            .value(hasItem(DEFAULT_UPDATED_DATE.toString()))
            .jsonPath("$.[*].expectedResolutionDate")
            .value(hasItem(DEFAULT_EXPECTED_RESOLUTION_DATE.toString()))
            .jsonPath("$.[*].resolvedDate")
            .value(hasItem(DEFAULT_RESOLVED_DATE.toString()))
            .jsonPath("$.[*].aiSummary")
            .value(hasItem(DEFAULT_AI_SUMMARY))
            .jsonPath("$.[*].aiDuplicate")
            .value(hasItem(DEFAULT_AI_DUPLICATE))
            .jsonPath("$.[*].duplicateScore")
            .value(hasItem(DEFAULT_DUPLICATE_SCORE))
            .jsonPath("$.[*].aiConfidence")
            .value(hasItem(DEFAULT_AI_CONFIDENCE))
            .jsonPath("$.[*].duplicateTicketId")
            .value(hasItem(DEFAULT_DUPLICATE_TICKET_ID.intValue()))
            .jsonPath("$.[*].deleted")
            .value(hasItem(DEFAULT_DELETED))
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
    private void defaultTicketShouldNotBeFound(String filter) {
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
    void getNonExistingTicket() {
        // Get the ticket
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingTicket() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ticket
        Ticket updatedTicket = ticketRepository.findById(ticket.getId()).block();
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

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, ticketDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Ticket in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTicketToMatchAllProperties(updatedTicket);
    }

    @Test
    void putNonExistingTicket() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticket.setId(longCount.incrementAndGet());

        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, ticketDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Ticket in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTicket() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticket.setId(longCount.incrementAndGet());

        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Ticket in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTicket() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticket.setId(longCount.incrementAndGet());

        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Ticket in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTicketWithPatch() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

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

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTicket.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedTicket))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Ticket in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTicketUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTicket, ticket), getPersistedTicket(ticket));
    }

    @Test
    void fullUpdateTicketWithPatch() throws Exception {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

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

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTicket.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedTicket))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Ticket in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTicketUpdatableFieldsEquals(partialUpdatedTicket, getPersistedTicket(partialUpdatedTicket));
    }

    @Test
    void patchNonExistingTicket() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticket.setId(longCount.incrementAndGet());

        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, ticketDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(ticketDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Ticket in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTicket() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticket.setId(longCount.incrementAndGet());

        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(ticketDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Ticket in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTicket() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticket.setId(longCount.incrementAndGet());

        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(ticketDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Ticket in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTicket() {
        // Initialize the database
        insertedTicket = ticketRepository.save(ticket).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ticket
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, ticket.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ticketRepository.count().block();
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
        return ticketRepository.findById(ticket.getId()).block();
    }

    protected void assertPersistedTicketToMatchAllProperties(Ticket expectedTicket) {
        // Test fails because reactive api returns an empty object instead of null
        // assertTicketAllPropertiesEquals(expectedTicket, getPersistedTicket(expectedTicket));
        assertTicketUpdatableFieldsEquals(expectedTicket, getPersistedTicket(expectedTicket));
    }

    protected void assertPersistedTicketToMatchUpdatableProperties(Ticket expectedTicket) {
        // Test fails because reactive api returns an empty object instead of null
        // assertTicketAllUpdatablePropertiesEquals(expectedTicket, getPersistedTicket(expectedTicket));
        assertTicketUpdatableFieldsEquals(expectedTicket, getPersistedTicket(expectedTicket));
    }
}
