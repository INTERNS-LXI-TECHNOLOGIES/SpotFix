package com.divisosofttech.spot_fix.domain;

import static com.divisosofttech.spot_fix.domain.LocationTestSamples.*;
import static com.divisosofttech.spot_fix.domain.WardTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.divisosofttech.spot_fix.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LocationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Location.class);
        Location location1 = getLocationSample1();
        Location location2 = new Location();
        assertThat(location1).isNotEqualTo(location2);

        location2.setId(location1.getId());
        assertThat(location1).isEqualTo(location2);

        location2 = getLocationSample2();
        assertThat(location1).isNotEqualTo(location2);
    }

    @Test
    void wardTest() {
        Location location = getLocationRandomSampleGenerator();
        Ward wardBack = getWardRandomSampleGenerator();

        location.setWard(wardBack);
        assertThat(location.getWard()).isEqualTo(wardBack);

        location.ward(null);
        assertThat(location.getWard()).isNull();
    }
}
