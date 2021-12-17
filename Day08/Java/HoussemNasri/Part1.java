package tech.houssemnasri;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class Part1 {

    public static void run(String inputPath) throws IOException {
        System.out.println(Files.readAllLines(Path.of(inputPath)).stream()
                                .map(l -> l.split(" \\| "))
                                .map(a -> a[1])
                                .map(s -> s.split("\s"))
                                .flatMap(Stream::of)
                                .map(String::length)
                                .filter(l -> List.of(2, 4, 3, 7).contains(l)).count());
    }
}
