package tech.houssemnasri;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Part1 {

    public static void run(String inputPath) throws IOException {
        List<Integer> positions = Arrays.stream(new BufferedReader(new FileReader(Path.of(inputPath).toFile()))
                .readLine()
                .split(",")).map(Integer::valueOf).collect(Collectors.toList());
        System.out.println("Positions Size: " + positions.size());
        List<Integer> sortedPositions = positions.stream().sorted().collect(Collectors.toList());
        int median = sortedPositions.get(sortedPositions.size() / 2);
        System.out.println("Median: " + median);
        int minimumFuel = positions.stream().map(p -> Math.abs(p - median)).mapToInt(Integer::intValue).sum();
        System.out.println("Minimum Fuel: " + minimumFuel);
    }
}
