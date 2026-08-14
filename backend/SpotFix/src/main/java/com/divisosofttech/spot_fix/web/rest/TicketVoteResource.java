package com.divisosofttech.spot_fix.web.rest;

import com.divisosofttech.spot_fix.domain.criteria.TicketVoteCriteria;
import com.divisosofttech.spot_fix.repository.TicketVoteRepository;
import com.divisosofttech.spot_fix.service.TicketVoteService;
import com.divisosofttech.spot_fix.service.dto.TicketVoteDTO;
import com.divisosofttech.spot_fix.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.ForwardedHeaderUtils;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.divisosofttech.spot_fix.domain.TicketVote}.
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

    public TicketVoteResource(TicketVoteService ticketVoteService, TicketVoteRepository ticketVoteRepository) {
        this.ticketVoteService = ticketVoteService;
        this.ticketVoteRepository = ticketVoteRepository;
    }

    /**
     * {@code POST  /ticket-votes} : Create a new ticketVote.
     *
     * @param ticketVoteDTO the ticketVoteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketVoteDTO, or with status {@code 400 (Bad Request)} if the ticketVote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<TicketVoteDTO>> createTicketVote(@Valid @RequestBody TicketVoteDTO ticketVoteDTO) throws URISyntaxException {
        LOG.debug("REST request to save TicketVote : {}", ticketVoteDTO);
        if (ticketVoteDTO.getId() != null) {
            throw new BadRequestAlertException("A new ticketVote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return ticketVoteService    
            .save(ticketVoteDTO)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/ticket-votes/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
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
    public Mono<ResponseEntity<TicketVoteDTO>> updateTicketVote(
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

        return ticketVoteRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return ticketVoteService
                    .update(ticketVoteDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity.ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
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
    public Mono<ResponseEntity<TicketVoteDTO>> partialUpdateTicketVote(
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

        return ticketVoteRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<TicketVoteDTO> result = ticketVoteService.partialUpdate(ticketVoteDTO);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity.ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /ticket-votes} : get all the Ticket Votes.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Ticket Votes in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<TicketVoteDTO>>> getAllTicketVotes(
        TicketVoteCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        LOG.debug("REST request to get TicketVotes by criteria: {}", criteria);
        return ticketVoteService
            .countByCriteria(criteria)
            .zipWith(ticketVoteService.findByCriteria(criteria, pageable).collectList())
            .map(countWithEntities ->
                ResponseEntity.ok()
                    .headers(
                        PaginationUtil.generatePaginationHttpHeaders(
                            ForwardedHeaderUtils.adaptFromForwardedHeaders(request.getURI(), request.getHeaders()),
                            new PageImpl<>(countWithEntities.getT2(), pageable, countWithEntities.getT1())
                        )
                    )
                    .body(countWithEntities.getT2())
            );
    }

    /**
     * {@code GET  /ticket-votes/count} : count all the ticketVotes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public Mono<ResponseEntity<Long>> countTicketVotes(TicketVoteCriteria criteria) {
        LOG.debug("REST request to count TicketVotes by criteria: {}", criteria);
        return ticketVoteService.countByCriteria(criteria).map(count -> ResponseEntity.status(HttpStatus.OK).body(count));
    }

    /**
     * {@code GET  /ticket-votes/:id} : get the "id" ticketVote.
     *
     * @param id the id of the ticketVoteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ticketVoteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<TicketVoteDTO>> getTicketVote(@PathVariable("id") Long id) {
        LOG.debug("REST request to get TicketVote : {}", id);
        Mono<TicketVoteDTO> ticketVoteDTO = ticketVoteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ticketVoteDTO);
    }

    /**
     * {@code DELETE  /ticket-votes/:id} : delete the "id" ticketVote.
     *
     * @param id the id of the ticketVoteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteTicketVote(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete TicketVote : {}", id);
        return ticketVoteService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                        .build()
                )
            );
    }
}
