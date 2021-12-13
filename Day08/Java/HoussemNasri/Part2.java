package tech.houssemnasri;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Part2 {

    private static Map<Character, Character> generateSegmentMapper(List<String> signalPatterns) {
        Map<Integer, List<Character>> easyDigitsMapper = generateEasyDigitMapper(signalPatterns);
        Map<Integer, List<String>> segmentCountMapper = generateSegmentCountMapper(signalPatterns);
        Map<Character, Character> result = new HashMap<>();

        List<Character> _in7ButNotIn1 = new ArrayList<>(easyDigitsMapper.get(7));
        _in7ButNotIn1.removeAll(easyDigitsMapper.get(1));
        result.put(_in7ButNotIn1.get(0), 'a');

        for (String _6SegmentNumber : segmentCountMapper.get(6)) {
            List<Character> _in8ButNotIn_6SegmentNumber = new ArrayList<>(easyDigitsMapper.get(8));
            _in8ButNotIn_6SegmentNumber.removeAll(_6SegmentNumber.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
            if (easyDigitsMapper.get(1).contains(_in8ButNotIn_6SegmentNumber.get(0))) {
                easyDigitsMapper.put(6, _6SegmentNumber.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
                result.put(_in8ButNotIn_6SegmentNumber.get(0), 'c');
                List<Character> f = new ArrayList<>(easyDigitsMapper.get(1));
                f.removeAll(_in8ButNotIn_6SegmentNumber);
                result.put(f.get(0), 'f');
            }
        }
        // 0 or 9
        List<String> len6 = new ArrayList<>(segmentCountMapper.get(6));
        len6.remove(easyDigitsMapper.get(6).stream().map(String::valueOf).collect(Collectors.joining()));

        List<Character> nineOrZero1 = len6.get(0).chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        List<Character> nineOrZero2 = len6.get(1).chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        List<Character> intersectionNineOrZero = new ArrayList<>(nineOrZero1);
        intersectionNineOrZero.retainAll(nineOrZero2);
        List<Character> notIntersectionNineOrZero = new ArrayList<>(nineOrZero1);
        notIntersectionNineOrZero.addAll(nineOrZero2);
        notIntersectionNineOrZero.removeAll(intersectionNineOrZero);

        List<Character> intersectionWith4 = new ArrayList<>(easyDigitsMapper.get(4));
        intersectionWith4.retainAll(notIntersectionNineOrZero);
        if (intersectionWith4.contains(notIntersectionNineOrZero.get(0))) {
            result.put(notIntersectionNineOrZero.get(0), 'd');
            result.put(notIntersectionNineOrZero.get(1), 'e');
            easyDigitsMapper.put(9, nineOrZero1);
            easyDigitsMapper.put(0, nineOrZero2);
        } else {
            result.put(notIntersectionNineOrZero.get(1), 'd');
            result.put(notIntersectionNineOrZero.get(0), 'e');
            easyDigitsMapper.put(0, nineOrZero1);
            easyDigitsMapper.put(9, nineOrZero2);
        }

        // 9 and 4
        List<Character> _in9AndNotIn4And7 = new ArrayList<>(easyDigitsMapper.get(9));
        _in9AndNotIn4And7.removeAll(easyDigitsMapper.get(4));
        _in9AndNotIn4And7.removeAll(easyDigitsMapper.get(7));
        result.put(_in9AndNotIn4And7.get(0), 'g');

        List<Character> lastKey = new ArrayList<>(List.of('a', 'b', 'c', 'd', 'e', 'f', 'g'));
        List<Character> lastValue = new ArrayList<>(List.of('a', 'b', 'c', 'd', 'e', 'f', 'g'));
        lastKey.removeAll(new ArrayList<>(result.keySet()));
        lastValue.removeAll(new ArrayList<>(result.values()));
        result.put(lastKey.get(0), lastValue.get(0));
        return result;
    }

    private static Map<Integer, List<String>> generateSegmentCountMapper(List<String> signalPattern) {
        Map<Integer, List<String>> res = new HashMap<>();
        signalPattern.forEach(pat -> {
            if (!res.containsKey(pat.length())) {
                res.put(pat.length(), new ArrayList<>());
            }
            res.get(pat.length()).add(pat);
        });
        return res;
    }

    // For Mapping digits with unique number of segments
    private static Map<Integer, List<Character>> generateEasyDigitMapper(List<String> signalPatterns) {
        Map<Integer, List<Character>> result = new HashMap<>();
        signalPatterns.forEach(pat -> {
            switch (pat.length()) {
                case 2 -> result.put(1, pat.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
                case 4 -> result.put(4, pat.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
                case 7 -> result.put(8, pat.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
                case 3 -> result.put(7, pat.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
            }
        });
        return result;
    }

    public static Map<String, Integer> patternToDigitMapper = Map.of(
            "abcefg", 0,
            "cf", 1,
            "acdeg", 2,
            "acdfg", 3,
            "bcdf", 4,
            "abdfg", 5,
            "abdefg", 6,
            "acf", 7,
            "abcdefg", 8,
            "abcdfg", 9
    );

    public static void run(String inputPath) throws IOException {
        List<String[]> entries = Files.readAllLines(Path.of(inputPath)).stream()
                                      .map(l -> l.split(" \\| "))
                                      .collect(Collectors.toList());

        int sum = 0;
        for (String[] entry : entries) {
            List<String> signalPatterns = Arrays.asList(entry[0].split("\s"));
            Map<Character, Character> segmentMapper = generateSegmentMapper(signalPatterns);
            System.out.println(segmentMapper);
            String badOutputValue = entry[1];
            StringBuilder correctValue = new StringBuilder();
            System.out.println(badOutputValue);

            for (String value : badOutputValue.split("\s")) {
                System.out.println(value);
                String sorted = value.chars().mapToObj(c -> (char) c).map(segmentMapper::get).sorted().map(String::valueOf).collect(Collectors.joining());
                correctValue.append(patternToDigitMapper.get(sorted));
            }
            sum += Integer.parseInt(correctValue.toString());
        }
        System.out.println("Sum: " + sum);
    }
}
