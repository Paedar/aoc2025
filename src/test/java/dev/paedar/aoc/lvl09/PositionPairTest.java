package dev.paedar.aoc.lvl09;

import dev.paedar.aoc.util.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionPairTest {

    @Test
    void squareSize() {
        var first = new Position(11, 1);
        var second = new Position(2, 5);

        var pair = new PositionPair(first, second);

        assertEquals(50, pair.squareSize());
    }

}