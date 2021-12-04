package tech.houssemnasri;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;

public class Part1 {
    public static void run(String inputPath) {
        try {
            List<String> lines = Files.readAllLines(Path.of(inputPath));
            int[] binaryFrequency = new int[lines.get(0).length()];
            for (String binary : lines) {
                if (!binary.isBlank()) {
                    IntStream.range(0, binary.length()).forEach(i -> {
                        if (binary.charAt(i) == '1') {
                            binaryFrequency[i]++;
                        } else {
                            binaryFrequency[i]--;
                        }
                    });
                }
            }
            int pos = binaryFrequency.length - 1;
            int gammaRate = 0;
            for (int freq : binaryFrequency) {
                if (freq > 0) {
                    gammaRate += 1 << pos;
                }
                pos--;
            }
            int epsilonRate = ~gammaRate & ((1 << binaryFrequency.length) - 1);

            System.out.println("Result: " + (gammaRate * epsilonRate));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
