package dev.paedar.aoc.lvl05;

public record Range(long beginInclusive, long endInclusive) {

    public Range {
        if (endInclusive < beginInclusive) {
            throw new IllegalArgumentException("End (%d) should not be smaller than start (%d)".formatted(endInclusive, beginInclusive));
        }
    }

    public boolean contains(long value) {
        return value <= endInclusive && value >= beginInclusive;
    }

    public boolean canCombine(Range other) {
        return this.contains(other.beginInclusive) || this.contains(other.endInclusive) || // Either end of the 'other' range is included in 'this'
                       other.contains(this.beginInclusive) || other.contains(this.endInclusive) || // Either end of the 'this' range is included in 'other'
                       this.endInclusive == other.beginInclusive - 1 || // 'other' is immediately consecutive to 'this'
                       other.endInclusive == this.beginInclusive - 1; // 'this' is immediately consecutive to 'other'
    }

    public Range combine(Range other) {
        if (!canCombine(other)) {
            throw new IllegalArgumentException("Ranges are not combinable");
        }

        var newBegin = Math.min(this.beginInclusive, other.beginInclusive);
        var newEnd = Math.max(this.endInclusive, other.endInclusive);
        return new Range(newBegin, newEnd);
    }

    public long elementCount() {
        return endInclusive - beginInclusive + 1;
    }

}
