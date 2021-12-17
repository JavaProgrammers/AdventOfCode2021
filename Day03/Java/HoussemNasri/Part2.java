package tech.houssemnasri;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Part2 {
    public static Set<Integer> getSet(int n) {
        return IntStream.range(0, n).boxed().collect(Collectors.toSet());
    }

    public static void run(String inputPath) {
        try {
            List<String> lines = Files.readAllLines(Path.of(inputPath));

            Set<Integer> oxygen = getSet(lines.size());
            int bitCount = lines.get(0).length();
            for (int i = 0; i < bitCount; i++) {
                Set<Integer> zerosSet = new HashSet<>();
                Set<Integer> onesSet = new HashSet<>();
                for (int o : oxygen) {
                    int bitValue = Integer.valueOf(lines.get(o), 2);
                    if ((bitValue & 1 << (bitCount - i - 1)) > 0) {
                        onesSet.add(o);
                    } else {
                        zerosSet.add(o);
                    }
                }

                int zeros = zerosSet.size();
                int ones = onesSet.size();

                if (ones >= zeros) {
                    oxygen.retainAll(onesSet);
                } else {
                    oxygen.retainAll(zerosSet);
                }
                System.out.println(oxygen);
                if (oxygen.size() == 1) {
                    break;
                }
            }
            int oxygenValue = Integer.parseInt(lines.get((Integer) oxygen.toArray()[0]), 2);

            Set<Integer> co2Set = getSet(lines.size());
            for (int i = 0; i < bitCount; i++) {
                Set<Integer> zerosSet = new HashSet<>();
                Set<Integer> onesSet = new HashSet<>();
                for (int co : co2Set) {
                    int bitValue = Integer.valueOf(lines.get(co), 2);
                    if ((bitValue & 1 << (bitCount - i - 1)) > 0) {
                        onesSet.add(co);
                    } else {
                        zerosSet.add(co);
                    }
                }

                int zeros = zerosSet.size();
                int ones = onesSet.size();

                if (ones >= zeros) {
                    co2Set.retainAll(zerosSet);
                } else {
                    co2Set.retainAll(onesSet);
                }
                System.out.println(co2Set);
                if (co2Set.size() == 1) {
                    break;
                }
            }
            int co2Value = Integer.parseInt(lines.get((Integer) co2Set.toArray()[0]), 2);

            System.out.println("Result : " + co2Value * oxygenValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
