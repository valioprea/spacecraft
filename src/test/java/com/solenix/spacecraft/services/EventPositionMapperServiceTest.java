package com.solenix.spacecraft.services;

import com.solenix.spacecraft.dto.Latitude;
import com.solenix.spacecraft.dto.Longitude;
import com.solenix.spacecraft.dto.PositionType;
import com.solenix.spacecraft.dto.TimeStampedPosition;
import com.solenix.spacecraft.util.ReaderEngine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventPositionMapperServiceTest {
    private static final Instant NOW = Instant.now();
    private static final Instant TIMESTAMP_1 = NOW;
    private static final Instant TIMESTAMP_2 = NOW.plus(Duration.ofMinutes(5));
    private static final Double LATITUDE_VALUE_1 = 1.0;
    private static final Double LATITUDE_VALUE_2 = 2.0;
    private static final Double LONGITUDE_VALUE_1 = 3.0;
    private static final Double LONGITUDE_VALUE_2 = 4.0;
    private static final Latitude LATITUDE_1 = new Latitude(TIMESTAMP_1, LATITUDE_VALUE_1);
    private static final Latitude LATITUDE_2 = new Latitude(TIMESTAMP_2, LATITUDE_VALUE_2);
    private static final Longitude LONGITUDE_1 = new Longitude(TIMESTAMP_1, LONGITUDE_VALUE_1);
    private static final Longitude LONGITUDE_2 = new Longitude(TIMESTAMP_2, LONGITUDE_VALUE_2);
    private static final List<TimeStampedPosition> LATITUDES = List.of(LATITUDE_1, LATITUDE_2);
    private static final List<TimeStampedPosition> LONGITUDES = List.of(LONGITUDE_1, LONGITUDE_2);

    @Mock
    private ReaderEngine readerEngine;
    @InjectMocks
    private EventPositionMapperService eventPositionMapperService;

    /**
     * TEST FOR LATITUDES
     */

    @Test
    void test_findLatitude_whenMatchingTimestamps_ShouldReturnNOW() {
        when(readerEngine.readPositions(PositionType.LATITUDE)).thenReturn(LATITUDES);
        var result = eventPositionMapperService.findPosition(NOW, PositionType.LATITUDE);
        assertEquals(NOW, result.getTimeStamp());
    }

    @Test
    void test_findLatitude_whenNoMatchingTimestamps_ShouldReturnClosestTimestamp() {
        when(readerEngine.readPositions(PositionType.LATITUDE)).thenReturn(LATITUDES);
        var result = eventPositionMapperService.findPosition(NOW.plus(Duration.ofMinutes(4)), PositionType.LATITUDE);
        assertEquals(TIMESTAMP_2, result.getTimeStamp());
    }

    @Test
    void test_findLatitude_whenNoMatchingTimestampsAndTwoEquallyClose_ShouldReturnClosestTimestampAfterNOW() {

        var lat1 = new Latitude(NOW.minus(Duration.ofMinutes(5)), LATITUDE_VALUE_1);
        var lat2 = new Latitude(NOW.plus(Duration.ofMinutes(5)), LATITUDE_VALUE_2);

        var d1 = Duration.between(lat1.latitudeTimestamp(), NOW);
        var d2 = Duration.between(lat2.latitudeTimestamp(), NOW);

        assertEquals(d1.abs(), d2.abs());

        when(readerEngine.readPositions(PositionType.LATITUDE)).thenReturn(List.of(lat1, lat2));

        var result = eventPositionMapperService.findPosition(NOW, PositionType.LATITUDE);

        assertEquals(TIMESTAMP_2, result.getTimeStamp());
    }

    /**
     * TEST FOR LONGITUDES
     */

    @Test
    void test_findLongitude_whenMatchingTimestamps_ShouldReturnNOW() {
        when(readerEngine.readPositions(PositionType.LONGITUDE)).thenReturn(LONGITUDES);
        var result = eventPositionMapperService.findPosition(NOW, PositionType.LONGITUDE);
        assertEquals(NOW, result.getTimeStamp());
    }

    @Test
    void test_findLongitude_whenNoMatchingTimestamps_ShouldReturnClosestTimestamp() {
        when(readerEngine.readPositions(PositionType.LONGITUDE)).thenReturn(LONGITUDES);
        var result = eventPositionMapperService.findPosition(NOW.plus(Duration.ofMinutes(4)), PositionType.LONGITUDE);
        assertEquals(TIMESTAMP_2, result.getTimeStamp());
    }

    @Test
    void test_findLongitude_whenNoMatchingTimestampsAndTwoEquallyClose_ShouldReturnClosestTimestampAfterNOW() {

        var long1 = new Longitude(NOW.minus(Duration.ofMinutes(5)), LONGITUDE_VALUE_1);
        var long2 = new Longitude(NOW.plus(Duration.ofMinutes(5)), LONGITUDE_VALUE_2);

        var d1 = Duration.between(long1.longitudeTimestamp(), NOW);
        var d2 = Duration.between(long2.longitudeTimestamp(), NOW);

        assertEquals(d1.abs(), d2.abs());

        when(readerEngine.readPositions(PositionType.LONGITUDE)).thenReturn(List.of(long1, long2));

        var result = eventPositionMapperService.findPosition(NOW, PositionType.LONGITUDE);

        assertEquals(TIMESTAMP_2, result.getTimeStamp());
    }
}