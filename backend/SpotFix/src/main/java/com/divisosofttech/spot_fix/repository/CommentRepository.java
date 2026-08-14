package com.divisosofttech.spot_fix.repository;

import com.divisosofttech.spot_fix.domain.Comment;
import com.divisosofttech.spot_fix.domain.criteria.CommentCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Comment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentRepository extends ReactiveCrudRepository<Comment, Long>, CommentRepositoryInternal {
    Flux<Comment> findAllBy(Pageable pageable);

    @Override
    Mono<Comment> findOneWithEagerRelationships(Long id);

    @Override
    Flux<Comment> findAllWithEagerRelationships();

    @Override
    Flux<Comment> findAllWithEagerRelationships(Pageable page);

    @Query("SELECT * FROM comment entity WHERE entity.ticket_id = :id")
    Flux<Comment> findByTicket(Long id);

    @Query("SELECT * FROM comment entity WHERE entity.ticket_id IS NULL")
    Flux<Comment> findAllWhereTicketIsNull();

    @Query("SELECT * FROM comment entity WHERE entity.user_id = :id")
    Flux<Comment> findByUser(Long id);

    @Query("SELECT * FROM comment entity WHERE entity.user_id IS NULL")
    Flux<Comment> findAllWhereUserIsNull();

    @Override
    <S extends Comment> Mono<S> save(S entity);

    @Override
    Flux<Comment> findAll();

    @Override
    Mono<Comment> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface CommentRepositoryInternal {
    <S extends Comment> Mono<S> save(S entity);

    Flux<Comment> findAllBy(Pageable pageable);

    Flux<Comment> findAll();

    Mono<Comment> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Comment> findAllBy(Pageable pageable, Criteria criteria);
    Flux<Comment> findByCriteria(CommentCriteria criteria, Pageable pageable);

    Mono<Long> countByCriteria(CommentCriteria criteria);

    Mono<Comment> findOneWithEagerRelationships(Long id);

    Flux<Comment> findAllWithEagerRelationships();

    Flux<Comment> findAllWithEagerRelationships(Pageable page);

    Mono<Void> deleteById(Long id);
}
