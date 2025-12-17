package dev.paedar.aoc.lvl08;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AoCLvl08Test {

    @Test
    void productSizeOfBiggest3Circuits() {
        var tokens = InputReader.readTokens("08.example.txt");

        var biggest3CircuitSizeProduct = AoCLvl08.productSizeOfBiggest3Circuits(tokens, 10);

        assertEquals(40L, biggest3CircuitSizeProduct);
    }

    @Test
    void testWallDistanceProductAfterMergingAllCircuits() {

        var tokens = InputReader.readTokens("08.example.txt");

        var wallDistance = AoCLvl08.wallDistanceProductAfterMergingAllCircuits(tokens);

        assertEquals(25272, wallDistance);
    }

}