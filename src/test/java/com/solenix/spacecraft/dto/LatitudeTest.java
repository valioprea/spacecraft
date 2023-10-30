package com.solenix.spacecraft.dto;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class LatitudeTest {

    @Test
    void testLatitudeRecord() {
        var latitudeTimestamp = Instant.now();
        var latitudePosition = 1.234;

        var latitude = new Latitude(latitudeTimestamp, latitudePosition);

        assertEquals(latitudeTimestamp, latitude.latitudeTimestamp());
        assertEquals(latitudePosition, latitude.latitudePosition());
    }

    @Test
    void testLatitudeRecordWithNullValues() {
        var latitudeTimestamp = Instant.now();

        assertThrows(NullPointerException.class, () -> new Latitude(latitudeTimestamp, null));
    }
}