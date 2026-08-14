package com.divisosofttech.spot_fix.repository;

import com.divisosofttech.spot_fix.domain.TicketVote;
import com.divisosofttech.spot_fix.domain.criteria.TicketVoteCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the TicketVote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TicketVoteRepository extends ReactiveCrudRepository<TicketVote, Long>, TicketVoteRepositoryInternal {
    Flux<TicketVote> findAllBy(Pageable pageable);

    @Override
    Mono<TicketVote> findOneWithEagerRelationships(Long id);

    @Override
    Flux<TicketVote> findAllWithEagerRelationships();

    @Override
    Flux<TicketVote> findAllWithEagerRelationships(Pageable page);

    @Query("SELECT * FROM ticket_vote entity WHERE entity.ticket_id = :id")
    Flux<TicketVote> findByTicket(Long id);

    @Query("SELECT * FROM ticket_vote entity WHERE entity.ticket_id IS NULL")
    Flux<TicketVote> findAllWhereTicketIsNull();

    @Query("SELECT * FROM ticket_vote entity WHERE entity.user_id = :id")
    Flux<TicketVote> findByUser(Long id);

    @Query("SELECT * FROM ticket_vote entity WHERE entity.user_id IS NULL")
    Flux<TicketVote> findAllWhereUserIsNull();

    @Override
    <S extends TicketVote> Mono<S> save(S entity);

    @Override
    Flux<TicketVote> findAll();

    @Override
    Mono<TicketVote> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface TicketVoteRepositoryInternal {
    <S extends TicketVote> Mono<S> save(S entity);

    Flux<TicketVote> findAllBy(Pageable pageable);

    Flux<TicketVote> findAll();

    Mono<TicketVote> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<TicketVote> findAllBy(Pageable pageable, Criteria criteria);
    Flux<TicketVote> findByCriteria(TicketVoteCriteria criteria, Pageable pageable);

    Mono<Long> countByCriteria(TicketVoteCriteria criteria);

    Mono<TicketVote> findOneWithEagerRelationships(Long id);

    Flux<TicketVote> findAllWithEagerRelationships();

    Flux<TicketVote> findAllWithEagerRelationships(Pageable page);

    Mono<Void> deleteById(Long id);
}
