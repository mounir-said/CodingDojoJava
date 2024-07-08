import java.util.ArrayList;

public class TestPuzzleJava {
    
    public static void main(String[] args) {
        PuzzleJava generator = new PuzzleJava();

        // Test getTenRolls method
        ArrayList<Integer> randomRolls = generator.getTenRolls();
        System.out.println("Random Rolls: " + randomRolls);

        // Test getRandomLetter method
        char randomLetter = generator.getRandomLetter();
        System.out.println("Random Letter: " + randomLetter);

        // Test generatePassword method
        String password = generator.generatePassword();
        System.out.println("Generated Password: " + password);

        // Test getNewPasswordSet method
        ArrayList<String> passwordSet = generator.getNewPasswordSet(5);
        System.out.println("Password Set: " + passwordSet);

        // Test shuffleArray method (Ninja Bonus)
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println("Array before shuffle: " + java.util.Arrays.toString(array));
        generator.shuffleArray(array);
        System.out.println("Array after shuffle: " + java.util.Arrays.toString(array));
    }
}
