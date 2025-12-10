package dev.paedar.aoc.lvl03;

import dev.paedar.aoc.lvl02.AoCLvl02;
import dev.paedar.aoc.util.InputReader;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class AoCLvl03 {

    private static final Logger LOGGER = Logger.getLogger(AoCLvl02.class.getName());

    public static void main(String[] args) {
        var banks = InputReader.readLines("03.input.txt");

        var sumOfIdealJoltages = sumOfIdealJoltages(banks);
        LOGGER.log(Level.INFO, "Sum of ideal joltages: {0}", sumOfIdealJoltages);

    }

    public static long sumOfIdealJoltages(List<String> banks) {
        return banks.stream()
                    .mapToLong(AoCLvl03::determineJoltage)
                    .sum();
    }

    public static long determineJoltage(String bank) {
        if(bank.length() < 2) {
            throw new IllegalArgumentException("Bank must be at least 2 characters");
        }
        var digits = Stream.of(bank.split(""))
                           .map(Integer::parseInt)
                           .toList();
        var firstDigit = digits.subList(0, digits.size() - 1)
                               .stream()
                               .mapToInt(Integer::intValue)
                               .max()
                               .getAsInt();
        var firstDigitFirstPosition = digits.indexOf(firstDigit);

        var secondDigit = digits.subList(firstDigitFirstPosition + 1, digits.size())
                                .stream()
                                .mapToInt(Integer::intValue)
                                .max()
                                .getAsInt();

        return 10 * firstDigit + secondDigit;
    }

}
