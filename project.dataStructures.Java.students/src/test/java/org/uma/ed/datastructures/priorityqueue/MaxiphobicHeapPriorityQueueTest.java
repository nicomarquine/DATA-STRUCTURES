package org.uma.ed.datastructures.priorityqueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;

import java.util.Comparator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test cases for class MaxiphobicHeapPriorityQueue")
class MaxiphobicHeapPriorityQueueTest {

  @Nested
  @DisplayName("A MaxiphobicHeapPriorityQueue is created")
  class TestCasesForCreation {

    @Test
    @DisplayName("by calling the constructor with a comparator")
    void whenTheConstructorIsCalledThenAPriorityQueueIsCreated() {
      // Arrange
      PriorityQueue<Integer> pq = new MaxiphobicHeapPriorityQueue<Integer>(Comparator.naturalOrder());

      // Assert
      assertNotNull(pq);
      assertTrue(pq.isEmpty());
    }

    @Test
    @DisplayName("by calling the empty() method with a comparator")
    void whenEmptyWithComparatorIsCalledThenAPriorityQueueIsCreated() {
      // Arrange
      Comparator<String> reverseOrder = Comparator.reverseOrder();
      PriorityQueue<String> pq = MaxiphobicHeapPriorityQueue.empty(reverseOrder);

      // Assert
      assertNotNull(pq);
      assertTrue(pq.isEmpty());
      assertEquals(reverseOrder, pq.comparator());
    }

    @Test
    @DisplayName("by calling the empty() method without a comparator (natural order)")
    void whenEmptyIsCalledThenAnPriorityQueueIsCreated() {
      // Arrange
      PriorityQueue<Integer> pq = MaxiphobicHeapPriorityQueue.empty();

      // Assert
      assertNotNull(pq);
      assertTrue(pq.isEmpty());
      assertEquals(Comparator.naturalOrder(), pq.comparator());
    }

    @Test
    @DisplayName("from a sequence of values using the of() method")
    void whenOfIsCalledThenAHeapIsCreated() {
      // Arrange
      PriorityQueue<String> pq = MaxiphobicHeapPriorityQueue.of("orange", "blue", "green");

      // Assert
      assertNotNull(pq);
      assertEquals(3, pq.size());
      assertEquals("blue", pq.first());
    }

    @Test
    @DisplayName("from a sequence with duplicate values using the of() method")
    void whenOfIsCalledWithDuplicatesThenDuplicatesAreIncluded() {
      // Arrange
      PriorityQueue<Integer> pq = MaxiphobicHeapPriorityQueue.of(5, 3, 2, 1, 3, 5, 8);

      // Assert
      assertEquals(7, pq.size());
      assertEquals(1, pq.first());
    }

    @Test
    @DisplayName("from an iterable of values using the from() method")
    void givenAnIterableWhenFromIsCalledThenAHeapIsCreated() {
      // Arrange
      List<Integer> initialValues = JDKArrayList.of(10, 20, 10, 30);
      PriorityQueue<Integer> pq = MaxiphobicHeapPriorityQueue.from(initialValues);

      // Assert
      assertNotNull(pq);
      assertEquals(4, pq.size());
      assertEquals(10, pq.first());
    }
  }

  @Nested
  @DisplayName("The copyOf() method")
  class TestCasesForCopyOf {

    @Test
    @DisplayName("works correctly with an empty queue")
    void givenAnEmptyQueueWhenCopyOfThenTheNewQueueIsAlsoEmpty() {
      // Arrange
      MaxiphobicHeapPriorityQueue<Integer> pq = MaxiphobicHeapPriorityQueue.empty();

      // Act
      MaxiphobicHeapPriorityQueue<Integer> copiedPq = MaxiphobicHeapPriorityQueue.copyOf(pq);

      // Assert
      assertTrue(copiedPq.isEmpty());
      assertNotSame(pq, copiedPq);
      assertEquals(pq, copiedPq);
    }

