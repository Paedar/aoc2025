package dev.paedar.aoc.lvl03;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AoCLvl03Test {

    @Test
    void determineJoltage() {
        assertEquals(98, AoCLvl03.determineJoltage("987654321111111", 2));
        assertEquals(89, AoCLvl03.determineJoltage("811111111111119", 2));
        assertEquals(78, AoCLvl03.determineJoltage("234234234234278", 2));
        assertEquals(92, AoCLvl03.determineJoltage("818181911112111", 2));
    }

    @Test
    void determineHighJoltage() {
        assertEquals(987654321111L, AoCLvl03.determineJoltage("987654321111111", 12));
        assertEquals(811111111119L, AoCLvl03.determineJoltage("811111111111119", 12));
        assertEquals(434234234278L, AoCLvl03.determineJoltage("234234234234278", 12));
        assertEquals(888911112111L, AoCLvl03.determineJoltage("818181911112111", 12));
    }

    @Test
    void sumOfIdealJoltages() {
        var banks = InputReader.readLines("03.example.txt");

        assertEquals(357, AoCLvl03.sumOfIdealJoltages(banks, 2));
    }

    @Test
    void sumOfIdealHighJoltages() {
        var banks = InputReader.readLines("03.example.txt");

        assertEquals(3121910778619L, AoCLvl03.sumOfIdealJoltages(banks, 12));
    }

}