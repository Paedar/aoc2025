package dev.paedar.aoc.lvl09;

import dev.paedar.aoc.util.Position;

record PositionPair(Position first, Position second) {

    long squareSize() {
        return (Math.abs(first.x() - second.x()) + 1L) * (Math.abs(first.y() - second.y()) + 1L);
    }

}
