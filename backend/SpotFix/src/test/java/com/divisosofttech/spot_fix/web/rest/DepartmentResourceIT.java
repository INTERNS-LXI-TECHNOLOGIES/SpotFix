package com.divisosofttech.spot_fix.web.rest;

import static com.divisosofttech.spot_fix.domain.DepartmentAsserts.*;
import static com.divisosofttech.spot_fix.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.divisosofttech.spot_fix.IntegrationTest;
import com.divisosofttech.spot_fix.domain.Department;
import com.divisosofttech.spot_fix.repository.DepartmentRepository;
import com.divisosofttech.spot_fix.repository.EntityManager;
import com.divisosofttech.spot_fix.service.dto.DepartmentDTO;
import com.divisosofttech.spot_fix.service.mapper.DepartmentMapper;
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
 * Integration tests for the {@link DepartmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class DepartmentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PHONE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/departments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Department department;

    private Department insertedDepartment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Department createEntity() {
        return new Department()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .contactEmail(DEFAULT_CONTACT_EMAIL)
            .contactPhone(DEFAULT_CONTACT_PHONE)
            .active(DEFAULT_ACTIVE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Department createUpdatedEntity() {
        return new Department()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .contactPhone(UPDATED_CONTACT_PHONE)
            .active(UPDATED_ACTIVE);
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Department.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @BeforeEach
    void initTest() {
        department = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedDepartment != null) {
            departmentRepository.delete(insertedDepartment).block();
            insertedDepartment = null;
        }
        deleteEntities(em);
    }

    @Test
    void createDepartment() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);
        var returnedDepartmentDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(departmentDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(DepartmentDTO.class)
            .returnResult()
            .getResponseBody();

        // Validate the Department in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDepartment = departmentMapper.toEntity(returnedDepartmentDTO);
        assertDepartmentUpdatableFieldsEquals(returnedDepartment, getPersistedDepartment(returnedDepartment));

        insertedDepartment = returnedDepartment;
    }

    @Test
    void createDepartmentWithExistingId() throws Exception {
        // Create the Department with an existing ID
        department.setId(1L);
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(departmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        department.setName(null);

        // Create the Department, which fails.
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(departmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkActiveIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        department.setActive(null);

        // Create the Department, which fails.
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(departmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllDepartments() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        // Get all the departmentList
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
            .value(hasItem(department.getId().intValue()))
            .jsonPath("$.[*].name")
            .value(hasItem(DEFAULT_NAME))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION))
            .jsonPath("$.[*].contactEmail")
            .value(hasItem(DEFAULT_CONTACT_EMAIL))
            .jsonPath("$.[*].contactPhone")
            .value(hasItem(DEFAULT_CONTACT_PHONE))
            .jsonPath("$.[*].active")
            .value(hasItem(DEFAULT_ACTIVE));
    }

    @Test
    void getDepartment() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        // Get the department
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, department.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(department.getId().intValue()))
            .jsonPath("$.name")
            .value(is(DEFAULT_NAME))
            .jsonPath("$.description")
            .value(is(DEFAULT_DESCRIPTION))
            .jsonPath("$.contactEmail")
            .value(is(DEFAULT_CONTACT_EMAIL))
            .jsonPath("$.contactPhone")
            .value(is(DEFAULT_CONTACT_PHONE))
            .jsonPath("$.active")
            .value(is(DEFAULT_ACTIVE));
    }

    @Test
    void getDepartmentsByIdFiltering() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        Long id = department.getId();

        defaultDepartmentFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultDepartmentFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultDepartmentFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    void getAllDepartmentsByNameIsEqualToSomething() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        // Get all the departmentList where name equals to
        defaultDepartmentFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    void getAllDepartmentsByNameIsInShouldWork() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        // Get all the departmentList where name in
        defaultDepartmentFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    void getAllDepartmentsByNameIsNullOrNotNull() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        // Get all the departmentList where name is not null
        defaultDepartmentFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    void getAllDepartmentsByNameContainsSomething() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        // Get all the departmentList where name contains
        defaultDepartmentFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    void getAllDepartmentsByNameNotContainsSomething() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        // Get all the departmentList where name does not contain
        defaultDepartmentFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    void getAllDepartmentsByContactEmailIsEqualToSomething() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        // Get all the departmentList where contactEmail equals to
        defaultDepartmentFiltering("contactEmail.equals=" + DEFAULT_CONTACT_EMAIL, "contactEmail.equals=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    void getAllDepartmentsByContactEmailIsInShouldWork() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        // Get all the departmentList where contactEmail in
        defaultDepartmentFiltering(
            "contactEmail.in=" + DEFAULT_CONTACT_EMAIL + "," + UPDATED_CONTACT_EMAIL,
            "contactEmail.in=" + UPDATED_CONTACT_EMAIL
        );
    }

    @Test
    void getAllDepartmentsByContactEmailIsNullOrNotNull() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        // Get all the departmentList where contactEmail is not null
        defaultDepartmentFiltering("contactEmail.specified=true", "contactEmail.specified=false");
    }

    @Test
    void getAllDepartmentsByContactEmailContainsSomething() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        // Get all the departmentList where contactEmail contains
        defaultDepartmentFiltering("contactEmail.contains=" + DEFAULT_CONTACT_EMAIL, "contactEmail.contains=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    void getAllDepartmentsByContactEmailNotContainsSomething() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        // Get all the departmentList where contactEmail does not contain
        defaultDepartmentFiltering(
            "contactEmail.doesNotContain=" + UPDATED_CONTACT_EMAIL,
            "contactEmail.doesNotContain=" + DEFAULT_CONTACT_EMAIL
        );
    }

    @Test
    void getAllDepartmentsByContactPhoneIsEqualToSomething() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        // Get all the departmentList where contactPhone equals to
        defaultDepartmentFiltering("contactPhone.equals=" + DEFAULT_CONTACT_PHONE, "contactPhone.equals=" + UPDATED_CONTACT_PHONE);
    }

    @Test
    void getAllDepartmentsByContactPhoneIsInShouldWork() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        // Get all the departmentList where contactPhone in
        defaultDepartmentFiltering(
            "contactPhone.in=" + DEFAULT_CONTACT_PHONE + "," + UPDATED_CONTACT_PHONE,
            "contactPhone.in=" + UPDATED_CONTACT_PHONE
        );
    }

    @Test
    void getAllDepartmentsByContactPhoneIsNullOrNotNull() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        // Get all the departmentList where contactPhone is not null
        defaultDepartmentFiltering("contactPhone.specified=true", "contactPhone.specified=false");
    }

    @Test
    void getAllDepartmentsByContactPhoneContainsSomething() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        // Get all the departmentList where contactPhone contains
        defaultDepartmentFiltering("contactPhone.contains=" + DEFAULT_CONTACT_PHONE, "contactPhone.contains=" + UPDATED_CONTACT_PHONE);
    }

    @Test
    void getAllDepartmentsByContactPhoneNotContainsSomething() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        // Get all the departmentList where contactPhone does not contain
        defaultDepartmentFiltering(
            "contactPhone.doesNotContain=" + UPDATED_CONTACT_PHONE,
            "contactPhone.doesNotContain=" + DEFAULT_CONTACT_PHONE
        );
    }

    @Test
    void getAllDepartmentsByActiveIsEqualToSomething() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        // Get all the departmentList where active equals to
        defaultDepartmentFiltering("active.equals=" + DEFAULT_ACTIVE, "active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    void getAllDepartmentsByActiveIsInShouldWork() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        // Get all the departmentList where active in
        defaultDepartmentFiltering("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE, "active.in=" + UPDATED_ACTIVE);
    }

    @Test
    void getAllDepartmentsByActiveIsNullOrNotNull() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        // Get all the departmentList where active is not null
        defaultDepartmentFiltering("active.specified=true", "active.specified=false");
    }

    private void defaultDepartmentFiltering(String shouldBeFound, String shouldNotBeFound) {
        defaultDepartmentShouldBeFound(shouldBeFound);
        defaultDepartmentShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDepartmentShouldBeFound(String filter) {
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
            .value(hasItem(department.getId().intValue()))
            .jsonPath("$.[*].name")
            .value(hasItem(DEFAULT_NAME))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION))
            .jsonPath("$.[*].contactEmail")
            .value(hasItem(DEFAULT_CONTACT_EMAIL))
            .jsonPath("$.[*].contactPhone")
            .value(hasItem(DEFAULT_CONTACT_PHONE))
            .jsonPath("$.[*].active")
            .value(hasItem(DEFAULT_ACTIVE));

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
    private void defaultDepartmentShouldNotBeFound(String filter) {
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
    void getNonExistingDepartment() {
        // Get the department
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingDepartment() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the department
        Department updatedDepartment = departmentRepository.findById(department.getId()).block();
        updatedDepartment
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .contactPhone(UPDATED_CONTACT_PHONE)
            .active(UPDATED_ACTIVE);
        DepartmentDTO departmentDTO = departmentMapper.toDto(updatedDepartment);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, departmentDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(departmentDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDepartmentToMatchAllProperties(updatedDepartment);
    }

    @Test
    void putNonExistingDepartment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        department.setId(longCount.incrementAndGet());

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, departmentDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(departmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDepartment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        department.setId(longCount.incrementAndGet());

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(departmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDepartment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        department.setId(longCount.incrementAndGet());

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(departmentDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDepartmentWithPatch() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the department using partial update
        Department partialUpdatedDepartment = new Department();
        partialUpdatedDepartment.setId(department.getId());

        partialUpdatedDepartment.description(UPDATED_DESCRIPTION).contactEmail(UPDATED_CONTACT_EMAIL);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDepartment.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedDepartment))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Department in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDepartmentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDepartment, department),
            getPersistedDepartment(department)
        );
    }

    @Test
    void fullUpdateDepartmentWithPatch() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the department using partial update
        Department partialUpdatedDepartment = new Department();
        partialUpdatedDepartment.setId(department.getId());

        partialUpdatedDepartment
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .contactPhone(UPDATED_CONTACT_PHONE)
            .active(UPDATED_ACTIVE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDepartment.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedDepartment))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Department in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDepartmentUpdatableFieldsEquals(partialUpdatedDepartment, getPersistedDepartment(partialUpdatedDepartment));
    }

    @Test
    void patchNonExistingDepartment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        department.setId(longCount.incrementAndGet());

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, departmentDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(departmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDepartment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        department.setId(longCount.incrementAndGet());

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(departmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDepartment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        department.setId(longCount.incrementAndGet());

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(departmentDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDepartment() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the department
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, department.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return departmentRepository.count().block();
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

    protected Department getPersistedDepartment(Department department) {
        return departmentRepository.findById(department.getId()).block();
    }

    protected void assertPersistedDepartmentToMatchAllProperties(Department expectedDepartment) {
        // Test fails because reactive api returns an empty object instead of null
        // assertDepartmentAllPropertiesEquals(expectedDepartment, getPersistedDepartment(expectedDepartment));
        assertDepartmentUpdatableFieldsEquals(expectedDepartment, getPersistedDepartment(expectedDepartment));
    }

    protected void assertPersistedDepartmentToMatchUpdatableProperties(Department expectedDepartment) {
        // Test fails because reactive api returns an empty object instead of null
        // assertDepartmentAllUpdatablePropertiesEquals(expectedDepartment, getPersistedDepartment(expectedDepartment));
        assertDepartmentUpdatableFieldsEquals(expectedDepartment, getPersistedDepartment(expectedDepartment));
    }
}
