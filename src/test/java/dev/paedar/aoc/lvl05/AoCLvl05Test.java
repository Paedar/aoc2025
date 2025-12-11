package dev.paedar.aoc.lvl05;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AoCLvl05Test {

    @Test
    void countFreshIngredients() {
        var lines = InputReader.readLines("05.example.txt");

        var ranges = AoCLvl05.readRanges(lines);
        var ingredients = AoCLvl05.readIngredients(lines);

        var freshCount = AoCLvl05.countFreshIngredients(ingredients, ranges);
        assertEquals(3, freshCount);
    }

    @Test
    void countAllFreshIngredients() {
        var lines = InputReader.readLines("05.example.txt");

        var ranges = AoCLvl05.readRanges(lines);

        var freshCount = AoCLvl05.countFreshIngredients(ranges);
        assertEquals(14, freshCount);
    }

}