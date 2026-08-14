package com.divisosofttech.spot_fix.service.impl;

import com.divisosofttech.spot_fix.domain.criteria.TicketStatusHistoryCriteria;
import com.divisosofttech.spot_fix.repository.TicketStatusHistoryRepository;
import com.divisosofttech.spot_fix.service.TicketStatusHistoryService;
import com.divisosofttech.spot_fix.service.dto.TicketStatusHistoryDTO;
import com.divisosofttech.spot_fix.service.mapper.TicketStatusHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.divisosofttech.spot_fix.domain.TicketStatusHistory}.
 */
@Service
@Transactional
public class TicketStatusHistoryServiceImpl implements TicketStatusHistoryService {

    private static final Logger LOG = LoggerFactory.getLogger(TicketStatusHistoryServiceImpl.class);

    private final TicketStatusHistoryRepository ticketStatusHistoryRepository;

    private final TicketStatusHistoryMapper ticketStatusHistoryMapper;

    public TicketStatusHistoryServiceImpl(
        TicketStatusHistoryRepository ticketStatusHistoryRepository,
        TicketStatusHistoryMapper ticketStatusHistoryMapper
    ) {
        this.ticketStatusHistoryRepository = ticketStatusHistoryRepository;
        this.ticketStatusHistoryMapper = ticketStatusHistoryMapper;
    }

    @Override
    public Mono<TicketStatusHistoryDTO> save(TicketStatusHistoryDTO ticketStatusHistoryDTO) {
        LOG.debug("Request to save TicketStatusHistory : {}", ticketStatusHistoryDTO);
        return ticketStatusHistoryRepository
            .save(ticketStatusHistoryMapper.toEntity(ticketStatusHistoryDTO))
            .map(ticketStatusHistoryMapper::toDto);
    }

    @Override
    public Mono<TicketStatusHistoryDTO> update(TicketStatusHistoryDTO ticketStatusHistoryDTO) {
        LOG.debug("Request to update TicketStatusHistory : {}", ticketStatusHistoryDTO);
        return ticketStatusHistoryRepository
            .save(ticketStatusHistoryMapper.toEntity(ticketStatusHistoryDTO))
            .map(ticketStatusHistoryMapper::toDto);
    }

    @Override
    public Mono<TicketStatusHistoryDTO> partialUpdate(TicketStatusHistoryDTO ticketStatusHistoryDTO) {
        LOG.debug("Request to partially update TicketStatusHistory : {}", ticketStatusHistoryDTO);

        return ticketStatusHistoryRepository
            .findById(ticketStatusHistoryDTO.getId())
            .map(existingTicketStatusHistory -> {
                ticketStatusHistoryMapper.partialUpdate(existingTicketStatusHistory, ticketStatusHistoryDTO);

                return existingTicketStatusHistory;
            })
            .flatMap(ticketStatusHistoryRepository::save)
            .map(ticketStatusHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<TicketStatusHistoryDTO> findByCriteria(TicketStatusHistoryCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get all TicketStatusHistories by Criteria");
        return ticketStatusHistoryRepository.findByCriteria(criteria, pageable).map(ticketStatusHistoryMapper::toDto);
    }

    /**
     * Find the count of ticketStatusHistories by criteria.
     * @param criteria filtering criteria
     * @return the count of ticketStatusHistories
     */
    public Mono<Long> countByCriteria(TicketStatusHistoryCriteria criteria) {
        LOG.debug("Request to get the count of all TicketStatusHistories by Criteria");
        return ticketStatusHistoryRepository.countByCriteria(criteria);
    }

    public Flux<TicketStatusHistoryDTO> findAllWithEagerRelationships(Pageable pageable) {
        return ticketStatusHistoryRepository.findAllWithEagerRelationships(pageable).map(ticketStatusHistoryMapper::toDto);
    }

    public Mono<Long> countAll() {
        return ticketStatusHistoryRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<TicketStatusHistoryDTO> findOne(Long id) {
        LOG.debug("Request to get TicketStatusHistory : {}", id);
        return ticketStatusHistoryRepository.findOneWithEagerRelationships(id).map(ticketStatusHistoryMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete TicketStatusHistory : {}", id);
        return ticketStatusHistoryRepository.deleteById(id);
    }
}
