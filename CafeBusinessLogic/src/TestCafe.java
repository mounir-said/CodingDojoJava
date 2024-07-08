import java.util.ArrayList;
import java.util.Arrays;

public class TestCafe {
    public static void main(String[] args) {

        // Create an instance of CafeUtil
        CafeUtil appTest = new CafeUtil();

        // Test getStreakGoal method
        System.out.println("\n----- Streak Goal Test -----");
        System.out.printf("Purchases needed by week 10: %s \n\n", appTest.getStreakGoal());

        // Test getStreakGoal method with parameter
        System.out.println("----- Streak Goal with Parameter Test -----");
        System.out.printf("Purchases needed by week 15: %s \n\n", appTest.getStreakGoal(15));

        // Test getOrderTotal method
        System.out.println("----- Order Total Test-----");
        double[] lineItems = {3.5, 1.5, 4.0, 4.5};
        System.out.printf("Order total: %s \n\n", appTest.getOrderTotal(lineItems));

        // Test displayMenu method
        System.out.println("----- Display Menu Test-----");
        ArrayList<String> menu = new ArrayList<String>();
        menu.add("drip coffee");
        menu.add("cappuccino");
        menu.add("latte");
        menu.add("mocha");
        appTest.displayMenu(menu);

        // Test addCustomer method
        System.out.println("\n----- Add Customer Test-----");
        ArrayList<String> customers = new ArrayList<String>();
        // Test 4 times
        for (int i = 0; i < 4; i++) {
            appTest.addCustomer(customers);
            System.out.println("\n");
        }

        // Test printPriceChart method
        System.out.println("\n----- Print Price Chart Test-----");
        appTest.printPriceChart("Colombian Coffee Grounds", 15.0, 3);

        // Test overloaded displayMenu method
        System.out.println("\n----- Overloaded Display Menu Test-----");
        ArrayList<Double> prices = new ArrayList<>(Arrays.asList(1.5, 3.5, 4.5, 3.5));
        boolean isMenuDisplayed = appTest.displayMenu(menu, prices);
        System.out.println("Menu displayed: " + isMenuDisplayed);

        // Test addCustomers method
        System.out.println("\n----- Add Customers Test-----");
        ArrayList<String> customerList = new ArrayList<String>();
        appTest.addCustomers(customerList);
    }
}
