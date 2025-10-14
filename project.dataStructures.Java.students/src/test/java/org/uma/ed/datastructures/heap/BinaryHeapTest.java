package org.uma.ed.datastructures.heap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.uma.ed.datastructures.list.List;
import org.uma.ed.datastructures.list.JDKArrayList;

import java.lang.reflect.Field;
import java.util.Comparator;


import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test cases for class BinaryHeap")
class BinaryHeapTest {

  // ==========================================================
  // BLACK-BOX TESTS (Public API Behavior)
  // ==========================================================

  @Nested
  @DisplayName("A BinaryHeap is created")
  class CreationTests {

    @Test
    @DisplayName("by calling the constructor with a comparator")
    void constructorWithComparator() {
      Heap<Integer> heap = new BinaryHeap<Integer>(Comparator.naturalOrder());
      assertNotNull(heap);
      assertTrue(heap.isEmpty());
    }

    @Test
    @DisplayName("by calling the empty() method")
    void emptyFactory() {
      Heap<Integer> heap = BinaryHeap.empty();
      assertNotNull(heap);
      assertTrue(heap.isEmpty());
      assertEquals(Comparator.naturalOrder(), heap.comparator());
    }

    @Test
    @DisplayName("by calling the withCapacity() method")
    void withCapacityFactory() {
      Heap<Integer> heap = BinaryHeap.withCapacity(50);
      assertNotNull(heap);
      assertTrue(heap.isEmpty());
    }

    @Test
    @DisplayName("from a sequence of values using the of() method")
    void fromOfFactory() {
      Heap<String> heap = BinaryHeap.of("orange", "blue", "green");
      assertNotNull(heap);
      assertEquals(3, heap.size());
      assertEquals("blue", heap.minimum());
    }

    @Test
    @DisplayName("from an iterable of values using the from() method")
    void fromIterableFactory() {
      List<Integer> initialValues = JDKArrayList.of(10, 20, 10, 30);
      Heap<Integer> heap = BinaryHeap.from(initialValues);
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
      BinaryHeap<Integer> heap = BinaryHeap.empty();
      BinaryHeap<Integer> copiedHeap = BinaryHeap.copyOf(heap);
      assertTrue(copiedHeap.isEmpty());
      assertNotSame(heap, copiedHeap);
    }

    @Test
    @DisplayName("works correctly with a non-empty heap")
    void copyOfNonEmptyHeap() {
      BinaryHeap<Integer> heap = BinaryHeap.of(30, 10, 20);
      BinaryHeap<Integer> copiedHeap = BinaryHeap.copyOf(heap);

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
  @DisplayName("Basic properties: isEmpty(), size(), clear()")
  class BasicPropertiesTests {
    private Heap<Integer> heap;

    @BeforeEach void setup() { heap = BinaryHeap.empty(); }

    @Test @DisplayName("isEmpty() is true for new heap and false after insert")
    void testIsEmpty() {
      assertTrue(heap.isEmpty());
      heap.insert(1);
      assertFalse(heap.isEmpty());
    }

    @Test @DisplayName("size() correctly reflects the number of elements")
    void testSize() {
      assertEquals(0, heap.size());
      heap.insert(10);
      heap.insert(20);
      assertEquals(2, heap.size());
      heap.deleteMinimum();
      assertEquals(1, heap.size());
    }

    @Test @DisplayName("clear() empties a non-empty heap")
    void testClear() {
      heap = BinaryHeap.of(1, 2, 3);
      heap.clear();
      assertTrue(heap.isEmpty());
      assertEquals(0, heap.size());
    }
  }

  @Nested
  @DisplayName("Core operations: insert(), minimum(), deleteMinimum()")
  class CoreOperationsTests {

    @Test
    @DisplayName("a full sequence of operations results in a sorted sequence")
    void drainTest() {
      Heap<Integer> heap = BinaryHeap.of(5, 1, 4, 2, 3, 3);
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
      Heap<Integer> heap = BinaryHeap.empty();
      assertThrows(EmptyHeapException.class, heap::minimum);
    }

    @Test
    @DisplayName("deleteMinimum() throws EmptyHeapException on an empty heap")
    void deleteMinimumOnEmptyHeap() {
      Heap<Integer> heap = BinaryHeap.empty();
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
    private int getSize(BinaryHeap<?> heap) throws ReflectiveOperationException {
      Field sizeField = BinaryHeap.class.getDeclaredField("size");
      sizeField.setAccessible(true);
      return (int) sizeField.get(heap);
    }

    private Object[] getElementsArray(BinaryHeap<?> heap) throws ReflectiveOperationException {
      Field elementsField = BinaryHeap.class.getDeclaredField("elements");
      elementsField.setAccessible(true);
      return (Object[]) elementsField.get(heap);
    }

    /**
     * Validates that the internal array satisfies the Min-Heap property.
     */
    private <T> void validateHeapProperty(BinaryHeap<T> heap) throws ReflectiveOperationException {
      Comparator<T> comparator = heap.comparator();
      Object[] elements = getElementsArray(heap);
      int size = getSize(heap);

      // Iterate through all non-leaf nodes
      for (int i = 0; i < size / 2; i++) {
        int leftChildIndex = 2 * i + 1;
        int rightChildIndex = 2 * i + 2;

        @SuppressWarnings("unchecked")
        T parent = (T) elements[i];

        // Check left child
        if (leftChildIndex < size) {
          @SuppressWarnings("unchecked")
          T leftChild = (T) elements[leftChildIndex];
          assertTrue(comparator.compare(parent, leftChild) <= 0,
              "Heap property violated: parent " + parent + " at index " + i + " > left child " + leftChild + " at index " + leftChildIndex);
        }

        // Check right child
        if (rightChildIndex < size) {
          @SuppressWarnings("unchecked")
          T rightChild = (T) elements[rightChildIndex];
          assertTrue(comparator.compare(parent, rightChild) <= 0,
              "Heap property violated: parent " + parent + " at index " + i + " > right child " + rightChild + " at index " + rightChildIndex);
        }
      }
    }

    @Test
    @DisplayName("heap created with of() (heapify) should respect the heap property")
    void heapBuiltWithOfIsValid() throws ReflectiveOperationException {
      BinaryHeap<Integer> heap = BinaryHeap.of(10, 2, 13, 0, 5, 7, 10, 3, 1, 6);
      validateHeapProperty(heap);
    }

    @Test
    @DisplayName("heap created with from() (heapify) should respect the heap property")
    void heapBuiltWithFromIsValid() throws ReflectiveOperationException {
      List<Integer> data = JDKArrayList.of(10, 2, 13, 0, 5, 7, 10, 3, 1, 6);
      BinaryHeap<Integer> heap = BinaryHeap.from(data);
      validateHeapProperty(heap);
    }

    @Test
    @DisplayName("a sequence of random inserts and deletes should always maintain the heap property")
    void randomOperationsMaintainHeapProperty() throws ReflectiveOperationException {
      BinaryHeap<Integer> heap = BinaryHeap.empty();
      java.util.Random rand = new java.util.Random();

      for (int i = 0; i < 5000; i++) {
        if (rand.nextFloat() < 0.4 && !heap.isEmpty()) {
          heap.deleteMinimum();
        } else {
          heap.insert(rand.nextInt(1000));
        }
        // Validate the structure after every single modification
        validateHeapProperty(heap);
      }
    }
  }
}