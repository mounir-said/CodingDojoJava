<<<<<<< HEAD
public class TestOrders {
    public static void main(String[] args) {
        // Menu items
        Item item1 = new Item();
        item1.name = "mocha";
        item1.price = 3.5;

        Item item2 = new Item();
        item2.name = "latte";
        item2.price = 4.0;

        Item item3 = new Item();
        item3.name = "drip coffee";
        item3.price = 2.5;

        Item item4 = new Item();
        item4.name = "cappuccino";
        item4.price = 3.0;

        // Order variables
        Order order1 = new Order();
        order1.name = "Cindhuri";

        Order order2 = new Order();
        order2.name = "Jimmy";

        Order order3 = new Order();
        order3.name = "Noah";

        Order order4 = new Order();
        order4.name = "Sam";

        // Application Simulations
        System.out.printf("Name: %s\n", order1.name);
        System.out.printf("Total: %.2f\n", order1.total);
        System.out.printf("Ready: %s\n", order1.ready);

        // Add item1 to order2's item list and increment the order's total
        order2.items.add(item1);
        order2.total += item1.price;

        // Noah ordered a cappuccino
        order3.items.add(item4);
        order3.total += item4.price;

        // Sam added a latte
        order4.items.add(item2);
        order4.total += item2.price;

        // Cindhuri’s order is now ready
        order1.ready = true;

        // Sam ordered more drinks - 2 lattes
        order4.items.add(item2);
        order4.items.add(item2);
        order4.total += item2.price * 2;

        // Jimmy’s order is now ready
        order2.ready = true;

        // Print orders to verify
        printOrder(order1);
        printOrder(order2);
        printOrder(order3);
        printOrder(order4);
    }

    public static void printOrder(Order order) {
        System.out.printf("Order for %s\n", order.name);
        System.out.printf("Total: %.2f\n", order.total);
        System.out.printf("Ready: %s\n", order.ready);
        System.out.println("Items:");
        for (Item item : order.items) {
            System.out.printf("- %s: %.2f\n", item.name, item.price);
        }
        System.out.println("****************************");
    }
}
=======
public class TestOrders {
    public static void main(String[] args) {
        // Menu items
        Item item1 = new Item();
        item1.name = "mocha";
        item1.price = 3.5;

        Item item2 = new Item();
        item2.name = "latte";
        item2.price = 4.0;

        Item item3 = new Item();
        item3.name = "drip coffee";
        item3.price = 2.5;

        Item item4 = new Item();
        item4.name = "cappuccino";
        item4.price = 3.0;

        // Order variables
        Order order1 = new Order();
        order1.name = "Cindhuri";

        Order order2 = new Order();
        order2.name = "Jimmy";

        Order order3 = new Order();
        order3.name = "Noah";

        Order order4 = new Order();
        order4.name = "Sam";

        // Application Simulations
        System.out.printf("Name: %s\n", order1.name);
        System.out.printf("Total: %.2f\n", order1.total);
        System.out.printf("Ready: %s\n", order1.ready);

        // Add item1 to order2's item list and increment the order's total
        order2.items.add(item1);
        order2.total += item1.price;

        // Noah ordered a cappuccino
        order3.items.add(item4);
        order3.total += item4.price;

        // Sam added a latte
        order4.items.add(item2);
        order4.total += item2.price;

        // Cindhuri’s order is now ready
        order1.ready = true;

        // Sam ordered more drinks - 2 lattes
        order4.items.add(item2);
        order4.items.add(item2);
        order4.total += item2.price * 2;

        // Jimmy’s order is now ready
        order2.ready = true;

        // Print orders to verify
        printOrder(order1);
        printOrder(order2);
        printOrder(order3);
        printOrder(order4);
    }

    public static void printOrder(Order order) {
        System.out.printf("Order for %s\n", order.name);
        System.out.printf("Total: %.2f\n", order.total);
        System.out.printf("Ready: %s\n", order.ready);
        System.out.println("Items:");
        for (Item item : order.items) {
            System.out.printf("- %s: %.2f\n", item.name, item.price);
        }
        System.out.println("****************************");
    }
}
>>>>>>> c476ca249ee6d09e22b01edece3f1b50367937bd
