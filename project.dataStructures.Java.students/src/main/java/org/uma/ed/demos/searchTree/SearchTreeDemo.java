package org.uma.ed.demos.searchTree;

import org.uma.ed.datastructures.list.ArrayList;
import org.uma.ed.datastructures.searchtree.BST;
import org.uma.ed.datastructures.searchtree.SearchTree;

/**
 * A simple demonstration class for {@link SearchTree} implementations, focusing on
 * extremal operations.
 * <p>
 * This class builds a search tree and then repeatedly finds and deletes the minimum and
 * maximum elements, printing the new extremal values after each deletion. This serves
 * as a visual test for the correctness of {@code minimum}, {@code maximum},
 * {@code deleteMinimum}, and {@code deleteMaximum}.
 * <p>
 * This example uses {@link BST} as the concrete implementation.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class SearchTreeDemo {

  public static void main(String[] args) {
    // Use any SearchTree implementation. BST is chosen for simplicity.
    SearchTree<Integer> tree = BST.empty();

    int[] initialData = {8, 5, 7, 1, 9, 6};
    System.out.println("--- Building Search Tree ---");
    System.out.println("Inserting elements: " + java.util.Arrays.toString(initialData));

    for (int x : initialData) {
      tree.insert(x);
    }

    // To print the content, we create a List from the in-order iterable.
    System.out.println("Initial tree content (in-order): " + ArrayList.from(tree.inOrder()));
    System.out.println();


    System.out.println("--- Testing Extremal Operations ---");
    // Loop until the tree is empty, deleting min and max alternately (or together).
    int step = 1;
    while (!tree.isEmpty()) {
      System.out.printf("Step %d:%n", step++);
      System.out.printf("  Current Tree (in-order): %s%n", ArrayList.from(tree.inOrder()));
      System.out.printf("  - Minimum: %d%n", tree.minimum());
      System.out.printf("  - Maximum: %d%n", tree.maximum());

      if (tree.size() > 1) {
        System.out.println("  Deleting minimum (" + tree.minimum() + ") and maximum (" + tree.maximum() + ")...");
        tree.deleteMinimum();
        tree.deleteMaximum();
      } else {
        // If only one element is left, just delete it.
        System.out.println("  Deleting last element (" + tree.minimum() + ")...");
        tree.deleteMinimum();
      }
      System.out.println();
    }

    System.out.println("--- Final State ---");
    System.out.println("Tree is now empty: " + tree.isEmpty());
    System.out.println("Final tree content (in-order): " + ArrayList.from(tree.inOrder()));
  }
}