package com.diviso.spot_fix.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AttachmentCriteriaTest {

    @Test
    void newAttachmentCriteriaHasAllFiltersNullTest() {
        var attachmentCriteria = new AttachmentCriteria();
        assertThat(attachmentCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void attachmentCriteriaFluentMethodsCreatesFiltersTest() {
        var attachmentCriteria = new AttachmentCriteria();

        setAllFilters(attachmentCriteria);

        assertThat(attachmentCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void attachmentCriteriaCopyCreatesNullFilterTest() {
        var attachmentCriteria = new AttachmentCriteria();
        var copy = attachmentCriteria.copy();

        assertThat(attachmentCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(attachmentCriteria)
        );
    }

    @Test
    void attachmentCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var attachmentCriteria = new AttachmentCriteria();
        setAllFilters(attachmentCriteria);

        var copy = attachmentCriteria.copy();

        assertThat(attachmentCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(attachmentCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var attachmentCriteria = new AttachmentCriteria();

        assertThat(attachmentCriteria).hasToString("AttachmentCriteria{}");
    }

    private static void setAllFilters(AttachmentCriteria attachmentCriteria) {
        attachmentCriteria.id();
        attachmentCriteria.attachmentType();
        attachmentCriteria.fileName();
        attachmentCriteria.filePath();
        attachmentCriteria.fileType();
        attachmentCriteria.fileSize();
        attachmentCriteria.checksum();
        attachmentCriteria.uploadedDate();
        attachmentCriteria.durationSeconds();
        attachmentCriteria.language();
        attachmentCriteria.deleted();
        attachmentCriteria.updatedDate();
        attachmentCriteria.deletedDate();
        attachmentCriteria.ticketId();
        attachmentCriteria.uploadedById();
        attachmentCriteria.distinct();
    }

    private static Condition<AttachmentCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getAttachmentType()) &&
                condition.apply(criteria.getFileName()) &&
                condition.apply(criteria.getFilePath()) &&
                condition.apply(criteria.getFileType()) &&
                condition.apply(criteria.getFileSize()) &&
                condition.apply(criteria.getChecksum()) &&
                condition.apply(criteria.getUploadedDate()) &&
                condition.apply(criteria.getDurationSeconds()) &&
                condition.apply(criteria.getLanguage()) &&
                condition.apply(criteria.getDeleted()) &&
                condition.apply(criteria.getUpdatedDate()) &&
                condition.apply(criteria.getDeletedDate()) &&
                condition.apply(criteria.getTicketId()) &&
                condition.apply(criteria.getUploadedById()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AttachmentCriteria> copyFiltersAre(AttachmentCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getAttachmentType(), copy.getAttachmentType()) &&
                condition.apply(criteria.getFileName(), copy.getFileName()) &&
                condition.apply(criteria.getFilePath(), copy.getFilePath()) &&
                condition.apply(criteria.getFileType(), copy.getFileType()) &&
                condition.apply(criteria.getFileSize(), copy.getFileSize()) &&
                condition.apply(criteria.getChecksum(), copy.getChecksum()) &&
                condition.apply(criteria.getUploadedDate(), copy.getUploadedDate()) &&
                condition.apply(criteria.getDurationSeconds(), copy.getDurationSeconds()) &&
                condition.apply(criteria.getLanguage(), copy.getLanguage()) &&
                condition.apply(criteria.getDeleted(), copy.getDeleted()) &&
                condition.apply(criteria.getUpdatedDate(), copy.getUpdatedDate()) &&
                condition.apply(criteria.getDeletedDate(), copy.getDeletedDate()) &&
                condition.apply(criteria.getTicketId(), copy.getTicketId()) &&
                condition.apply(criteria.getUploadedById(), copy.getUploadedById()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
