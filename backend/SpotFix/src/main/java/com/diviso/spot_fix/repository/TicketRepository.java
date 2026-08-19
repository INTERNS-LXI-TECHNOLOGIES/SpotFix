package com.diviso.spot_fix.repository;

import com.diviso.spot_fix.domain.Ticket;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.diviso.spot_fix.domain.enumeration.Priority;
import com.diviso.spot_fix.domain.enumeration.TicketStatus;
import java.util.List;
/**
 * Spring Data JPA repository for the Ticket entity.
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {
    @Query("select ticket from Ticket ticket where ticket.reportedBy.login = ?#{authentication.name}")
    List<Ticket> findByReportedByIsCurrentUser();

    default Optional<Ticket> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Ticket> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }
 long countByWardId(Long wardId);
 long countbyWardIdAndPriority(long wardId,Priority priority);
 long countbyWardIdAndStatus(long wardId,List<TicketStatus> status);  
   long countByWardIdAndCreatedDateAfter(
        Long wardId,
        Instant date
    );
        long countByWard_Id(Long wardId);

long countByWard_IdAndPriority(
    Long wardId,
    Priority priority
);

long countByWard_IdAndStatus(
    Long wardId,
    TicketStatus status
);
 default Page<Ticket> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select ticket from Ticket ticket left join fetch ticket.reportedBy",
        countQuery = "select count(ticket) from Ticket ticket"
    )
    Page<Ticket> findAllWithToOneRelationships(Pageable pageable);

    @Query("select ticket from Ticket ticket left join fetch ticket.reportedBy")
    List<Ticket> findAllWithToOneRelationships();

    @Query("select ticket from Ticket ticket left join fetch ticket.reportedBy where ticket.id =:id")
    Optional<Ticket> findOneWithToOneRelationships(@Param("id") Long id);
}
