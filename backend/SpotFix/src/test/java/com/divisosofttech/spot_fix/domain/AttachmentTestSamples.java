package com.divisosofttech.spot_fix.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AttachmentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Attachment getAttachmentSample1() {
        return new Attachment()
            .id(1L)
            .fileName("fileName1")
            .filePath("filePath1")
            .fileType("fileType1")
            .fileSize(1L)
            .checksum("checksum1")
            .durationSeconds(1)
            .language("language1");
    }

    public static Attachment getAttachmentSample2() {
        return new Attachment()
            .id(2L)
            .fileName("fileName2")
            .filePath("filePath2")
            .fileType("fileType2")
            .fileSize(2L)
            .checksum("checksum2")
            .durationSeconds(2)
            .language("language2");
    }

    public static Attachment getAttachmentRandomSampleGenerator() {
        return new Attachment()
            .id(longCount.incrementAndGet())
            .fileName(UUID.randomUUID().toString())
            .filePath(UUID.randomUUID().toString())
            .fileType(UUID.randomUUID().toString())
            .fileSize(longCount.incrementAndGet())
            .checksum(UUID.randomUUID().toString())
            .durationSeconds(intCount.incrementAndGet())
            .language(UUID.randomUUID().toString());
    }
}
