package com.divisosofttech.spot_fix.service.mapper;

import static com.divisosofttech.spot_fix.domain.AttachmentAsserts.*;
import static com.divisosofttech.spot_fix.domain.AttachmentTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttachmentMapperTest {

    private AttachmentMapper attachmentMapper;

    @BeforeEach
    void setUp() {
        attachmentMapper = new AttachmentMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAttachmentSample1();
        var actual = attachmentMapper.toEntity(attachmentMapper.toDto(expected));
        assertAttachmentAllPropertiesEquals(expected, actual);
    }
}
