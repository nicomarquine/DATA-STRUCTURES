
package exam;

import dataStructures.list.LL;
import dataStructures.tree.binary.BT;
import dataStructures.tree.general.GT;

import java.util.Arrays;
import java.util.List;

/**
 * Simple test runner (no external libraries required).
 * Run this class' main method after compiling everything:
 *
 *   javac dataStructures/list/LL.java \
 *         dataStructures/tree/binary/BT.java \
 *         dataStructures/tree/general/GT.java \
 *         exam/Exam.java \
 *         exam/ExamTests.java
 *
 *   java exam.ExamTests
 */
public class ExamTests {

    public static void main(String[] args) {
        testReverseBetween();
        testCountSingleChildNodes();
        testCountNodesGreaterThan();
        testLevelWithMaxSum();
        System.out.println("All tests passed!");
    }

    // ---------- Question A tests ----------

    private static void testReverseBetween() {
        // Example 1: A -> B -> C -> D -> E, reverseBetween(first, 1, 3)
        LL.Node<String> list1 = LL.fromArray(new String[]{"A", "B", "C", "D", "E"});
        LL.Node<String> res1 = Exam.reverseBetween(list1, 1, 3);
        List<String> expected1 = Arrays.asList("A", "D", "C", "B", "E");
        assertEquals(expected1, LL.toList(res1), "reverseBetween example 1");

        // Example 2: A -> B -> C -> D -> E, reverseBetween(first, 0, 2)
        LL.Node<String> list2 = LL.fromArray(new String[]{"A", "B", "C", "D", "E"});
        LL.Node<String> res2 = Exam.reverseBetween(list2, 0, 2);
        List<String> expected2 = Arrays.asList("C", "B", "A", "D", "E");
        assertEquals(expected2, LL.toList(res2), "reverseBetween example 2");

        // Edge case: single-element sublist
        LL.Node<Integer> list3 = LL.fromArray(new Integer[]{1, 2, 3});
        LL.Node<Integer> res3 = Exam.reverseBetween(list3, 1, 1);
        List<Integer> expected3 = Arrays.asList(1, 2, 3);
        assertEquals(expected3, LL.toList(res3), "reverseBetween single element");
    }

    // ---------- Question B tests ----------

    private static void testCountSingleChildNodes() {
        // Example tree (using chars for clarity):
        //
        //      A
        //     /
        //    B
        //   / \
        //  C   D
        //       \
        //        E
        //
        // A and D have exactly one child => answer is 2.
        BT.Node<Character> c = BT.node('C', null, null);
        BT.Node<Character> e = BT.node('E', null, null);
        BT.Node<Character> d = BT.node('D', null, e);
        BT.Node<Character> b = BT.node('B', c, d);
        BT.Node<Character> a = BT.node('A', b, null);

        int result = Exam.countSingleChildNodes(a);
        assertEquals(2, result, "countSingleChildNodes example tree");

        // Empty tree
        assertEquals(0, Exam.countSingleChildNodes(null), "countSingleChildNodes empty");

        // Full binary tree – no node has a single child
        BT.Node<Integer> left = BT.node(2, BT.node(4, null, null), BT.node(5, null, null));
        BT.Node<Integer> right = BT.node(3, BT.node(6, null, null), BT.node(7, null, null));
        BT.Node<Integer> root = BT.node(1, left, right);
        assertEquals(0, Exam.countSingleChildNodes(root), "countSingleChildNodes full tree");
    }

    // ---------- Question C tests ----------

    private static void testCountNodesGreaterThan() {
        // BST:
        //         30
        //       /    \
        //     20      40
        //    /  \    /  \
        //  10   25  35  50
        BT.Node<Integer> n10 = BT.node(10, null, null);
        BT.Node<Integer> n25 = BT.node(25, null, null);
        BT.Node<Integer> n35 = BT.node(35, null, null);
        BT.Node<Integer> n50 = BT.node(50, null, null);
        BT.Node<Integer> n20 = BT.node(20, n10, n25);
        BT.Node<Integer> n40 = BT.node(40, n35, n50);
        BT.Node<Integer> n30 = BT.node(30, n20, n40);

        int res1 = Exam.countNodesGreaterThan(n30, 21);
        assertEquals(5, res1, "countNodesGreaterThan > 21");

        int res2 = Exam.countNodesGreaterThan(n30, 35);
        assertEquals(2, res2, "countNodesGreaterThan > 35");

        int res3 = Exam.countNodesGreaterThan(n30, 50);
        assertEquals(0, res3, "countNodesGreaterThan > 50");

        // Single-node tree
        BT.Node<Integer> single = BT.node(5, null, null);
        assertEquals(1, Exam.countNodesGreaterThan(single, 0), "countNodesGreaterThan single node");
        assertEquals(0, Exam.countNodesGreaterThan(single, 5), "countNodesGreaterThan equal value");
    }

    // ---------- Question D tests ----------

    private static void testLevelWithMaxSum() {
        // Example tree:
        //
        //          7                 // level 0 sum = 7
        //       /   |   \
        //      9    1   -5           // level 1 sum = 9 + 1 + (-5) = 5
        //     / \        \\
        //    2   4        8          // level 2 sum = 2 + 4 + 8 = 14  -> max = 14
        GT.Node<Integer> n2 = GT.node(2);
        GT.Node<Integer> n4 = GT.node(4);
        GT.Node<Integer> n8 = GT.node(8);
        GT.Node<Integer> n9 = GT.node(9, n2, n4);
        GT.Node<Integer> n1 = GT.node(1);
        GT.Node<Integer> nMinus5 = GT.node(-5, n8);
        GT.Node<Integer> n7 = GT.node(7, n9, n1, nMinus5);

        int result = Exam.levelWithMaxSum(n7);
        assertEquals(14, result, "levelWithMaxSum example tree");

        // Empty tree
        assertEquals(0, Exam.levelWithMaxSum(null), "levelWithMaxSum empty");

        // Single node
        GT.Node<Integer> single = GT.node(42);
        assertEquals(42, Exam.levelWithMaxSum(single), "levelWithMaxSum single node");
    }

    // ---------- tiny assertion helper ----------

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(
                    message + " – expected: " + expected + ", actual: " + actual);
        }
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(
                    message + " – expected: " + expected + ", actual: " + actual);
        }
    }
}
