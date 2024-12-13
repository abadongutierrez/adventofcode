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
                if (ex.getMessage().equals("Cycle!")) cycleCount.incrementAndGet();
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
        Map<Position, Integer> visitedAndHitPositions = new HashMap<>();
        // follow guard direction until is out of the map
        while (true) {
            GuardPositionAndDirection next = getNextPositionKeepingDirection(last);
            if (next.position().isInsideMap(lines)) {
                boolean hitObstacle = obstacles.contains(next.position());
                if (hitObstacle) {
                    next = new GuardPositionAndDirection(
                            last.row(), last.col(), getNewDirection(next.direction()));
                }
                addOrUpdateVisitedAndHitPositionsIfApply(last, visitedAndHitPositions, hitObstacle);
                // TODO: find a better way to find a cycle!!!
                if (hitObstacle && visitedAndHitPositions.get(last.position()) > 100) {
                    throw new RuntimeException("Cycle!");
                }
                last = next;
            } else {
                addOrUpdateVisitedAndHitPositionsIfApply(last, visitedAndHitPositions, false);
                break;
            }
        }
        return visitedAndHitPositions.keySet();
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

    private void addOrUpdateVisitedAndHitPositionsIfApply(GuardPositionAndDirection current, Map<Position, Integer> visitedPositions, boolean hit) {
        var pos = new Position(current.row(), current.col());
        if (!visitedPositions.containsKey(pos)) {
            visitedPositions.put(pos, 0);
        } else if (hit) {
            visitedPositions.put(pos, visitedPositions.get(pos) + 1);
        }
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
