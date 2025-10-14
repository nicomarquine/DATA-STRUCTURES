package org.uma.ed.datastructures.searchtree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;

import java.lang.reflect.Field;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test cases for class AVL")
class AVLTest {

  // ==========================================================
  // BLACK-BOX TESTS (Public API Behavior) - Adapted from BSTTest
  // ==========================================================

  @Nested
  @DisplayName("An AVL tree is created")
  class CreationTests {
    @Test @DisplayName("by calling the constructor with a comparator")
    void constructorWithComparator() {
      SearchTree<Integer> tree = new AVL<Integer>(Comparator.naturalOrder());
      assertNotNull(tree);
      assertTrue(tree.isEmpty());
    }
    @Test @DisplayName("by calling the empty() method")
    void emptyFactory() {
      SearchTree<Integer> tree = AVL.empty();
      assertNotNull(tree);
      assertTrue(tree.isEmpty());
      assertEquals(Comparator.naturalOrder(), tree.comparator());
    }
  }

  @Nested
  @DisplayName("The copyOf() method")
  class CopyOfTests {
    @Test @DisplayName("works correctly with an empty tree")
    void copyOfEmpty() {
      AVL<Integer> tree = AVL.empty();
      AVL<Integer> copiedTree = AVL.copyOf(tree);
      assertTrue(copiedTree.isEmpty());
      assertNotSame(tree, copiedTree);
    }
    @Test @DisplayName("works correctly with a non-empty tree")
    void copyOfNonEmpty() {
      AVL<Integer> tree = AVL.empty();
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);
      AVL<Integer> copiedTree = AVL.copyOf(tree);
      assertEquals(tree.size(), copiedTree.size());
      assertNotSame(tree, copiedTree);
      assertEquals(JDKArrayList.from(tree.inOrder()).toString(), JDKArrayList.from(copiedTree.inOrder()).toString());
    }
  }

  @Nested
  @DisplayName("Basic properties: isEmpty(), size(), clear(), height()")
  class BasicPropertiesTests {
    private SearchTree<Integer> tree;
    @BeforeEach void setup() { tree = AVL.empty(); }
    @Test @DisplayName("isEmpty() is true for a new tree and false after insert")
    void testIsEmpty() { assertTrue(tree.isEmpty()); tree.insert(1); assertFalse(tree.isEmpty()); }
    @Test @DisplayName("size() correctly reflects the number of elements")
    void testSize() {
      assertEquals(0, tree.size());
      tree.insert(10); tree.insert(20); tree.insert(10);
      assertEquals(2, tree.size());
      tree.delete(10);
      assertEquals(1, tree.size());
    }
    @Test @DisplayName("clear() empties a non-empty tree")
    void testClear() { tree.insert(1); tree.insert(2); tree.clear(); assertTrue(tree.isEmpty()); }
    @Test @DisplayName("height() is 0 for an empty tree and updates correctly")
    void testHeight() {
      assertEquals(0, tree.height());
      tree.insert(10); assertEquals(1, tree.height());
      tree.insert(5);  assertEquals(2, tree.height());
      tree.insert(15); assertEquals(2, tree.height());
      tree.insert(3);  assertEquals(3, tree.height());
      tree.insert(8);  assertEquals(3, tree.height());
    }
  }

  @Nested
  @DisplayName("Core operations: insert(), search(), contains(), delete()")
  class CoreOperationsTests {
    private SearchTree<Integer> tree;
    @BeforeEach void setup() {
      tree = AVL.empty();
      tree.insert(10); tree.insert(5); tree.insert(15);
      tree.insert(3); tree.insert(7); tree.insert(12); tree.insert(18);
    }
    @Test @DisplayName("contains() and search() work correctly")
    void testContainsAndSearch() {
      assertTrue(tree.contains(7));
      assertFalse(tree.contains(99));
      assertEquals(7, tree.search(7));
      assertNull(tree.search(99));
    }
    @Test @DisplayName("delete() a leaf node")
    void testDeleteLeaf() { tree.delete(3); assertFalse(tree.contains(3)); assertEquals(6, tree.size()); }
    @Test @DisplayName("delete() a node with one right child")
    void testDeleteNodeWithOneChild() {
      tree.insert(8); tree.delete(7); assertFalse(tree.contains(7)); assertTrue(tree.contains(8)); assertEquals(7, tree.size());
    }
    @Test @DisplayName("delete() a node with two children")
    void testDeleteNodeWithTwoChildren() { tree.delete(5); assertFalse(tree.contains(5)); assertEquals(6, tree.size()); }
    @Test @DisplayName("delete() the root node")
    void testDeleteRoot() {
      tree.delete(10);
      assertFalse(tree.contains(10));
      assertEquals(6, tree.size());
      assertEquals(3, tree.minimum());
      assertEquals(18, tree.maximum());
    }
  }

  @Nested
  @DisplayName("Extremal and Traversal operations")
  class ExtremalAndTraversalTests {
    private SearchTree<Integer> tree;
    @BeforeEach void setup() {
      tree = AVL.empty();
      tree.insert(8); tree.insert(3); tree.insert(10); tree.insert(1);
      tree.insert(6); tree.insert(14); tree.insert(4); tree.insert(7); tree.insert(13);
    }
    @Test @DisplayName("minimum() and maximum() work correctly")
    void testMinMax() { assertEquals(1, tree.minimum()); assertEquals(14, tree.maximum()); }
    @Test @DisplayName("deleteMinimum() and deleteMaximum() work correctly")
    void testDeleteMinMax() {
      tree.deleteMinimum(); assertEquals(3, tree.minimum());
      tree.deleteMaximum(); assertEquals(13, tree.maximum());
    }
    @Test @DisplayName("inOrder() traverses elements in ascending order")
    void testInOrder() {
      List<Integer> expected = JDKArrayList.of(1, 3, 4, 6, 7, 8, 10, 13, 14);
      List<Integer> actual = JDKArrayList.empty();
      tree.inOrder().forEach(actual::append);
      assertEquals(expected.toString(), actual.toString());
    }
  }

  // ==========================================================
  // WHITE-BOX TESTS (Structural Integrity)
  // ==========================================================

  @Nested
  @DisplayName("Structural Integrity Tests (White-Box)")
  class StructuralIntegrityTests {

    // --- Reflection Helper Methods ---
    private Object getRootNode(AVL<?> tree) throws ReflectiveOperationException {
      Field rootField = AVL.class.getDeclaredField("root");
      rootField.setAccessible(true);
      return rootField.get(tree);
    }

    private <T> T getField(Object obj, String fieldName) throws ReflectiveOperationException {
      Field field = obj.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      @SuppressWarnings("unchecked")
      T value = (T) field.get(obj);
      return value;
    }

    private int height(Object node) throws ReflectiveOperationException {
      return node == null ? 0 : (int) getField(node, "height");
    }

    /**
     * Recursively validates the AVL Tree properties.
     * 1. Binary Search Tree property.
     * 2. Balance Factor invariant (|height(left) - height(right)| <= 1).
     * 3. Height consistency (node.height == 1 + max(height(children))).
     */
    private void validateAVLInvariants(Object node, Comparator<Integer> comparator, Integer min, Integer max) throws ReflectiveOperationException {
      if (node == null) return;

      Integer key = getField(node, "key");
      Object left = getField(node, "left");
      Object right = getField(node, "right");

      // 1. BST Property
      if (min != null && comparator.compare(key, min) <= 0) fail("BST property violated at key " + key);
      if (max != null && comparator.compare(key, max) >= 0) fail("BST property violated at key " + key);

      // 2. Balance Factor Invariant
      int balanceFactor = height(left) - height(right);
      assertTrue(Math.abs(balanceFactor) <= 1,
          "AVL balance factor violated at key " + key + ". Factor: " + balanceFactor);

      // 3. Height Consistency Invariant
      assertEquals(1 + Math.max(height(left), height(right)), height(node),
          "Height inconsistency at key " + key);

      // Recurse
      validateAVLInvariants(left, comparator, min, key);
      validateAVLInvariants(right, comparator, key, max);
    }

    @Test
    @DisplayName("a sequence of random inserts and deletes should always maintain AVL invariants")
    void randomOperationsMaintainAVLInvariants() throws ReflectiveOperationException {
      AVL<Integer> tree = AVL.empty();
      java.util.Random rand = new java.util.Random();
      List<Integer> elementsInTree = JDKArrayList.empty();

      for (int i = 0; i < 5000; i++) {
        if (!elementsInTree.isEmpty() && rand.nextFloat() < 0.4) {
          int indexToRemove = rand.nextInt(elementsInTree.size());
          Integer valueToRemove = elementsInTree.get(indexToRemove);
          tree.delete(valueToRemove);
          elementsInTree.delete(indexToRemove);
        } else {
          int valueToInsert = rand.nextInt(5000);
          if (!tree.contains(valueToInsert)) {
            tree.insert(valueToInsert);
            elementsInTree.append(valueToInsert);
          }
        }
        validateAVLInvariants(getRootNode(tree), tree.comparator(), null, null);
        assertEquals(elementsInTree.size(), tree.size());
      }
    }

    @Test
    @DisplayName("insertions causing a Left-Left case should trigger a right rotation")
    void insertCausesLLRotation() throws ReflectiveOperationException {
      AVL<Integer> tree = AVL.empty();
      tree.insert(30);
      tree.insert(20);
      validateAVLInvariants(getRootNode(tree), tree.comparator(), null, null); // Pre-check

      tree.insert(10); // Causes LL imbalance at root

      Object root = getRootNode(tree);
      assertEquals(20, (Integer)getField(root, "key"), "Root should be 20 after right rotation.");
      assertEquals(10, (Integer)getField(getField(root, "left"), "key"));
      assertEquals(30, (Integer)getField(getField(root, "right"), "key"));
      validateAVLInvariants(root, tree.comparator(), null, null);
    }

    @Test
    @DisplayName("insertions causing a Right-Right case should trigger a left rotation")
    void insertCausesRRRotation() throws ReflectiveOperationException {
      AVL<Integer> tree = AVL.empty();
      tree.insert(10);
      tree.insert(20);
      validateAVLInvariants(getRootNode(tree), tree.comparator(), null, null);

      tree.insert(30); // Causes RR imbalance at root

      Object root = getRootNode(tree);
      assertEquals(20, (Integer)getField(root, "key"), "Root should be 20 after left rotation.");
      assertEquals(10, (Integer)getField(getField(root, "left"), "key"));
      assertEquals(30, (Integer)getField(getField(root, "right"), "key"));
      validateAVLInvariants(root, tree.comparator(), null, null);
    }

    @Test
    @DisplayName("insertions causing a Left-Right case should trigger a double rotation")
    void insertCausesLRRotation() throws ReflectiveOperationException {
      AVL<Integer> tree = AVL.empty();
      tree.insert(30);
      tree.insert(10);
      validateAVLInvariants(getRootNode(tree), tree.comparator(), null, null);

      tree.insert(20); // Causes LR imbalance at root

      Object root = getRootNode(tree);
      assertEquals(20, (Integer)getField(root, "key"), "Root should be 20 after LR rotation.");
      assertEquals(10, (Integer)getField(getField(root, "left"), "key"));
      assertEquals(30, (Integer)getField(getField(root, "right"), "key"));
      validateAVLInvariants(root, tree.comparator(), null, null);
    }

    @Test
    @DisplayName("insertions causing a Right-Left case should trigger a double rotation")
    void insertCausesRLRotation() throws ReflectiveOperationException {
      AVL<Integer> tree = AVL.empty();
      tree.insert(10);
      tree.insert(30);
      validateAVLInvariants(getRootNode(tree), tree.comparator(), null, null);

      tree.insert(20); // Causes RL imbalance at root

      Object root = getRootNode(tree);
      assertEquals(20, (Integer)getField(root, "key"), "Root should be 20 after RL rotation.");
      assertEquals(10, (Integer)getField(getField(root, "left"), "key"));
      assertEquals(30, (Integer)getField(getField(root, "right"), "key"));
      validateAVLInvariants(root, tree.comparator(), null, null);
    }
  }
}