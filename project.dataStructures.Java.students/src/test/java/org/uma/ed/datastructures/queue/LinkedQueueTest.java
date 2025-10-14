package org.uma.ed.datastructures.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.uma.ed.datastructures.list.ArrayList;
import org.uma.ed.datastructures.list.List;

@DisplayName("Test cases for class LinkedQueue")
class LinkedQueueTest {

  @Nested
  @DisplayName("An LinkedQueue is created")
  class TestCasesForCreatingQueues {

    @Test
    @DisplayName("by calling the default constructor")
    void whenTheConstructorIsCalledThenAnQueueIsCreated() {
      // Arrange
      Queue<Integer> queue = new LinkedQueue<>();

      // Assert
      assertNotNull(queue);
      assertTrue(queue.isEmpty());
    }

    @Test
    @DisplayName("by calling the empty() method")
    void whenTheEmptyMethodIsCalledThenAnQueueIsCreated() {
      // Arrange
      Queue<Integer> Queue = LinkedQueue.empty();

      // Assert
      assertNotNull(Queue);
      assertTrue(Queue.isEmpty());
    }

    @Test
    @DisplayName("from a sequence of values using the of() method")
    void whenTheOfMethodIsCalledThenAQueueIsCreated() {
      // Arrange
      Queue<String> Queue = LinkedQueue.of("orange", "blue", "green");

      // Assert
      assertNotNull(Queue);

      int expectedElementsInTheQueue = 3;
      assertEquals(expectedElementsInTheQueue, Queue.size());
    }

    @Test
    @DisplayName("from an iterable (list) of values using the from() method")
    void givenAListOfValuesWhenTheFromMethodIsCalledThenAnLinkedQueueIsCreated() {
      // Arrange
      List<Integer> initialValues = ArrayList.of(1, 2, 3, 4, 5);
      Queue<Integer> Queue = LinkedQueue.from(initialValues);

      // Assert
      assertNotNull(Queue);

      int expectedElementsInTheQueue = initialValues.size();
      assertEquals(expectedElementsInTheQueue, Queue.size());
    }
  }

  @Nested
  @DisplayName("The copyOf() method")
  class TestCasesForCopyOfMethod {

    @Test
    @DisplayName("works properly with an empty queue")
    void givenAEmptyQueueWhenCopyOfThenTheNewQueueIsAlsoEmpty() {
      // Arrange
      Queue<Integer> queue = new LinkedQueue<>();

      // Act
      Queue<Integer> copiedQueue = LinkedQueue.copyOf(queue);

      // Assert
      assertTrue(copiedQueue.isEmpty());
      assertEquals(queue, copiedQueue);
    }

    @Test
    @DisplayName("works properly with an queue with one element")
    void givenAQueueWithOneElementWhenCopyOfThenTheNewQueueIsEqual() {
      // Arrange
      Queue<Double> Queue = new LinkedQueue<>();
      Queue.enqueue(1.235);

      // Act
      LinkedQueue<Double> copiedQueue = LinkedQueue.copyOf(Queue);

      // Assert
      assertEquals(Queue, copiedQueue);
    }

    @Test
    @DisplayName("works properly with an queue with four elements")
    void givenAQueueWithFourElementWhenCopyOfThenTheNewQueueIsEqual() {
      // Arrange
      Queue<String> Queue = LinkedQueue.of("Monday", "Tuesday", "Friday", "Sunday");

      // Act
      Queue<String> copiedQueue = LinkedQueue.copyOf(Queue);

      // Assert
      assertEquals(Queue, copiedQueue);
    }

    @Test
    @DisplayName("works properly with an ArrayQueue with four elements")
    void givenALinkedQueueWithFourElementWhenCopyOfThenTheNewQueueIsEqual() {
      // Arrange
      Queue<String> Queue = ArrayQueue.of("Monday", "Tuesday", "Friday", "Sunday");

      // Act
      Queue<String> copiedQueue = LinkedQueue.copyOf(Queue);

      // Assert
      assertEquals(Queue, copiedQueue);
    }

    @Test
    @DisplayName("works properly with an JDKQueue with four elements")
    void givenAJDKQueueWithFourElementWhenCopyOfThenTheNewQueueIsEqual() {
      // Arrange
      Queue<String> Queue = JDKQueue.of("Monday", "Tuesday", "Friday", "Sunday");

      // Act
      Queue<String> copiedQueue = LinkedQueue.copyOf(Queue);

      // Assert
      assertEquals(Queue, copiedQueue);
    }
  }

  @Nested
  @DisplayName("The isEmpty() method")
  class TestCasesForMethodIsEmpty {

    @Test
    @DisplayName("returns true when a new queue is created")
    void givenANewQueueThenItIsEmpty() {
      // Arrange
      var queue = new LinkedQueue<Integer>();

      // Assert
      assertTrue(queue.isEmpty());
    }

    @Test
    @DisplayName("returns false when there is an element in the Queue")
    void givenAnEmptyQueueWhenEnqueueAnElementThenTheQueueIsNotEmpty() {
      // Arrange
      var queue = new LinkedQueue<Double>();

      // Act
      queue.enqueue(1.235);

      // Assert
      assertFalse(queue.isEmpty());
    }
  }

  @Nested
  @DisplayName("When method enqueue() is called")
  class TestCasesForMethodEnqueue {

    @Test
    @DisplayName("if the queue is empty, its size becomes one")
    void givenTheQueueIsEmptyWhenEnqueueThenTheQueueHasOneElement() {
      // Arrange
      LinkedQueue<Integer> queue = new LinkedQueue<>();

      // Act
      int value = 4;
      queue.enqueue(value);

      // Assert
      assertEquals(1, queue.size());
    }

