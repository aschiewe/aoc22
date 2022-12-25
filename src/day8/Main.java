package day8;

import util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        // First try just iterate the grid four times
        Set<Pair<Integer, Integer>> visibleTrees = new HashSet<>();
        ArrayList<List<Integer>> input = new ArrayList<>();
        String line;
//        String inputFile = "in/8-example.txt";
        String inputFile = "in/8.txt";
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        while ((line = reader.readLine()) != null) {
            String[] values = line.split("");
            input.add(new ArrayList<>(Arrays.stream(values).map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new))));
        }
        // Iterate the created grid and search for visible trees. Brute force is fast enough
        // First, iterate from the left
        for (int i = 0; i < input.size(); i++) {
            int biggestSize = -1;
            for (int j = 0; j < input.get(0).size(); j++) {
                if (input.get(i).get(j) > biggestSize) {
                    visibleTrees.add(new Pair<>(i, j));
                    biggestSize = input.get(i).get(j);
                }
            }
            // and the right
            biggestSize = -1;
            for (int j = input.get(0).size() - 1; j >= 0; j--) {
                if (input.get(i).get(j) > biggestSize) {
                    visibleTrees.add(new Pair<>(i, j));
                    biggestSize = input.get(i).get(j);
                }
            }
        }
        // Now, top to bottom
        for (int j = 0; j < input.get(0).size(); j++) {
            int biggestSize = -1;
            for (int i = 0; i < input.size(); i++) {
                if (input.get(i).get(j) > biggestSize) {
                    visibleTrees.add(new Pair<>(i, j));
                    biggestSize = input.get(i).get(j);
                }
            }
            // and bottom to top
            biggestSize = -1;
            for (int i = input.size() - 1; i >= 0; i--) {
                if (input.get(i).get(j) > biggestSize) {
                    visibleTrees.add(new Pair<>(i, j));
                    biggestSize = input.get(i).get(j);
                }
            }
        }

        System.out.println("Found " + visibleTrees.size() + " visible trees");

        // Again, just bruteforce try every tree position. All grid borders are never optimal.
        int bestObjective = 0;
        for (int i = 1; i < input.size() - 1; i++) {
            for (int j = 1; j < input.get(0).size() - 1; j++) {
                int candidateSize = input.get(i).get(j);
                int subSolutionObjective = 1;
                // Check all four directions, first i direction
                for (int iChange: new int[]{-1, 1}) {
                    int subSearchI = i;
                    int subCountTrees = 0;
                    while (subSearchI > 0 && subSearchI < input.size() - 1) {
                        subSearchI += iChange;
                        subCountTrees += 1;
                        // Check if we can still see
                        if (input.get(subSearchI).get(j) >= candidateSize) {
                            // Stop search here
                            break;
                        }
                    }
                    subSolutionObjective *= subCountTrees;
                }
                // and now the j direction
                for (int jChange: new int[]{-1, 1}) {
                    int subSearchJ = j;
                    int subCountTrees = 0;
                    while (subSearchJ > 0 && subSearchJ < input.get(0).size() - 1) {
                        subSearchJ += jChange;
                        subCountTrees += 1;
                        // Check if we can still see
                        if (input.get(i).get(subSearchJ) >= candidateSize) {
                            // Stop search here
                            break;
                        }
                    }
                    subSolutionObjective *= subCountTrees;
                }
                if (subSolutionObjective > bestObjective) {
                    bestObjective = subSolutionObjective;
                }
            }
        }
        System.out.println("Best objective for part two: " + bestObjective);



    }


}
