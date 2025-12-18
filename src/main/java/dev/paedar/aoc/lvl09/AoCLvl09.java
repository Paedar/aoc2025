package dev.paedar.aoc.lvl09;

import dev.paedar.aoc.util.InputReader;
import dev.paedar.aoc.util.Position;
import dev.paedar.aoc.util.gatherers.CrossProductGatherer;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

public class AoCLvl09 {

    private static final Logger LOGGER = Logger.getLogger(AoCLvl09.class.getName());

    static void main() {
        var tokens = InputReader.readTokens("09.input.txt");

        var biggestSquareSize = biggestSquareOfTiling(tokens);
        LOGGER.log(Level.INFO, "Size of biggest square: {0}", biggestSquareSize);
    }

    public static long biggestSquareOfTiling(List<String> tokens) {
        var tiles = toPosition(tokens);
        var distancePrecalculation = calculateSquareSizes(tiles);
        return distancePrecalculation.getLast().squareSize();
    }

    private static List<PrecalculatedSquareSize> calculateSquareSizes(List<Position> tiles) {
        return getPairCrossProduct(tiles)
                       .map(p -> new PrecalculatedSquareSize(p, p.squareSize()))
                       .sorted(Comparator.comparingLong(PrecalculatedSquareSize::squareSize))
                       .collect(Collectors.toList()); // Do not replace with Stream.toList(), modifiability is desired here
    }

    private static Stream<PositionPair> getPairCrossProduct(List<Position> tiles) {
        return tiles.stream()
                    .gather(CrossProductGatherer.noInverseProductNoSelfCrossing())
                    .map(l -> new PositionPair(l.getFirst(), l.getLast()));
    }

    private static List<Position> toPosition(List<String> tokens) {
        return tokens.stream()
                     .map(Integer::parseInt)
                     .gather(Gatherers.windowFixed(2))
                     .map(l -> new Position(l.get(0), l.get(1)))
                     .toList();
    }

}
