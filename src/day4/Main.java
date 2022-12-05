package day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        String fileName = "in/4-example.txt";
        String fileName = "in/4.txt";
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        int scorePart1 = 0;
        int scorePart2 = 0;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            // Parse the intervals
            int lowerBound1 = Integer.parseInt(values[0].split("-")[0]);
            int upperBound1 = Integer.parseInt(values[0].split("-")[1]);
            int lowerBound2 = Integer.parseInt(values[1].split("-")[0]);
            int upperBound2 = Integer.parseInt(values[1].split("-")[1]);
            // Determine intersection
            if (lowerBound1 <= lowerBound2 && upperBound1 >= upperBound2 ||
                lowerBound1 >= lowerBound2 && upperBound1 <= upperBound2) {
                // Found two containing sections
                scorePart1 += 1;
                scorePart2 += 1;
            }
            else if (lowerBound2 <= upperBound1 && lowerBound1 <= upperBound2) {
                // Found an intersection
                scorePart2 += 1;
            }
        }
        System.out.println("Score part 1: " + scorePart1);
        System.out.println("Score part 2: " + scorePart2);
    }
}
