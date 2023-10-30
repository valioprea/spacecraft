package com.solenix.spacecraft.dto;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class MappedEventTest {
    @Test
    void testMappedEventConstructor() {
        var eventTimestamp = Instant.now();
        var eventData = new Event(eventTimestamp, "TestEvent", "12345", "High");
        var latitudeData = new Latitude(eventTimestamp, 1.234);
        var longitudeData = new Longitude(eventTimestamp, 2.345);

        var mappedEvent = new MappedEvent.Builder()
                .eventData(eventData)
                .latitudeData(latitudeData)
                .longitudeData(longitudeData)
                .build();

        assertEquals(eventData, mappedEvent.getEventData());
        assertEquals(latitudeData, mappedEvent.getLatitudeData());
        assertEquals(longitudeData, mappedEvent.getLongitudeData());
    }

    @Test
    void testMappedEventWithNullValues() {
        assertThrows(NullPointerException.class, () -> new MappedEvent.Builder()
                .eventData(null)
                .latitudeData(null)
                .longitudeData(null)
                .build());
    }
}