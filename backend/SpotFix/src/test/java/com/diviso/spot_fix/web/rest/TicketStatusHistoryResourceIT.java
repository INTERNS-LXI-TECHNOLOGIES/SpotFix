package com.diviso.spot_fix.web.rest;

import static com.diviso.spot_fix.domain.TicketStatusHistoryAsserts.*;
import static com.diviso.spot_fix.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.diviso.spot_fix.IntegrationTest;
import com.diviso.spot_fix.domain.Ticket;
import com.diviso.spot_fix.domain.TicketStatusHistory;
import com.diviso.spot_fix.domain.User;
import com.diviso.spot_fix.domain.enumeration.TicketStatus;
import com.diviso.spot_fix.domain.enumeration.TicketStatus;
import com.diviso.spot_fix.repository.TicketStatusHistoryRepository;
import com.diviso.spot_fix.repository.UserRepository;
import com.diviso.spot_fix.service.TicketStatusHistoryService;
import com.diviso.spot_fix.service.dto.TicketStatusHistoryDTO;
import com.diviso.spot_fix.service.mapper.TicketStatusHistoryMapper;
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
 * Integration tests for the {@link TicketStatusHistoryResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TicketStatusHistoryResourceIT {

    private static final TicketStatus DEFAULT_OLD_STATUS = TicketStatus.OPEN;
    private static final TicketStatus UPDATED_OLD_STATUS = TicketStatus.UNDER_REVIEW;

    private static final TicketStatus DEFAULT_NEW_STATUS = TicketStatus.OPEN;
    private static final TicketStatus UPDATED_NEW_STATUS = TicketStatus.UNDER_REVIEW;

    private static final Instant DEFAULT_CHANGED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CHANGED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/ticket-status-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TicketStatusHistoryRepository ticketStatusHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private TicketStatusHistoryRepository ticketStatusHistoryRepositoryMock;

    @Autowired
    private TicketStatusHistoryMapper ticketStatusHistoryMapper;

    @Mock
    private TicketStatusHistoryService ticketStatusHistoryServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTicketStatusHistoryMockMvc;

    private TicketStatusHistory ticketStatusHistory;

    private TicketStatusHistory insertedTicketStatusHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketStatusHistory createEntity(EntityManager em) {
        TicketStatusHistory ticketStatusHistory = new TicketStatusHistory()
            .oldStatus(DEFAULT_OLD_STATUS)
            .newStatus(DEFAULT_NEW_STATUS)
            .changedDate(DEFAULT_CHANGED_DATE);
        // Add required entity
        Ticket ticket;
        if (TestUtil.findAll(em, Ticket.class).isEmpty()) {
            ticket = TicketResourceIT.createEntity(em);
            em.persist(ticket);
            em.flush();
        } else {
            ticket = TestUtil.findAll(em, Ticket.class).get(0);
        }
        ticketStatusHistory.setTicket(ticket);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        ticketStatusHistory.setChangedBy(user);
        return ticketStatusHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketStatusHistory createUpdatedEntity(EntityManager em) {
        TicketStatusHistory updatedTicketStatusHistory = new TicketStatusHistory()
            .oldStatus(UPDATED_OLD_STATUS)
            .newStatus(UPDATED_NEW_STATUS)
            .changedDate(UPDATED_CHANGED_DATE);
        // Add required entity
        Ticket ticket;
        if (TestUtil.findAll(em, Ticket.class).isEmpty()) {
            ticket = TicketResourceIT.createUpdatedEntity(em);
            em.persist(ticket);
            em.flush();
        } else {
            ticket = TestUtil.findAll(em, Ticket.class).get(0);
        }
        updatedTicketStatusHistory.setTicket(ticket);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        updatedTicketStatusHistory.setChangedBy(user);
        return updatedTicketStatusHistory;
    }

    @BeforeEach
    void initTest() {
        ticketStatusHistory = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedTicketStatusHistory != null) {
            ticketStatusHistoryRepository.delete(insertedTicketStatusHistory);
            insertedTicketStatusHistory = null;
        }
    }

    @Test
    @Transactional
    void createTicketStatusHistory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TicketStatusHistory
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(ticketStatusHistory);
        var returnedTicketStatusHistoryDTO = om.readValue(
            restTicketStatusHistoryMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketStatusHistoryDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TicketStatusHistoryDTO.class
        );

        // Validate the TicketStatusHistory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTicketStatusHistory = ticketStatusHistoryMapper.toEntity(returnedTicketStatusHistoryDTO);
        assertTicketStatusHistoryUpdatableFieldsEquals(
            returnedTicketStatusHistory,
            getPersistedTicketStatusHistory(returnedTicketStatusHistory)
        );

        insertedTicketStatusHistory = returnedTicketStatusHistory;
    }

    @Test
    @Transactional
    void createTicketStatusHistoryWithExistingId() throws Exception {
        // Create the TicketStatusHistory with an existing ID
        ticketStatusHistory.setId(1L);
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(ticketStatusHistory);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTicketStatusHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketStatusHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TicketStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOldStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticketStatusHistory.setOldStatus(null);

        // Create the TicketStatusHistory, which fails.
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(ticketStatusHistory);

        restTicketStatusHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketStatusHistoryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNewStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticketStatusHistory.setNewStatus(null);

        // Create the TicketStatusHistory, which fails.
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(ticketStatusHistory);

        restTicketStatusHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketStatusHistoryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkChangedDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticketStatusHistory.setChangedDate(null);

        // Create the TicketStatusHistory, which fails.
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(ticketStatusHistory);

        restTicketStatusHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketStatusHistoryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTicketStatusHistories() throws Exception {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.saveAndFlush(ticketStatusHistory);

        // Get all the ticketStatusHistoryList
        restTicketStatusHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticketStatusHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].oldStatus").value(hasItem(DEFAULT_OLD_STATUS.toString())))
            .andExpect(jsonPath("$.[*].newStatus").value(hasItem(DEFAULT_NEW_STATUS.toString())))
            .andExpect(jsonPath("$.[*].changedDate").value(hasItem(DEFAULT_CHANGED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTicketStatusHistoriesWithEagerRelationshipsIsEnabled() throws Exception {
        when(ticketStatusHistoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTicketStatusHistoryMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(ticketStatusHistoryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTicketStatusHistoriesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(ticketStatusHistoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTicketStatusHistoryMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(ticketStatusHistoryRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTicketStatusHistory() throws Exception {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.saveAndFlush(ticketStatusHistory);

        // Get the ticketStatusHistory
        restTicketStatusHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, ticketStatusHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ticketStatusHistory.getId().intValue()))
            .andExpect(jsonPath("$.oldStatus").value(DEFAULT_OLD_STATUS.toString()))
            .andExpect(jsonPath("$.newStatus").value(DEFAULT_NEW_STATUS.toString()))
            .andExpect(jsonPath("$.changedDate").value(DEFAULT_CHANGED_DATE.toString()));
    }

    @Test
    @Transactional
    void getTicketStatusHistoriesByIdFiltering() throws Exception {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.saveAndFlush(ticketStatusHistory);

        Long id = ticketStatusHistory.getId();

        defaultTicketStatusHistoryFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultTicketStatusHistoryFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultTicketStatusHistoryFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTicketStatusHistoriesByOldStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.saveAndFlush(ticketStatusHistory);

        // Get all the ticketStatusHistoryList where oldStatus equals to
        defaultTicketStatusHistoryFiltering("oldStatus.equals=" + DEFAULT_OLD_STATUS, "oldStatus.equals=" + UPDATED_OLD_STATUS);
    }

    @Test
    @Transactional
    void getAllTicketStatusHistoriesByOldStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.saveAndFlush(ticketStatusHistory);

        // Get all the ticketStatusHistoryList where oldStatus in
        defaultTicketStatusHistoryFiltering(
            "oldStatus.in=" + DEFAULT_OLD_STATUS + "," + UPDATED_OLD_STATUS,
            "oldStatus.in=" + UPDATED_OLD_STATUS
        );
    }

    @Test
    @Transactional
    void getAllTicketStatusHistoriesByOldStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.saveAndFlush(ticketStatusHistory);

        // Get all the ticketStatusHistoryList where oldStatus is not null
        defaultTicketStatusHistoryFiltering("oldStatus.specified=true", "oldStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllTicketStatusHistoriesByNewStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.saveAndFlush(ticketStatusHistory);

        // Get all the ticketStatusHistoryList where newStatus equals to
        defaultTicketStatusHistoryFiltering("newStatus.equals=" + DEFAULT_NEW_STATUS, "newStatus.equals=" + UPDATED_NEW_STATUS);
    }

    @Test
    @Transactional
    void getAllTicketStatusHistoriesByNewStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.saveAndFlush(ticketStatusHistory);

        // Get all the ticketStatusHistoryList where newStatus in
        defaultTicketStatusHistoryFiltering(
            "newStatus.in=" + DEFAULT_NEW_STATUS + "," + UPDATED_NEW_STATUS,
            "newStatus.in=" + UPDATED_NEW_STATUS
        );
    }

    @Test
    @Transactional
    void getAllTicketStatusHistoriesByNewStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.saveAndFlush(ticketStatusHistory);

        // Get all the ticketStatusHistoryList where newStatus is not null
        defaultTicketStatusHistoryFiltering("newStatus.specified=true", "newStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllTicketStatusHistoriesByChangedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.saveAndFlush(ticketStatusHistory);

        // Get all the ticketStatusHistoryList where changedDate equals to
        defaultTicketStatusHistoryFiltering("changedDate.equals=" + DEFAULT_CHANGED_DATE, "changedDate.equals=" + UPDATED_CHANGED_DATE);
    }

    @Test
    @Transactional
    void getAllTicketStatusHistoriesByChangedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.saveAndFlush(ticketStatusHistory);

        // Get all the ticketStatusHistoryList where changedDate in
        defaultTicketStatusHistoryFiltering(
            "changedDate.in=" + DEFAULT_CHANGED_DATE + "," + UPDATED_CHANGED_DATE,
            "changedDate.in=" + UPDATED_CHANGED_DATE
        );
    }

    @Test
    @Transactional
    void getAllTicketStatusHistoriesByChangedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.saveAndFlush(ticketStatusHistory);

        // Get all the ticketStatusHistoryList where changedDate is not null
        defaultTicketStatusHistoryFiltering("changedDate.specified=true", "changedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTicketStatusHistoriesByTicketIsEqualToSomething() throws Exception {
        Ticket ticket;
        if (TestUtil.findAll(em, Ticket.class).isEmpty()) {
            ticketStatusHistoryRepository.saveAndFlush(ticketStatusHistory);
            ticket = TicketResourceIT.createEntity(em);
        } else {
            ticket = TestUtil.findAll(em, Ticket.class).get(0);
        }
        em.persist(ticket);
        em.flush();
        ticketStatusHistory.setTicket(ticket);
        ticketStatusHistoryRepository.saveAndFlush(ticketStatusHistory);
        Long ticketId = ticket.getId();
        // Get all the ticketStatusHistoryList where ticket equals to ticketId
        defaultTicketStatusHistoryShouldBeFound("ticketId.equals=" + ticketId);

        // Get all the ticketStatusHistoryList where ticket equals to (ticketId + 1)
        defaultTicketStatusHistoryShouldNotBeFound("ticketId.equals=" + (ticketId + 1));
    }

    @Test
    @Transactional
    void getAllTicketStatusHistoriesByChangedByIsEqualToSomething() throws Exception {
        User changedBy;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            ticketStatusHistoryRepository.saveAndFlush(ticketStatusHistory);
            changedBy = UserResourceIT.createEntity();
        } else {
            changedBy = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(changedBy);
        em.flush();
        ticketStatusHistory.setChangedBy(changedBy);
        ticketStatusHistoryRepository.saveAndFlush(ticketStatusHistory);
        Long changedById = changedBy.getId();
        // Get all the ticketStatusHistoryList where changedBy equals to changedById
        defaultTicketStatusHistoryShouldBeFound("changedById.equals=" + changedById);

        // Get all the ticketStatusHistoryList where changedBy equals to (changedById + 1)
        defaultTicketStatusHistoryShouldNotBeFound("changedById.equals=" + (changedById + 1));
    }

    private void defaultTicketStatusHistoryFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultTicketStatusHistoryShouldBeFound(shouldBeFound);
        defaultTicketStatusHistoryShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTicketStatusHistoryShouldBeFound(String filter) throws Exception {
        restTicketStatusHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticketStatusHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].oldStatus").value(hasItem(DEFAULT_OLD_STATUS.toString())))
            .andExpect(jsonPath("$.[*].newStatus").value(hasItem(DEFAULT_NEW_STATUS.toString())))
            .andExpect(jsonPath("$.[*].changedDate").value(hasItem(DEFAULT_CHANGED_DATE.toString())));

        // Check, that the count call also returns 1
        restTicketStatusHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTicketStatusHistoryShouldNotBeFound(String filter) throws Exception {
        restTicketStatusHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTicketStatusHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTicketStatusHistory() throws Exception {
        // Get the ticketStatusHistory
        restTicketStatusHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTicketStatusHistory() throws Exception {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.saveAndFlush(ticketStatusHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ticketStatusHistory
        TicketStatusHistory updatedTicketStatusHistory = ticketStatusHistoryRepository.findById(ticketStatusHistory.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTicketStatusHistory are not directly saved in db
        em.detach(updatedTicketStatusHistory);
        updatedTicketStatusHistory.oldStatus(UPDATED_OLD_STATUS).newStatus(UPDATED_NEW_STATUS).changedDate(UPDATED_CHANGED_DATE);
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(updatedTicketStatusHistory);

        restTicketStatusHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ticketStatusHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ticketStatusHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the TicketStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTicketStatusHistoryToMatchAllProperties(updatedTicketStatusHistory);
    }

    @Test
    @Transactional
    void putNonExistingTicketStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketStatusHistory.setId(longCount.incrementAndGet());

        // Create the TicketStatusHistory
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(ticketStatusHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketStatusHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ticketStatusHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ticketStatusHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTicketStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketStatusHistory.setId(longCount.incrementAndGet());

        // Create the TicketStatusHistory
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(ticketStatusHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketStatusHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ticketStatusHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTicketStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketStatusHistory.setId(longCount.incrementAndGet());

        // Create the TicketStatusHistory
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(ticketStatusHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketStatusHistoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketStatusHistoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TicketStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTicketStatusHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.saveAndFlush(ticketStatusHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ticketStatusHistory using partial update
        TicketStatusHistory partialUpdatedTicketStatusHistory = new TicketStatusHistory();
        partialUpdatedTicketStatusHistory.setId(ticketStatusHistory.getId());

        restTicketStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTicketStatusHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTicketStatusHistory))
            )
            .andExpect(status().isOk());

        // Validate the TicketStatusHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTicketStatusHistoryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTicketStatusHistory, ticketStatusHistory),
            getPersistedTicketStatusHistory(ticketStatusHistory)
        );
    }

    @Test
    @Transactional
    void fullUpdateTicketStatusHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.saveAndFlush(ticketStatusHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ticketStatusHistory using partial update
        TicketStatusHistory partialUpdatedTicketStatusHistory = new TicketStatusHistory();
        partialUpdatedTicketStatusHistory.setId(ticketStatusHistory.getId());

        partialUpdatedTicketStatusHistory.oldStatus(UPDATED_OLD_STATUS).newStatus(UPDATED_NEW_STATUS).changedDate(UPDATED_CHANGED_DATE);

        restTicketStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTicketStatusHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTicketStatusHistory))
            )
            .andExpect(status().isOk());

        // Validate the TicketStatusHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTicketStatusHistoryUpdatableFieldsEquals(
            partialUpdatedTicketStatusHistory,
            getPersistedTicketStatusHistory(partialUpdatedTicketStatusHistory)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTicketStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketStatusHistory.setId(longCount.incrementAndGet());

        // Create the TicketStatusHistory
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(ticketStatusHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ticketStatusHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ticketStatusHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTicketStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketStatusHistory.setId(longCount.incrementAndGet());

        // Create the TicketStatusHistory
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(ticketStatusHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ticketStatusHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTicketStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketStatusHistory.setId(longCount.incrementAndGet());

        // Create the TicketStatusHistory
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(ticketStatusHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ticketStatusHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TicketStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTicketStatusHistory() throws Exception {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.saveAndFlush(ticketStatusHistory);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ticketStatusHistory
        restTicketStatusHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, ticketStatusHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ticketStatusHistoryRepository.count();
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

    protected TicketStatusHistory getPersistedTicketStatusHistory(TicketStatusHistory ticketStatusHistory) {
        return ticketStatusHistoryRepository.findById(ticketStatusHistory.getId()).orElseThrow();
    }

    protected void assertPersistedTicketStatusHistoryToMatchAllProperties(TicketStatusHistory expectedTicketStatusHistory) {
        assertTicketStatusHistoryAllPropertiesEquals(
            expectedTicketStatusHistory,
            getPersistedTicketStatusHistory(expectedTicketStatusHistory)
        );
    }

    protected void assertPersistedTicketStatusHistoryToMatchUpdatableProperties(TicketStatusHistory expectedTicketStatusHistory) {
        assertTicketStatusHistoryAllUpdatablePropertiesEquals(
            expectedTicketStatusHistory,
            getPersistedTicketStatusHistory(expectedTicketStatusHistory)
        );
    }
}
