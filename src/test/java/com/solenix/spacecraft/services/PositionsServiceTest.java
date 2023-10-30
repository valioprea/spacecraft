package com.solenix.spacecraft.services;

import com.solenix.spacecraft.dto.*;
import com.solenix.spacecraft.util.ReaderEngine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PositionsServiceTest {
    private static final Event EVENT_1 = new Event(Instant.now(), "Event1", "1", "severe");
    private static final Event EVENT_2 = new Event(Instant.now(), "Event2", "2", "info");
    private static final Latitude LATITUDE_1 = new Latitude(Instant.now(),  1.0);
    private static final Latitude LATITUDE_2 = new Latitude(Instant.now(),  2.0);
    private static final Longitude LONGITUDE_1 = new Longitude(Instant.now(),  1.0);
    private static final Longitude LONGITUDE_2 = new Longitude(Instant.now(),  2.0);
    private static final MappedEvent MAPPED_EVENT_1 = new MappedEvent.Builder()
            .eventData(EVENT_1)
            .latitudeData(LATITUDE_1)
            .longitudeData(LONGITUDE_1)
            .build();
    private static final List<TimeStampedPosition> LATITUDES = List.of(LATITUDE_1, LATITUDE_2);
    private static final List<TimeStampedPosition> LONGITUDES = List.of(LONGITUDE_1, LONGITUDE_2);

    @Mock
    ReaderEngine readerEngine;
    @Mock
    EventPositionMapperService eventPositionMapperService;
    @InjectMocks
    PositionsService positionsService;

    @Test
    void test_getEventList() {
        var mockEventList = List.of(EVENT_1, EVENT_2);
        when(readerEngine.readEvents()).thenReturn(mockEventList);

        assertEquals(mockEventList, positionsService.getEventList());
    }

    @Test
    void test_getLatitudes() {
        var mockLatitudeList = List.of(LATITUDE_1, LATITUDE_2);
        when(readerEngine.readPositions(PositionType.LATITUDE)).thenReturn(LATITUDES);

        assertEquals(mockLatitudeList, positionsService.getLatitudes());
    }

    @Test
    void test_getLongitudes() {
        var mockLongitudeList = List.of(LONGITUDE_1, LONGITUDE_2);
        when(readerEngine.readPositions(PositionType.LONGITUDE)).thenReturn(LONGITUDES);

        assertEquals(mockLongitudeList, positionsService.getLongitudes());
    }

    @Test
    void test_getEventById() {
        when(readerEngine.readEvents()).thenReturn(List.of(EVENT_1, EVENT_2));
        when(eventPositionMapperService.findPosition(EVENT_1.eventTimestamp(), PositionType.LATITUDE)).thenReturn(LATITUDE_1);
        when(eventPositionMapperService.findPosition(EVENT_1.eventTimestamp(), PositionType.LONGITUDE)).thenReturn(LONGITUDE_1);

        var result = positionsService.getEventById(EVENT_1.id());

        assertEquals(EVENT_1.id(), result.getEventData().id());
        assertEquals(MAPPED_EVENT_1.getLatitudeData().latitudePosition(), result.getLatitudeData().latitudePosition());
        assertEquals(MAPPED_EVENT_1.getLongitudeData().longitudePosition(), result.getLongitudeData().longitudePosition());
    }

    @Test
    void test_getEventById_WhenIdIsNotFound_NoSuchElementExceptionIsThrown() {
        when(readerEngine.readEvents()).thenReturn(List.of());
        assertThrows(NoSuchElementException.class, () -> positionsService.getEventById(EVENT_1.id()));
    }

    @Test
    void test_getAllMappedEvents() {
        when(readerEngine.readEvents()).thenReturn(List.of(EVENT_1));

        when(eventPositionMapperService.findPosition(EVENT_1.eventTimestamp(), PositionType.LATITUDE)).thenReturn(LATITUDE_1);
        when(eventPositionMapperService.findPosition(EVENT_1.eventTimestamp(), PositionType.LONGITUDE)).thenReturn(LONGITUDE_1);

        var result = positionsService.getAllMappedEvents();

        assertEquals(1, result.size());
        assertEquals(Set.of(MAPPED_EVENT_1), Set.copyOf(result));
    }
}