package com.divisosofttech.spot_fix.repository;

import com.divisosofttech.spot_fix.domain.Ticket;
import com.divisosofttech.spot_fix.domain.criteria.TicketCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Ticket entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TicketRepository extends ReactiveCrudRepository<Ticket, Long>, TicketRepositoryInternal {
    Flux<Ticket> findAllBy(Pageable pageable);

    @Override
    Mono<Ticket> findOneWithEagerRelationships(Long id);

    @Override
    Flux<Ticket> findAllWithEagerRelationships();

    @Override
    Flux<Ticket> findAllWithEagerRelationships(Pageable page);

    @Query("SELECT * FROM ticket entity WHERE entity.reported_by_id = :id")
    Flux<Ticket> findByReportedBy(Long id);

    @Query("SELECT * FROM ticket entity WHERE entity.reported_by_id IS NULL")
    Flux<Ticket> findAllWhereReportedByIsNull();

    @Query("SELECT * FROM ticket entity WHERE entity.location_id = :id")
    Flux<Ticket> findByLocation(Long id);

    @Query("SELECT * FROM ticket entity WHERE entity.location_id IS NULL")
    Flux<Ticket> findAllWhereLocationIsNull();

    @Query("SELECT * FROM ticket entity WHERE entity.ward_id = :id")
    Flux<Ticket> findByWard(Long id);

    @Query("SELECT * FROM ticket entity WHERE entity.ward_id IS NULL")
    Flux<Ticket> findAllWhereWardIsNull();

    @Query("SELECT * FROM ticket entity WHERE entity.assigned_department_id = :id")
    Flux<Ticket> findByAssignedDepartment(Long id);

    @Query("SELECT * FROM ticket entity WHERE entity.assigned_department_id IS NULL")
    Flux<Ticket> findAllWhereAssignedDepartmentIsNull();

    @Override
    <S extends Ticket> Mono<S> save(S entity);

    @Override
    Flux<Ticket> findAll();

    @Override
    Mono<Ticket> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface TicketRepositoryInternal {
    <S extends Ticket> Mono<S> save(S entity);

    Flux<Ticket> findAllBy(Pageable pageable);

    Flux<Ticket> findAll();

    Mono<Ticket> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Ticket> findAllBy(Pageable pageable, Criteria criteria);
    Flux<Ticket> findByCriteria(TicketCriteria criteria, Pageable pageable);

    Mono<Long> countByCriteria(TicketCriteria criteria);

    Mono<Ticket> findOneWithEagerRelationships(Long id);

    Flux<Ticket> findAllWithEagerRelationships();

    Flux<Ticket> findAllWithEagerRelationships(Pageable page);

    Mono<Void> deleteById(Long id);
}
