package day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    private final static int SCORE_WIN = 6;
    private final static int SCORE_DRAW = 3;
    private final static int SCORE_LOSS = 0;

    /**
     * Possible choices in a rock-paper-scissor-game.
     */
    public enum Choice {
        ROCK(1), PAPER(2), SCISSOR(3);

        private final int score;

        Choice(int score) {
            this.score = score;
        }

        /**
         * Get the choice that will loose against this
         * @return the corresponding choice
         */
        public Choice getLoosing() {
            if (this == ROCK) {
                return SCISSOR;
            }
            if (this == PAPER) {
                return ROCK;
            }
            if (this == SCISSOR) {
                return PAPER;
            }
            else {
                throw new IllegalArgumentException("Cannot process choice " + score);
            }
        }

        /**
         * Get the choice that will win against this
         * @return the corresponding choice
         */
        public Choice getWinning() {
            if (this == ROCK) {
                return PAPER;
            }
            if (this == PAPER) {
                return SCISSOR;
            }
            if (this == SCISSOR) {
                return ROCK;
            }
            else {
                throw new IllegalArgumentException("Cannot process choice " + score);
            }
        }

        /**
         * Get the score of part 1, where the opponent choice is given and our choice is this
         * @param opponent the choice of the opponent
         * @return the score for part 1
         */
        public int getScore(Choice opponent) {
            if (opponent == this) {
                return score + SCORE_DRAW;
            }
            // is it a win?
            if (opponent == this.getLoosing()) {
                return score + SCORE_WIN;
            }
            // Last case, we loose
            return score;
        }

        /**
         * Get the result for part 2
         * @param value the value to choose the response
         * @return the result for part 2
         */
        public Choice getResponse (String value) {
            if (value.equals("X")) {
                // we need to loose
                return this.getLoosing();
            }
            if (value.equals("Y")) {
                return this;
            }
            return this.getWinning();
        }


    }

    /**
     * Translate string encoding into choice
     * @param choiceString the encoding from the input file
     * @return the corresponding {@link Main.Choice}
     */
    private static Choice getChoice(String choiceString) {
        if (choiceString.equals("A") || choiceString.equals("X")) {
            return Choice.ROCK;
        }
        if (choiceString.equals("B") || choiceString.equals("Y")) {
            return Choice.PAPER;
        }
        if (choiceString.equals("C") || choiceString.equals("Z")) {
            return Choice.SCISSOR;
        }
        else {
            throw new IllegalArgumentException("Cannot process choiceString " + choiceString);
        }
    }


    public static void main(String[] args) throws IOException {
//        String inputFile = "in/2-example.txt";
        String inputFile = "in/2.txt";
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line;
        int partOneScore = 0;
        int partTwoScore = 0;
        while ((line = reader.readLine()) != null) {
            line = line.strip();
            if (line.isEmpty()) {
                continue;
            }
            String[] values = line.split(" ");
            Choice opponentChoice = getChoice(values[0]);
            Choice myChoice = getChoice(values[1]);
            partOneScore += myChoice.getScore(opponentChoice);
            Choice partTwoChoice = opponentChoice.getResponse(values[1]);
            partTwoScore += partTwoChoice.getScore(opponentChoice);
        }
        System.out.println("Part 1 score: " + partOneScore);
        System.out.println("Part 2 score: " + partTwoScore);
    }
}
