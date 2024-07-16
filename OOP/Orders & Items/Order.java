import java.util.ArrayList;

public class Order {
    String name;
    double total;
    boolean ready;
    ArrayList<Item> items;

    public Order() {
        this.items = new ArrayList<Item>();
        this.total = 0.0;
        this.ready = false;
    }
}
