package com.divisosofttech.spot_fix.web.rest;

import static com.divisosofttech.spot_fix.domain.CommentAsserts.*;
import static com.divisosofttech.spot_fix.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import com.divisosofttech.spot_fix.IntegrationTest;
import com.divisosofttech.spot_fix.domain.Comment;
import com.divisosofttech.spot_fix.domain.Ticket;
import com.divisosofttech.spot_fix.domain.User;
import com.divisosofttech.spot_fix.repository.CommentRepository;
import com.divisosofttech.spot_fix.repository.EntityManager;
import com.divisosofttech.spot_fix.repository.TicketRepository;
import com.divisosofttech.spot_fix.repository.UserRepository;
import com.divisosofttech.spot_fix.repository.UserRepository;
import com.divisosofttech.spot_fix.service.CommentService;
import com.divisosofttech.spot_fix.service.dto.CommentDTO;
import com.divisosofttech.spot_fix.service.mapper.CommentMapper;
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
 * Integration tests for the {@link CommentResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class CommentResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Instant DEFAULT_DELETED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/comments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private CommentRepository commentRepositoryMock;

    @Autowired
    private CommentMapper commentMapper;

    @Mock
    private CommentService commentServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Comment comment;

    private Comment insertedComment;

    @Autowired
    private TicketRepository ticketRepository;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comment createEntity(EntityManager em) {
        Comment comment = new Comment()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .deleted(DEFAULT_DELETED)
            .deletedDate(DEFAULT_DELETED_DATE);
        // Add required entity
        Ticket ticket;
        ticket = em.insert(TicketResourceIT.createEntity(em)).block();
        comment.setTicket(ticket);
        // Add required entity
        User user = em.insert(UserResourceIT.createEntity()).block();
        comment.setUser(user);
        return comment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comment createUpdatedEntity(EntityManager em) {
        Comment updatedComment = new Comment()
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedDate(UPDATED_DELETED_DATE);
        // Add required entity
        Ticket ticket;
        ticket = em.insert(TicketResourceIT.createUpdatedEntity(em)).block();
        updatedComment.setTicket(ticket);
        // Add required entity
        User user = em.insert(UserResourceIT.createEntity()).block();
        updatedComment.setUser(user);
        return updatedComment;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Comment.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
        TicketResourceIT.deleteEntities(em);
        UserResourceIT.deleteEntities(em);
    }

    @BeforeEach
    void initTest() {
        comment = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedComment != null) {
            commentRepository.delete(insertedComment).block();
            insertedComment = null;
        }
        deleteEntities(em);
        userRepository.deleteAllUserAuthorities().block();
        userRepository.deleteAll().block();
    }

    @Test
    void createComment() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);
        var returnedCommentDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(commentDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(CommentDTO.class)
            .returnResult()
            .getResponseBody();

        // Validate the Comment in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedComment = commentMapper.toEntity(returnedCommentDTO);
        assertCommentUpdatableFieldsEquals(returnedComment, getPersistedComment(returnedComment));

        insertedComment = returnedComment;
    }

    @Test
    void createCommentWithExistingId() throws Exception {
        // Create the Comment with an existing ID
        comment.setId(1L);
        CommentDTO commentDTO = commentMapper.toDto(comment);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(commentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Comment in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkCreatedDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        comment.setCreatedDate(null);

        // Create the Comment, which fails.
        CommentDTO commentDTO = commentMapper.toDto(comment);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(commentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDeletedIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        comment.setDeleted(null);

        // Create the Comment, which fails.
        CommentDTO commentDTO = commentMapper.toDto(comment);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(commentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDeletedDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        comment.setDeletedDate(null);

        // Create the Comment, which fails.
        CommentDTO commentDTO = commentMapper.toDto(comment);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(commentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllComments() {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        // Get all the commentList
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
            .value(hasItem(comment.getId().intValue()))
            .jsonPath("$.[*].title")
            .value(hasItem(DEFAULT_TITLE))
            .jsonPath("$.[*].content")
            .value(hasItem(DEFAULT_CONTENT))
            .jsonPath("$.[*].createdDate")
            .value(hasItem(DEFAULT_CREATED_DATE.toString()))
            .jsonPath("$.[*].updatedDate")
            .value(hasItem(DEFAULT_UPDATED_DATE.toString()))
            .jsonPath("$.[*].deleted")
            .value(hasItem(DEFAULT_DELETED))
            .jsonPath("$.[*].deletedDate")
            .value(hasItem(DEFAULT_DELETED_DATE.toString()));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCommentsWithEagerRelationshipsIsEnabled() {
        when(commentServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=true").exchange().expectStatus().isOk();

        verify(commentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCommentsWithEagerRelationshipsIsNotEnabled() {
        when(commentServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=false").exchange().expectStatus().isOk();
        verify(commentRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    void getComment() {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        // Get the comment
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, comment.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(comment.getId().intValue()))
            .jsonPath("$.title")
            .value(is(DEFAULT_TITLE))
            .jsonPath("$.content")
            .value(is(DEFAULT_CONTENT))
            .jsonPath("$.createdDate")
            .value(is(DEFAULT_CREATED_DATE.toString()))
            .jsonPath("$.updatedDate")
            .value(is(DEFAULT_UPDATED_DATE.toString()))
            .jsonPath("$.deleted")
            .value(is(DEFAULT_DELETED))
            .jsonPath("$.deletedDate")
            .value(is(DEFAULT_DELETED_DATE.toString()));
    }

    @Test
    void getCommentsByIdFiltering() {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        Long id = comment.getId();

        defaultCommentFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCommentFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCommentFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    void getAllCommentsByTitleIsEqualToSomething() {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        // Get all the commentList where title equals to
        defaultCommentFiltering("title.equals=" + DEFAULT_TITLE, "title.equals=" + UPDATED_TITLE);
    }

    @Test
    void getAllCommentsByTitleIsInShouldWork() {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        // Get all the commentList where title in
        defaultCommentFiltering("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE, "title.in=" + UPDATED_TITLE);
    }

    @Test
    void getAllCommentsByTitleIsNullOrNotNull() {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        // Get all the commentList where title is not null
        defaultCommentFiltering("title.specified=true", "title.specified=false");
    }

    @Test
    void getAllCommentsByTitleContainsSomething() {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        // Get all the commentList where title contains
        defaultCommentFiltering("title.contains=" + DEFAULT_TITLE, "title.contains=" + UPDATED_TITLE);
    }

    @Test
    void getAllCommentsByTitleNotContainsSomething() {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        // Get all the commentList where title does not contain
        defaultCommentFiltering("title.doesNotContain=" + UPDATED_TITLE, "title.doesNotContain=" + DEFAULT_TITLE);
    }

    @Test
    void getAllCommentsByCreatedDateIsEqualToSomething() {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        // Get all the commentList where createdDate equals to
        defaultCommentFiltering("createdDate.equals=" + DEFAULT_CREATED_DATE, "createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    void getAllCommentsByCreatedDateIsInShouldWork() {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        // Get all the commentList where createdDate in
        defaultCommentFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    void getAllCommentsByCreatedDateIsNullOrNotNull() {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        // Get all the commentList where createdDate is not null
        defaultCommentFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    void getAllCommentsByUpdatedDateIsEqualToSomething() {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        // Get all the commentList where updatedDate equals to
        defaultCommentFiltering("updatedDate.equals=" + DEFAULT_UPDATED_DATE, "updatedDate.equals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    void getAllCommentsByUpdatedDateIsInShouldWork() {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        // Get all the commentList where updatedDate in
        defaultCommentFiltering(
            "updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE,
            "updatedDate.in=" + UPDATED_UPDATED_DATE
        );
    }

    @Test
    void getAllCommentsByUpdatedDateIsNullOrNotNull() {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        // Get all the commentList where updatedDate is not null
        defaultCommentFiltering("updatedDate.specified=true", "updatedDate.specified=false");
    }

    @Test
    void getAllCommentsByDeletedIsEqualToSomething() {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        // Get all the commentList where deleted equals to
        defaultCommentFiltering("deleted.equals=" + DEFAULT_DELETED, "deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    void getAllCommentsByDeletedIsInShouldWork() {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        // Get all the commentList where deleted in
        defaultCommentFiltering("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED, "deleted.in=" + UPDATED_DELETED);
    }

    @Test
    void getAllCommentsByDeletedIsNullOrNotNull() {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        // Get all the commentList where deleted is not null
        defaultCommentFiltering("deleted.specified=true", "deleted.specified=false");
    }

    @Test
    void getAllCommentsByDeletedDateIsEqualToSomething() {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        // Get all the commentList where deletedDate equals to
        defaultCommentFiltering("deletedDate.equals=" + DEFAULT_DELETED_DATE, "deletedDate.equals=" + UPDATED_DELETED_DATE);
    }

    @Test
    void getAllCommentsByDeletedDateIsInShouldWork() {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        // Get all the commentList where deletedDate in
        defaultCommentFiltering(
            "deletedDate.in=" + DEFAULT_DELETED_DATE + "," + UPDATED_DELETED_DATE,
            "deletedDate.in=" + UPDATED_DELETED_DATE
        );
    }

    @Test
    void getAllCommentsByDeletedDateIsNullOrNotNull() {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        // Get all the commentList where deletedDate is not null
        defaultCommentFiltering("deletedDate.specified=true", "deletedDate.specified=false");
    }

    @Test
    void getAllCommentsByTicketIsEqualToSomething() {
        Ticket ticket = TicketResourceIT.createEntity(em);
        ticketRepository.save(ticket).block();
        Long ticketId = ticket.getId();
        comment.setTicketId(ticketId);
        insertedComment = commentRepository.save(comment).block();
        // Get all the commentList where ticket equals to ticketId
        defaultCommentShouldBeFound("ticketId.equals=" + ticketId);

        // Get all the commentList where ticket equals to (ticketId + 1)
        defaultCommentShouldNotBeFound("ticketId.equals=" + (ticketId + 1));
    }

    @Test
    void getAllCommentsByUserIsEqualToSomething() {
        User user = UserResourceIT.createEntity();
        userRepository.save(user).block();
        Long userId = user.getId();
        comment.setUserId(userId);
        insertedComment = commentRepository.save(comment).block();
        // Get all the commentList where user equals to userId
        defaultCommentShouldBeFound("userId.equals=" + userId);

        // Get all the commentList where user equals to (userId + 1)
        defaultCommentShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    private void defaultCommentFiltering(String shouldBeFound, String shouldNotBeFound) {
        defaultCommentShouldBeFound(shouldBeFound);
        defaultCommentShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCommentShouldBeFound(String filter) {
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
            .value(hasItem(comment.getId().intValue()))
            .jsonPath("$.[*].title")
            .value(hasItem(DEFAULT_TITLE))
            .jsonPath("$.[*].content")
            .value(hasItem(DEFAULT_CONTENT))
            .jsonPath("$.[*].createdDate")
            .value(hasItem(DEFAULT_CREATED_DATE.toString()))
            .jsonPath("$.[*].updatedDate")
            .value(hasItem(DEFAULT_UPDATED_DATE.toString()))
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
    private void defaultCommentShouldNotBeFound(String filter) {
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
    void getNonExistingComment() {
        // Get the comment
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingComment() throws Exception {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the comment
        Comment updatedComment = commentRepository.findById(comment.getId()).block();
        updatedComment
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedDate(UPDATED_DELETED_DATE);
        CommentDTO commentDTO = commentMapper.toDto(updatedComment);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, commentDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(commentDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Comment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCommentToMatchAllProperties(updatedComment);
    }

    @Test
    void putNonExistingComment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        comment.setId(longCount.incrementAndGet());

        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, commentDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(commentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Comment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchComment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        comment.setId(longCount.incrementAndGet());

        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(commentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Comment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamComment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        comment.setId(longCount.incrementAndGet());

        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(commentDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Comment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCommentWithPatch() throws Exception {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the comment using partial update
        Comment partialUpdatedComment = new Comment();
        partialUpdatedComment.setId(comment.getId());

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedComment.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedComment))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Comment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCommentUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedComment, comment), getPersistedComment(comment));
    }

    @Test
    void fullUpdateCommentWithPatch() throws Exception {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the comment using partial update
        Comment partialUpdatedComment = new Comment();
        partialUpdatedComment.setId(comment.getId());

        partialUpdatedComment
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedDate(UPDATED_DELETED_DATE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedComment.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedComment))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Comment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCommentUpdatableFieldsEquals(partialUpdatedComment, getPersistedComment(partialUpdatedComment));
    }

    @Test
    void patchNonExistingComment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        comment.setId(longCount.incrementAndGet());

        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, commentDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(commentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Comment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchComment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        comment.setId(longCount.incrementAndGet());

        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(commentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Comment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamComment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        comment.setId(longCount.incrementAndGet());

        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(commentDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Comment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteComment() {
        // Initialize the database
        insertedComment = commentRepository.save(comment).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the comment
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, comment.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return commentRepository.count().block();
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

    protected Comment getPersistedComment(Comment comment) {
        return commentRepository.findById(comment.getId()).block();
    }

    protected void assertPersistedCommentToMatchAllProperties(Comment expectedComment) {
        // Test fails because reactive api returns an empty object instead of null
        // assertCommentAllPropertiesEquals(expectedComment, getPersistedComment(expectedComment));
        assertCommentUpdatableFieldsEquals(expectedComment, getPersistedComment(expectedComment));
    }

    protected void assertPersistedCommentToMatchUpdatableProperties(Comment expectedComment) {
        // Test fails because reactive api returns an empty object instead of null
        // assertCommentAllUpdatablePropertiesEquals(expectedComment, getPersistedComment(expectedComment));
        assertCommentUpdatableFieldsEquals(expectedComment, getPersistedComment(expectedComment));
    }
}
