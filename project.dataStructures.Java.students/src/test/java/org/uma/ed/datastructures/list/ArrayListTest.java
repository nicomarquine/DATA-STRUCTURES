package org.uma.ed.datastructures.list;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test cases for class ArrayList")
class ArrayListTest {

  @Nested
  @DisplayName("An ArrayList is created")
  class TestCasesForCreatingLists {

    @Test
    @DisplayName("by calling the default constructor")
    void whenTheConstructorIsCalledThenAnArraylistIsCreated() {
      // Arrange
      List<Integer> list = new ArrayList<>();

      // Assert
      assertNotNull(list);
      assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("by calling the constructor with a capacity value")
    void whenTheConstructorWithCapacityValueIsCalledThenAnArraylistIsCreated() {
      // Arrange
      int capacity = 10;
      List<Integer> list = new ArrayList<>(capacity);

      // Assert
      assertNotNull(list);
      assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("by calling the withCapacity() method")
    void whenTheWithCapacityMethodIsCalledThenAnArraylistIsCreated() {
      // Arrange
      int capacity = 20;
      List<Integer> list = ArrayList.withCapacity(capacity);

      // Assert
      assertNotNull(list);
      assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("by calling the empty() method")
    void whenTheEmptyMethodIsCalledThenAnArraylistIsCreated() {
      // Arrange
      List<Integer> list = ArrayList.empty();

      // Assert
      assertNotNull(list);
      assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("from a sequence of values using the of() method")
    void whenTheOfMethodIsCalledThenAnArraylistIsCreated() {
      // Arrange
      List<String> list = ArrayList.of("orange", "blue", "green");

      // Assert
      assertNotNull(list);

      int expectedElementsInThelist = 3;
      assertEquals(expectedElementsInThelist, list.size());
    }

    @Test
    @DisplayName("from an iterable (list) of values using the from() method")
    void givenAListOfValuesWhenTheFromMethodIsCalledThenAnArraylistIsCreated() {
      // Arrange
      List<Integer> initialValues = JDKArrayList.of(1, 2, 3, 4, 5);
      List<Integer> list = ArrayList.from(initialValues);

      // Assert
      assertNotNull(list);

      int expectedElementsInThelist = initialValues.size();
      assertEquals(expectedElementsInThelist, list.size());
    }
  }

  @Nested
  @DisplayName("The isEmpty() method")
  class TestCasesForMethodIsEmpty {

    @Test
    @DisplayName("returns true when a new list is created")
    void givenANewListThenItIsEmpty() {
      // Arrange
      var list = new ArrayList<Integer>();

      // Assert
      assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("returns false when there is an element in the list")
    void givenAnEmptyListWhenPushAnElementThenTheListIsNotEmpty() {
      // Arrange
      var list = new ArrayList<Double>();

      // Act
      list.append(1.235);

      // Assert
      assertFalse(list.isEmpty());
    }
  }

  @Nested
  @DisplayName("The copyOf() method")
  class TestCasesForCopyOfMethod {

    @Test
    @DisplayName("works properly with an empty ArrayList")
    void givenAEmptyListWhenCopyOfThenTheNewListIsAlsoEmpty() {
      // Arrange
      List<Integer> list = new ArrayList<>();

      // Act
      List<Integer> copiedList = ArrayList.copyOf(list);

      // Assert
      assertTrue(copiedList.isEmpty());
      assertEquals(list, copiedList);
    }

    @Test
    @DisplayName("works properly with an ArrayList with one element")
    void givenAListWithOneElementWhenCopyOfThenTheNewListIsEqual() {
      // Arrange
      List<Double> list = new ArrayList<>();
      list.append(1.235);

      // Act
      ArrayList<Double> copiedList = ArrayList.copyOf(list);

      // Assert
      assertEquals(list, copiedList);
    }

    @Test
    @DisplayName("works properly with an ArrayList with four elements")
    void givenAListWithFourElementWhenCopyOfThenTheNewListIsEqual() {
      // Arrange
      List<String> list = ArrayList.of("Monday", "Tuesday", "Friday", "Sunday");

      // Act
      List<String> copiedList = ArrayList.copyOf(list);

      // Assert
      assertEquals(list, copiedList);
    }

    @Test
    @DisplayName("works properly with a JDKArrayList with four elements")
    void givenAJDKArrayListWithFourElementWhenCopyOfThenTheNewListIsEqual() {
      // Arrange
      List<String> list = JDKArrayList.of("Monday", "Tuesday", "Friday", "Sunday");

      // Act
      List<String> copiedList = ArrayList.copyOf(list);

      // Assert
      assertEquals(list, copiedList);
    }
  }

  @Nested
  @DisplayName("A call to method clear()")
  class TestCasesForMethodClear {

    @Test
    @DisplayName("removes all the elements of the list")
    void givenListWhenClearThenTheListIsEmpty() {
      // Arrange
      List<Integer> list = ArrayList.of(2, 5, 3, 5, 7);

      // Act
      list.clear();

      // Assert
      assertTrue(list.isEmpty());
    }
  }

  @Nested
  @DisplayName("When method append() is called")
  class TestCasesForMethodAppend {

    @Test
    @DisplayName("if the list is empty, its size becomes one")
    void givenTheListIsEmptyWhenAppendThenTheListHasOneElement() {
      // Arrange
      List<Integer> list = new ArrayList<>();

      // Act
      int value = 4;
      list.append(value);

      // Assert
      assertEquals(1, list.size());
      assertEquals(value, list.get(0));
    }

    @Test
    @DisplayName("if the list is not empty, its size is incremented by one")
    void givenTheListIsNoNEmptyWhenAppendThenTheListSizeIncrementedByOne() {
      // Arrange
      List<Integer> list = ArrayList.of(1, 2, 3, 4, 5);

      // Act
      int currentSize = list.size();
      list.append(14);

      // Assert
      int expectedElementsInTheList = currentSize + 1;
      assertEquals(expectedElementsInTheList, list.size());
      assertEquals(14, list.get(5));
    }

    @Test
    @DisplayName("the element is at the end of the list")
    void givenAListWhenAppendAnElementThenItIsAtTheEndOfTheList() {
      // Arrange
      ArrayList<Integer> list = ArrayList.of(65, 25, 35);
      int listSize = list.size();

      // Act
      int newElement = 2352;
      list.append(newElement);
      int gottenElement = list.get(listSize);

      // Assert
      assertEquals(newElement, gottenElement);
    }

    @Test
    @DisplayName("the elements in the list are in the right order")
    void givenAListWhenAppendAnElementThenItTheOrderOfTheListElementsIsCorrect() {
      // Arrange
      ArrayList<Integer> list = ArrayList.of(65, 25, 35);

      // Act
      int newElement = 2352;
      list.append(newElement);

      // Assert
      assertEquals(65, list.get(0));
      assertEquals(25, list.get(1));
      assertEquals(35, list.get(2));
      assertEquals(newElement, list.get(3));
    }
  }

  @Nested
  @DisplayName("When method prepend() is called")
  class TestCasesForMethodPrepend {

    @Test
    @DisplayName("if the list is empty, its size becomes one")
    void givenTheListIsEmptyWhenPrependThenTheListHasOneElement() {
      // Arrange
      List<Integer> list = new ArrayList<>();

      // Act
      int value = 4;
      list.prepend(value);

      // Assert
      assertEquals(1, list.size());
      assertEquals(value, list.get(0));
    }

    @Test
    @DisplayName("if the list is not empty, its size is incremented by one")
    void givenTheListIsNoNEmptyWhenPrependThenTheListSizeIncrementedByOne() {
      // Arrange
      List<Integer> list = ArrayList.of(1, 2, 3, 4, 5);

      // Act
      int currentSize = list.size();
      list.prepend(14);

      // Assert
      int expectedElementsInTheList = currentSize + 1;
      assertEquals(expectedElementsInTheList, list.size());
    }

    @Test
    @DisplayName("the element is at the beginning of the list")
    void givenAListWhenPrependAnElementThenItIsAtTheBeginningOfTheList() {
      // Arrange
      ArrayList<Integer> list = ArrayList.of(65, 25, 35);

      // Act
      int newElement = 2352;
      list.prepend(newElement);
      int gottenElement = list.get(0);

      // Assert
      assertEquals(newElement, gottenElement);
    }

    @Test
    @DisplayName("the elements in the list are in the right order")
    void givenAListWhenPrependAnElementThenItTheOrderOfTheListElementsIsCorrect() {
      // Arrange
      ArrayList<Integer> list = ArrayList.of(65, 25, 35);

      // Act
      int newElement = 2352;
      list.prepend(newElement);

      // Assert
      assertEquals(newElement, list.get(0));
      assertEquals(65, list.get(1));
      assertEquals(25, list.get(2));
      assertEquals(35, list.get(3));
    }
  }

  @Nested
  @DisplayName("When method set() is called")
  class TestCasesForMethodSet {

    @DisplayName("an exception is raised if the list is empty")
    @Test
    void givenAnEmptyListWithSetIsCalledThenAnExceptionIsRaised() {
      // Arrange
      ArrayList<Integer> list = new ArrayList<>();

      // Assert
      assertThrows(ListException.class, () -> list.set(0, 123));
    }

    @DisplayName("an exception is raised if the index is equal to the list size")
    @Test
    void givenAnListWithSetIsCalledWhenAnIndexEqualsToTheListSizeThenAnExceptionIsRaised() {
      // Arrange
      ArrayList<Integer> list = ArrayList.of(3, 6, 1, 6);

      // Assert
      assertThrows(ListException.class, () -> list.set(4, 123));
    }

    @DisplayName("an exception is raised if the index is higher to the list size")
    @Test
    void givenAnListWithSetIsCalledWhenAnIndexHigherThanTheListSizeThenAnExceptionIsRaised() {
      // Arrange
      ArrayList<Integer> list = ArrayList.of(3, 6, 1, 6);

      // Assert
      assertThrows(ListException.class, () -> list.set(6, 123));
    }

    @DisplayName("if the list has one element, the element at position 0 is changed")
    @Test
    void givenAListWithOneElementWhenSetIsCalledAtPositionZeroThenThatElementIsChanged() {
      // Arrange
      ArrayList<Integer> list = ArrayList.of(5);

      // Act
      int newElement = 15;
      list.set(0, newElement);

      // Assert
      assertEquals(newElement, list.get(0));
      assertEquals(1, list.size());
    }

    @DisplayName("if the list has three elements, the element at position 0 is changed")
    @Test
    void givenAListWithThreeElementsWhenSetIsCalledAtPositionZeroThenThatElementIsChanged() {
      // Arrange
      ArrayList<Integer> list = ArrayList.of(3, 9, 6);

      // Act
      int newElement = 15;
      list.set(0, newElement);

      // Assert
      assertEquals(newElement, list.get(0));
      assertEquals(9, list.get(1));
      assertEquals(6, list.get(2));
    }

    @DisplayName("if the list has three elements, the element at position 1 is changed")
    @Test
    void givenAListWithThreeElementsWhenSetIsCalledAtPositionOneThenThatElementIsChanged() {
      // Arrange
      ArrayList<Integer> list = ArrayList.of(3, 9, 6);

      // Act
      int newElement = 15;
      list.set(1, newElement);

      // Assert
      assertEquals(newElement, list.get(1));
      assertEquals(3, list.get(0));
      assertEquals(6, list.get(2));
    }

    @DisplayName("if the list has three elements, the element at position 2 is changed")
    @Test
    void givenAListWithThreeElementsWhenSetIsCalledAtPositionTwoThenThatElementIsChanged() {
      // Arrange
      ArrayList<Integer> list = ArrayList.of(3, 9, 6);

      // Act
      int newElement = 15;
      list.set(2, newElement);

      // Assert
      assertEquals(newElement, list.get(2));
      assertEquals(3, list.get(0));
      assertEquals(9, list.get(1));
    }
  }

  @Nested
  @DisplayName("An exception is raised when")
  class TestCasesForRaisedExceptions {

    @DisplayName("The constructor is called with a negative  value")
    @Test
    void whenConstructorIsCalledWithANegativeValueThenAnExceptionIsThrown() {
      assertThrows(IllegalArgumentException.class, () -> new ArrayList<>(-1));
    }
  }

  @Nested
  @DisplayName("When method insert() is called")
  class TestCasesForMethodInsert {

    @DisplayName("an exception is raised if the list is empty and the index is negative ")
    @Test
    void givenAnEmptyListWhenTheIndexIsNegativeThenAnExceptionIsRaised() {
      // Arrange
      ArrayList<Integer> list = new ArrayList<>();

      // Assert
      assertThrows(ListException.class, () -> list.insert(-1, 123));
    }

    @DisplayName("an exception is raised if the list is not empty and the index is negative ")
    @Test
    void givenANonEmptyListWhenTheIndexIsNegativeThenAnExceptionIsRaised() {
      // Arrange
      ArrayList<Integer> list = ArrayList.of(5, 3, 3);

      // Assert
      assertThrows(ListException.class, () -> list.insert(-1, 123));
    }

    @DisplayName("an exception is raised if the list is empty and the index is greater and zero")
    @Test
    void givenAnEmptyListWhenTheIndexIsGreaterThanZeroThenAnExceptionIsRaised() {
      // Arrange
      ArrayList<Integer> list = new ArrayList<>();

      // Assert
      assertThrows(ListException.class, () -> list.insert(1, 123));
    }

    @DisplayName("an element is inserted if the list is empty and the index is zero")
    @Test
    void givenAnEmptyListWhenTheIndexIsZeroThenTheElementIsInserted() {
      // Arrange
      ArrayList<Integer> list = new ArrayList<>();

      // Act
      int element = 15;
      list.insert(0, element);

      // Assert
      assertEquals(element, list.get(0));
      assertEquals(1, list.size());
    }

    @DisplayName("an element is inserted at the beginning of a list of with one element")
    @Test
    void giveAListWithOneElementWhenTheIndexIsZeroThenElementIsInserted() {
      // Arrange
      ArrayList<Double> list = ArrayList.of(2.2);

      // Act
      double element = -15.5;
      list.insert(0, element);

      // Assert
      assertEquals(2, list.size());
      assertEquals(element, list.get(0));
      assertEquals(2.2, list.get(1));
    }

    @DisplayName("an element is inserted at the end of a list of with one element")
    @Test
    void giveAListWithOneElementWhenTheIndexIsOneThenElementIsInserted() {
      // Arrange
      ArrayList<Double> list = ArrayList.of(2.2);

      // Act
      double element = -15.5;
      list.insert(1, element);

      // Assert
      assertEquals(2, list.size());
      assertEquals(2.2, list.get(0));
      assertEquals(element, list.get(1));
    }

    @DisplayName("an element is inserted at the beginning of a list of with two elements")
    @Test
    void giveAListWithTwoElementsWhenTheIndexIsZeroThenElementIsInserted() {
      // Arrange
      ArrayList<String> list = ArrayList.of("Málaga", "Sevilla");

      // Act
      String element = "Cádiz";
      list.insert(0, element);

      // Assert
      assertEquals(3, list.size());
      assertEquals(element, list.get(0));
      assertEquals("Málaga", list.get(1));
      assertEquals("Sevilla", list.get(2));
    }

    @DisplayName("an element is inserted in the middle of a list of with two elements")
    @Test
    void giveAListWithTwoElementsWhenTheIndexIsOneThenElementIsInserted() {
      // Arrange
      ArrayList<String> list = ArrayList.of("Málaga", "Sevilla");

      // Act
      String element = "Cádiz";
      list.insert(1, element);

      // Assert
      assertEquals(3, list.size());
      assertEquals("Málaga", list.get(0));
      assertEquals(element, list.get(1));
      assertEquals("Sevilla", list.get(2));
    }

    @DisplayName("an element is inserted at the end of a list of with two elements")
    @Test
    void giveAListWithTwoElementsWhenTheIndexIsTwoThenElementIsInserted() {
      // Arrange
      ArrayList<String> list = ArrayList.of("Málaga", "Sevilla");

      // Act
      String element = "Cádiz";
      list.insert(2, element);

      // Assert
      assertEquals(3, list.size());
      assertEquals("Málaga", list.get(0));
      assertEquals("Sevilla", list.get(1));
      assertEquals(element, list.get(2));
    }

    @DisplayName("an element is inserted when the index is equals to the list size")
    @Test
    void givenANonemptyListWhenTheIndexIsEqualsToTheListSizeThenTheElementIsInserted() {
      // Arrange
      ArrayList<Double> list = ArrayList.of(2.2, 3.5, 4.1);

      // Act
      double element = -5;
      int listSize = list.size();
      list.insert(listSize, element);

      // Assert
      assertEquals(listSize + 1, list.size());
      assertEquals(element, list.get(list.size() - 1));
    }
  }

  @Nested
  @DisplayName("A call to method delete()")
  class TestCasesForMethodDelete {

    @DisplayName("raises and exception if the list is empty and the index is negative")
    @Test
    void givenAnEmptyListWhenTheIndexIsNegativeThenAnExceptionIsRaised() {
      // Arrange
      ArrayList<Integer> list = new ArrayList<>();

      // Assert
      assertThrows(ListException.class, () -> list.delete(-1));
    }

    @DisplayName("raises and exception if the list is not empty and the index is negative")
    @Test
    void givenANonEmptyListWhenTheIndexIsNegativeThenAnExceptionIsRaised() {
      // Arrange
      ArrayList<Integer> list = ArrayList.of(2, 5, 3, 5);

      // Assert
      assertThrows(ListException.class, () -> list.delete(-1));
    }

    @DisplayName("raises an exception is the list is empty and the index is zero")
    @Test
    void givenAnEmptyListWhenTheIndexIsZeroThenAnExceptionIsRaised() {
      // Arrange
      ArrayList<Integer> list = new ArrayList<>();

      // Assert
      assertThrows(ListException.class, () -> list.delete(0));
    }

    @DisplayName("deletes the element of a list with an element")
    @Test
    void givenAListWithOneElementWhenTheIndexIsZeroThenTheListIsEmpty() {
      // Arrange
      ArrayList<Boolean> list = new ArrayList<>();
      list.append(true);

      // Act
      list.delete(0);

      // Assert
      assertTrue(list.isEmpty());
    }

    @DisplayName("given a list with three elements, deletes the first one")
    @Test
    void givenAListWithThreeElementsWhenTheIndexIsZeroThenTheFirstElementIsDeleted() {
      // Arrange
      ArrayList<String> list = ArrayList.of("blue", "black", "green");

      // Act
      int listSize = list.size();
      list.delete(0);

      // Assert
      assertEquals(listSize - 1, list.size());
      assertEquals("black", list.get(0));
      assertEquals("green", list.get(1));
    }

    @DisplayName("given a list with three elements, deletes the second one")
    @Test
    void givenAListWithThreeElementsWhenTheIndexIsOneThenTheSecondElementIsDeleted() {
      // Arrange
      ArrayList<String> list = ArrayList.of("blue", "black", "green");

      // Act
      int listSize = list.size();
      list.delete(1);

      // Assert
      assertEquals(listSize - 1, list.size());
      assertEquals("blue", list.get(0));
      assertEquals("green", list.get(1));
    }

    @DisplayName("given a list with three elements, deletes the third one")
    @Test
    void givenAListWithThreeElementsWhenTheIndexIsTwoThenTheThirdElementIsDeleted() {
      // Arrange
      ArrayList<String> list = ArrayList.of("blue", "black", "green");

      // Act
      int listSize = list.size();
      list.delete(2);

      // Assert
      Assertions.assertAll(
          () -> assertEquals(listSize - 1, list.size()),
          () -> assertEquals("blue", list.get(0)),
          () -> assertEquals("black", list.get(1)));
    }

    @DisplayName("given a list with three elements, an exception is raised if the index is three")
    @Test
    void givenAListWithThreeElementsWhenTheIndexIsThreeThenAnExceptionIsRaised() {
      // Arrange
      ArrayList<String> list = ArrayList.of("blue", "black", "green");

      // Assert
      assertThrows(ListException.class, () -> list.delete(3));
    }
  }

  @Nested
  @DisplayName("When method equals() is called")
  class TestCasesForEqualsMethod {

    @DisplayName("true is returned if two lists are empty")
    @Test
    void givenTwoEmptyArrayListsWhenEqualsThenTrue() {
      // Arrange
      List<Integer> list1 = new ArrayList<>();
      List<Integer> list2 = new ArrayList<>();

      // Assert
      assertEquals(list1, list2);
    }

    @DisplayName("true is returned if two equals Lists are non empty")
    @Test
    void givenTwoNonEmptyArrayListsWhenEqualsThenTrue() {
      // Arrange
      List<Integer> List1 = ArrayList.of(1, 2, 3);
      List<Integer> List2 = ArrayList.of(1, 2, 3);

      // Assert
      assertEquals(List1, List2);
    }

    @DisplayName("false is returned if the two lists have one element and they are not equal")
    @Test
    void givenTwoArrayListsWithOneElementWhenNotEqualsThenFalse() {
      // Arrange
      List<Integer> List1 = ArrayList.of(1);
      List<Integer> List2 = ArrayList.of(2);

      // Assert
      assertNotEquals(List1, List2);
    }

    @DisplayName("false is returned if the two lists have three elements and the first one is different")
    @Test
    void givenTwoEqualsArrayListsWithThreeElementsWhenTheFirstOneIsNotEqualsThenFalse() {
      // Arrange
      List<Integer> List1 = ArrayList.of(0, 2, 3);
      List<Integer> List2 = ArrayList.of(1, 2, 3);

      // Assert
      assertNotEquals(List1, List2);
    }

    @DisplayName("false is returned if the two lists have three elements and the second one is different")
    @Test
    void givenTwoEqualsArrayListsWithThreeElementsWhenTheSecondOneIsNotEqualsThenFalse() {
      // Arrange
      List<Integer> List1 = ArrayList.of(1, 2, 3);
      List<Integer> List2 = ArrayList.of(1, 5, 3);

      // Assert
      assertNotEquals(List1, List2);
    }

    @DisplayName("false is returned if the two lists have three elements and the third one is different")
    @Test
    void givenTwoEqualsArrayListsWithThreeElementsWhenTheThirdOneIsNotEqualsThenFalse() {
      // Arrange
      List<Integer> List1 = ArrayList.of(0, 2, 3);
      List<Integer> List2 = ArrayList.of(1, 2, 5);

      // Assert
      assertNotEquals(List1, List2);
    }
  }
}
