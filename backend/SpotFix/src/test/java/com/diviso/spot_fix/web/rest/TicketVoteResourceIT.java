package com.diviso.spot_fix.web.rest;

import static com.diviso.spot_fix.domain.TicketVoteAsserts.*;
import static com.diviso.spot_fix.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.diviso.spot_fix.IntegrationTest;
import com.diviso.spot_fix.domain.Ticket;
import com.diviso.spot_fix.domain.TicketVote;
import com.diviso.spot_fix.domain.User;
import com.diviso.spot_fix.domain.enumeration.VoteType;
import com.diviso.spot_fix.repository.TicketVoteRepository;
import com.diviso.spot_fix.repository.UserRepository;
import com.diviso.spot_fix.service.TicketVoteService;
import com.diviso.spot_fix.service.dto.TicketVoteDTO;
import com.diviso.spot_fix.service.mapper.TicketVoteMapper;
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
 * Integration tests for the {@link TicketVoteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
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
    private MockMvc restTicketVoteMockMvc;

    private TicketVote ticketVote;

    private TicketVote insertedTicketVote;

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
        if (TestUtil.findAll(em, Ticket.class).isEmpty()) {
            ticket = TicketResourceIT.createEntity(em);
            em.persist(ticket);
            em.flush();
        } else {
            ticket = TestUtil.findAll(em, Ticket.class).get(0);
        }
        ticketVote.setTicket(ticket);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
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
        if (TestUtil.findAll(em, Ticket.class).isEmpty()) {
            ticket = TicketResourceIT.createUpdatedEntity(em);
            em.persist(ticket);
            em.flush();
        } else {
            ticket = TestUtil.findAll(em, Ticket.class).get(0);
        }
        updatedTicketVote.setTicket(ticket);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        updatedTicketVote.setUser(user);
        return updatedTicketVote;
    }

    @BeforeEach
    void initTest() {
        ticketVote = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedTicketVote != null) {
            ticketVoteRepository.delete(insertedTicketVote);
            insertedTicketVote = null;
        }
    }

    @Test
    @Transactional
    void createTicketVote() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TicketVote
        TicketVoteDTO ticketVoteDTO = ticketVoteMapper.toDto(ticketVote);
        var returnedTicketVoteDTO = om.readValue(
            restTicketVoteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketVoteDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TicketVoteDTO.class
        );

        // Validate the TicketVote in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTicketVote = ticketVoteMapper.toEntity(returnedTicketVoteDTO);
        assertTicketVoteUpdatableFieldsEquals(returnedTicketVote, getPersistedTicketVote(returnedTicketVote));

        insertedTicketVote = returnedTicketVote;
    }

    @Test
    @Transactional
    void createTicketVoteWithExistingId() throws Exception {
        // Create the TicketVote with an existing ID
        ticketVote.setId(1L);
        TicketVoteDTO ticketVoteDTO = ticketVoteMapper.toDto(ticketVote);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTicketVoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketVoteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TicketVote in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVoteTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticketVote.setVoteType(null);

        // Create the TicketVote, which fails.
        TicketVoteDTO ticketVoteDTO = ticketVoteMapper.toDto(ticketVote);

        restTicketVoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketVoteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ticketVote.setCreatedDate(null);

        // Create the TicketVote, which fails.
        TicketVoteDTO ticketVoteDTO = ticketVoteMapper.toDto(ticketVote);

        restTicketVoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketVoteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTicketVotes() throws Exception {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.saveAndFlush(ticketVote);

        // Get all the ticketVoteList
        restTicketVoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticketVote.getId().intValue())))
            .andExpect(jsonPath("$.[*].voteType").value(hasItem(DEFAULT_VOTE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTicketVotesWithEagerRelationshipsIsEnabled() throws Exception {
        when(ticketVoteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTicketVoteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(ticketVoteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTicketVotesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(ticketVoteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTicketVoteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(ticketVoteRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTicketVote() throws Exception {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.saveAndFlush(ticketVote);

        // Get the ticketVote
        restTicketVoteMockMvc
            .perform(get(ENTITY_API_URL_ID, ticketVote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ticketVote.getId().intValue()))
            .andExpect(jsonPath("$.voteType").value(DEFAULT_VOTE_TYPE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getTicketVotesByIdFiltering() throws Exception {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.saveAndFlush(ticketVote);

        Long id = ticketVote.getId();

        defaultTicketVoteFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultTicketVoteFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultTicketVoteFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTicketVotesByVoteTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.saveAndFlush(ticketVote);

        // Get all the ticketVoteList where voteType equals to
        defaultTicketVoteFiltering("voteType.equals=" + DEFAULT_VOTE_TYPE, "voteType.equals=" + UPDATED_VOTE_TYPE);
    }

    @Test
    @Transactional
    void getAllTicketVotesByVoteTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.saveAndFlush(ticketVote);

        // Get all the ticketVoteList where voteType in
        defaultTicketVoteFiltering("voteType.in=" + DEFAULT_VOTE_TYPE + "," + UPDATED_VOTE_TYPE, "voteType.in=" + UPDATED_VOTE_TYPE);
    }

    @Test
    @Transactional
    void getAllTicketVotesByVoteTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.saveAndFlush(ticketVote);

        // Get all the ticketVoteList where voteType is not null
        defaultTicketVoteFiltering("voteType.specified=true", "voteType.specified=false");
    }

    @Test
    @Transactional
    void getAllTicketVotesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.saveAndFlush(ticketVote);

        // Get all the ticketVoteList where createdDate equals to
        defaultTicketVoteFiltering("createdDate.equals=" + DEFAULT_CREATED_DATE, "createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllTicketVotesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.saveAndFlush(ticketVote);

        // Get all the ticketVoteList where createdDate in
        defaultTicketVoteFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllTicketVotesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.saveAndFlush(ticketVote);

        // Get all the ticketVoteList where createdDate is not null
        defaultTicketVoteFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTicketVotesByTicketIsEqualToSomething() throws Exception {
        Ticket ticket;
        if (TestUtil.findAll(em, Ticket.class).isEmpty()) {
            ticketVoteRepository.saveAndFlush(ticketVote);
            ticket = TicketResourceIT.createEntity(em);
        } else {
            ticket = TestUtil.findAll(em, Ticket.class).get(0);
        }
        em.persist(ticket);
        em.flush();
        ticketVote.setTicket(ticket);
        ticketVoteRepository.saveAndFlush(ticketVote);
        Long ticketId = ticket.getId();
        // Get all the ticketVoteList where ticket equals to ticketId
        defaultTicketVoteShouldBeFound("ticketId.equals=" + ticketId);

        // Get all the ticketVoteList where ticket equals to (ticketId + 1)
        defaultTicketVoteShouldNotBeFound("ticketId.equals=" + (ticketId + 1));
    }

    @Test
    @Transactional
    void getAllTicketVotesByUserIsEqualToSomething() throws Exception {
        User user;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            ticketVoteRepository.saveAndFlush(ticketVote);
            user = UserResourceIT.createEntity();
        } else {
            user = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(user);
        em.flush();
        ticketVote.setUser(user);
        ticketVoteRepository.saveAndFlush(ticketVote);
        Long userId = user.getId();
        // Get all the ticketVoteList where user equals to userId
        defaultTicketVoteShouldBeFound("userId.equals=" + userId);

        // Get all the ticketVoteList where user equals to (userId + 1)
        defaultTicketVoteShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    private void defaultTicketVoteFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultTicketVoteShouldBeFound(shouldBeFound);
        defaultTicketVoteShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTicketVoteShouldBeFound(String filter) throws Exception {
        restTicketVoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticketVote.getId().intValue())))
            .andExpect(jsonPath("$.[*].voteType").value(hasItem(DEFAULT_VOTE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restTicketVoteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTicketVoteShouldNotBeFound(String filter) throws Exception {
        restTicketVoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTicketVoteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTicketVote() throws Exception {
        // Get the ticketVote
        restTicketVoteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTicketVote() throws Exception {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.saveAndFlush(ticketVote);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ticketVote
        TicketVote updatedTicketVote = ticketVoteRepository.findById(ticketVote.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTicketVote are not directly saved in db
        em.detach(updatedTicketVote);
        updatedTicketVote.voteType(UPDATED_VOTE_TYPE).createdDate(UPDATED_CREATED_DATE);
        TicketVoteDTO ticketVoteDTO = ticketVoteMapper.toDto(updatedTicketVote);

        restTicketVoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ticketVoteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ticketVoteDTO))
            )
            .andExpect(status().isOk());

        // Validate the TicketVote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTicketVoteToMatchAllProperties(updatedTicketVote);
    }

    @Test
    @Transactional
    void putNonExistingTicketVote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketVote.setId(longCount.incrementAndGet());

        // Create the TicketVote
        TicketVoteDTO ticketVoteDTO = ticketVoteMapper.toDto(ticketVote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketVoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ticketVoteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ticketVoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketVote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTicketVote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketVote.setId(longCount.incrementAndGet());

        // Create the TicketVote
        TicketVoteDTO ticketVoteDTO = ticketVoteMapper.toDto(ticketVote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketVoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ticketVoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketVote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTicketVote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketVote.setId(longCount.incrementAndGet());

        // Create the TicketVote
        TicketVoteDTO ticketVoteDTO = ticketVoteMapper.toDto(ticketVote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketVoteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketVoteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TicketVote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTicketVoteWithPatch() throws Exception {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.saveAndFlush(ticketVote);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ticketVote using partial update
        TicketVote partialUpdatedTicketVote = new TicketVote();
        partialUpdatedTicketVote.setId(ticketVote.getId());

        partialUpdatedTicketVote.createdDate(UPDATED_CREATED_DATE);

        restTicketVoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTicketVote.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTicketVote))
            )
            .andExpect(status().isOk());

        // Validate the TicketVote in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTicketVoteUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTicketVote, ticketVote),
            getPersistedTicketVote(ticketVote)
        );
    }

    @Test
    @Transactional
    void fullUpdateTicketVoteWithPatch() throws Exception {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.saveAndFlush(ticketVote);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ticketVote using partial update
        TicketVote partialUpdatedTicketVote = new TicketVote();
        partialUpdatedTicketVote.setId(ticketVote.getId());

        partialUpdatedTicketVote.voteType(UPDATED_VOTE_TYPE).createdDate(UPDATED_CREATED_DATE);

        restTicketVoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTicketVote.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTicketVote))
            )
            .andExpect(status().isOk());

        // Validate the TicketVote in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTicketVoteUpdatableFieldsEquals(partialUpdatedTicketVote, getPersistedTicketVote(partialUpdatedTicketVote));
    }

    @Test
    @Transactional
    void patchNonExistingTicketVote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketVote.setId(longCount.incrementAndGet());

        // Create the TicketVote
        TicketVoteDTO ticketVoteDTO = ticketVoteMapper.toDto(ticketVote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketVoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ticketVoteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ticketVoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketVote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTicketVote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketVote.setId(longCount.incrementAndGet());

        // Create the TicketVote
        TicketVoteDTO ticketVoteDTO = ticketVoteMapper.toDto(ticketVote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketVoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ticketVoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketVote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTicketVote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketVote.setId(longCount.incrementAndGet());

        // Create the TicketVote
        TicketVoteDTO ticketVoteDTO = ticketVoteMapper.toDto(ticketVote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketVoteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ticketVoteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TicketVote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTicketVote() throws Exception {
        // Initialize the database
        insertedTicketVote = ticketVoteRepository.saveAndFlush(ticketVote);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ticketVote
        restTicketVoteMockMvc
            .perform(delete(ENTITY_API_URL_ID, ticketVote.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ticketVoteRepository.count();
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
        return ticketVoteRepository.findById(ticketVote.getId()).orElseThrow();
    }

    protected void assertPersistedTicketVoteToMatchAllProperties(TicketVote expectedTicketVote) {
        assertTicketVoteAllPropertiesEquals(expectedTicketVote, getPersistedTicketVote(expectedTicketVote));
    }

    protected void assertPersistedTicketVoteToMatchUpdatableProperties(TicketVote expectedTicketVote) {
        assertTicketVoteAllUpdatablePropertiesEquals(expectedTicketVote, getPersistedTicketVote(expectedTicketVote));
    }
}
