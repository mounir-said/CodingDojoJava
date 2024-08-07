import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SimpleHashMap<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private List<LinkedList<Entry<K, V>>> table;
    private int size;
    private int capacity;
    private int threshold;

    public SimpleHashMap() {
        this.capacity = INITIAL_CAPACITY;
        this.threshold = (int) (capacity * LOAD_FACTOR);
        this.table = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            table.add(new LinkedList<>());
        }
    }

    private static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public void put(K key, V value) {
        int index = getIndex(key);
        LinkedList<Entry<K, V>> bucket = table.get(index);

        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        bucket.add(new Entry<>(key, value));
        size++;

        if (size > threshold) {
            resize();
        }
    }

    public V get(K key) {
        int index = getIndex(key);
        LinkedList<Entry<K, V>> bucket = table.get(index);

        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }

        return null;
    }

    public V remove(K key) {
        int index = getIndex(key);
        LinkedList<Entry<K, V>> bucket = table.get(index);

        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                V value = entry.value;
                bucket.remove(entry);
                size--;
                return value;
            }
        }

        return null;
    }

    public boolean containsKey(K key) {
        int index = getIndex(key);
        LinkedList<Entry<K, V>> bucket = table.get(index);

        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return true;
            }
        }

        return false;
    }

    public boolean containsValue(V value) {
        for (LinkedList<Entry<K, V>> bucket : table) {
            for (Entry<K, V> entry : bucket) {
                if (entry.value.equals(value)) {
                    return true;
                }
            }
        }

        return false;
    }

    private int getIndex(K key) {
        return (key == null) ? 0 : Math.abs(key.hashCode() % capacity);
    }

    private void resize() {
        capacity *= 2;
        threshold = (int) (capacity * LOAD_FACTOR);
        List<LinkedList<Entry<K, V>>> newTable = new ArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            newTable.add(new LinkedList<>());
        }

        for (LinkedList<Entry<K, V>> bucket : table) {
            for (Entry<K, V> entry : bucket) {
                int index = (entry.key == null) ? 0 : Math.abs(entry.key.hashCode() % capacity);
                newTable.get(index).add(entry);
            }
        }

        table = newTable;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args) {
        SimpleHashMap<String, Integer> map = new SimpleHashMap<>();
        map.put("One", 1);
        map.put("Two", 2);
        map.put("Three", 3);

        System.out.println("Size: " + map.size()); // Output: Size: 3
        System.out.println("Value for 'Two': " + map.get("Two")); // Output: Value for 'Two': 2
        System.out.println("Contains key 'Three': " + map.containsKey("Three")); // Output: Contains key 'Three': true
        System.out.println("Contains value 1: " + map.containsValue(1)); // Output: Contains value 1: true

        map.remove("Two");
        System.out.println("Size after removing 'Two': " + map.size()); // Output: Size after removing 'Two': 2
        System.out.println("Contains key 'Two': " + map.containsKey("Two")); // Output: Contains key 'Two': false
    }
}
