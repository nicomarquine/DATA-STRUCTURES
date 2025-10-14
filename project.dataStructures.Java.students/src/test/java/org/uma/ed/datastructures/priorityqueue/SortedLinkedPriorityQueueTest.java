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

@DisplayName("Test cases for class LinkedPriorityQueue")
class SortedLinkedPriorityQueueTest {

  @Nested
  @DisplayName("A LinkedPriorityQueue is created")
  class CreationTests {

    @Test
    @DisplayName("by calling the constructor with a comparator")
    void constructorWithComparator() {
      PriorityQueue<Integer> pq = new SortedLinkedPriorityQueue<Integer>(Comparator.naturalOrder());
      assertNotNull(pq);
      assertTrue(pq.isEmpty());
    }

    @Test
    @DisplayName("by calling the empty() method with a comparator")
    void emptyFactoryWithComparator() {
      Comparator<String> reverseOrder = Comparator.reverseOrder();
      PriorityQueue<String> pq = SortedLinkedPriorityQueue.empty(reverseOrder);
      assertNotNull(pq);
      assertTrue(pq.isEmpty());
      assertEquals(reverseOrder, pq.comparator());
    }

    @Test
    @DisplayName("by calling the empty() method without a comparator (natural order)")
    void emptyFactoryNaturalOrder() {
      PriorityQueue<Integer> pq = SortedLinkedPriorityQueue.empty();
      assertNotNull(pq);
      assertTrue(pq.isEmpty());
      assertEquals(Comparator.naturalOrder(), pq.comparator());
    }

    @Test
    @DisplayName("from a sequence of values using the of() method")
    void fromOfFactory() {
      PriorityQueue<String> pq = SortedLinkedPriorityQueue.of("orange", "blue", "green", "blue");
      assertNotNull(pq);
      assertEquals(4, pq.size());
      assertEquals("blue", pq.first());
    }

    @Test
    @DisplayName("from an iterable of values using the from() method")
    void fromIterableFactory() {
      List<Integer> initialValues = JDKArrayList.of(10, 20, 10, 30, 5);
      PriorityQueue<Integer> pq = SortedLinkedPriorityQueue.from(initialValues);
      assertNotNull(pq);
      assertEquals(5, pq.size());
      assertEquals(5, pq.first());
    }
  }

  @Nested
  @DisplayName("The copyOf() method")
  class CopyOfTests {
    @Test
    @DisplayName("works correctly with an empty LinkedPriorityQueue")
    void copyOfEmpty() {
      SortedLinkedPriorityQueue<Integer> pq = SortedLinkedPriorityQueue.empty();
      SortedLinkedPriorityQueue<Integer> copiedPq = SortedLinkedPriorityQueue.copyOf(pq);
      assertTrue(copiedPq.isEmpty());
      assertEquals(pq, copiedPq);
      assertNotSame(pq, copiedPq);
    }

    @Test
    @DisplayName("works correctly with a non-empty LinkedPriorityQueue")
    void copyOfNonEmpty() {
      SortedLinkedPriorityQueue<String> pq = SortedLinkedPriorityQueue.of("C", "A", "B", "A");
      SortedLinkedPriorityQueue<String> copiedPq = SortedLinkedPriorityQueue.copyOf(pq);
      assertEquals(pq, copiedPq);
      assertNotSame(pq, copiedPq);
    }
  }

  @Nested
  @DisplayName("Basic properties: isEmpty(), size(), clear()")
  class BasicPropertiesTests {
    private PriorityQueue<Integer> pq;

    @BeforeEach
    void setup() {
      pq = SortedLinkedPriorityQueue.empty();
    }

    @Test
    @DisplayName("isEmpty() is true for a new queue and false after enqueue")
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
    }