    @Test
    @DisplayName("works correctly with a non-empty queue")
    void givenANonEmptyQueueWhenCopyOfThenTheNewQueueIsEqualInContent() {
      // Arrange
      MaxiphobicHeapPriorityQueue<String> pq = MaxiphobicHeapPriorityQueue.of("C", "A", "B");

      // Act
      MaxiphobicHeapPriorityQueue<String> copiedPq = MaxiphobicHeapPriorityQueue.copyOf(pq);

      // Assert
      assertEquals(pq.size(), copiedPq.size());
      assertEquals(pq.first(), copiedPq.first());
      assertNotSame(pq, copiedPq);

      // Drain both to verify full content equality
      while(!pq.isEmpty()) {
        assertEquals(pq.first(), copiedPq.first());
        pq.dequeue();
        copiedPq.dequeue();
      }
      assertTrue(copiedPq.isEmpty());
    }
  }

  @Nested
  @DisplayName("Basic properties: isEmpty(), size(), clear()")
  class TestCasesForBasicProperties {

    private PriorityQueue<Integer> pq;

    @BeforeEach
    void setup() {
      pq = MaxiphobicHeapPriorityQueue.empty();
    }

    @Test
    @DisplayName("isEmpty() is true for new queue and false after enqueue")
    void testIsEmpty() {
      assertTrue(pq.isEmpty());
      pq.enqueue(1);
      assertFalse(pq.isEmpty());
    }

    @Test
    @DisplayName("size() correctly reflects the number of elements")
    void testSize() {
      assertEquals(0, pq.size());
      pq.enqueue(10);
      pq.enqueue(20);
      assertEquals(2, pq.size());
      pq.dequeue();
      assertEquals(1, pq.size());
      pq.clear();
      assertEquals(0, pq.size());
    }

    @Test
    @DisplayName("clear() empties a non-empty queue")
    void whenClearIsCalledThenTheQueueIsEmpty() {
      pq = MaxiphobicHeapPriorityQueue.of(1, 2, 3);
      assertFalse(pq.isEmpty());
      pq.clear();
      assertTrue(pq.isEmpty());
      assertEquals(0, pq.size());
    }
  }

  @Nested
  @DisplayName("Core operations: enqueue(), first(), dequeue()")
  class TestCasesForCoreOperations {

    private PriorityQueue<Integer> pq;

    @BeforeEach
    void setup() {
      pq = MaxiphobicHeapPriorityQueue.empty();
    }

    @Test
    @DisplayName("enqueue() adds an element to an empty queue")
    void whenEnqueueIntoEmptyQueueThenSizeIsOne() {
      pq.enqueue(42);
      assertEquals(1, pq.size());
      assertEquals(42, pq.first());
    }

    @Test
    @DisplayName("enqueue() maintains the heap property")
    void whenEnqueueMultipleElementsThenFirstIsAlwaysTheMinimum() {
      pq.enqueue(30);
      assertEquals(30, pq.first());
      pq.enqueue(10);
      assertEquals(10, pq.first());
      pq.enqueue(20);
      assertEquals(10, pq.first());
    }

    @Test
    @DisplayName("first() returns the minimum element without removing it")
    void whenFirstIsCalledThenSizeIsUnchanged() {
      pq = MaxiphobicHeapPriorityQueue.of(10, 20);
      assertEquals(10, pq.first());
      assertEquals(2, pq.size());
      assertEquals(10, pq.first()); // Call again to be sure
    }

    @Test
    @DisplayName("dequeue() removes the minimum element")
    void whenDequeueIsCalledThenMinimumIsRemoved() {
      pq = MaxiphobicHeapPriorityQueue.of(10, 20, 5);
      assertEquals(5, pq.first());
      pq.dequeue();
      assertEquals(10, pq.first());
      assertEquals(2, pq.size());
    }

    @Test
    @DisplayName("a full sequence of operations results in a sorted sequence")
    void whenDrainingTheQueueElementsAreSorted() {
      pq = MaxiphobicHeapPriorityQueue.of(5, 1, 4, 2, 3, 3);
      List<Integer> sortedList = JDKArrayList.empty();
      while(!pq.isEmpty()) {
        sortedList.append(pq.first());
        pq.dequeue();
      }
      // Using ArrayList's toString for comparison
      assertEquals(JDKArrayList.of(1, 2, 3, 3, 4, 5), sortedList);
    }
  }

  @Nested
  @DisplayName("Exception handling")
  class TestCasesForExceptions {

