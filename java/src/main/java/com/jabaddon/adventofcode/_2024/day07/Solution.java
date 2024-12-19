package com.jabaddon.adventofcode._2024.day07;

import com.jabaddon.adventofcode.InputReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

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
            System.out.println(solution.part1(lines));
        });
    }

    private long part1(List<String> lines) {
        AtomicLong total = new AtomicLong(0);
        lines.forEach(line -> {
            long testValue = parseTestValue(line);
            List<Long> numbers = parseNumbers(line);

            Tree tree = new Tree(numbers.getFirst());
            for (int i = 1; i < numbers.size(); i++) {
                tree.addChild(numbers.get(i));
            }

            if (tree.test(testValue)) {
                total.addAndGet(testValue);
            }
        });
        return total.get();
    }

    private List<Long> parseNumbers(String line) {
        var parts = line.split(":")[1].trim().split(" ");
        return Stream.of(parts).map(Long::parseLong).toList();
    }

    private long parseTestValue(String line) {
        return Long.parseLong(line.substring(0, line.indexOf(":")));
    }
}

class Tree {
    Node root;

    public Tree(long value) {
        root = new Node(value);
    }

    public void addChild(long value) {
        addChild(root, value);
    }

    public void addChild(Node node, long value) {
        if (node.isLeaf()) {
            node.addChild(value);
        } else {
            node.children.forEach((operator, child) -> {
                addChild(child, value);
            });
        }
    }

    public boolean test(long testValue) {
        List<Boolean> results = new ArrayList<>();
        test(root.value, testValue, root, results);
        return results.stream().anyMatch(i -> i);
    }

    private void test(long acc, long testValue, Node node, List<Boolean> results) {
        if (node.isLeaf()) {
            results.add(acc == testValue);
        } else {
            if (results.stream().anyMatch(i -> i)) {
               // do nothing
            } else {
                node.children.forEach((operator, child) -> {
                    if (operator.equals("+")) {
                        test(acc + child.value, testValue, child, results);
                    } else {
                        test(acc * child.value, testValue, child, results);
                    }
                });
            }
        }
    }
}

class Node {
    long value;
    Map<String, Node> children = new HashMap<>();

    public Node(long value) {
        this.value = value;
    }

    public void addChild(long value) {
        List.of("+", "*").forEach(operator -> {
            children.put(operator, new Node(value));
        });
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }
}