    @Test
    @DisplayName("clear() empties a non-empty queue")
    void testClear() {
      pq = SortedLinkedPriorityQueue.of(1, 2, 3);
      pq.clear();
      assertTrue(pq.isEmpty());
      assertEquals(0, pq.size());
    }
  }

  @Nested
  @DisplayName("Core operations: enqueue(), first(), dequeue()")
  class CoreOperationsTests {
    private PriorityQueue<Integer> pq;

    @BeforeEach
    void setup() {
      pq = SortedLinkedPriorityQueue.empty();
    }

    @Test
    @DisplayName("enqueue() adds an element to an empty queue")
    void enqueueIntoEmpty() {
      pq.enqueue(42);
      assertEquals(1, pq.size());
      assertEquals(42, pq.first());
    }

    @Test
    @DisplayName("enqueue() maintains the sorted order (insert at head)")
    void enqueueAtHead() {
      pq.enqueue(20);
      pq.enqueue(10);
      assertEquals(10, pq.first());
      assertEquals(2, pq.size());
    }

    @Test
    @DisplayName("enqueue() maintains the sorted order (insert at tail)")
    void enqueueAtTail() {
      pq.enqueue(10);
      pq.enqueue(20);
      assertEquals(10, pq.first());
      assertEquals(2, pq.size());
    }

    @Test
    @DisplayName("enqueue() maintains the sorted order (insert in middle)")
    void enqueueInMiddle() {
      pq.enqueue(10);
      pq.enqueue(30);
      pq.enqueue(20);
      assertEquals(10, pq.first());
      assertEquals(3, pq.size());
    }

    @Test
    @DisplayName("enqueue() handles duplicate values correctly")
    void enqueueDuplicates() {
      pq.enqueue(20);
      pq.enqueue(10);
      pq.enqueue(20);
      assertEquals(3, pq.size());
      assertEquals(10, pq.first());
      pq.dequeue();
      assertEquals(20, pq.first());
    }

    @Test
    @DisplayName("first() returns the minimum element without removing it")
    void firstDoesNotRemove() {
      pq = SortedLinkedPriorityQueue.of(10, 20);
      assertEquals(10, pq.first());
      assertEquals(2, pq.size());
    }

    @Test
    @DisplayName("dequeue() removes the minimum element")
    void dequeueRemovesMinimum() {
      pq = SortedLinkedPriorityQueue.of(10, 20, 5);
      assertEquals(5, pq.first());
      pq.dequeue();
      assertEquals(10, pq.first());
      assertEquals(2, pq.size());
    }

    @Test
    @DisplayName("a full sequence of operations results in a sorted sequence")
    void drainTest() {
      pq = SortedLinkedPriorityQueue.of(5, 1, 4, 2, 3, 3);
      List<Integer> sortedList = JDKArrayList.empty();
      while (!pq.isEmpty()) {
        sortedList.append(pq.first());
        pq.dequeue();
      }
      List<Integer> expected = JDKArrayList.of(1, 2, 3, 3, 4, 5);
      assertEquals(expected, sortedList);
    }
  }

  @Nested
  @DisplayName("Exception handling")
  class ExceptionTests {
    @Test
    @DisplayName("first() throws EmptyPriorityQueueException on an empty queue")
    void firstOnEmptyQueue() {
      PriorityQueue<Integer> pq = SortedLinkedPriorityQueue.empty();
      assertThrows(EmptyPriorityQueueException.class, pq::first);
    }

    @Test
    @DisplayName("dequeue() throws EmptyPriorityQueueException on an empty queue")
    void dequeueOnEmptyQueue() {
      PriorityQueue<Integer> pq = SortedLinkedPriorityQueue.empty();
      assertThrows(EmptyPriorityQueueException.class, pq::dequeue);
    }
  }

  @Nested
  @DisplayName("The elements() iterable")
  class IteratorTests {
    @Test
    @DisplayName("provides an iterator that yields elements in sorted order")
    void elementsIteratorOrder() {
      SortedLinkedPriorityQueue<Integer> pq = SortedLinkedPriorityQueue.of(5, 1, 4, 2, 3);
      List<Integer> expected = JDKArrayList.of(1, 2, 3, 4, 5);
      List<Integer> actual = JDKArrayList.empty();
      pq.elements().forEach(actual::append);
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("for an empty queue provides an empty iterator")
    void elementsIteratorOnEmptyQueue() {
      SortedLinkedPriorityQueue<Integer> pq = SortedLinkedPriorityQueue.empty();
      var iterator = pq.elements().iterator();
      assertFalse(iterator.hasNext());
      assertThrows(NoSuchElementException.class, iterator::next);
    }
  }

  @Nested
  @DisplayName("The equals() and hashCode() methods")
  class EqualityAndHashCodeTests {
    @Test
    @DisplayName("equals() returns true for two identical priority queues")
    void testEqualsWithIdenticalQueues() {
      PriorityQueue<Integer> pq1 = SortedLinkedPriorityQueue.of(1, 2, 3);
      PriorityQueue<Integer> pq2 = SortedLinkedPriorityQueue.of(3, 1, 2);
      assertEquals(pq1, pq2);
      assertEquals(pq1.hashCode(), pq2.hashCode());
    }
  }

  @Nested
  @DisplayName("Behavior with custom comparators")
  class CustomComparatorTests {
    @Test
    @DisplayName("correctly orders elements with a reverse order comparator")
    void testWithReverseOrderComparator() {
      PriorityQueue<Integer> maxQueue = new SortedLinkedPriorityQueue<Integer>(Comparator.reverseOrder());
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