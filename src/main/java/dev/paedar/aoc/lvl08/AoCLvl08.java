package dev.paedar.aoc.lvl08;

import dev.paedar.aoc.util.InputReader;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Gatherers;

public class AoCLvl08 {

    private static final Logger LOGGER = Logger.getLogger(AoCLvl08.class.getName());

    static void main() {
        var tokens = InputReader.readTokens("07.input.txt");

        var biggest3CircuitSizeProduct = productSizeOfBiggest3Circuits(tokens, 1000);
        LOGGER.log(Level.INFO, "Product of size of 3 biggest circuits: {0}", biggest3CircuitSizeProduct);
    }

    public static long productSizeOfBiggest3Circuits(List<String> tokens, int numConnections) {
        var junctionBoxes = toPoints(tokens);
        var circuitRegistration = junctionBoxes.stream()
                                               .collect(Collectors.toMap(Function.identity(), Point3D::hashCode));
        var distancePrecalculation = junctionBoxes.stream()
                                                  .flatMap(jb1 -> junctionBoxes.stream()
                                                                               .map(jb2 -> new Pair(jb1, jb2))
                                                  )
                                                  .filter(pair -> !pair.first().equals(pair.second()))
                                                  .collect(Collectors.toMap(Function.identity(), Pair::distanceSquared));

        for (int i = 0; i < numConnections; ++i) {
            var shortestDistancePair = distancePrecalculation.keySet()
                                                             .stream()
                                                             .min(Comparator.comparingLong(Pair::distanceSquared))
                                                             .orElseThrow(() -> new IllegalStateException("No more pairs remaining"));

            var circuitNumberFirst = circuitRegistration.get(shortestDistancePair.first());
            var circuitNumberSecond = circuitRegistration.get(shortestDistancePair.second());

            if (!Objects.equals(circuitNumberFirst, circuitNumberSecond)) {
                // Do update circuit registration
                var affectedSecondCircuitJunctionBoxes = circuitRegistration.entrySet().stream()
                                                                            .filter(e -> circuitNumberSecond == e.getValue())
                                                                            .map(Map.Entry::getKey)
                                                                            .toList();
                affectedSecondCircuitJunctionBoxes.forEach(affectedJunctionBox -> circuitRegistration.put(affectedJunctionBox, circuitNumberFirst));
            }

            // Remove both boxes from pool of unconnected distance precalculation
            distancePrecalculation.remove(shortestDistancePair);
            distancePrecalculation.remove(shortestDistancePair.reverse());
        }

        // Calculate size of the circuits:
        var circuitSizes = circuitRegistration.entrySet()
                                              .stream()
                                              .collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.counting()));
        return circuitSizes.values().stream()
                           .sorted(Comparator.comparingLong(Long::longValue).reversed())
                           .limit(3)
                           .reduce(1L, (aLong, aLong2) -> aLong * aLong2);
    }

    private static List<Point3D> toPoints(List<String> tokens) {
        return tokens.stream()
                     .map(Long::parseLong)
                     .gather(Gatherers.windowFixed(3))
                     .map(Point3D::ofCoordinates)
                     .toList();
    }

    private static record Pair(Point3D first, Point3D second) {

        long distanceSquared() {
            return first.distanceSquared(second);
        }

        Pair reverse() {
            return new Pair(second, first);
        }

    }

}
