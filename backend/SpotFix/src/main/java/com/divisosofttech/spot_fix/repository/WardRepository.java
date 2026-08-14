package com.divisosofttech.spot_fix.repository;

import com.divisosofttech.spot_fix.domain.Ward;
import com.divisosofttech.spot_fix.domain.criteria.WardCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Ward entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WardRepository extends ReactiveCrudRepository<Ward, Long>, WardRepositoryInternal {
    Flux<Ward> findAllBy(Pageable pageable);

    @Override
    <S extends Ward> Mono<S> save(S entity);

    @Override
    Flux<Ward> findAll();

    @Override
    Mono<Ward> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface WardRepositoryInternal {
    <S extends Ward> Mono<S> save(S entity);

    Flux<Ward> findAllBy(Pageable pageable);

    Flux<Ward> findAll();

    Mono<Ward> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Ward> findAllBy(Pageable pageable, Criteria criteria);
    Flux<Ward> findByCriteria(WardCriteria criteria, Pageable pageable);

    Mono<Long> countByCriteria(WardCriteria criteria);
}
