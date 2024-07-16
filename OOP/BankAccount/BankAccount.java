import java.util.Random;

public class BankAccount {
    // MEMBER VARIABLES
    private double checkingBalance;
    private double savingsBalance;
    private static int accounts = 0;
    private static double totalMoney = 0;
    private String accountNumber;

    // CONSTRUCTOR
    public BankAccount() {
        this.checkingBalance = 0;
        this.savingsBalance = 0;
        this.accountNumber = generateAccountNumber();
        accounts++;
    }

    // GETTERS
    public double getCheckingBalance() {
        return checkingBalance;
    }

    public double getSavingsBalance() {
        return savingsBalance;
    }

    public static int getAccounts() {
        return accounts;
    }

    public static double getTotalMoney() {
        return totalMoney;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    // METHODS
    public void deposit(double amount, String accountType) {
        if (accountType.equals("checking")) {
            checkingBalance += amount;
        } else if (accountType.equals("savings")) {
            savingsBalance += amount;
        } else {
            System.out.println("Invalid account type. Please use 'checking' or 'savings'.");
            return;
        }
        totalMoney += amount;
    }

    public void withdraw(double amount, String accountType) {
        if (accountType.equals("checking")) {
            if (checkingBalance >= amount) {
                checkingBalance -= amount;
                totalMoney -= amount;
            } else {
                System.out.println("Insufficient funds in checking account.");
            }
        } else if (accountType.equals("savings")) {
            if (savingsBalance >= amount) {
                savingsBalance -= amount;
                totalMoney -= amount;
            } else {
                System.out.println("Insufficient funds in savings account.");
            }
        } else {
            System.out.println("Invalid account type. Please use 'checking' or 'savings'.");
        }
    }

    public double getBalance() {
        return checkingBalance + savingsBalance;
    }

    // PRIVATE METHOD TO GENERATE A RANDOM ACCOUNT NUMBER
    private String generateAccountNumber() {
        Random rand = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            accountNumber.append(rand.nextInt(10));
        }
        return accountNumber.toString();
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
