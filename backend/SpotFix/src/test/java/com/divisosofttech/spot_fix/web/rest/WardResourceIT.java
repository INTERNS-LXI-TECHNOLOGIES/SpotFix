package com.divisosofttech.spot_fix.web.rest;

import static com.divisosofttech.spot_fix.domain.WardAsserts.*;
import static com.divisosofttech.spot_fix.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.divisosofttech.spot_fix.IntegrationTest;
import com.divisosofttech.spot_fix.domain.Ward;
import com.divisosofttech.spot_fix.repository.EntityManager;
import com.divisosofttech.spot_fix.repository.WardRepository;
import com.divisosofttech.spot_fix.service.dto.WardDTO;
import com.divisosofttech.spot_fix.service.mapper.WardMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
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
 * Integration tests for the {@link WardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class WardResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MUNICIPALITY = "AAAAAAAAAA";
    private static final String UPDATED_MUNICIPALITY = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/wards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WardRepository wardRepository;

    @Autowired
    private WardMapper wardMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Ward ward;

    private Ward insertedWard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ward createEntity() {
        return new Ward().code(DEFAULT_CODE).name(DEFAULT_NAME).municipality(DEFAULT_MUNICIPALITY).description(DEFAULT_DESCRIPTION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ward createUpdatedEntity() {
        return new Ward().code(UPDATED_CODE).name(UPDATED_NAME).municipality(UPDATED_MUNICIPALITY).description(UPDATED_DESCRIPTION);
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Ward.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @BeforeEach
    void initTest() {
        ward = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedWard != null) {
            wardRepository.delete(insertedWard).block();
            insertedWard = null;
        }
        deleteEntities(em);
    }

    @Test
    void createWard() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ward
        WardDTO wardDTO = wardMapper.toDto(ward);
        var returnedWardDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(wardDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(WardDTO.class)
            .returnResult()
            .getResponseBody();

        // Validate the Ward in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedWard = wardMapper.toEntity(returnedWardDTO);
        assertWardUpdatableFieldsEquals(returnedWard, getPersistedWard(returnedWard));

        insertedWard = returnedWard;
    }

    @Test
    void createWardWithExistingId() throws Exception {
        // Create the Ward with an existing ID
        ward.setId(1L);
        WardDTO wardDTO = wardMapper.toDto(ward);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(wardDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Ward in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ward.setCode(null);

        // Create the Ward, which fails.
        WardDTO wardDTO = wardMapper.toDto(ward);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(wardDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ward.setName(null);

        // Create the Ward, which fails.
        WardDTO wardDTO = wardMapper.toDto(ward);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(wardDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllWards() {
        // Initialize the database
        insertedWard = wardRepository.save(ward).block();

        // Get all the wardList
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
            .value(hasItem(ward.getId().intValue()))
            .jsonPath("$.[*].code")
            .value(hasItem(DEFAULT_CODE))
            .jsonPath("$.[*].name")
            .value(hasItem(DEFAULT_NAME))
            .jsonPath("$.[*].municipality")
            .value(hasItem(DEFAULT_MUNICIPALITY))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION));
    }

    @Test
    void getWard() {
        // Initialize the database
        insertedWard = wardRepository.save(ward).block();

        // Get the ward
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, ward.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(ward.getId().intValue()))
            .jsonPath("$.code")
            .value(is(DEFAULT_CODE))
            .jsonPath("$.name")
            .value(is(DEFAULT_NAME))
            .jsonPath("$.municipality")
            .value(is(DEFAULT_MUNICIPALITY))
            .jsonPath("$.description")
            .value(is(DEFAULT_DESCRIPTION));
    }

    @Test
    void getWardsByIdFiltering() {
        // Initialize the database
        insertedWard = wardRepository.save(ward).block();

        Long id = ward.getId();

        defaultWardFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultWardFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultWardFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    void getAllWardsByCodeIsEqualToSomething() {
        // Initialize the database
        insertedWard = wardRepository.save(ward).block();

        // Get all the wardList where code equals to
        defaultWardFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    void getAllWardsByCodeIsInShouldWork() {
        // Initialize the database
        insertedWard = wardRepository.save(ward).block();

        // Get all the wardList where code in
        defaultWardFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    void getAllWardsByCodeIsNullOrNotNull() {
        // Initialize the database
        insertedWard = wardRepository.save(ward).block();

        // Get all the wardList where code is not null
        defaultWardFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    void getAllWardsByCodeContainsSomething() {
        // Initialize the database
        insertedWard = wardRepository.save(ward).block();

        // Get all the wardList where code contains
        defaultWardFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    void getAllWardsByCodeNotContainsSomething() {
        // Initialize the database
        insertedWard = wardRepository.save(ward).block();

        // Get all the wardList where code does not contain
        defaultWardFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    void getAllWardsByNameIsEqualToSomething() {
        // Initialize the database
        insertedWard = wardRepository.save(ward).block();

        // Get all the wardList where name equals to
        defaultWardFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    void getAllWardsByNameIsInShouldWork() {
        // Initialize the database
        insertedWard = wardRepository.save(ward).block();

        // Get all the wardList where name in
        defaultWardFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    void getAllWardsByNameIsNullOrNotNull() {
        // Initialize the database
        insertedWard = wardRepository.save(ward).block();

        // Get all the wardList where name is not null
        defaultWardFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    void getAllWardsByNameContainsSomething() {
        // Initialize the database
        insertedWard = wardRepository.save(ward).block();

        // Get all the wardList where name contains
        defaultWardFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    void getAllWardsByNameNotContainsSomething() {
        // Initialize the database
        insertedWard = wardRepository.save(ward).block();

        // Get all the wardList where name does not contain
        defaultWardFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    void getAllWardsByMunicipalityIsEqualToSomething() {
        // Initialize the database
        insertedWard = wardRepository.save(ward).block();

        // Get all the wardList where municipality equals to
        defaultWardFiltering("municipality.equals=" + DEFAULT_MUNICIPALITY, "municipality.equals=" + UPDATED_MUNICIPALITY);
    }

    @Test
    void getAllWardsByMunicipalityIsInShouldWork() {
        // Initialize the database
        insertedWard = wardRepository.save(ward).block();

        // Get all the wardList where municipality in
        defaultWardFiltering(
            "municipality.in=" + DEFAULT_MUNICIPALITY + "," + UPDATED_MUNICIPALITY,
            "municipality.in=" + UPDATED_MUNICIPALITY
        );
    }

    @Test
    void getAllWardsByMunicipalityIsNullOrNotNull() {
        // Initialize the database
        insertedWard = wardRepository.save(ward).block();

        // Get all the wardList where municipality is not null
        defaultWardFiltering("municipality.specified=true", "municipality.specified=false");
    }

    @Test
    void getAllWardsByMunicipalityContainsSomething() {
        // Initialize the database
        insertedWard = wardRepository.save(ward).block();

        // Get all the wardList where municipality contains
        defaultWardFiltering("municipality.contains=" + DEFAULT_MUNICIPALITY, "municipality.contains=" + UPDATED_MUNICIPALITY);
    }

    @Test
    void getAllWardsByMunicipalityNotContainsSomething() {
        // Initialize the database
        insertedWard = wardRepository.save(ward).block();

        // Get all the wardList where municipality does not contain
        defaultWardFiltering("municipality.doesNotContain=" + UPDATED_MUNICIPALITY, "municipality.doesNotContain=" + DEFAULT_MUNICIPALITY);
    }

    private void defaultWardFiltering(String shouldBeFound, String shouldNotBeFound) {
        defaultWardShouldBeFound(shouldBeFound);
        defaultWardShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWardShouldBeFound(String filter) {
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
            .value(hasItem(ward.getId().intValue()))
            .jsonPath("$.[*].code")
            .value(hasItem(DEFAULT_CODE))
            .jsonPath("$.[*].name")
            .value(hasItem(DEFAULT_NAME))
            .jsonPath("$.[*].municipality")
            .value(hasItem(DEFAULT_MUNICIPALITY))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION));

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
    private void defaultWardShouldNotBeFound(String filter) {
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
    void getNonExistingWard() {
        // Get the ward
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingWard() throws Exception {
        // Initialize the database
        insertedWard = wardRepository.save(ward).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ward
        Ward updatedWard = wardRepository.findById(ward.getId()).block();
        updatedWard.code(UPDATED_CODE).name(UPDATED_NAME).municipality(UPDATED_MUNICIPALITY).description(UPDATED_DESCRIPTION);
        WardDTO wardDTO = wardMapper.toDto(updatedWard);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, wardDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(wardDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Ward in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWardToMatchAllProperties(updatedWard);
    }

    @Test
    void putNonExistingWard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ward.setId(longCount.incrementAndGet());

        // Create the Ward
        WardDTO wardDTO = wardMapper.toDto(ward);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, wardDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(wardDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Ward in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchWard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ward.setId(longCount.incrementAndGet());

        // Create the Ward
        WardDTO wardDTO = wardMapper.toDto(ward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(wardDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Ward in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamWard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ward.setId(longCount.incrementAndGet());

        // Create the Ward
        WardDTO wardDTO = wardMapper.toDto(ward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(wardDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Ward in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateWardWithPatch() throws Exception {
        // Initialize the database
        insertedWard = wardRepository.save(ward).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ward using partial update
        Ward partialUpdatedWard = new Ward();
        partialUpdatedWard.setId(ward.getId());

        partialUpdatedWard.code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedWard.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedWard))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Ward in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWardUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedWard, ward), getPersistedWard(ward));
    }

    @Test
    void fullUpdateWardWithPatch() throws Exception {
        // Initialize the database
        insertedWard = wardRepository.save(ward).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ward using partial update
        Ward partialUpdatedWard = new Ward();
        partialUpdatedWard.setId(ward.getId());

        partialUpdatedWard.code(UPDATED_CODE).name(UPDATED_NAME).municipality(UPDATED_MUNICIPALITY).description(UPDATED_DESCRIPTION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedWard.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedWard))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Ward in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWardUpdatableFieldsEquals(partialUpdatedWard, getPersistedWard(partialUpdatedWard));
    }

    @Test
    void patchNonExistingWard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ward.setId(longCount.incrementAndGet());

        // Create the Ward
        WardDTO wardDTO = wardMapper.toDto(ward);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, wardDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(wardDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Ward in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchWard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ward.setId(longCount.incrementAndGet());

        // Create the Ward
        WardDTO wardDTO = wardMapper.toDto(ward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(wardDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Ward in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamWard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ward.setId(longCount.incrementAndGet());

        // Create the Ward
        WardDTO wardDTO = wardMapper.toDto(ward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(wardDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Ward in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteWard() {
        // Initialize the database
        insertedWard = wardRepository.save(ward).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ward
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, ward.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return wardRepository.count().block();
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

    protected Ward getPersistedWard(Ward ward) {
        return wardRepository.findById(ward.getId()).block();
    }

    protected void assertPersistedWardToMatchAllProperties(Ward expectedWard) {
        // Test fails because reactive api returns an empty object instead of null
        // assertWardAllPropertiesEquals(expectedWard, getPersistedWard(expectedWard));
        assertWardUpdatableFieldsEquals(expectedWard, getPersistedWard(expectedWard));
    }

    protected void assertPersistedWardToMatchUpdatableProperties(Ward expectedWard) {
        // Test fails because reactive api returns an empty object instead of null
        // assertWardAllUpdatablePropertiesEquals(expectedWard, getPersistedWard(expectedWard));
        assertWardUpdatableFieldsEquals(expectedWard, getPersistedWard(expectedWard));
    }
}
