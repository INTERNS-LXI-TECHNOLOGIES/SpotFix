package com.diviso.spot_fix.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class WorkPlanCriteriaTest {

    @Test
    void newWorkPlanCriteriaHasAllFiltersNullTest() {
        var workPlanCriteria = new WorkPlanCriteria();
        assertThat(workPlanCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void workPlanCriteriaFluentMethodsCreatesFiltersTest() {
        var workPlanCriteria = new WorkPlanCriteria();

        setAllFilters(workPlanCriteria);

        assertThat(workPlanCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void workPlanCriteriaCopyCreatesNullFilterTest() {
        var workPlanCriteria = new WorkPlanCriteria();
        var copy = workPlanCriteria.copy();

        assertThat(workPlanCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(workPlanCriteria)
        );
    }

    @Test
    void workPlanCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var workPlanCriteria = new WorkPlanCriteria();
        setAllFilters(workPlanCriteria);

        var copy = workPlanCriteria.copy();

        assertThat(workPlanCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(workPlanCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var workPlanCriteria = new WorkPlanCriteria();

        assertThat(workPlanCriteria).hasToString("WorkPlanCriteria{}");
    }

    private static void setAllFilters(WorkPlanCriteria workPlanCriteria) {
        workPlanCriteria.id();
        workPlanCriteria.estimatedCost();
        workPlanCriteria.startedDate();
        workPlanCriteria.expectedCompletionDate();
        workPlanCriteria.actualCompletionDate();
        workPlanCriteria.completionPercentage();
        workPlanCriteria.status();
        workPlanCriteria.deleted();
        workPlanCriteria.deletedDate();
        workPlanCriteria.ticketId();
        workPlanCriteria.departmentId();
        workPlanCriteria.distinct();
    }

    private static Condition<WorkPlanCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getEstimatedCost()) &&
                condition.apply(criteria.getStartedDate()) &&
                condition.apply(criteria.getExpectedCompletionDate()) &&
                condition.apply(criteria.getActualCompletionDate()) &&
                condition.apply(criteria.getCompletionPercentage()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getDeleted()) &&
                condition.apply(criteria.getDeletedDate()) &&
                condition.apply(criteria.getTicketId()) &&
                condition.apply(criteria.getDepartmentId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<WorkPlanCriteria> copyFiltersAre(WorkPlanCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getEstimatedCost(), copy.getEstimatedCost()) &&
                condition.apply(criteria.getStartedDate(), copy.getStartedDate()) &&
                condition.apply(criteria.getExpectedCompletionDate(), copy.getExpectedCompletionDate()) &&
                condition.apply(criteria.getActualCompletionDate(), copy.getActualCompletionDate()) &&
                condition.apply(criteria.getCompletionPercentage(), copy.getCompletionPercentage()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getDeleted(), copy.getDeleted()) &&
                condition.apply(criteria.getDeletedDate(), copy.getDeletedDate()) &&
                condition.apply(criteria.getTicketId(), copy.getTicketId()) &&
                condition.apply(criteria.getDepartmentId(), copy.getDepartmentId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
