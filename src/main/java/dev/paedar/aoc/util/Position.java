package dev.paedar.aoc.util;

import java.util.stream.Stream;

public record Position(int x, int y) {

    Position northWest() {
        return new Position(x - 1, y - 1);
    }

    Position north() {
        return new Position(x, y - 1);
    }

    Position northEast() {
        return new Position(x + 1, y - 1);
    }

    Position west() {
        return new Position(x - 1, y);
    }

    Position east() {
        return new Position(x + 1, y);
    }

    Position southWest() {
        return new Position(x - 1, y + 1);
    }

    Position south() {
        return new Position(x, y + 1);
    }

    Position southEast() {
        return new Position(x + 1, y + 1);
    }

    public Position plus(int dx, int dy) {
        return new Position(x + dx, y + dy);
    }

    public Position minus(int dx, int dy) {
        return plus(-dx, -dy);
    }

    public boolean isD4Neighbour(Position other) {
        return getD4Neighbours()
                       .anyMatch(other::equals);
    }

    public boolean isD8Neighbour(Position other) {
        return getD8Neighbours()
                       .anyMatch(other::equals);
    }

    public Stream<Position> getD4Neighbours() {
        return Direction.cardinalDirections()
                        .map(direction -> direction.next(this));
    }

    public Stream<Position> getD8Neighbours() {
        return Stream.of(Direction.values())
                       .map(direction -> direction.next(this));
    }

    public int manhattanDistance(Position other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }

}
