package dev.paedar.aoc.lvl08;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {

    @Test
    void distanceSquared() {
        var firstPoint = Point3D.ofCoordinates(List.of(3L, 4L, 5L));
        var secondPoint = Point3D.ofCoordinates(List.of(1L, 4L, 9L));

        var expectedValue = 20L;
        assertEquals(expectedValue, firstPoint.distanceSquared(secondPoint));
        assertEquals(expectedValue, secondPoint.distanceSquared(firstPoint));
        assertEquals(0L, firstPoint.distanceSquared(firstPoint));
    }

}