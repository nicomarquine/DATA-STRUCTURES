package org.uma.ed.datastructures.queue;

import static org.junit.jupiter.api.Assertions.*;

import org.uma.ed.datastructures.list.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.uma.ed.datastructures.list.JDKArrayList;

@DisplayName("Test cases for class ArrayQueue")
class ArrayQueueTest {

  @Nested
  @DisplayName("An ArrayQueue is created")
  class TestCasesForCreatingQueues {

    @Test
    @DisplayName("by calling the default constructor")
    void whenTheConstructorIsCalledThenAnQueueIsCreated() {
      // Arrange
      Queue<Integer> queue = new ArrayQueue<>();

      // Assert
      assertNotNull(queue);
      assertTrue(queue.isEmpty());
    }

    @Test
    @DisplayName("by calling the constructor with a capacity value")
    void whenTheConstructorWithCapacityValueIsCalledThenAQueueIsCreated() {
      // Arrange
      int capacity = 10;
      Queue<Integer> Queue = new ArrayQueue<>(capacity);

      // Assert
      assertNotNull(Queue);
      assertTrue(Queue.isEmpty());
    }

    @Test
    @DisplayName("by calling the withCapacity() method")
    void whenTheWithCapacityMethodIsCalledThenAQueueIsCreated() {
      // Arrange
      int capacity = 20;
      Queue<Integer> Queue = ArrayQueue.withCapacity(capacity);

      // Assert
      assertNotNull(Queue);
      assertTrue(Queue.isEmpty());
    }

    @Test
    @DisplayName("by calling the empty() method")
    void whenTheEmptyMethodIsCalledThenAnQueueIsCreated() {
      // Arrange
      Queue<Integer> Queue = ArrayQueue.empty();

      // Assert
      assertNotNull(Queue);
      assertTrue(Queue.isEmpty());
    }

    @Test
    @DisplayName("from a sequence of values using the of() method")
    void whenTheOfMethodIsCalledThenAQueueIsCreated() {
      // Arrange
      Queue<String> Queue = ArrayQueue.of("orange", "blue", "green");

      // Assert
      assertNotNull(Queue);

      int expectedElementsInTheQueue = 3;
      assertEquals(expectedElementsInTheQueue, Queue.size());
    }

    @Test
    @DisplayName("from an iterable (list) of values using the from() method")
    void givenAListOfValuesWhenTheFromMethodIsCalledThenAnArrayQueueIsCreated() {
      // Arrange
      List<Integer> initialValues = JDKArrayList.of(1, 2, 3, 4, 5);
      Queue<Integer> Queue = ArrayQueue.from(initialValues);

      // Assert
      assertNotNull(Queue);

      int expectedElementsInTheQueue = initialValues.size();
      assertEquals(expectedElementsInTheQueue, Queue.size());
    }
  }

  @Nested
  @DisplayName("An exception is raised when")
  class TestCasesForRaisedExceptions {

    @DisplayName("The constructor is called with a negative  value")
    @Test
    void whenConstructorIsCalledWithANegativeValueThenAnExceptionIsThrown() {
      assertThrows(IllegalArgumentException.class, () -> new ArrayQueue<>(-1));
    }
  }

  @Nested
  @DisplayName("The copyOf() method")
  class TestCasesForCopyOfMethod {

    @Test
    @DisplayName("works properly with an empty queue")
    void givenAEmptyQueueWhenCopyOfThenTheNewQueueIsAlsoEmpty() {
      // Arrange
      Queue<Integer> queue = new ArrayQueue<>();

      // Act
      Queue<Integer> copiedQueue = ArrayQueue.copyOf(queue);

      // Assert
      assertTrue(copiedQueue.isEmpty());
      assertEquals(queue, copiedQueue);
    }

    @Test
    @DisplayName("works properly with an queue with one element")
    void givenAQueueWithOneElementWhenCopyOfThenTheNewQueueIsEqual() {
      // Arrange
      Queue<Double> Queue = new ArrayQueue<>();
      Queue.enqueue(1.235);

      // Act
      ArrayQueue<Double> copiedQueue = ArrayQueue.copyOf(Queue);

      // Assert
      assertEquals(Queue, copiedQueue);
    }

