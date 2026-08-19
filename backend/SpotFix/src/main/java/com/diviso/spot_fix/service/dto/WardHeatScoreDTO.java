package com.diviso.spot_fix.service.dto;

public record WardHeatScoreDTO(Long wardId, String wardName,  long totalTickets, long lowPriorityTickets, long mediumPriorityTickets, long highPriorityTickets, long urgentPriorityTickets, long openTickets, long underReviewTickets, long approvedTickets, long assignedTickets, long inProgressTickets, long resolvedTickets, long rejectedTickets, long closedTickets, long heatScore , String heatLevel) {

    
}
