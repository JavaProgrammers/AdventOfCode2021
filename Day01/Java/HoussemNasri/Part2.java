package tech.houssemnasri.HoussemNasri;

import java.util.List;

public class Part2 implements Runnable {
    @Override
    public void run() {
        List<Integer> depths = Utils.readIntegerInputs();
        int previousSum = depths.get(0) + depths.get(1) + depths.get(2);
        int increasedCount = 0;
        for (int i = 3; i < depths.size(); i++) {
            int newSum = previousSum + depths.get(i) - depths.get(i - 3);
            if (newSum > previousSum) {
                increasedCount++;
            }
        }
        System.out.println("Part Two Answer: " + increasedCount);
    }
}
