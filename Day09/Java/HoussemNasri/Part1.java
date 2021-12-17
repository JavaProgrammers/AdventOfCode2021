package tech.houssemnasri;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Part1 {
    public static void run(String inputPath) throws IOException {
        List<List<String>> heightMap = Files.readAllLines(Path.of(inputPath))
                                            .stream().map(s -> Arrays.asList(s.split(""))).collect(Collectors.toList());
        int riskLevelsSum = 0;
        for (int i = 0; i < heightMap.size(); i++) {
            List<String> row = heightMap.get(i);
            for (int j = 0; j < row.size(); j++) {
                int point = Integer.parseInt(row.get(j));
                List<Integer> neighbours = new ArrayList<>();
                if (j > 0) {
                    neighbours.add(Integer.parseInt(row.get(j - 1)));
                }
                if (j < row.size() - 1) {
                    neighbours.add(Integer.parseInt(row.get(j + 1)));
                }
                if (i > 0) {
                    neighbours.add(Integer.parseInt(heightMap.get(i - 1).get(j)));
                }
                if (i < heightMap.size() - 1) {
                    neighbours.add(Integer.parseInt(heightMap.get(i + 1).get(j)));
                }
                if (neighbours.stream().mapToInt(Integer::intValue).min().getAsInt() > point) {
                    riskLevelsSum += 1 + point;
                }
            }
        }
        System.out.println("Risk Levels Sum: " + riskLevelsSum);
    }
}
