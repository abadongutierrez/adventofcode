package com.jabaddon.adventofcode._2022.day01;

import java.util.*;
import java.util.stream.IntStream;

public class SolutionPart2 extends AbstractSolutionDay {
    public int solution(List<String> lines) {
        PriorityQueue<Integer> elfCounts = new PriorityQueue<>(lines.size(), Comparator.comparing(Integer::intValue).reversed());
        iterateSumAndAdd(lines, elfCounts);
        return IntStream.rangeClosed(1, 3).map(i -> elfCounts.poll()).sum();
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(Objects.requireNonNull(SolutionPart1.class.getResourceAsStream("input.txt")))) {
            SolutionPart2 solution = new SolutionPart2();
            List<String> lines = new ArrayList<>();
            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
            System.out.println(solution.solution(lines));
        }
    }
}
