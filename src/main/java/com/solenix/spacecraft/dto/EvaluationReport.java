package com.solenix.spacecraft.dto;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class EvaluationReport {
    private final Instant eventTimeStamp;
    private final Instant latitudeTimeStamp;
    private final Instant longitudeTimeStamp;
    private final Duration standardComparisonSetting = Duration.ofMinutes(5L);
    private boolean isLatitudeRecent;
    private boolean isLongitudeRecent;

    public EvaluationReport(Instant eventTimeStamp, Instant latitudeTimeStamp, Instant longitudeTimeStamp) {
        this.eventTimeStamp = Objects.requireNonNull(eventTimeStamp, "eventTimeStamp cannot be null");
        this.latitudeTimeStamp = Objects.requireNonNull(latitudeTimeStamp, "latitudeTimeStamp cannot be null");
        this.longitudeTimeStamp = Objects.requireNonNull(longitudeTimeStamp, "longitudeTimeStamp cannot be null");
        evaluateLatitudeAccuracy();
        evaluateLongitudeAccuracy();
    }

    public boolean isLatitudeRecent() {
        return isLatitudeRecent;
    }

    public boolean isLongitudeRecent() {
        return isLongitudeRecent;
    }

    private void evaluateLatitudeAccuracy() {
        var latDif = Duration.between(eventTimeStamp, latitudeTimeStamp).abs();
        isLatitudeRecent = latDif.compareTo(standardComparisonSetting) <= 0;
    }

    private void evaluateLongitudeAccuracy() {
        var longDif = Duration.between(eventTimeStamp, longitudeTimeStamp).abs();
        isLongitudeRecent = longDif.compareTo(standardComparisonSetting) <= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvaluationReport that = (EvaluationReport) o;
        return isLatitudeRecent == that.isLatitudeRecent && isLongitudeRecent == that.isLongitudeRecent && Objects.equals(eventTimeStamp, that.eventTimeStamp) && Objects.equals(latitudeTimeStamp, that.latitudeTimeStamp) && Objects.equals(longitudeTimeStamp, that.longitudeTimeStamp) && Objects.equals(standardComparisonSetting, that.standardComparisonSetting);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventTimeStamp, latitudeTimeStamp, longitudeTimeStamp, isLatitudeRecent, isLongitudeRecent, standardComparisonSetting);
    }
}