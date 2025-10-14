package org.uma.ed.datastructures.heap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test cases for class MaxiphobicHeap")
class MaxiphobicHeapTest {

  @Nested
  @DisplayName("A MaxiphobicHeap is created")
  class TestCasesForCreation {

    @Test
    @DisplayName("by calling the constructor with a comparator")
    void whenConstructorIsCalledThenAHeapIsCreated() {
      Heap<Integer> heap = new MaxiphobicHeap<Integer>(Comparator.naturalOrder());
      assertNotNull(heap);
      assertTrue(heap.isEmpty());
    }

    @Test
    @DisplayName("by calling the empty() method with a comparator")
    void whenEmptyWithComparatorIsCalledThenAHeapIsCreated() {
      Comparator<String> reverseOrder = Comparator.reverseOrder();
      Heap<String> heap = MaxiphobicHeap.empty(reverseOrder);
      assertNotNull(heap);
      assertTrue(heap.isEmpty());
      assertEquals(reverseOrder, heap.comparator());
    }

    @Test
    @DisplayName("by calling the empty() method without a comparator (natural order)")
    void whenEmptyIsCalledThenAHeapIsCreated() {
      Heap<Integer> heap = MaxiphobicHeap.empty();
      assertNotNull(heap);
      assertTrue(heap.isEmpty());
      assertEquals(Comparator.naturalOrder(), heap.comparator());
    }

    @Test
    @DisplayName("from a sequence of values using the of() method")
    void whenOfIsCalledThenAHeapIsCreated() {
      Heap<String> heap = MaxiphobicHeap.of("orange", "blue", "green");
      assertNotNull(heap);
      assertEquals(3, heap.size());
      assertEquals("blue", heap.minimum());
    }

    @Test
    @DisplayName("from a sequence with duplicate values using the of() method")
    void whenOfIsCalledWithDuplicatesThenDuplicatesAreIncluded() {
      Heap<Integer> heap = MaxiphobicHeap.of(5, 3, 2, 1, 3, 5, 8);
      assertEquals(7, heap.size());
      assertEquals(1, heap.minimum());
    }

    @Test
    @DisplayName("from an iterable of values using the from() method")
    void givenAnIterableWhenFromIsCalledThenAHeapIsCreated() {
      List<Integer> initialValues = JDKArrayList.of(10, 20, 10, 30);
      Heap<Integer> heap = MaxiphobicHeap.from(initialValues);
      assertNotNull(heap);
      assertEquals(4, heap.size());
      assertEquals(10, heap.minimum());
    }
  }

  @Nested
  @DisplayName("The private static merge() method (O(n) heapify)")
  class StaticMergeMethodTest {

    private Method staticMergeMethod;

    @BeforeEach
    void setup() {
      // Find the specific static merge method that takes a Comparator and an ArrayList.
      // This is different from the instance method merge(Node, Node).
      for (Method method : MaxiphobicHeap.class.getDeclaredMethods()) {
        if (method.getName().equals("merge") && method.getParameterCount() == 2 && (method.getModifiers() & java.lang.reflect.Modifier.STATIC) != 0) {
          staticMergeMethod = method;
          staticMergeMethod.setAccessible(true);
          break;
        }
      }
      assertNotNull(staticMergeMethod, "Could not find private static method 'merge(Comparator, ArrayList)'.");
    }

    /**
     * Helper to drain a heap and return its elements as a sorted list.
     */
    private <T> List<T> drainHeap(MaxiphobicHeap<T> heap) {
      List<T> elements = JDKArrayList.empty();
      while (!heap.isEmpty()) {
        elements.append(heap.minimum());
        heap.deleteMinimum();
      }
      return elements;
    }

    @Test
    @DisplayName("returns an empty heap when given an empty list of nodes")
    void staticMergeWithEmptyList() throws ReflectiveOperationException {
      // Arrange
      org.uma.ed.datastructures.list.List<Object> nodes = org.uma.ed.datastructures.list.JDKArrayList.empty();

      // Act
      @SuppressWarnings("unchecked")
      MaxiphobicHeap<Integer> resultHeap = (MaxiphobicHeap<Integer>) staticMergeMethod.invoke(
          null, // First argument is null for a static method
          Comparator.naturalOrder(),
          nodes
      );

      // Assert
      assertTrue(resultHeap.isEmpty());
    }

