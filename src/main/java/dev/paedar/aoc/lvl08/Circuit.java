//package dev.paedar.aoc.lvl08;
//
//import java.util.LinkedHashSet;
//import java.util.Objects;
//import java.util.SequencedSet;
//
//public class Circuit {
//
//    private final SequencedSet<Point3D> includedJunctionBoxes;
//
//    private final long identifier;
//
//    private static long autoIncrementNextId = 0L;
//
//    public static Circuit ofJunctionBox(Point3D junctionBox) {
//        var circuit = new Circuit();
//        circuit.includedJunctionBoxes.add(junctionBox);
//        return circuit;
//    }
//
//    private static long nextIdentifier() {
//        var identifier = autoIncrementNextId;
//        ++autoIncrementNextId;
//        return identifier;
//    }
//
//    private Circuit() {
//        includedJunctionBoxes = new LinkedHashSet<>();
//        identifier = nextIdentifier();
//    }
//
//    public Circuit
//
//    @Override
//    public boolean equals(Object o) {
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        Circuit circuit = (Circuit) o;
//        return identifier == circuit.identifier;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hashCode(identifier);
//    }
//
//}
