package dev.paedar.aoc.lvl05;

public record Range(long beginInclusive, long endInclusive) {

    public boolean contains(long value) {
        return value <= endInclusive && value >= beginInclusive;
    }
}
