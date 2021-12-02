package tech.houssemnasri.HoussemNasri;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static List<Integer> readIntegerInputs() {
        try {
            return Files.readAllLines(Path.of("input.txt"))
                        .stream()
                        .map(Integer::valueOf)
                        .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return List.of();
    }
}
