package com.jabaddon.adventofcode._2024.day01;

import com.jabaddon.adventofcode.InputReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    private final List<Long> numbersRight = new ArrayList<>();
    private final List<Long> numbersLeft = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = InputReader.readLines(Solution.class, "input.txt");
        Solution solution = new Solution();
        // total lines
        System.out.println(lines.size());
        // total distance
        System.out.println(solution.solution(lines));
    }

    private long solution(List<String> lines) {
        lines.forEach(line -> {
            String[] split = line.split("\\s+");
            numbersLeft.add(Long.parseLong(split[0]));
            numbersRight.add(Long.parseLong(split[1]));
        });

        numbersLeft.sort(null);
        numbersRight.sort(null);

        long distance = 0;
        for (int i = 0; i < numbersLeft.size(); i++) {
            distance += Math.abs(numbersLeft.get(i) - numbersRight.get(i));
        }
        return distance;
    }
}
