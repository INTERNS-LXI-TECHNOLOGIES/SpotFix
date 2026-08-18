package com.diviso.spot_fix.web.rest;

import com.diviso.spot_fix.repository.WorkPlanRepository;
import com.diviso.spot_fix.service.WorkPlanQueryService;
import com.diviso.spot_fix.service.WorkPlanService;
import com.diviso.spot_fix.service.criteria.WorkPlanCriteria;
import com.diviso.spot_fix.service.dto.WorkPlanDTO;
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
 * REST controller for managing {@link com.diviso.spot_fix.domain.WorkPlan}.
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

    private final WorkPlanQueryService workPlanQueryService;

    public WorkPlanResource(
        WorkPlanService workPlanService,
        WorkPlanRepository workPlanRepository,
        WorkPlanQueryService workPlanQueryService
    ) {
        this.workPlanService = workPlanService;
        this.workPlanRepository = workPlanRepository;
        this.workPlanQueryService = workPlanQueryService;
    }

    /**
     * {@code POST  /work-plans} : Create a new workPlan.
     *
     * @param workPlanDTO the workPlanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workPlanDTO, or with status {@code 400 (Bad Request)} if the workPlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<WorkPlanDTO> createWorkPlan(@Valid @RequestBody WorkPlanDTO workPlanDTO) throws URISyntaxException {
        LOG.debug("REST request to save WorkPlan : {}", workPlanDTO);
        if (workPlanDTO.getId() != null) {
            throw new BadRequestAlertException("A new workPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        workPlanDTO = workPlanService.save(workPlanDTO);
        return ResponseEntity.created(new URI("/api/work-plans/" + workPlanDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, workPlanDTO.getId().toString()))
            .body(workPlanDTO);
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
    public ResponseEntity<WorkPlanDTO> updateWorkPlan(
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

        if (!workPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        workPlanDTO = workPlanService.update(workPlanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workPlanDTO.getId().toString()))
            .body(workPlanDTO);
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
    public ResponseEntity<WorkPlanDTO> partialUpdateWorkPlan(
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

        if (!workPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorkPlanDTO> result = workPlanService.partialUpdate(workPlanDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workPlanDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /work-plans} : get all the Work Plans.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Work Plans in body.
     */
    @GetMapping("")
    public ResponseEntity<List<WorkPlanDTO>> getAllWorkPlans(
        WorkPlanCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get WorkPlans by criteria: {}", criteria);

        Page<WorkPlanDTO> page = workPlanQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /work-plans/count} : count all the workPlans.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countWorkPlans(WorkPlanCriteria criteria) {
        LOG.debug("REST request to count WorkPlans by criteria: {}", criteria);
        return ResponseEntity.ok().body(workPlanQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /work-plans/:id} : get the "id" workPlan.
     *
     * @param id the id of the workPlanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workPlanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<WorkPlanDTO> getWorkPlan(@PathVariable("id") Long id) {
        LOG.debug("REST request to get WorkPlan : {}", id);
        Optional<WorkPlanDTO> workPlanDTO = workPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workPlanDTO);
    }

    /**
     * {@code DELETE  /work-plans/:id} : delete the "id" workPlan.
     *
     * @param id the id of the workPlanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkPlan(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete WorkPlan : {}", id);
        workPlanService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
