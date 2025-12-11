package dev.paedar.aoc.lvl01;

import dev.paedar.aoc.util.InputReader;

import java.util.List;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AoCLvl01 {

    private static final Logger LOGGER = Logger.getLogger(AoCLvl01.class.getName());

    static void main() {
        var lines = InputReader.readLines("01.input.txt");

        var instructions = lines.stream()
                                .map(AoCLvl01::toInstruction)
                                .toList();

        var password = determinePassword(instructions);
        LOGGER.log(Level.INFO, "Password: {0}", password);

        var tougherPassword = determinePasswordMethod0x434C49434B(instructions);
        LOGGER.log(Level.INFO, "Actual Password: {0}", tougherPassword);
    }

    public static Instruction toInstruction(String line) {
        var direction = line.charAt(0);
        var value = Integer.parseInt(line.substring(1));
        return switch (direction) {
            case 'L' -> new LeftTurn(value);
            case 'R' -> new RightTurn(value);
            default -> throw new IllegalArgumentException("Invalid direction: " + direction);
        };
    }

    public static int determinePassword(List<Instruction> instructions) {
        var decoded = determinePasswordUsingMethod(instructions, (decoder, instruction) -> {
            decoder.process(instruction);
            return decoder;
        });
        return decoded.getPassword();
    }

    public static int determinePasswordMethod0x434C49434B(List<Instruction> instructions) {
        var decoded = determinePasswordUsingMethod(instructions, (decoder, instruction) -> {
            decoder.processMethod0x434C49434B(instruction);
            return decoder;
        });
        return decoded.getPassword();
    }

    private static PasswordDecoder determinePasswordUsingMethod(List<Instruction> instructions, BiFunction<PasswordDecoder, Instruction, PasswordDecoder> passwordDecodingMethod) {
        return instructions.stream()
                           .reduce(new PasswordDecoder(),
                                   passwordDecodingMethod,
                                   (_, _) -> {
                                       throw new UnsupportedOperationException("Password decoding is order dependant and cannot be parallelized");
                                   });
    }

}
