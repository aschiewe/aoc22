package day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
//        String fileName = "in/5-example.txt";
        String fileName = "in/5.txt";
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        HashMap<Integer, List<Character>> lists = new HashMap<>();
        // First, parse the drawing
        while (true) {
            line = reader.readLine();
            System.out.println("Line: " + line);
            if (line.isBlank()) {
                // Found the end of the drawing, move to instruction parsing
                System.out.println("Is blank");
                break;
            }
            // Iterate the line of the drawing and add the entries according to their position
            char[] entries = line.toCharArray();
            System.out.println("Length of char array: " + entries.length);
            for (int i = 0; i < entries.length/4 + 1; i++) {
                System.out.println("Check position " + entries[4*i+1]);
                // Check position 4*i+1, there could be a character that belongs to stack (i+1)
                if (entries[4*i+1] != ' ') {
                    lists.computeIfAbsent(i + 1, j -> new ArrayList<>()).add(entries[4*i+1]);
                }
            }
        }
        // For every list, remove the last element (the index)
        lists.values().forEach(l -> l.remove(l.size() - 1));
        // Now, convert the lists to stacks to further work with
        lists.values().forEach(Collections::reverse);
        // Create separate stacks for the different operating cranes in part one and two
        HashMap<Integer, Stack<Character>> stacksPartOne = new HashMap<>();
        HashMap<Integer, Stack<Character>> stacksPartTwo = new HashMap<>();
        for (int i = 1; i <= lists.size(); i++) {
            stacksPartOne.put(i, new Stack<>());
            stacksPartOne.get(i).addAll(lists.get(i));
            stacksPartTwo.put(i, new Stack<>());
            stacksPartTwo.get(i).addAll(lists.get(i));
        }
        // Now, parse the instructions
        Stack<Character> tempStack = new Stack<>();
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(" ");
            int amount = Integer.parseInt(values[1]);
            int startStack = Integer.parseInt(values[3]);
            int endStack = Integer.parseInt(values[5]);
            // Parse part one
            for (int pack = 0; pack < amount; pack++) {
                char content = stacksPartOne.get(startStack).pop();
                stacksPartOne.get(endStack).push(content);
            }
            // Parse part two
            // First, move everything to a temporary stack, then to the final stack. This will maintain the order
            // of the packages
            tempStack.clear();
            for (int pack = 0; pack < amount; pack++) {
                char content = stacksPartTwo.get(startStack).pop();
                tempStack.push(content);
            }
            for (int pack = 0; pack < amount; pack++) {
                char content = tempStack.pop();
                stacksPartTwo.get(endStack).push(content);
            }

        }
        // Read out the end result for part 1
        StringBuilder builderPartOne = new StringBuilder();
        StringBuilder builderPartTwo = new StringBuilder();
        for(int i = 1; i <= stacksPartOne.size(); i++) {
            builderPartOne.append(stacksPartOne.get(i).peek());
            builderPartTwo.append(stacksPartTwo.get(i).peek());
        }
        System.out.println("Result for part 1: " + builderPartOne);
        System.out.println("Result for part 2: " + builderPartTwo);
        for (Map.Entry<Integer, Stack<Character>> stackEntry: stacksPartOne.entrySet()) {
            System.out.println(stackEntry);
        }
    }
}
