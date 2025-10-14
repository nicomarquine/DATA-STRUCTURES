package org.uma.ed.demos.tree;

import org.uma.ed.datastructures.tree.Tree;
import org.uma.ed.datastructures.tree.Tree.Node;

/**
 * A demonstration class for the utility methods in {@link Tree}.
 * <p>
 * This class constructs a sample general (multi-way) tree and then calls each of the static
 * methods from the {@code Tree} class to show how they work. The output
 * for each operation is printed to the console for verification.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class TreeDemo {
  public static void main(String[] args) {
    // Constructing a sample general tree:
    //
    //          1
    //       /  |  \
    //      2   4   5
    //      |      /|\
    //      3     6 7 8
    //
    Node<Integer> tree = Node.of(1,
        Node.of(2, Node.of(3)),
        Node.of(4),
        Node.of(5, Node.of(6), Node.of(7), Node.of(8))
    );

    System.out.println("--- General Tree Structure ---");
    System.out.println("          1");
    System.out.println("       /  |  \\");
    System.out.println("      2   4   5");
    System.out.println("      |      /|\\");
    System.out.println("      3     6 7 8");
    System.out.println("----------------------------\n");


    System.out.println("--- Tree Properties ---");
    System.out.printf("Size (number of nodes): %d%n", Tree.size(tree)); // prints 8
    System.out.printf("Height of the tree: %d%n", Tree.height(tree)); // prints 3
    System.out.printf("Sum of all elements: %d%n", Tree.sum(tree)); // prints 36
    System.out.printf("Maximum element: %d%n", Tree.maximum(tree, Integer::compareTo)); // prints 8
    System.out.printf("Occurrences of element 7: %d%n", Tree.count(tree, 7)); // prints 1
    System.out.printf("Leaf elements: %s%n", Tree.leaves(tree)); // prints [3, 4, 6, 7, 8]
    System.out.println();

    System.out.println("--- Tree Traversal Orders ---");
    System.out.printf("Preorder traversal:      %s%n", Tree.preorder(tree)); // prints [1, 2, 3, 4, 5, 6, 7, 8]
    System.out.printf("Postorder traversal:     %s%n", Tree.postorder(tree)); // prints [3, 2, 4, 6, 7, 8, 5, 1]
    System.out.printf("Breadth-first traversal: %s%n", Tree.breadthFirst(tree)); // prints [1, 2, 4, 5, 3, 6, 7, 8]
  }
}