package com.diviso.spot_fix.web.rest;

import static com.diviso.spot_fix.domain.WorkPlanAsserts.*;
import static com.diviso.spot_fix.web.rest.TestUtil.createUpdateProxyForBean;
import static com.diviso.spot_fix.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.diviso.spot_fix.IntegrationTest;
import com.diviso.spot_fix.domain.Department;
import com.diviso.spot_fix.domain.Ticket;
import com.diviso.spot_fix.domain.WorkPlan;
import com.diviso.spot_fix.domain.enumeration.WorkStatus;
import com.diviso.spot_fix.repository.WorkPlanRepository;
import com.diviso.spot_fix.service.dto.WorkPlanDTO;
import com.diviso.spot_fix.service.mapper.WorkPlanMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WorkPlanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
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
    private MockMvc restWorkPlanMockMvc;

    private WorkPlan workPlan;

    private WorkPlan insertedWorkPlan;

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
        if (TestUtil.findAll(em, Ticket.class).isEmpty()) {
            ticket = TicketResourceIT.createEntity(em);
            em.persist(ticket);
            em.flush();
        } else {
            ticket = TestUtil.findAll(em, Ticket.class).get(0);
        }
        workPlan.setTicket(ticket);
        // Add required entity
        Department department;
        if (TestUtil.findAll(em, Department.class).isEmpty()) {
            department = DepartmentResourceIT.createEntity();
            em.persist(department);
            em.flush();
        } else {
            department = TestUtil.findAll(em, Department.class).get(0);
        }
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
        if (TestUtil.findAll(em, Ticket.class).isEmpty()) {
            ticket = TicketResourceIT.createUpdatedEntity(em);
            em.persist(ticket);
            em.flush();
        } else {
            ticket = TestUtil.findAll(em, Ticket.class).get(0);
        }
        updatedWorkPlan.setTicket(ticket);
        // Add required entity
        Department department;
        if (TestUtil.findAll(em, Department.class).isEmpty()) {
            department = DepartmentResourceIT.createUpdatedEntity();
            em.persist(department);
            em.flush();
        } else {
            department = TestUtil.findAll(em, Department.class).get(0);
        }
        updatedWorkPlan.setDepartment(department);
        return updatedWorkPlan;
    }

    @BeforeEach
    void initTest() {
        workPlan = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedWorkPlan != null) {
            workPlanRepository.delete(insertedWorkPlan);
            insertedWorkPlan = null;
        }
    }

    @Test
    @Transactional
    void createWorkPlan() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the WorkPlan
        WorkPlanDTO workPlanDTO = workPlanMapper.toDto(workPlan);
        var returnedWorkPlanDTO = om.readValue(
            restWorkPlanMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(workPlanDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            WorkPlanDTO.class
        );

        // Validate the WorkPlan in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedWorkPlan = workPlanMapper.toEntity(returnedWorkPlanDTO);
        assertWorkPlanUpdatableFieldsEquals(returnedWorkPlan, getPersistedWorkPlan(returnedWorkPlan));

        insertedWorkPlan = returnedWorkPlan;
    }

    @Test
    @Transactional
    void createWorkPlanWithExistingId() throws Exception {
        // Create the WorkPlan with an existing ID
        workPlan.setId(1L);
        WorkPlanDTO workPlanDTO = workPlanMapper.toDto(workPlan);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkPlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(workPlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorkPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        workPlan.setStatus(null);

        // Create the WorkPlan, which fails.
        WorkPlanDTO workPlanDTO = workPlanMapper.toDto(workPlan);

        restWorkPlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(workPlanDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeletedIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        workPlan.setDeleted(null);

        // Create the WorkPlan, which fails.
        WorkPlanDTO workPlanDTO = workPlanMapper.toDto(workPlan);

        restWorkPlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(workPlanDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWorkPlans() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList
        restWorkPlanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].estimatedCost").value(hasItem(sameNumber(DEFAULT_ESTIMATED_COST))))
            .andExpect(jsonPath("$.[*].startedDate").value(hasItem(DEFAULT_STARTED_DATE.toString())))
            .andExpect(jsonPath("$.[*].expectedCompletionDate").value(hasItem(DEFAULT_EXPECTED_COMPLETION_DATE.toString())))
            .andExpect(jsonPath("$.[*].actualCompletionDate").value(hasItem(DEFAULT_ACTUAL_COMPLETION_DATE.toString())))
            .andExpect(jsonPath("$.[*].completionPercentage").value(hasItem(DEFAULT_COMPLETION_PERCENTAGE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED)))
            .andExpect(jsonPath("$.[*].deletedDate").value(hasItem(DEFAULT_DELETED_DATE.toString())));
    }

    @Test
    @Transactional
    void getWorkPlan() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get the workPlan
        restWorkPlanMockMvc
            .perform(get(ENTITY_API_URL_ID, workPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workPlan.getId().intValue()))
            .andExpect(jsonPath("$.estimatedCost").value(sameNumber(DEFAULT_ESTIMATED_COST)))
            .andExpect(jsonPath("$.startedDate").value(DEFAULT_STARTED_DATE.toString()))
            .andExpect(jsonPath("$.expectedCompletionDate").value(DEFAULT_EXPECTED_COMPLETION_DATE.toString()))
            .andExpect(jsonPath("$.actualCompletionDate").value(DEFAULT_ACTUAL_COMPLETION_DATE.toString()))
            .andExpect(jsonPath("$.completionPercentage").value(DEFAULT_COMPLETION_PERCENTAGE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED))
            .andExpect(jsonPath("$.deletedDate").value(DEFAULT_DELETED_DATE.toString()));
    }

    @Test
    @Transactional
    void getWorkPlansByIdFiltering() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        Long id = workPlan.getId();

        defaultWorkPlanFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultWorkPlanFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultWorkPlanFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllWorkPlansByEstimatedCostIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where estimatedCost equals to
        defaultWorkPlanFiltering("estimatedCost.equals=" + DEFAULT_ESTIMATED_COST, "estimatedCost.equals=" + UPDATED_ESTIMATED_COST);
    }

    @Test
    @Transactional
    void getAllWorkPlansByEstimatedCostIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where estimatedCost in
        defaultWorkPlanFiltering(
            "estimatedCost.in=" + DEFAULT_ESTIMATED_COST + "," + UPDATED_ESTIMATED_COST,
            "estimatedCost.in=" + UPDATED_ESTIMATED_COST
        );
    }

    @Test
    @Transactional
    void getAllWorkPlansByEstimatedCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where estimatedCost is not null
        defaultWorkPlanFiltering("estimatedCost.specified=true", "estimatedCost.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkPlansByEstimatedCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where estimatedCost is greater than or equal to
        defaultWorkPlanFiltering(
            "estimatedCost.greaterThanOrEqual=" + DEFAULT_ESTIMATED_COST,
            "estimatedCost.greaterThanOrEqual=" + UPDATED_ESTIMATED_COST
        );
    }

    @Test
    @Transactional
    void getAllWorkPlansByEstimatedCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where estimatedCost is less than or equal to
        defaultWorkPlanFiltering(
            "estimatedCost.lessThanOrEqual=" + DEFAULT_ESTIMATED_COST,
            "estimatedCost.lessThanOrEqual=" + SMALLER_ESTIMATED_COST
        );
    }

    @Test
    @Transactional
    void getAllWorkPlansByEstimatedCostIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where estimatedCost is less than
        defaultWorkPlanFiltering("estimatedCost.lessThan=" + UPDATED_ESTIMATED_COST, "estimatedCost.lessThan=" + DEFAULT_ESTIMATED_COST);
    }

    @Test
    @Transactional
    void getAllWorkPlansByEstimatedCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where estimatedCost is greater than
        defaultWorkPlanFiltering(
            "estimatedCost.greaterThan=" + SMALLER_ESTIMATED_COST,
            "estimatedCost.greaterThan=" + DEFAULT_ESTIMATED_COST
        );
    }

    @Test
    @Transactional
    void getAllWorkPlansByStartedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where startedDate equals to
        defaultWorkPlanFiltering("startedDate.equals=" + DEFAULT_STARTED_DATE, "startedDate.equals=" + UPDATED_STARTED_DATE);
    }

    @Test
    @Transactional
    void getAllWorkPlansByStartedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where startedDate in
        defaultWorkPlanFiltering(
            "startedDate.in=" + DEFAULT_STARTED_DATE + "," + UPDATED_STARTED_DATE,
            "startedDate.in=" + UPDATED_STARTED_DATE
        );
    }

    @Test
    @Transactional
    void getAllWorkPlansByStartedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where startedDate is not null
        defaultWorkPlanFiltering("startedDate.specified=true", "startedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkPlansByExpectedCompletionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where expectedCompletionDate equals to
        defaultWorkPlanFiltering(
            "expectedCompletionDate.equals=" + DEFAULT_EXPECTED_COMPLETION_DATE,
            "expectedCompletionDate.equals=" + UPDATED_EXPECTED_COMPLETION_DATE
        );
    }

    @Test
    @Transactional
    void getAllWorkPlansByExpectedCompletionDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where expectedCompletionDate in
        defaultWorkPlanFiltering(
            "expectedCompletionDate.in=" + DEFAULT_EXPECTED_COMPLETION_DATE + "," + UPDATED_EXPECTED_COMPLETION_DATE,
            "expectedCompletionDate.in=" + UPDATED_EXPECTED_COMPLETION_DATE
        );
    }

    @Test
    @Transactional
    void getAllWorkPlansByExpectedCompletionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where expectedCompletionDate is not null
        defaultWorkPlanFiltering("expectedCompletionDate.specified=true", "expectedCompletionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkPlansByActualCompletionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where actualCompletionDate equals to
        defaultWorkPlanFiltering(
            "actualCompletionDate.equals=" + DEFAULT_ACTUAL_COMPLETION_DATE,
            "actualCompletionDate.equals=" + UPDATED_ACTUAL_COMPLETION_DATE
        );
    }

    @Test
    @Transactional
    void getAllWorkPlansByActualCompletionDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where actualCompletionDate in
        defaultWorkPlanFiltering(
            "actualCompletionDate.in=" + DEFAULT_ACTUAL_COMPLETION_DATE + "," + UPDATED_ACTUAL_COMPLETION_DATE,
            "actualCompletionDate.in=" + UPDATED_ACTUAL_COMPLETION_DATE
        );
    }

    @Test
    @Transactional
    void getAllWorkPlansByActualCompletionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where actualCompletionDate is not null
        defaultWorkPlanFiltering("actualCompletionDate.specified=true", "actualCompletionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkPlansByCompletionPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where completionPercentage equals to
        defaultWorkPlanFiltering(
            "completionPercentage.equals=" + DEFAULT_COMPLETION_PERCENTAGE,
            "completionPercentage.equals=" + UPDATED_COMPLETION_PERCENTAGE
        );
    }

    @Test
    @Transactional
    void getAllWorkPlansByCompletionPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where completionPercentage in
        defaultWorkPlanFiltering(
            "completionPercentage.in=" + DEFAULT_COMPLETION_PERCENTAGE + "," + UPDATED_COMPLETION_PERCENTAGE,
            "completionPercentage.in=" + UPDATED_COMPLETION_PERCENTAGE
        );
    }

    @Test
    @Transactional
    void getAllWorkPlansByCompletionPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where completionPercentage is not null
        defaultWorkPlanFiltering("completionPercentage.specified=true", "completionPercentage.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkPlansByCompletionPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where completionPercentage is greater than or equal to
        defaultWorkPlanFiltering(
            "completionPercentage.greaterThanOrEqual=" + DEFAULT_COMPLETION_PERCENTAGE,
            "completionPercentage.greaterThanOrEqual=" + (DEFAULT_COMPLETION_PERCENTAGE + 1)
        );
    }

    @Test
    @Transactional
    void getAllWorkPlansByCompletionPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where completionPercentage is less than or equal to
        defaultWorkPlanFiltering(
            "completionPercentage.lessThanOrEqual=" + DEFAULT_COMPLETION_PERCENTAGE,
            "completionPercentage.lessThanOrEqual=" + SMALLER_COMPLETION_PERCENTAGE
        );
    }

    @Test
    @Transactional
    void getAllWorkPlansByCompletionPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where completionPercentage is less than
        defaultWorkPlanFiltering(
            "completionPercentage.lessThan=" + (DEFAULT_COMPLETION_PERCENTAGE + 1),
            "completionPercentage.lessThan=" + DEFAULT_COMPLETION_PERCENTAGE
        );
    }

    @Test
    @Transactional
    void getAllWorkPlansByCompletionPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where completionPercentage is greater than
        defaultWorkPlanFiltering(
            "completionPercentage.greaterThan=" + SMALLER_COMPLETION_PERCENTAGE,
            "completionPercentage.greaterThan=" + DEFAULT_COMPLETION_PERCENTAGE
        );
    }

    @Test
    @Transactional
    void getAllWorkPlansByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where status equals to
        defaultWorkPlanFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllWorkPlansByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where status in
        defaultWorkPlanFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllWorkPlansByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where status is not null
        defaultWorkPlanFiltering("status.specified=true", "status.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkPlansByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where deleted equals to
        defaultWorkPlanFiltering("deleted.equals=" + DEFAULT_DELETED, "deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllWorkPlansByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where deleted in
        defaultWorkPlanFiltering("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED, "deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllWorkPlansByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where deleted is not null
        defaultWorkPlanFiltering("deleted.specified=true", "deleted.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkPlansByDeletedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where deletedDate equals to
        defaultWorkPlanFiltering("deletedDate.equals=" + DEFAULT_DELETED_DATE, "deletedDate.equals=" + UPDATED_DELETED_DATE);
    }

    @Test
    @Transactional
    void getAllWorkPlansByDeletedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where deletedDate in
        defaultWorkPlanFiltering(
            "deletedDate.in=" + DEFAULT_DELETED_DATE + "," + UPDATED_DELETED_DATE,
            "deletedDate.in=" + UPDATED_DELETED_DATE
        );
    }

    @Test
    @Transactional
    void getAllWorkPlansByDeletedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList where deletedDate is not null
        defaultWorkPlanFiltering("deletedDate.specified=true", "deletedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkPlansByTicketIsEqualToSomething() throws Exception {
        Ticket ticket;
        if (TestUtil.findAll(em, Ticket.class).isEmpty()) {
            workPlanRepository.saveAndFlush(workPlan);
            ticket = TicketResourceIT.createEntity(em);
        } else {
            ticket = TestUtil.findAll(em, Ticket.class).get(0);
        }
        em.persist(ticket);
        em.flush();
        workPlan.setTicket(ticket);
        workPlanRepository.saveAndFlush(workPlan);
        Long ticketId = ticket.getId();
        // Get all the workPlanList where ticket equals to ticketId
        defaultWorkPlanShouldBeFound("ticketId.equals=" + ticketId);

        // Get all the workPlanList where ticket equals to (ticketId + 1)
        defaultWorkPlanShouldNotBeFound("ticketId.equals=" + (ticketId + 1));
    }

    @Test
    @Transactional
    void getAllWorkPlansByDepartmentIsEqualToSomething() throws Exception {
        Department department;
        if (TestUtil.findAll(em, Department.class).isEmpty()) {
            workPlanRepository.saveAndFlush(workPlan);
            department = DepartmentResourceIT.createEntity();
        } else {
            department = TestUtil.findAll(em, Department.class).get(0);
        }
        em.persist(department);
        em.flush();
        workPlan.setDepartment(department);
        workPlanRepository.saveAndFlush(workPlan);
        Long departmentId = department.getId();
        // Get all the workPlanList where department equals to departmentId
        defaultWorkPlanShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the workPlanList where department equals to (departmentId + 1)
        defaultWorkPlanShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }

    private void defaultWorkPlanFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultWorkPlanShouldBeFound(shouldBeFound);
        defaultWorkPlanShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWorkPlanShouldBeFound(String filter) throws Exception {
        restWorkPlanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].estimatedCost").value(hasItem(sameNumber(DEFAULT_ESTIMATED_COST))))
            .andExpect(jsonPath("$.[*].startedDate").value(hasItem(DEFAULT_STARTED_DATE.toString())))
            .andExpect(jsonPath("$.[*].expectedCompletionDate").value(hasItem(DEFAULT_EXPECTED_COMPLETION_DATE.toString())))
            .andExpect(jsonPath("$.[*].actualCompletionDate").value(hasItem(DEFAULT_ACTUAL_COMPLETION_DATE.toString())))
            .andExpect(jsonPath("$.[*].completionPercentage").value(hasItem(DEFAULT_COMPLETION_PERCENTAGE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED)))
            .andExpect(jsonPath("$.[*].deletedDate").value(hasItem(DEFAULT_DELETED_DATE.toString())));

        // Check, that the count call also returns 1
        restWorkPlanMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWorkPlanShouldNotBeFound(String filter) throws Exception {
        restWorkPlanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWorkPlanMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingWorkPlan() throws Exception {
        // Get the workPlan
        restWorkPlanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWorkPlan() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the workPlan
        WorkPlan updatedWorkPlan = workPlanRepository.findById(workPlan.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWorkPlan are not directly saved in db
        em.detach(updatedWorkPlan);
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

        restWorkPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workPlanDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(workPlanDTO))
            )
            .andExpect(status().isOk());

        // Validate the WorkPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWorkPlanToMatchAllProperties(updatedWorkPlan);
    }

    @Test
    @Transactional
    void putNonExistingWorkPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workPlan.setId(longCount.incrementAndGet());

        // Create the WorkPlan
        WorkPlanDTO workPlanDTO = workPlanMapper.toDto(workPlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workPlanDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(workPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workPlan.setId(longCount.incrementAndGet());

        // Create the WorkPlan
        WorkPlanDTO workPlanDTO = workPlanMapper.toDto(workPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(workPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workPlan.setId(longCount.incrementAndGet());

        // Create the WorkPlan
        WorkPlanDTO workPlanDTO = workPlanMapper.toDto(workPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkPlanMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(workPlanDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkPlanWithPatch() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the workPlan using partial update
        WorkPlan partialUpdatedWorkPlan = new WorkPlan();
        partialUpdatedWorkPlan.setId(workPlan.getId());

        partialUpdatedWorkPlan
            .estimatedCost(UPDATED_ESTIMATED_COST)
            .actualCompletionDate(UPDATED_ACTUAL_COMPLETION_DATE)
            .remarks(UPDATED_REMARKS)
            .deletedDate(UPDATED_DELETED_DATE);

        restWorkPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkPlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWorkPlan))
            )
            .andExpect(status().isOk());

        // Validate the WorkPlan in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWorkPlanUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedWorkPlan, workPlan), getPersistedWorkPlan(workPlan));
    }

    @Test
    @Transactional
    void fullUpdateWorkPlanWithPatch() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

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

        restWorkPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkPlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWorkPlan))
            )
            .andExpect(status().isOk());

        // Validate the WorkPlan in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWorkPlanUpdatableFieldsEquals(partialUpdatedWorkPlan, getPersistedWorkPlan(partialUpdatedWorkPlan));
    }

    @Test
    @Transactional
    void patchNonExistingWorkPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workPlan.setId(longCount.incrementAndGet());

        // Create the WorkPlan
        WorkPlanDTO workPlanDTO = workPlanMapper.toDto(workPlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workPlanDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(workPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workPlan.setId(longCount.incrementAndGet());

        // Create the WorkPlan
        WorkPlanDTO workPlanDTO = workPlanMapper.toDto(workPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(workPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workPlan.setId(longCount.incrementAndGet());

        // Create the WorkPlan
        WorkPlanDTO workPlanDTO = workPlanMapper.toDto(workPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkPlanMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(workPlanDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkPlan() throws Exception {
        // Initialize the database
        insertedWorkPlan = workPlanRepository.saveAndFlush(workPlan);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the workPlan
        restWorkPlanMockMvc
            .perform(delete(ENTITY_API_URL_ID, workPlan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return workPlanRepository.count();
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
        return workPlanRepository.findById(workPlan.getId()).orElseThrow();
    }

    protected void assertPersistedWorkPlanToMatchAllProperties(WorkPlan expectedWorkPlan) {
        assertWorkPlanAllPropertiesEquals(expectedWorkPlan, getPersistedWorkPlan(expectedWorkPlan));
    }

    protected void assertPersistedWorkPlanToMatchUpdatableProperties(WorkPlan expectedWorkPlan) {
        assertWorkPlanAllUpdatablePropertiesEquals(expectedWorkPlan, getPersistedWorkPlan(expectedWorkPlan));
    }
}
