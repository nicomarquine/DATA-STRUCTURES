
package exam;

import dataStructures.list.LL;
import dataStructures.queue.ArrayQueue;
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
        if(first == null || start == end){
            return first;
        }

        LL.Node<E> current = first;
        LL.Node<E> beforeStart = null;
        LL.Node<E> next = null;

        int count = 0;
        while(count<start){
            beforeStart = current;
            current = current.getNext();
            count++;
        }

        LL.Node<E> afterStart = current;
        LL.Node<E> previous = null;

        while(current != null && count<=end){
            next = current.getNext();
            current.setNext(previous);

            previous = current;
            current = next;
            count++;
        }
        if(beforeStart == null){
            first = previous;
        } else {
            beforeStart.setNext(previous);
        }
        afterStart.setNext(current);
        return first;
    }

    /**
     * Question B – Binary Trees
     *
     * Count how many nodes in the (possibly empty) binary tree rooted at {@code root}
     * have exactly one child (either only left or only right, but not both).
     */
    public static <E> int countSingleChildNodes(BT.Node<E> root) {
       if(root == null){
           return 0;
       }else if(root.getRight() != null && root.getLeft()==null ||root.getRight() == null && root.getLeft()!=null){
           return 1 + countSingleChildNodes(root.getLeft()) + countSingleChildNodes(root.getRight());
       }else{
           return countSingleChildNodes(root.getLeft()) + countSingleChildNodes(root.getRight());
       }
    }

    /**
     * Question C – Binary Search Trees
     *
     * Count the number of nodes in the BST whose element is strictly greater than {@code value}.
     * Uses the BST ordering to avoid visiting unnecessary nodes. It also uses BT.size to
     * count entire subtrees when all of their values are guaranteed to be > value.
     */
    public static int countNodesGreaterThan(BT.Node<Integer> root, int value) {
       if(BT.size(root) == 0){
           return 0;
       }
        if(root.getElement() <= value){
            return countNodesGreaterThan(root.getRight(),value);
        } else {
            int rightCount = BT.size(root.getRight());
            int leftCount = countNodesGreaterThan(root.getLeft(),value);
            return 1 + rightCount + leftCount;
        }
    }

    /**
     * Question D – General Trees
     *
     * Returns the maximum among the sums of node values at each level of the tree.
     * For an empty tree (root == null) the result is 0.
     */
    public static int levelWithMaxSum(GT.Node<Integer> root) {
        if(root == null){
            return 0;
        }
        ArrayQueue<GT.Node<Integer>> queue = new ArrayQueue<>();
        queue.enqueue(root);
        int maxSum = root.getElement();
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            int levelSum = 0;
            for (int i = 0; i < levelSize; i++) {
                GT.Node<Integer> node = queue.dequeue();
                levelSum += node.getElement();
                for (GT.Node<Integer> child : node.getChildren()) {
                    queue.enqueue(child);
                }
            }
            if (levelSum > maxSum) {
                maxSum = levelSum;
            }
        }
        return maxSum;
    }
}
