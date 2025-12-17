package dev.paedar.aoc.lvl08;

import dev.paedar.aoc.util.InputReader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
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
        var tokens = InputReader.readTokens("08.input.txt");

        var biggest3CircuitSizeProduct = productSizeOfBiggest3Circuits(tokens, 1000);
        LOGGER.log(Level.INFO, "Product of size of 3 biggest circuits: {0}", biggest3CircuitSizeProduct);

        var wallDistance = wallDistanceProductAfterMergingAllCircuits(tokens);
        LOGGER.log(Level.INFO, "Product of x coordinate after merging down to single circuit: {0}", wallDistance);
    }

    public static long productSizeOfBiggest3Circuits(List<String> tokens, int numConnections) {
        var junctionBoxes = toPoints(tokens);
        var circuitRegistration = junctionBoxes.stream()
                                               .collect(Collectors.toMap(Function.identity(), Point3D::hashCode));
        var distancePrecalculation = getPrecalculatedDistances(junctionBoxes);

        for (int i = 0; i < numConnections; ++i) {
            var precalculated = distancePrecalculation.removeFirst();
            var shortestDistancePair = precalculated.pair();

            var circuitNumberFirst = circuitRegistration.get(shortestDistancePair.first());
            var circuitNumberSecond = circuitRegistration.get(shortestDistancePair.second());

            mergeCircuits(circuitNumberFirst, circuitNumberSecond, circuitRegistration);
        }

        // Calculate size of the circuits:
        return circuitRegistration.entrySet()
                                  .stream()
                                  .collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.counting()))
                                  .values().stream()
                                  .sorted(Comparator.comparingLong(Long::longValue).reversed())
                                  .limit(3)
                                  .reduce(1L, (aLong, aLong2) -> aLong * aLong2);
    }

    public static long wallDistanceProductAfterMergingAllCircuits(List<String> tokens) {
        var junctionBoxes = toPoints(tokens);
        var circuitRegistration = junctionBoxes.stream()
                                               .collect(Collectors.toMap(Function.identity(), Point3D::hashCode));
        var existingCircuits = new HashSet<>(circuitRegistration.values());
        var distancePrecalculation = getPrecalculatedDistances(junctionBoxes);
        Point3DPair lastMergedPair = null;

        while (existingCircuits.size() > 1) {
            var precalculated = distancePrecalculation.removeFirst();
            lastMergedPair = precalculated.pair();

            var circuitNumberFirst = circuitRegistration.get(lastMergedPair.first());
            var circuitNumberSecond = circuitRegistration.get(lastMergedPair.second());

            var merged = mergeCircuits(circuitNumberFirst, circuitNumberSecond, circuitRegistration);
            if (merged) {
                existingCircuits.remove(circuitNumberSecond);
            }
        }

        Objects.requireNonNull(lastMergedPair);

        return lastMergedPair.first().x() * lastMergedPair.second().x();
    }

    private static List<PrecalculatedDistance> getPrecalculatedDistances(List<Point3D> junctionBoxes) {
        return getPairCrossProduct(junctionBoxes)
                       .stream()
                       .filter(pair -> !pair.first().equals(pair.second()))
                       .map(p -> new PrecalculatedDistance(p, p.distanceSquared()))
                       .sorted(Comparator.comparingLong(PrecalculatedDistance::distanceSquared))
                       .collect(Collectors.toList()); // Do not replace with Stream.toList(), modifiability is desired here
    }

    private static boolean mergeCircuits(Integer circuitNumberFirst, Integer circuitNumberSecond, Map<Point3D, Integer> circuitRegistration) {
        if (!Objects.equals(circuitNumberFirst, circuitNumberSecond)) {
            // Do update circuit registration
            var affectedSecondCircuitJunctionBoxes = circuitRegistration.entrySet().stream()
                                                                        .filter(e -> Objects.equals(circuitNumberSecond, e.getValue()))
                                                                        .map(Map.Entry::getKey)
                                                                        .toList();
            affectedSecondCircuitJunctionBoxes.forEach(affectedJunctionBox -> circuitRegistration.put(affectedJunctionBox, circuitNumberFirst));
            return true;
        }
        return false;
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
