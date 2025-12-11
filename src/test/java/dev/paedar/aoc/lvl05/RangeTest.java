package dev.paedar.aoc.lvl05;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RangeTest {

    private static final Range RANGE_1 = new Range(3, 5);

    private static final Range RANGE_2 = new Range(9, 12);

    private static final Range RANGE_3 = new Range(1, 4);

    private static final Range RANGE_4 = new Range(4, 7);

    private static final Range RANGE_5 = new Range(1, 7);

    @Test
    void contains() {
        assertFalse(RANGE_1.contains(2));
        assertTrue(RANGE_1.contains(3));
        assertTrue(RANGE_1.contains(4));
        assertTrue(RANGE_1.contains(5));
        assertFalse(RANGE_1.contains(6));

        assertFalse(RANGE_2.contains(2));
        assertFalse(RANGE_2.contains(3));
        assertFalse(RANGE_2.contains(4));
        assertFalse(RANGE_2.contains(5));
        assertFalse(RANGE_2.contains(6));
    }

    @Test
    void canCombine() {
        assertFalse(RANGE_1.canCombine(RANGE_2));
        assertFalse(RANGE_2.canCombine(RANGE_1));
        assertTrue(RANGE_1.canCombine(RANGE_3));
        assertTrue(RANGE_1.canCombine(RANGE_4));
        assertTrue(RANGE_1.canCombine(RANGE_5));
        assertTrue(RANGE_3.canCombine(RANGE_1));
        assertTrue(RANGE_4.canCombine(RANGE_1));
        assertTrue(RANGE_5.canCombine(RANGE_1));
    }

    @Test
    void combine() {
        assertEquals(new Range(1, 5), RANGE_1.combine(RANGE_3));
        assertEquals(new Range(1, 5), RANGE_3.combine(RANGE_1));
        assertEquals(new Range(3, 7), RANGE_1.combine(RANGE_4));
        assertEquals(new Range(3, 7), RANGE_4.combine(RANGE_1));
        assertEquals(new Range(1, 7), RANGE_1.combine(RANGE_5));
        assertEquals(new Range(1, 7), RANGE_5.combine(RANGE_1));
    }

    @Test
    void elementCount() {
        assertEquals(3, RANGE_1.elementCount());
        assertEquals(7, RANGE_5.elementCount());
    }

}