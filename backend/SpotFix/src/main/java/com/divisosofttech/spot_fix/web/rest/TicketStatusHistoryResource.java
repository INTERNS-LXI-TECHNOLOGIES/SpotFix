package com.divisosofttech.spot_fix.web.rest;

import com.divisosofttech.spot_fix.domain.criteria.TicketStatusHistoryCriteria;
import com.divisosofttech.spot_fix.repository.TicketStatusHistoryRepository;
import com.divisosofttech.spot_fix.service.TicketStatusHistoryService;
import com.divisosofttech.spot_fix.service.dto.TicketStatusHistoryDTO;
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
 * REST controller for managing {@link com.divisosofttech.spot_fix.domain.TicketStatusHistory}.
 */
@RestController
@RequestMapping("/api/ticket-status-histories")
public class TicketStatusHistoryResource {

    private static final Logger LOG = LoggerFactory.getLogger(TicketStatusHistoryResource.class);

    private static final String ENTITY_NAME = "ticketStatusHistory";

    @Value("${jhipster.clientApp.name:spotfix}")
    private String applicationName;

    private final TicketStatusHistoryService ticketStatusHistoryService;

    private final TicketStatusHistoryRepository ticketStatusHistoryRepository;

    public TicketStatusHistoryResource(
        TicketStatusHistoryService ticketStatusHistoryService,
        TicketStatusHistoryRepository ticketStatusHistoryRepository
    ) {
        this.ticketStatusHistoryService = ticketStatusHistoryService;
        this.ticketStatusHistoryRepository = ticketStatusHistoryRepository;
    }

    /**
     * {@code POST  /ticket-status-histories} : Create a new ticketStatusHistory.
     *
     * @param ticketStatusHistoryDTO the ticketStatusHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketStatusHistoryDTO, or with status {@code 400 (Bad Request)} if the ticketStatusHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<TicketStatusHistoryDTO>> createTicketStatusHistory(
        @Valid @RequestBody TicketStatusHistoryDTO ticketStatusHistoryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save TicketStatusHistory : {}", ticketStatusHistoryDTO);
        if (ticketStatusHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new ticketStatusHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return ticketStatusHistoryService
            .save(ticketStatusHistoryDTO)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/ticket-status-histories/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /ticket-status-histories/:id} : Updates an existing ticketStatusHistory.
     *
     * @param id the id of the ticketStatusHistoryDTO to save.
     * @param ticketStatusHistoryDTO the ticketStatusHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketStatusHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the ticketStatusHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ticketStatusHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<TicketStatusHistoryDTO>> updateTicketStatusHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TicketStatusHistoryDTO ticketStatusHistoryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update TicketStatusHistory : {}, {}", id, ticketStatusHistoryDTO);
        if (ticketStatusHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ticketStatusHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return ticketStatusHistoryRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return ticketStatusHistoryService
                    .update(ticketStatusHistoryDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity.ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /ticket-status-histories/:id} : Partial updates given fields of an existing ticketStatusHistory, field will ignore if it is null
     *
     * @param id the id of the ticketStatusHistoryDTO to save.
     * @param ticketStatusHistoryDTO the ticketStatusHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketStatusHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the ticketStatusHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ticketStatusHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ticketStatusHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<TicketStatusHistoryDTO>> partialUpdateTicketStatusHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TicketStatusHistoryDTO ticketStatusHistoryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update TicketStatusHistory partially : {}, {}", id, ticketStatusHistoryDTO);
        if (ticketStatusHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ticketStatusHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return ticketStatusHistoryRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<TicketStatusHistoryDTO> result = ticketStatusHistoryService.partialUpdate(ticketStatusHistoryDTO);

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
     * {@code GET  /ticket-status-histories} : get all the Ticket Status Histories.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Ticket Status Histories in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<TicketStatusHistoryDTO>>> getAllTicketStatusHistories(
        TicketStatusHistoryCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        LOG.debug("REST request to get TicketStatusHistories by criteria: {}", criteria);
        return ticketStatusHistoryService
            .countByCriteria(criteria)
            .zipWith(ticketStatusHistoryService.findByCriteria(criteria, pageable).collectList())
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
     * {@code GET  /ticket-status-histories/count} : count all the ticketStatusHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public Mono<ResponseEntity<Long>> countTicketStatusHistories(TicketStatusHistoryCriteria criteria) {
        LOG.debug("REST request to count TicketStatusHistories by criteria: {}", criteria);
        return ticketStatusHistoryService.countByCriteria(criteria).map(count -> ResponseEntity.status(HttpStatus.OK).body(count));
    }

    /**
     * {@code GET  /ticket-status-histories/:id} : get the "id" ticketStatusHistory.
     *
     * @param id the id of the ticketStatusHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ticketStatusHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<TicketStatusHistoryDTO>> getTicketStatusHistory(@PathVariable("id") Long id) {
        LOG.debug("REST request to get TicketStatusHistory : {}", id);
        Mono<TicketStatusHistoryDTO> ticketStatusHistoryDTO = ticketStatusHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ticketStatusHistoryDTO);
    }

    /**
     * {@code DELETE  /ticket-status-histories/:id} : delete the "id" ticketStatusHistory.
     *
     * @param id the id of the ticketStatusHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteTicketStatusHistory(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete TicketStatusHistory : {}", id);
        return ticketStatusHistoryService
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
