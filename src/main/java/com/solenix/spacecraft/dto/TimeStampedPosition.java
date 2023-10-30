package com.solenix.spacecraft.dto;

import java.time.Instant;

public interface TimeStampedPosition {
    Instant getTimeStamp();
}