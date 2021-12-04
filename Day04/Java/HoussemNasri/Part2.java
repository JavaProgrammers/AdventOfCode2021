package tech.houssemnasri;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Part2 {

    private static void mark(List<List<List<Integer>>> boards, Integer card, List<List<Integer>> marks) {
        for (int i = 0; i < boards.size(); i++) {
            List<List<Integer>> board = boards.get(i);
            if (marks.get(i) == null) {
                marks.set(i, new ArrayList<>());
            }
            List<Integer> boardMarks = marks.get(i);
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    if (board.get(j).get(k).equals(card)) {
                        int index = 5 * j + k;
                        boardMarks.add(index);
                    }
                }
            }
        }
    }

    public static void run(String inputPath) {
        try {
            List<String> lines = Files.readAllLines(Path.of(inputPath));
            List<Integer> cards = Arrays.stream(lines.get(0).split(",")).map(Integer::valueOf).collect(Collectors.toList());
            List<List<List<Integer>>> boards = readBoards(lines);
            List<List<Integer>> marks = new ArrayList<>();
            List<Integer> wonBoards = new ArrayList<>();
            IntStream.range(0, boards.size()).forEach(ii -> marks.add(new ArrayList<>()));
            int unmarkedSum = 0;
            int lastToWinCard = 0;
            mainLoop:
            for (Integer card : cards) {
                mark(boards, card, marks);
                List<List<List<Integer>>> ijMarks = new ArrayList<>();
                for (List<Integer> mark : marks) {
                    ijMarks.add(mark.stream().map(m -> List.of(m / 5, m % 5)).collect(Collectors.toList()));
                }

                for (int ii = 0; ii < ijMarks.size(); ii++) {
                    List<List<Integer>> coordMark = ijMarks.get(ii);
                    for (int j = 0; j < 5; j++) {
                        int jj = j;
                        long inRowCount = coordMark.stream().map(coo -> coo.get(0)).filter(x -> x == jj).count();
                        long inColCount = coordMark.stream().map(coo -> coo.get(1)).filter(x -> x == jj).count();
                        if (inRowCount == 5 || inColCount == 5) {
                            if (wonBoards.contains(ii)) {
                                break;
                            }
                            wonBoards.add(ii);
                            lastToWinCard = card;

                            System.out.println("Bingo! " + card);
                            List<Integer> mark = marks.get(ii);
                            List<List<Integer>> thisBoard = boards.get(ii);

                            unmarkedSum = IntStream.range(0, 25)
                                                   .filter(n -> !mark.contains(n))
                                                   .map(s -> thisBoard.get(s / 5).get(s % 5)).sum();
                            if(wonBoards.size() == boards.size())
                                break mainLoop;
                        }
                    }
                }
            }
            System.out.println("Result: " + (lastToWinCard * unmarkedSum));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<List<List<Integer>>> readBoards(List<String> lines) {
        List<List<List<Integer>>> boards = new ArrayList<>();
        int i = 2;
        while (i < lines.size()) {
            List<List<Integer>> board = new ArrayList<>();
            int j = 0;
            while (j < 5) {
                String boardLine = lines.get(i + j);
                board.add(Arrays.stream(boardLine.split("\s+")).filter(w -> !w.isBlank())
                                .map(Integer::valueOf).collect(Collectors.toList()));
                j++;
            }
            i = i + 6;
            boards.add(board);
        }
        return boards;
    }
}
