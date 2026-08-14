package com.divisosofttech.spot_fix.repository;

import com.divisosofttech.spot_fix.domain.Ticket;
import com.divisosofttech.spot_fix.domain.criteria.TicketCriteria;
import com.divisosofttech.spot_fix.repository.rowmapper.ColumnConverter;
import com.divisosofttech.spot_fix.repository.rowmapper.DepartmentRowMapper;
import com.divisosofttech.spot_fix.repository.rowmapper.LocationRowMapper;
import com.divisosofttech.spot_fix.repository.rowmapper.TicketRowMapper;
import com.divisosofttech.spot_fix.repository.rowmapper.UserRowMapper;
import com.divisosofttech.spot_fix.repository.rowmapper.WardRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Comparison;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Conditions;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoinCondition;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.service.ConditionBuilder;

/**
 * Spring Data R2DBC custom repository implementation for the Ticket entity.
 */
@SuppressWarnings("unused")
class TicketRepositoryInternalImpl extends SimpleR2dbcRepository<Ticket, Long> implements TicketRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final UserRowMapper userMapper;
    private final LocationRowMapper locationMapper;
    private final WardRowMapper wardMapper;
    private final DepartmentRowMapper departmentMapper;
    private final TicketRowMapper ticketMapper;
    private final ColumnConverter columnConverter;

    private static final Table entityTable = Table.aliased("ticket", EntityManager.ENTITY_ALIAS);
    private static final Table reportedByTable = Table.aliased("jhi_user", "reportedBy");
    private static final Table locationTable = Table.aliased("location", "location");
    private static final Table wardTable = Table.aliased("ward", "ward");
    private static final Table assignedDepartmentTable = Table.aliased("department", "assignedDepartment");

    public TicketRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        UserRowMapper userMapper,
        LocationRowMapper locationMapper,
        WardRowMapper wardMapper,
        DepartmentRowMapper departmentMapper,
        TicketRowMapper ticketMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter,
        ColumnConverter columnConverter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Ticket.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.userMapper = userMapper;
        this.locationMapper = locationMapper;
        this.wardMapper = wardMapper;
        this.departmentMapper = departmentMapper;
        this.ticketMapper = ticketMapper;
        this.columnConverter = columnConverter;
    }

    @Override
    public Flux<Ticket> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Ticket> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = TicketSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(UserSqlHelper.getColumns(reportedByTable, "reportedBy"));
        columns.addAll(LocationSqlHelper.getColumns(locationTable, "location"));
        columns.addAll(WardSqlHelper.getColumns(wardTable, "ward"));
        columns.addAll(DepartmentSqlHelper.getColumns(assignedDepartmentTable, "assignedDepartment"));
        SelectFromAndJoinCondition selectFrom = Select.builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(reportedByTable)
            .on(Column.create("reported_by_id", entityTable))
            .equals(Column.create("id", reportedByTable))
            .leftOuterJoin(locationTable)
            .on(Column.create("location_id", entityTable))
            .equals(Column.create("id", locationTable))
            .leftOuterJoin(wardTable)
            .on(Column.create("ward_id", entityTable))
            .equals(Column.create("id", wardTable))
            .leftOuterJoin(assignedDepartmentTable)
            .on(Column.create("assigned_department_id", entityTable))
            .equals(Column.create("id", assignedDepartmentTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Ticket.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Ticket> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Ticket> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    @Override
    public Mono<Ticket> findOneWithEagerRelationships(Long id) {
        return findById(id);
    }

    @Override
    public Flux<Ticket> findAllWithEagerRelationships() {
        return findAll();
    }

    @Override
    public Flux<Ticket> findAllWithEagerRelationships(Pageable page) {
        return findAllBy(page);
    }

    private Ticket process(Row row, RowMetadata metadata) {
        Ticket entity = ticketMapper.apply(row, "e");
        entity.setReportedBy(userMapper.apply(row, "reportedBy"));
        entity.setLocation(locationMapper.apply(row, "location"));
        entity.setWard(wardMapper.apply(row, "ward"));
        entity.setAssignedDepartment(departmentMapper.apply(row, "assignedDepartment"));
        return entity;
    }

    @Override
    public <S extends Ticket> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Flux<Ticket> findByCriteria(TicketCriteria ticketCriteria, Pageable page) {
        return createQuery(page, buildConditions(ticketCriteria)).all();
    }

    @Override
    public Mono<Long> countByCriteria(TicketCriteria criteria) {
        return findByCriteria(criteria, null)
            .collectList()
            .map(collectedList -> collectedList != null ? (long) collectedList.size() : (long) 0);
    }

    private Condition buildConditions(TicketCriteria criteria) {
        ConditionBuilder builder = new ConditionBuilder(this.columnConverter);
        List<Condition> allConditions = new ArrayList<Condition>();
        if (criteria != null) {
            if (criteria.getId() != null) {
                builder.buildFilterConditionForField(criteria.getId(), entityTable.column("id"));
            }
            if (criteria.getTitle() != null) {
                builder.buildFilterConditionForField(criteria.getTitle(), entityTable.column("title"));
            }
            if (criteria.getStatus() != null) {
                builder.buildFilterConditionForField(criteria.getStatus(), entityTable.column("status"));
            }
            if (criteria.getPriority() != null) {
                builder.buildFilterConditionForField(criteria.getPriority(), entityTable.column("priority"));
            }
            if (criteria.getVisibility() != null) {
                builder.buildFilterConditionForField(criteria.getVisibility(), entityTable.column("visibility"));
            }
            if (criteria.getCategory() != null) {
                builder.buildFilterConditionForField(criteria.getCategory(), entityTable.column("category"));
            }
            if (criteria.getCreatedDate() != null) {
                builder.buildFilterConditionForField(criteria.getCreatedDate(), entityTable.column("created_date"));
            }
            if (criteria.getUpdatedDate() != null) {
                builder.buildFilterConditionForField(criteria.getUpdatedDate(), entityTable.column("updated_date"));
            }
            if (criteria.getExpectedResolutionDate() != null) {
                builder.buildFilterConditionForField(criteria.getExpectedResolutionDate(), entityTable.column("expected_resolution_date"));
            }
            if (criteria.getResolvedDate() != null) {
                builder.buildFilterConditionForField(criteria.getResolvedDate(), entityTable.column("resolved_date"));
            }
            if (criteria.getAiDuplicate() != null) {
                builder.buildFilterConditionForField(criteria.getAiDuplicate(), entityTable.column("ai_duplicate"));
            }
            if (criteria.getDuplicateScore() != null) {
                builder.buildFilterConditionForField(criteria.getDuplicateScore(), entityTable.column("duplicate_score"));
            }
            if (criteria.getAiConfidence() != null) {
                builder.buildFilterConditionForField(criteria.getAiConfidence(), entityTable.column("ai_confidence"));
            }
            if (criteria.getDuplicateTicketId() != null) {
                builder.buildFilterConditionForField(criteria.getDuplicateTicketId(), entityTable.column("duplicate_ticket_id"));
            }
            if (criteria.getDeleted() != null) {
                builder.buildFilterConditionForField(criteria.getDeleted(), entityTable.column("deleted"));
            }
            if (criteria.getDeletedDate() != null) {
                builder.buildFilterConditionForField(criteria.getDeletedDate(), entityTable.column("deleted_date"));
            }
            if (criteria.getReportedById() != null) {
                builder.buildFilterConditionForField(criteria.getReportedById(), reportedByTable.column("id"));
            }
            if (criteria.getLocationId() != null) {
                builder.buildFilterConditionForField(criteria.getLocationId(), locationTable.column("id"));
            }
            if (criteria.getWardId() != null) {
                builder.buildFilterConditionForField(criteria.getWardId(), wardTable.column("id"));
            }
            if (criteria.getAssignedDepartmentId() != null) {
                builder.buildFilterConditionForField(criteria.getAssignedDepartmentId(), assignedDepartmentTable.column("id"));
            }
        }
        return builder.buildConditions();
    }
}
