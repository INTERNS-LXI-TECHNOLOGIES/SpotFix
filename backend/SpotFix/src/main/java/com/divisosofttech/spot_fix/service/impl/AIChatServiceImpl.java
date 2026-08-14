package com.divisosofttech.spot_fix.service.impl;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.divisosofttech.spot_fix.service.AIChatService;
import com.divisosofttech.spot_fix.service.dto.TicketDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service

public class AIChatServiceImpl implements AIChatService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;
public AIChatServiceImpl( ChatClient chatClient, ObjectMapper objectMapper ) { this.chatClient = chatClient; this.objectMapper = objectMapper; }
    @SuppressWarnings("null")
    @Override
    public TicketDTO genarateTicketUsingLLM(TicketDTO ticketDto) {
          try {
            String ticketJson = objectMapper.writeValueAsString(ticketDto);

     String prompt = """
    You are an AI assistant for a civic complaint management system.

    Your task is to analyze the incoming TicketDTO and return a completed TicketDTO.

    The input TicketDTO may contain only a few fields. Use the available
    information to complete the ticket.

    AI responsibilities:
    - Generate a clear and concise title when the title is missing or unclear.
    - Improve the description while preserving the original meaning.
    - Determine the appropriate TicketCategory.
    - Determine the appropriate Priority.
    - Determine the appropriate TicketStatus.
    - Determine the appropriate Visibility.
    - Generate a concise aiSummary.
    - Determine assignedDepartment only when it can be reasonably inferred.
    - Set aiConfidence between 0.0 and 1.0.

    IMPORTANT:
    - Preserve all information supplied by the user.
    - Do not invent information.
    - Do not invent database IDs.
    - Do not invent users.
    - Do not invent locations.
    - Do not invent wards.
    - Do not invent dates.

    Backend-controlled fields:
    - id
    - createdDate
    - updatedDate
    - expectedResolutionDate
    - resolvedDate
    - deleted
    - deletedDate
    - reportedBy
    - location
    - ward

    These fields MUST remain exactly as provided in the input.

    For assignedDepartment:
    - Only assign a department when the complaint clearly indicates one.
    - Otherwise preserve the existing value or leave it null.

    Enum values MUST be valid Java enum values for:
    - TicketStatus
    - Priority
    - Visibility
    - TicketCategory

    Return the completed TicketDTO as structured data.
    Do not return Markdown.
    Do not return explanations.
    """;

            return chatClient
                .prompt()
                .system(prompt)
                .user("""
                    Here is the ticket:

                    %s

                    Complete this ticket and return the TicketDTO.
                    """.formatted(ticketJson))
                .call()
                .entity(TicketDTO.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert TicketDTO to JSON", e);
        }
    }
}
