package tech.houssemnasri;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Part2 {

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

    private static void increaseAllByOne(Part2.Octopus[][] grid) {
        Arrays.stream(grid).flatMap(Stream::of).forEach(Part2::increaseByOne);
    }

    private static void increaseByOne(Part2.Octopus octopus) {
        octopus.val = (octopus.val + 1) % 10;
    }

    private static int flash(Part2.Octopus octopus, Part2.Octopus[][] grid) {
        int flashCount = 0;
        // Find all adjacent octopus who were not flashed already
        List<Part2.Octopus> adjacency = findAdjacents(octopus, grid).stream().filter(o -> o.val != 0).collect(Collectors.toList());
        adjacency.forEach(Part2::increaseByOne);
        Stack<Part2.Octopus> s = new Stack<>();

        for (Part2.Octopus adj : adjacency) {
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

    private static List<Part2.Octopus> findAdjacents(Part2.Octopus octopus, Part2.Octopus[][] grid) {
        List<Part2.Octopus> adjacent = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int ox = octopus.px;
                int oy = octopus.py;
                if (isPositionValid(ox + j, oy + i, grid[0].length - 1, grid.length - 1)) {
                    if (i != 0 || j != 0)
                        adjacent.add(grid[oy + i][ox + j]);
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
        final Part2.Octopus[][] grid = new Part2.Octopus[lines.size()][lines.get(0).length()];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = new Part2.Octopus(i, j, (byte) (lines.get(i).charAt(j) - '0'));
            }
        }
        int step;
        for (step = 1; true; step++) {
            increaseAllByOne(grid);
            List<Part2.Octopus> flashedOctopuses = Arrays.stream(grid)
                                                         .flatMap(Stream::of)
                                                         .filter(o -> o.val == 0)
                                                         .collect(Collectors.toList());
            for (Part2.Octopus octopus : flashedOctopuses) {
                flash(octopus, grid);
            }

            boolean isAllFlashed = Arrays.stream(grid).flatMap(Stream::of).filter(o -> o.val != 0).findAny().isEmpty();
            if (isAllFlashed) {
                break;
            }
        }
        System.out.println("Final Step: " + step);
    }
}