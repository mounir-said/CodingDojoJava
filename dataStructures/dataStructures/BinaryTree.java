public class BinaryTree {
  private Node root;

  private class Node {
      int data;
      Node left, right;

      Node(int data) {
          this.data = data;
          this.left = this.right = null;
      }
  }

  public BinaryTree() {
      this.root = null;
  }

  public void insert(int data) {
      root = insertRec(root, data);
  }

  private Node insertRec(Node root, int data) {
      if (root == null) {
          root = new Node(data);
          return root;
      }
      if (data < root.data) {
          root.left = insertRec(root.left, data);
      } else if (data > root.data) {
          root.right = insertRec(root.right, data);
      }
      return root;
  }

  public boolean search(int data) {
      return searchRec(root, data);
  }

  private boolean searchRec(Node root, int data) {
      if (root == null) {
          return false;
      }
      if (root.data == data) {
          return true;
      }
      return data < root.data ? searchRec(root.left, data) : searchRec(root.right, data);
  }

  public void inorder() {
      inorderRec(root);
  }

  private void inorderRec(Node root) {
      if (root != null) {
          inorderRec(root.left);
          System.out.print(root.data + " ");
          inorderRec(root.right);
      }
  }

  public void delete(int data) {
      root = deleteRec(root, data);
  }

  private Node deleteRec(Node root, int data) {
      if (root == null) {
          return root;
      }
      if (data < root.data) {
          root.left = deleteRec(root.left, data);
      } else if (data > root.data) {
          root.right = deleteRec(root.right, data);
      } else {
          if (root.left == null) {
              return root.right;
          } else if (root.right == null) {
              return root.left;
          }
          root.data = minValue(root.right);
          root.right = deleteRec(root.right, root.data);
      }
      return root;
  }

  private int minValue(Node root) {
      int minValue = root.data;
      while (root.left != null) {
          minValue = root.left.data;
          root = root.left;
      }
      return minValue;
  }

  public static void main(String[] args) {
      BinaryTree tree = new BinaryTree();
      tree.insert(50);
      tree.insert(30);
      tree.insert(20);
      tree.insert(40);
      tree.insert(70);
      tree.insert(60);
      tree.insert(80);

      System.out.println("Inorder traversal:");
      tree.inorder(); // Output: 20 30 40 50 60 70 80

      System.out.println("\n\nAfter deleting 20:");
      tree.delete(20);
      tree.inorder(); // Output: 30 40 50 60 70 80

      System.out.println("\n\nSearch for 70:");
      System.out.println(tree.search(70)); // Output: true
      System.out.println("Search for 100:");
      System.out.println(tree.search(100)); // Output: false
  }
}