    @Test
    @DisplayName("returns a valid heap for a list with an even number of nodes")
    void staticMergeWithEvenNumberOfNodes() throws ReflectiveOperationException {
      // Arrange
      Integer[] data = {10, 2, 8, 5, 1, 9};
      org.uma.ed.datastructures.list.List<Object> nodes = org.uma.ed.datastructures.list.JDKArrayList.empty();
      for (Integer value : data) {
        nodes.append(createNode(value));
      }

      // Act
      @SuppressWarnings("unchecked")
      MaxiphobicHeap<Integer> resultHeap = (MaxiphobicHeap<Integer>) staticMergeMethod.invoke(
          null, Comparator.naturalOrder(), nodes);

      // Assert
      // 1. Check content and order by draining the heap
      java.util.Arrays.sort(data);
      List<Integer> expectedOrder = JDKArrayList.of(data);
      List<Integer> actualOrder = drainHeap(resultHeap);
      assertEquals(expectedOrder, actualOrder, "The heap did not contain the correct elements in sorted order.");

      // 2. Re-build and check structural invariants
      nodes.clear();
      for (Integer value : new Integer[]{10, 2, 8, 5, 1, 9}) { // Unsorted data again
        nodes.append(createNode(value));
      }
      @SuppressWarnings("unchecked")
      MaxiphobicHeap<Integer> heapToInspect = (MaxiphobicHeap<Integer>) staticMergeMethod.invoke(
          null, Comparator.naturalOrder(), nodes);

      assertEquals(data.length, heapToInspect.size());
      validateInvariants(getRootNode(heapToInspect), heapToInspect.comparator());
    }

    @Test
    @DisplayName("returns a valid heap for a list with an odd number of nodes")
    void staticMergeWithOddNumberOfNodes() throws ReflectiveOperationException {
      // Arrange
      Integer[] data = {10, 2, 8, 5, 1, 9, 0};
      org.uma.ed.datastructures.list.List<Object> nodes = org.uma.ed.datastructures.list.JDKArrayList.empty();
      for (Integer value : data) {
        nodes.append(createNode(value));
      }

      // Act
      @SuppressWarnings("unchecked")
      MaxiphobicHeap<Integer> resultHeap = (MaxiphobicHeap<Integer>) staticMergeMethod.invoke(
          null, Comparator.naturalOrder(), nodes);

      // Assert
      // 1. Check content and order
      java.util.Arrays.sort(data);
      List<Integer> expectedOrder = JDKArrayList.of(data);
      List<Integer> actualOrder = drainHeap(resultHeap);
      assertEquals(expectedOrder, actualOrder, "The heap did not contain the correct elements in sorted order.");

      // 2. Re-build and check structural invariants
      nodes.clear();
      for (Integer value : new Integer[]{10, 2, 8, 5, 1, 9, 0}) {
        nodes.append(createNode(value));
      }
      @SuppressWarnings("unchecked")
      MaxiphobicHeap<Integer> heapToInspect = (MaxiphobicHeap<Integer>) staticMergeMethod.invoke(
          null, Comparator.naturalOrder(), nodes);

      assertEquals(data.length, heapToInspect.size());
      validateInvariants(getRootNode(heapToInspect), heapToInspect.comparator());
    }
  }

  @Nested
  @DisplayName("The copyOf() method")
  class TestCasesForCopyOf {

    @Test
    @DisplayName("works correctly with an empty heap")
    void givenAnEmptyHeapWhenCopyOfThenTheNewHeapIsAlsoEmpty() {
      MaxiphobicHeap<Integer> heap = MaxiphobicHeap.empty();
      MaxiphobicHeap<Integer> copiedHeap = MaxiphobicHeap.copyOf(heap);
      assertTrue(copiedHeap.isEmpty());
      assertNotSame(heap, copiedHeap);
    }

