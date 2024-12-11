package com.jabaddon.adventofcode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputReader {
    public List<String> readLines(InputStream inputStream1) throws IOException {
        List<String> lines = new ArrayList<>();
        try (InputStream inputStream = inputStream1;
             Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        }
        return lines;
    }

    public static <T> List<String> readLines(Class<T> clazz, String resourceName) throws IOException {
        return new InputReader().readLines(clazz.getResourceAsStream(resourceName));
    }
}
