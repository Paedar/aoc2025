package dev.paedar.aoc.lvl03;

import dev.paedar.aoc.util.InputReader;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class AoCLvl03 {

    private static final Logger LOGGER = Logger.getLogger(AoCLvl03.class.getName());

    public static void main(String[] args) {
        var banks = InputReader.readLines("03.input.txt");

        var sumOfIdealJoltages = sumOfIdealJoltages(banks, 2);
        LOGGER.log(Level.INFO, "Sum of ideal joltages: {0}", sumOfIdealJoltages);
        var sumOfIdealHighJoltages = sumOfIdealJoltages(banks, 12);
        LOGGER.log(Level.INFO, "Sum of ideal high joltages: {0}", sumOfIdealHighJoltages);

    }

    public static long sumOfIdealJoltages(List<String> banks, int batteriesUsed) {
        return banks.stream()
                    .mapToLong(bank -> determineJoltage(bank, batteriesUsed))
                    .sum();
    }

    public static long determineJoltage(String bank, int batteriesUsed) {
        if (bank.length() < batteriesUsed) {
            throw new IllegalArgumentException("Bank must have at least as many batteries as used");
        }
        var digits = Stream.of(bank.split(""))
                           .map(Integer::parseInt)
                           .toList();
        var firstDigit = digits.subList(0, digits.size() - batteriesUsed + 1)
                               .stream()
                               .mapToInt(Integer::intValue)
                               .max()
                               .orElseThrow();

        if (1 == batteriesUsed) {
            return firstDigit;
        }

        var firstDigitFirstPosition = digits.indexOf(firstDigit);

        var remainingBank = bank.substring(firstDigitFirstPosition + 1);
        var remainder = determineJoltage(remainingBank, batteriesUsed - 1);

        return Math.powExact(10L, batteriesUsed - 1) * firstDigit + remainder;
    }

}