    @Test
    @DisplayName("works correctly with a non-empty heap")
    void givenANonEmptyHeapWhenCopyOfThenTheNewHeapIsEqualInContent() {
      MaxiphobicHeap<Integer> heap = MaxiphobicHeap.of(30, 10, 20);
      MaxiphobicHeap<Integer> copiedHeap = MaxiphobicHeap.copyOf(heap);

      assertEquals(heap.size(), copiedHeap.size());
      assertEquals(heap.minimum(), copiedHeap.minimum());
      assertNotSame(heap, copiedHeap);

      // Drain both to verify full content equality
      while(!heap.isEmpty()) {
        assertEquals(heap.minimum(), copiedHeap.minimum());
        heap.deleteMinimum();
        copiedHeap.deleteMinimum();
      }
    }
  }

  @Nested
  @DisplayName("Basic properties: isEmpty(), size(), clear()")
  class TestCasesForBasicProperties {

    @Test
    @DisplayName("isEmpty() returns true for a new heap and false after insert")
    void testIsEmpty() {
      Heap<Integer> heap = MaxiphobicHeap.empty();
      assertTrue(heap.isEmpty());
      heap.insert(1);
      assertFalse(heap.isEmpty());
    }

    @Test
    @DisplayName("size() correctly reflects the number of elements")
    void testSize() {
      Heap<Integer> heap = MaxiphobicHeap.empty();
      assertEquals(0, heap.size());
      heap.insert(10);
      heap.insert(20);
      assertEquals(2, heap.size());
      heap.deleteMinimum();
      assertEquals(1, heap.size());
    }

    @Test
    @DisplayName("clear() empties a non-empty heap")
    void whenClearIsCalledThenTheHeapIsEmpty() {
      Heap<Integer> heap = MaxiphobicHeap.of(1, 2, 3);
      assertFalse(heap.isEmpty());
      heap.clear();
      assertTrue(heap.isEmpty());
      assertEquals(0, heap.size());
    }
  }

  @Nested
  @DisplayName("Core operations: insert(), minimum(), deleteMinimum()")
  class TestCasesForCoreOperations {

    @Test
    @DisplayName("insert() maintains the heap property")
    void whenInsertMultipleElementsThenMinimumIsAlwaysCorrect() {
      Heap<Integer> heap = MaxiphobicHeap.empty();
      heap.insert(30);
      assertEquals(30, heap.minimum());
      heap.insert(10);
      assertEquals(10, heap.minimum());
      heap.insert(20);
      assertEquals(10, heap.minimum());
    }

    @Test
    @DisplayName("minimum() returns the smallest element without removing it")
    void whenMinimumIsCalledThenSizeIsUnchanged() {
      Heap<Integer> heap = MaxiphobicHeap.of(10, 20);
      assertEquals(10, heap.minimum());
      assertEquals(2, heap.size());
    }

    @Test
    @DisplayName("deleteMinimum() removes the smallest element")
    void whenDeleteMinimumIsCalledThenMinimumIsRemoved() {
      Heap<Integer> heap = MaxiphobicHeap.of(10, 20, 5);
      assertEquals(5, heap.minimum());
      heap.deleteMinimum();
      assertEquals(10, heap.minimum());
      assertEquals(2, heap.size());
    }

    @Test
    @DisplayName("a full sequence of operations results in a sorted sequence")
    void whenDrainingTheHeapElementsAreSorted() {
      Heap<Integer> heap = MaxiphobicHeap.of(5, 1, 4, 2, 3, 3);
      List<Integer> sortedList = JDKArrayList.empty();
      while(!heap.isEmpty()) {
        sortedList.append(heap.minimum());
        heap.deleteMinimum();
      }
      assertEquals(JDKArrayList.of(1, 2, 3, 3, 4, 5), sortedList);
    }
  }

  @Nested
  @DisplayName("Exception handling")
  class TestCasesForExceptions {
    @Test
    @DisplayName("minimum() throws EmptyHeapException on an empty heap")
    void whenMinimumOnEmptyHeapThenThrowsException() {
      Heap<Integer> heap = MaxiphobicHeap.empty();
      assertThrows(EmptyHeapException.class, heap::minimum);
    }

