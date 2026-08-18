package com.diviso.spot_fix.service.impl;

import com.diviso.spot_fix.domain.TicketVote;
import com.diviso.spot_fix.repository.TicketVoteRepository;
import com.diviso.spot_fix.service.TicketVoteService;
import com.diviso.spot_fix.service.dto.TicketVoteDTO;
import com.diviso.spot_fix.service.mapper.TicketVoteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.diviso.spot_fix.domain.TicketVote}.
 */
@Service
@Transactional
public class TicketVoteServiceImpl implements TicketVoteService {

    private static final Logger LOG = LoggerFactory.getLogger(TicketVoteServiceImpl.class);

    private final TicketVoteRepository ticketVoteRepository;

    private final TicketVoteMapper ticketVoteMapper;

    public TicketVoteServiceImpl(TicketVoteRepository ticketVoteRepository, TicketVoteMapper ticketVoteMapper) {
        this.ticketVoteRepository = ticketVoteRepository;
        this.ticketVoteMapper = ticketVoteMapper;
    }

    @Override
    public TicketVoteDTO save(TicketVoteDTO ticketVoteDTO) {
        LOG.debug("Request to save TicketVote : {}", ticketVoteDTO);
        TicketVote ticketVote = ticketVoteMapper.toEntity(ticketVoteDTO);
        ticketVote = ticketVoteRepository.save(ticketVote);
        return ticketVoteMapper.toDto(ticketVote);
    }

    @Override
    public TicketVoteDTO update(TicketVoteDTO ticketVoteDTO) {
        LOG.debug("Request to update TicketVote : {}", ticketVoteDTO);
        TicketVote ticketVote = ticketVoteMapper.toEntity(ticketVoteDTO);
        ticketVote = ticketVoteRepository.save(ticketVote);
        return ticketVoteMapper.toDto(ticketVote);
    }

    @Override
    public Optional<TicketVoteDTO> partialUpdate(TicketVoteDTO ticketVoteDTO) {
        LOG.debug("Request to partially update TicketVote : {}", ticketVoteDTO);

        return ticketVoteRepository
            .findById(ticketVoteDTO.getId())
            .map(existingTicketVote -> {
                ticketVoteMapper.partialUpdate(existingTicketVote, ticketVoteDTO);

                return existingTicketVote;
            })
            .map(ticketVoteRepository::save)
            .map(ticketVoteMapper::toDto);
    }

    public Page<TicketVoteDTO> findAllWithEagerRelationships(Pageable pageable) {
        return ticketVoteRepository.findAllWithEagerRelationships(pageable).map(ticketVoteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TicketVoteDTO> findOne(Long id) {
        LOG.debug("Request to get TicketVote : {}", id);
        return ticketVoteRepository.findOneWithEagerRelationships(id).map(ticketVoteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete TicketVote : {}", id);
        ticketVoteRepository.deleteById(id);
    }
}
