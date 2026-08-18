package com.diviso.spot_fix.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class TicketCriteriaTest {

    @Test
    void newTicketCriteriaHasAllFiltersNullTest() {
        var ticketCriteria = new TicketCriteria();
        assertThat(ticketCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void ticketCriteriaFluentMethodsCreatesFiltersTest() {
        var ticketCriteria = new TicketCriteria();

        setAllFilters(ticketCriteria);

        assertThat(ticketCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void ticketCriteriaCopyCreatesNullFilterTest() {
        var ticketCriteria = new TicketCriteria();
        var copy = ticketCriteria.copy();

        assertThat(ticketCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(ticketCriteria)
        );
    }

    @Test
    void ticketCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var ticketCriteria = new TicketCriteria();
        setAllFilters(ticketCriteria);

        var copy = ticketCriteria.copy();

        assertThat(ticketCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(ticketCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var ticketCriteria = new TicketCriteria();

        assertThat(ticketCriteria).hasToString("TicketCriteria{}");
    }

    private static void setAllFilters(TicketCriteria ticketCriteria) {
        ticketCriteria.id();
        ticketCriteria.title();
        ticketCriteria.status();
        ticketCriteria.priority();
        ticketCriteria.visibility();
        ticketCriteria.category();
        ticketCriteria.createdDate();
        ticketCriteria.updatedDate();
        ticketCriteria.expectedResolutionDate();
        ticketCriteria.resolvedDate();
        ticketCriteria.aiDuplicate();
        ticketCriteria.duplicateScore();
        ticketCriteria.aiConfidence();
        ticketCriteria.duplicateTicketId();
        ticketCriteria.deleted();
        ticketCriteria.deletedDate();
        ticketCriteria.reportedById();
        ticketCriteria.locationId();
        ticketCriteria.wardId();
        ticketCriteria.assignedDepartmentId();
        ticketCriteria.distinct();
    }

    private static Condition<TicketCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getTitle()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getPriority()) &&
                condition.apply(criteria.getVisibility()) &&
                condition.apply(criteria.getCategory()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getUpdatedDate()) &&
                condition.apply(criteria.getExpectedResolutionDate()) &&
                condition.apply(criteria.getResolvedDate()) &&
                condition.apply(criteria.getAiDuplicate()) &&
                condition.apply(criteria.getDuplicateScore()) &&
                condition.apply(criteria.getAiConfidence()) &&
                condition.apply(criteria.getDuplicateTicketId()) &&
                condition.apply(criteria.getDeleted()) &&
                condition.apply(criteria.getDeletedDate()) &&
                condition.apply(criteria.getReportedById()) &&
                condition.apply(criteria.getLocationId()) &&
                condition.apply(criteria.getWardId()) &&
                condition.apply(criteria.getAssignedDepartmentId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<TicketCriteria> copyFiltersAre(TicketCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getTitle(), copy.getTitle()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getPriority(), copy.getPriority()) &&
                condition.apply(criteria.getVisibility(), copy.getVisibility()) &&
                condition.apply(criteria.getCategory(), copy.getCategory()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getUpdatedDate(), copy.getUpdatedDate()) &&
                condition.apply(criteria.getExpectedResolutionDate(), copy.getExpectedResolutionDate()) &&
                condition.apply(criteria.getResolvedDate(), copy.getResolvedDate()) &&
                condition.apply(criteria.getAiDuplicate(), copy.getAiDuplicate()) &&
                condition.apply(criteria.getDuplicateScore(), copy.getDuplicateScore()) &&
                condition.apply(criteria.getAiConfidence(), copy.getAiConfidence()) &&
                condition.apply(criteria.getDuplicateTicketId(), copy.getDuplicateTicketId()) &&
                condition.apply(criteria.getDeleted(), copy.getDeleted()) &&
                condition.apply(criteria.getDeletedDate(), copy.getDeletedDate()) &&
                condition.apply(criteria.getReportedById(), copy.getReportedById()) &&
                condition.apply(criteria.getLocationId(), copy.getLocationId()) &&
                condition.apply(criteria.getWardId(), copy.getWardId()) &&
                condition.apply(criteria.getAssignedDepartmentId(), copy.getAssignedDepartmentId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
