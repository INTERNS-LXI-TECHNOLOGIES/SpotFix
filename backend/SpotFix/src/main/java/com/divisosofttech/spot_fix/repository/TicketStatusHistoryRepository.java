package com.divisosofttech.spot_fix.repository;

import com.divisosofttech.spot_fix.domain.TicketStatusHistory;
import com.divisosofttech.spot_fix.domain.criteria.TicketStatusHistoryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the TicketStatusHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TicketStatusHistoryRepository
    extends ReactiveCrudRepository<TicketStatusHistory, Long>, TicketStatusHistoryRepositoryInternal
{
    Flux<TicketStatusHistory> findAllBy(Pageable pageable);

    @Override
    Mono<TicketStatusHistory> findOneWithEagerRelationships(Long id);

    @Override
    Flux<TicketStatusHistory> findAllWithEagerRelationships();

    @Override
    Flux<TicketStatusHistory> findAllWithEagerRelationships(Pageable page);

    @Query("SELECT * FROM ticket_status_history entity WHERE entity.ticket_id = :id")
    Flux<TicketStatusHistory> findByTicket(Long id);

    @Query("SELECT * FROM ticket_status_history entity WHERE entity.ticket_id IS NULL")
    Flux<TicketStatusHistory> findAllWhereTicketIsNull();

    @Query("SELECT * FROM ticket_status_history entity WHERE entity.changed_by_id = :id")
    Flux<TicketStatusHistory> findByChangedBy(Long id);

    @Query("SELECT * FROM ticket_status_history entity WHERE entity.changed_by_id IS NULL")
    Flux<TicketStatusHistory> findAllWhereChangedByIsNull();

    @Override
    <S extends TicketStatusHistory> Mono<S> save(S entity);

    @Override
    Flux<TicketStatusHistory> findAll();

    @Override
    Mono<TicketStatusHistory> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface TicketStatusHistoryRepositoryInternal {
    <S extends TicketStatusHistory> Mono<S> save(S entity);

    Flux<TicketStatusHistory> findAllBy(Pageable pageable);

    Flux<TicketStatusHistory> findAll();

    Mono<TicketStatusHistory> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<TicketStatusHistory> findAllBy(Pageable pageable, Criteria criteria);
    Flux<TicketStatusHistory> findByCriteria(TicketStatusHistoryCriteria criteria, Pageable pageable);

    Mono<Long> countByCriteria(TicketStatusHistoryCriteria criteria);

    Mono<TicketStatusHistory> findOneWithEagerRelationships(Long id);

    Flux<TicketStatusHistory> findAllWithEagerRelationships();

    Flux<TicketStatusHistory> findAllWithEagerRelationships(Pageable page);

    Mono<Void> deleteById(Long id);
}
