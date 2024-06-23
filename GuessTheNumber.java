import java.io.*;
import java.util.*;

public class GuessTheNumber {
    private static final int MAX_ATTEMPTS = 5;
    private static final int MAX_HIGH_SCORES = 5;
    private static final String HIGH_SCORES_FILE = "highscores.txt";

    private static int targetNumber;
    private static int numberOfAttempts;
    private static boolean hasGuessedCorrectly;
    private static ArrayList<Integer> highScores;

    public static void main(String[] args) {
        initializeGame();

        displayWelcomeMessage();
        playGame();

        displayHighScores();
        saveHighScores();

        System.out.println("Thank you for playing Guess the Number!");
    }

    private static void initializeGame() {
        Random random = new Random();
        targetNumber = random.nextInt(100) + 1;
        numberOfAttempts = 0;
        hasGuessedCorrectly = false;
        highScores = new ArrayList<>();

        loadHighScores();
    }

    private static void displayWelcomeMessage() {
        System.out.println("Welcome to Guess the Number Game!");
        System.out.println("I have selected a number between 1 and 100. Try to guess it.");
    }

    private static void playGame() {
        Scanner scanner = new Scanner(System.in);

        while (numberOfAttempts < MAX_ATTEMPTS && !hasGuessedCorrectly) {
            System.out.println("Attempt " + (numberOfAttempts + 1) + ": Enter your guess:");
            int userGuess = scanner.nextInt();
            numberOfAttempts++;

            if (userGuess == targetNumber) {
                System.out.println("Congratulations! You've guessed the correct number.");
                hasGuessedCorrectly = true;
            } else if (userGuess < targetNumber) {
                System.out.println("Your guess is too low. Try again.");
            } else {
                System.out.println("Your guess is too high. Try again.");
            }
        }

        scanner.close();
    }

    private static void loadHighScores() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HIGH_SCORES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                highScores.add(Integer.parseInt(line));
            }
            Collections.sort(highScores, Collections.reverseOrder());
        } catch (IOException e) {
            System.err.println("Error loading high scores: " + e.getMessage());
        }
    }

    private static void saveHighScores() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HIGH_SCORES_FILE))) {
            for (int i = 0; i < Math.min(highScores.size(), MAX_HIGH_SCORES); i++) {
                writer.println(highScores.get(i));
            }
        } catch (IOException e) {
            System.err.println("Error saving high scores: " + e.getMessage());
        }
    }

    private static void displayHighScores() {
        System.out.println("\n--- High Scores ---");
        for (int i = 0; i < Math.min(highScores.size(), MAX_HIGH_SCORES); i++) {
            System.out.println((i + 1) + ". " + highScores.get(i));
        }
    }
}