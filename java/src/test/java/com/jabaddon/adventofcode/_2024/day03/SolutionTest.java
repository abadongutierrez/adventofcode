package com.jabaddon.adventofcode._2024.day03;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
    @Test
    void test1() {
        var solution = new Solution();
        assertEquals(solution.captureAndSumMulInstructions(
                List.of("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")), 161);
    }

    @Test
    void test2() {
        var solution = new Solution();
        assertEquals(solution.captureAndSumMulInstructionsWhenEnabled(
                List.of("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")), 48);
    }
}