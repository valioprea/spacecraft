package com.solenix.spacecraft.dto;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class EvaluationReportTest {

    @Test
    void testEvaluationReportConstructor() {
        var eventTimeStamp = Instant.now();
        var latitudeTimeStamp = eventTimeStamp.plus(Duration.ofMinutes(3));
        var longitudeTimeStamp = eventTimeStamp.plus(Duration.ofMinutes(6));

        var evaluationReport = new EvaluationReport(eventTimeStamp, latitudeTimeStamp, longitudeTimeStamp);

        assertTrue(evaluationReport.isLatitudeRecent());
        assertFalse(evaluationReport.isLongitudeRecent());
    }

    @Test
    void testEvaluationReportWithNullValues_ShouldThrowNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> new EvaluationReport(Instant.now(), null, null));
    }

    @Test
    void testEqualsAndHashCode() {
        var eventTimeStamp = Instant.now();
        var latitudeTimeStamp = eventTimeStamp.plus(Duration.ofMinutes(3));
        var longitudeTimeStamp = eventTimeStamp.plus(Duration.ofMinutes(6));

        var report1 = new EvaluationReport(eventTimeStamp, latitudeTimeStamp, longitudeTimeStamp);
        var report2 = new EvaluationReport(eventTimeStamp, latitudeTimeStamp, longitudeTimeStamp);

        assertEquals(report1, report2);
        assertEquals(report1.hashCode(), report2.hashCode());
    }
}