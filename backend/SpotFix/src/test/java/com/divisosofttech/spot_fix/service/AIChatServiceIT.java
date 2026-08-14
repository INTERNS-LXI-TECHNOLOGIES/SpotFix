
package com.divisosofttech.spot_fix.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.divisosofttech.spot_fix.service.dto.TicketDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
class AIChatServiceIT {

    @Autowired
    private AIChatService aiChatService;

    @Test
    @Timeout(1000)
    void shouldGenerateTicketUsingLLM() {

        // Arrange
        TicketDTO ticketDTO = new TicketDTO();

        ticketDTO.setTitle("Pothole");
        ticketDTO.setDescription(
            "There is a very large pothole near the main bus stand. " +
            "It is dangerous for vehicles and pedestrians."
        );

        // Act
        TicketDTO generatedTicket =
            aiChatService.genarateTicketUsingLLM(ticketDTO);

        // Assert
        assertThat(generatedTicket).isNotNull();

        System.out.println("========== AI RESPONSE ==========");
        System.out.println("Title       : " + generatedTicket.getTitle());
        System.out.println("Description : " + generatedTicket.getDescription());
        System.out.println("Status      : " + generatedTicket.getStatus());
        System.out.println("Priority    : " + generatedTicket.getPriority());
        System.out.println("Category    : " + generatedTicket.getCategory());
        System.out.println("AI Summary  : " + generatedTicket.getAiSummary());
        System.out.println("Confidence  : " + generatedTicket.getAiConfidence());
        System.out.println("Department  : " + generatedTicket.getAssignedDepartment());
        System.out.println("=================================");

        assertThat(generatedTicket.getTitle()).isNotBlank();
        assertThat(generatedTicket.getDescription()).isNotBlank();
        assertThat(generatedTicket.getStatus()).isNotNull();
        assertThat(generatedTicket.getPriority()).isNotNull();
        assertThat(generatedTicket.getCategory()).isNotNull();
        assertThat(generatedTicket.getAiSummary()).isNotBlank();
        assertThat(generatedTicket.getAiConfidence()).isNotNull();
    }
}
