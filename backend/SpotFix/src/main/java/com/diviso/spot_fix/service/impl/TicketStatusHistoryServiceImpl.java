package com.diviso.spot_fix.service.impl;

import com.diviso.spot_fix.domain.TicketStatusHistory;
import com.diviso.spot_fix.repository.TicketStatusHistoryRepository;
import com.diviso.spot_fix.service.TicketStatusHistoryService;
import com.diviso.spot_fix.service.dto.TicketStatusHistoryDTO;
import com.diviso.spot_fix.service.mapper.TicketStatusHistoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.diviso.spot_fix.domain.TicketStatusHistory}.
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
    public TicketStatusHistoryDTO save(TicketStatusHistoryDTO ticketStatusHistoryDTO) {
        LOG.debug("Request to save TicketStatusHistory : {}", ticketStatusHistoryDTO);
        TicketStatusHistory ticketStatusHistory = ticketStatusHistoryMapper.toEntity(ticketStatusHistoryDTO);
        ticketStatusHistory = ticketStatusHistoryRepository.save(ticketStatusHistory);
        return ticketStatusHistoryMapper.toDto(ticketStatusHistory);
    }

    @Override
    public TicketStatusHistoryDTO update(TicketStatusHistoryDTO ticketStatusHistoryDTO) {
        LOG.debug("Request to update TicketStatusHistory : {}", ticketStatusHistoryDTO);
        TicketStatusHistory ticketStatusHistory = ticketStatusHistoryMapper.toEntity(ticketStatusHistoryDTO);
        ticketStatusHistory = ticketStatusHistoryRepository.save(ticketStatusHistory);
        return ticketStatusHistoryMapper.toDto(ticketStatusHistory);
    }

    @Override
    public Optional<TicketStatusHistoryDTO> partialUpdate(TicketStatusHistoryDTO ticketStatusHistoryDTO) {
        LOG.debug("Request to partially update TicketStatusHistory : {}", ticketStatusHistoryDTO);

        return ticketStatusHistoryRepository
            .findById(ticketStatusHistoryDTO.getId())
            .map(existingTicketStatusHistory -> {
                ticketStatusHistoryMapper.partialUpdate(existingTicketStatusHistory, ticketStatusHistoryDTO);

                return existingTicketStatusHistory;
            })
            .map(ticketStatusHistoryRepository::save)
            .map(ticketStatusHistoryMapper::toDto);
    }

    public Page<TicketStatusHistoryDTO> findAllWithEagerRelationships(Pageable pageable) {
        return ticketStatusHistoryRepository.findAllWithEagerRelationships(pageable).map(ticketStatusHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TicketStatusHistoryDTO> findOne(Long id) {
        LOG.debug("Request to get TicketStatusHistory : {}", id);
        return ticketStatusHistoryRepository.findOneWithEagerRelationships(id).map(ticketStatusHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete TicketStatusHistory : {}", id);
        ticketStatusHistoryRepository.deleteById(id);
    }
}
