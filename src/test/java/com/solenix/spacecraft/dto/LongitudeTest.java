package com.solenix.spacecraft.dto;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class LongitudeTest {

    @Test
    void testLongitudeRecord() {
        var longitudeTimestamp = Instant.now();
        var longitudePosition = 1.234;

        var longitude = new Longitude(longitudeTimestamp, longitudePosition);

        assertEquals(longitudeTimestamp, longitude.longitudeTimestamp());
        assertEquals(longitudePosition, longitude.longitudePosition());
    }

    @Test
    void testLongitudeRecordWithNullValues() {
        var longitudeTimestamp = Instant.now();

        assertThrows(NullPointerException.class, () -> new Longitude(longitudeTimestamp, null));
    }
}