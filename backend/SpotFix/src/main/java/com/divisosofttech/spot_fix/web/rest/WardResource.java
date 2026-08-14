package com.divisosofttech.spot_fix.web.rest;

import com.divisosofttech.spot_fix.domain.criteria.WardCriteria;
import com.divisosofttech.spot_fix.repository.WardRepository;
import com.divisosofttech.spot_fix.service.WardService;
import com.divisosofttech.spot_fix.service.dto.WardDTO;
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
 * REST controller for managing {@link com.divisosofttech.spot_fix.domain.Ward}.
 */
@RestController
@RequestMapping("/api/wards")
public class WardResource {

    private static final Logger LOG = LoggerFactory.getLogger(WardResource.class);

    private static final String ENTITY_NAME = "ward";

    @Value("${jhipster.clientApp.name:spotfix}")
    private String applicationName;

    private final WardService wardService;

    private final WardRepository wardRepository;

    public WardResource(WardService wardService, WardRepository wardRepository) {
        this.wardService = wardService;
        this.wardRepository = wardRepository;
    }

    /**
     * {@code POST  /wards} : Create a new ward.
     *
     * @param wardDTO the wardDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wardDTO, or with status {@code 400 (Bad Request)} if the ward has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<WardDTO>> createWard(@Valid @RequestBody WardDTO wardDTO) throws URISyntaxException {
        LOG.debug("REST request to save Ward : {}", wardDTO);
        if (wardDTO.getId() != null) {
            throw new BadRequestAlertException("A new ward cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return wardService
            .save(wardDTO)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/wards/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /wards/:id} : Updates an existing ward.
     *
     * @param id the id of the wardDTO to save.
     * @param wardDTO the wardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wardDTO,
     * or with status {@code 400 (Bad Request)} if the wardDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<WardDTO>> updateWard(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WardDTO wardDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Ward : {}, {}", id, wardDTO);
        if (wardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return wardRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return wardService
                    .update(wardDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity.ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /wards/:id} : Partial updates given fields of an existing ward, field will ignore if it is null
     *
     * @param id the id of the wardDTO to save.
     * @param wardDTO the wardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wardDTO,
     * or with status {@code 400 (Bad Request)} if the wardDTO is not valid,
     * or with status {@code 404 (Not Found)} if the wardDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the wardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<WardDTO>> partialUpdateWard(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WardDTO wardDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Ward partially : {}, {}", id, wardDTO);
        if (wardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return wardRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<WardDTO> result = wardService.partialUpdate(wardDTO);

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
     * {@code GET  /wards} : get all the Wards.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Wards in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<WardDTO>>> getAllWards(
        WardCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        LOG.debug("REST request to get Wards by criteria: {}", criteria);
        return wardService
            .countByCriteria(criteria)
            .zipWith(wardService.findByCriteria(criteria, pageable).collectList())
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
     * {@code GET  /wards/count} : count all the wards.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public Mono<ResponseEntity<Long>> countWards(WardCriteria criteria) {
        LOG.debug("REST request to count Wards by criteria: {}", criteria);
        return wardService.countByCriteria(criteria).map(count -> ResponseEntity.status(HttpStatus.OK).body(count));
    }

    /**
     * {@code GET  /wards/:id} : get the "id" ward.
     *
     * @param id the id of the wardDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<WardDTO>> getWard(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Ward : {}", id);
        Mono<WardDTO> wardDTO = wardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wardDTO);
    }

    /**
     * {@code DELETE  /wards/:id} : delete the "id" ward.
     *
     * @param id the id of the wardDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteWard(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Ward : {}", id);
        return wardService
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
