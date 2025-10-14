package org.uma.ed.demos.tree;

import org.uma.ed.datastructures.tree.BinaryTree;
import org.uma.ed.datastructures.tree.BinaryTree.Node;

/**
 * A demonstration class for the utility methods in {@link BinaryTree}.
 * <p>
 * This class constructs a sample binary tree and then calls each of the static
 * methods from the {@code BinaryTree} class to show how they work. The output
 * for each operation is printed to the console for verification.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class BinaryTreeDemo {

  public static void main(String[] args) {
    // Constructing a sample binary tree:
    //
    //          1
    //         / \
    //        2   4
    //       /   / \
    //      3   5   6
    //               \
    //                7
    //
    Node<Integer> tree = Node.of(1,
        Node.of(2, Node.of(3), null),
        Node.of(4, Node.of(5), Node.of(6, null, Node.of(7)))
    );

    System.out.println("--- Binary Tree Structure ---");
    System.out.println("         1");
    System.out.println("        / \\");
    System.out.println("       2   4");
    System.out.println("      /   / \\");
    System.out.println("     3   5   6");
    System.out.println("              \\");
    System.out.println("               7");
    System.out.println("---------------------------\n");


    System.out.println("--- Tree Properties ---");
    System.out.printf("Size (number of nodes): %d%n", BinaryTree.size(tree)); // prints 7
    System.out.printf("Height of the tree: %d%n", BinaryTree.height(tree)); // prints 4
    System.out.printf("Sum of all elements: %d%n", BinaryTree.sum(tree)); // prints 28
    System.out.printf("Maximum element: %d%n", BinaryTree.maximum(tree, Integer::compareTo)); // prints 7
    System.out.printf("Occurrences of element 7: %d%n", BinaryTree.count(tree, 7)); // prints 1
    System.out.printf("Leaf elements: %s%n", BinaryTree.leaves(tree)); // prints [3, 5, 7]
    System.out.println();

    System.out.println("--- Tree Traversal Orders ---");
    System.out.printf("Preorder traversal:    %s%n", BinaryTree.preorder(tree)); // prints [1, 2, 3, 4, 5, 6, 7]
    System.out.printf("Postorder traversal:   %s%n", BinaryTree.postorder(tree)); // prints [3, 2, 5, 7, 6, 4, 1]
    System.out.printf("Inorder traversal:     %s%n", BinaryTree.inorder(tree)); // prints [3, 2, 1, 5, 4, 6, 7]
    System.out.printf("Breadth-first traversal: %s%n", BinaryTree.breadthFirst(tree)); // prints [1, 2, 4, 3, 5, 6, 7]
  }
}