package dev.paedar.aoc.lvl06;

import java.util.Collection;

public record SumProblem(Collection<Long> operands) implements Problem {

    @Override
    public long solution() {
        return operands.stream()
                       .mapToLong(Long::longValue)
                       .sum();
    }

}
