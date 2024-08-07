public class DynamicArray {
  int size;
  int capacity = 10;
  Object[] array;

  public DynamicArray(){
      this.array = new Object[capacity];
  }

  public DynamicArray(int capacity){
      this.capacity = capacity;
      this.array = new Object[capacity];
  }

  public void add(Object data){
      if(size >= capacity){
          grow();
      }
      array[size] = data;
      size++;
  }

  public void insert(int index, Object data){
      if(index < 0 || index > size){
          throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      }
      if(size >= capacity){
          grow();
      }
      for(int i = size; i > index; i--){
          array[i] = array[i - 1];
      }
      array[index] = data;
      size++;
  }

  public void delete(Object data){
      int index = search(data);
      if(index == -1){
          throw new IllegalArgumentException("Element not found: " + data);
      }
      for(int i = index; i < size - 1; i++){
          array[i] = array[i + 1];
      }
      array[size - 1] = null;
      size--;
      if(size <= capacity / 3){
          shrink();
      }
  }

  public int search(Object data){
      for(int i = 0; i < size; i++){
          if(array[i].equals(data)){
              return i;
          }
      }
      return -1;
  }

  private void grow(){
      capacity *= 2;
      Object[] newArray = new Object[capacity];
      for(int i = 0; i < size; i++){
          newArray[i] = array[i];
      }
      array = newArray;
  }

  private void shrink(){
      capacity /= 2;
      Object[] newArray = new Object[capacity];
      for(int i = 0; i < size; i++){
          newArray[i] = array[i];
      }
      array = newArray;
  }

  public boolean isEmpty(){
      return size == 0;
  }

  public String toString(){
      String string = "";
      for (int i = 0; i < size; i++){
          string += array[i] + ", ";
      }
      if (!string.isEmpty()){
          string = "[" + string.substring(0, string.length() - 2) + "]";
      }
      return string;
  }

  public static void main(String[] args) {
      DynamicArray da = new DynamicArray();
      da.add(1);
      da.add(2);
      da.add(3);
      System.out.println(da); // Output: [1, 2, 3]

      da.insert(1, 4);
      System.out.println(da); // Output: [1, 4, 2, 3]

      da.delete(2);
      System.out.println(da); // Output: [1, 4, 3]

      System.out.println(da.search(4)); // Output: 1
      System.out.println(da.isEmpty()); // Output: false

      da.delete(1);
      da.delete(4);
      da.delete(3);
      System.out.println(da.isEmpty()); // Output: true
  }
}
