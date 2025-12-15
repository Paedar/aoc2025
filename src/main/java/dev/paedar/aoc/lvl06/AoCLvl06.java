package dev.paedar.aoc.lvl06;

import dev.paedar.aoc.util.InputReader;
import dev.paedar.aoc.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;

public class AoCLvl06 {

    private static final Logger LOGGER = Logger.getLogger(AoCLvl06.class.getName());

    static void main() {
        var lines = InputReader.readLines("06.input.txt");

        var solutionSum = solutionSum(lines);
        LOGGER.log(Level.INFO, "Solution sum: {0}", solutionSum);

        var cephalopodSolutionSum = cephalopodSolutionSum(lines);
        LOGGER.log(Level.INFO, "cephalopodSolutionSum sum: {0}", cephalopodSolutionSum);

    }

    public static long cephalopodSolutionSum(List<String> lines) {
        var maxLineLength = lines.stream()
                                 .mapToInt(String::length)
                                 .max()
                                 .orElseThrow();

        var individualCharacters = lines.stream()
                                        .map(line -> rightPad(line, maxLineLength, ' '))
                                        .map(s -> Util.split(s, ""))
                                        .flatMap(List::stream)
                                        .toList();
        var lineCount = lines.size();
        var columnCount = individualCharacters.size() / lineCount;

        var columns = transpose(columnCount, lineCount, individualCharacters)
                              .stream()
                              .gather(Gatherers.windowFixed(lineCount))
                              .map(column -> String.join("", column))
                              .toList();

        return columns.stream()
                      .gather(splitByEmptyString())
                      .map(AoCLvl06::toCephaloProblem)
                      .mapToLong(Problem::solution)
                      .sum();
    }

    private static Gatherer<String, List<String>, List<String>> splitByEmptyString() {
        return Gatherer.ofSequential(ArrayList::new,
                                     (state, column, downstream) -> {
                                         if (column.isBlank()) {
                                             var newDownStreamItem = new ArrayList<>(state);
                                             downstream.push(newDownStreamItem);
                                             state.clear();
                                         } else {
                                             state.add(column);
                                         }
                                         return true;
                                     },
                                     (state, downstream) -> downstream.push(state)
        );
    }

    private static Problem toCephaloProblem(List<String> cephaloColumns) {
        var operands = cephaloColumns.stream()
                                     .map(c -> c.replaceAll("[ *+]", ""))
                                     .map(Long::parseLong)
                                     .toList();

        var operator = Util.split(cephaloColumns.getFirst(), "").getLast();

        return createProblem(operator, operands);
    }

    private static String rightPad(String string, int padTo, char padCharacter) {
        var originalSize = string.length();
        var padSize = Math.max(0, padTo - originalSize);
        return string + new String(new char[padSize]).replace('\0', padCharacter);
    }

    private static <T> List<T> transpose(int columns, int lines, List<T> tokens) {
        var result = new ArrayList<T>(tokens.size());
        for (int x = 0; x < columns; ++x) {
            for (int y = 0; y < lines; ++y) {
                result.add(tokens.get(y * columns + x));
            }
        }
        return result;
    }

    public static long solutionSum(List<String> lines) {
        var lineCount = lines.size();
        var tokens = lines.stream()
                          .map(content -> Util.split(content, "\\s+"))
                          .flatMap(List::stream)
                          .filter(Predicate.not(String::isBlank))
                          .toList();
        var columnCount = tokens.size() / lineCount;

        var columns = transpose(columnCount, lineCount, tokens)
                              .stream()
                              .gather(Gatherers.windowFixed(lineCount))
                              .toList();

        var problems = columns.stream()
                              .map(AoCLvl06::ofTokens)
                              .toList();

        return problems.stream()
                       .mapToLong(Problem::solution)
                       .sum();
    }

    private static Problem ofTokens(List<String> column) {
        var operator = column.getLast();

        var operands = column.subList(0, column.size() - 1)
                             .stream()
                             .map(Long::parseLong)
                             .toList();

        return createProblem(operator, operands);
    }

    private static Problem createProblem(String operator, List<Long> operands) {
        return switch (operator) {
            case "*" -> new ProductProblem(operands);
            case "+" -> new SumProblem(operands);
            default -> throw new UnsupportedOperationException("Operator %s unknown".formatted(operator));
        };
    }

}
