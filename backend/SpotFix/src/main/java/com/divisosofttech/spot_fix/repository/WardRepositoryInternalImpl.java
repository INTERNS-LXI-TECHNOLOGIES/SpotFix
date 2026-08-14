package com.divisosofttech.spot_fix.repository;

import com.divisosofttech.spot_fix.domain.Ward;
import com.divisosofttech.spot_fix.domain.criteria.WardCriteria;
import com.divisosofttech.spot_fix.repository.rowmapper.ColumnConverter;
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
 * Spring Data R2DBC custom repository implementation for the Ward entity.
 */
@SuppressWarnings("unused")
class WardRepositoryInternalImpl extends SimpleR2dbcRepository<Ward, Long> implements WardRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final WardRowMapper wardMapper;
    private final ColumnConverter columnConverter;

    private static final Table entityTable = Table.aliased("ward", EntityManager.ENTITY_ALIAS);

    public WardRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        WardRowMapper wardMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter,
        ColumnConverter columnConverter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Ward.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.wardMapper = wardMapper;
        this.columnConverter = columnConverter;
    }

    @Override
    public Flux<Ward> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Ward> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = WardSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        SelectFromAndJoin selectFrom = Select.builder().select(columns).from(entityTable);
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Ward.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Ward> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Ward> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Ward process(Row row, RowMetadata metadata) {
        Ward entity = wardMapper.apply(row, "e");
        return entity;
    }

    @Override
    public <S extends Ward> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Flux<Ward> findByCriteria(WardCriteria wardCriteria, Pageable page) {
        return createQuery(page, buildConditions(wardCriteria)).all();
    }

    @Override
    public Mono<Long> countByCriteria(WardCriteria criteria) {
        return findByCriteria(criteria, null)
            .collectList()
            .map(collectedList -> collectedList != null ? (long) collectedList.size() : (long) 0);
    }

    private Condition buildConditions(WardCriteria criteria) {
        ConditionBuilder builder = new ConditionBuilder(this.columnConverter);
        List<Condition> allConditions = new ArrayList<Condition>();
        if (criteria != null) {
            if (criteria.getId() != null) {
                builder.buildFilterConditionForField(criteria.getId(), entityTable.column("id"));
            }
            if (criteria.getCode() != null) {
                builder.buildFilterConditionForField(criteria.getCode(), entityTable.column("code"));
            }
            if (criteria.getName() != null) {
                builder.buildFilterConditionForField(criteria.getName(), entityTable.column("name"));
            }
            if (criteria.getMunicipality() != null) {
                builder.buildFilterConditionForField(criteria.getMunicipality(), entityTable.column("municipality"));
            }
        }
        return builder.buildConditions();
    }
}
