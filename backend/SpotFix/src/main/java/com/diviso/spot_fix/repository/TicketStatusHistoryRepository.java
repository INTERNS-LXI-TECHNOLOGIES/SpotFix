package com.diviso.spot_fix.repository;

import com.diviso.spot_fix.domain.TicketStatusHistory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TicketStatusHistory entity.
 */
@Repository
public interface TicketStatusHistoryRepository
    extends JpaRepository<TicketStatusHistory, Long>, JpaSpecificationExecutor<TicketStatusHistory>
{
    @Query(
        "select ticketStatusHistory from TicketStatusHistory ticketStatusHistory where ticketStatusHistory.changedBy.login = ?#{authentication.name}"
    )
    List<TicketStatusHistory> findByChangedByIsCurrentUser();

    default Optional<TicketStatusHistory> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TicketStatusHistory> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TicketStatusHistory> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select ticketStatusHistory from TicketStatusHistory ticketStatusHistory left join fetch ticketStatusHistory.changedBy",
        countQuery = "select count(ticketStatusHistory) from TicketStatusHistory ticketStatusHistory"
    )
    Page<TicketStatusHistory> findAllWithToOneRelationships(Pageable pageable);

    @Query("select ticketStatusHistory from TicketStatusHistory ticketStatusHistory left join fetch ticketStatusHistory.changedBy")
    List<TicketStatusHistory> findAllWithToOneRelationships();

    @Query(
        "select ticketStatusHistory from TicketStatusHistory ticketStatusHistory left join fetch ticketStatusHistory.changedBy where ticketStatusHistory.id =:id"
    )
    Optional<TicketStatusHistory> findOneWithToOneRelationships(@Param("id") Long id);
}
