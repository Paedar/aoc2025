package dev.paedar.aoc.lvl02;

import dev.paedar.aoc.util.InputReader;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Gatherers;
import java.util.stream.LongStream;

public class AoCLvl02 {

    private static final Logger LOGGER = Logger.getLogger(AoCLvl02.class.getName());

    public static void main(String[] args) {
        var ids = InputReader.readTokens("02.input.txt");

        var sumOfInvalidIds = sumInvalidIdsFromRanges(ids);
        LOGGER.log(Level.INFO, "Sum of invalid ids: {0}", sumOfInvalidIds);
    }

    public static boolean isValid(String id) {
        return !id.matches("^(\\d+)\\1$");
    }

    public static Long sumInvalidIds(long startInclusive, long endInclusive) {
        return getInvalidIds(startInclusive, endInclusive)
                         .sum();
    }

    private static LongStream getInvalidIds(long startInclusive, long endInclusive) {
        return LongStream.range(startInclusive, endInclusive + 1)
                         .mapToObj(Long::toString)
                         .filter(Predicate.not(AoCLvl02::isValid))
                         .mapToLong(Long::parseLong);
    }

    public static Long sumInvalidIdsFromRanges(List<String> tokens) {
        return tokens.stream()
                       .map(Long::parseLong)
                       .gather(Gatherers.windowFixed(2))
                       .mapToLong((range) -> sumInvalidIds(range.getFirst(), range.getLast()))
                       .sum();
    }

}
