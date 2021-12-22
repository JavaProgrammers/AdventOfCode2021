package tech.houssemnasri;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Part1 {

    public static void run(String inputPath) throws IOException {
        List<String> folds = new ArrayList<>();
        Boolean[][] grid = new Boolean[1500][1500];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = Boolean.FALSE;
            }
        }
        int width = 0;
        int height = 0;
        for (String ln : Files.readAllLines(Path.of(inputPath))) {
            if (ln.matches("^\\d+,\\d+$")) {
                int x = Integer.parseInt(ln.split(",")[0]);
                int y = Integer.parseInt(ln.split(",")[1]);
                grid[y][x] = true;
                width = Math.max(x + 1, width);
                height = Math.max(y + 1, height);
            } else if (!ln.isBlank()) {
                folds.add(ln.split("\s+")[2]);
            }
        }

        for (int i = 0; i < 1; i++) {
            String fold = folds.get(i);
            int foldValue = Integer.parseInt(fold.split("=")[1]);
            if (fold.charAt(0) == 'x') {
                width = Math.min(foldValue, width - foldValue);
                for (int j = 0; j < width; j++) {
                    for (int k = 0; k < height; k++) {
                        boolean left = grid[k][foldValue - j - 1];
                        boolean right = grid[k][foldValue + j + 1];
                        grid[k][foldValue - j - 1] = left || right;
                    }
                }
            } else {
                height = Math.min(foldValue, height - foldValue);
                for (int j = 0; j < height; j++) {
                    for (int k = 0; k < width; k++) {
                        boolean up = grid[foldValue - j - 1][k];
                        boolean down = grid[foldValue + j + 1][k];
                        grid[foldValue - j - 1][k] = up || down;
                    }
                }
            }
        }
        final int wi = width;
        long count = Arrays.stream(grid).limit(height)
                           .map(r -> Arrays.stream(r).limit(wi).collect(Collectors.toList()))
                           .flatMap(Collection::stream).filter(Boolean::booleanValue).count();
        System.out.printf("Answer: %d", count);
    }
}
