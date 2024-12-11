package com.jabaddon.adventofcode._2024.day06;

import com.jabaddon.adventofcode.InputReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {
    public static void main(String[] args) throws IOException {
        List<String> lines = InputReader.readLines(Solution.class, "input.txt");

        var solution = new Solution();
        System.out.println(solution.part1(lines));
        // TODO part2
    }

    private int part1(List<String> original) {
        List<String> lines = new ArrayList<>(original);
        int totalRows = lines.size();
        int totalCols = lines.get(0).length();
        GuardPositionAndDirection current = findGuard(lines);
        Set<Position> visitedPositions = new HashSet<>();
        int countDistinctPositions = 0;
        // follow guard direction until is out of the map
        while (true) {
//            System.out.println(current);
            GuardPositionAndDirection next = null;
            switch (current.direction()) {
                case '^' -> next = new GuardPositionAndDirection(current.row() - 1, current.col(), current.direction());
                case 'v' -> next = new GuardPositionAndDirection(current.row() + 1, current.col(), current.direction());
                case '<' -> next = new GuardPositionAndDirection(current.row(), current.col() - 1, current.direction());
                case '>' -> next = new GuardPositionAndDirection(current.row(), current.col() + 1, current.direction());
            }
            // verify if guard is still inside the map
            if (next.row() >= 0 && next.row() < totalRows && next.col() >= 0 && next.col() < totalCols) {
                // verify if new guard position hit an obstacle
                if (lines.get(next.row()).charAt(next.col()) == '#') {
                    var newDir = ' ';
                    switch (next.direction()) {
                        case '^' -> newDir = '>';
                        case 'v' -> newDir = '<';
                        case '<' -> newDir = '^';
                        case '>' -> newDir = 'v';
                    }
                    // new guard position is the last one but with new direction
                    next = new GuardPositionAndDirection(current.row(), current.col(), newDir);
                }
                // update visited position in map
                countDistinctPositions = getCountDistinctPositions(current, visitedPositions, countDistinctPositions);
                // update guard position
                current = next;
            } else {
                // guard is out of the map
                countDistinctPositions = getCountDistinctPositions(current, visitedPositions, countDistinctPositions);
                break;
            }
        }
        return countDistinctPositions;
    }

    private int getCountDistinctPositions(GuardPositionAndDirection current, Set<Position> visitedPositions, int count) {
        var pos = new Position(current.row(), current.col());
        if (!visitedPositions.contains(pos)) {
            visitedPositions.add(pos);
            return count + 1;
        }
        return count;
    }

    private GuardPositionAndDirection findGuard(List<String> lines) {
        for (int row = 0; row < lines.size(); row++) {
            for (int col = 0; col < lines.get(row).length(); col++) {
                char c = lines.get(row).charAt(col);
                if (c == '^' || c == 'v' || c == '<' || c == '>') {
                    return new GuardPositionAndDirection(row, col, c);
                }
            }
        }
        throw new IllegalArgumentException("Guard not found");
    }
}

record Position(int row, int col) {
}

record GuardPositionAndDirection(int row, int col, char direction) {
}
