package dev.paedar.aoc.lvl07;

import dev.paedar.aoc.util.Direction;
import dev.paedar.aoc.util.GridInfo;
import dev.paedar.aoc.util.InputReader;
import dev.paedar.aoc.util.Position;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AoCLvl07 {

    private static final Logger LOGGER = Logger.getLogger(AoCLvl07.class.getName());

    public static final char START_POSITION_CHAR = 'S';

    public static final char EMPTY_CHAR = '.';

    public static final char BEAM_CHAR = '|';

    public static final char BEAM_SPLITTER_CHAR = '^';

    static void main() {
        var lines = InputReader.readLines("07.input.txt");

        var grid = GridInfo.of(lines);
        var beamPropagatedGrid = propagateTachyonBeams(grid);
        var numSplitBeams = countBeamsSplit(beamPropagatedGrid);
        LOGGER.log(Level.INFO, "Number of beams splitted: {0}", numSplitBeams);

        var numTimelines = timelineCount(beamPropagatedGrid);
        LOGGER.log(Level.INFO, "Number of possible timelines: {0}", numTimelines);
    }

    public static long timelineCount(GridInfo grid) {
        var memoization = new HashMap<Position, Long>();
        return grid.allInboundsPositions()
                   .filter(p -> p.y() == grid.height() - 1)
                   .filter(p -> grid.charAt(p) == BEAM_CHAR)
                   .mapToLong(p -> timelineCount(grid, p, memoization))
                   .sum();
    }

    public static long timelineCount(GridInfo grid, Position at, Map<Position, Long> memoization) {
        if(!grid.inbounds(at)) {
            return 0;
        }

        if (memoization.containsKey(at)) {
            return memoization.get(at);
        }

        var c = grid.charAt(at);
        long count = switch (c) {
            case EMPTY_CHAR -> 0L;
            case START_POSITION_CHAR -> 1L;
            case BEAM_CHAR -> {
                var aboveLeftPosition = Direction.NORTH_WEST.next(at);
                var aboveRightPosition = Direction.NORTH_EAST.next(at);
                var abovePosition = Direction.NORTH.next(at);

                var runningCount = timelineCount(grid, abovePosition, memoization);

                if(grid.inbounds(aboveLeftPosition) && BEAM_SPLITTER_CHAR == grid.charAt(aboveLeftPosition)) {
                    runningCount += timelineCount(grid, aboveLeftPosition, memoization);
                }
                if(grid.inbounds(aboveRightPosition) && BEAM_SPLITTER_CHAR == grid.charAt(aboveRightPosition)) {
                    runningCount += timelineCount(grid, aboveRightPosition, memoization);
                }

                yield runningCount;
            }
            case BEAM_SPLITTER_CHAR -> timelineCount(grid, Direction.NORTH.next(at), memoization);
            default -> throw new IllegalStateException("Illegal character found on map");
        };
        memoization.put(at, count);
        return count;
    }

    public static long countBeamsSplit(GridInfo grid) {
        return grid.allInboundsPositions()
                   .filter(p -> BEAM_SPLITTER_CHAR == grid.charAt(p)) // finds all splitters
                   .map(Direction.NORTH::next) // Map to point above
                   .filter(p -> BEAM_CHAR == grid.charAt(p)) // Checks if splitter has an incoming beam
                   .count();
    }

    public static GridInfo propagateTachyonBeams(GridInfo grid) {
        var propagated = GridInfo.of(grid.lines());

        var startPositions = propagated.allInboundsPositions()
                                       .filter(p -> START_POSITION_CHAR == propagated.charAt(p))
                                       .toList();
        if (startPositions.size() != 1) {
            throw new IllegalArgumentException("Unexpected amount of start positions found: %d".formatted(startPositions.size()));
        }

        var startPosition = startPositions.getFirst();
        var beamIndices = new HashSet<Integer>();
        beamIndices.add(startPosition.x());

        for (int i = startPosition.y() + 1; i < propagated.height(); ++i) {
            var oldIndices = new HashSet<>(beamIndices);
            final int y = i;
            oldIndices.forEach(x -> {
                var position = new Position(x, y);
                var c = propagated.charAt(position);
                switch (c) {
                    case START_POSITION_CHAR -> throw new IllegalStateException("Extra start position encountered.");
                    case EMPTY_CHAR -> propagated.putAt(position, BEAM_CHAR);
                    case BEAM_SPLITTER_CHAR -> {
                        beamIndices.remove(x);
                        var west = Direction.WEST.next(position);
                        var east = Direction.EAST.next(position);
                        beamIndices.add(west.x());
                        beamIndices.add(east.x());
                        propagated.putAt(west, BEAM_CHAR);
                        propagated.putAt(east, BEAM_CHAR);
                    }
                    case BEAM_CHAR -> {/*Do Nothing, propagation has reached here already*/}
                    default -> throw new IllegalStateException("Illegal character found on map");
                }
            });
        }

        return propagated;
    }

}
