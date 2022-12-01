package day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) throws IOException {
        String inputFileName = "in/1.txt";
        BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
        String line;
        int sum = 0;
        ArrayList<Integer> elfs = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            line = line.strip();
            // First, check if we have an empty line, i.e., a new elf is starting
            if (line.isEmpty()) {
                elfs.add(sum);
                sum = 0;
                continue;
            }
            // We are currently filling up an elf, parse and add to the pile
            int currentValue = Integer.parseInt(line);
            sum += currentValue;
        }
        // Create the last elf, since the file may not end with an empty line
        if (sum > 0) {
            elfs.add(sum);
        }
        // Sort the elfs by carried calories
        elfs.sort(Comparator.comparingInt(Integer::intValue).reversed());
        int topCalories = elfs.get(0);
        System.out.println("The elf carrying the most calories carries " + topCalories+ " calories");
        int topThreeCalories = elfs.stream().limit(3).mapToInt(Integer::intValue).sum();
        System.out.println("The three elves carrying the most calories carry " + topThreeCalories + " calories");
    }
}
