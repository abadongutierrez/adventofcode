package com.jabaddon.adventofcode._2024.day03;

import com.jabaddon.adventofcode.InputReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {
    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader();
        List<String> lines = inputReader.readLines(Solution.class.getResourceAsStream("input.txt"));
        var solution = new Solution();
        System.out.println(solution.captureAndSumMulInstructions(lines));
        System.out.println(solution.captureAndSumMulInstructionsWhenEnabled(lines));
    }

    Long captureAndSumMulInstructions(List<String> lines) {
        long result = 0;
        String regexMul = "mul\\((\\d{1,3}),(\\d{1,3})\\)";
        // Compile the regex pattern
        Pattern patternMul = Pattern.compile(regexMul);
        for (String line : lines) {
            Matcher matcher = patternMul.matcher(line);
            // Find and print all matches
            while (matcher.find()) {
                String fullMatch = matcher.group(0); // the whole "mul(x,y)"
                String firstNum = matcher.group(1);  // captured first number
                String secondNum = matcher.group(2); // captured second number
                // Get the full match and the captured groups

                //System.out.println("Full Match: " + fullMatch);
                //System.out.println("Region start: " + matcher.start());
                //System.out.println("First Number: " + firstNum);
                //System.out.println("Second Number: " + secondNum);
                result += (Long.parseLong(firstNum) * Long.parseLong(secondNum));
            }
        }
        return result;
    }

    Long captureAndSumMulInstructionsWhenEnabled(List<String> lines) {
        long result = 0;
        String regexMul = "mul\\((\\d{1,3}),(\\d{1,3})\\)";
        String regexDo = "do\\(\\)";
        String regexDont = "don't\\(\\)";
        // Compile the regex pattern
        Pattern patternMul = Pattern.compile(regexMul);
        Pattern patternDo = Pattern.compile(regexDo);
        Pattern patternDont = Pattern.compile(regexDont);
        for (String line : lines) {
            List<Match> matches = new ArrayList<>();

            applyMatcher(patternMul.matcher(line), matches);
            applyMatcher(patternDo.matcher(line), matches);
            applyMatcher(patternDont.matcher(line), matches);

            matches.sort(Comparator.comparing(Match::index));

            boolean enabled = true;
            for (Match match : matches) {
                if (match.str().startsWith("mul") && enabled) {
                    var numbers = match.str().substring(match.str().indexOf("(")+1, match.str().length()-1);
                    String[] split = numbers.split(",");
                    result += (Long.parseLong(split[0]) * Long.parseLong(split[1]));
                } else enabled = match.str().equals("do()");
            }

        }
        return result;
    }

    private void applyMatcher(Matcher matcher, List<Match> matches) {
        // Find and print all matches
        while (matcher.find()) {
            String fullMatch = matcher.group(0); // the whole "mul(x,y)"
            matches.add(new Match(matcher.start(), fullMatch));
        }
    }
}

record Match(int index, String str) {}
