package com.divisosofttech.spot_fix.repository;

import com.divisosofttech.spot_fix.domain.TicketStatusHistory;
import com.divisosofttech.spot_fix.domain.criteria.TicketStatusHistoryCriteria;
import com.divisosofttech.spot_fix.repository.rowmapper.ColumnConverter;
import com.divisosofttech.spot_fix.repository.rowmapper.TicketRowMapper;
import com.divisosofttech.spot_fix.repository.rowmapper.TicketStatusHistoryRowMapper;
import com.divisosofttech.spot_fix.repository.rowmapper.UserRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the TicketStatusHistory entity.
 */
@SuppressWarnings("unused")
class TicketStatusHistoryRepositoryInternalImpl
    extends SimpleR2dbcRepository<TicketStatusHistory, Long>
    implements TicketStatusHistoryRepositoryInternal
{

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final TicketRowMapper ticketMapper;
    private final UserRowMapper userMapper;
    private final TicketStatusHistoryRowMapper ticketstatushistoryMapper;
    private final ColumnConverter columnConverter;

    private static final Table entityTable = Table.aliased("ticket_status_history", EntityManager.ENTITY_ALIAS);
    private static final Table ticketTable = Table.aliased("ticket", "ticket");
    private static final Table changedByTable = Table.aliased("jhi_user", "changedBy");

    public TicketStatusHistoryRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        TicketRowMapper ticketMapper,
        UserRowMapper userMapper,
        TicketStatusHistoryRowMapper ticketstatushistoryMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter,
        ColumnConverter columnConverter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(TicketStatusHistory.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.ticketMapper = ticketMapper;
        this.userMapper = userMapper;
        this.ticketstatushistoryMapper = ticketstatushistoryMapper;
        this.columnConverter = columnConverter;
    }

    @Override
    public Flux<TicketStatusHistory> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<TicketStatusHistory> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = TicketStatusHistorySqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(TicketSqlHelper.getColumns(ticketTable, "ticket"));
        columns.addAll(UserSqlHelper.getColumns(changedByTable, "changedBy"));
        SelectFromAndJoinCondition selectFrom = Select.builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(ticketTable)
            .on(Column.create("ticket_id", entityTable))
            .equals(Column.create("id", ticketTable))
            .leftOuterJoin(changedByTable)
            .on(Column.create("changed_by_id", entityTable))
            .equals(Column.create("id", changedByTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, TicketStatusHistory.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<TicketStatusHistory> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<TicketStatusHistory> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    @Override
    public Mono<TicketStatusHistory> findOneWithEagerRelationships(Long id) {
        return findById(id);
    }

    @Override
    public Flux<TicketStatusHistory> findAllWithEagerRelationships() {
        return findAll();
    }

    @Override
    public Flux<TicketStatusHistory> findAllWithEagerRelationships(Pageable page) {
        return findAllBy(page);
    }

    private TicketStatusHistory process(Row row, RowMetadata metadata) {
        TicketStatusHistory entity = ticketstatushistoryMapper.apply(row, "e");
        entity.setTicket(ticketMapper.apply(row, "ticket"));
        entity.setChangedBy(userMapper.apply(row, "changedBy"));
        return entity;
    }

    @Override
    public <S extends TicketStatusHistory> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Flux<TicketStatusHistory> findByCriteria(TicketStatusHistoryCriteria ticketStatusHistoryCriteria, Pageable page) {
        return createQuery(page, buildConditions(ticketStatusHistoryCriteria)).all();
    }

    @Override
    public Mono<Long> countByCriteria(TicketStatusHistoryCriteria criteria) {
        return findByCriteria(criteria, null)
            .collectList()
            .map(collectedList -> collectedList != null ? (long) collectedList.size() : (long) 0);
    }

    private Condition buildConditions(TicketStatusHistoryCriteria criteria) {
        ConditionBuilder builder = new ConditionBuilder(this.columnConverter);
        List<Condition> allConditions = new ArrayList<Condition>();
        if (criteria != null) {
            if (criteria.getId() != null) {
                builder.buildFilterConditionForField(criteria.getId(), entityTable.column("id"));
            }
            if (criteria.getOldStatus() != null) {
                builder.buildFilterConditionForField(criteria.getOldStatus(), entityTable.column("old_status"));
            }
            if (criteria.getNewStatus() != null) {
                builder.buildFilterConditionForField(criteria.getNewStatus(), entityTable.column("new_status"));
            }
            if (criteria.getChangedDate() != null) {
                builder.buildFilterConditionForField(criteria.getChangedDate(), entityTable.column("changed_date"));
            }
            if (criteria.getTicketId() != null) {
                builder.buildFilterConditionForField(criteria.getTicketId(), ticketTable.column("id"));
            }
            if (criteria.getChangedById() != null) {
                builder.buildFilterConditionForField(criteria.getChangedById(), changedByTable.column("id"));
            }
        }
        return builder.buildConditions();
    }
}
