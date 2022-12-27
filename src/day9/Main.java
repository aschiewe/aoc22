package day9;

import util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Main {

    enum DIRECTION {
        RIGHT ("R"),
        LEFT ("L"),
        DOWN("D"),
        UP("U");

        private String representation;

        DIRECTION(String direction) {
            this.representation = direction;
        }

        static DIRECTION get(String representation) {
            for (DIRECTION direction: DIRECTION.values()) {
                if (representation.equals(direction.representation)) {
                    return direction;
                }
            }
            throw new RuntimeException();
        }
    }
    public static void main(String[] args) throws IOException {
//        String fileName = "in/9-example.txt";
        String fileName = "in/9.txt";

        int numberOfKnots = 10;
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        HashMap<Integer, Pair<Integer, Integer>> knotPositions = new HashMap<>();
        for (int i = 0; i < numberOfKnots; i++) {
            knotPositions.put(i, new Pair<>(0, 0));
        }
        HashSet<Pair<Integer, Integer>> visitedPositions = new HashSet<>();
        // do not need to add 0,0, this will always be the tail position after the first move
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(" ");
            DIRECTION direction = DIRECTION.get(values[0]);
            int steps = Integer.parseInt(values[1]);
            for (int i = 0; i < steps; i++) {
                // First, determine the movement of the head
                knotPositions.put(0, determineNextHeadPosition(knotPositions.get(0), direction));
                // Now, iterate through the other knots
                for (int knot = 1; knot < knotPositions.size(); knot++) {
                    knotPositions.put(knot, determineNextTailPosition(knotPositions.get(knot - 1), knotPositions.get(knot)));
                }
                // Lastly, add the tail to the visited positions
                visitedPositions.add(knotPositions.get(knotPositions.size() - 1));
            }
        }
        System.out.println("Visited " + visitedPositions.size() + " positions.");

    }

    private static Pair<Integer, Integer> determineNextTailPosition (Pair<Integer, Integer> head, Pair<Integer, Integer> tail) {
        int xDelta = head.getFirstEntry() - tail.getFirstEntry();
        int yDelta = head.getSecondEntry() - tail.getSecondEntry();
        if (Math.abs(xDelta) == 1 && Math.abs(yDelta) == 1) {
            // Diagonal, but touching
            return tail;
        }
        if (Math.abs(xDelta) +  Math.abs(yDelta) <= 1) {
            // Horizontal moved, but only one square
            return tail;
        }
        // Otherwise, move in every direction with positive delta, but maximal one step
        int newXPosition = xDelta == 0? tail.getFirstEntry() : tail.getFirstEntry() + xDelta / Math.abs(xDelta);
        int newYPosition = yDelta == 0? tail.getSecondEntry() : tail.getSecondEntry() + yDelta / Math.abs(yDelta);
        return new Pair<>(newXPosition, newYPosition);
    }

    private static Pair<Integer, Integer> determineNextHeadPosition(Pair<Integer, Integer> head, DIRECTION direction) {
        switch (direction) {
            case RIGHT -> {
                return new Pair<>(head.getFirstEntry()+1, head.getSecondEntry());
            }
            case UP -> {
                return new Pair<>(head.getFirstEntry(), head.getSecondEntry() + 1);
            }
            case DOWN -> {
                return new Pair<>(head.getFirstEntry(), head.getSecondEntry() - 1);
            }
            case LEFT -> {
                return new Pair<>(head.getFirstEntry() - 1, head.getSecondEntry());
            }
            default -> throw new RuntimeException();
        }
    }
}
