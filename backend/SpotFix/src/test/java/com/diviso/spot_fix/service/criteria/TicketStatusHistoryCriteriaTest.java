package com.diviso.spot_fix.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class TicketStatusHistoryCriteriaTest {

    @Test
    void newTicketStatusHistoryCriteriaHasAllFiltersNullTest() {
        var ticketStatusHistoryCriteria = new TicketStatusHistoryCriteria();
        assertThat(ticketStatusHistoryCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void ticketStatusHistoryCriteriaFluentMethodsCreatesFiltersTest() {
        var ticketStatusHistoryCriteria = new TicketStatusHistoryCriteria();

        setAllFilters(ticketStatusHistoryCriteria);

        assertThat(ticketStatusHistoryCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void ticketStatusHistoryCriteriaCopyCreatesNullFilterTest() {
        var ticketStatusHistoryCriteria = new TicketStatusHistoryCriteria();
        var copy = ticketStatusHistoryCriteria.copy();

        assertThat(ticketStatusHistoryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(ticketStatusHistoryCriteria)
        );
    }

    @Test
    void ticketStatusHistoryCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var ticketStatusHistoryCriteria = new TicketStatusHistoryCriteria();
        setAllFilters(ticketStatusHistoryCriteria);

        var copy = ticketStatusHistoryCriteria.copy();

        assertThat(ticketStatusHistoryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(ticketStatusHistoryCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var ticketStatusHistoryCriteria = new TicketStatusHistoryCriteria();

        assertThat(ticketStatusHistoryCriteria).hasToString("TicketStatusHistoryCriteria{}");
    }

    private static void setAllFilters(TicketStatusHistoryCriteria ticketStatusHistoryCriteria) {
        ticketStatusHistoryCriteria.id();
        ticketStatusHistoryCriteria.oldStatus();
        ticketStatusHistoryCriteria.newStatus();
        ticketStatusHistoryCriteria.changedDate();
        ticketStatusHistoryCriteria.ticketId();
        ticketStatusHistoryCriteria.changedById();
        ticketStatusHistoryCriteria.distinct();
    }

    private static Condition<TicketStatusHistoryCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getOldStatus()) &&
                condition.apply(criteria.getNewStatus()) &&
                condition.apply(criteria.getChangedDate()) &&
                condition.apply(criteria.getTicketId()) &&
                condition.apply(criteria.getChangedById()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<TicketStatusHistoryCriteria> copyFiltersAre(
        TicketStatusHistoryCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getOldStatus(), copy.getOldStatus()) &&
                condition.apply(criteria.getNewStatus(), copy.getNewStatus()) &&
                condition.apply(criteria.getChangedDate(), copy.getChangedDate()) &&
                condition.apply(criteria.getTicketId(), copy.getTicketId()) &&
                condition.apply(criteria.getChangedById(), copy.getChangedById()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
