package com.divisosofttech.spot_fix.repository;

import com.divisosofttech.spot_fix.domain.Attachment;
import com.divisosofttech.spot_fix.domain.criteria.AttachmentCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Attachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachmentRepository extends ReactiveCrudRepository<Attachment, Long>, AttachmentRepositoryInternal {
    Flux<Attachment> findAllBy(Pageable pageable);

    @Override
    Mono<Attachment> findOneWithEagerRelationships(Long id);

    @Override
    Flux<Attachment> findAllWithEagerRelationships();

    @Override
    Flux<Attachment> findAllWithEagerRelationships(Pageable page);

    @Query("SELECT * FROM attachment entity WHERE entity.ticket_id = :id")
    Flux<Attachment> findByTicket(Long id);

    @Query("SELECT * FROM attachment entity WHERE entity.ticket_id IS NULL")
    Flux<Attachment> findAllWhereTicketIsNull();

    @Query("SELECT * FROM attachment entity WHERE entity.uploaded_by_id = :id")
    Flux<Attachment> findByUploadedBy(Long id);

    @Query("SELECT * FROM attachment entity WHERE entity.uploaded_by_id IS NULL")
    Flux<Attachment> findAllWhereUploadedByIsNull();

    @Override
    <S extends Attachment> Mono<S> save(S entity);

    @Override
    Flux<Attachment> findAll();

    @Override
    Mono<Attachment> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface AttachmentRepositoryInternal {
    <S extends Attachment> Mono<S> save(S entity);

    Flux<Attachment> findAllBy(Pageable pageable);

    Flux<Attachment> findAll();

    Mono<Attachment> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Attachment> findAllBy(Pageable pageable, Criteria criteria);
    Flux<Attachment> findByCriteria(AttachmentCriteria criteria, Pageable pageable);

    Mono<Long> countByCriteria(AttachmentCriteria criteria);

    Mono<Attachment> findOneWithEagerRelationships(Long id);

    Flux<Attachment> findAllWithEagerRelationships();

    Flux<Attachment> findAllWithEagerRelationships(Pageable page);

    Mono<Void> deleteById(Long id);
}