    @Test
    @DisplayName("deleteMinimum() throws EmptyHeapException on an empty heap")
    void whenDeleteMinimumOnEmptyHeapThenThrowsException() {
      Heap<Integer> heap = MaxiphobicHeap.empty();
      assertThrows(EmptyHeapException.class, heap::deleteMinimum);
    }
  }

  @Nested
  @DisplayName("Structural Integrity Tests (White-Box)")
  class StructuralIntegrityTests {

    @Nested
    @DisplayName("The private merge() method's base cases")
    class MergeMethodBaseCases {
      private MaxiphobicHeap<Integer> heapInstance;
      private Method mergeMethod;

      @BeforeEach
      void setup() throws ReflectiveOperationException {
        heapInstance = createHeap();
        mergeMethod = getMergeMethod();
      }

      @Test @DisplayName("returns null when both arguments are null")
      void mergeWithTwoNulls() throws ReflectiveOperationException {
        Object result = mergeMethod.invoke(heapInstance, null, null);
        assertNull(result);
      }

      @Test @DisplayName("returns the non-null node when the first is null")
      void mergeWithFirstNull() throws ReflectiveOperationException {
        Object node = createNode(100);
        Object result = mergeMethod.invoke(heapInstance, null, node);
        assertSame(node, result);
      }

      @Test @DisplayName("returns the non-null node when the second is null")
      void mergeWithSecondNull() throws ReflectiveOperationException {
        Object node = createNode(100);
        Object result = mergeMethod.invoke(heapInstance, node, null);
        assertSame(node, result);
      }
    }

    @Nested
    @DisplayName("The private merge() method's recursive logic")
    class MergeMethodRecursiveLogic {
      private Object buildTreeOfWeight(int targetWeight, int value) throws ReflectiveOperationException {
        if (targetWeight <= 0) return null;
        MaxiphobicHeap<Integer> heap = createHeap();
        Method merge = getMergeMethod();
        Object currentTree = createNode(value);
        for (int i = 1; i < targetWeight; i++) {
          currentTree = merge.invoke(heap, currentTree, createNode(value + i));
        }
        return currentTree;
      }

      private void assertMaxiphobicMergeLogic(Object candidate1, Object candidate2, Object candidate3) throws ReflectiveOperationException {
        MaxiphobicHeap<Integer> heapInstance = createHeap();
        Method mergeMethod = getMergeMethod();

        Object node1 = createNode(10);
        setField(node1, "left", candidate1);
        setField(node1, "right", candidate2);
        setField(node1, "weight", 1 + weight(candidate1) + weight(candidate2));
        Object node2 = candidate3;

        Object heaviest = candidate1;
        if (weight(candidate2) > weight(heaviest)) heaviest = candidate2;
        if (weight(candidate3) > weight(heaviest)) heaviest = candidate3;

        int expectedRightWeight = weight(candidate1) + weight(candidate2) + weight(candidate3) - weight(heaviest);
        Object mergedNode = mergeMethod.invoke(heapInstance, node1, node2);

        Object newLeftChild = getField(mergedNode, "left");
        Object newRightChild = getField(mergedNode, "right");

        assertSame(heaviest, newLeftChild, "The heaviest candidate was not assigned to the left child.");
        assertEquals(expectedRightWeight, weight(newRightChild), "The right child's weight is incorrect.");

        validateInvariants(mergedNode, Comparator.naturalOrder());
      }

      @Test @DisplayName("positions heaviest correctly: heavy, medium, light")
      void mergeHeavyMediumLight() throws ReflectiveOperationException {
        assertMaxiphobicMergeLogic(buildTreeOfWeight(3, 100), buildTreeOfWeight(2, 200), buildTreeOfWeight(1, 300));
      }

      @Test @DisplayName("positions heaviest correctly: medium, heavy, light")
      void mergeMediumHeavyLight() throws ReflectiveOperationException {
        assertMaxiphobicMergeLogic(buildTreeOfWeight(2, 100), buildTreeOfWeight(3, 200), buildTreeOfWeight(1, 300));
      }

