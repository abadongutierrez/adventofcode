package com.jabaddon.adventofcode._2022.day01;

import java.util.*;

public class SolutionPart1 extends AbstractSolutionDay {
    public int solution(List<String> lines) {
        Queue<Integer> elfCounts = new ArrayDeque<>(lines.size());
        extracted(lines, elfCounts);
        return elfCounts.stream().max(Integer::compare).get();
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(Objects.requireNonNull(SolutionPart1.class.getResourceAsStream("input.txt")))) {
            SolutionPart1 solutionPart1 = new SolutionPart1();
            List<String> lines = new ArrayList<>();
            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
            System.out.println(solutionPart1.solution(lines));
        }
    }
}
