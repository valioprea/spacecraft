package com.solenix.spacecraft.dto;

import java.util.Objects;

public class MappedEvent {
    private final Event eventData;
    private final TimeStampedPosition latitudeData;
    private final TimeStampedPosition longitudeData;
    private final EvaluationReport evaluationReport;

    private MappedEvent(Builder builder) {
        this.eventData = Objects.requireNonNull(builder.eventData, "eventData cannot be null");
        this.latitudeData = Objects.requireNonNull(builder.latitudeData, "latitudeData cannot be null");
        this.longitudeData = Objects.requireNonNull(builder.longitudeData, "longitudeData cannot be null");
        this.evaluationReport = new EvaluationReport(builder.eventData.eventTimestamp(), builder.latitudeData.getTimeStamp(), builder.longitudeData.getTimeStamp());
    }

    public Event getEventData() {
        return eventData;
    }

    public Latitude getLatitudeData() {
        return (Latitude) latitudeData;
    }

    public Longitude getLongitudeData() {
        return (Longitude) longitudeData;
    }

    public EvaluationReport getEvaluationReport() {
        return evaluationReport;
    }

    public static class Builder {
        private Event eventData;
        private TimeStampedPosition latitudeData;
        private TimeStampedPosition longitudeData;

        public Builder eventData(Event eventData) {
            this.eventData = eventData;
            return this;
        }

        public Builder latitudeData(TimeStampedPosition latitudeData) {
            this.latitudeData = latitudeData;
            return this;
        }

        public Builder longitudeData(TimeStampedPosition longitudeData) {
            this.longitudeData = longitudeData;
            return this;
        }

        public MappedEvent build() {
            return new MappedEvent(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MappedEvent that = (MappedEvent) o;
        return Objects.equals(eventData, that.eventData) && Objects.equals(latitudeData, that.latitudeData) && Objects.equals(longitudeData, that.longitudeData) && Objects.equals(evaluationReport, that.evaluationReport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventData, latitudeData, longitudeData, evaluationReport);
    }
}