import java.util.Random;
import java.util.Scanner;

public class NumbersGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello, human. I am thinking of a number between 0 and 10.");
        System.out.println("*********************************************************");

        boolean playAgain = true;

        while (playAgain) {
            System.out.println("Can you guess the number?");
            System.out.println("If you are not up to the task, you can always type 'q' to quit.");

            int answer = new Random().nextInt(11);
            int attempts = 0;
            boolean guessedCorrectly = false;

            while (attempts < 3) {
                String guess = scanner.nextLine();

                if (guess.equals("q")) {
                    System.out.println("I knew you didn't have it in you.");
                    System.out.println("Shutting down...");
                    return;
                }

                try {
                    int guessedNumber = Integer.parseInt(guess);

                    if (guessedNumber < 0 || guessedNumber > 10) {
                        System.out.println("Please guess a number between 0 and 10.");
                        continue;
                    }

                    if (guessedNumber == answer) {
                        System.out.println("Lucky guess! But can you do it again?");
                        guessedCorrectly = true;
                        break;
                    } else {
                        System.out.println("Swing and a miss! Keep trying...");
                        attempts++;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number between 0 and 10 or 'q' to quit.");
                }
            }

            if (!guessedCorrectly) {
                System.out.println("You've used all your guesses. Better luck next time!");
            }

            System.out.println("Game over. Would you like to play again? (yes/no)");
            String response = scanner.nextLine();
            if (!response.equalsIgnoreCase("yes")) {
                playAgain = false;
                System.out.println("Shutting down...");
            }
        }

        scanner.close();
    }
}
