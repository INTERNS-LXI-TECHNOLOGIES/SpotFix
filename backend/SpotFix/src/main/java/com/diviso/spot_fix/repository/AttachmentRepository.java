package com.diviso.spot_fix.repository;

import com.diviso.spot_fix.domain.Attachment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Attachment entity.
 */
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long>, JpaSpecificationExecutor<Attachment> {
    @Query("select attachment from Attachment attachment where attachment.uploadedBy.login = ?#{authentication.name}")
    List<Attachment> findByUploadedByIsCurrentUser();

    default Optional<Attachment> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Attachment> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Attachment> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select attachment from Attachment attachment left join fetch attachment.uploadedBy",
        countQuery = "select count(attachment) from Attachment attachment"
    )
    Page<Attachment> findAllWithToOneRelationships(Pageable pageable);

    @Query("select attachment from Attachment attachment left join fetch attachment.uploadedBy")
    List<Attachment> findAllWithToOneRelationships();

    @Query("select attachment from Attachment attachment left join fetch attachment.uploadedBy where attachment.id =:id")
    Optional<Attachment> findOneWithToOneRelationships(@Param("id") Long id);
}
