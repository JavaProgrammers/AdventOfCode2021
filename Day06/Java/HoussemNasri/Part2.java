package tech.houssemnasri;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Part2 {

    static Map<Integer, Long> memo = new HashMap<>();

    public static long gen(int startDay, int days) {
        long result = 0L;
        int genCount = ((days - startDay) / 7) + 1;
        if (memo.get(startDay) != null) {
            return memo.get(startDay);
        } else {
            for (int i = 0; i < genCount; i++) {
                int day = 7 * i + startDay + 9;
                if (day <= days) {
                    result = result + gen(day, days);
                }
            }
            memo.put(startDay, result + genCount);
            return result + genCount;
        }
    }

    public static void run(String inputPath) {
        try {
            List<Integer> timers = Arrays.stream(new BufferedReader(new FileReader(Path.of(inputPath).toFile()))
                    .readLine()
                    .split(",")).map(Integer::valueOf).collect(Collectors.toList());
            long smartCount = 0;
            for (int t : timers) {
                smartCount = smartCount + 1 + gen(t + 1, 256);
            }
            System.out.println("SMART: " + smartCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
