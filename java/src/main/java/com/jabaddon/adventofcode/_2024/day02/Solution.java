package com.jabaddon.adventofcode._2024.day02;

import com.jabaddon.adventofcode.InputReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader();
        List<String> lines = inputReader.readLines(Solution.class.getResourceAsStream("input.txt"));
        System.out.println(lines.size());
        Solution solution = new Solution();
        System.out.println(solution.countSafeLevels(lines));
        System.out.println(solution.countSafeLevelsWithTolerance(lines));

    }

    long countSafeLevels(List<String> lines) {
        return lines.stream().mapToLong(line -> isSafeLevel(line) ? 1L : 0L).sum();
    }

    long countSafeLevelsWithTolerance(List<String> lines) {
        return lines.stream().mapToLong(line -> isSafeLevelWithTolerance(line) ? 1L : 0L).sum();
    }

    boolean isSafeLevelWithTolerance(String line) {
        var list = Arrays.stream(line.split("\\s+")).mapToLong(Long::valueOf).boxed().toList();
        if (list.isEmpty() || list.size() < 2) return false;

        for (int i = 0; i < list.size(); i++) {
            var newList = copyListExceptIndex(list, i);
            // if it is safe without this element considere it safe
            if (isSafeLevelForList(newList)) return true;
        }
        return false;
    }

    List<Long> copyListExceptIndex(List<Long> list, int index) {
        List<Long> newList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i != index) newList.add(list.get(i));
        }
        return newList;
    }

    boolean isSafeLevel(String line) {
        var list = Arrays.stream(line.split("\\s+")).mapToLong(Long::valueOf).boxed().toList();
        if (list.isEmpty() || list.size() < 2) return false;
        return isSafeLevelForList(list);
    }

    private boolean isSafeLevelForList(List<Long> list) {
        boolean increasing = false;
        if (isAllIncreasing(list)) increasing = true;
        else if (isAllDecreasing(list)) increasing = false;
        else return false;

        var last = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            var curr = list.get(i);
            // check increasing or decreasing is correct
            if ((increasing && curr <= last) || (!increasing && curr >= last)) {
                return false;
            }
            // check difference
            var diff = increasing ? curr - last : last - curr;
            if (isValidDiff(diff)) {
                last = curr;
            } else return false;
        }
        return true;
    }

    private boolean isAllIncreasing(List<Long> list) {
        var last = -1L;
        for (Long l : list) {
            if (l < last) return false;
            last = l;
        }
        return true;
    }

    private boolean isAllDecreasing(List<Long> list) {
        var last = Long.MAX_VALUE;
        for (Long l : list) {
            if (l > last) return false;
            last = l;
        }
        return true;
    }

    private boolean isValidDiff(long diff) {
        return diff >= 1 && diff <= 3;
    }
}
