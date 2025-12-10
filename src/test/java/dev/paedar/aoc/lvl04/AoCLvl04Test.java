package dev.paedar.aoc.lvl04;

import dev.paedar.aoc.util.GridInfo;
import dev.paedar.aoc.util.InputReader;
import dev.paedar.aoc.util.Position;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AoCLvl04Test {

    @Test
    void testIsAccessible() {
        var grid = GridInfo.of(InputReader.readLines("04.example.txt"));

        assertTrue(AoCLvl04.isAccessibleStack(grid, new Position(2, 0))); // enough free room
        assertFalse(AoCLvl04.isAccessibleStack(grid, new Position(0, 0))); // Not a stack
        assertFalse(AoCLvl04.isAccessibleStack(grid, new Position(1, 1))); // Not accessible, to many surrounding stacks
    }

    @Test
    void countAccessibleStacks() {
        var grid = GridInfo.of(InputReader.readLines("04.example.txt"));

        assertEquals(13, AoCLvl04.countAccessibleStacks(grid));
    }
}