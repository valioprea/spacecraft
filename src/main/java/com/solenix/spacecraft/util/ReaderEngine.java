package com.solenix.spacecraft.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solenix.spacecraft.dto.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ReaderEngine {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String EVENTS_FILE = "static/events.json";
    private static final String LATITUDES_FILE = "static/latitudes.json";
    private static final String LONGITUDES_FILE = "static/longitudes.json";

    public List<Event> readEvents() {
        try {
            return List.of(mapper.readValue(readResourceContent(EVENTS_FILE), Event[].class));
        } catch (IOException e) {
            throw new IllegalStateException("File reading error");
        }
    }

    public List<TimeStampedPosition> readPositions(PositionType positionType) {
        return switch (positionType) {
            case LATITUDE -> readLatitudes();
            case LONGITUDE -> readLongitudes();
        };
    }

    private List<TimeStampedPosition> readLatitudes(){
        try {
            return List.of(mapper.readValue(readResourceContent(LATITUDES_FILE), Latitude[].class));
        } catch (IOException e) {
            throw new IllegalStateException("File reading error");
        }
    }

    private List<TimeStampedPosition> readLongitudes(){
        try {
            return List.of(mapper.readValue(readResourceContent(LONGITUDES_FILE), Longitude[].class));
        } catch (IOException e) {
            throw new IllegalStateException("File reading error");
        }
    }

    /**
     * Custom deserializers for the DTOs, to be able to parse the JSON files and set the types correctly
     */

    public static class LatitudeDeserializer extends JsonDeserializer<Latitude> {
        @Override
        public Latitude deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            var instant = getInstantHelper(node.get("timestamp").textValue());
            var position = node.get("position").asDouble();
            return new Latitude(instant, position);
        }
    }

    public static class LongitudeDeserializer extends JsonDeserializer<Longitude> {
        @Override
        public Longitude deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            var instant = getInstantHelper(node.get("timestamp").textValue());
            var position = node.get("position").asDouble();
            return new Longitude(instant, position);
        }
    }

    public static class EventDeserializer extends JsonDeserializer<Event> {
        @Override
        public Event deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            var instant = getInstantHelper(node.get("occurrence_time").textValue());
            var eventName = node.get("event_name").textValue();
            var eventId = node.get("id").textValue();
            var severity = node.get("severity").textValue();
            return new Event(instant, eventName, eventId, severity);
        }
    }

    /**
     * Helper method for the deserializers
     * @param timestamp = the timestamp from the JSON file
     * @return = the timestamp in the correct format
     */
    private static Instant getInstantHelper(String timestamp) {
        var localDateTime = LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        return localDateTime.toInstant(ZoneOffset.UTC);
    }

    /**
     * Helper method for reading the files
     * @param filePath = the path of the file in String format
     * @return = the file contents as an InputStream
     * @throws IOException = if the file is not found
     */
    private InputStream readResourceContent(String filePath) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResourceAsStream(filePath);
    }
}