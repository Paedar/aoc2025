package dev.paedar.aoc.lvl05;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Gatherer;

class RangeReducingGatherer implements Gatherer<Range, Set<Range>, Range> {

    @Override
    public Integrator<Set<Range>, Range, Range> integrator() {
        return (state, range, _) -> {
            addToStateAndReduce(state, range);
            return true;
        };
    }

    private static void addToStateAndReduce(Set<Range> state, Range range) {
        var combinable = state.stream()
                              .filter(r -> r.canCombine(range))
                              .toList();
        combinable.forEach(state::remove);
        var reduced = combinable.stream()
                                .reduce(range, Range::combine);
        state.add(reduced);
    }

    @Override
    public Supplier<Set<Range>> initializer() {
        return HashSet::new;
    }

    @Override
    public BinaryOperator<Set<Range>> combiner() {
        return (first, second) -> {
            second.forEach(e -> addToStateAndReduce(first, e));
            return first;
        };
    }

    @Override
    public BiConsumer<Set<Range>, Downstream<? super Range>> finisher() {
        return (state, downstream) -> state.forEach(downstream::push);
    }

}
