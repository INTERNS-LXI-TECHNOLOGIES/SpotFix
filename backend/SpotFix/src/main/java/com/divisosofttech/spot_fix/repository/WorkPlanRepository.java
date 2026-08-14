package com.divisosofttech.spot_fix.repository;

import com.divisosofttech.spot_fix.domain.WorkPlan;
import com.divisosofttech.spot_fix.domain.criteria.WorkPlanCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the WorkPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkPlanRepository extends ReactiveCrudRepository<WorkPlan, Long>, WorkPlanRepositoryInternal {
    Flux<WorkPlan> findAllBy(Pageable pageable);

    @Query("SELECT * FROM work_plan entity WHERE entity.ticket_id = :id")
    Flux<WorkPlan> findByTicket(Long id);

    @Query("SELECT * FROM work_plan entity WHERE entity.ticket_id IS NULL")
    Flux<WorkPlan> findAllWhereTicketIsNull();

    @Query("SELECT * FROM work_plan entity WHERE entity.department_id = :id")
    Flux<WorkPlan> findByDepartment(Long id);

    @Query("SELECT * FROM work_plan entity WHERE entity.department_id IS NULL")
    Flux<WorkPlan> findAllWhereDepartmentIsNull();

    @Override
    <S extends WorkPlan> Mono<S> save(S entity);

    @Override
    Flux<WorkPlan> findAll();

    @Override
    Mono<WorkPlan> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface WorkPlanRepositoryInternal {
    <S extends WorkPlan> Mono<S> save(S entity);

    Flux<WorkPlan> findAllBy(Pageable pageable);

    Flux<WorkPlan> findAll();

    Mono<WorkPlan> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<WorkPlan> findAllBy(Pageable pageable, Criteria criteria);
    Flux<WorkPlan> findByCriteria(WorkPlanCriteria criteria, Pageable pageable);

    Mono<Long> countByCriteria(WorkPlanCriteria criteria);
}
