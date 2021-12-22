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

public class Part2 {

    private static class Cave {
        private final Set<Cave> neighbours = new HashSet<>();
        private final String name;
        private final boolean isSmall;
        private int visitsCount = 0;

        public Cave(String name) {
            this.name = name;
            this.isSmall = Character.isLowerCase(name.charAt(0));
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
        DFS2(caveLookup.get("start"), new ArrayList<>(), allPaths, true);
        System.out.println("Answer: " + allPaths.size());
    }

    public static void DFS2(Cave cave, List<String> currentPathParts, List<String> allPaths, boolean allowRevisiting) {
        currentPathParts.add(cave.toString());
        if (cave.isSmall) {
            cave.visitsCount++;
        }
        if (cave.name.equals("end")) {
            allPaths.add(String.join(",", currentPathParts));
        } else {
            for (Cave neighbour : cave.neighbours) {
                if (!neighbour.name.equals("start")) {
                    if (allowRevisiting) {
                        if (neighbour.visitsCount == 0) {
                            // Calling DFS2() will visit neighbour for the first time, so revisiting should be allowed.
                            DFS2(neighbour, currentPathParts, allPaths, true);
                        } else if (neighbour.visitsCount == 1) {
                            // Calling DFS2() will revisit neighbour, so revisiting should not be allowed anymore.
                            DFS2(neighbour, currentPathParts, allPaths, false);
                        }
                        if (neighbour.isSmall) {
                            neighbour.visitsCount--;
                        }
                    } else if (neighbour.visitsCount == 0)/* We didn't visit this node before but our coupon for revisiting is expired */ {
                        DFS2(neighbour, currentPathParts, allPaths, false);
                        neighbour.visitsCount = 0;
                    }
                }
            }
        }
        currentPathParts.remove(currentPathParts.size() - 1);
    }
}