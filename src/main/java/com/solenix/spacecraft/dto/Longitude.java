package com.solenix.spacecraft.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.solenix.spacecraft.util.ReaderEngine;

import java.time.Instant;
import java.util.Objects;

@JsonDeserialize(using = ReaderEngine.LongitudeDeserializer.class)
public record Longitude(
        Instant longitudeTimestamp,
        Double longitudePosition
) implements TimeStampedPosition {
    public Longitude {
        Objects.requireNonNull(longitudeTimestamp, "longitudeTimestamp cannot be null");
        Objects.requireNonNull(longitudePosition, "longitudePosition cannot be null");
    }

    @JsonIgnore
    @Override
    public Instant getTimeStamp() {
        return this.longitudeTimestamp;
    }
}