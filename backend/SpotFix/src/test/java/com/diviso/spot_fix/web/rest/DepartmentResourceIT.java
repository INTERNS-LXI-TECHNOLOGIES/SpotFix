package com.diviso.spot_fix.web.rest;

import static com.diviso.spot_fix.domain.DepartmentAsserts.*;
import static com.diviso.spot_fix.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.diviso.spot_fix.IntegrationTest;
import com.diviso.spot_fix.domain.Department;
import com.diviso.spot_fix.repository.DepartmentRepository;
import com.diviso.spot_fix.service.dto.DepartmentDTO;
import com.diviso.spot_fix.service.mapper.DepartmentMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link DepartmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
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
    private MockMvc restDepartmentMockMvc;

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

    @BeforeEach
    void initTest() {
        department = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedDepartment != null) {
            departmentRepository.delete(insertedDepartment);
            insertedDepartment = null;
        }
    }

    @Test
    @Transactional
    void createDepartment() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);
        var returnedDepartmentDTO = om.readValue(
            restDepartmentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(departmentDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DepartmentDTO.class
        );

        // Validate the Department in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDepartment = departmentMapper.toEntity(returnedDepartmentDTO);
        assertDepartmentUpdatableFieldsEquals(returnedDepartment, getPersistedDepartment(returnedDepartment));

        insertedDepartment = returnedDepartment;
    }

    @Test
    @Transactional
    void createDepartmentWithExistingId() throws Exception {
        // Create the Department with an existing ID
        department.setId(1L);
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(departmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        department.setName(null);

        // Create the Department, which fails.
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        restDepartmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(departmentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        department.setActive(null);

        // Create the Department, which fails.
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        restDepartmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(departmentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDepartments() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        // Get all the departmentList
        restDepartmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(department.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL)))
            .andExpect(jsonPath("$.[*].contactPhone").value(hasItem(DEFAULT_CONTACT_PHONE)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)));
    }

    @Test
    @Transactional
    void getDepartment() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        // Get the department
        restDepartmentMockMvc
            .perform(get(ENTITY_API_URL_ID, department.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(department.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.contactEmail").value(DEFAULT_CONTACT_EMAIL))
            .andExpect(jsonPath("$.contactPhone").value(DEFAULT_CONTACT_PHONE))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE));
    }

    @Test
    @Transactional
    void getDepartmentsByIdFiltering() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        Long id = department.getId();

        defaultDepartmentFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultDepartmentFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultDepartmentFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDepartmentsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        // Get all the departmentList where name equals to
        defaultDepartmentFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDepartmentsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        // Get all the departmentList where name in
        defaultDepartmentFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDepartmentsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        // Get all the departmentList where name is not null
        defaultDepartmentFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllDepartmentsByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        // Get all the departmentList where name contains
        defaultDepartmentFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDepartmentsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        // Get all the departmentList where name does not contain
        defaultDepartmentFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllDepartmentsByContactEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        // Get all the departmentList where contactEmail equals to
        defaultDepartmentFiltering("contactEmail.equals=" + DEFAULT_CONTACT_EMAIL, "contactEmail.equals=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void getAllDepartmentsByContactEmailIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        // Get all the departmentList where contactEmail in
        defaultDepartmentFiltering(
            "contactEmail.in=" + DEFAULT_CONTACT_EMAIL + "," + UPDATED_CONTACT_EMAIL,
            "contactEmail.in=" + UPDATED_CONTACT_EMAIL
        );
    }

    @Test
    @Transactional
    void getAllDepartmentsByContactEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        // Get all the departmentList where contactEmail is not null
        defaultDepartmentFiltering("contactEmail.specified=true", "contactEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllDepartmentsByContactEmailContainsSomething() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        // Get all the departmentList where contactEmail contains
        defaultDepartmentFiltering("contactEmail.contains=" + DEFAULT_CONTACT_EMAIL, "contactEmail.contains=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void getAllDepartmentsByContactEmailNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        // Get all the departmentList where contactEmail does not contain
        defaultDepartmentFiltering(
            "contactEmail.doesNotContain=" + UPDATED_CONTACT_EMAIL,
            "contactEmail.doesNotContain=" + DEFAULT_CONTACT_EMAIL
        );
    }

    @Test
    @Transactional
    void getAllDepartmentsByContactPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        // Get all the departmentList where contactPhone equals to
        defaultDepartmentFiltering("contactPhone.equals=" + DEFAULT_CONTACT_PHONE, "contactPhone.equals=" + UPDATED_CONTACT_PHONE);
    }

    @Test
    @Transactional
    void getAllDepartmentsByContactPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        // Get all the departmentList where contactPhone in
        defaultDepartmentFiltering(
            "contactPhone.in=" + DEFAULT_CONTACT_PHONE + "," + UPDATED_CONTACT_PHONE,
            "contactPhone.in=" + UPDATED_CONTACT_PHONE
        );
    }

    @Test
    @Transactional
    void getAllDepartmentsByContactPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        // Get all the departmentList where contactPhone is not null
        defaultDepartmentFiltering("contactPhone.specified=true", "contactPhone.specified=false");
    }

    @Test
    @Transactional
    void getAllDepartmentsByContactPhoneContainsSomething() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        // Get all the departmentList where contactPhone contains
        defaultDepartmentFiltering("contactPhone.contains=" + DEFAULT_CONTACT_PHONE, "contactPhone.contains=" + UPDATED_CONTACT_PHONE);
    }

    @Test
    @Transactional
    void getAllDepartmentsByContactPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        // Get all the departmentList where contactPhone does not contain
        defaultDepartmentFiltering(
            "contactPhone.doesNotContain=" + UPDATED_CONTACT_PHONE,
            "contactPhone.doesNotContain=" + DEFAULT_CONTACT_PHONE
        );
    }

    @Test
    @Transactional
    void getAllDepartmentsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        // Get all the departmentList where active equals to
        defaultDepartmentFiltering("active.equals=" + DEFAULT_ACTIVE, "active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllDepartmentsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        // Get all the departmentList where active in
        defaultDepartmentFiltering("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE, "active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllDepartmentsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        // Get all the departmentList where active is not null
        defaultDepartmentFiltering("active.specified=true", "active.specified=false");
    }

    private void defaultDepartmentFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultDepartmentShouldBeFound(shouldBeFound);
        defaultDepartmentShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDepartmentShouldBeFound(String filter) throws Exception {
        restDepartmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(department.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL)))
            .andExpect(jsonPath("$.[*].contactPhone").value(hasItem(DEFAULT_CONTACT_PHONE)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)));

        // Check, that the count call also returns 1
        restDepartmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDepartmentShouldNotBeFound(String filter) throws Exception {
        restDepartmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDepartmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDepartment() throws Exception {
        // Get the department
        restDepartmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDepartment() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the department
        Department updatedDepartment = departmentRepository.findById(department.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDepartment are not directly saved in db
        em.detach(updatedDepartment);
        updatedDepartment
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .contactPhone(UPDATED_CONTACT_PHONE)
            .active(UPDATED_ACTIVE);
        DepartmentDTO departmentDTO = departmentMapper.toDto(updatedDepartment);

        restDepartmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, departmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(departmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDepartmentToMatchAllProperties(updatedDepartment);
    }

    @Test
    @Transactional
    void putNonExistingDepartment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        department.setId(longCount.incrementAndGet());

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, departmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(departmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDepartment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        department.setId(longCount.incrementAndGet());

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(departmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDepartment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        department.setId(longCount.incrementAndGet());

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartmentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(departmentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDepartmentWithPatch() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the department using partial update
        Department partialUpdatedDepartment = new Department();
        partialUpdatedDepartment.setId(department.getId());

        partialUpdatedDepartment.description(UPDATED_DESCRIPTION).contactEmail(UPDATED_CONTACT_EMAIL);

        restDepartmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepartment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDepartment))
            )
            .andExpect(status().isOk());

        // Validate the Department in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDepartmentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDepartment, department),
            getPersistedDepartment(department)
        );
    }

    @Test
    @Transactional
    void fullUpdateDepartmentWithPatch() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

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

        restDepartmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepartment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDepartment))
            )
            .andExpect(status().isOk());

        // Validate the Department in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDepartmentUpdatableFieldsEquals(partialUpdatedDepartment, getPersistedDepartment(partialUpdatedDepartment));
    }

    @Test
    @Transactional
    void patchNonExistingDepartment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        department.setId(longCount.incrementAndGet());

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, departmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(departmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDepartment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        department.setId(longCount.incrementAndGet());

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(departmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDepartment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        department.setId(longCount.incrementAndGet());

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartmentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(departmentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDepartment() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.saveAndFlush(department);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the department
        restDepartmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, department.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return departmentRepository.count();
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
        return departmentRepository.findById(department.getId()).orElseThrow();
    }

    protected void assertPersistedDepartmentToMatchAllProperties(Department expectedDepartment) {
        assertDepartmentAllPropertiesEquals(expectedDepartment, getPersistedDepartment(expectedDepartment));
    }

    protected void assertPersistedDepartmentToMatchUpdatableProperties(Department expectedDepartment) {
        assertDepartmentAllUpdatablePropertiesEquals(expectedDepartment, getPersistedDepartment(expectedDepartment));
    }
}
