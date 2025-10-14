package org.uma.ed.datastructures.heap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test cases for class WBLeftistHeap")
class WBLeftistHeapTest {

  // ==========================================================
  // BLACK-BOX TESTS (Public API Behavior)
  // ==========================================================

  @Nested
  @DisplayName("A WBLeftistHeap is created")
  class CreationTests {

    @Test
    @DisplayName("by calling the constructor with a comparator")
    void constructorWithComparator() {
      Heap<Integer> heap = new WBLeftistHeap<Integer>(Comparator.naturalOrder());
      assertNotNull(heap);
      assertTrue(heap.isEmpty());
    }

    @Test
    @DisplayName("by calling the empty() method")
    void emptyFactory() {
      Heap<Integer> heap = WBLeftistHeap.empty();
      assertNotNull(heap);
      assertTrue(heap.isEmpty());
      assertEquals(Comparator.naturalOrder(), heap.comparator());
    }

    @Test
    @DisplayName("from a sequence of values using the of() method")
    void fromOfFactory() {
      Heap<String> heap = WBLeftistHeap.of("orange", "blue", "green");
      assertNotNull(heap);
      assertEquals(3, heap.size());
      assertEquals("blue", heap.minimum());
    }

    @Test
    @DisplayName("from an iterable of values using the from() method")
    void fromIterableFactory() {
      List<Integer> initialValues = JDKArrayList.of(10, 20, 10, 30);
      Heap<Integer> heap = WBLeftistHeap.from(initialValues);
      assertNotNull(heap);
      assertEquals(4, heap.size());
      assertEquals(10, heap.minimum());
    }
  }

  @Nested
  @DisplayName("The copyOf() method")
  class CopyOfTests {
    @Test
    @DisplayName("works correctly with an empty heap")
    void copyOfEmptyHeap() {
      WBLeftistHeap<Integer> heap = WBLeftistHeap.empty();
      WBLeftistHeap<Integer> copiedHeap = WBLeftistHeap.copyOf(heap);
      assertTrue(copiedHeap.isEmpty());
      assertNotSame(heap, copiedHeap);
    }

    @Test
    @DisplayName("works correctly with a non-empty heap")
    void copyOfNonEmptyHeap() {
      WBLeftistHeap<Integer> heap = WBLeftistHeap.of(30, 10, 20);
      WBLeftistHeap<Integer> copiedHeap = WBLeftistHeap.copyOf(heap);
      assertEquals(heap.size(), copiedHeap.size());
      assertNotSame(heap, copiedHeap);
      while (!heap.isEmpty()) {
        assertEquals(heap.minimum(), copiedHeap.minimum());
        heap.deleteMinimum();
        copiedHeap.deleteMinimum();
      }
    }
  }

  @Nested
  @DisplayName("Core functional operations")
  class CoreOperationsTests {

    @Test
    @DisplayName("a full sequence of inserts and deletes results in a sorted sequence")
    void drainTest() {
      Heap<Integer> heap = WBLeftistHeap.of(5, 1, 4, 2, 3, 3);
      List<Integer> sortedList = JDKArrayList.empty();
      while(!heap.isEmpty()) {
        sortedList.append(heap.minimum());
        heap.deleteMinimum();
      }
      List<Integer> expected = JDKArrayList.of(1, 2, 3, 3, 4, 5);
      assertEquals(expected, sortedList);
    }

    @Test
    @DisplayName("minimum() throws EmptyHeapException on an empty heap")
    void minimumOnEmptyHeap() {
      Heap<Integer> heap = WBLeftistHeap.empty();
      assertThrows(EmptyHeapException.class, heap::minimum);
    }

    @Test
    @DisplayName("deleteMinimum() throws EmptyHeapException on an empty heap")
    void deleteMinimumOnEmptyHeap() {
      Heap<Integer> heap = WBLeftistHeap.empty();
      assertThrows(EmptyHeapException.class, heap::deleteMinimum);
    }
  }

  // ==========================================================
  // WHITE-BOX TESTS (Structural Integrity)
  // ==========================================================

  @Nested
  @DisplayName("Structural Integrity Tests (White-Box)")
  class StructuralIntegrityTests {

    // --- Reflection Helper Methods ---
    private Object getRootNode(WBLeftistHeap<?> heap) throws ReflectiveOperationException {
      Field rootField = WBLeftistHeap.class.getDeclaredField("root");
      rootField.setAccessible(true);
      return rootField.get(heap);
    }

