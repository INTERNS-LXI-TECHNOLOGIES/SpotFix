package com.divisosofttech.spot_fix.repository.rowmapper;

import com.divisosofttech.spot_fix.domain.TicketVote;
import com.divisosofttech.spot_fix.domain.enumeration.VoteType;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link TicketVote}, with proper type conversions.
 */
@Service
public class TicketVoteRowMapper implements BiFunction<Row, String, TicketVote> {

    private final ColumnConverter converter;

    public TicketVoteRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link TicketVote} stored in the database.
     */
    @Override
    public TicketVote apply(Row row, String prefix) {
        TicketVote entity = new TicketVote();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setVoteType(converter.fromRow(row, prefix + "_vote_type", VoteType.class));
        entity.setCreatedDate(converter.fromRow(row, prefix + "_created_date", Instant.class));
        entity.setTicketId(converter.fromRow(row, prefix + "_ticket_id", Long.class));
        entity.setUserId(converter.fromRow(row, prefix + "_user_id", Long.class));
        return entity;
    }
}
