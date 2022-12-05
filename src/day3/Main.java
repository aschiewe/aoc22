package day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    /**
     * Convert chars to score based on the part 1 assignment, i.e., lower case letters score 1-26,
     * upper case letters score 27-52
     * @param input the char to convert
     * @return the value of the provided char
     */
    private static int convertToValue(char input) {
        if (input > 96) {
            // This is a lower case letter, i.e., asci value 97-122
            return input - 96;
        }
        // This is an upper case letter, i.e., asci value 65-90
        return input - 38;
    }

    public static void main(String[] args) throws IOException {
//        String fileName = "in/3-example.txt";
        String fileName = "in/3.txt";
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        // Sets for part 1
        HashSet<Character> firstCompartmentItems;
        HashSet<Character> secondCompartmentItems;
        // Sets for part 2
        ArrayList<HashSet<Character>> groupItemSets =
                new ArrayList<>(Arrays.asList(new HashSet<>(), new HashSet<>(), new HashSet<>()));
        int part1Score = 0;
        int part2Score = 0;
        while (true) {
            groupItemSets.forEach(HashSet::clear);
            // Always read the complete group
            for (int i = 0; i < 3; i++) {
                if ((line = reader.readLine()) != null) {
                    firstCompartmentItems = new HashSet<>();
                    secondCompartmentItems = new HashSet<>();
                    char[] charArray = line.toCharArray();
                    for (int index = 0; index < charArray.length / 2; index++) {
                        firstCompartmentItems.add(charArray[index]);
                        secondCompartmentItems.add(charArray[index + charArray.length / 2]);
                    }
                    // Add all items to the part 2 sets
                    groupItemSets.get(i).addAll(firstCompartmentItems);
                    groupItemSets.get(i).addAll(secondCompartmentItems);
                    // Find the intersection of the sets
                    firstCompartmentItems.retainAll(secondCompartmentItems);
                    // Convert to value
                    part1Score += convertToValue(firstCompartmentItems.stream().findAny().orElseThrow());
                }
                else {
                    // Done reading file, output scores
                    System.out.println("Part 1 score: " + part1Score);
                    System.out.println("Part 2 score: " + part2Score);
                    return;
                }
            }
            // Read the whole group, identify the common item
            groupItemSets.get(0).retainAll(groupItemSets.get(1));
            groupItemSets.get(0).retainAll(groupItemSets.get(2));
            // Convert to value
            part2Score += convertToValue(groupItemSets.get(0).stream().findAny().orElseThrow());
        }

    }
}
