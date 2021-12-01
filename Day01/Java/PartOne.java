package tech.houssemnasri;

import java.util.List;

public class PartOne implements Runnable {
    @Override
    public void run() {
        List<Integer> depths = Utils.readIntegerInputs();
        int increasedCount = 0;
        for (int i = 1; i < depths.size(); i++) {
            if (depths.get(i).compareTo(depths.get(i - 1)) > 0) {
                increasedCount++;
            }
        }
        System.out.println("Part One Answer: " + increasedCount);
    }
}
