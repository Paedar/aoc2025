package dev.paedar.aoc.lvl06;

import dev.paedar.aoc.util.InputReader;
import dev.paedar.aoc.util.Util;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class AoCLvl06 {

    private static final Logger LOGGER = Logger.getLogger(AoCLvl06.class.getName());

    static void main() {
        var lines = InputReader.readLines("06.input.txt");

        var solutionSum = solutionSum(lines);
        LOGGER.log(Level.INFO, "Solution sum: {0}", solutionSum);

    }

    public static long solutionSum(List<String> lines) {
        var lineCount = lines.size();
        var tokens = lines.stream()
                          .map(content -> Util.split(content, "\\s+"))
                          .flatMap(List::stream)
                          .filter(Predicate.not(String::isBlank))
                          .toList();
        var columnCount = tokens.size() / lineCount;
        var columns = IntStream.range(0, columnCount)
                               .mapToObj(i -> takeTokens(i, lineCount, columnCount, tokens))
                               .toList();
        var problems = columns.stream()
                              .map(AoCLvl06::ofTokens)
                              .toList();

        return problems.stream()
                       .mapToLong(Problem::solution)
                       .sum();
    }

    private static List<String> takeTokens(int offset, int lineCount, int columnCount, List<String> tokens) {
        return IntStream.range(0, lineCount)
                        .map(i -> i * columnCount + offset)
                        .mapToObj(tokens::get)
                        .toList();
    }

    private static Problem ofTokens(List<String> column) {
        var operator = column.getLast();

        var operands = column.subList(0, column.size() - 1)
                             .stream()
                             .map(Long::parseLong)
                             .toList();

        return switch (operator) {
            case "*" -> new ProductProblem(operands);
            case "+" -> new SumProblem(operands);
            default -> throw new UnsupportedOperationException("Operator %s unknown".formatted(operator));
        };
    }

}
