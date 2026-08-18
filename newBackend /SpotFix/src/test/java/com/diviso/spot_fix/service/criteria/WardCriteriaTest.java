package com.diviso.spot_fix.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class WardCriteriaTest {

    @Test
    void newWardCriteriaHasAllFiltersNullTest() {
        var wardCriteria = new WardCriteria();
        assertThat(wardCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void wardCriteriaFluentMethodsCreatesFiltersTest() {
        var wardCriteria = new WardCriteria();

        setAllFilters(wardCriteria);

        assertThat(wardCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void wardCriteriaCopyCreatesNullFilterTest() {
        var wardCriteria = new WardCriteria();
        var copy = wardCriteria.copy();

        assertThat(wardCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(wardCriteria)
        );
    }

    @Test
    void wardCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var wardCriteria = new WardCriteria();
        setAllFilters(wardCriteria);

        var copy = wardCriteria.copy();

        assertThat(wardCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(wardCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var wardCriteria = new WardCriteria();

        assertThat(wardCriteria).hasToString("WardCriteria{}");
    }

    private static void setAllFilters(WardCriteria wardCriteria) {
        wardCriteria.id();
        wardCriteria.code();
        wardCriteria.name();
        wardCriteria.municipality();
        wardCriteria.distinct();
    }

    private static Condition<WardCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getMunicipality()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<WardCriteria> copyFiltersAre(WardCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCode(), copy.getCode()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getMunicipality(), copy.getMunicipality()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
