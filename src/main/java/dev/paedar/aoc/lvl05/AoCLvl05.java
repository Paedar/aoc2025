package dev.paedar.aoc.lvl05;

import dev.paedar.aoc.util.InputReader;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AoCLvl05 {

    private static final Logger LOGGER = Logger.getLogger(AoCLvl05.class.getName());

    static void main() {
        var lines = InputReader.readLines("05.input.txt");

        var ranges = readRanges(lines);
        var ingredients = readIngredients(lines);
        var freshIngredientCount = countFreshIngredients(ingredients, ranges);
        LOGGER.log(Level.INFO, "Fresh ingredient count: {0}", freshIngredientCount);
    }

    public static Collection<Range> readRanges(List<String> lines) {
        return lines.stream()
                    .takeWhile(Predicate.not(String::isBlank))
                    .map(AoCLvl05::toRange)
                    .toList();
    }

    public static Collection<Long> readIngredients(List<String> lines) {
        return lines.stream()
                    .filter(l -> l.matches("^\\d+$"))
                    .map(Long::parseLong)
                    .toList();
    }

    public static long countFreshIngredients(Collection<Long> ingredients, Collection<Range> ranges) {
        return ingredients.stream()
                          .filter(i -> anyRangeContains(ranges, i))
                          .count();
    }

    private static boolean anyRangeContains(Collection<Range> ranges, Long ingredient) {
        return ranges.stream()
                     .anyMatch(r -> r.contains(ingredient));
    }

    private static Range toRange(String line) {
        var split = line.split("-");
        if (split.length != 2) {
            throw new IllegalArgumentException("Range not properly defined");
        }
        return new Range(Long.parseLong(split[0]), Long.parseLong(split[1]));
    }

}
