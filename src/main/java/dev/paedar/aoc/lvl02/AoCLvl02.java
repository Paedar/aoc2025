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

        var sumOfInvalidIds = sumInvalidIdsFromRanges(ids, AoCLvl02::isNotTwiceRepeatedNumber);
        LOGGER.log(Level.INFO, "Sum of invalid ids: {0}", sumOfInvalidIds);

        sumOfInvalidIds = sumInvalidIdsFromRanges(ids, AoCLvl02::isNotRepeatedNumber);
        LOGGER.log(Level.INFO, "Sum of invalid ids, part 2: {0}", sumOfInvalidIds);
    }

    public static boolean isNotTwiceRepeatedNumber(String id) {
        return !id.matches("^(\\d+)\\1$");
    }

    public static boolean isNotRepeatedNumber(String id) {
        return !id.matches("^(\\d+)\\1+$");
    }

    public static Long sumInvalidIds(long startInclusive, long endInclusive, Predicate<String> isValid) {
        return getInvalidIds(startInclusive, endInclusive, isValid)
                         .sum();
    }

    private static LongStream getInvalidIds(long startInclusive, long endInclusive, Predicate<String> isValid) {
        return LongStream.range(startInclusive, endInclusive + 1)
                         .mapToObj(Long::toString)
                         .filter(Predicate.not(isValid))
                         .mapToLong(Long::parseLong);
    }

    public static Long sumInvalidIdsFromRanges(List<String> tokens, Predicate<String> isValid) {
        return tokens.stream()
                       .map(Long::parseLong)
                       .gather(Gatherers.windowFixed(2))
                       .mapToLong((range) -> sumInvalidIds(range.getFirst(), range.getLast(), isValid))
                       .sum();
    }

}
