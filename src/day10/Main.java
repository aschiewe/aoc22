package day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        String inputFile = "in/10-example.txt";
        String inputFile = "in/10.txt";
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        int currentCycle = 1;
        int currentX = 1;
        int objective = 0;
        StringBuilder builder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            if (currentCycle % 40 == 1) {
                builder.append("\n");
            }
            if (currentCycle <= 220) {
                objective = computeObjective(objective, currentX, currentCycle);
            }
            // See if we need to draw something
            addToDrawing(builder, currentX, currentCycle);
            if (line.equals("noop")) {
                // Do nothing, just increase the cycle
                currentCycle += 1;
            }
            else {
                // First, compute the objective in the next step (nothing changed yet)
                if (currentCycle <= 220) {
                    objective = computeObjective(objective, currentX, currentCycle + 1);
                }
                if (currentCycle % 40 == 0) {
                    builder.append("\n");
                }
                addToDrawing(builder, currentX, currentCycle + 1);
                String[] values = line.split(" ");
                int amount = Integer.parseInt(values[1]);
                currentX += amount;
                currentCycle += 2;
            }
        }

        System.out.println("Objective: " + objective);
        System.out.println("Image produced: " + builder);
    }

    static int computeObjective(int oldObjective, int currentX, int currentCycle) {
        if (currentCycle % 40 == 20) {
            return oldObjective + currentCycle * currentX;
        }
        return oldObjective;
    }

    static void addToDrawing(StringBuilder builder, int currentX, int currentCycle) {
        // Pixle and cycle are moved by one and pixels are only in [0,39]
        int positionToDraw = (currentCycle - 1) % 40;
        if (Math.abs(currentX - positionToDraw) <= 1) {
            builder.append("#");
        }
        else {
            builder.append(".");
        }
    }
}
