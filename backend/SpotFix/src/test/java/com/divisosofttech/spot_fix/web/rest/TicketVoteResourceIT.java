package com.divisosofttech.spot_fix.web.rest;

import static com.divisosofttech.spot_fix.domain.TicketVoteAsserts.*;
import static com.divisosofttech.spot_fix.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import com.divisosofttech.spot_fix.IntegrationTest;
import com.divisosofttech.spot_fix.domain.Ticket;
import com.divisosofttech.spot_fix.domain.TicketVote;
import com.divisosofttech.spot_fix.domain.User;
import com.divisosofttech.spot_fix.domain.enumeration.VoteType;
import com.divisosofttech.spot_fix.repository.EntityManager;
import com.divisosofttech.spot_fix.repository.TicketRepository;
import com.divisosofttech.spot_fix.repository.TicketVoteRepository;
import com.divisosofttech.spot_fix.repository.UserRepository;
import com.divisosofttech.spot_fix.repository.UserRepository;
import com.divisosofttech.spot_fix.service.TicketVoteService;
import com.divisosofttech.spot_fix.service.dto.TicketVoteDTO;
import com.divisosofttech.spot_fix.service.mapper.TicketVoteMapper;
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
 * Integration tests for the {@link TicketVoteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class TicketVoteResourceIT {

    private static final VoteType DEFAULT_VOTE_TYPE = VoteType.UPVOTE;
    private static final VoteType UPDATED_VOTE_TYPE = VoteType.DOWNVOTE;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/ticket-votes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TicketVoteRepository ticketVoteRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private TicketVoteRepository ticketVoteRepositoryMock;

    @Autowired
    private TicketVoteMapper ticketVoteMapper;

    @Mock
    private TicketVoteService ticketVoteServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private TicketVote ticketVote;

    private TicketVote insertedTicketVote;

    @Autowired
    private TicketRepository ticketRepository;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketVote createEntity(EntityManager em) {
        TicketVote ticketVote = new TicketVote().voteType(DEFAULT_VOTE_TYPE).createdDate(DEFAULT_CREATED_DATE);
        // Add required entity
        Ticket ticket;
        ticket = em.insert(TicketResourceIT.createEntity(em)).block();
        ticketVote.setTicket(ticket);
        // Add required entity
        User user = em.insert(UserResourceIT.createEntity()).block();
        ticketVote.setUser(user);
        return ticketVote;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketVote createUpdatedEntity(EntityManager em) {
        TicketVote updatedTicketVote = new TicketVote().voteType(UPDATED_VOTE_TYPE).createdDate(UPDATED_CREATED_DATE);
        // Add required entity
        Ticket ticket;
        ticket = em.insert(TicketResourceIT.createUpdatedEntity(em)).block();
        updatedTicketVote.setTicket(ticket);
        // Add required entity
        User user = em.insert(UserResourceIT.createEntity()).block();
        updatedTicketVote.setUser(user);
        return updatedTicketVote;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(TicketVote.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
        TicketResourceIT.deleteEntities(em);
        UserResourceIT.deleteEntities(em);
    }

    @BeforeEach
    void initTest() {
        ticketVote = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedTicketVote != null) {
            ticketVoteRepository.delete(insertedTicketVote).block();
            insertedTicketVote = null;
        }
        deleteEntities(em);
        userRepository.deleteAllUserAuthorities().block();
        userRepository.deleteAll().block();
    }

    @Test
    void createTicketVote() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TicketVote
        TicketVoteDTO ticketVoteDTO = ticketVoteMapper.toDto(ticketVote);
        var returnedTicketVoteDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketVoteDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(TicketVoteDTO.class)
            .returnResult()
            .getResponseBody();

        // Validate the TicketVote in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTicketVote = ticketVoteMapper.toEntity(returnedTicketVoteDTO);
        assertTicketVoteUpdatableFieldsEquals(returnedTicketVote, getPersistedTicketVote(returnedTicketVote));

        insertedTicketVote = returnedTicketVote;
    }

    @Test
    void createTicketVoteWithExistingId() throws Exception {
        // Create the TicketVote with an existing ID
        ticketVote.setId(1L);
        TicketVoteDTO ticketVoteDTO = ticketVoteMapper.toDto(ticketVote);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketVoteDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TicketVote in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkVoteTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticketVote.setVoteType(null);

        // Create the TicketVote, which fails.
        TicketVoteDTO ticketVoteDTO = ticketVoteMapper.toDto(ticketVote);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketVoteDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkCreatedDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticketVote.setCreatedDate(null);

        // Create the TicketVote, which fails.
        TicketVoteDTO ticketVoteDTO = ticketVoteMapper.toDto(ticketVote);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketVoteDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllTicketVotes() {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.save(ticketVote).block();

        // Get all the ticketVoteList
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
            .value(hasItem(ticketVote.getId().intValue()))
            .jsonPath("$.[*].voteType")
            .value(hasItem(DEFAULT_VOTE_TYPE.toString()))
            .jsonPath("$.[*].createdDate")
            .value(hasItem(DEFAULT_CREATED_DATE.toString()));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTicketVotesWithEagerRelationshipsIsEnabled() {
        when(ticketVoteServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=true").exchange().expectStatus().isOk();

        verify(ticketVoteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTicketVotesWithEagerRelationshipsIsNotEnabled() {
        when(ticketVoteServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=false").exchange().expectStatus().isOk();
        verify(ticketVoteRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    void getTicketVote() {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.save(ticketVote).block();

        // Get the ticketVote
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, ticketVote.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(ticketVote.getId().intValue()))
            .jsonPath("$.voteType")
            .value(is(DEFAULT_VOTE_TYPE.toString()))
            .jsonPath("$.createdDate")
            .value(is(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    void getTicketVotesByIdFiltering() {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.save(ticketVote).block();

        Long id = ticketVote.getId();

        defaultTicketVoteFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultTicketVoteFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultTicketVoteFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    void getAllTicketVotesByVoteTypeIsEqualToSomething() {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.save(ticketVote).block();

        // Get all the ticketVoteList where voteType equals to
        defaultTicketVoteFiltering("voteType.equals=" + DEFAULT_VOTE_TYPE, "voteType.equals=" + UPDATED_VOTE_TYPE);
    }

    @Test
    void getAllTicketVotesByVoteTypeIsInShouldWork() {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.save(ticketVote).block();

        // Get all the ticketVoteList where voteType in
        defaultTicketVoteFiltering("voteType.in=" + DEFAULT_VOTE_TYPE + "," + UPDATED_VOTE_TYPE, "voteType.in=" + UPDATED_VOTE_TYPE);
    }

    @Test
    void getAllTicketVotesByVoteTypeIsNullOrNotNull() {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.save(ticketVote).block();

        // Get all the ticketVoteList where voteType is not null
        defaultTicketVoteFiltering("voteType.specified=true", "voteType.specified=false");
    }

    @Test
    void getAllTicketVotesByCreatedDateIsEqualToSomething() {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.save(ticketVote).block();

        // Get all the ticketVoteList where createdDate equals to
        defaultTicketVoteFiltering("createdDate.equals=" + DEFAULT_CREATED_DATE, "createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    void getAllTicketVotesByCreatedDateIsInShouldWork() {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.save(ticketVote).block();

        // Get all the ticketVoteList where createdDate in
        defaultTicketVoteFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    void getAllTicketVotesByCreatedDateIsNullOrNotNull() {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.save(ticketVote).block();

        // Get all the ticketVoteList where createdDate is not null
        defaultTicketVoteFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    void getAllTicketVotesByTicketIsEqualToSomething() {
        Ticket ticket = TicketResourceIT.createEntity(em);
        ticketRepository.save(ticket).block();
        Long ticketId = ticket.getId();
        ticketVote.setTicketId(ticketId);
        insertedTicketVote = ticketVoteRepository.save(ticketVote).block();
        // Get all the ticketVoteList where ticket equals to ticketId
        defaultTicketVoteShouldBeFound("ticketId.equals=" + ticketId);

        // Get all the ticketVoteList where ticket equals to (ticketId + 1)
        defaultTicketVoteShouldNotBeFound("ticketId.equals=" + (ticketId + 1));
    }

    @Test
    void getAllTicketVotesByUserIsEqualToSomething() {
        User user = UserResourceIT.createEntity();
        userRepository.save(user).block();
        Long userId = user.getId();
        ticketVote.setUserId(userId);
        insertedTicketVote = ticketVoteRepository.save(ticketVote).block();
        // Get all the ticketVoteList where user equals to userId
        defaultTicketVoteShouldBeFound("userId.equals=" + userId);

        // Get all the ticketVoteList where user equals to (userId + 1)
        defaultTicketVoteShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    private void defaultTicketVoteFiltering(String shouldBeFound, String shouldNotBeFound) {
        defaultTicketVoteShouldBeFound(shouldBeFound);
        defaultTicketVoteShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTicketVoteShouldBeFound(String filter) {
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
            .value(hasItem(ticketVote.getId().intValue()))
            .jsonPath("$.[*].voteType")
            .value(hasItem(DEFAULT_VOTE_TYPE.toString()))
            .jsonPath("$.[*].createdDate")
            .value(hasItem(DEFAULT_CREATED_DATE.toString()));

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
    private void defaultTicketVoteShouldNotBeFound(String filter) {
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
    void getNonExistingTicketVote() {
        // Get the ticketVote
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingTicketVote() throws Exception {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.save(ticketVote).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ticketVote
        TicketVote updatedTicketVote = ticketVoteRepository.findById(ticketVote.getId()).block();
        updatedTicketVote.voteType(UPDATED_VOTE_TYPE).createdDate(UPDATED_CREATED_DATE);
        TicketVoteDTO ticketVoteDTO = ticketVoteMapper.toDto(updatedTicketVote);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, ticketVoteDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketVoteDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the TicketVote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTicketVoteToMatchAllProperties(updatedTicketVote);
    }

    @Test
    void putNonExistingTicketVote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketVote.setId(longCount.incrementAndGet());

        // Create the TicketVote
        TicketVoteDTO ticketVoteDTO = ticketVoteMapper.toDto(ticketVote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, ticketVoteDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketVoteDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TicketVote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTicketVote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketVote.setId(longCount.incrementAndGet());

        // Create the TicketVote
        TicketVoteDTO ticketVoteDTO = ticketVoteMapper.toDto(ticketVote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketVoteDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TicketVote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTicketVote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketVote.setId(longCount.incrementAndGet());

        // Create the TicketVote
        TicketVoteDTO ticketVoteDTO = ticketVoteMapper.toDto(ticketVote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ticketVoteDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the TicketVote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTicketVoteWithPatch() throws Exception {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.save(ticketVote).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ticketVote using partial update
        TicketVote partialUpdatedTicketVote = new TicketVote();
        partialUpdatedTicketVote.setId(ticketVote.getId());

        partialUpdatedTicketVote.createdDate(UPDATED_CREATED_DATE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTicketVote.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedTicketVote))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the TicketVote in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTicketVoteUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTicketVote, ticketVote),
            getPersistedTicketVote(ticketVote)
        );
    }

    @Test
    void fullUpdateTicketVoteWithPatch() throws Exception {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.save(ticketVote).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ticketVote using partial update
        TicketVote partialUpdatedTicketVote = new TicketVote();
        partialUpdatedTicketVote.setId(ticketVote.getId());

        partialUpdatedTicketVote.voteType(UPDATED_VOTE_TYPE).createdDate(UPDATED_CREATED_DATE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTicketVote.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedTicketVote))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the TicketVote in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTicketVoteUpdatableFieldsEquals(partialUpdatedTicketVote, getPersistedTicketVote(partialUpdatedTicketVote));
    }

    @Test
    void patchNonExistingTicketVote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketVote.setId(longCount.incrementAndGet());

        // Create the TicketVote
        TicketVoteDTO ticketVoteDTO = ticketVoteMapper.toDto(ticketVote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, ticketVoteDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(ticketVoteDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TicketVote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTicketVote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketVote.setId(longCount.incrementAndGet());

        // Create the TicketVote
        TicketVoteDTO ticketVoteDTO = ticketVoteMapper.toDto(ticketVote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(ticketVoteDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TicketVote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTicketVote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketVote.setId(longCount.incrementAndGet());

        // Create the TicketVote
        TicketVoteDTO ticketVoteDTO = ticketVoteMapper.toDto(ticketVote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(ticketVoteDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the TicketVote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTicketVote() {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.save(ticketVote).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ticketVote
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, ticketVote.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ticketVoteRepository.count().block();
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

    protected TicketVote getPersistedTicketVote(TicketVote ticketVote) {
        return ticketVoteRepository.findById(ticketVote.getId()).block();
    }

    protected void assertPersistedTicketVoteToMatchAllProperties(TicketVote expectedTicketVote) {
        // Test fails because reactive api returns an empty object instead of null
        // assertTicketVoteAllPropertiesEquals(expectedTicketVote, getPersistedTicketVote(expectedTicketVote));
        assertTicketVoteUpdatableFieldsEquals(expectedTicketVote, getPersistedTicketVote(expectedTicketVote));
    }

    protected void assertPersistedTicketVoteToMatchUpdatableProperties(TicketVote expectedTicketVote) {
        // Test fails because reactive api returns an empty object instead of null
        // assertTicketVoteAllUpdatablePropertiesEquals(expectedTicketVote, getPersistedTicketVote(expectedTicketVote));
        assertTicketVoteUpdatableFieldsEquals(expectedTicketVote, getPersistedTicketVote(expectedTicketVote));
    }
}
