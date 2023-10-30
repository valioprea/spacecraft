package com.solenix.spacecraft.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.solenix.spacecraft.util.ReaderEngine;

import java.time.Instant;
import java.util.Objects;

@JsonDeserialize(using = ReaderEngine.LatitudeDeserializer.class)
public record Latitude(
        Instant latitudeTimestamp,
        Double latitudePosition
) implements TimeStampedPosition {
    public Latitude {
        Objects.requireNonNull(latitudeTimestamp, "latitudeTimestamp cannot be null");
        Objects.requireNonNull(latitudePosition, "latitudePosition cannot be null");
    }

    @JsonIgnore
    @Override
    public Instant getTimeStamp() {
        return this.latitudeTimestamp;
    }
}