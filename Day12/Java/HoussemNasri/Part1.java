package tech.houssemnasri;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Part1 {
    private static class Cave {
        private final Set<Cave> neighbours = new HashSet<>();
        private final String name;
        private final boolean isSmall;
        private boolean isVisited = false;

        public Cave(String name) {
            this.name = name;
            isSmall = Character.isLowerCase(name.charAt(0));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Cave cave = (Cave) o;
            return Objects.equals(name, cave.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, isSmall);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static void run(String inputPath) throws IOException {
        final Map<String, Cave> caveLookup = new HashMap<>();
        Files.readAllLines(Path.of(inputPath)).stream().map(line -> line.split("-")).forEach(caveNames -> {
            if (caveLookup.get(caveNames[0]) == null) {
                caveLookup.put(caveNames[0], new Cave(caveNames[0]));
            }
            if (caveLookup.get(caveNames[1]) == null) {
                caveLookup.put(caveNames[1], new Cave(caveNames[1]));
            }
            Cave cave1 = caveLookup.get(caveNames[0]);
            Cave cave2 = caveLookup.get(caveNames[1]);

            cave1.neighbours.add(cave2);
            cave2.neighbours.add(cave1);
        });
        List<String> allPaths = new ArrayList<>();
        DFS2(caveLookup.get("start"), new ArrayList<>(), allPaths);
        System.out.println("Answer: " + allPaths.size());
    }

    public static void DFS2(Cave cave, List<String> currentPathParts, List<String> allPaths) {
        currentPathParts.add(cave.toString());
        if (cave.isSmall) {
            cave.isVisited = true;
        }
        if (cave.name.equals("end")) {
            allPaths.add(String.join(",", currentPathParts));
        } else {
            for (Cave neighbour : cave.neighbours) {
                if (!neighbour.name.equals("start")) {
                    if (!neighbour.isVisited) {
                        DFS2(neighbour, currentPathParts, allPaths);
                        neighbour.isVisited = false;
                    }
                }
            }
        }
        currentPathParts.remove(currentPathParts.size() - 1);
    }
}
