package com.solenix.spacecraft.services;

import com.solenix.spacecraft.dto.PositionType;
import com.solenix.spacecraft.dto.TimeStampedPosition;
import com.solenix.spacecraft.util.ReaderEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class EventPositionMapperService {

    private static final Logger LOG = LoggerFactory.getLogger(EventPositionMapperService.class);
    private static final int UNIQUE = 1;
    private final ReaderEngine readerEngine;

    public EventPositionMapperService(ReaderEngine readerEngine) {
        this.readerEngine = readerEngine;
    }

    public TimeStampedPosition findPosition(Instant timestamp, PositionType positionType) {

        var timeStampedPositions = readerEngine.readPositions(positionType);
        // check first for exact match
        for (var position : timeStampedPositions) {
            if (position.getTimeStamp().equals(timestamp)) {
                return position;
            }
        }

        LOG.info("Exact match for {} not found for timestamp: {}", positionType.name(), timestamp);

        var differencesMap = new HashMap<TimeStampedPosition, Duration>();
        timeStampedPositions.forEach(pos -> differencesMap.put(pos, Duration.between(pos.getTimeStamp(), timestamp).abs()));

        // find the min value in the map
        var minDiffAbs = differencesMap.entrySet().stream().min(Map.Entry.comparingByValue()).orElseThrow(IllegalArgumentException::new).getValue();

        // check to see if there are duplicates of this minimum in the map
        var duplicates = differencesMap.entrySet().stream().filter(entry -> entry.getValue().equals(minDiffAbs)).count();

        // if no duplicates, then just return the min value
        if (duplicates == UNIQUE) {
            return differencesMap.entrySet().stream().min(Map.Entry.comparingByValue()).orElseThrow(IllegalArgumentException::new).getKey();
        } else {
            return constructPositionHelper(timestamp, positionType, differencesMap, minDiffAbs);
        }
    }

    private TimeStampedPosition constructPositionHelper(
            Instant timestamp,
            PositionType positionType,
            Map<TimeStampedPosition, Duration> differencesMap,
            Duration minDiffAbs
    ) {
        return differencesMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(minDiffAbs))
                .filter(entry -> entry.getKey().getTimeStamp().isAfter(timestamp)) // Controls rounding to past / future
                .findFirst()
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new IllegalStateException("No " + positionType.name() + " found"));
    }
}