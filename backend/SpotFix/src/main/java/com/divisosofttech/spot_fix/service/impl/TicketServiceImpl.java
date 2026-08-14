
package com.divisosofttech.spot_fix.service.impl;

import com.divisosofttech.spot_fix.domain.criteria.TicketCriteria;
import com.divisosofttech.spot_fix.repository.TicketRepository;
import com.divisosofttech.spot_fix.service.PriorityCalculatorService;
import com.divisosofttech.spot_fix.service.TicketService;
import com.divisosofttech.spot_fix.service.dto.TicketDTO;
import com.divisosofttech.spot_fix.service.mapper.TicketMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule.Priority;
import com.divisosofttech.spot_fix.service.AIChatService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Service Implementation for managing {@link com.divisosofttech.spot_fix.domain.Ticket}.
 */
@Service
@Transactional
public class TicketServiceImpl implements TicketService {

     private static final Logger LOG = LoggerFactory.getLogger(TicketServiceImpl.class);
    private final AIChatService aiChatService;
    private final TicketRepository ticketRepository;

    private final TicketMapper ticketMapper;
    private final PriorityCalculatorService priorityCalculatorService;

    public TicketServiceImpl(TicketRepository ticketRepository, TicketMapper ticketMapper, PriorityCalculatorService priorityCalculatorService, AIChatService aiChatService) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.priorityCalculatorService = priorityCalculatorService;
        this.aiChatService = aiChatService;
     }

    @Override
public Mono<TicketDTO> save(TicketDTO ticketDTO) {

    LOG.debug("Request to save Ticket : {}", ticketDTO);

    com.divisosofttech.spot_fix.domain.enumeration.Priority calculatedPriority =
        priorityCalculatorService.calculatePriority(
            ticketDTO.getTitle(),
            ticketDTO.getDescription()
        );

    ticketDTO.setPriority(calculatedPriority);

    LOG.info(
        "Automatic priority calculated for ticket '{}': {}",
        ticketDTO.getTitle(),
        calculatedPriority
    );
    return Mono.fromCallable(() ->
        aiChatService.genarateTicketUsingLLM(ticketDTO)
    )
    .subscribeOn(Schedulers.boundedElastic())
    .flatMap(generatedTicket ->
        ticketRepository
            .save(ticketMapper.toEntity(generatedTicket))
    )
    .map(ticketMapper::toDto);
}

  @Override
    @Transactional(readOnly = true)
    public Flux<TicketDTO> findByCriteria(TicketCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get all Tickets by Criteria");
        return ticketRepository.findByCriteria(criteria, pageable).map(ticketMapper::toDto);
    }

    /**
     * Find the count of tickets by criteria.
     * @param criteria filtering criteria
     * @return the count of tickets
     */
    public Mono<Long> countByCriteria(TicketCriteria criteria) {
        LOG.debug("Request to get the count of all Tickets by Criteria");
        return ticketRepository.countByCriteria(criteria);
    }

    public Flux<TicketDTO> findAllWithEagerRelationships(Pageable pageable) {
        return ticketRepository.findAllWithEagerRelationships(pageable).map(ticketMapper::toDto);
    }

    public Mono<Long> countAll() {
        return ticketRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<TicketDTO> findOne(Long id) {
        LOG.debug("Request to get Ticket : {}", id);
        return ticketRepository.findOneWithEagerRelationships(id).map(ticketMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete Ticket : {}", id);
        return ticketRepository.deleteById(id);
    }

    @Override
    public Mono<TicketDTO> update(TicketDTO ticketDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Mono<TicketDTO> partialUpdate(TicketDTO ticketDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'partialUpdate'");
    }
}