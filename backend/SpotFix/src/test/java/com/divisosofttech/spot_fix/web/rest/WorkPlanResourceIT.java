package com.divisosofttech.spot_fix.web.rest;

import static com.divisosofttech.spot_fix.domain.WorkPlanAsserts.*;
import static com.divisosofttech.spot_fix.web.rest.TestUtil.createUpdateProxyForBean;
import static com.divisosofttech.spot_fix.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.divisosofttech.spot_fix.IntegrationTest;
import com.divisosofttech.spot_fix.domain.Department;
import com.divisosofttech.spot_fix.domain.Ticket;
import com.divisosofttech.spot_fix.domain.WorkPlan;
import com.divisosofttech.spot_fix.domain.enumeration.WorkStatus;
import com.divisosofttech.spot_fix.repository.DepartmentRepository;
import com.divisosofttech.spot_fix.repository.EntityManager;
import com.divisosofttech.spot_fix.repository.TicketRepository;
import com.divisosofttech.spot_fix.repository.WorkPlanRepository;
import com.divisosofttech.spot_fix.service.dto.WorkPlanDTO;
import com.divisosofttech.spot_fix.service.mapper.WorkPlanMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link WorkPlanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class WorkPlanResourceIT {

    private static final BigDecimal DEFAULT_ESTIMATED_COST = new BigDecimal(0);
    private static final BigDecimal UPDATED_ESTIMATED_COST = new BigDecimal(1);
    private static final BigDecimal SMALLER_ESTIMATED_COST = new BigDecimal(0 - 1);

    private static final Instant DEFAULT_STARTED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STARTED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXPECTED_COMPLETION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPECTED_COMPLETION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ACTUAL_COMPLETION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTUAL_COMPLETION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_COMPLETION_PERCENTAGE = 0;
    private static final Integer UPDATED_COMPLETION_PERCENTAGE = 1;
    private static final Integer SMALLER_COMPLETION_PERCENTAGE = 0 - 1;

    private static final WorkStatus DEFAULT_STATUS = WorkStatus.PLANNED;
    private static final WorkStatus UPDATED_STATUS = WorkStatus.ASSIGNED;

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Instant DEFAULT_DELETED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/work-plans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WorkPlanRepository workPlanRepository;

    @Autowired
    private WorkPlanMapper workPlanMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private WorkPlan workPlan;

    private WorkPlan insertedWorkPlan;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkPlan createEntity(EntityManager em) {
        WorkPlan workPlan = new WorkPlan()
            .estimatedCost(DEFAULT_ESTIMATED_COST)
            .startedDate(DEFAULT_STARTED_DATE)
            .expectedCompletionDate(DEFAULT_EXPECTED_COMPLETION_DATE)
            .actualCompletionDate(DEFAULT_ACTUAL_COMPLETION_DATE)
            .completionPercentage(DEFAULT_COMPLETION_PERCENTAGE)
            .status(DEFAULT_STATUS)
            .remarks(DEFAULT_REMARKS)
            .deleted(DEFAULT_DELETED)
            .deletedDate(DEFAULT_DELETED_DATE);
        // Add required entity
        Ticket ticket;
        ticket = em.insert(TicketResourceIT.createEntity(em)).block();
        workPlan.setTicket(ticket);
        // Add required entity
        Department department;
        department = em.insert(DepartmentResourceIT.createEntity()).block();
        workPlan.setDepartment(department);
        return workPlan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkPlan createUpdatedEntity(EntityManager em) {
        WorkPlan updatedWorkPlan = new WorkPlan()
            .estimatedCost(UPDATED_ESTIMATED_COST)
            .startedDate(UPDATED_STARTED_DATE)
            .expectedCompletionDate(UPDATED_EXPECTED_COMPLETION_DATE)
            .actualCompletionDate(UPDATED_ACTUAL_COMPLETION_DATE)
            .completionPercentage(UPDATED_COMPLETION_PERCENTAGE)
            .status(UPDATED_STATUS)
            .remarks(UPDATED_REMARKS)
            .deleted(UPDATED_DELETED)
            .deletedDate(UPDATED_DELETED_DATE);
        // Add required entity
        Ticket ticket;
        ticket = em.insert(TicketResourceIT.createUpdatedEntity(em)).block();
        updatedWorkPlan.setTicket(ticket);
        // Add required entity
        Department department;
        department = em.insert(DepartmentResourceIT.createUpdatedEntity()).block();
        updatedWorkPlan.setDepartment(department);
        return updatedWorkPlan;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(WorkPlan.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
        TicketResourceIT.deleteEntities(em);
        DepartmentResourceIT.deleteEntities(em);
    }

    @BeforeEach
    void initTest() {
        workPlan = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedWorkPlan != null) {
            workPlanRepository.delete(insertedWorkPlan).block();
            insertedWorkPlan = null;
        }
        deleteEntities(em);
    }

    @Test
    void createWorkPlan() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the WorkPlan
        WorkPlanDTO workPlanDTO = workPlanMapper.toDto(workPlan);
        var returnedWorkPlanDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(workPlanDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(WorkPlanDTO.class)
            .returnResult()
            .getResponseBody();

        // Validate the WorkPlan in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedWorkPlan = workPlanMapper.toEntity(returnedWorkPlanDTO);
        assertWorkPlanUpdatableFieldsEquals(returnedWorkPlan, getPersistedWorkPlan(returnedWorkPlan));

        insertedWorkPlan = returnedWorkPlan;
    }

    @Test
    void createWorkPlanWithExistingId() throws Exception {
        // Create the WorkPlan with an existing ID
        workPlan.setId(1L);
        WorkPlanDTO workPlanDTO = workPlanMapper.toDto(workPlan);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(workPlanDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the WorkPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        workPlan.setStatus(null);

        // Create the WorkPlan, which fails.
        WorkPlanDTO workPlanDTO = workPlanMapper.toDto(workPlan);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(workPlanDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDeletedIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        workPlan.setDeleted(null);

        // Create the WorkPlan, which fails.
        WorkPlanDTO workPlanDTO = workPlanMapper.toDto(workPlan);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(workPlanDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllWorkPlans() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList
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
            .value(hasItem(workPlan.getId().intValue()))
            .jsonPath("$.[*].estimatedCost")
            .value(hasItem(sameNumber(DEFAULT_ESTIMATED_COST)))
            .jsonPath("$.[*].startedDate")
            .value(hasItem(DEFAULT_STARTED_DATE.toString()))
            .jsonPath("$.[*].expectedCompletionDate")
            .value(hasItem(DEFAULT_EXPECTED_COMPLETION_DATE.toString()))
            .jsonPath("$.[*].actualCompletionDate")
            .value(hasItem(DEFAULT_ACTUAL_COMPLETION_DATE.toString()))
            .jsonPath("$.[*].completionPercentage")
            .value(hasItem(DEFAULT_COMPLETION_PERCENTAGE))
            .jsonPath("$.[*].status")
            .value(hasItem(DEFAULT_STATUS.toString()))
            .jsonPath("$.[*].remarks")
            .value(hasItem(DEFAULT_REMARKS))
            .jsonPath("$.[*].deleted")
            .value(hasItem(DEFAULT_DELETED))
            .jsonPath("$.[*].deletedDate")
            .value(hasItem(DEFAULT_DELETED_DATE.toString()));
    }

    @Test
    void getWorkPlan() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get the workPlan
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, workPlan.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(workPlan.getId().intValue()))
            .jsonPath("$.estimatedCost")
            .value(is(sameNumber(DEFAULT_ESTIMATED_COST)))
            .jsonPath("$.startedDate")
            .value(is(DEFAULT_STARTED_DATE.toString()))
            .jsonPath("$.expectedCompletionDate")
            .value(is(DEFAULT_EXPECTED_COMPLETION_DATE.toString()))
            .jsonPath("$.actualCompletionDate")
            .value(is(DEFAULT_ACTUAL_COMPLETION_DATE.toString()))
            .jsonPath("$.completionPercentage")
            .value(is(DEFAULT_COMPLETION_PERCENTAGE))
            .jsonPath("$.status")
            .value(is(DEFAULT_STATUS.toString()))
            .jsonPath("$.remarks")
            .value(is(DEFAULT_REMARKS))
            .jsonPath("$.deleted")
            .value(is(DEFAULT_DELETED))
            .jsonPath("$.deletedDate")
            .value(is(DEFAULT_DELETED_DATE.toString()));
    }

    @Test
    void getWorkPlansByIdFiltering() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        Long id = workPlan.getId();

        defaultWorkPlanFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultWorkPlanFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultWorkPlanFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    void getAllWorkPlansByEstimatedCostIsEqualToSomething() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where estimatedCost equals to
        defaultWorkPlanFiltering("estimatedCost.equals=" + DEFAULT_ESTIMATED_COST, "estimatedCost.equals=" + UPDATED_ESTIMATED_COST);
    }

    @Test
    void getAllWorkPlansByEstimatedCostIsInShouldWork() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where estimatedCost in
        defaultWorkPlanFiltering(
            "estimatedCost.in=" + DEFAULT_ESTIMATED_COST + "," + UPDATED_ESTIMATED_COST,
            "estimatedCost.in=" + UPDATED_ESTIMATED_COST
        );
    }

    @Test
    void getAllWorkPlansByEstimatedCostIsNullOrNotNull() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where estimatedCost is not null
        defaultWorkPlanFiltering("estimatedCost.specified=true", "estimatedCost.specified=false");
    }

    @Test
    void getAllWorkPlansByEstimatedCostIsGreaterThanOrEqualToSomething() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where estimatedCost is greater than or equal to
        defaultWorkPlanFiltering(
            "estimatedCost.greaterThanOrEqual=" + DEFAULT_ESTIMATED_COST,
            "estimatedCost.greaterThanOrEqual=" + UPDATED_ESTIMATED_COST
        );
    }

    @Test
    void getAllWorkPlansByEstimatedCostIsLessThanOrEqualToSomething() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where estimatedCost is less than or equal to
        defaultWorkPlanFiltering(
            "estimatedCost.lessThanOrEqual=" + DEFAULT_ESTIMATED_COST,
            "estimatedCost.lessThanOrEqual=" + SMALLER_ESTIMATED_COST
        );
    }

    @Test
    void getAllWorkPlansByEstimatedCostIsLessThanSomething() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where estimatedCost is less than
        defaultWorkPlanFiltering("estimatedCost.lessThan=" + UPDATED_ESTIMATED_COST, "estimatedCost.lessThan=" + DEFAULT_ESTIMATED_COST);
    }

    @Test
    void getAllWorkPlansByEstimatedCostIsGreaterThanSomething() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where estimatedCost is greater than
        defaultWorkPlanFiltering(
            "estimatedCost.greaterThan=" + SMALLER_ESTIMATED_COST,
            "estimatedCost.greaterThan=" + DEFAULT_ESTIMATED_COST
        );
    }

    @Test
    void getAllWorkPlansByStartedDateIsEqualToSomething() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where startedDate equals to
        defaultWorkPlanFiltering("startedDate.equals=" + DEFAULT_STARTED_DATE, "startedDate.equals=" + UPDATED_STARTED_DATE);
    }

    @Test
    void getAllWorkPlansByStartedDateIsInShouldWork() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where startedDate in
        defaultWorkPlanFiltering(
            "startedDate.in=" + DEFAULT_STARTED_DATE + "," + UPDATED_STARTED_DATE,
            "startedDate.in=" + UPDATED_STARTED_DATE
        );
    }

    @Test
    void getAllWorkPlansByStartedDateIsNullOrNotNull() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where startedDate is not null
        defaultWorkPlanFiltering("startedDate.specified=true", "startedDate.specified=false");
    }

    @Test
    void getAllWorkPlansByExpectedCompletionDateIsEqualToSomething() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where expectedCompletionDate equals to
        defaultWorkPlanFiltering(
            "expectedCompletionDate.equals=" + DEFAULT_EXPECTED_COMPLETION_DATE,
            "expectedCompletionDate.equals=" + UPDATED_EXPECTED_COMPLETION_DATE
        );
    }

    @Test
    void getAllWorkPlansByExpectedCompletionDateIsInShouldWork() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where expectedCompletionDate in
        defaultWorkPlanFiltering(
            "expectedCompletionDate.in=" + DEFAULT_EXPECTED_COMPLETION_DATE + "," + UPDATED_EXPECTED_COMPLETION_DATE,
            "expectedCompletionDate.in=" + UPDATED_EXPECTED_COMPLETION_DATE
        );
    }

    @Test
    void getAllWorkPlansByExpectedCompletionDateIsNullOrNotNull() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where expectedCompletionDate is not null
        defaultWorkPlanFiltering("expectedCompletionDate.specified=true", "expectedCompletionDate.specified=false");
    }

    @Test
    void getAllWorkPlansByActualCompletionDateIsEqualToSomething() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where actualCompletionDate equals to
        defaultWorkPlanFiltering(
            "actualCompletionDate.equals=" + DEFAULT_ACTUAL_COMPLETION_DATE,
            "actualCompletionDate.equals=" + UPDATED_ACTUAL_COMPLETION_DATE
        );
    }

    @Test
    void getAllWorkPlansByActualCompletionDateIsInShouldWork() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where actualCompletionDate in
        defaultWorkPlanFiltering(
            "actualCompletionDate.in=" + DEFAULT_ACTUAL_COMPLETION_DATE + "," + UPDATED_ACTUAL_COMPLETION_DATE,
            "actualCompletionDate.in=" + UPDATED_ACTUAL_COMPLETION_DATE
        );
    }

    @Test
    void getAllWorkPlansByActualCompletionDateIsNullOrNotNull() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where actualCompletionDate is not null
        defaultWorkPlanFiltering("actualCompletionDate.specified=true", "actualCompletionDate.specified=false");
    }

    @Test
    void getAllWorkPlansByCompletionPercentageIsEqualToSomething() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where completionPercentage equals to
        defaultWorkPlanFiltering(
            "completionPercentage.equals=" + DEFAULT_COMPLETION_PERCENTAGE,
            "completionPercentage.equals=" + UPDATED_COMPLETION_PERCENTAGE
        );
    }

    @Test
    void getAllWorkPlansByCompletionPercentageIsInShouldWork() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where completionPercentage in
        defaultWorkPlanFiltering(
            "completionPercentage.in=" + DEFAULT_COMPLETION_PERCENTAGE + "," + UPDATED_COMPLETION_PERCENTAGE,
            "completionPercentage.in=" + UPDATED_COMPLETION_PERCENTAGE
        );
    }

    @Test
    void getAllWorkPlansByCompletionPercentageIsNullOrNotNull() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where completionPercentage is not null
        defaultWorkPlanFiltering("completionPercentage.specified=true", "completionPercentage.specified=false");
    }

    @Test
    void getAllWorkPlansByCompletionPercentageIsGreaterThanOrEqualToSomething() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where completionPercentage is greater than or equal to
        defaultWorkPlanFiltering(
            "completionPercentage.greaterThanOrEqual=" + DEFAULT_COMPLETION_PERCENTAGE,
            "completionPercentage.greaterThanOrEqual=" + (DEFAULT_COMPLETION_PERCENTAGE + 1)
        );
    }

    @Test
    void getAllWorkPlansByCompletionPercentageIsLessThanOrEqualToSomething() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where completionPercentage is less than or equal to
        defaultWorkPlanFiltering(
            "completionPercentage.lessThanOrEqual=" + DEFAULT_COMPLETION_PERCENTAGE,
            "completionPercentage.lessThanOrEqual=" + SMALLER_COMPLETION_PERCENTAGE
        );
    }

    @Test
    void getAllWorkPlansByCompletionPercentageIsLessThanSomething() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where completionPercentage is less than
        defaultWorkPlanFiltering(
            "completionPercentage.lessThan=" + (DEFAULT_COMPLETION_PERCENTAGE + 1),
            "completionPercentage.lessThan=" + DEFAULT_COMPLETION_PERCENTAGE
        );
    }

    @Test
    void getAllWorkPlansByCompletionPercentageIsGreaterThanSomething() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where completionPercentage is greater than
        defaultWorkPlanFiltering(
            "completionPercentage.greaterThan=" + SMALLER_COMPLETION_PERCENTAGE,
            "completionPercentage.greaterThan=" + DEFAULT_COMPLETION_PERCENTAGE
        );
    }

    @Test
    void getAllWorkPlansByStatusIsEqualToSomething() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where status equals to
        defaultWorkPlanFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    void getAllWorkPlansByStatusIsInShouldWork() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where status in
        defaultWorkPlanFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    void getAllWorkPlansByStatusIsNullOrNotNull() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where status is not null
        defaultWorkPlanFiltering("status.specified=true", "status.specified=false");
    }

    @Test
    void getAllWorkPlansByDeletedIsEqualToSomething() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where deleted equals to
        defaultWorkPlanFiltering("deleted.equals=" + DEFAULT_DELETED, "deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    void getAllWorkPlansByDeletedIsInShouldWork() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where deleted in
        defaultWorkPlanFiltering("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED, "deleted.in=" + UPDATED_DELETED);
    }

    @Test
    void getAllWorkPlansByDeletedIsNullOrNotNull() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where deleted is not null
        defaultWorkPlanFiltering("deleted.specified=true", "deleted.specified=false");
    }

    @Test
    void getAllWorkPlansByDeletedDateIsEqualToSomething() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where deletedDate equals to
        defaultWorkPlanFiltering("deletedDate.equals=" + DEFAULT_DELETED_DATE, "deletedDate.equals=" + UPDATED_DELETED_DATE);
    }

    @Test
    void getAllWorkPlansByDeletedDateIsInShouldWork() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where deletedDate in
        defaultWorkPlanFiltering(
            "deletedDate.in=" + DEFAULT_DELETED_DATE + "," + UPDATED_DELETED_DATE,
            "deletedDate.in=" + UPDATED_DELETED_DATE
        );
    }

    @Test
    void getAllWorkPlansByDeletedDateIsNullOrNotNull() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        // Get all the workPlanList where deletedDate is not null
        defaultWorkPlanFiltering("deletedDate.specified=true", "deletedDate.specified=false");
    }

    @Test
    void getAllWorkPlansByTicketIsEqualToSomething() {
        Ticket ticket = TicketResourceIT.createEntity(em);
        ticketRepository.save(ticket).block();
        Long ticketId = ticket.getId();
        workPlan.setTicketId(ticketId);
        insertedWorkPlan = workPlanRepository.save(workPlan).block();
        // Get all the workPlanList where ticket equals to ticketId
        defaultWorkPlanShouldBeFound("ticketId.equals=" + ticketId);

        // Get all the workPlanList where ticket equals to (ticketId + 1)
        defaultWorkPlanShouldNotBeFound("ticketId.equals=" + (ticketId + 1));
    }

    @Test
    void getAllWorkPlansByDepartmentIsEqualToSomething() {
        Department department = DepartmentResourceIT.createEntity();
        departmentRepository.save(department).block();
        Long departmentId = department.getId();
        workPlan.setDepartmentId(departmentId);
        insertedWorkPlan = workPlanRepository.save(workPlan).block();
        // Get all the workPlanList where department equals to departmentId
        defaultWorkPlanShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the workPlanList where department equals to (departmentId + 1)
        defaultWorkPlanShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }

    private void defaultWorkPlanFiltering(String shouldBeFound, String shouldNotBeFound) {
        defaultWorkPlanShouldBeFound(shouldBeFound);
        defaultWorkPlanShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWorkPlanShouldBeFound(String filter) {
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
            .value(hasItem(workPlan.getId().intValue()))
            .jsonPath("$.[*].estimatedCost")
            .value(hasItem(sameNumber(DEFAULT_ESTIMATED_COST)))
            .jsonPath("$.[*].startedDate")
            .value(hasItem(DEFAULT_STARTED_DATE.toString()))
            .jsonPath("$.[*].expectedCompletionDate")
            .value(hasItem(DEFAULT_EXPECTED_COMPLETION_DATE.toString()))
            .jsonPath("$.[*].actualCompletionDate")
            .value(hasItem(DEFAULT_ACTUAL_COMPLETION_DATE.toString()))
            .jsonPath("$.[*].completionPercentage")
            .value(hasItem(DEFAULT_COMPLETION_PERCENTAGE))
            .jsonPath("$.[*].status")
            .value(hasItem(DEFAULT_STATUS.toString()))
            .jsonPath("$.[*].remarks")
            .value(hasItem(DEFAULT_REMARKS))
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
    private void defaultWorkPlanShouldNotBeFound(String filter) {
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
    void getNonExistingWorkPlan() {
        // Get the workPlan
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingWorkPlan() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the workPlan
        WorkPlan updatedWorkPlan = workPlanRepository.findById(workPlan.getId()).block();
        updatedWorkPlan
            .estimatedCost(UPDATED_ESTIMATED_COST)
            .startedDate(UPDATED_STARTED_DATE)
            .expectedCompletionDate(UPDATED_EXPECTED_COMPLETION_DATE)
            .actualCompletionDate(UPDATED_ACTUAL_COMPLETION_DATE)
            .completionPercentage(UPDATED_COMPLETION_PERCENTAGE)
            .status(UPDATED_STATUS)
            .remarks(UPDATED_REMARKS)
            .deleted(UPDATED_DELETED)
            .deletedDate(UPDATED_DELETED_DATE);
        WorkPlanDTO workPlanDTO = workPlanMapper.toDto(updatedWorkPlan);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, workPlanDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(workPlanDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the WorkPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWorkPlanToMatchAllProperties(updatedWorkPlan);
    }

    @Test
    void putNonExistingWorkPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workPlan.setId(longCount.incrementAndGet());

        // Create the WorkPlan
        WorkPlanDTO workPlanDTO = workPlanMapper.toDto(workPlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, workPlanDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(workPlanDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the WorkPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchWorkPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workPlan.setId(longCount.incrementAndGet());

        // Create the WorkPlan
        WorkPlanDTO workPlanDTO = workPlanMapper.toDto(workPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(workPlanDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the WorkPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamWorkPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workPlan.setId(longCount.incrementAndGet());

        // Create the WorkPlan
        WorkPlanDTO workPlanDTO = workPlanMapper.toDto(workPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(workPlanDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the WorkPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateWorkPlanWithPatch() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the workPlan using partial update
        WorkPlan partialUpdatedWorkPlan = new WorkPlan();
        partialUpdatedWorkPlan.setId(workPlan.getId());

        partialUpdatedWorkPlan
            .estimatedCost(UPDATED_ESTIMATED_COST)
            .actualCompletionDate(UPDATED_ACTUAL_COMPLETION_DATE)
            .remarks(UPDATED_REMARKS)
            .deletedDate(UPDATED_DELETED_DATE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedWorkPlan.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedWorkPlan))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the WorkPlan in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWorkPlanUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedWorkPlan, workPlan), getPersistedWorkPlan(workPlan));
    }

    @Test
    void fullUpdateWorkPlanWithPatch() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the workPlan using partial update
        WorkPlan partialUpdatedWorkPlan = new WorkPlan();
        partialUpdatedWorkPlan.setId(workPlan.getId());

        partialUpdatedWorkPlan
            .estimatedCost(UPDATED_ESTIMATED_COST)
            .startedDate(UPDATED_STARTED_DATE)
            .expectedCompletionDate(UPDATED_EXPECTED_COMPLETION_DATE)
            .actualCompletionDate(UPDATED_ACTUAL_COMPLETION_DATE)
            .completionPercentage(UPDATED_COMPLETION_PERCENTAGE)
            .status(UPDATED_STATUS)
            .remarks(UPDATED_REMARKS)
            .deleted(UPDATED_DELETED)
            .deletedDate(UPDATED_DELETED_DATE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedWorkPlan.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedWorkPlan))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the WorkPlan in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWorkPlanUpdatableFieldsEquals(partialUpdatedWorkPlan, getPersistedWorkPlan(partialUpdatedWorkPlan));
    }

    @Test
    void patchNonExistingWorkPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workPlan.setId(longCount.incrementAndGet());

        // Create the WorkPlan
        WorkPlanDTO workPlanDTO = workPlanMapper.toDto(workPlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, workPlanDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(workPlanDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the WorkPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchWorkPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workPlan.setId(longCount.incrementAndGet());

        // Create the WorkPlan
        WorkPlanDTO workPlanDTO = workPlanMapper.toDto(workPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(workPlanDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the WorkPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamWorkPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workPlan.setId(longCount.incrementAndGet());

        // Create the WorkPlan
        WorkPlanDTO workPlanDTO = workPlanMapper.toDto(workPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(workPlanDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the WorkPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteWorkPlan() {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.save(workPlan).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the workPlan
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, workPlan.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return workPlanRepository.count().block();
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

    protected WorkPlan getPersistedWorkPlan(WorkPlan workPlan) {
        return workPlanRepository.findById(workPlan.getId()).block();
    }

    protected void assertPersistedWorkPlanToMatchAllProperties(WorkPlan expectedWorkPlan) {
        // Test fails because reactive api returns an empty object instead of null
        // assertWorkPlanAllPropertiesEquals(expectedWorkPlan, getPersistedWorkPlan(expectedWorkPlan));
        assertWorkPlanUpdatableFieldsEquals(expectedWorkPlan, getPersistedWorkPlan(expectedWorkPlan));
    }

    protected void assertPersistedWorkPlanToMatchUpdatableProperties(WorkPlan expectedWorkPlan) {
        // Test fails because reactive api returns an empty object instead of null
        // assertWorkPlanAllUpdatablePropertiesEquals(expectedWorkPlan, getPersistedWorkPlan(expectedWorkPlan));
        assertWorkPlanUpdatableFieldsEquals(expectedWorkPlan, getPersistedWorkPlan(expectedWorkPlan));
    }
}
