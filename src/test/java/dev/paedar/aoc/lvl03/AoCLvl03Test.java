package dev.paedar.aoc.lvl03;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AoCLvl03Test {

    @Test
    void determineJoltage() {
        assertEquals(98, AoCLvl03.determineJoltage("987654321111111"));
        assertEquals(89, AoCLvl03.determineJoltage("811111111111119"));
        assertEquals(78, AoCLvl03.determineJoltage("234234234234278"));
        assertEquals(92, AoCLvl03.determineJoltage("818181911112111"));
    }

    @Test
    void sumOfIdealJoltages() {
        var banks = InputReader.readLines("03.example.txt");

        assertEquals(357, AoCLvl03.sumOfIdealJoltages(banks));
    }

}