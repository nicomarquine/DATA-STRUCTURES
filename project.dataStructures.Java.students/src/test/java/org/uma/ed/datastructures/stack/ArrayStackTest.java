package org.uma.ed.datastructures.stack;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;

/**
 * Test cases for class {@link ArrayStack}
 *
 * @author Antonio J. Nebro
 */
@DisplayName("Test cases for class ArrayStack")
class ArrayStackTest {

  @Nested
  @DisplayName("An ArrayStack is created")
  class TestCasesForCreatingStacks {

    @Test
    @DisplayName("by calling the default constructor")
    void whenTheConstructorIsCalledThenAnArrayStackIsCreated() {
      // Arrange
      Stack<Integer> stack = new ArrayStack<>();

      // Assert
      assertNotNull(stack);
      assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("by calling the constructor with a capacity value")
    void whenTheConstructorWithCapacityValueIsCalledThenAnArrayStackIsCreated() {
      // Arrange
      int capacity = 10;
      Stack<Integer> stack = new ArrayStack<>(capacity);

      // Assert
      assertNotNull(stack);
      assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("by calling the withCapacity() method")
    void whenTheWithCapacityMethodIsCalledThenAnArrayStackIsCreated() {
      // Arrange
      int capacity = 20;
      Stack<Integer> stack = ArrayStack.withCapacity(capacity);

      // Assert
      assertNotNull(stack);
      assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("by calling the empty() method")
    void whenTheEmptyMethodIsCalledThenAnArrayStackIsCreated() {
      // Arrange
      Stack<Integer> stack = ArrayStack.empty();

      // Assert
      assertNotNull(stack);
      assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("from a sequence of values using the of() method")
    void whenTheOfMethodIsCalledThenAnArrayStackIsCreated() {
      // Arrange
      Stack<String> stack = ArrayStack.of("orange", "blue", "green");

      // Assert
      assertNotNull(stack);

      int expectedElementsInTheStack = 3;
      assertEquals(expectedElementsInTheStack, stack.size());
    }

    @Test
    @DisplayName("from an iterable (list) of values using the from() method")
    void givenAListOfValuesWhenTheFromMethodIsCalledThenAnArrayStackIsCreated() {
      // Arrange
      List<Integer> initialValues = JDKArrayList.of(1, 2, 3, 4, 5);
      Stack<Integer> stack = ArrayStack.from(initialValues);

      // Assert
      assertNotNull(stack);

      int expectedElementsInTheStack = initialValues.size();
      assertEquals(expectedElementsInTheStack, stack.size());
    }
  }

  @Nested
  @DisplayName("When method push() is called")
  class TestCasesForMethodPush {

    @Test
    @DisplayName("if the stack is empty, its size becomes one")
    void givenTheStackIsEmptyWhenPushThenTheStackHasOneElement() {
      // Arrange
      ArrayStack<Integer> stack = new ArrayStack<>();

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
      ArrayStack<Boolean> stack = ArrayStack.of(true, false, true);

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
      ArrayStack<Integer> stack = new ArrayStack<>();

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
      ArrayStack<String> stack = ArrayStack.from(colors);
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
  class TestCasesForMethodPop {

    @Test
    @DisplayName("an exception is raised if the stack is empty")
    void givenTheStackIsEmptyWhenPopThenAnExceptionIsThrown() {
      // Arrange
      Stack<Integer> stack = new ArrayStack<>();

      // Assert
      EmptyStackException exception = assertThrows(EmptyStackException.class, stack::pop);
      assertEquals("pop on empty stack", exception.getMessage());
    }

    @Test
    @DisplayName("if the stack has one element, the stack size is becomes zero")
    void givenTheStackHasOneElementWhenTopThenTheElementIsReturned() {
      // Arrange
      String value = "Hello";
      Stack<String> stack = ArrayStack.of(value);

      // Act
      stack.pop();

      // Assert
      int expectedStackSize = 0;
      assertEquals(expectedStackSize, stack.size());
    }
  }

  @Nested
  @DisplayName("When method top() is called")
  class TestCasesForMethodTop {

    @Test
    @DisplayName("an exception is raised if the stack is empty")
    void givenTheStackIsEmptyWhenTopThenAnExceptionIsThrown() {
      // Arrange
      Stack<Integer> stack = new ArrayStack<>();

      // Assert
      EmptyStackException exception = assertThrows(EmptyStackException.class, stack::top);
      assertEquals("top on empty stack", exception.getMessage());
    }

    @Test
    @DisplayName("if the stack has one element, that element is returned")
    void givenTheStackHasOneElementWhenTopThenTheElementIsReturned() {
      // Arrange
      String value = "Hello";
      Stack<String> stack = ArrayStack.of(value);

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
      Stack<Double> stack = ArrayStack.of(5.0, 8.1, 0.5e15);

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
      Stack<String> stack = ArrayStack.of("Hello");

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
      var stack = new ArrayStack<Integer>();

      // Assert
      assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("returns false when there is an element in the stack")
    void givenAnEmptyStackWhenPushAnElementThenTheStackIsNotEmpty() {
      // Arrange
      var stack = new ArrayStack<Double>();

      // Act
      stack.push(1.235);

      // Assert
      assertFalse(stack.isEmpty());
    }
  }

  @Nested
  @DisplayName("The copyOf() method")
  class TestCasesForMethodCopyOf {

    @Test
    @DisplayName("works properly with an empty ArrayStack")
    void givenAEmptyStackWhenCopyOfThenTheNewStackIsAlsoEmpty() {
      // Arrange
      Stack<Integer> stack = new ArrayStack<>();

      // Act
      Stack<Integer> copiedStack = ArrayStack.copyOf(stack);

      // Assert
      assertTrue(copiedStack.isEmpty());
      assertEquals(stack, copiedStack);
    }

    @Test
    @DisplayName("works properly with an ArrayStack with one element")
    void givenAStackWithOneElementWhenCopyOfThenTheNewStackIsEqual() {
      // Arrange
      Stack<Double> stack = new ArrayStack<>();
      stack.push(1.235);

      // Act
      ArrayStack<Double> copiedStack = ArrayStack.copyOf(stack);

      // Assert
      assertEquals(stack, copiedStack);
    }

    @Test
    @DisplayName("works properly with an ArrayStack with four elements")
    void givenAStackWithFourElementWhenCopyOfThenTheNewStackIsEqual() {
      // Arrange
      Stack<String> stack = ArrayStack.of("Monday", "Tuesday", "Friday", "Sunday");

      // Act
      Stack<String> copiedStack = ArrayStack.copyOf(stack);

      // Assert
      assertEquals(stack, copiedStack);
    }

    @Test
    @DisplayName("works properly with an JDKStack with four elements")
    void givenALinkedStackWithFourElementWhenCopyOfThenTheNewStackIsEqual() {
      // Arrange
      Stack<String> stack = JDKStack.of("Monday", "Tuesday", "Friday", "Sunday");

      // Act
      Stack<String> copiedStack = ArrayStack.copyOf(stack);

      // Assert
      assertEquals(stack, copiedStack);
    }

    @Test
    @DisplayName("works properly with an JDKStack with four elements")
    void givenALinkedListStackWithFourElementWhenCopyOfThenTheNewStackIsEqual() {
      // Arrange
      Stack<String> stack = JDKStack.of("Monday", "Tuesday", "Friday", "Sunday");

      // Act
      Stack<String> copiedStack = ArrayStack.copyOf(stack);

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
      ArrayStack<Integer> stack = new ArrayStack<>();

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
      ArrayStack<Integer> stack = ArrayStack.from(values);

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
      Stack<Integer> stack = ArrayStack.of(2, 5, 3, 5, 7);

      // Act
      stack.clear();

      // Assert
      assertTrue(stack.isEmpty());
    }
  }

  @Nested
  @DisplayName("An exception is raised when")
  class TestCasesForRaisedExceptions {

    @DisplayName("The constructor is called with a negative value")
    @Test
    void whenConstructorIsCalledWithANegativeValueThenAnExceptionIsThrown() {
      assertThrows(IllegalArgumentException.class, () -> new ArrayStack<>(-1));
    }
  }

  @Nested
  @DisplayName("When method equals() is called")
  class TestCasesForMethodEquals {

    @DisplayName("true is returned if two array stacks are empty")
    @Test
    void givenTwoEmptyArrayStacksWhenEqualsThenTrue() {
      // Arrange
      Stack<Integer> stack1 = new ArrayStack<>();
      Stack<Integer> stack2 = new ArrayStack<>();

      // Assert
      assertEquals(stack1, stack2);
    }

    @DisplayName("true is returned if two equal array stacks are non empty")
    @Test
    void givenTwoNonEmptyArrayStacksWhenEqualsThenTrue() {
      // Arrange
      Stack<Integer> stack1 = ArrayStack.of(1, 2, 3);
      Stack<Integer> stack2 = ArrayStack.of(1, 2, 3);

      // Assert
      assertEquals(stack1, stack2);
    }

    @DisplayName("false is returned if the two arrays have one element and they are not equal")
    @Test
    void givenTwoArrayStacksWithOneElementWhenNotEqualsThenFalse() {
      // Arrange
      Stack<Integer> stack1 = ArrayStack.of(1);
      Stack<Integer> stack2 = ArrayStack.of(2);

      // Assert
      assertNotEquals(stack1, stack2);
    }

    @DisplayName("false is returned if the two arrays have three elements and the first one is different")
    @Test
    void givenTwoEqualsArrayStacksWithThreeElementsWhenTheFirstOneIsNotEqualsThenFalse() {
      // Arrange
      Stack<Integer> stack1 = ArrayStack.of(0, 2, 3);
      Stack<Integer> stack2 = ArrayStack.of(1, 2, 3);

      // Assert
      assertNotEquals(stack1, stack2);
    }

    @DisplayName("false is returned if the two arrays have three elements and the second one is different")
    @Test
    void givenTwoEqualsArrayStacksWithThreeElementsWhenTheSecondOneIsNotEqualsThenFalse() {
      // Arrange
      Stack<Integer> stack1 = ArrayStack.of(1, 2, 3);
      Stack<Integer> stack2 = ArrayStack.of(1, 5, 3);

      // Assert
      assertNotEquals(stack1, stack2);
    }

    @DisplayName("false is returned if the two arrays have three elements and the third one is different")
    @Test
    void givenTwoEqualsArrayStacksWithThreeElementsWhenTheThirdOneIsNotEqualsThenFalse() {
      // Arrange
      Stack<Integer> stack1 = ArrayStack.of(0, 2, 3);
      Stack<Integer> stack2 = ArrayStack.of(1, 2, 5);

      // Assert
      assertNotEquals(stack1, stack2);
    }
  }
}