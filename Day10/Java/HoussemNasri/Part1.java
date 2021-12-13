package tech.houssemnasri;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Part1 {
    private static final Map<Character, Integer> illegalCharScoreMap = Map.of(
            ')', 3,
            ']', 57,
            '}', 1197,
            '>', 25137);

    public static void run(String inputPath) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(inputPath));
        int errorScore = 0;
        for (String line : lines) {
            Stack<Character> balance = new Stack<>();
            for (char c : line.toCharArray()) {
                if (c == '(' || c == '[' || c == '{' || c == '<') {
                    balance.push(c);
                } else {
                    if (balance.peek() == '(' && c != ')'
                            || balance.peek() == '[' && c != ']'
                            || balance.peek() == '{' && c != '}'
                            || balance.peek() == '<' && c != '>') {
                        errorScore += illegalCharScoreMap.get(c);
                        break;
                    } else {
                        balance.pop();
                    }
                }
            }
        }
        System.out.println("Score: " + errorScore);
    }
}
