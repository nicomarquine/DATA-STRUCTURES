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

@DisplayName("Test cases for class BST")
class BSTTest {

  // ==========================================================
  // BLACK-BOX TESTS (Public API Behavior)
  // ==========================================================

  @Nested
  @DisplayName("A BST is created")
  class CreationTests {

    @Test @DisplayName("by calling the constructor with a comparator")
    void constructorWithComparator() {
      SearchTree<Integer> tree = new BST<Integer>(Comparator.naturalOrder());
      assertNotNull(tree);
      assertTrue(tree.isEmpty());
    }

    @Test @DisplayName("by calling the empty() method")
    void emptyFactory() {
      SearchTree<Integer> tree = BST.empty();
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
      BST<Integer> tree = BST.empty();
      BST<Integer> copiedTree = BST.copyOf(tree);
      assertTrue(copiedTree.isEmpty());
      assertNotSame(tree, copiedTree);
    }

    @Test @DisplayName("works correctly with a non-empty tree")
    void copyOfNonEmpty() {
      BST<Integer> tree = BST.empty();
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);
      BST<Integer> copiedTree = BST.copyOf(tree);

      assertEquals(tree.size(), copiedTree.size());
      assertNotSame(tree, copiedTree);
      assertEquals(JDKArrayList.from(tree.inOrder()), JDKArrayList.from(copiedTree.inOrder()));
    }
  }

  @Nested
  @DisplayName("Basic properties: isEmpty(), size(), clear(), height()")
  class BasicPropertiesTests {
    private SearchTree<Integer> tree;

    @BeforeEach void setup() { tree = BST.empty(); }

    @Test @DisplayName("isEmpty() is true for a new tree and false after insert")
    void testIsEmpty() {
      assertTrue(tree.isEmpty());
      tree.insert(1);
      assertFalse(tree.isEmpty());
    }

    @Test @DisplayName("size() correctly reflects the number of elements")
    void testSize() {
      assertEquals(0, tree.size());
      tree.insert(10);
      tree.insert(20);
      tree.insert(10); // Duplicate, should not increase size
      assertEquals(2, tree.size());
      tree.delete(10);
      assertEquals(1, tree.size());
    }

    @Test @DisplayName("clear() empties a non-empty tree")
    void testClear() {
      tree.insert(1);
      tree.insert(2);
      tree.clear();
      assertTrue(tree.isEmpty());
      assertEquals(0, tree.size());
    }

    @Test @DisplayName("height() is 0 for an empty tree and updates correctly")
    void testHeight() {
      assertEquals(0, tree.height());
      tree.insert(10); // height 1
      assertEquals(1, tree.height());
      tree.insert(5);  // height 2
      assertEquals(2, tree.height());
      tree.insert(15); // height 2
      assertEquals(2, tree.height());
      tree.insert(3);  // height 3
      assertEquals(3, tree.height());
    }
  }

  @Nested
  @DisplayName("Core operations: insert(), search(), contains(), delete()")
  class CoreOperationsTests {
    private SearchTree<Integer> tree;

    @BeforeEach void setup() {
      tree = BST.empty();
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);
      tree.insert(3);
      tree.insert(7);
      tree.insert(12);
      tree.insert(18);
    }

    @Test @DisplayName("contains() returns true for existing elements and false otherwise")
    void testContains() {
      assertTrue(tree.contains(7));
      assertFalse(tree.contains(99));
    }

    @Test @DisplayName("search() returns the element if it exists, null otherwise")
    void testSearch() {
      assertEquals(7, tree.search(7));
      assertNull(tree.search(99));
    }

    @Test @DisplayName("delete() a leaf node")
    void testDeleteLeaf() {
      assertTrue(tree.contains(3));
      tree.delete(3);
      assertFalse(tree.contains(3));
      assertEquals(6, tree.size());
    }

    @Test @DisplayName("delete() a node with one right child")
    void testDeleteNodeWithOneRightChild() {
      tree.delete(7);
      tree.insert(8); // To make sure 7 has one child
      tree.delete(7);
      assertFalse(tree.contains(7));
      assertTrue(tree.contains(8));
      assertEquals(7, tree.size());
    }

    @Test @DisplayName("delete() a node with one left child")
    void testDeleteNodeWithOneLeftChild() {
      tree.insert(6);
      tree.delete(7);
      assertFalse(tree.contains(7));
      assertTrue(tree.contains(6));
      assertEquals(7, tree.size());
    }

    @Test @DisplayName("delete() a node with two children")
    void testDeleteNodeWithTwoChildren() {
      tree.delete(5);
      assertFalse(tree.contains(5));
      // Successor (7) takes its place
      assertEquals(6, tree.size());
    }

    @Test @DisplayName("delete() the root node")
    void testDeleteRoot() {
      tree.delete(10);
      assertFalse(tree.contains(10));
      // Successor (12) takes its place
      assertEquals(6, tree.size());
      assertEquals(3, tree.minimum());
      assertEquals(18, tree.maximum());
    }
  }

  @Nested
  @DisplayName("Extremal operations: minimum(), maximum(), deleteMinimum(), deleteMaximum()")
  class ExtremalOperationsTests {
    private SearchTree<Integer> tree;

    @BeforeEach void setup() {
      tree = BST.empty();
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);
    }

    @Test @DisplayName("minimum() and maximum() throw EmptySearchTreeException on an empty tree")
    void extremalOnEmptyTree() {
      tree.clear();
      assertThrows(EmptySearchTreeException.class, tree::minimum);
      assertThrows(EmptySearchTreeException.class, tree::maximum);
    }

    @Test @DisplayName("minimum() returns the smallest element")
    void testMinimum() {
      assertEquals(5, tree.minimum());
    }

    @Test @DisplayName("maximum() returns the largest element")
    void testMaximum() {
      assertEquals(15, tree.maximum());
    }

    @Test @DisplayName("deleteMinimum() removes the smallest element")
    void testDeleteMinimum() {
      tree.deleteMinimum();
      assertEquals(10, tree.minimum());
      assertEquals(2, tree.size());
    }

    @Test @DisplayName("deleteMaximum() removes the largest element")
    void testDeleteMaximum() {
      tree.deleteMaximum();
      assertEquals(10, tree.maximum());
      assertEquals(2, tree.size());
    }
  }

  @Nested
  @DisplayName("Traversal iterators")
  class TraversalTests {
    private SearchTree<Integer> tree;
    @BeforeEach void setup() {
      tree = BST.empty();
      tree.insert(8); tree.insert(3); tree.insert(10); tree.insert(1);
      tree.insert(6); tree.insert(14); tree.insert(4); tree.insert(7); tree.insert(13);
    }

    @Test @DisplayName("inOrder() traverses elements in ascending order")
    void testInOrder() {
      List<Integer> expected = JDKArrayList.of(1, 3, 4, 6, 7, 8, 10, 13, 14);
      List<Integer> actual = JDKArrayList.empty();
      tree.inOrder().forEach(actual::append);
      assertEquals(expected, actual);
    }

    @Test @DisplayName("preOrder() traverses correctly")
    void testPreOrder() {
      List<Integer> expected = JDKArrayList.of(8, 3, 1, 6, 4, 7, 10, 14, 13);
      List<Integer> actual = JDKArrayList.empty();
      tree.preOrder().forEach(actual::append);
      assertEquals(expected, actual);
    }

    @Test @DisplayName("postOrder() traverses correctly")
    void testPostOrder() {
      List<Integer> expected = JDKArrayList.of(1, 4, 7, 6, 3, 13, 14, 10, 8);
      List<Integer> actual = JDKArrayList.empty();
      tree.postOrder().forEach(actual::append);
      assertEquals(expected, actual);
    }
  }

  // ==========================================================
  // WHITE-BOX TESTS (Structural Integrity)
  // ==========================================================

  @Nested
  @DisplayName("Structural Integrity Tests (White-Box)")
  class StructuralIntegrityTests {

    // --- Reflection Helper Methods ---
    private Object getRootNode(BST<?> tree) throws ReflectiveOperationException {
      Field rootField = BST.class.getDeclaredField("root");
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

    /**
     * Recursively validates the Binary Search Tree property.
     * For any node, all keys in its left subtree must be smaller,
     * and all keys in its right subtree must be larger.
     */
    private void validateBSTProperty(Object node, Comparator<Integer> comparator, Integer min, Integer max) throws ReflectiveOperationException {
      if (node == null) return;

      Integer key = getField(node, "key");

      if (min != null && comparator.compare(key, min) <= 0) {
        fail("BST property violated: key " + key + " is not greater than min bound " + min);
      }
      if (max != null && comparator.compare(key, max) >= 0) {
        fail("BST property violated: key " + key + " is not less than max bound " + max);
      }

      validateBSTProperty(getField(node, "left"), comparator, min, key);
      validateBSTProperty(getField(node, "right"), comparator, key, max);
    }

    @Test
    @DisplayName("a sequence of random inserts and deletes should always maintain the BST property")
    void randomOperationsMaintainBSTProperty() throws ReflectiveOperationException {
      BST<Integer> tree = BST.empty();
      java.util.Random rand = new java.util.Random();
      List<Integer> elementsInTree = JDKArrayList.empty();

      for (int i = 0; i < 2000; i++) {
        if (!elementsInTree.isEmpty() && rand.nextFloat() < 0.4) { // 40% chance to delete
          int indexToRemove = rand.nextInt(elementsInTree.size());
          Integer valueToRemove = elementsInTree.get(indexToRemove);
          tree.delete(valueToRemove);
          elementsInTree.delete(indexToRemove);
        } else { // 60% chance to insert
          int valueToInsert = rand.nextInt(1000);
          if (!tree.contains(valueToInsert)) {
            tree.insert(valueToInsert);
            elementsInTree.append(valueToInsert);
          }
        }

        // Validate the entire tree structure after every single modification
        validateBSTProperty(getRootNode(tree), tree.comparator(), null, null);
        assertEquals(elementsInTree.size(), tree.size(), "Size mismatch after operation " + i);
      }
    }
  }
}