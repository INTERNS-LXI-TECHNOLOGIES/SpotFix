package com.divisosofttech.spot_fix.web.rest;

import static com.divisosofttech.spot_fix.domain.TicketStatusHistoryAsserts.*;
import static com.divisosofttech.spot_fix.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import com.divisosofttech.spot_fix.IntegrationTest;
import com.divisosofttech.spot_fix.domain.Ticket;
import com.divisosofttech.spot_fix.domain.TicketStatusHistory;
import com.divisosofttech.spot_fix.domain.User;
import com.divisosofttech.spot_fix.domain.enumeration.TicketStatus;
import com.divisosofttech.spot_fix.domain.enumeration.TicketStatus;
import com.divisosofttech.spot_fix.repository.EntityManager;
import com.divisosofttech.spot_fix.repository.TicketRepository;
import com.divisosofttech.spot_fix.repository.TicketStatusHistoryRepository;
import com.divisosofttech.spot_fix.repository.UserRepository;
import com.divisosofttech.spot_fix.repository.UserRepository;
import com.divisosofttech.spot_fix.service.TicketStatusHistoryService;
import com.divisosofttech.spot_fix.service.dto.TicketStatusHistoryDTO;
import com.divisosofttech.spot_fix.service.mapper.TicketStatusHistoryMapper;
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
 * Integration tests for the {@link TicketStatusHistoryResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
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
    private WebTestClient webTestClient;

    private TicketStatusHistory ticketStatusHistory;

    private TicketStatusHistory insertedTicketStatusHistory;

    @Autowired
    private TicketRepository ticketRepository;

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
        ticket = em.insert(TicketResourceIT.createEntity(em)).block();
        ticketStatusHistory.setTicket(ticket);
        // Add required entity
        User user = em.insert(UserResourceIT.createEntity()).block();
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
        ticket = em.insert(TicketResourceIT.createUpdatedEntity(em)).block();
        updatedTicketStatusHistory.setTicket(ticket);
        // Add required entity
        User user = em.insert(UserResourceIT.createEntity()).block();
        updatedTicketStatusHistory.setChangedBy(user);
        return updatedTicketStatusHistory;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(TicketStatusHistory.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
        TicketResourceIT.deleteEntities(em);
        UserResourceIT.deleteEntities(em);
    }

    @BeforeEach
    void initTest() {
        ticketStatusHistory = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedTicketStatusHistory != null) {
            ticketStatusHistoryRepository.delete(insertedTicketStatusHistory).block();
            insertedTicketStatusHistory = null;
        }
        deleteEntities(em);
        userRepository.deleteAllUserAuthorities().block();
        userRepository.deleteAll().block();
    }

    @Test
    void createTicketStatusHistory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TicketStatusHistory
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(ticketStatusHistory);
        var returnedTicketStatusHistoryDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketStatusHistoryDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(TicketStatusHistoryDTO.class)
            .returnResult()
            .getResponseBody();

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
    void createTicketStatusHistoryWithExistingId() throws Exception {
        // Create the TicketStatusHistory with an existing ID
        ticketStatusHistory.setId(1L);
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(ticketStatusHistory);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketStatusHistoryDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TicketStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkOldStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticketStatusHistory.setOldStatus(null);

        // Create the TicketStatusHistory, which fails.
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(ticketStatusHistory);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketStatusHistoryDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkNewStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticketStatusHistory.setNewStatus(null);

        // Create the TicketStatusHistory, which fails.
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(ticketStatusHistory);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketStatusHistoryDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkChangedDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticketStatusHistory.setChangedDate(null);

        // Create the TicketStatusHistory, which fails.
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(ticketStatusHistory);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketStatusHistoryDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllTicketStatusHistories() {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.save(ticketStatusHistory).block();

        // Get all the ticketStatusHistoryList
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
            .value(hasItem(ticketStatusHistory.getId().intValue()))
            .jsonPath("$.[*].oldStatus")
            .value(hasItem(DEFAULT_OLD_STATUS.toString()))
            .jsonPath("$.[*].newStatus")
            .value(hasItem(DEFAULT_NEW_STATUS.toString()))
            .jsonPath("$.[*].changedDate")
            .value(hasItem(DEFAULT_CHANGED_DATE.toString()));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTicketStatusHistoriesWithEagerRelationshipsIsEnabled() {
        when(ticketStatusHistoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=true").exchange().expectStatus().isOk();

        verify(ticketStatusHistoryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTicketStatusHistoriesWithEagerRelationshipsIsNotEnabled() {
        when(ticketStatusHistoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=false").exchange().expectStatus().isOk();
        verify(ticketStatusHistoryRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    void getTicketStatusHistory() {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.save(ticketStatusHistory).block();

        // Get the ticketStatusHistory
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, ticketStatusHistory.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(ticketStatusHistory.getId().intValue()))
            .jsonPath("$.oldStatus")
            .value(is(DEFAULT_OLD_STATUS.toString()))
            .jsonPath("$.newStatus")
            .value(is(DEFAULT_NEW_STATUS.toString()))
            .jsonPath("$.changedDate")
            .value(is(DEFAULT_CHANGED_DATE.toString()));
    }

    @Test
    void getTicketStatusHistoriesByIdFiltering() {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.save(ticketStatusHistory).block();

        Long id = ticketStatusHistory.getId();

        defaultTicketStatusHistoryFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultTicketStatusHistoryFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultTicketStatusHistoryFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    void getAllTicketStatusHistoriesByOldStatusIsEqualToSomething() {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.save(ticketStatusHistory).block();

        // Get all the ticketStatusHistoryList where oldStatus equals to
        defaultTicketStatusHistoryFiltering("oldStatus.equals=" + DEFAULT_OLD_STATUS, "oldStatus.equals=" + UPDATED_OLD_STATUS);
    }

    @Test
    void getAllTicketStatusHistoriesByOldStatusIsInShouldWork() {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.save(ticketStatusHistory).block();

        // Get all the ticketStatusHistoryList where oldStatus in
        defaultTicketStatusHistoryFiltering(
            "oldStatus.in=" + DEFAULT_OLD_STATUS + "," + UPDATED_OLD_STATUS,
            "oldStatus.in=" + UPDATED_OLD_STATUS
        );
    }

    @Test
    void getAllTicketStatusHistoriesByOldStatusIsNullOrNotNull() {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.save(ticketStatusHistory).block();

        // Get all the ticketStatusHistoryList where oldStatus is not null
        defaultTicketStatusHistoryFiltering("oldStatus.specified=true", "oldStatus.specified=false");
    }

    @Test
    void getAllTicketStatusHistoriesByNewStatusIsEqualToSomething() {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.save(ticketStatusHistory).block();

        // Get all the ticketStatusHistoryList where newStatus equals to
        defaultTicketStatusHistoryFiltering("newStatus.equals=" + DEFAULT_NEW_STATUS, "newStatus.equals=" + UPDATED_NEW_STATUS);
    }

    @Test
    void getAllTicketStatusHistoriesByNewStatusIsInShouldWork() {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.save(ticketStatusHistory).block();

        // Get all the ticketStatusHistoryList where newStatus in
        defaultTicketStatusHistoryFiltering(
            "newStatus.in=" + DEFAULT_NEW_STATUS + "," + UPDATED_NEW_STATUS,
            "newStatus.in=" + UPDATED_NEW_STATUS
        );
    }

    @Test
    void getAllTicketStatusHistoriesByNewStatusIsNullOrNotNull() {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.save(ticketStatusHistory).block();

        // Get all the ticketStatusHistoryList where newStatus is not null
        defaultTicketStatusHistoryFiltering("newStatus.specified=true", "newStatus.specified=false");
    }

    @Test
    void getAllTicketStatusHistoriesByChangedDateIsEqualToSomething() {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.save(ticketStatusHistory).block();

        // Get all the ticketStatusHistoryList where changedDate equals to
        defaultTicketStatusHistoryFiltering("changedDate.equals=" + DEFAULT_CHANGED_DATE, "changedDate.equals=" + UPDATED_CHANGED_DATE);
    }

    @Test
    void getAllTicketStatusHistoriesByChangedDateIsInShouldWork() {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.save(ticketStatusHistory).block();

        // Get all the ticketStatusHistoryList where changedDate in
        defaultTicketStatusHistoryFiltering(
            "changedDate.in=" + DEFAULT_CHANGED_DATE + "," + UPDATED_CHANGED_DATE,
            "changedDate.in=" + UPDATED_CHANGED_DATE
        );
    }

    @Test
    void getAllTicketStatusHistoriesByChangedDateIsNullOrNotNull() {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.save(ticketStatusHistory).block();

        // Get all the ticketStatusHistoryList where changedDate is not null
        defaultTicketStatusHistoryFiltering("changedDate.specified=true", "changedDate.specified=false");
    }

    @Test
    void getAllTicketStatusHistoriesByTicketIsEqualToSomething() {
        Ticket ticket = TicketResourceIT.createEntity(em);
        ticketRepository.save(ticket).block();
        Long ticketId = ticket.getId();
        ticketStatusHistory.setTicketId(ticketId);
        insertedTicketStatusHistory = ticketStatusHistoryRepository.save(ticketStatusHistory).block();
        // Get all the ticketStatusHistoryList where ticket equals to ticketId
        defaultTicketStatusHistoryShouldBeFound("ticketId.equals=" + ticketId);

        // Get all the ticketStatusHistoryList where ticket equals to (ticketId + 1)
        defaultTicketStatusHistoryShouldNotBeFound("ticketId.equals=" + (ticketId + 1));
    }

    @Test
    void getAllTicketStatusHistoriesByChangedByIsEqualToSomething() {
        User changedBy = UserResourceIT.createEntity();
        userRepository.save(changedBy).block();
        Long changedById = changedBy.getId();
        ticketStatusHistory.setChangedById(changedById);
        insertedTicketStatusHistory = ticketStatusHistoryRepository.save(ticketStatusHistory).block();
        // Get all the ticketStatusHistoryList where changedBy equals to changedById
        defaultTicketStatusHistoryShouldBeFound("changedById.equals=" + changedById);

        // Get all the ticketStatusHistoryList where changedBy equals to (changedById + 1)
        defaultTicketStatusHistoryShouldNotBeFound("changedById.equals=" + (changedById + 1));
    }

    private void defaultTicketStatusHistoryFiltering(String shouldBeFound, String shouldNotBeFound) {
        defaultTicketStatusHistoryShouldBeFound(shouldBeFound);
        defaultTicketStatusHistoryShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTicketStatusHistoryShouldBeFound(String filter) {
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
            .value(hasItem(ticketStatusHistory.getId().intValue()))
            .jsonPath("$.[*].oldStatus")
            .value(hasItem(DEFAULT_OLD_STATUS.toString()))
            .jsonPath("$.[*].newStatus")
            .value(hasItem(DEFAULT_NEW_STATUS.toString()))
            .jsonPath("$.[*].changedDate")
            .value(hasItem(DEFAULT_CHANGED_DATE.toString()));

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
    private void defaultTicketStatusHistoryShouldNotBeFound(String filter) {
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
    void getNonExistingTicketStatusHistory() {
        // Get the ticketStatusHistory
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingTicketStatusHistory() throws Exception {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.save(ticketStatusHistory).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ticketStatusHistory
        TicketStatusHistory updatedTicketStatusHistory = ticketStatusHistoryRepository.findById(ticketStatusHistory.getId()).block();
        updatedTicketStatusHistory.oldStatus(UPDATED_OLD_STATUS).newStatus(UPDATED_NEW_STATUS).changedDate(UPDATED_CHANGED_DATE);
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(updatedTicketStatusHistory);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, ticketStatusHistoryDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketStatusHistoryDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the TicketStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTicketStatusHistoryToMatchAllProperties(updatedTicketStatusHistory);
    }

    @Test
    void putNonExistingTicketStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketStatusHistory.setId(longCount.incrementAndGet());

        // Create the TicketStatusHistory
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(ticketStatusHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, ticketStatusHistoryDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketStatusHistoryDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TicketStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTicketStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketStatusHistory.setId(longCount.incrementAndGet());

        // Create the TicketStatusHistory
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(ticketStatusHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketStatusHistoryDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TicketStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTicketStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketStatusHistory.setId(longCount.incrementAndGet());

        // Create the TicketStatusHistory
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(ticketStatusHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketStatusHistoryDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the TicketStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTicketStatusHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.save(ticketStatusHistory).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ticketStatusHistory using partial update
        TicketStatusHistory partialUpdatedTicketStatusHistory = new TicketStatusHistory();
        partialUpdatedTicketStatusHistory.setId(ticketStatusHistory.getId());

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTicketStatusHistory.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedTicketStatusHistory))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the TicketStatusHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTicketStatusHistoryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTicketStatusHistory, ticketStatusHistory),
            getPersistedTicketStatusHistory(ticketStatusHistory)
        );
    }

    @Test
    void fullUpdateTicketStatusHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.save(ticketStatusHistory).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ticketStatusHistory using partial update
        TicketStatusHistory partialUpdatedTicketStatusHistory = new TicketStatusHistory();
        partialUpdatedTicketStatusHistory.setId(ticketStatusHistory.getId());

        partialUpdatedTicketStatusHistory.oldStatus(UPDATED_OLD_STATUS).newStatus(UPDATED_NEW_STATUS).changedDate(UPDATED_CHANGED_DATE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTicketStatusHistory.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedTicketStatusHistory))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the TicketStatusHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTicketStatusHistoryUpdatableFieldsEquals(
            partialUpdatedTicketStatusHistory,
            getPersistedTicketStatusHistory(partialUpdatedTicketStatusHistory)
        );
    }

    @Test
    void patchNonExistingTicketStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketStatusHistory.setId(longCount.incrementAndGet());

        // Create the TicketStatusHistory
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(ticketStatusHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, ticketStatusHistoryDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(ticketStatusHistoryDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TicketStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTicketStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketStatusHistory.setId(longCount.incrementAndGet());

        // Create the TicketStatusHistory
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(ticketStatusHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(ticketStatusHistoryDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TicketStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTicketStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketStatusHistory.setId(longCount.incrementAndGet());

        // Create the TicketStatusHistory
        TicketStatusHistoryDTO ticketStatusHistoryDTO = ticketStatusHistoryMapper.toDto(ticketStatusHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(ticketStatusHistoryDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the TicketStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTicketStatusHistory() {
        // Initialize the database
        insertedTicketStatusHistory = ticketStatusHistoryRepository.save(ticketStatusHistory).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ticketStatusHistory
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, ticketStatusHistory.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ticketStatusHistoryRepository.count().block();
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
        return ticketStatusHistoryRepository.findById(ticketStatusHistory.getId()).block();
    }

    protected void assertPersistedTicketStatusHistoryToMatchAllProperties(TicketStatusHistory expectedTicketStatusHistory) {
        // Test fails because reactive api returns an empty object instead of null
        // assertTicketStatusHistoryAllPropertiesEquals(expectedTicketStatusHistory, getPersistedTicketStatusHistory(expectedTicketStatusHistory));
        assertTicketStatusHistoryUpdatableFieldsEquals(
            expectedTicketStatusHistory,
            getPersistedTicketStatusHistory(expectedTicketStatusHistory)
        );
    }

    protected void assertPersistedTicketStatusHistoryToMatchUpdatableProperties(TicketStatusHistory expectedTicketStatusHistory) {
        // Test fails because reactive api returns an empty object instead of null
        // assertTicketStatusHistoryAllUpdatablePropertiesEquals(expectedTicketStatusHistory, getPersistedTicketStatusHistory(expectedTicketStatusHistory));
        assertTicketStatusHistoryUpdatableFieldsEquals(
            expectedTicketStatusHistory,
            getPersistedTicketStatusHistory(expectedTicketStatusHistory)
        );
    }
}
