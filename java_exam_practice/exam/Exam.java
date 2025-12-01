
package exam;

import dataStructures.list.LL;
import dataStructures.tree.binary.BT;
import dataStructures.tree.general.GT;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Implementation of the four exam methods based only on the PDF statements.
 * You can compile this together with the dataStructures.* classes and run ExamTests
 * to verify the behaviour.
 */
public class Exam {

    /**
     * Question A – Linked Lists
     *
     * Reverse in-place the segment of the list from index {@code start} to index {@code end}
     * (both inclusive). Positions are 0‑indexed. The method returns a reference to the
     * first node of the modified list (which may change when start == 0).
     */
    public static <E> LL.Node<E> reverseBetween(LL.Node<E> first, int start, int end) {
       return null;
    }

    /**
     * Question B – Binary Trees
     *
     * Count how many nodes in the (possibly empty) binary tree rooted at {@code root}
     * have exactly one child (either only left or only right, but not both).
     */
    public static <E> int countSingleChildNodes(BT.Node<E> root) {
       return 0;
    }

    /**
     * Question C – Binary Search Trees
     *
     * Count the number of nodes in the BST whose element is strictly greater than {@code value}.
     * Uses the BST ordering to avoid visiting unnecessary nodes. It also uses BT.size to
     * count entire subtrees when all of their values are guaranteed to be > value.
     */
    public static int countNodesGreaterThan(BT.Node<Integer> root, int value) {
       return 0;
    }

    /**
     * Question D – General Trees
     *
     * Returns the maximum among the sums of node values at each level of the tree.
     * For an empty tree (root == null) the result is 0.
     */
    public static int levelWithMaxSum(GT.Node<Integer> root) {
       return 0;
    }
}
