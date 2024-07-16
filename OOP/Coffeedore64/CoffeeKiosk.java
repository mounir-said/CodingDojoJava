import java.util.ArrayList;
import java.util.Scanner;

public class CoffeeKiosk {
    private ArrayList<Item> menu;
    private ArrayList<Order> orders;

    public CoffeeKiosk() {
        this.menu = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public void addMenuItem(String name, double price) {
        Item newItem = new Item(name, price);
        newItem.setIndex(this.menu.size());
        this.menu.add(newItem);
    }

    public void displayMenu() {
        for (Item item : this.menu) {
            System.out.println(item.getIndex() + " " + item.getName() + " -- $" + item.getPrice());
        }
    }

    public void newOrder() {
        try (Scanner scanner = new Scanner(System.in)) {
          System.out.println("Please enter customer name for new order:");
          String name = scanner.nextLine();
          
          Order newOrder = new Order(name);
          
          displayMenu();
          
          System.out.println("Please enter a menu item index or q to quit:");
          String itemNumber = scanner.nextLine();
          
          while (!itemNumber.equals("q")) {
              try {
                  int itemIndex = Integer.parseInt(itemNumber);
                  if (itemIndex >= 0 && itemIndex < this.menu.size()) {
                      Item orderedItem = this.menu.get(itemIndex);
                      newOrder.addItem(orderedItem);
                      System.out.println("Added " + orderedItem.getName() + " to the order.");
                  } else {
                      System.out.println("Invalid item index. Please try again.");
                  }
              } catch (NumberFormatException e) {
                  System.out.println("Invalid input. Please enter a valid item index or 'q' to quit.");
              }
              
              System.out.println("Please enter a menu item index or q to quit:");
              itemNumber = scanner.nextLine();
          }
          
          this.orders.add(newOrder);
          newOrder.display();
        }
    }

    // Ninja Bonus
    public void addMenuItemByInput() {
        try (Scanner scanner = new Scanner(System.in)) {
          System.out.println("Enter the name of the new menu item:");
          String name = scanner.nextLine();
          
          System.out.println("Enter the price of the new menu item:");
          double price = scanner.nextDouble();
          
          addMenuItem(name, price);
          System.out.println("Added new menu item: " + name + " -- $" + price);
        }
    }

    public ArrayList<Item> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<Item> menu) {
        this.menu = menu;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
