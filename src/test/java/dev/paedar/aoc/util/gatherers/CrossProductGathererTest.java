package dev.paedar.aoc.util.gatherers;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CrossProductGathererTest {

    private static final List<String> TEST_STRINGS = List.of("A", "B", "C");

    @Test
    void fullCrossProduct() {
        var crossed = TEST_STRINGS.stream().gather(CrossProductGatherer.fullCrossProduct())
                                  .map(l -> String.join("", l))
                                  .toList();

        var expected = List.of("AA", "AA", "AB", "AC", "BA", "BB", "BB", "BC", "CA", "CB", "CC", "CC");
        assertEquals(expected.size(), crossed.size());
        assertTrue(crossed.containsAll(expected));
        assertTrue(expected.containsAll(crossed));
    }

    @Test
    void noInverseProduct() {
        var crossed = TEST_STRINGS.stream().gather(CrossProductGatherer.noInverseProduct())
                                  .map(l -> String.join("", l))
                                  .toList();

        var expected = List.of("AA", "AB", "AC", "BB", "BC", "CC");
        assertEquals(expected.size(), crossed.size());
        assertTrue(crossed.containsAll(expected));
        assertTrue(expected.containsAll(crossed));
    }

    @Test
    void noSelfCrossing() {
        var crossed = TEST_STRINGS.stream().gather(CrossProductGatherer.noSelfCrossing())
                                  .map(l -> String.join("", l))
                                  .toList();

        var expected = List.of("AB", "AC", "BA", "BC", "CA", "CB");
        assertEquals(expected.size(), crossed.size());
        assertTrue(crossed.containsAll(expected));
        assertTrue(expected.containsAll(crossed));
    }

    @Test
    void noInverseProductNoSelfCrossing() {
        var crossed = TEST_STRINGS.stream().gather(CrossProductGatherer.noInverseProductNoSelfCrossing())
                                  .map(l -> String.join("", l))
                                  .toList();

        var expected = List.of("AB", "AC", "BC");
        assertEquals(expected.size(), crossed.size());
        assertTrue(crossed.containsAll(expected));
        assertTrue(expected.containsAll(crossed));
    }

}