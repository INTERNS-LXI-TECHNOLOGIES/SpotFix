package com.diviso.spot_fix.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class LocationCriteriaTest {

    @Test
    void newLocationCriteriaHasAllFiltersNullTest() {
        var locationCriteria = new LocationCriteria();
        assertThat(locationCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void locationCriteriaFluentMethodsCreatesFiltersTest() {
        var locationCriteria = new LocationCriteria();

        setAllFilters(locationCriteria);

        assertThat(locationCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void locationCriteriaCopyCreatesNullFilterTest() {
        var locationCriteria = new LocationCriteria();
        var copy = locationCriteria.copy();

        assertThat(locationCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(locationCriteria)
        );
    }

    @Test
    void locationCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var locationCriteria = new LocationCriteria();
        setAllFilters(locationCriteria);

        var copy = locationCriteria.copy();

        assertThat(locationCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(locationCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var locationCriteria = new LocationCriteria();

        assertThat(locationCriteria).hasToString("LocationCriteria{}");
    }

    private static void setAllFilters(LocationCriteria locationCriteria) {
        locationCriteria.id();
        locationCriteria.addressText();
        locationCriteria.landmark();
        locationCriteria.latitude();
        locationCriteria.longitude();
        locationCriteria.wardId();
        locationCriteria.distinct();
    }

    private static Condition<LocationCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getAddressText()) &&
                condition.apply(criteria.getLandmark()) &&
                condition.apply(criteria.getLatitude()) &&
                condition.apply(criteria.getLongitude()) &&
                condition.apply(criteria.getWardId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<LocationCriteria> copyFiltersAre(LocationCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getAddressText(), copy.getAddressText()) &&
                condition.apply(criteria.getLandmark(), copy.getLandmark()) &&
                condition.apply(criteria.getLatitude(), copy.getLatitude()) &&
                condition.apply(criteria.getLongitude(), copy.getLongitude()) &&
                condition.apply(criteria.getWardId(), copy.getWardId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
