package dev.paedar.aoc.lvl04;

import dev.paedar.aoc.util.GridInfo;
import dev.paedar.aoc.util.InputReader;
import dev.paedar.aoc.util.Position;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class AoCLvl04 {

    private static final Logger LOGGER = Logger.getLogger(AoCLvl04.class.getName());

    public static final char PAPER_STACK = '@';

    public static final char OPEN_SPACE = '.';

    public static final int MAX_SURROUNDING_PAPER_STACKS = 3;

    public static void main(String[] args) {
        var lines = InputReader.readLines("04.input.txt");

        var grid = GridInfo.of(lines);
        var accessibleStacks = countAccessibleStacks(grid);
        LOGGER.log(Level.INFO, "Accessible Stacks: {0}", accessibleStacks);

        var totalRemovableStacks = removeAccessibleStackUntilAllInaccessible(grid);
        LOGGER.log(Level.INFO, "Total removable Stacks: {0}", totalRemovableStacks);
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
        return accessiblePaperStacks(grid)
                   .count();
    }

    private static Stream<Position> accessiblePaperStacks(GridInfo grid) {
        return grid.allInboundsPositions()
                   .filter(position -> isAccessibleStack(grid, position));
    }

    public static long removeAccessibleStacks(GridInfo grid) {
        var accessiblePaperStacks = accessiblePaperStacks(grid)
                                            .toList();
        var removedCount = accessiblePaperStacks.size();
        accessiblePaperStacks.forEach(p -> grid.putAt(p, OPEN_SPACE));
        return removedCount;
    }

    public static long removeAccessibleStackUntilAllInaccessible(GridInfo grid) {
        var totalCount = 0L;
        long removedCount;
        do {
            removedCount = removeAccessibleStacks(grid);
            totalCount += removedCount;
        } while (removedCount > 0);
        return totalCount;
    }

}
