package com.solenix.spacecraft.util;

import com.solenix.spacecraft.dto.PositionType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReaderEngineTest {
    private final ReaderEngine readerEngine = new ReaderEngine();
    @Test
    void test_readEvents() {
        assertEquals(10, readerEngine.readEvents().size());
    }

    @Test
    void test_readLatitudes() {
        assertEquals(39, readerEngine.readPositions(PositionType.LATITUDE).size());
    }

    @Test
    void test_readLongitudes() {
        assertEquals(39, readerEngine.readPositions(PositionType.LONGITUDE).size());
    }
}