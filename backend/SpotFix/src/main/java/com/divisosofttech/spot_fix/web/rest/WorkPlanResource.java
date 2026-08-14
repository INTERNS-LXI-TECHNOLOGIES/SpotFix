package com.divisosofttech.spot_fix.web.rest;

import com.divisosofttech.spot_fix.domain.criteria.WorkPlanCriteria;
import com.divisosofttech.spot_fix.repository.WorkPlanRepository;
import com.divisosofttech.spot_fix.service.WorkPlanService;
import com.divisosofttech.spot_fix.service.dto.WorkPlanDTO;
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
 * REST controller for managing {@link com.divisosofttech.spot_fix.domain.WorkPlan}.
 */
@RestController
@RequestMapping("/api/work-plans")
public class WorkPlanResource {

    private static final Logger LOG = LoggerFactory.getLogger(WorkPlanResource.class);

    private static final String ENTITY_NAME = "workPlan";

    @Value("${jhipster.clientApp.name:spotfix}")
    private String applicationName;

    private final WorkPlanService workPlanService;

    private final WorkPlanRepository workPlanRepository;

    public WorkPlanResource(WorkPlanService workPlanService, WorkPlanRepository workPlanRepository) {
        this.workPlanService = workPlanService;
        this.workPlanRepository = workPlanRepository;
    }

    /**
     * {@code POST  /work-plans} : Create a new workPlan.
     *
     * @param workPlanDTO the workPlanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workPlanDTO, or with status {@code 400 (Bad Request)} if the workPlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<WorkPlanDTO>> createWorkPlan(@Valid @RequestBody WorkPlanDTO workPlanDTO) throws URISyntaxException {
        LOG.debug("REST request to save WorkPlan : {}", workPlanDTO);
        if (workPlanDTO.getId() != null) {
            throw new BadRequestAlertException("A new workPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return workPlanService
            .save(workPlanDTO)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/work-plans/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /work-plans/:id} : Updates an existing workPlan.
     *
     * @param id the id of the workPlanDTO to save.
     * @param workPlanDTO the workPlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workPlanDTO,
     * or with status {@code 400 (Bad Request)} if the workPlanDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workPlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<WorkPlanDTO>> updateWorkPlan(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WorkPlanDTO workPlanDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update WorkPlan : {}, {}", id, workPlanDTO);
        if (workPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workPlanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return workPlanRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return workPlanService
                    .update(workPlanDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity.ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /work-plans/:id} : Partial updates given fields of an existing workPlan, field will ignore if it is null
     *
     * @param id the id of the workPlanDTO to save.
     * @param workPlanDTO the workPlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workPlanDTO,
     * or with status {@code 400 (Bad Request)} if the workPlanDTO is not valid,
     * or with status {@code 404 (Not Found)} if the workPlanDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the workPlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<WorkPlanDTO>> partialUpdateWorkPlan(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WorkPlanDTO workPlanDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update WorkPlan partially : {}, {}", id, workPlanDTO);
        if (workPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workPlanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return workPlanRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<WorkPlanDTO> result = workPlanService.partialUpdate(workPlanDTO);

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
     * {@code GET  /work-plans} : get all the Work Plans.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Work Plans in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<WorkPlanDTO>>> getAllWorkPlans(
        WorkPlanCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        LOG.debug("REST request to get WorkPlans by criteria: {}", criteria);
        return workPlanService
            .countByCriteria(criteria)
            .zipWith(workPlanService.findByCriteria(criteria, pageable).collectList())
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
     * {@code GET  /work-plans/count} : count all the workPlans.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public Mono<ResponseEntity<Long>> countWorkPlans(WorkPlanCriteria criteria) {
        LOG.debug("REST request to count WorkPlans by criteria: {}", criteria);
        return workPlanService.countByCriteria(criteria).map(count -> ResponseEntity.status(HttpStatus.OK).body(count));
    }

    /**
     * {@code GET  /work-plans/:id} : get the "id" workPlan.
     *
     * @param id the id of the workPlanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workPlanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<WorkPlanDTO>> getWorkPlan(@PathVariable("id") Long id) {
        LOG.debug("REST request to get WorkPlan : {}", id);
        Mono<WorkPlanDTO> workPlanDTO = workPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workPlanDTO);
    }

    /**
     * {@code DELETE  /work-plans/:id} : delete the "id" workPlan.
     *
     * @param id the id of the workPlanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteWorkPlan(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete WorkPlan : {}", id);
        return workPlanService
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
