package tech.houssemnasri;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Part1 {

    public static void run(String inputPath) {
        try {
            Byte[][] pointsGrid = new Byte[1000][1000];
            Files.readAllLines(Path.of(inputPath)).forEach(line -> {
                String[] segmentPoints = line.split(" -> ");
                String[] point1 = segmentPoints[0].split(",");
                String[] point2 = segmentPoints[1].split(",");
                int x1 = Integer.parseInt(point1[0]);
                int y1 = Integer.parseInt(point1[1]);
                int x2 = Integer.parseInt(point2[0]);
                int y2 = Integer.parseInt(point2[1]);
                //Vertical
                if (x1 == x2) {
                    IntStream.range(Math.min(y1, y2), Math.max(y1, y2) + 1).forEach(i -> {
                        if (pointsGrid[i][x1] == null)
                            pointsGrid[i][x1] = 0;
                        pointsGrid[i][x1]++;
                    });
                } else if (y1 == y2) {
                    IntStream.range(Math.min(x1, x2), Math.max(x1, x2) + 1).forEach(i -> {
                        if (pointsGrid[y1][i] == null)
                            pointsGrid[y1][i] = 0;
                        pointsGrid[y1][i]++;
                    });
                }
            });
            System.out.println(Arrays.stream(pointsGrid).flatMap(Stream::of).filter(i -> i != null && i >= 2).count());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
