package com.solenix.spacecraft.services;

import com.solenix.spacecraft.dto.*;
import com.solenix.spacecraft.util.ReaderEngine;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PositionsService {

    private final ReaderEngine readerEngine;
    private final EventPositionMapperService eventPositionMapperService;

    public PositionsService(ReaderEngine readerEngine, EventPositionMapperService eventPositionMapperService) {
        this.readerEngine = readerEngine;
        this.eventPositionMapperService = eventPositionMapperService;
    }

    public List<Event> getEventList() {
        return readerEngine.readEvents();
    }

    public List<TimeStampedPosition> getLatitudes() {
        return readerEngine.readPositions(PositionType.LATITUDE);
    }

    public List<TimeStampedPosition> getLongitudes() {
        return readerEngine.readPositions(PositionType.LONGITUDE);
    }

    public MappedEvent getEventById(String eventId) {
        return readerEngine.readEvents().stream()
                .filter(event -> event.id().equals(eventId))
                .findFirst()
                .map(this::constructMappedEvent)
                .orElseThrow(() -> new NoSuchElementException("No event found with id: " + eventId));
    }

    public List<MappedEvent> getAllMappedEvents() {
        return readerEngine.readEvents().stream().map(this::constructMappedEvent).toList();
    }

    private MappedEvent constructMappedEvent(Event event){
        return new MappedEvent.Builder()
                .eventData(event)
                .latitudeData(eventPositionMapperService.findPosition(event.eventTimestamp(), PositionType.LATITUDE))
                .longitudeData(eventPositionMapperService.findPosition(event.eventTimestamp(), PositionType.LONGITUDE))
                .build();
    }
}