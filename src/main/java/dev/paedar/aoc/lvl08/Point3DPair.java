package dev.paedar.aoc.lvl08;

record Point3DPair(Point3D first, Point3D second) {

    long distanceSquared() {
        return first.distanceSquared(second);
    }

}
