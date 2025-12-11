package dev.paedar.aoc.lvl06;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AoCLvl06Test {

    @Test
    void testSolutionSums() {
        var lines = InputReader.readLines("06.example.txt");

        var solutionSum = AoCLvl06.solutionSum(lines);
        assertEquals(4277556L, solutionSum);
    }

}