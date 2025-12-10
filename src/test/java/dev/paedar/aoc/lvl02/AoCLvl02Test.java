package dev.paedar.aoc.lvl02;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AoCLvl02Test {

    @Test
    void testIsNotTwiceRepeatedNumber() {
        assertTrue(AoCLvl02.isNotTwiceRepeatedNumber("101"));
        assertFalse(AoCLvl02.isNotTwiceRepeatedNumber("11"));
        assertTrue(AoCLvl02.isNotTwiceRepeatedNumber("111"));
        assertFalse(AoCLvl02.isNotTwiceRepeatedNumber("1010"));
    }

    @Test
    void testIsNotRepeatedNumber() {
        assertTrue(AoCLvl02.isNotRepeatedNumber("101"));
        assertFalse(AoCLvl02.isNotRepeatedNumber("11"));
        assertFalse(AoCLvl02.isNotRepeatedNumber("111"));
        assertFalse(AoCLvl02.isNotRepeatedNumber("1010"));
    }


    @Test
    void sumInvalidIdsInRange() {
        assertEquals(33, AoCLvl02.sumInvalidIds(11, 22, AoCLvl02::isNotTwiceRepeatedNumber));
        assertEquals(99, AoCLvl02.sumInvalidIds(95, 115, AoCLvl02::isNotTwiceRepeatedNumber));
        assertEquals(1010, AoCLvl02.sumInvalidIds(998, 1012, AoCLvl02::isNotTwiceRepeatedNumber));
        assertEquals(0, AoCLvl02.sumInvalidIds(2121212118, 2121212124, AoCLvl02::isNotTwiceRepeatedNumber));
        assertEquals(2121212121, AoCLvl02.sumInvalidIds(2121212118, 2121212124, AoCLvl02::isNotRepeatedNumber));
    }

    @Test
    void testExampleInput() {
        var tokens = InputReader.readTokens("02.example.txt");

        assertEquals(22, tokens.size());

        assertEquals(1227775554, AoCLvl02.sumInvalidIdsFromRanges(tokens, AoCLvl02::isNotTwiceRepeatedNumber));
        assertEquals(4174379265L, AoCLvl02.sumInvalidIdsFromRanges(tokens, AoCLvl02::isNotRepeatedNumber));
    }
}