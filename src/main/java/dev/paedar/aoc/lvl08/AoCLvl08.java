package dev.paedar.aoc.lvl08;

import dev.paedar.aoc.util.InputReader;

import java.util.ArrayList;
import java.util.Collection;
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
        var distancePrecalculation = getPairCrossProduct(junctionBoxes)
                                             .stream()
                                             .filter(pair -> !pair.first().equals(pair.second()))
                                             .map(p -> new PrecalculatedDistance(p, p.distanceSquared()))
                                             .sorted(Comparator.comparingLong(PrecalculatedDistance::distanceSquared))
                                             .collect(Collectors.toList()); // Do not replace with Stream.toList(), modifiability is desired here

        for (int i = 0; i < numConnections; ++i) {
            var precalculated = distancePrecalculation.removeFirst();
            var shortestDistancePair = precalculated.pair();

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

    private static Collection<Point3DPair> getPairCrossProduct(List<Point3D> junctionBoxes) {
        var numJunctionBoxes = junctionBoxes.size();
        var result = new ArrayList<Point3DPair>(numJunctionBoxes * numJunctionBoxes); // upper bound
        for (int i = 0; i < numJunctionBoxes; ++i) {
            for (int j = i; j < numJunctionBoxes; ++j) {
                result.add(new Point3DPair(junctionBoxes.get(i), junctionBoxes.get(j)));
            }
        }
        return result;
    }

    private static List<Point3D> toPoints(List<String> tokens) {
        return tokens.stream()
                     .map(Long::parseLong)
                     .gather(Gatherers.windowFixed(3))
                     .map(Point3D::ofCoordinates)
                     .toList();
    }

}
