package com.diviso.spot_fix.repository;

import com.diviso.spot_fix.domain.TicketVote;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TicketVote entity.
 */
@Repository
public interface TicketVoteRepository extends JpaRepository<TicketVote, Long>, JpaSpecificationExecutor<TicketVote> {
    @Query("select ticketVote from TicketVote ticketVote where ticketVote.user.login = ?#{authentication.name}")
    List<TicketVote> findByUserIsCurrentUser();

    default Optional<TicketVote> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TicketVote> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TicketVote> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select ticketVote from TicketVote ticketVote left join fetch ticketVote.user",
        countQuery = "select count(ticketVote) from TicketVote ticketVote"
    )
    Page<TicketVote> findAllWithToOneRelationships(Pageable pageable);

    @Query("select ticketVote from TicketVote ticketVote left join fetch ticketVote.user")
    List<TicketVote> findAllWithToOneRelationships();

    @Query("select ticketVote from TicketVote ticketVote left join fetch ticketVote.user where ticketVote.id =:id")
    Optional<TicketVote> findOneWithToOneRelationships(@Param("id") Long id);
}
