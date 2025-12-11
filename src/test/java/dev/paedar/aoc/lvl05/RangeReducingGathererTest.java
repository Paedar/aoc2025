package dev.paedar.aoc.lvl05;

import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Gatherer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RangeReducingGathererTest {

    private static final Set<Range> IRREDUCIBLE_RANGES = Set.of(new Range(3, 5), new Range(8, 12));

    private static final Set<Range> PARTLY_REDUCIBLE_RANGES = Set.of(new Range(3, 5), new Range(8, 12), new Range(4, 6));

    private static final Set<Range> FULLY_REDUCIBLE_RANGES = Set.of(new Range(3, 5), new Range(8, 12), new Range(6, 13));

    private static final RangeReducingGatherer GATHERER = new RangeReducingGatherer();

    @Test
    void testIrreducible() {
        var result = IRREDUCIBLE_RANGES.stream()
                                       .gather(GATHERER)
                                       .collect(Collectors.toSet());

        assertEquals(IRREDUCIBLE_RANGES, result);
    }

    @Test
    void testPartlyReducible() {
        var result = PARTLY_REDUCIBLE_RANGES.stream()
                                            .gather(GATHERER)
                                            .collect(Collectors.toSet());

        assertEquals(Set.of(new Range(3, 6), new Range(8, 12)), result);
    }

    @Test
    void fullyReducible() {
        var result = FULLY_REDUCIBLE_RANGES.stream()
                                           .gather(GATHERER)
                                           .collect(Collectors.toSet());

        assertEquals(Set.of(new Range(3, 13)), result);
    }

    @Test
    void testIntegrator() {
        var state = new HashSet<Range>();
        var keepDownstreamOpen = GATHERER.integrator().integrate(state, new Range(0, 2), dummyDownstream());

        assertTrue(keepDownstreamOpen);
        assertEquals(Set.of(new Range(0, 2)), state);

        keepDownstreamOpen = GATHERER.integrator().integrate(state, new Range(4, 6), dummyDownstream());
        assertTrue(keepDownstreamOpen);
        assertEquals(Set.of(new Range(0, 2), new Range(4, 6)), state);

        keepDownstreamOpen = GATHERER.integrator().integrate(state, new Range(1, 2), dummyDownstream());
        assertTrue(keepDownstreamOpen);
        assertEquals(Set.of(new Range(0, 2), new Range(4, 6)), state);

        keepDownstreamOpen = GATHERER.integrator().integrate(state, new Range(1, 3), dummyDownstream());
        assertTrue(keepDownstreamOpen);
        assertEquals(Set.of(new Range(0, 6)), state);
    }

    private static Gatherer.@NonNull Downstream<Range> dummyDownstream() {
        return _ -> true;
    }

}