package com.solenix.spacecraft.controllers;

import com.solenix.spacecraft.dto.Event;
import com.solenix.spacecraft.dto.Latitude;
import com.solenix.spacecraft.dto.Longitude;
import com.solenix.spacecraft.dto.MappedEvent;
import com.solenix.spacecraft.services.PositionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PositionsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PositionsService positionsService;

    @InjectMocks
    private PositionsController positionsController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(positionsController).build();
    }

    @Test
    void testGetEvents() throws Exception {
        when(positionsService.getEventList()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetLatitudes() throws Exception {
        when(positionsService.getLatitudes()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/latitudes"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetLongitudes() throws Exception {
        when(positionsService.getLongitudes()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/longitudes"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetEventById() throws Exception {
        var eventId = "1";
        var event = new Event(Instant.now(), "Event1", eventId, "severe");
        var latitude = new Latitude(Instant.now(), 1.0);
        var longitude = new Longitude(Instant.now(), 1.0);

        var mappedEvent = new MappedEvent.Builder()
                .eventData(event)
                .latitudeData(latitude)
                .longitudeData(longitude)
                .build();

        when(positionsService.getEventById(eventId)).thenReturn(mappedEvent);

        mockMvc.perform(get("/events/mapped/{eventId}", eventId))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllMappedEvents() throws Exception {
        when(positionsService.getAllMappedEvents()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/events/mapped/all"))
                .andExpect(status().isOk());
    }
}