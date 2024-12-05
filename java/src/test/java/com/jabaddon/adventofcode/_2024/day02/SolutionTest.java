package com.jabaddon.adventofcode._2024.day02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    void test() {
        var solution = new Solution();

        assertTrue(solution.isSafeLevelWithTolerance("1 3 2 4 5"));
        assertTrue(solution.isSafeLevelWithTolerance("8 6 4 4 1"));
    }
}