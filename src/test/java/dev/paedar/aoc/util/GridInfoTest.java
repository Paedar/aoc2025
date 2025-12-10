package dev.paedar.aoc.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GridInfoTest {

    @Test
    void testReadFromLines() {
        var lines = InputReader.readLines("grid.example.txt");

        var grid = GridInfo.of(lines);
        assertNotNull(grid);
        assertEquals(10, grid.width());
        assertEquals(10, grid.height());

        assertEquals('.', grid.charAt(new Position(0,0)));
        assertEquals('@', grid.charAt(new Position(0,1)));
        assertEquals('@', grid.charAt(new Position(0,1)));
        assertEquals('.', grid.charAt(new Position(1,0)));
    }

}