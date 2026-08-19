package com.diviso.spot_fix.web.rest;

import com.diviso.spot_fix.repository.TicketVoteRepository;
import com.diviso.spot_fix.service.TicketVoteQueryService;
import com.diviso.spot_fix.service.TicketVoteService;
import com.diviso.spot_fix.service.criteria.TicketVoteCriteria;
import com.diviso.spot_fix.service.dto.TicketVoteDTO;
import com.diviso.spot_fix.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.diviso.spot_fix.domain.TicketVote}.
 */
@RestController
@RequestMapping("/api/ticket-votes")
public class TicketVoteResource {

    private static final Logger LOG = LoggerFactory.getLogger(TicketVoteResource.class);

    private static final String ENTITY_NAME = "ticketVote";

    @Value("${jhipster.clientApp.name:spotfix}")
    private String applicationName;

    private final TicketVoteService ticketVoteService;

    private final TicketVoteRepository ticketVoteRepository;

    private final TicketVoteQueryService ticketVoteQueryService;

    public TicketVoteResource(
        TicketVoteService ticketVoteService,
        TicketVoteRepository ticketVoteRepository,
        TicketVoteQueryService ticketVoteQueryService
    ) {
        this.ticketVoteService = ticketVoteService;
        this.ticketVoteRepository = ticketVoteRepository;
        this.ticketVoteQueryService = ticketVoteQueryService;
    }

    /**
     * {@code POST  /ticket-votes} : Create a new ticketVote.
     *
     * @param ticketVoteDTO the ticketVoteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketVoteDTO, or with status {@code 400 (Bad Request)} if the ticketVote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TicketVoteDTO> createTicketVote(@Valid @RequestBody TicketVoteDTO ticketVoteDTO) throws URISyntaxException {
        LOG.debug("REST request to save TicketVote : {}", ticketVoteDTO);
        if (ticketVoteDTO.getId() != null) {
            throw new BadRequestAlertException("A new ticketVote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ticketVoteDTO = ticketVoteService.save(ticketVoteDTO);
        return ResponseEntity.created(new URI("/api/ticket-votes/" + ticketVoteDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ticketVoteDTO.getId().toString()))
            .body(ticketVoteDTO);
    }

    /**
     * {@code PUT  /ticket-votes/:id} : Updates an existing ticketVote.
     *
     * @param id the id of the ticketVoteDTO to save.
     * @param ticketVoteDTO the ticketVoteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketVoteDTO,
     * or with status {@code 400 (Bad Request)} if the ticketVoteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ticketVoteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TicketVoteDTO> updateTicketVote(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TicketVoteDTO ticketVoteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update TicketVote : {}, {}", id, ticketVoteDTO);
        if (ticketVoteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ticketVoteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ticketVoteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ticketVoteDTO = ticketVoteService.update(ticketVoteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ticketVoteDTO.getId().toString()))
            .body(ticketVoteDTO);
    }

    /**
     * {@code PATCH  /ticket-votes/:id} : Partial updates given fields of an existing ticketVote, field will ignore if it is null
     *
     * @param id the id of the ticketVoteDTO to save.
     * @param ticketVoteDTO the ticketVoteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketVoteDTO,
     * or with status {@code 400 (Bad Request)} if the ticketVoteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ticketVoteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ticketVoteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TicketVoteDTO> partialUpdateTicketVote(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TicketVoteDTO ticketVoteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update TicketVote partially : {}, {}", id, ticketVoteDTO);
        if (ticketVoteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ticketVoteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ticketVoteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TicketVoteDTO> result = ticketVoteService.partialUpdate(ticketVoteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ticketVoteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ticket-votes} : get all the Ticket Votes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Ticket Votes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TicketVoteDTO>> getAllTicketVotes(
        TicketVoteCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get TicketVotes by criteria: {}", criteria);

        Page<TicketVoteDTO> page = ticketVoteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ticket-votes/count} : count all the ticketVotes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTicketVotes(TicketVoteCriteria criteria) {
        LOG.debug("REST request to count TicketVotes by criteria: {}", criteria);
        return ResponseEntity.ok().body(ticketVoteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ticket-votes/:id} : get the "id" ticketVote.
     *
     * @param id the id of the ticketVoteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ticketVoteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TicketVoteDTO> getTicketVote(@PathVariable("id") Long id) {
        LOG.debug("REST request to get TicketVote : {}", id);
        Optional<TicketVoteDTO> ticketVoteDTO = ticketVoteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ticketVoteDTO);
    }

    /**
     * {@code DELETE  /ticket-votes/:id} : delete the "id" ticketVote.
     *
     * @param id the id of the ticketVoteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicketVote(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete TicketVote : {}", id);
        ticketVoteService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
