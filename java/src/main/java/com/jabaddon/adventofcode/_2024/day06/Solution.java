package com.jabaddon.adventofcode._2024.day06;

import com.jabaddon.adventofcode.InputReader;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Solution {
    public static void main(String[] args) throws IOException {
        List.of("inputExample.txt", "input.txt").forEach(input -> {
            System.out.println("Input: " + input);
            List<String> lines = null;
            try {
                lines = InputReader.readLines(Solution.class, input);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            var solution = new Solution();
            System.out.println(solution.part1(lines).size());
            System.out.println(solution.part2(lines));
        });
    }

    private int part2(List<String> original) {
        Set<Position> visitedPositions = part1(original);
        List<String> lines = new ArrayList<>(original);
        GuardPositionAndDirection initPosition = findGuard(lines);
        // remove guard current position
        visitedPositions.remove(new Position(initPosition.row(), initPosition.col()));
        Set<Position> obstacles = findObstacles(lines);
        AtomicInteger cycleCount = new AtomicInteger(0);
        visitedPositions.forEach(vistedPosition -> {
            try {
                Set<Position> newObstacles = new HashSet<>(obstacles);
                newObstacles.add(vistedPosition);
                internalGetPositions(initPosition, lines, newObstacles);
            } catch (RuntimeException ex) {
                if (ex.getMessage().equals("Loop!")) cycleCount.incrementAndGet();
            }
        });
        return cycleCount.get();
    }

    private Set<Position> part1(List<String> original) {
        List<String> lines = new ArrayList<>(original);
        Set<Position> obstacles = findObstacles(lines);
        GuardPositionAndDirection last = findGuard(lines);
        return internalGetPositions(last, lines, obstacles);
    }

    private Set<Position> internalGetPositions(GuardPositionAndDirection last, List<String> lines, Set<Position> obstacles) {
        // keeps track of the visited positions
        Set<Position> visitedPositions = new HashSet<>();
        // keeps track of the number of times an obstacle has been hit
        Map<Position, Integer> obstaclesHit = new HashMap<>();
        // follow guard direction until is out of the map
        while (true) {
            // update current position as visited
            visitedPositions.add(new Position(last.row(), last.col()));
            // find next position
            GuardPositionAndDirection next = getNextPositionKeepingDirection(last);
            if (next.position().isInsideMap(lines)) {
                if (obstacles.contains(next.position())) {
                    // update obstacle hit count
                    obstaclesHit.compute(new Position(next.row(), next.col()), (k, v) -> v == null ? 1 : v + 1);
                    // verify a loop
                    if (obstaclesHit.get(new Position(next.row(), next.col())) > 2) {
                        throw new RuntimeException("Loop!");
                    }

                    // fix next because we cannot go into an obstacle
                    next = new GuardPositionAndDirection(
                            last.row(), last.col(), getNewDirection(next.direction()));
                }
                last = next;
            } else {
                break;
            }
        }
        return visitedPositions;
    }

    private Set<Position> findObstacles(List<String> lines) {
        Set<Position> obstacles = new HashSet<>();
        for (int row = 0; row < lines.size(); row++) {
            for (int col = 0; col < lines.get(row).length(); col++) {
                char c = lines.get(row).charAt(col);
                if (c == '#') {
                    obstacles.add(new Position(row, col));
                }
            }
        }
        return obstacles;
    }

    private char getNewDirection(char direction) {
        var newDir = direction;
        switch (direction) {
            case '^' -> newDir = '>';
            case 'v' -> newDir = '<';
            case '<' -> newDir = '^';
            case '>' -> newDir = 'v';
        }
        return newDir;
    }

    private GuardPositionAndDirection getNextPositionKeepingDirection(GuardPositionAndDirection current) {
        GuardPositionAndDirection next = null;
        switch (current.direction()) {
            case '^' -> next = new GuardPositionAndDirection(current.row() - 1, current.col(),  current.direction());
            case 'v' -> next = new GuardPositionAndDirection(current.row() + 1, current.col(), current.direction());
            case '<' -> next = new GuardPositionAndDirection(current.row(), current.col() - 1, current.direction());
            case '>' -> next = new GuardPositionAndDirection(current.row(), current.col() + 1, current.direction());
        }
        return next;
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
    boolean isInsideMap(List<String> lines) {
        return row() >= 0 && row() < lines.size() && col() >= 0 && col() < lines.get(0).length();
    }
}

record GuardPositionAndDirection(int row, int col, char direction) {
    public Position position() {
        return new Position(row, col);
    }
}
