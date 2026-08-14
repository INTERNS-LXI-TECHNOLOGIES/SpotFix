package com.divisosofttech.spot_fix.web.rest;

import static com.divisosofttech.spot_fix.domain.LocationAsserts.*;
import static com.divisosofttech.spot_fix.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.divisosofttech.spot_fix.IntegrationTest;
import com.divisosofttech.spot_fix.domain.Location;
import com.divisosofttech.spot_fix.domain.Ward;
import com.divisosofttech.spot_fix.repository.EntityManager;
import com.divisosofttech.spot_fix.repository.LocationRepository;
import com.divisosofttech.spot_fix.repository.WardRepository;
import com.divisosofttech.spot_fix.service.dto.LocationDTO;
import com.divisosofttech.spot_fix.service.mapper.LocationMapper;
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
 * Integration tests for the {@link LocationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class LocationResourceIT {

    private static final String DEFAULT_ADDRESS_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_LANDMARK = "AAAAAAAAAA";
    private static final String UPDATED_LANDMARK = "BBBBBBBBBB";

    private static final Double DEFAULT_LATITUDE = -90D;
    private static final Double UPDATED_LATITUDE = -89D;
    private static final Double SMALLER_LATITUDE = -90D - 1D;

    private static final Double DEFAULT_LONGITUDE = -180D;
    private static final Double UPDATED_LONGITUDE = -179D;
    private static final Double SMALLER_LONGITUDE = -180D - 1D;

    private static final String ENTITY_API_URL = "/api/locations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Location location;

    private Location insertedLocation;

    @Autowired
    private WardRepository wardRepository;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Location createEntity() {
        return new Location()
            .addressText(DEFAULT_ADDRESS_TEXT)
            .landmark(DEFAULT_LANDMARK)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Location createUpdatedEntity() {
        return new Location()
            .addressText(UPDATED_ADDRESS_TEXT)
            .landmark(UPDATED_LANDMARK)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Location.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @BeforeEach
    void initTest() {
        location = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedLocation != null) {
            locationRepository.delete(insertedLocation).block();
            insertedLocation = null;
        }
        deleteEntities(em);
    }

    @Test
    void createLocation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);
        var returnedLocationDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(locationDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(LocationDTO.class)
            .returnResult()
            .getResponseBody();

        // Validate the Location in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedLocation = locationMapper.toEntity(returnedLocationDTO);
        assertLocationUpdatableFieldsEquals(returnedLocation, getPersistedLocation(returnedLocation));

        insertedLocation = returnedLocation;
    }

    @Test
    void createLocationWithExistingId() throws Exception {
        // Create the Location with an existing ID
        location.setId(1L);
        LocationDTO locationDTO = locationMapper.toDto(location);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(locationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkAddressTextIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        location.setAddressText(null);

        // Create the Location, which fails.
        LocationDTO locationDTO = locationMapper.toDto(location);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(locationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllLocations() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList
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
            .value(hasItem(location.getId().intValue()))
            .jsonPath("$.[*].addressText")
            .value(hasItem(DEFAULT_ADDRESS_TEXT))
            .jsonPath("$.[*].landmark")
            .value(hasItem(DEFAULT_LANDMARK))
            .jsonPath("$.[*].latitude")
            .value(hasItem(DEFAULT_LATITUDE))
            .jsonPath("$.[*].longitude")
            .value(hasItem(DEFAULT_LONGITUDE));
    }

    @Test
    void getLocation() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get the location
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, location.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(location.getId().intValue()))
            .jsonPath("$.addressText")
            .value(is(DEFAULT_ADDRESS_TEXT))
            .jsonPath("$.landmark")
            .value(is(DEFAULT_LANDMARK))
            .jsonPath("$.latitude")
            .value(is(DEFAULT_LATITUDE))
            .jsonPath("$.longitude")
            .value(is(DEFAULT_LONGITUDE));
    }

    @Test
    void getLocationsByIdFiltering() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        Long id = location.getId();

        defaultLocationFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultLocationFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultLocationFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    void getAllLocationsByAddressTextIsEqualToSomething() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where addressText equals to
        defaultLocationFiltering("addressText.equals=" + DEFAULT_ADDRESS_TEXT, "addressText.equals=" + UPDATED_ADDRESS_TEXT);
    }

    @Test
    void getAllLocationsByAddressTextIsInShouldWork() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where addressText in
        defaultLocationFiltering(
            "addressText.in=" + DEFAULT_ADDRESS_TEXT + "," + UPDATED_ADDRESS_TEXT,
            "addressText.in=" + UPDATED_ADDRESS_TEXT
        );
    }

    @Test
    void getAllLocationsByAddressTextIsNullOrNotNull() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where addressText is not null
        defaultLocationFiltering("addressText.specified=true", "addressText.specified=false");
    }

    @Test
    void getAllLocationsByAddressTextContainsSomething() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where addressText contains
        defaultLocationFiltering("addressText.contains=" + DEFAULT_ADDRESS_TEXT, "addressText.contains=" + UPDATED_ADDRESS_TEXT);
    }

    @Test
    void getAllLocationsByAddressTextNotContainsSomething() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where addressText does not contain
        defaultLocationFiltering(
            "addressText.doesNotContain=" + UPDATED_ADDRESS_TEXT,
            "addressText.doesNotContain=" + DEFAULT_ADDRESS_TEXT
        );
    }

    @Test
    void getAllLocationsByLandmarkIsEqualToSomething() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where landmark equals to
        defaultLocationFiltering("landmark.equals=" + DEFAULT_LANDMARK, "landmark.equals=" + UPDATED_LANDMARK);
    }

    @Test
    void getAllLocationsByLandmarkIsInShouldWork() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where landmark in
        defaultLocationFiltering("landmark.in=" + DEFAULT_LANDMARK + "," + UPDATED_LANDMARK, "landmark.in=" + UPDATED_LANDMARK);
    }

    @Test
    void getAllLocationsByLandmarkIsNullOrNotNull() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where landmark is not null
        defaultLocationFiltering("landmark.specified=true", "landmark.specified=false");
    }

    @Test
    void getAllLocationsByLandmarkContainsSomething() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where landmark contains
        defaultLocationFiltering("landmark.contains=" + DEFAULT_LANDMARK, "landmark.contains=" + UPDATED_LANDMARK);
    }

    @Test
    void getAllLocationsByLandmarkNotContainsSomething() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where landmark does not contain
        defaultLocationFiltering("landmark.doesNotContain=" + UPDATED_LANDMARK, "landmark.doesNotContain=" + DEFAULT_LANDMARK);
    }

    @Test
    void getAllLocationsByLatitudeIsEqualToSomething() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where latitude equals to
        defaultLocationFiltering("latitude.equals=" + DEFAULT_LATITUDE, "latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    void getAllLocationsByLatitudeIsInShouldWork() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where latitude in
        defaultLocationFiltering("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE, "latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    void getAllLocationsByLatitudeIsNullOrNotNull() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where latitude is not null
        defaultLocationFiltering("latitude.specified=true", "latitude.specified=false");
    }

    @Test
    void getAllLocationsByLatitudeIsGreaterThanOrEqualToSomething() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where latitude is greater than or equal to
        defaultLocationFiltering(
            "latitude.greaterThanOrEqual=" + DEFAULT_LATITUDE,
            "latitude.greaterThanOrEqual=" + (DEFAULT_LATITUDE + 1)
        );
    }

    @Test
    void getAllLocationsByLatitudeIsLessThanOrEqualToSomething() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where latitude is less than or equal to
        defaultLocationFiltering("latitude.lessThanOrEqual=" + DEFAULT_LATITUDE, "latitude.lessThanOrEqual=" + SMALLER_LATITUDE);
    }

    @Test
    void getAllLocationsByLatitudeIsLessThanSomething() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where latitude is less than
        defaultLocationFiltering("latitude.lessThan=" + (DEFAULT_LATITUDE + 1), "latitude.lessThan=" + DEFAULT_LATITUDE);
    }

    @Test
    void getAllLocationsByLatitudeIsGreaterThanSomething() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where latitude is greater than
        defaultLocationFiltering("latitude.greaterThan=" + SMALLER_LATITUDE, "latitude.greaterThan=" + DEFAULT_LATITUDE);
    }

    @Test
    void getAllLocationsByLongitudeIsEqualToSomething() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where longitude equals to
        defaultLocationFiltering("longitude.equals=" + DEFAULT_LONGITUDE, "longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    void getAllLocationsByLongitudeIsInShouldWork() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where longitude in
        defaultLocationFiltering("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE, "longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    void getAllLocationsByLongitudeIsNullOrNotNull() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where longitude is not null
        defaultLocationFiltering("longitude.specified=true", "longitude.specified=false");
    }

    @Test
    void getAllLocationsByLongitudeIsGreaterThanOrEqualToSomething() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where longitude is greater than or equal to
        defaultLocationFiltering(
            "longitude.greaterThanOrEqual=" + DEFAULT_LONGITUDE,
            "longitude.greaterThanOrEqual=" + (DEFAULT_LONGITUDE + 1)
        );
    }

    @Test
    void getAllLocationsByLongitudeIsLessThanOrEqualToSomething() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where longitude is less than or equal to
        defaultLocationFiltering("longitude.lessThanOrEqual=" + DEFAULT_LONGITUDE, "longitude.lessThanOrEqual=" + SMALLER_LONGITUDE);
    }

    @Test
    void getAllLocationsByLongitudeIsLessThanSomething() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where longitude is less than
        defaultLocationFiltering("longitude.lessThan=" + (DEFAULT_LONGITUDE + 1), "longitude.lessThan=" + DEFAULT_LONGITUDE);
    }

    @Test
    void getAllLocationsByLongitudeIsGreaterThanSomething() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        // Get all the locationList where longitude is greater than
        defaultLocationFiltering("longitude.greaterThan=" + SMALLER_LONGITUDE, "longitude.greaterThan=" + DEFAULT_LONGITUDE);
    }

    @Test
    void getAllLocationsByWardIsEqualToSomething() {
        Ward ward = WardResourceIT.createEntity();
        wardRepository.save(ward).block();
        Long wardId = ward.getId();
        location.setWardId(wardId);
        insertedLocation = locationRepository.save(location).block();
        // Get all the locationList where ward equals to wardId
        defaultLocationShouldBeFound("wardId.equals=" + wardId);

        // Get all the locationList where ward equals to (wardId + 1)
        defaultLocationShouldNotBeFound("wardId.equals=" + (wardId + 1));
    }

    private void defaultLocationFiltering(String shouldBeFound, String shouldNotBeFound) {
        defaultLocationShouldBeFound(shouldBeFound);
        defaultLocationShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLocationShouldBeFound(String filter) {
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
            .value(hasItem(location.getId().intValue()))
            .jsonPath("$.[*].addressText")
            .value(hasItem(DEFAULT_ADDRESS_TEXT))
            .jsonPath("$.[*].landmark")
            .value(hasItem(DEFAULT_LANDMARK))
            .jsonPath("$.[*].latitude")
            .value(hasItem(DEFAULT_LATITUDE))
            .jsonPath("$.[*].longitude")
            .value(hasItem(DEFAULT_LONGITUDE));

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
    private void defaultLocationShouldNotBeFound(String filter) {
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
    void getNonExistingLocation() {
        // Get the location
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingLocation() throws Exception {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the location
        Location updatedLocation = locationRepository.findById(location.getId()).block();
        updatedLocation
            .addressText(UPDATED_ADDRESS_TEXT)
            .landmark(UPDATED_LANDMARK)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        LocationDTO locationDTO = locationMapper.toDto(updatedLocation);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, locationDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(locationDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLocationToMatchAllProperties(updatedLocation);
    }

    @Test
    void putNonExistingLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, locationDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(locationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(locationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(locationDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateLocationWithPatch() throws Exception {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the location using partial update
        Location partialUpdatedLocation = new Location();
        partialUpdatedLocation.setId(location.getId());

        partialUpdatedLocation.landmark(UPDATED_LANDMARK).latitude(UPDATED_LATITUDE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedLocation.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedLocation))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Location in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocationUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedLocation, location), getPersistedLocation(location));
    }

    @Test
    void fullUpdateLocationWithPatch() throws Exception {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the location using partial update
        Location partialUpdatedLocation = new Location();
        partialUpdatedLocation.setId(location.getId());

        partialUpdatedLocation
            .addressText(UPDATED_ADDRESS_TEXT)
            .landmark(UPDATED_LANDMARK)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedLocation.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedLocation))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Location in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocationUpdatableFieldsEquals(partialUpdatedLocation, getPersistedLocation(partialUpdatedLocation));
    }

    @Test
    void patchNonExistingLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, locationDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(locationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(locationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(locationDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteLocation() {
        // Initialize the database
        insertedLocation = locationRepository.save(location).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the location
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, location.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return locationRepository.count().block();
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

    protected Location getPersistedLocation(Location location) {
        return locationRepository.findById(location.getId()).block();
    }

    protected void assertPersistedLocationToMatchAllProperties(Location expectedLocation) {
        // Test fails because reactive api returns an empty object instead of null
        // assertLocationAllPropertiesEquals(expectedLocation, getPersistedLocation(expectedLocation));
        assertLocationUpdatableFieldsEquals(expectedLocation, getPersistedLocation(expectedLocation));
    }

    protected void assertPersistedLocationToMatchUpdatableProperties(Location expectedLocation) {
        // Test fails because reactive api returns an empty object instead of null
        // assertLocationAllUpdatablePropertiesEquals(expectedLocation, getPersistedLocation(expectedLocation));
        assertLocationUpdatableFieldsEquals(expectedLocation, getPersistedLocation(expectedLocation));
    }
}
