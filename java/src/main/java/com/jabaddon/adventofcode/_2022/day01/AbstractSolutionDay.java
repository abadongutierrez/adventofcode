package com.jabaddon.adventofcode._2022.day01;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class AbstractSolutionDay {
    static void extracted(List<String> lines, Queue<Integer> elfCounts) {
        int caloriesCount = 0;
        for (String line : lines) {
            if (line.isBlank()) {
                elfCounts.add(caloriesCount);
                caloriesCount = 0;
            } else {
                caloriesCount += Integer.parseInt(line);
            }
        }
        if (caloriesCount > 0) {
            elfCounts.add(caloriesCount);
        }
    }
}
