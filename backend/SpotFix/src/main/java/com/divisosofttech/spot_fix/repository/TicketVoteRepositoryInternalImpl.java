package com.divisosofttech.spot_fix.repository;

import com.divisosofttech.spot_fix.domain.TicketVote;
import com.divisosofttech.spot_fix.domain.criteria.TicketVoteCriteria;
import com.divisosofttech.spot_fix.repository.rowmapper.ColumnConverter;
import com.divisosofttech.spot_fix.repository.rowmapper.TicketRowMapper;
import com.divisosofttech.spot_fix.repository.rowmapper.TicketVoteRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the TicketVote entity.
 */
@SuppressWarnings("unused")
class TicketVoteRepositoryInternalImpl extends SimpleR2dbcRepository<TicketVote, Long> implements TicketVoteRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final TicketRowMapper ticketMapper;
    private final UserRowMapper userMapper;
    private final TicketVoteRowMapper ticketvoteMapper;
    private final ColumnConverter columnConverter;

    private static final Table entityTable = Table.aliased("ticket_vote", EntityManager.ENTITY_ALIAS);
    private static final Table ticketTable = Table.aliased("ticket", "ticket");
    private static final Table userTable = Table.aliased("jhi_user", "e_user");

    public TicketVoteRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        TicketRowMapper ticketMapper,
        UserRowMapper userMapper,
        TicketVoteRowMapper ticketvoteMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter,
        ColumnConverter columnConverter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(TicketVote.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.ticketMapper = ticketMapper;
        this.userMapper = userMapper;
        this.ticketvoteMapper = ticketvoteMapper;
        this.columnConverter = columnConverter;
    }

    @Override
    public Flux<TicketVote> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<TicketVote> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = TicketVoteSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(TicketSqlHelper.getColumns(ticketTable, "ticket"));
        columns.addAll(UserSqlHelper.getColumns(userTable, "user"));
        SelectFromAndJoinCondition selectFrom = Select.builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(ticketTable)
            .on(Column.create("ticket_id", entityTable))
            .equals(Column.create("id", ticketTable))
            .leftOuterJoin(userTable)
            .on(Column.create("user_id", entityTable))
            .equals(Column.create("id", userTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, TicketVote.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<TicketVote> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<TicketVote> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    @Override
    public Mono<TicketVote> findOneWithEagerRelationships(Long id) {
        return findById(id);
    }

    @Override
    public Flux<TicketVote> findAllWithEagerRelationships() {
        return findAll();
    }

    @Override
    public Flux<TicketVote> findAllWithEagerRelationships(Pageable page) {
        return findAllBy(page);
    }

    private TicketVote process(Row row, RowMetadata metadata) {
        TicketVote entity = ticketvoteMapper.apply(row, "e");
        entity.setTicket(ticketMapper.apply(row, "ticket"));
        entity.setUser(userMapper.apply(row, "user"));
        return entity;
    }

    @Override
    public <S extends TicketVote> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Flux<TicketVote> findByCriteria(TicketVoteCriteria ticketVoteCriteria, Pageable page) {
        return createQuery(page, buildConditions(ticketVoteCriteria)).all();
    }

    @Override
    public Mono<Long> countByCriteria(TicketVoteCriteria criteria) {
        return findByCriteria(criteria, null)
            .collectList()
            .map(collectedList -> collectedList != null ? (long) collectedList.size() : (long) 0);
    }

    private Condition buildConditions(TicketVoteCriteria criteria) {
        ConditionBuilder builder = new ConditionBuilder(this.columnConverter);
        List<Condition> allConditions = new ArrayList<Condition>();
        if (criteria != null) {
            if (criteria.getId() != null) {
                builder.buildFilterConditionForField(criteria.getId(), entityTable.column("id"));
            }
            if (criteria.getVoteType() != null) {
                builder.buildFilterConditionForField(criteria.getVoteType(), entityTable.column("vote_type"));
            }
            if (criteria.getCreatedDate() != null) {
                builder.buildFilterConditionForField(criteria.getCreatedDate(), entityTable.column("created_date"));
            }
            if (criteria.getTicketId() != null) {
                builder.buildFilterConditionForField(criteria.getTicketId(), ticketTable.column("id"));
            }
            if (criteria.getUserId() != null) {
                builder.buildFilterConditionForField(criteria.getUserId(), userTable.column("id"));
            }
        }
        return builder.buildConditions();
    }
}
