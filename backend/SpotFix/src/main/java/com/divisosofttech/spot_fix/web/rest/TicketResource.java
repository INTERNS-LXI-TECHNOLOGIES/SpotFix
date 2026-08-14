package com.divisosofttech.spot_fix.web.rest;

import com.divisosofttech.spot_fix.domain.criteria.TicketCriteria;
import com.divisosofttech.spot_fix.repository.TicketRepository;
import com.divisosofttech.spot_fix.service.TicketService;
import com.divisosofttech.spot_fix.service.dto.TicketDTO;
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
 * REST controller for managing {@link com.divisosofttech.spot_fix.domain.Ticket}.
 */
@RestController
@RequestMapping("/api/tickets")
public class TicketResource {

    private static final Logger LOG = LoggerFactory.getLogger(TicketResource.class);

    private static final String ENTITY_NAME = "ticket";

    @Value("${jhipster.clientApp.name:spotfix}")
    private String applicationName;

    private final TicketService ticketService;

    private final TicketRepository ticketRepository;

    public TicketResource(TicketService ticketService, TicketRepository ticketRepository) {
        this.ticketService = ticketService;
        this.ticketRepository = ticketRepository;
    }

    /**
     * {@code POST  /tickets} : Create a new ticket.
     *
     * @param ticketDTO the ticketDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketDTO, or with status {@code 400 (Bad Request)} if the ticket has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<TicketDTO>> createTicket(@Valid @RequestBody TicketDTO ticketDTO) throws URISyntaxException {
        LOG.debug("REST request to save Ticket : {}", ticketDTO);
        if (ticketDTO.getId() != null) {
            throw new BadRequestAlertException("A new ticket cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return ticketService
            .save(ticketDTO)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/tickets/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /tickets/:id} : Updates an existing ticket.
     *
     * @param id the id of the ticketDTO to save.
     * @param ticketDTO the ticketDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketDTO,
     * or with status {@code 400 (Bad Request)} if the ticketDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ticketDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<TicketDTO>> updateTicket(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TicketDTO ticketDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Ticket : {}, {}", id, ticketDTO);
        if (ticketDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ticketDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return ticketRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return ticketService
                    .update(ticketDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity.ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /tickets/:id} : Partial updates given fields of an existing ticket, field will ignore if it is null
     *
     * @param id the id of the ticketDTO to save.
     * @param ticketDTO the ticketDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketDTO,
     * or with status {@code 400 (Bad Request)} if the ticketDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ticketDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ticketDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<TicketDTO>> partialUpdateTicket(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TicketDTO ticketDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Ticket partially : {}, {}", id, ticketDTO);
        if (ticketDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ticketDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return ticketRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<TicketDTO> result = ticketService.partialUpdate(ticketDTO);

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
     * {@code GET  /tickets} : get all the Tickets.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Tickets in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<TicketDTO>>> getAllTickets(
        TicketCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        LOG.debug("REST request to get Tickets by criteria: {}", criteria);
        return ticketService
            .countByCriteria(criteria)
            .zipWith(ticketService.findByCriteria(criteria, pageable).collectList())
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
     * {@code GET  /tickets/count} : count all the tickets.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public Mono<ResponseEntity<Long>> countTickets(TicketCriteria criteria) {
        LOG.debug("REST request to count Tickets by criteria: {}", criteria);
        return ticketService.countByCriteria(criteria).map(count -> ResponseEntity.status(HttpStatus.OK).body(count));
    }

    /**
     * {@code GET  /tickets/:id} : get the "id" ticket.
     *
     * @param id the id of the ticketDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ticketDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<TicketDTO>> getTicket(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Ticket : {}", id);
        Mono<TicketDTO> ticketDTO = ticketService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ticketDTO);
    }

    /**
     * {@code DELETE  /tickets/:id} : delete the "id" ticket.
     *
     * @param id the id of the ticketDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteTicket(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Ticket : {}", id);
        return ticketService
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
