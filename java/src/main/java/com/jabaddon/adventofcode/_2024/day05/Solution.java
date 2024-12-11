package com.jabaddon.adventofcode._2024.day05;

import com.jabaddon.adventofcode.InputReader;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Solution {
    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader();
        List<String> lines = inputReader.readLines(Solution.class.getResourceAsStream("input.txt"));

        var solution = new Solution();
        System.out.println(solution.part1(lines));
        System.out.println(solution.part2(lines));
    }

    private int part1(List<String> lines) {
        Set<String> rules = getRules(lines);
        AtomicInteger sum = new AtomicInteger();
        for (int i = rules.size(); i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.contains(",")) {
                List<Integer> update = getUpdate(line);
                Optional<Integer> result = processUpdate(rules, update);
                result.ifPresent(sum::addAndGet);
            }
        };
        return sum.get();
    }

    private int part2(List<String> lines) {
        Set<String> rules = getRules(lines);
        AtomicInteger sum = new AtomicInteger();
        for (int i = rules.size(); i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.contains(",")) {
                List<Integer> update = getUpdate(line);
                if (processUpdate(rules, update).isEmpty()) {
                    // part 2 only process those "updates" that failed part 1 validation
                    Optional<Integer> result = processUpdatePart2(rules, new ArrayList<>(update));
                    result.ifPresent(sum::addAndGet);
                }
            }
        };
        return sum.get();
    }

    private List<Integer> getUpdate(String line) {
        return Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).boxed().toList();
    }

    private static Set<String> getRules(List<String> lines) {
        Set<String> rules = new HashSet<>();
        lines.forEach(line -> {
            if (line.contains("|")) rules.add(line);
        });
        return rules;
    }

    private Optional<Integer> processUpdate(Set<String> rules, List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            // scan forward
            for (int j = i + 1; j < list.size(); j++) {
                if (!rules.contains(list.get(i) + "|" + list.get(j))) return Optional.empty(); // no rule at all
            }

            // scan backward
            for (int j = i - 1; j >= 0; j--) {
                if (!rules.contains(list.get(j) + "|" + list.get(i))) return Optional.empty(); // no rule at all
            }
        }

        // if all updates are correct, return the middle element
        return Optional.of(list.get(list.size() / 2));
    }

    private Optional<Integer> processUpdatePart2(Set<String> rules, List<Integer> original) {
        ArrayList<Integer> list = new ArrayList<>(original);
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (!rules.contains(list.get(i) + "|" + list.get(j))) {
                    if (rules.contains(list.get(j) + "|" + list.get(i))) {
                        Integer removed = list.remove(j);
                        list.add(i, removed);
                        // i changed, reprocess with same i
                        i--; // to reprocess same i
                        break;
                    } else {
                        return Optional.empty(); // no rule at all
                    }
                }
            }
        }
        // once fixed, return middle element
        return Optional.of(list.get(list.size() / 2));
    }
}
