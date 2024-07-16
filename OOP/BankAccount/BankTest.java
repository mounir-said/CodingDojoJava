public class BankTest {
  public static void main(String[] args) {
      // Create 3 bank accounts
      BankAccount account1 = new BankAccount();
      BankAccount account2 = new BankAccount();
      BankAccount account3 = new BankAccount();

      // Deposit Test
      account1.deposit(1000, "checking");
      System.out.println("Account 1 Checking Balance: " + account1.getCheckingBalance());
      account1.deposit(500, "savings");
      System.out.println("Account 1 Savings Balance: " + account1.getSavingsBalance());

      account2.deposit(2000, "checking");
      System.out.println("Account 2 Checking Balance: " + account2.getCheckingBalance());
      account2.deposit(1000, "savings");
      System.out.println("Account 2 Savings Balance: " + account2.getSavingsBalance());

      account3.deposit(3000, "checking");
      System.out.println("Account 3 Checking Balance: " + account3.getCheckingBalance());
      account3.deposit(1500, "savings");
      System.out.println("Account 3 Savings Balance: " + account3.getSavingsBalance());

      // Withdrawal Test
      account1.withdraw(200, "checking");
      System.out.println("Account 1 Checking Balance after withdrawal: " + account1.getCheckingBalance());
      account1.withdraw(100, "savings");
      System.out.println("Account 1 Savings Balance after withdrawal: " + account1.getSavingsBalance());

      account2.withdraw(300, "checking");
      System.out.println("Account 2 Checking Balance after withdrawal: " + account2.getCheckingBalance());
      account2.withdraw(200, "savings");
      System.out.println("Account 2 Savings Balance after withdrawal: " + account2.getSavingsBalance());

      account3.withdraw(400, "checking");
      System.out.println("Account 3 Checking Balance after withdrawal: " + account3.getCheckingBalance());
      account3.withdraw(300, "savings");
      System.out.println("Account 3 Savings Balance after withdrawal: " + account3.getSavingsBalance());

      // Static Test
      System.out.println("Total number of bank accounts: " + BankAccount.getAccounts());
      System.out.println("Total money in all accounts: " + BankAccount.getTotalMoney());
  }
}
