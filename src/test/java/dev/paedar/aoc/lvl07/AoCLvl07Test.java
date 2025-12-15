package dev.paedar.aoc.lvl07;

import dev.paedar.aoc.util.GridInfo;
import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AoCLvl07Test {

    @Test
    void propagateTachyonBeams() {
        var lines = InputReader.readLines("07.example.txt");
        var originalGrid = GridInfo.of(lines);
        var expectedPropagatedGridString = """
                                           .......S.......
                                           .......|.......
                                           ......|^|......
                                           ......|.|......
                                           .....|^|^|.....
                                           .....|.|.|.....
                                           ....|^|^|^|....
                                           ....|.|.|.|....
                                           ...|^|^|||^|...
                                           ...|.|.|||.|...
                                           ..|^|^|||^|^|..
                                           ..|.|.|||.|.|..
                                           .|^|||^||.||^|.
                                           .|.|||.||.||.|.
                                           |^|^|^|^|^|||^|
                                           |.|.|.|.|.|||.|
                                           """;

        var propagatedGrid = AoCLvl07.propagateTachyonBeams(originalGrid);

        assertEquals(expectedPropagatedGridString.lines().toList(), propagatedGrid.lines());

    }

    @Test
    void countBeamsSplit() {
        var lines = InputReader.readLines("07.example.txt");
        var originalGrid = GridInfo.of(lines);

        var propagatedGrid = AoCLvl07.propagateTachyonBeams(originalGrid);

        assertEquals(21, AoCLvl07.countBeamsSplit(propagatedGrid));
    }

}