package com.jabaddon.adventofcode._2024.day04;

import com.jabaddon.adventofcode.InputReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    void countXmasHorizontally() {
        var solution = new Solution();
        assertEquals(solution.countXmasHorizontally(List.of("XMAS"), 0, 0), 1);
        assertEquals(solution.countXmasHorizontally(List.of("MMXMASS"), 0, 2), 1);
        assertEquals(solution.countXmasHorizontally(List.of("MMXMAMMS"), 0, 2), 0);
        assertEquals(solution.countXmasHorizontally(List.of("MMXMA"), 0, 2), 0);
    }

    @Test
    void countXmasVerticallyDown() {
        var solution = new Solution();
        assertEquals(solution.countXmasVerticallyDown(List.of("X", "M", "A", "S"), 0, 0), 1);
        assertEquals(solution.countXmasVerticallyDown(List.of("X", "M", "A", "S", "M"), 0, 0), 1);
        assertEquals(solution.countXmasVerticallyDown(List.of("X", "M", "A", "S", "M", "M"), 0, 0), 1);
        assertEquals(solution.countXmasVerticallyDown(List.of("X", "M", "A", "S", "M", "M", "S"), 0, 0), 1);
        assertEquals(solution.countXmasVerticallyDown(List.of("X", "M", "A", "M", "M", "S"), 0, 0), 0);
        assertEquals(solution.countXmasVerticallyDown(List.of("M", "X", "M", "A"), 1, 0), 0);
    }

    @Test
    void countXmasBackwards() {
        var solution = new Solution();
        assertEquals(solution.countXmasBackwards(List.of("XMAS"), 0, 3), 0);
        assertEquals(solution.countXmasBackwards(List.of("MMSAMXS"), 0, 5), 1);
    }

    @Test
    void isSDownAhead() throws IOException {
        InputReader inputReader = new InputReader();
        List<String> lines = inputReader.readLines(Solution.class.getResourceAsStream("input0.txt"));

        var solution = new Solution();
        assertXMas(lines, solution, 1, 2);
        assertXMas(lines, solution, 2, 6);

    }

    private void assertXMas(List<String> lines, Solution solution, int row, int col) {
        String line = lines.get(row);
        assertTrue(solution.isXmas(lines, row, col, line));
    }
}