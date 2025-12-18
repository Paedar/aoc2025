package dev.paedar.aoc.util.gatherers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Gatherer;

public class CrossProductGatherer<E> implements Gatherer<E, List<E>, List<E>> {

    private final boolean twoWays;

    private final boolean includeSelfCombination;

    public static <E>CrossProductGatherer<E> fullCrossProduct() {
        return new CrossProductGatherer<>(true, true);
    }

    public static <E> CrossProductGatherer<E> noInverseProduct() {
        return new CrossProductGatherer<>(false, true);
    }

    public static <E> CrossProductGatherer<E> noSelfCrossing() {
        return new CrossProductGatherer<>(true, false);
    }

    public static <E> CrossProductGatherer<E> noInverseProductNoSelfCrossing() {
        return new CrossProductGatherer<>(false, false);
    }

    private CrossProductGatherer(boolean twoWays, boolean includeSelfCombination) {
        this.twoWays = twoWays;
        this.includeSelfCombination = includeSelfCombination;
    }

    @Override
    public Integrator<List<E>, E, List<E>> integrator() {
        return (state, element, downstream) -> {
            if (includeSelfCombination) {
                state.add(element);
            }

            state.forEach(se -> {
                downstream.push(List.of(se, element));
                if (twoWays) {
                    downstream.push(List.of(element, se));
                }
            });

            if (!includeSelfCombination) {
                state.add(element);
            }

            return true;
        };
    }

    @Override
    public Supplier<List<E>> initializer() {
        return ArrayList::new;
    }

}
