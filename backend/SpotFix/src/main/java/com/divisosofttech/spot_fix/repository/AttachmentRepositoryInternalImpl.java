package com.divisosofttech.spot_fix.repository;

import com.divisosofttech.spot_fix.domain.Attachment;
import com.divisosofttech.spot_fix.domain.criteria.AttachmentCriteria;
import com.divisosofttech.spot_fix.repository.rowmapper.AttachmentRowMapper;
import com.divisosofttech.spot_fix.repository.rowmapper.ColumnConverter;
import com.divisosofttech.spot_fix.repository.rowmapper.TicketRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the Attachment entity.
 */
@SuppressWarnings("unused")
class AttachmentRepositoryInternalImpl extends SimpleR2dbcRepository<Attachment, Long> implements AttachmentRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final TicketRowMapper ticketMapper;
    private final UserRowMapper userMapper;
    private final AttachmentRowMapper attachmentMapper;
    private final ColumnConverter columnConverter;

    private static final Table entityTable = Table.aliased("attachment", EntityManager.ENTITY_ALIAS);
    private static final Table ticketTable = Table.aliased("ticket", "ticket");
    private static final Table uploadedByTable = Table.aliased("jhi_user", "uploadedBy");

    public AttachmentRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        TicketRowMapper ticketMapper,
        UserRowMapper userMapper,
        AttachmentRowMapper attachmentMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter,
        ColumnConverter columnConverter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Attachment.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.ticketMapper = ticketMapper;
        this.userMapper = userMapper;
        this.attachmentMapper = attachmentMapper;
        this.columnConverter = columnConverter;
    }

    @Override
    public Flux<Attachment> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Attachment> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = AttachmentSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(TicketSqlHelper.getColumns(ticketTable, "ticket"));
        columns.addAll(UserSqlHelper.getColumns(uploadedByTable, "uploadedBy"));
        SelectFromAndJoinCondition selectFrom = Select.builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(ticketTable)
            .on(Column.create("ticket_id", entityTable))
            .equals(Column.create("id", ticketTable))
            .leftOuterJoin(uploadedByTable)
            .on(Column.create("uploaded_by_id", entityTable))
            .equals(Column.create("id", uploadedByTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Attachment.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Attachment> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Attachment> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    @Override
    public Mono<Attachment> findOneWithEagerRelationships(Long id) {
        return findById(id);
    }

    @Override
    public Flux<Attachment> findAllWithEagerRelationships() {
        return findAll();
    }

    @Override
    public Flux<Attachment> findAllWithEagerRelationships(Pageable page) {
        return findAllBy(page);
    }

    private Attachment process(Row row, RowMetadata metadata) {
        Attachment entity = attachmentMapper.apply(row, "e");
        entity.setTicket(ticketMapper.apply(row, "ticket"));
        entity.setUploadedBy(userMapper.apply(row, "uploadedBy"));
        return entity;
    }

    @Override
    public <S extends Attachment> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Flux<Attachment> findByCriteria(AttachmentCriteria attachmentCriteria, Pageable page) {
        return createQuery(page, buildConditions(attachmentCriteria)).all();
    }

    @Override
    public Mono<Long> countByCriteria(AttachmentCriteria criteria) {
        return findByCriteria(criteria, null)
            .collectList()
            .map(collectedList -> collectedList != null ? (long) collectedList.size() : (long) 0);
    }

    private Condition buildConditions(AttachmentCriteria criteria) {
        ConditionBuilder builder = new ConditionBuilder(this.columnConverter);
        List<Condition> allConditions = new ArrayList<Condition>();
        if (criteria != null) {
            if (criteria.getId() != null) {
                builder.buildFilterConditionForField(criteria.getId(), entityTable.column("id"));
            }
            if (criteria.getAttachmentType() != null) {
                builder.buildFilterConditionForField(criteria.getAttachmentType(), entityTable.column("attachment_type"));
            }
            if (criteria.getFileName() != null) {
                builder.buildFilterConditionForField(criteria.getFileName(), entityTable.column("file_name"));
            }
            if (criteria.getFilePath() != null) {
                builder.buildFilterConditionForField(criteria.getFilePath(), entityTable.column("file_path"));
            }
            if (criteria.getFileType() != null) {
                builder.buildFilterConditionForField(criteria.getFileType(), entityTable.column("file_type"));
            }
            if (criteria.getFileSize() != null) {
                builder.buildFilterConditionForField(criteria.getFileSize(), entityTable.column("file_size"));
            }
            if (criteria.getChecksum() != null) {
                builder.buildFilterConditionForField(criteria.getChecksum(), entityTable.column("checksum"));
            }
            if (criteria.getUploadedDate() != null) {
                builder.buildFilterConditionForField(criteria.getUploadedDate(), entityTable.column("uploaded_date"));
            }
            if (criteria.getDurationSeconds() != null) {
                builder.buildFilterConditionForField(criteria.getDurationSeconds(), entityTable.column("duration_seconds"));
            }
            if (criteria.getLanguage() != null) {
                builder.buildFilterConditionForField(criteria.getLanguage(), entityTable.column("language"));
            }
            if (criteria.getDeleted() != null) {
                builder.buildFilterConditionForField(criteria.getDeleted(), entityTable.column("deleted"));
            }
            if (criteria.getUpdatedDate() != null) {
                builder.buildFilterConditionForField(criteria.getUpdatedDate(), entityTable.column("updated_date"));
            }
            if (criteria.getDeletedDate() != null) {
                builder.buildFilterConditionForField(criteria.getDeletedDate(), entityTable.column("deleted_date"));
            }
            if (criteria.getTicketId() != null) {
                builder.buildFilterConditionForField(criteria.getTicketId(), ticketTable.column("id"));
            }
            if (criteria.getUploadedById() != null) {
                builder.buildFilterConditionForField(criteria.getUploadedById(), uploadedByTable.column("id"));
            }
        }
        return builder.buildConditions();
    }
}
