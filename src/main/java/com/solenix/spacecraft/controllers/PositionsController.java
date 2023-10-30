package com.solenix.spacecraft.controllers;

import com.solenix.spacecraft.dto.*;
import com.solenix.spacecraft.services.PositionsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PositionsController {

    private final PositionsService positionsService;

    public PositionsController(PositionsService positionsService) {
        this.positionsService = positionsService;
    }

    @GetMapping("/events")
    @Operation(summary = "Get events", description = "Retrieves all the events from the .json file")
    public List<Event> getEvents() {
        return positionsService.getEventList();
    }

    @GetMapping("/latitudes")
    @Operation(summary = "Get latitudes", description = "Retrieves all the latitudes from the .json file")
    public List<TimeStampedPosition> getLatitudes() {
        return positionsService.getLatitudes();
    }

    @GetMapping("/longitudes")
    @Operation(summary = "Get longitudes", description = "Retrieves all the longitudes from the .json file")
    public List<TimeStampedPosition> getLongitudes() {
        return positionsService.getLongitudes();
    }

    /**
     * This is the first feature required: Delivering latitude / longitude for a given Id
     * @param eventId = the id of the event
     * @return = the latitude / longitude for the given event and their timestamps
     */
    @GetMapping("/events/mapped/{eventId}")
    @Operation(summary = "Get mapped event by id", description = "Retrieves the mapped event with the given id from the .json file along with information about latitude and longitude at the most accurate timestamp available")
    public MappedEvent getMappedEventById(@NonNull @PathVariable String eventId) {
        return positionsService.getEventById(eventId);
    }

    /**
     * This is the second feature required: Delivering latitude / longitude for all events
     * @return = the latitude / longitude for all events and their timestamps
     */
    @GetMapping("/events/mapped/all")
    @Operation(summary = "Get all mapped events", description = "Retrieves all the mapped events from the .json file along with information about latitude and longitude at the most accurate timestamps available")
    public List<MappedEvent> getAllMappedEvents() {
        return positionsService.getAllMappedEvents();
    }
}