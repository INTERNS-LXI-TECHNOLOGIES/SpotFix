package com.divisosofttech.spot_fix.repository;

import com.divisosofttech.spot_fix.domain.Location;
import com.divisosofttech.spot_fix.domain.criteria.LocationCriteria;
import com.divisosofttech.spot_fix.repository.rowmapper.ColumnConverter;
import com.divisosofttech.spot_fix.repository.rowmapper.LocationRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the Location entity.
 */
@SuppressWarnings("unused")
class LocationRepositoryInternalImpl extends SimpleR2dbcRepository<Location, Long> implements LocationRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final WardRowMapper wardMapper;
    private final LocationRowMapper locationMapper;
    private final ColumnConverter columnConverter;

    private static final Table entityTable = Table.aliased("location", EntityManager.ENTITY_ALIAS);
    private static final Table wardTable = Table.aliased("ward", "ward");

    public LocationRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        WardRowMapper wardMapper,
        LocationRowMapper locationMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter,
        ColumnConverter columnConverter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Location.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.wardMapper = wardMapper;
        this.locationMapper = locationMapper;
        this.columnConverter = columnConverter;
    }

    @Override
    public Flux<Location> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Location> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = LocationSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(WardSqlHelper.getColumns(wardTable, "ward"));
        SelectFromAndJoinCondition selectFrom = Select.builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(wardTable)
            .on(Column.create("ward_id", entityTable))
            .equals(Column.create("id", wardTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Location.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Location> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Location> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Location process(Row row, RowMetadata metadata) {
        Location entity = locationMapper.apply(row, "e");
        entity.setWard(wardMapper.apply(row, "ward"));
        return entity;
    }

    @Override
    public <S extends Location> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Flux<Location> findByCriteria(LocationCriteria locationCriteria, Pageable page) {
        return createQuery(page, buildConditions(locationCriteria)).all();
    }

    @Override
    public Mono<Long> countByCriteria(LocationCriteria criteria) {
        return findByCriteria(criteria, null)
            .collectList()
            .map(collectedList -> collectedList != null ? (long) collectedList.size() : (long) 0);
    }

    private Condition buildConditions(LocationCriteria criteria) {
        ConditionBuilder builder = new ConditionBuilder(this.columnConverter);
        List<Condition> allConditions = new ArrayList<Condition>();
        if (criteria != null) {
            if (criteria.getId() != null) {
                builder.buildFilterConditionForField(criteria.getId(), entityTable.column("id"));
            }
            if (criteria.getAddressText() != null) {
                builder.buildFilterConditionForField(criteria.getAddressText(), entityTable.column("address_text"));
            }
            if (criteria.getLandmark() != null) {
                builder.buildFilterConditionForField(criteria.getLandmark(), entityTable.column("landmark"));
            }
            if (criteria.getLatitude() != null) {
                builder.buildFilterConditionForField(criteria.getLatitude(), entityTable.column("latitude"));
            }
            if (criteria.getLongitude() != null) {
                builder.buildFilterConditionForField(criteria.getLongitude(), entityTable.column("longitude"));
            }
            if (criteria.getWardId() != null) {
                builder.buildFilterConditionForField(criteria.getWardId(), wardTable.column("id"));
            }
        }
        return builder.buildConditions();
    }
}
