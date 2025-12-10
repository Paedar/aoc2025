package dev.paedar.aoc.lvl01;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AoCLvl01Test {

    @Test
    void toInstruction() {
        var leftInstructionString = "L31";
        var rightInstructionString = "R2";

        var leftInstruction = AoCLvl01.toInstruction(leftInstructionString);
        var rightInstruction = AoCLvl01.toInstruction(rightInstructionString);

        assertEquals(new LeftTurn(31), leftInstruction);
        assertEquals(new RightTurn(2), rightInstruction);

        assertThrows(IllegalArgumentException.class, () -> AoCLvl01.toInstruction("G01"));
        assertThrows(NumberFormatException.class, () -> AoCLvl01.toInstruction("LRGEENNUMMER"));
    }

    @Test
    void determinePasswordExample() {
        var lines = InputReader.readLines("01.example.txt");
        var instructions = lines.stream().map(AoCLvl01::toInstruction).toList();
        var password = AoCLvl01.determinePassword(instructions);
        assertEquals(3, password);
    }

    @Test
    void determinePasswordMethod0x434C49434BExample() {
        var lines = InputReader.readLines("01.example.txt");
        var instructions = lines.stream().map(AoCLvl01::toInstruction).toList();
        var password = AoCLvl01.determinePasswordMethod0x434C49434B(instructions);
        assertEquals(6, password);
    }

}