    @Test
    @DisplayName("first() throws EmptyPriorityQueueException on an empty queue")
    void whenFirstOnEmptyQueueThenThrowsException() {
      PriorityQueue<Integer> pq = MaxiphobicHeapPriorityQueue.empty();
      assertThrows(EmptyPriorityQueueException.class, pq::first);
    }

    @Test
    @DisplayName("dequeue() throws EmptyPriorityQueueException on an empty queue")
    void whenDequeueOnEmptyQueueThenThrowsException() {
      PriorityQueue<Integer> pq = MaxiphobicHeapPriorityQueue.empty();
      assertThrows(EmptyPriorityQueueException.class, pq::dequeue);
    }
  }

  @Nested
  @DisplayName("The elements() iterable")
  class IteratorTests {
    @Test
    @DisplayName("provides an iterator that yields elements in sorted order")
    void elementsIteratorOrder() {
      MaxiphobicHeapPriorityQueue<Integer> pq = MaxiphobicHeapPriorityQueue.of(5, 1, 4, 2, 3);
      List<Integer> expected = JDKArrayList.of(1, 2, 3, 4, 5);
      List<Integer> actual = JDKArrayList.empty();
      pq.elements().forEach(actual::append);
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("for an empty queue provides an empty iterator")
    void elementsIteratorOnEmptyQueue() {
      MaxiphobicHeapPriorityQueue<Integer> pq = MaxiphobicHeapPriorityQueue.empty();
      var iterator = pq.elements().iterator();
      assertFalse(iterator.hasNext());
      assertThrows(NoSuchElementException.class, iterator::next);
    }
  }

  @Nested
  @DisplayName("The equals() and hashCode() methods")
  class TestCasesForEqualsAndHashCode {

    @Test
    @DisplayName("equals() returns true for two identical priority queues")
    void testEqualsWithIdenticalQueues() {
      PriorityQueue<Integer> pq1 = MaxiphobicHeapPriorityQueue.of(1, 2, 3);
      PriorityQueue<Integer> pq2 = MaxiphobicHeapPriorityQueue.of(3, 1, 2);
      assertEquals(pq1, pq2);
      assertEquals(pq1.hashCode(), pq2.hashCode());
    }

    @Test
    @DisplayName("equals() returns false for queues with different sizes")
    void testEqualsWithDifferentSizes() {
      PriorityQueue<Integer> pq1 = MaxiphobicHeapPriorityQueue.of(1, 2);
      PriorityQueue<Integer> pq2 = MaxiphobicHeapPriorityQueue.of(1, 2, 3);
      assertNotEquals(pq1, pq2);
    }

    @Test
    @DisplayName("equals() returns false for queues with different content")
    void testEqualsWithDifferentContent() {
      PriorityQueue<Integer> pq1 = MaxiphobicHeapPriorityQueue.of(1, 2, 3);
      PriorityQueue<Integer> pq2 = MaxiphobicHeapPriorityQueue.of(1, 2, 4);
      assertNotEquals(pq1, pq2);
    }

    @Test
    @DisplayName("equals() returns false for queues with same elements but different counts")
    void testEqualsWithDifferentCounts() {
      PriorityQueue<Integer> pq1 = MaxiphobicHeapPriorityQueue.of(1, 1, 2);
      PriorityQueue<Integer> pq2 = MaxiphobicHeapPriorityQueue.of(1, 2, 2);
      assertNotEquals(pq1, pq2);
    }
  }

  @Nested
  @DisplayName("Behavior with custom comparators")
  class TestCasesForCustomComparators {

    @Test
    @DisplayName("correctly orders elements with a reverse order comparator")
    void testWithReverseOrderComparator() {
      PriorityQueue<Integer> maxQueue = new MaxiphobicHeapPriorityQueue<Integer>(Comparator.reverseOrder());
      maxQueue.enqueue(10);
      maxQueue.enqueue(30);
      maxQueue.enqueue(20);

      assertEquals(30, maxQueue.first()); // 30 is the "minimum" in reverse order
      maxQueue.dequeue();
      assertEquals(20, maxQueue.first());
      maxQueue.dequeue();
      assertEquals(10, maxQueue.first());
    }
  }
}