package com.solenix.spacecraft.dto;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void testEventRecord() {
        var eventTimestamp = Instant.now();
        var eventName = "TestEvent";
        var id = "12345";
        var severity = "High";

        var event = new Event(eventTimestamp, eventName, id, severity);

        assertEquals(eventTimestamp, event.eventTimestamp());
        assertEquals(eventName, event.eventName());
        assertEquals(id, event.id());
        assertEquals(severity, event.severity());
    }

    @Test
    void testEventRecordWithNullValues() {
        var eventTimestamp = Instant.now();
        assertThrows(NullPointerException.class, () -> new Event(eventTimestamp, null, null, null));
    }
}