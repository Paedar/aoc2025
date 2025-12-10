package dev.paedar.aoc.lvl02;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AoCLvl02Test {

    @Test
    void testIsValid() {
        assertTrue(AoCLvl02.isValid("101"));
        assertFalse(AoCLvl02.isValid("11"));
        assertFalse(AoCLvl02.isValid("1010"));
    }


    @Test
    void sumInvalidIdsInRange() {
        assertEquals(33, AoCLvl02.sumInvalidIds(11, 22));
        assertEquals(99, AoCLvl02.sumInvalidIds(95, 115));
        assertEquals(1010, AoCLvl02.sumInvalidIds(998, 1012));
        assertEquals(0, AoCLvl02.sumInvalidIds(2121212118, 2121212124));
    }

    @Test
    void testExampleInput() {
        var tokens = InputReader.readTokens("02.example.txt");

        assertEquals(22, tokens.size());

        assertEquals(1227775554, AoCLvl02.sumInvalidIdsFromRanges(tokens));
    }
}