    @Test
    @DisplayName("works properly with an queue with four elements")
    void givenAQueueWithFourElementWhenCopyOfThenTheNewQueueIsEqual() {
      // Arrange
      Queue<String> Queue = ArrayQueue.of("Monday", "Tuesday", "Friday", "Sunday");

      // Act
      Queue<String> copiedQueue = ArrayQueue.copyOf(Queue);

      // Assert
      assertEquals(Queue, copiedQueue);
    }

    @Test
    @DisplayName("works properly with an JDKQueue with four elements")
    void givenAJDKQueueWithFourElementWhenCopyOfThenTheNewQueueIsEqual() {
      // Arrange
      Queue<String> Queue = JDKQueue.of("Monday", "Tuesday", "Friday", "Sunday");

      // Act
      Queue<String> copiedQueue = ArrayQueue.copyOf(Queue);

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
      var queue = new ArrayQueue<Integer>();

      // Assert
      assertTrue(queue.isEmpty());
    }

    @Test
    @DisplayName("returns false when there is an element in the Queue")
    void givenAnEmptyQueueWhenEnqueueAnElementThenTheQueueIsNotEmpty() {
      // Arrange
      var queue = new ArrayQueue<Double>();

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
      ArrayQueue<Integer> queue = new ArrayQueue<>();

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
      ArrayQueue<Boolean> queue = ArrayQueue.of(true, false, true);

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
      ArrayQueue<Integer> queue = new ArrayQueue<>();

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
      ArrayQueue<String> queue = ArrayQueue.of(firstElement);

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
      List<Double> elements = JDKArrayList.of(6.0, -1.0);
      ArrayQueue<Double> queue = ArrayQueue.from(elements);

      // Act
      double newElement = 7.3;
      queue.enqueue(newElement);

      // Assert
      List<Double> expectedElements = JDKArrayList.of(6.0, -1.0, newElement);
      List<Double> queueContents = JDKArrayList.from(queue.elements());
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
      ArrayQueue<Integer> queue = new ArrayQueue<>();

      // Assert
      EmptyQueueException exception = assertThrows(EmptyQueueException.class,
          queue::dequeue);
      assertEquals("dequeue on empty queue", exception.getMessage());
    }

    @DisplayName("if the list has one element, the list becomes is empty")
    @Test
    void givenAQueueWithOneElementWhenDequeueThenTheListIsEmpty() {
      // Arrange
      ArrayQueue<Integer> queue = ArrayQueue.of(6);

      // Act
      queue.dequeue();

      // Assert
      assertTrue(queue.isEmpty());
    }

    @DisplayName("if the list has two elements, the first element is dequeued")
    @Test
    void givenAnEmptyQueueWhenDequeueThenAnElementIsDequeued() {
      // Arrange
      ArrayQueue<Integer> queue = ArrayQueue.of(6, 7);

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
      Queue<Integer> queue = ArrayQueue.of(2, 5, 3, 5, 7);

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
      Queue<Integer> queue = new ArrayQueue<>();

      // Assert
      EmptyQueueException exception = assertThrows(EmptyQueueException.class, queue::first);
      assertEquals("first on empty queue", exception.getMessage());
    }

    @Test
    @DisplayName("if the queue has one element, that element is returned")
    void givenTheQueueHasOneElementWhenFirstThenTheElementIsReturned() {
      // Arrange
      String value = "Hello";
      Queue<String> queue = ArrayQueue.of(value);

      // Act
      String obtainedValue = queue.first();

      // Assert
      assertEquals(value, obtainedValue);
    }

    @Test
    @DisplayName("if the queue has three elements, that first one is returned")
    void givenTheQueueHasThreeElementWhenFirstThenTheFirstElementIsReturned() {
      // Arrange
      Queue<Double> queue = ArrayQueue.of(5.0, 8.1, 0.5e15);

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
      ArrayQueue<Double> queue = ArrayQueue.of(5.0, 8.1, 0.5e15);

      // Act
      queue.first();

      // Assert
      assertEquals(3, queue.size());
      assertEquals(JDKArrayList.of(5.0, 8.1, 0.5e15), JDKArrayList.from(queue.elements()));
    }
  }
}