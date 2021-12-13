package tech.houssemnasri;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Part2 {
    static List<Integer[]> visited = new ArrayList<>();

    private static void expand(int pointX, int pointY, List<List<Integer>> map, List<Integer> basin) {
        if (pointX < 0 || pointX >= map.get(0).size() || pointY < 0 || pointY >= map.size() || map.get(pointY).get(pointX) == 9 ||
                containsPoint(visited, pointY, pointX)) {
            return;
        }
        visited.add(new Integer[] {pointY, pointX});
        basin.add(map.get(pointY).get(pointX));
        expand(pointX - 1, pointY, map, basin);
        expand(pointX + 1, pointY, map, basin);
        expand(pointX, pointY - 1, map, basin);
        expand(pointX, pointY + 1, map, basin);
    }

    public static void run(String inputPath) throws IOException {
        List<List<String>> heightMap = Files.readAllLines(Path.of(inputPath))
                                            .stream().map(s -> Arrays.asList(s.split(""))).collect(Collectors.toList());
        List<Integer[]> lowPoints = new ArrayList<>();
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
                    lowPoints.add(new Integer[] {i, j});
                }
            }
        }
        List<List<Integer>> heightMapMatrix = heightMap.stream().map(l -> l.stream().map(Integer::valueOf).collect(Collectors.toList())).collect(Collectors.toList());
        List<Integer> basinSizes = new ArrayList<>();
        for (Integer[] lowPoint : lowPoints) {
            visited.clear();
            List<Integer> basin = new ArrayList<>();
            expand(lowPoint[1], lowPoint[0], heightMapMatrix, basin);
            basinSizes.add(basin.size());
        }
        System.out.println("Basin Result: " + basinSizes.stream().sorted(Comparator.reverseOrder()).limit(3).reduce((x, y) -> x * y).get());
    }

    private static boolean containsPoint(List<Integer[]> lst, int py, int px) {
        for (Integer[] point : lst) {
            if (point[0] == py && point[1] == px) {
                return true;
            }
        }
        return false;
    }
}