    @Test
    @DisplayName("if the queue is not empty, its size is incremented by one")
    void givenTheQueueIsNoNEmptyWhenEnqueueThenTheQueueSizeIsIncrementedByOne() {
      // Arrange
      LinkedQueue<Boolean> queue = LinkedQueue.of(true, false, true);

      // Act
      int currentSize = queue.size();
      queue.enqueue(true);

      // Assert
      int expectedElementsInTheQueue = currentSize + 1;
      assertEquals(expectedElementsInTheQueue, queue.size());
    }

    @Test
    @DisplayName("if the queue is empty, the element is the first")
    void givenTheQueueIsEmptyWhenEnqueueThenTheElementIsTheFirstInTheQueue() {
      // Arrange
      LinkedQueue<Integer> queue = new LinkedQueue<>();

      // Act
      int value = 4;
      queue.enqueue(value);

      // Assert
      assertEquals(value, queue.first());
    }

    @Test
    @DisplayName("if the queue has one element, the existing element is the first ")
    void givenAQueueWithOneElementWhenEnqueueThenTheExistingElementIsTheFirst() {
      // Arrange
      String firstElement = "Carrot";
      LinkedQueue<String> queue = LinkedQueue.of(firstElement);

      // Act
      String newElement = "Aubergine";
      queue.enqueue(newElement);

      // Assert
      assertEquals(firstElement, queue.first());
    }

    @Test
    @DisplayName("if the queue has two elements, the queue contains the right sequence of elements")
    void givenAQueueWithTwoElementsWhenEnqueueThenTheQueueContainsTheRightSequenceOfElements() {
      // Arrange
      List<Double> elements = ArrayList.of(6.0, -1.0);
      LinkedQueue<Double> queue = LinkedQueue.from(elements);

      // Act
      double newElement = 7.3;
      queue.enqueue(newElement);

      // Assert
      List<Double> expectedElements = ArrayList.of(6.0, -1.0, newElement);
      List<Double> queueContents = ArrayList.from(queue.elements());
      assertEquals(expectedElements, queueContents);
    }
  }

  @Nested
  @DisplayName("When method dequeue() is called")
  class TestCasesForMethodDequeue {

    @DisplayName("an exception is raised if the queue is empty")
    @Test
    void givenAnEmptyQueueWhenDequeueThenAnExceptionIsRaised() {
      // Arrange
      LinkedQueue<Integer> queue = new LinkedQueue<>();

      // Assert
      EmptyQueueException exception = assertThrows(EmptyQueueException.class,
          queue::dequeue);
      assertEquals("dequeue on empty queue", exception.getMessage());
    }

    @DisplayName("if the list has one element, the list becomes is empty")
    @Test
    void givenAQueueWithOneElementWhenDequeueThenTheListIsEmpty() {
      // Arrange
      LinkedQueue<Integer> queue = LinkedQueue.of(6);

      // Act
      queue.dequeue();

      // Assert
      assertTrue(queue.isEmpty());
    }

    @DisplayName("if the list has two elements, the first element is dequeued")
    @Test
    void givenAnEmptyQueueWhenDequeueThenAnElementIsDequeued() {
      // Arrange
      LinkedQueue<Integer> queue = LinkedQueue.of(6, 7);

      // Act
      queue.dequeue();

      // Assert
      assertEquals(1, queue.size());
      assertEquals(7, queue.first());
    }
  }

  @Nested
  @DisplayName("A call to method clear()")
  class TestCasesForMethodClear {

    @Test
    @DisplayName("removes all the elements of the queue")
    void givenAQueueWhenClearThenTheQueueIsEmpty() {
      // Arrange
      Queue<Integer> queue = LinkedQueue.of(2, 5, 3, 5, 7);

      // Act
      queue.clear();

      // Assert
      assertTrue(queue.isEmpty());
    }
  }

  @Nested
  @DisplayName("When method first() is called")
  class TestCasesForMethodFirst {

    @Test
    @DisplayName("an exception is raised if the queue is empty")
    void givenTheQueueIsEmptyWhenFirstThenAnExceptionIsThrown() {
      // Arrange
      Queue<Integer> queue = new LinkedQueue<>();

      // Assert
      EmptyQueueException exception = assertThrows(EmptyQueueException.class, queue::first);
      assertEquals("first on empty queue", exception.getMessage());
    }

    @Test
    @DisplayName("if the queue has one element, that element is returned")
    void givenTheQueueHasOneElementWhenFirstThenTheElementIsReturned() {
      // Arrange
      String value = "Hello";
      Queue<String> queue = LinkedQueue.of(value);

      // Act
      String obtainedValue = queue.first();

      // Assert
      assertEquals(value, obtainedValue);
    }

    @Test
    @DisplayName("if the queue has three elements, that first one is returned")
    void givenTheQueueHasThreeElementWhenFirstThenTheFirstElementIsReturned() {
      // Arrange
      Queue<Double> queue = LinkedQueue.of(5.0, 8.1, 0.5e15);

      // Act
      Double obtainedValue = queue.first();

      // Assert
      Double expectedValue = 5.0;
      assertEquals(expectedValue, obtainedValue);
    }

    @Test
    @DisplayName("the list remains unchanged")
    void givenAQueueWhenFirstThenTheListIsNotModified() {
      // Arrange
      LinkedQueue<Double> queue = LinkedQueue.of(5.0, 8.1, 0.5e15);

      // Act
      queue.first();

      // Assert
      assertEquals(3, queue.size());
      assertEquals(ArrayList.of(5.0, 8.1, 0.5e15), ArrayList.from(queue.elements()));
    }
  }
}