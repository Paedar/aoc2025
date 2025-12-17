package dev.paedar.aoc.lvl08;

import java.util.List;

public record Point3D(long x, long y, long z) {

    static Point3D ofCoordinates(List<Long> coordinates) {
        if (coordinates.size() != 3) {
            throw new IllegalArgumentException("3 coordinates expected");
        }
        return new Point3D(coordinates.get(0), coordinates.get(1), coordinates.get(2));
    }

    long distanceSquared(Point3D other) {
        var diffX = this.x - other.x;
        var diffY = this.y - other.y;
        var diffZ = this.z - other.z;

        return diffX * diffX + diffY * diffY + diffZ * diffZ;
    }

}
