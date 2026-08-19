package com.diviso.spot_fix.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class TicketVoteCriteriaTest {

    @Test
    void newTicketVoteCriteriaHasAllFiltersNullTest() {
        var ticketVoteCriteria = new TicketVoteCriteria();
        assertThat(ticketVoteCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void ticketVoteCriteriaFluentMethodsCreatesFiltersTest() {
        var ticketVoteCriteria = new TicketVoteCriteria();

        setAllFilters(ticketVoteCriteria);

        assertThat(ticketVoteCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void ticketVoteCriteriaCopyCreatesNullFilterTest() {
        var ticketVoteCriteria = new TicketVoteCriteria();
        var copy = ticketVoteCriteria.copy();

        assertThat(ticketVoteCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(ticketVoteCriteria)
        );
    }

    @Test
    void ticketVoteCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var ticketVoteCriteria = new TicketVoteCriteria();
        setAllFilters(ticketVoteCriteria);

        var copy = ticketVoteCriteria.copy();

        assertThat(ticketVoteCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(ticketVoteCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var ticketVoteCriteria = new TicketVoteCriteria();

        assertThat(ticketVoteCriteria).hasToString("TicketVoteCriteria{}");
    }

    private static void setAllFilters(TicketVoteCriteria ticketVoteCriteria) {
        ticketVoteCriteria.id();
        ticketVoteCriteria.voteType();
        ticketVoteCriteria.createdDate();
        ticketVoteCriteria.ticketId();
        ticketVoteCriteria.userId();
        ticketVoteCriteria.distinct();
    }

    private static Condition<TicketVoteCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getVoteType()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getTicketId()) &&
                condition.apply(criteria.getUserId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<TicketVoteCriteria> copyFiltersAre(TicketVoteCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getVoteType(), copy.getVoteType()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getTicketId(), copy.getTicketId()) &&
                condition.apply(criteria.getUserId(), copy.getUserId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
