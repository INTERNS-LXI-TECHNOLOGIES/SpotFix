package com.divisosofttech.spot_fix.service.impl;

import com.divisosofttech.spot_fix.domain.criteria.TicketVoteCriteria;
import com.divisosofttech.spot_fix.repository.TicketVoteRepository;
import com.divisosofttech.spot_fix.service.TicketVoteService;
import com.divisosofttech.spot_fix.service.dto.TicketVoteDTO;
import com.divisosofttech.spot_fix.service.mapper.TicketVoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.divisosofttech.spot_fix.domain.TicketVote}.
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
    public Mono<TicketVoteDTO> save(TicketVoteDTO ticketVoteDTO) {
        LOG.debug("Request to save TicketVote : {}", ticketVoteDTO);
        return ticketVoteRepository.save(ticketVoteMapper.toEntity(ticketVoteDTO)).map(ticketVoteMapper::toDto);
    }

    @Override
    public Mono<TicketVoteDTO> update(TicketVoteDTO ticketVoteDTO) {
        LOG.debug("Request to update TicketVote : {}", ticketVoteDTO);
        return ticketVoteRepository.save(ticketVoteMapper.toEntity(ticketVoteDTO)).map(ticketVoteMapper::toDto);
    }

    @Override
    public Mono<TicketVoteDTO> partialUpdate(TicketVoteDTO ticketVoteDTO) {
        LOG.debug("Request to partially update TicketVote : {}", ticketVoteDTO);

        return ticketVoteRepository
            .findById(ticketVoteDTO.getId())
            .map(existingTicketVote -> {
                ticketVoteMapper.partialUpdate(existingTicketVote, ticketVoteDTO);

                return existingTicketVote;
            })
            .flatMap(ticketVoteRepository::save)
            .map(ticketVoteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<TicketVoteDTO> findByCriteria(TicketVoteCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get all TicketVotes by Criteria");
        return ticketVoteRepository.findByCriteria(criteria, pageable).map(ticketVoteMapper::toDto);
    }

    /**
     * Find the count of ticketVotes by criteria.
     * @param criteria filtering criteria
     * @return the count of ticketVotes
     */
    public Mono<Long> countByCriteria(TicketVoteCriteria criteria) {
        LOG.debug("Request to get the count of all TicketVotes by Criteria");
        return ticketVoteRepository.countByCriteria(criteria);
    }

    public Flux<TicketVoteDTO> findAllWithEagerRelationships(Pageable pageable) {
        return ticketVoteRepository.findAllWithEagerRelationships(pageable).map(ticketVoteMapper::toDto);
    }

    public Mono<Long> countAll() {
        return ticketVoteRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<TicketVoteDTO> findOne(Long id) {
        LOG.debug("Request to get TicketVote : {}", id);
        return ticketVoteRepository.findOneWithEagerRelationships(id).map(ticketVoteMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete TicketVote : {}", id);
        return ticketVoteRepository.deleteById(id);
    }
}
