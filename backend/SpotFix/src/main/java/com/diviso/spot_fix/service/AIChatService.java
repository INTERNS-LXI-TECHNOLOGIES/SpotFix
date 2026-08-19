package com.divisosofttech.spot_fix.service;

import com.divisosofttech.spot_fix.service.dto.TicketDTO;

public interface AIChatService {
    
    public TicketDTO genarateTicketUsingLLM(TicketDTO ticketDTO);
    
}
