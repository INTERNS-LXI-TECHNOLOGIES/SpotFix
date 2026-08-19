
package com.diviso.spot_fix.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diviso.spot_fix.domain.Ward;
import com.diviso.spot_fix.domain.enumeration.Priority;
import com.diviso.spot_fix.domain.enumeration.TicketStatus;
import com.diviso.spot_fix.repository.TicketRepository;
import com.diviso.spot_fix.repository.WardRepository;
import com.diviso.spot_fix.service.WardHeatScoreService;
import com.diviso.spot_fix.service.dto.WardHeatScoreDTO;

@Service
@Transactional(readOnly = true)
public class WardHeatScoreServiceImpl implements WardHeatScoreService {

    private final TicketRepository ticketRepository;
    private final WardRepository wardRepository;

    public WardHeatScoreServiceImpl(
        TicketRepository ticketRepository,
        WardRepository wardRepository
    ) {
        this.ticketRepository = ticketRepository;
        this.wardRepository = wardRepository;
    }

    @Override
    public WardHeatScoreDTO calculateHeatScore(Long wardId) {

        Ward ward = wardRepository.findById(wardId)
            .orElseThrow(() ->
                new IllegalArgumentException("Ward not found: " + wardId)
            );

        long total = ticketRepository.countByWard_Id(wardId);

        long low = ticketRepository.countByWard_IdAndPriority(
            wardId, Priority.LOW
        );

        long medium = ticketRepository.countByWard_IdAndPriority(
            wardId, Priority.MEDIUM
        );

        long high = ticketRepository.countByWard_IdAndPriority(
            wardId, Priority.HIGH
        );

        long urgent = ticketRepository.countByWard_IdAndPriority(
            wardId, Priority.URGENT
        );

        long open = ticketRepository.countByWard_IdAndStatus(
            wardId, TicketStatus.OPEN
        );

        long underReview = ticketRepository.countByWard_IdAndStatus(
            wardId, TicketStatus.UNDER_REVIEW
        );

        long approved = ticketRepository.countByWard_IdAndStatus(
            wardId, TicketStatus.APPROVED
        );

        long assigned = ticketRepository.countByWard_IdAndStatus(
            wardId, TicketStatus.ASSIGNED
        );

        long inProgress = ticketRepository.countByWard_IdAndStatus(
            wardId, TicketStatus.IN_PROGRESS
        );

        long resolved = ticketRepository.countByWard_IdAndStatus(
            wardId, TicketStatus.RESOLVED
        );

        long rejected = ticketRepository.countByWard_IdAndStatus(
            wardId, TicketStatus.REJECTED
        );

        long closed = ticketRepository.countByWard_IdAndStatus(
            wardId, TicketStatus.CLOSED
        );

        long unresolved =
            open +
            underReview +
            approved +
            assigned +
            inProgress;

        double priorityScore = total == 0
            ? 0
            : ((low * 1.0)
                + (medium * 2.0)
                + (high * 3.0)
                + (urgent * 4.0))
                / (total * 4.0)
                * 100;

        double unresolvedScore = total == 0
            ? 0
            : ((double) unresolved / total) * 100;

        long heatScore = Math.round(
            (priorityScore * 0.6)
            + (unresolvedScore * 0.4)
        );

        String heatLevel = getHeatLevel(heatScore);

        return new WardHeatScoreDTO(
            ward.getId(),
            ward.getName(),
            total,

            low,
            medium,
            high,
            urgent,

            open,
            underReview,
            approved,
            assigned,
            inProgress,
            resolved,
            rejected,
            closed,

            heatScore,
            heatLevel
        );
    }

    private String getHeatLevel(long score) {

        if (score >= 76) {
            return "CRITICAL";
        }

        if (score >= 51) {
            return "HIGH";
        }

        if (score >= 26) {
            return "MODERATE";
        }

        return "LOW";
    }
}