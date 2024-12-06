package com.jabaddon.adventofcode._2024.day04;

import com.jabaddon.adventofcode.InputReader;

import java.io.IOException;
import java.util.List;

public class Solution {

    private static final int MAS_LENGTH = "MAS".length();

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader();
        List<String> lines = inputReader.readLines(Solution.class.getResourceAsStream("input.txt"));
        var solution = new Solution();
        System.out.println(solution.countXmas(lines));
        System.out.println(solution.countX_mas(lines));
    }

    public int countXmas(List<String> lines) {
        int count = 0;
        for (int row = 0; row < lines.size(); row++) {
            for (int col = 0; col < lines.get(row).length(); col++) {
                char c = lines.get(row).charAt(col);
                if (c == 'X') {
                    count += countXmasHorizontally(lines, row, col);
                    count += countXmasVerticallyDown(lines, row, col);
                    count += countXmasVerticallyUp(lines, row, col);
                    count += countXmasBackwards(lines, row, col);
                    count += countXmasDiagonallyDown(lines, row, col);
                    count += countXmasDiagonallyUp(lines, row, col);
                    count += countXmasDiagonallyDownBackwards(lines, row, col);
                    count += countXmasDiagonallyUpBackwards(lines, row, col);
                }
            }
        }
        return count;
    }

    public int countX_mas(List<String> lines) {
        int count = 0;
        for (int row = 0; row < lines.size(); row++) {
            for (int col = 0; col < lines.get(row).length(); col++) {
                String line = lines.get(row);
                char c = line.charAt(col);
                if (c == 'A') {
                    count += isXmas(lines, row, col, line) ? 1 : 0;
                }
            }
        }
        return count;
    }

    boolean isXmas(List<String> lines, int row, int col, String line) {
        // take first MAS or SAM in diagonal
        String first = getCharUpBackwards(lines, row, col) + "A" + getCharDownAhead(lines, row, col, line);
        // take second MAS or SAM in diagonal
        String second = getCharUpAhead(lines, row, col, line) + "A" + getCharDownBackwards(lines, row, col);

        return (first.equals("MAS") || first.equals("SAM")) &&
                (second.equals("MAS") || second.equals("SAM"));
    }

    String getCharDownAhead(List<String> lines, int row, int col, String line) {
        if (isEnoughRowsDown(lines, 1, row) && isEnoughCharsAhead(col, 1, line)) {
            return "" + lines.get(row + 1).charAt(col + 1);
        }
        return "";
    }

    String getCharUpBackwards(List<String> lines, int row, int col) {
        if (isEnoughRowsUp(row, 1) && isEnoughCharsBackwards(col, 1)) {
            return "" + lines.get(row - 1).charAt(col - 1);
        }
        return "";
    }

    String getCharUpAhead(List<String> lines, int row, int col, String line) {
        if (isEnoughRowsUp(row, 1) && isEnoughCharsAhead(col, 1, line)) {
            return "" + lines.get(row - 1).charAt(col + 1);
        }
        return "";
    }

    String getCharDownBackwards(List<String> lines, int row, int col) {
        if (isEnoughRowsDown(lines, 1, row) && isEnoughCharsBackwards(col, 1)) {
            return "" + lines.get(row + 1).charAt(col - 1);
        }
        return "";
    }

    private int countXmasDiagonallyUpBackwards(List<String> lines, int row, int col) {
        // we know (row,col) contains X
        // need to check if there are enough rows up and columns backwards
        String line = lines.get(row);
        if (isEnoughRowsUp(row, MAS_LENGTH) && isEnoughCharsBackwards(col, MAS_LENGTH)) {
            // now from the same row we need to get the char in the last 3 columns
            String substring = "" +
                    lines.get(row).charAt(col) + // X ?
                    lines.get(row - 1).charAt(col - 1) + // M ?
                    lines.get(row - 2).charAt(col - 2) + // A ?
                    lines.get(row - 3).charAt(col - 3); // S ?
            return substring.equals("XMAS") ? 1 : 0;
        }
        return 0;
    }

    int countXmasDiagonallyDownBackwards(List<String> lines, int row, int col) {
        // we know (row,col) contains X
        // need to check if there are enough rows down and columns backwards
        String line = lines.get(row);
        if (isEnoughRowsDown(lines, row, MAS_LENGTH) && isEnoughCharsBackwards(col, MAS_LENGTH)) {
            // now from the same row we need to get the char in the following 3 rows
            String substring = "" +
                    lines.get(row).charAt(col) + // X ?
                    lines.get(row + 1).charAt(col - 1) + // M ?
                    lines.get(row + 2).charAt(col - 2) + // A ?
                    lines.get(row + 3).charAt(col - 3); // S ?
            return substring.equals("XMAS") ? 1 : 0;
        }
        return 0;
    }

    private int countXmasDiagonallyUp(List<String> lines, int row, int col) {
        // we know (row,col) contains X
        // need to check if there are enough rows up and columns ahead
        String line = lines.get(row);
        if (isEnoughRowsUp(row, MAS_LENGTH) && isEnoughCharsAhead(col, MAS_LENGTH, line)) {
            String substring = "" +
                    lines.get(row).charAt(col) + // X ?
                    lines.get(row - 1).charAt(col + 1) + // M ?
                    lines.get(row - 2).charAt(col + 2) + // A ?
                    lines.get(row - 3).charAt(col + 3); // S ?
            return substring.equals("XMAS") ? 1 : 0;
        }
        return 0;
    }

    int countXmasDiagonallyDown(List<String> lines, int row, int col) {
        // we know (row,col) contains X
        // need to check if there are enough rows down and columns ahead
        String line = lines.get(row);
        if (isEnoughRowsDown(lines, row, MAS_LENGTH) && isEnoughCharsAhead(col, MAS_LENGTH, line)) {
            // now from the same row we need to get the char in the following 3 rows
            String substring = "" +
                    lines.get(row).charAt(col) + // X ?
                    lines.get(row + 1).charAt(col + 1) + // M ?
                    lines.get(row + 2).charAt(col + 2) + // A ?
                    lines.get(row + 3).charAt(col + 3); // S ?
            return substring.equals("XMAS") ? 1 : 0;
        }
        return 0;
    }

    int countXmasBackwards(List<String> lines, int row, int col) {
        // we know (row,col) contains X
        // need to check if there are enough characters backwards
        if (isEnoughCharsBackwards(col, MAS_LENGTH)) {
            // now from the same row we need to get the char in the last 3 columns
            String substring = "" +
                    lines.get(row).charAt(col) + // X ?
                    lines.get(row).charAt(col - 1) + // M ?
                    lines.get(row).charAt(col - 2) + // A ?
                    lines.get(row).charAt(col - 3); // S ?
            return substring.equals("XMAS") ? 1 : 0;
        }
        return 0;
    }

    int countXmasVerticallyUp(List<String> lines, int row, int col) {
        // we know (row,col) contains X
        // need to check if there are enough rows up
        if (isEnoughRowsUp(row, MAS_LENGTH)) {
            // now from the same row we need to get the char in the last 3 rows
            String substring = "" +
                    lines.get(row).charAt(col) + // X ?
                    lines.get(row - 1).charAt(col) + // M ?
                    lines.get(row - 2).charAt(col) + // A ?
                    lines.get(row - 3).charAt(col); // S ?
            return substring.equals("XMAS") ? 1 : 0;
        }
        return 0;
    }

    int countXmasVerticallyDown(List<String> lines, int row, int col) {
        // we know (row,col) contains X
        // need to check if there are enough rows down
        if (isEnoughRowsDown(lines, row, MAS_LENGTH)) {
            // now from the same row we need to get the char in the following 3 rows
            String substring = "" +
                    lines.get(row).charAt(col) + // X ?
                    lines.get(row + 1).charAt(col) + // M ?
                    lines.get(row + 2).charAt(col) + // A ?
                    lines.get(row + 3).charAt(col); // S ?
            return substring.equals("XMAS") ? 1 : 0;
        }
        return 0;
    }

    int countXmasHorizontally(List<String> lines, int row, int col) {
        // we know (row,col) contains X
        var line = lines.get(row);
        // need to check if there are enough chars ahead
        if (isEnoughCharsAhead(col, MAS_LENGTH, line)) {
            String substring = line.substring(col, col + MAS_LENGTH + 1);
            return substring.equals("XMAS") ? 1 : 0;
        }
        return 0;
    }

    private static boolean isEnoughCharsBackwards(int col, int length) {
        return isEnoughRowsUp(col, length);
    }

    private static boolean isEnoughRowsUp(int row, int length) {
        return row - length >= 0;
    }

    private static boolean isEnoughCharsAhead(int col, int length, String line) {
        return col + length < line.length();
    }

    private static boolean isEnoughRowsDown(List<String> lines, int row, int length) {
        return row + length < lines.size();
    }
}