    private <T> T getField(Object obj, String fieldName) throws ReflectiveOperationException {
      Field field = obj.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      @SuppressWarnings("unchecked")
      T value = (T) field.get(obj);
      return value;
    }

    private int weight(Object node) throws ReflectiveOperationException {
      return node == null ? 0 : (int) getField(node, "weight");
    }

    /**
     * Validates the three core invariants of a Weight-Biased Leftist Heap.
     */
    private void validateInvariants(Object node, Comparator<Integer> comparator) throws ReflectiveOperationException {
      if (node == null) return;

      Integer element = getField(node, "element");
      Object left = getField(node, "left");
      Object right = getField(node, "right");

      // 1. Min-Heap Property
      if (left != null) {
        assertTrue(comparator.compare(element, getField(left, "element")) <= 0,
            "Heap property violated at node " + element);
      }
      if (right != null) {
        assertTrue(comparator.compare(element, getField(right, "element")) <= 0,
            "Heap property violated at node " + element);
      }

      // 2. Weight-Biased Leftist Property
      assertTrue(weight(left) >= weight(right),
          "Leftist property violated at node " + element + ": left weight=" + weight(left) + ", right weight=" + weight(right));

      // 3. Weight Consistency Property
      assertEquals(1 + weight(left) + weight(right), weight(node),
          "Weight inconsistency at node " + element);

      // 4. Recurse on children
      validateInvariants(left, comparator);
      validateInvariants(right, comparator);
    }

    @Test
    @DisplayName("heap created with of() (O(n) heapify) should respect all invariants")
    void heapBuiltWithOfIsValid() throws ReflectiveOperationException {
      WBLeftistHeap<Integer> heap = WBLeftistHeap.of(10, 2, 13, 0, 5, 7, 10, 3, 1, 6);
      validateInvariants(getRootNode(heap), heap.comparator());
    }

    @Test
    @DisplayName("heap created with from() (O(n) heapify) should respect all invariants")
    void heapBuiltWithFromIsValid() throws ReflectiveOperationException {
      List<Integer> data = JDKArrayList.of(10, 2, 13, 0, 5, 7, 10, 3, 1, 6);
      WBLeftistHeap<Integer> heap = WBLeftistHeap.from(data);
      validateInvariants(getRootNode(heap), heap.comparator());
    }

    @Test
    @DisplayName("a sequence of random inserts and deletes should always maintain all invariants")
    void randomOperationsMaintainInvariants() throws ReflectiveOperationException {
      WBLeftistHeap<Integer> heap = WBLeftistHeap.empty();
      Comparator<Integer> comparator = heap.comparator();
      java.util.Random rand = new java.util.Random();

      for (int i = 0; i < 5000; i++) {
        if (rand.nextFloat() < 0.4 && !heap.isEmpty()) {
          heap.deleteMinimum();
        } else {
          heap.insert(rand.nextInt(1000));
        }
        // Validate the entire tree structure after every single modification
        validateInvariants(getRootNode(heap), comparator);
      }
    }

    @Nested
    @DisplayName("The private merge() method base cases")
    class MergeMethodBaseCases {

      @Test
      @DisplayName("should be handled correctly")
      void mergeBaseCases() throws ReflectiveOperationException {
        // We need to test the private merge method.
        WBLeftistHeap<Integer> heapInstance = WBLeftistHeap.empty();
        Class<?> nodeClass = WBLeftistHeap.class.getDeclaredClasses()[0]; // Assumes Node is the first inner class
        Method mergeMethod = WBLeftistHeap.class.getDeclaredMethod("merge", nodeClass, nodeClass);
        mergeMethod.setAccessible(true);

        // Case 1: merge(null, null)
        Object result1 = mergeMethod.invoke(heapInstance, null, null);
        assertNull(result1);

        // Case 2: merge(node, null)
        Object node = nodeClass.getDeclaredConstructor(Object.class).newInstance(100);
        Object result2 = mergeMethod.invoke(heapInstance, node, null);
        assertSame(node, result2);

        // Case 3: merge(null, node)
        Object result3 = mergeMethod.invoke(heapInstance, null, node);
        assertSame(node, result3);
      }
    }
  }
}