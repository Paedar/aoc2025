package dev.paedar.aoc.lvl05;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AoCLvl05Test {

    @Test
    void isIngredientFresh() {
        var range = new Range(3,5);

        assertFalse(range.contains(2));
        assertTrue(range.contains(3));
        assertTrue(range.contains(4));
        assertTrue(range.contains(5));
        assertFalse(range.contains(6));
    }

    @Test
    void countFreshIngredients() {
        var lines = InputReader.readLines("05.example.txt");

        var ranges = AoCLvl05.readRanges(lines);
        var ingredients = AoCLvl05.readIngredients(lines);

        var freshCount = AoCLvl05.countFreshIngredients(ingredients, ranges);
        assertEquals(3, freshCount);
    }
}