package com.solenix.spacecraft.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.solenix.spacecraft.util.ReaderEngine;

import java.time.Instant;
import java.util.Objects;

@JsonDeserialize(using = ReaderEngine.EventDeserializer.class)
public record Event(
        Instant eventTimestamp,
        String eventName,
        String id,
        String severity
) {
    public Event {
        Objects.requireNonNull(eventTimestamp, "eventTimestamp cannot be null");
        Objects.requireNonNull(eventName, "eventName cannot be null");
        Objects.requireNonNull(id, "id cannot be null");
        Objects.requireNonNull(severity, "severity cannot be null");
    }
}