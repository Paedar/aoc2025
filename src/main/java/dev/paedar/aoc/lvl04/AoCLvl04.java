package dev.paedar.aoc.lvl04;

import dev.paedar.aoc.util.GridInfo;
import dev.paedar.aoc.util.InputReader;
import dev.paedar.aoc.util.Position;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AoCLvl04 {

    private static final Logger LOGGER = Logger.getLogger(AoCLvl04.class.getName());

    public static final char PAPER_STACK = '@';

    public static final int MAX_SURROUNDING_PAPER_STACKS = 3;

    public static void main(String[] args) {
        var lines = InputReader.readLines("04.input.txt");

        var grid = GridInfo.of(lines);
        var accessibleStacks = countAccessibleStacks(grid);
        LOGGER.log(Level.INFO, "Accessible Stacks: {0}", accessibleStacks);
    }

    public static boolean isAccessibleStack(GridInfo grid, Position position) {
        if (!isPaperStack(grid, position)) {
            return false;
        }

        var surroundingPaperStacks = position.getD8Neighbours()
                                             .filter(grid::inbounds)
                                             .filter(p -> isPaperStack(grid, p))
                                             .count();
        return MAX_SURROUNDING_PAPER_STACKS >= surroundingPaperStacks;
    }

    private static boolean isPaperStack(GridInfo grid, Position position) {
        return PAPER_STACK == grid.charAt(position);
    }

    public static long countAccessibleStacks(GridInfo grid) {
        return grid.allInboundsPositions()
                   .filter(position -> isAccessibleStack(grid, position))
                   .count();
    }

}
