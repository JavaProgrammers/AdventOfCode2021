package tech.houssemnasri;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Part2 {

    public static void run(String inputPath) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(inputPath));
        List<Long> autoCompleteScores = new ArrayList<>();
        for (String line : lines) {
            Stack<Character> balance = new Stack<>();
            boolean error = false;
            for (char c : line.toCharArray()) {
                if (c == '(' || c == '[' || c == '{' || c == '<') {
                    balance.push(c);
                } else {
                    if (balance.peek() == '(' && c != ')'
                            || balance.peek() == '[' && c != ']'
                            || balance.peek() == '{' && c != '}'
                            || balance.peek() == '<' && c != '>') {
                        error = true;
                        break;
                    } else {
                        balance.pop();
                    }
                }
            }
            if (!error) {
                List<Character> completionCharScore = List.of('(', '[', '{', '<');
                long score = 0;
                while (!balance.isEmpty()) {
                    score = (score * 5) + (completionCharScore.indexOf(balance.pop()) + 1);
                }
                autoCompleteScores.add(score);
            }
        }
        System.out.println("Middle Score: " + autoCompleteScores.stream().sorted().collect(Collectors.toList()).get(autoCompleteScores.size() / 2));
    }
}