package tech.houssemnasri;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Part1 {
    private static class Octopus {
        public int py;
        public int px;
        public int val;

        public Octopus(int py, int px, int val) {
            this.py = py;
            this.px = px;
            this.val = val;
        }
    }

    private static void increaseAllByOne(Octopus[][] grid) {
        Arrays.stream(grid).flatMap(Stream::of).forEach(Part1::increaseByOne);
    }

    private static void increaseByOne(Octopus octopus) {
        octopus.val = (octopus.val + 1) % 10;
    }

    private static int flash(Octopus octopus, Octopus[][] grid) {
        int flashCount = 0;
        // Find all adjacent octopus who were not flashed already
        List<Octopus> adjacency = findAdjacents(octopus, grid).stream().filter(o -> o.val != 0).collect(Collectors.toList());
        adjacency.forEach(Part1::increaseByOne);
        Stack<Octopus> s = new Stack<>();

        for (Octopus adj : adjacency) {
            if (adj.val == 0) {
                flashCount++;
                s.push(adj);
            }
        }
        while (!s.isEmpty()) {
            flashCount = flashCount + flash(s.pop(), grid);
        }
        return flashCount;
    }

    private static List<Octopus> findAdjacents(Octopus octopus, Octopus[][] grid) {
        List<Octopus> adjacent = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int ox = octopus.px;
                int oy = octopus.py;
                if (isPositionValid(ox + j, oy + i, grid[0].length - 1, grid.length - 1)) {
                    if (!(i == 0 && j == 0)) {
                        adjacent.add(grid[oy + i][ox + j]);
                    }
                }
            }
        }
        return adjacent;
    }

    private static boolean isPositionValid(int px, int py, int maxX, int maxY) {
        return px >= 0 && px <= maxX && py >= 0 && py <= maxY;
    }

    public static void run(String inputPath) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(inputPath));
        final Octopus[][] grid = new Octopus[lines.size()][lines.get(0).length()];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = new Octopus(i, j, (byte) (lines.get(i).charAt(j) - '0'));
            }
        }
        int totalFlashCount = 0;
        for (int step = 1; step <= 100; step++) {
            increaseAllByOne(grid);
            List<Octopus> flashedOctopuses = Arrays.stream(grid)
                                                   .flatMap(Stream::of)
                                                   .filter(o -> o.val == 0).collect(Collectors.toList());
            int thisStepFlashCount = 0;
            for (Octopus octopus : flashedOctopuses) {
                thisStepFlashCount += flash(octopus, grid) + 1;
            }

            totalFlashCount += thisStepFlashCount;
        }
        System.out.println("Total Flashes: " + totalFlashCount);
    }
}
