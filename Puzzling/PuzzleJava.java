import java.util.ArrayList;
import java.util.Random;

public class PuzzleJava {

    // Method to generate an array with 10 random numbers between 1 and 20 inclusive
    public ArrayList<Integer> getTenRolls() {
        Random rand = new Random();
        ArrayList<Integer> rolls = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            rolls.add(rand.nextInt(20) + 1);
        }
        return rolls;
    }

    // Method to get a random letter from the alphabet
    public char getRandomLetter() {
        Random rand = new Random();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        return alphabet[rand.nextInt(26)];
    }

    // Method to generate a random 8-character password
    public String generatePassword() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            password.append(getRandomLetter());
        }
        return password.toString();
    }

    // Method to create an array of random 8-character passwords
    public ArrayList<String> getNewPasswordSet(int length) {
        ArrayList<String> passwordSet = new ArrayList<String>();
        for (int i = 0; i < length; i++) {
            passwordSet.add(generatePassword());
        }
        return passwordSet;
    }

    // Ninja Bonus: Method to shuffle an array
    public void shuffleArray(int[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
