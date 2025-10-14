package org.uma.ed.datastructures.stack;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;

@DisplayName("Test cases for class LinkedStack")
class LinkedStackTest {

  @Nested
  @DisplayName("A LinkedStack is created")
  class TestCasesForCreatingStacks {

    @Test
    @DisplayName("by calling the default constructor")
    void whenTheConstructorIsCalledThenALinkedStackIsCreated() {
      // Arrange
      Stack<Integer> stack = new LinkedStack<>();

      // Assert
      assertNotNull(stack);
      assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("by calling the empty() method")
    void whenTheEmptyMethodIsCalledThenALinkedStackIsCreated() {
      // Arrange
      Stack<Integer> stack = LinkedStack.empty();

      // Assert
      assertNotNull(stack);
      assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("from a sequence of values using the of() method")
    void whenTheOfMethodIsCalledThenALinkedStackIsCreated() {
      // Arrange
      Stack<String> stack = LinkedStack.of("orange", "blue", "green");

      // Assert
      assertNotNull(stack);

      int expectedElementsInTheStack = 3;
      assertEquals(expectedElementsInTheStack, stack.size());
    }

    @Test
    @DisplayName("from an iterable (list) of values using the from() method")
    void givenAListOfValuesWhenTheFromMethodIsCalledThenALinkedStackIsCreated() {
      // Arrange
      List<Integer> initialValues = JDKArrayList.of(1, 2, 3, 4, 5);
      Stack<Integer> stack = LinkedStack.from(initialValues);

      // Assert
      assertNotNull(stack);

      int expectedElementsInTheStack = initialValues.size();
      assertEquals(expectedElementsInTheStack, stack.size());
    }

    @Test
    @DisplayName("works properly with an JDKStack with four elements")
    void givenAJDKStackWithFourElementWhenCopyOfThenTheNewStackIsEqual() {
      // Arrange
      Stack<String> stack = JDKStack.of("Monday", "Tuesday", "Friday", "Sunday");

      // Act
      Stack<String> copiedStack = LinkedStack.copyOf(stack);

      // Assert
      assertEquals(stack, copiedStack);
    }

    @Test
    @DisplayName("works properly with an JDKStack with four elements")
    void givenAnArrayStackWithFourElementWhenCopyOfThenTheNewStackIsEqual() {
      // Arrange
      Stack<String> stack = JDKStack.of("Monday", "Tuesday", "Friday", "Sunday");

      // Act
      Stack<String> copiedStack = LinkedStack.copyOf(stack);

      // Assert
      assertEquals(stack, copiedStack);
    }
  }

  @Nested
  @DisplayName("When method push() is called")
  class TestCasesForPushMethod {

    @Test
    @DisplayName("if the stack is empty, its size becomes one")
    void givenTheStackIsEmptyWhenPushThenTheStackHasOneElement() {
      // Arrange
      Stack<Integer> stack = new LinkedStack<>();

      // Act
      int value = 4;
      stack.push(value);

      // Assert
      assertEquals(1, stack.size());
      assertEquals(value, stack.top());
    }

    @Test
    @DisplayName("if the stack is not empty, its size is incremented by one")
    void givenTheStackIsNoNEmptyWhenPushThenTheStackSizeIncrementedByOne() {
      // Arrange
      Stack<Boolean> stack = LinkedStack.of(true, false, true);

      // Act
      int currentSize = stack.size();
      stack.push(true);

      // Assert
      int expectedElementsInTheStack = currentSize + 1;
      assertEquals(expectedElementsInTheStack, stack.size());
    }

    @Test
    @DisplayName("a call to method top() return the pushed element")
    void givenAStackWhenPushingAnElementThenTopReturnsThatElement() {
      // Arrange
      Stack<Integer> stack = new LinkedStack<>();

      // Act
      int element = 2352;
      stack.push(element);
      int gottenElement = stack.top();

      // Assert
      assertEquals(element, gottenElement);
    }

    @Test
    @DisplayName("a call to method pop() deletes that element from the list")
    void givenAStackWhenPushingAnElementThenPopDeletesThatElement() {
      // Arrange
      List<String> colors = JDKArrayList.of("green", "red", "black", "blue");
      Stack<String> stack = LinkedStack.from(colors);
      int stackSize = stack.size();

      // Act
      String newColor = "white";
      stack.push(newColor);
      stack.pop();

      // Assert
      assertEquals(stackSize, stack.size());
    }
  }

  @Nested
  @DisplayName("When method pop() is called")
  class TestCasesForPopMethod {

    @Test
    @DisplayName("an exception is raised if the stack is empty")
    void givenTheStackIsEmptyWhenPopThenAnExceptionIsThrown() {
      // Arrange
      Stack<Integer> stack = new LinkedStack<>();

      // Assert
      EmptyStackException exception = assertThrows(EmptyStackException.class, stack::pop);
      assertEquals("pop on empty stack", exception.getMessage());
    }

    @Test
    @DisplayName("if the stack has one element, the stack size is becomes zero")
    void givenTheStackHasOneElementWhenTopThenTheElementIsReturned() {
      // Arrange
      String value = "Hello";
      Stack<String> stack = LinkedStack.of(value);

      // Act
      stack.pop();

      // Assert
      int expectedStackSize = 0;
      assertEquals(expectedStackSize, stack.size());
    }
  }

  @Nested
  @DisplayName("When method top() is called")
  class TestCasesForTopMethod {

    @Test
    @DisplayName("an exception is raised if the stack is empty")
    void givenTheStackIsEmptyWhenTopThenAnExceptionIsThrown() {
      // Arrange
      Stack<Integer> stack = new LinkedStack<>();

      // Assert
      EmptyStackException exception = assertThrows(EmptyStackException.class, stack::top);
      assertEquals("top on empty stack", exception.getMessage());
    }

    @Test
    @DisplayName("if the stack has one element, that element is returned")
    void givenTheStackHasOneElementWhenTopThenTheElementIsReturned() {
      // Arrange
      String value = "Hello";
      Stack<String> stack = LinkedStack.of(value);

      // Act
      String obtainedValue = stack.top();

      // Assert
      assertEquals(value, obtainedValue);

      int expectedStackSize = 1;
      assertEquals(expectedStackSize, stack.size());
    }

    @Test
    @DisplayName("if the stack has three elements, that last one is returned")
    void givenTheStackHasThreeElementWhenTopThenTheLastElementIsReturned() {
      // Arrange
      Stack<Double> stack = LinkedStack.of(5.0, 8.1, 0.5e15);

      // Act
      Double obtainedValue = stack.top();

      // Assert
      Double expectedValue = 0.5e15;
      assertEquals(expectedValue, obtainedValue);

      int expectedStackSize = 3;
      assertEquals(expectedStackSize, stack.size());
    }

    @Test
    @DisplayName("if an element has been pushed before, it is returned")
    void givenAPushedElementBeforeWhenTopItIsReturned() {
      // Arrange
      Stack<String> stack = LinkedStack.of("Hello");

      // Act
      String newValue = "World";
      stack.push(newValue);

      // Assert
      assertEquals(newValue, stack.top());
    }
  }

  @Nested
  @DisplayName("The isEmpty() method")
  class TestCasesForMethodIsEmpty {

    @Test
    @DisplayName("returns true when a new stack is created")
    void givenANewStackThenItIsEmpty() {
      // Arrange
      var stack = new LinkedStack<Integer>();

      // Assert
      assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("returns false when there is an element into the stack")
    void givenAnEmptyStackWhenPushAnElementThenTheStackIsNotEmpty() {
      // Arrange
      var stack = new LinkedStack<Double>();

      // Act
      stack.push(1.235);

      // Assert
      assertFalse(stack.isEmpty());
    }
  }

  @Nested
  @DisplayName("The copyOf() method")
  class TestCasesForCopyOfMethod {

    @Test
    @DisplayName("works properly with an empty LinkedStack")
    void givenAEmptyStackWhenCopyOfThenTheNewStackIsAlsoEmpty() {
      // Arrange
      Stack<Integer> stack = new LinkedStack<>();

      // Act
      Stack<Integer> copiedStack = LinkedStack.copyOf(stack);

      // Assert
      assertTrue(copiedStack.isEmpty());
      assertEquals(stack, copiedStack);
    }

    @Test
    @DisplayName("works properly with an LinkedStack with one element")
    void givenAStackWithOneElementWhenCopyOfThenTheNewStackIsEqual() {
      // Arrange
      Stack<Double> stack = new LinkedStack<>();
      stack.push(1.235);

      // Act
      Stack<Double> copiedStack = LinkedStack.copyOf(stack);

      // Assert
      assertEquals(stack, copiedStack);
    }

    @Test
    @DisplayName("works properly with an LinkedStack with four elements")
    void givenAStackWithFourElementWhenCopyOfThenTheNewStackIsEqual() {
      // Arrange
      Stack<String> stack = LinkedStack.of("Monday", "Tuesday", "Friday", "Sunday");

      // Act
      Stack<String> copiedStack = LinkedStack.copyOf(stack);

      // Assert
      assertEquals(stack, copiedStack);
    }
  }

  @Nested
  @DisplayName("A call to method elements()")
  class TestCasesForMethodElements {

    @Test
    @DisplayName("returns an iterable with no elements")
    void givenAEmptyStackWhenElementsThenTheIterableHasNoElements() {
      // Arrange
      LinkedStack<Integer> stack = new LinkedStack<>();

      // Act
      Iterable<Integer> iterable = stack.elements();

      // Assert
      assertFalse(iterable.iterator().hasNext());
      assertThrows(NoSuchElementException.class, () -> iterable.iterator().next());
    }

    @Test
    @DisplayName("returns an iterable with all the stack elements")
    void givenAStackWhenElementsThenTheIterableReturnsTheStackContents() {
      // Arrange
      List<Integer> values = JDKArrayList.of(3, 5, 6, 2, 0);
      LinkedStack<Integer> stack = LinkedStack.from(values);

      // Act
      Iterable<Integer> iterable = stack.elements();
      List<Integer> stackContents = JDKArrayList.empty();
      for (Integer value : iterable) {
        stackContents.prepend(value);
      }

      // Assert
      assertEquals(values, stackContents);
    }
  }

  @Nested
  @DisplayName("A call to method clear()")
  class TestCasesForMethodClear {

    @Test
    @DisplayName("removes all the elements of the stack")
    void givenAStackWhenClearThenTheStackIsEmpty() {
      // Arrange
      Stack<Integer> stack = LinkedStack.of(2, 5, 3, 5, 7);

      // Act
      stack.clear();

      // Assert
      assertTrue(stack.isEmpty());
    }
  }


  @Nested
  @DisplayName("When method equals() is called")
  class TestCasesForEqualsMethod {

    @DisplayName("true is returned if two stacks are empty")
    @Test
    void givenTwoEmptyListStacksWhenEqualsThenTrue() {
      // Arrange
      Stack<Integer> stack1 = new LinkedStack<>();
      Stack<Integer> stack2 = new LinkedStack<>();

      // Assert
      assertEquals(stack1, stack2);
    }

    @DisplayName("true is returned if two equal stacks are non empty")
    @Test
    void givenTwoNonEmptyListStacksWhenEqualsThenTrue() {
      // Arrange
      Stack<Integer> stack1 = LinkedStack.of(1, 2, 3);
      Stack<Integer> stack2 = LinkedStack.of(1, 2, 3);

      // Assert
      assertEquals(stack1, stack2);
    }

    @DisplayName("false is returned if the two arrays have one element and they are not equal")
    @Test
    void givenTwoLinkedLinkedWithOneElementWhenNotEqualsThenFalse() {
      // Arrange
      Stack<Integer> stack1 = LinkedStack.of(1);
      Stack<Integer> stack2 = LinkedStack.of(2);

      // Assert
      assertNotEquals(stack1, stack2);
    }

    @DisplayName("false is returned if the two arrays have three elements and the first one is different")
    @Test
    void givenTwoEqualsLinkedStacksWithThreeElementsWhenTheFirstOneIsNotEqualsThenFalse() {
      // Arrange
      Stack<Integer> stack1 = LinkedStack.of(0, 2, 3);
      Stack<Integer> stack2 = LinkedStack.of(1, 2, 3);

      // Assert
      assertNotEquals(stack1, stack2);
    }

    @DisplayName("false is returned if the two arrays have three elements and the second one is different")
    @Test
    void givenTwoEqualsLinkedStacksWithThreeElementsWhenTheSecondOneIsNotEqualsThenFalse() {
      // Arrange
      Stack<Integer> stack1 = LinkedStack.of(1, 2, 3);
      Stack<Integer> stack2 = LinkedStack.of(1, 5, 3);

      // Assert
      assertNotEquals(stack1, stack2);
    }

    @DisplayName("false is returned if the two arrays have three elements and the third one is different")
    @Test
    void givenTwoEqualsLinkedStacksWithThreeElementsWhenTheThirdOneIsNotEqualsThenFalse() {
      // Arrange
      Stack<Integer> stack1 = LinkedStack.of(0, 2, 3);
      Stack<Integer> stack2 = LinkedStack.of(1, 2, 5);

      // Assert
      assertNotEquals(stack1, stack2);
    }
  }
}