package tech.houssemnasri;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Part1 {

    static long bruteforce(int days, List<Integer> initialTimers) {
        List<Integer> timers = new ArrayList<>(initialTimers);
        int newFishCount = 0;
        System.out.println(timers);
        for (int i = 0; i < days + 1; i++) {
            for (int f = 0; f < newFishCount; f++) {
                timers.add(8);
            }
            newFishCount = (int) timers.stream().filter(t -> t == 0).count();

            timers = timers.stream().map(t -> (t == 0 ? 6 : t - 1)).collect(Collectors.toList());
        }
        return timers.size();
    }

    public static void run(String inputPath) {
        try {
            List<Integer> timers = Arrays.stream(new BufferedReader(new FileReader(Path.of(inputPath).toFile()))
                    .readLine()
                    .split(",")).map(Integer::valueOf).collect(Collectors.toList());

            System.out.println("STUPID: " + bruteforce(80, timers));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
