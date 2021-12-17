package tech.houssemnasri;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Part2 {
    public static void run(String inputPath) throws IOException {
        List<Integer> positions = Arrays.stream(new BufferedReader(new FileReader(Path.of(inputPath).toFile()))
                .readLine()
                .split("\s*,\s*")).map(Integer::valueOf).collect(Collectors.toList());
        double sum = positions.stream().mapToDouble(Double::valueOf).sum();
        double median = sum / positions.size();
        System.out.println("Median: " + median);
        int minimumFuel = positions.stream().map(p -> Math.abs(p - Math.floor(median))).map(n -> (int) (n * (n + 1)) / 2).mapToInt(Integer::intValue).sum();
        System.out.println("Minimum Fuel: " + minimumFuel);
    }
}
