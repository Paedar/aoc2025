package dev.paedar.aoc.lvl09;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AoCLvl09Test {

    @Test
    void biggestSquareOfTiling() {
        var tokens = InputReader.readTokens("09.example.txt");

        var biggestSquareSize = AoCLvl09.biggestSquareOfTiling(tokens);

        assertEquals(50L, biggestSquareSize);
    }

}