      @Test @DisplayName("positions heaviest correctly: light, medium, heavy")
      void mergeLightMediumHeavy() throws ReflectiveOperationException {
        assertMaxiphobicMergeLogic(buildTreeOfWeight(1, 100), buildTreeOfWeight(2, 200), buildTreeOfWeight(3, 300));
      }

      @Test @DisplayName("handles tie for heaviest: heavy, heavy, light")
      void mergeTieHeavyHeavyLight() throws ReflectiveOperationException {
        assertMaxiphobicMergeLogic(buildTreeOfWeight(3, 100), buildTreeOfWeight(3, 200), buildTreeOfWeight(1, 300));
      }
    }

    @Nested
    @DisplayName("After random modification operations")
    class AfterRandomModification {
      @Test @DisplayName("all invariants should be maintained")
      void randomOperationsMaintainInvariants() throws ReflectiveOperationException {
        MaxiphobicHeap<Integer> heap = MaxiphobicHeap.of(100, 20, 50, 10, 80);
        Comparator<Integer> comparator = heap.comparator();
        java.util.Random rand = new java.util.Random();
        for (int i = 0; i < 2000; i++) {
          if (rand.nextFloat() < 0.4 && !heap.isEmpty()) {
            heap.deleteMinimum();
          } else {
            heap.insert(rand.nextInt(1000));
          }
          validateInvariants(getRootNode(heap), comparator);
        }
      }
    }
  }

  // --- Reflection Helper Methods (Now Static) ---
  private static Class<?> getNodeClass() throws NoSuchFieldException {
    Field rootField = MaxiphobicHeap.class.getDeclaredField("root");
    return rootField.getType();
  }

  private static Object getRootNode(MaxiphobicHeap<?> heap) throws ReflectiveOperationException {
    Field rootField = MaxiphobicHeap.class.getDeclaredField("root");
    rootField.setAccessible(true);
    return rootField.get(heap);
  }

  @SuppressWarnings("unchecked")
  private static <T> T getField(Object obj, String fieldName) throws ReflectiveOperationException {
    Field field = obj.getClass().getDeclaredField(fieldName);
    field.setAccessible(true);
    return (T) field.get(obj);
  }

  private static Method getMergeMethod() throws NoSuchMethodException, NoSuchFieldException {
    Class<?> nodeClass = getNodeClass();
    Method mergeMethod = MaxiphobicHeap.class.getDeclaredMethod("merge", nodeClass, nodeClass);
    mergeMethod.setAccessible(true);
    return mergeMethod;
  }

  private static Object createNode(Object element) throws ReflectiveOperationException {
    Class<?> nodeClass = getNodeClass();
    var constructor = nodeClass.getDeclaredConstructor(Object.class);
    constructor.setAccessible(true);
    return constructor.newInstance(element);
  }

  private static int weight(Object node) throws ReflectiveOperationException {
    return node == null ? 0 : (int) getField(node, "weight");
  }

  private static void setField(Object obj, String fieldName, Object value) throws ReflectiveOperationException {
    Field field = obj.getClass().getDeclaredField(fieldName);
    field.setAccessible(true);
    field.set(obj, value);
  }

  private static MaxiphobicHeap<Integer> createHeap() {
    return MaxiphobicHeap.<Integer>empty(Comparator.naturalOrder());
  }

  // --- Invariant Validation Logic (Now Static) ---
  private static void validateInvariants(Object node, Comparator<Integer> comparator) throws ReflectiveOperationException {
    if (node == null) return;
    Integer element = getField(node, "element");
    Object left = getField(node, "left");
    Object right = getField(node, "right");
    if (left != null) assertTrue(comparator.compare(element, getField(left, "element")) <= 0);
    if (right != null) assertTrue(comparator.compare(element, getField(right, "element")) <= 0);
    assertEquals(1 + weight(left) + weight(right), weight(node));
    validateInvariants(left, comparator);
    validateInvariants(right, comparator);
  }
}