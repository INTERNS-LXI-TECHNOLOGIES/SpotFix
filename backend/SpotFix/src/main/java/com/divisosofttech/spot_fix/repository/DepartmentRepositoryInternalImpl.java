package com.divisosofttech.spot_fix.repository;

import com.divisosofttech.spot_fix.domain.Department;
import com.divisosofttech.spot_fix.domain.criteria.DepartmentCriteria;
import com.divisosofttech.spot_fix.repository.rowmapper.ColumnConverter;
import com.divisosofttech.spot_fix.repository.rowmapper.DepartmentRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.core.sql.Comparison;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Conditions;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoin;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.service.ConditionBuilder;

/**
 * Spring Data R2DBC custom repository implementation for the Department entity.
 */
@SuppressWarnings("unused")
class DepartmentRepositoryInternalImpl extends SimpleR2dbcRepository<Department, Long> implements DepartmentRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final DepartmentRowMapper departmentMapper;
    private final ColumnConverter columnConverter;

    private static final Table entityTable = Table.aliased("department", EntityManager.ENTITY_ALIAS);

    public DepartmentRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        DepartmentRowMapper departmentMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter,
        ColumnConverter columnConverter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Department.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.departmentMapper = departmentMapper;
        this.columnConverter = columnConverter;
    }

    @Override
    public Flux<Department> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Department> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = DepartmentSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        SelectFromAndJoin selectFrom = Select.builder().select(columns).from(entityTable);
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Department.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Department> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Department> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Department process(Row row, RowMetadata metadata) {
        Department entity = departmentMapper.apply(row, "e");
        return entity;
    }

    @Override
    public <S extends Department> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Flux<Department> findByCriteria(DepartmentCriteria departmentCriteria, Pageable page) {
        return createQuery(page, buildConditions(departmentCriteria)).all();
    }

    @Override
    public Mono<Long> countByCriteria(DepartmentCriteria criteria) {
        return findByCriteria(criteria, null)
            .collectList()
            .map(collectedList -> collectedList != null ? (long) collectedList.size() : (long) 0);
    }

    private Condition buildConditions(DepartmentCriteria criteria) {
        ConditionBuilder builder = new ConditionBuilder(this.columnConverter);
        List<Condition> allConditions = new ArrayList<Condition>();
        if (criteria != null) {
            if (criteria.getId() != null) {
                builder.buildFilterConditionForField(criteria.getId(), entityTable.column("id"));
            }
            if (criteria.getName() != null) {
                builder.buildFilterConditionForField(criteria.getName(), entityTable.column("name"));
            }
            if (criteria.getContactEmail() != null) {
                builder.buildFilterConditionForField(criteria.getContactEmail(), entityTable.column("contact_email"));
            }
            if (criteria.getContactPhone() != null) {
                builder.buildFilterConditionForField(criteria.getContactPhone(), entityTable.column("contact_phone"));
            }
            if (criteria.getActive() != null) {
                builder.buildFilterConditionForField(criteria.getActive(), entityTable.column("active"));
            }
        }
        return builder.buildConditions();
    }
}
