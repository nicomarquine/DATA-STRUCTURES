package org.uma.ed.datastructures.set;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;

@DisplayName("Test cases for class AVLSet")
class AVLSetTest {

  @Nested
  @DisplayName("An AVLSet is created")
  class TestCasesForCreatingSets {

    @Test
    @DisplayName("by calling the constructor with a comparator")
    void whenTheConstructorIsCalledThenAnAVLSetIsCreated() {
      // Arrange
      SortedSet<Integer> set = new AVLSet<Integer>(Comparator.naturalOrder());

      // Assert
      assertNotNull(set);
      assertTrue(set.isEmpty());
    }

    @Test
    @DisplayName("by calling the empty() method with a comparator")
    void whenTheEmptyMethodWithComparatorIsCalledThenAnAVLSetIsCreated() {
      // Arrange
      Comparator<String> reverseOrder = Comparator.reverseOrder();
      SortedSet<String> set = AVLSet.empty(reverseOrder);

      // Assert
      assertNotNull(set);
      assertTrue(set.isEmpty());
      assertEquals(reverseOrder, set.comparator());
    }

    @Test
    @DisplayName("by calling the empty() method without a comparator (natural order)")
    void whenTheEmptyMethodIsCalledThenAnAVLSetIsCreated() {
      // Arrange
      SortedSet<Integer> set = AVLSet.empty();

      // Assert
      assertNotNull(set);
      assertTrue(set.isEmpty());
      assertEquals(Comparator.naturalOrder(), set.comparator());
    }

    @Test
    @DisplayName("from a sequence of unique values using the of() method")
    void whenTheOfMethodIsCalledWithUniqueValuesThenAnAVLSetIsCreated() {
      // Arrange
      SortedSet<String> set = AVLSet.of("orange", "blue", "green");

      // Assert
      assertNotNull(set);
      assertEquals(3, set.size());
      assertEquals("AVLSet(blue, green, orange)", set.toString());
    }

    @Test
    @DisplayName("from a sequence with duplicate values using the of() method")
    void whenTheOfMethodIsCalledWithDuplicateValuesThenDuplicatesAreIgnored() {
      // Arrange
      SortedSet<Integer> set = AVLSet.of(5, 3, 2, 1, 3, 5, 8);

      // Assert
      assertEquals(5, set.size());
      assertEquals("AVLSet(1, 2, 3, 5, 8)", set.toString());
    }

    @Test
    @DisplayName("from an iterable of values using the from() method")
    void givenAnIterableWhenTheFromMethodIsCalledThenAnAVLSetIsCreated() {
      // Arrange
      List<Integer> initialValues = JDKArrayList.of(10, 20, 10, 30);
      SortedSet<Integer> set = AVLSet.from(initialValues);

      // Assert
      assertNotNull(set);
      assertEquals(3, set.size());
      assertTrue(set.contains(20));
    }
  }

  @Nested
  @DisplayName("The isEmpty() and size() methods")
  class TestCasesForIsEmptyAndSize {

    @Test
    @DisplayName("isEmpty() returns true for a new set")
    void givenANewSetThenItIsEmpty() {
      var set = AVLSet.empty();
      assertTrue(set.isEmpty());
      assertEquals(0, set.size());
    }

    @Test
    @DisplayName("isEmpty() returns false after an insertion")
    void givenAnEmptySetWhenAnElementIsInsertedThenItIsNotEmpty() {
      var set = AVLSet.<Integer>empty();
      set.insert(1);
      assertFalse(set.isEmpty());
      assertEquals(1, set.size());
    }
  }

  @Nested
  @DisplayName("The copyOf() method")
  class TestCasesForCopyOf {

    @Test
    @DisplayName("works correctly with an empty AVLSet")
    void givenAnEmptyAVLSetWhenCopyOfThenTheNewSetIsAlsoEmpty() {
      SortedSet<Integer> set = AVLSet.empty();
      SortedSet<Integer> copiedSet = AVLSet.copyOf(set);
      assertTrue(copiedSet.isEmpty());
      assertEquals(set, copiedSet);
    }

    @Test
    @DisplayName("works correctly with a non-empty AVLSet")
    void givenANonEmptyAVLSetWhenCopyOfThenTheNewSetIsEqual() {
      SortedSet<String> set = AVLSet.of("C", "A", "B");
      SortedSet<String> copiedSet = AVLSet.copyOf(set);
      assertEquals(set, copiedSet);
      assertNotSame(set, copiedSet);
    }
  }

  @Nested
  @DisplayName("The insert() method")
  class TestCasesForInsert {

    @Test
    @DisplayName("adds an element to an empty set")
    void whenInsertIntoEmptySetThenSizeIsOne() {
      SortedSet<Integer> set = AVLSet.empty();
      set.insert(42);
      assertEquals(1, set.size());
      assertTrue(set.contains(42));
    }

    @Test
    @DisplayName("does not change the size if the element already exists")
    void whenInsertExistingElementThenSizeIsUnchanged() {
      SortedSet<Integer> set = AVLSet.of(1, 2, 3);
      set.insert(2);
      assertEquals(3, set.size());
    }

    @Test
    @DisplayName("maintains the sorted order")
    void whenInsertElementsThenSortedOrderIsMaintained() {
      SortedSet<Integer> set = AVLSet.empty();
      set.insert(30);
      set.insert(10);
      set.insert(20);
      assertEquals("AVLSet(10, 20, 30)", set.toString());
    }
  }

  @Nested
  @DisplayName("The delete() method")
  class TestCasesForDelete {

    @Test
    @DisplayName("does nothing on an empty set")
    void whenDeleteFromEmptySetThenItRemainsEmpty() {
      SortedSet<Integer> set = AVLSet.empty();
      set.delete(1);
      assertTrue(set.isEmpty());
    }

    @Test
    @DisplayName("removes an existing element and decreases size")
    void whenDeleteExistingElementThenItIsRemoved() {
      SortedSet<Integer> set = AVLSet.of(1, 2, 3);
      set.delete(2);
      assertEquals(2, set.size());
      assertFalse(set.contains(2));
    }

    @Test
    @DisplayName("does nothing if the element does not exist")
    void whenDeleteNonExistentKeyThenSetIsUnchanged() {
      SortedSet<Integer> set = AVLSet.of(1, 2, 3);
      set.delete(4);
      assertEquals(3, set.size());
    }
  }

  @Nested
  @DisplayName("The clear() method")
  class TestCasesForClear {
    @Test
    @DisplayName("empties a non-empty set")
    void whenClearIsCalledThenTheSetIsEmpty() {
      SortedSet<Integer> set = AVLSet.of(1, 2, 3);
      set.clear();
      assertTrue(set.isEmpty());
      assertEquals(0, set.size());
    }
  }

  @Nested
  @DisplayName("The contains() method")
  class TestCasesForContains {
    @Test
    @DisplayName("returns true for an existing element and false otherwise")
    void testContains() {
      SortedSet<Integer> set = AVLSet.of(10, 20, 30);
      assertTrue(set.contains(20));
      assertFalse(set.contains(15));
    }
  }

  @Nested
  @DisplayName("Extremal operations: minimum() and maximum()")
  class TestCasesForExtremalOperations {

    @Test
    @DisplayName("minimum() throws exception on an empty set")
    void whenMinimumOnEmptySetThenThrowsException() {
      SortedSet<Integer> set = AVLSet.empty();
      assertThrows(NoSuchElementException.class, set::minimum);
    }

    @Test
    @DisplayName("maximum() throws exception on an empty set")
    void whenMaximumOnEmptySetThenThrowsException() {
      SortedSet<Integer> set = AVLSet.empty();
      assertThrows(NoSuchElementException.class, set::maximum);
    }

    @Test
    @DisplayName("minimum() returns the smallest element")
    void testMinimum() {
      SortedSet<Integer> set = AVLSet.of(30, 10, 20);
      assertEquals(10, set.minimum());
    }

    @Test
    @DisplayName("maximum() returns the largest element")
    void testMaximum() {
      SortedSet<Integer> set = AVLSet.of(30, 10, 20);
      assertEquals(30, set.maximum());
    }

    @Test
    @DisplayName("minimum() and maximum() work with a custom comparator")
    void testMinMaxWithCustomComparator() {
      SortedSet<String> set = AVLSet.of(Comparator.reverseOrder(), "C", "A", "B");
      assertEquals("C", set.minimum()); // 'C' is smallest in reverse order
      assertEquals("A", set.maximum()); // 'A' is largest in reverse order
    }
  }

  @Nested
  @DisplayName("The iterator()")
  class TestCasesForIterator {

    @Test
    @DisplayName("iterates in sorted key order")
    void testIteratorOrder() {
      SortedSet<Integer> set = AVLSet.of(3, 1, 2, 5, 4);
      List<Integer> expected = JDKArrayList.of(1, 2, 3, 4, 5);

      List<Integer> actual = JDKArrayList.empty();
      set.iterator().forEachRemaining(actual::append);

      assertEquals(expected, actual);
    }
  }

  @Nested
  @DisplayName("The equals() and hashCode() methods")
  class TestCasesForEqualsAndHashCode {

    @Test
    @DisplayName("equals() returns true for two identical AVLSets")
    void testEqualsWithIdenticalAVLSets() {
      SortedSet<Integer> set1 = AVLSet.of(1, 2, 3);
      SortedSet<Integer> set2 = AVLSet.of(3, 1, 2);
      assertEquals(set1, set2);
      assertEquals(set1.hashCode(), set2.hashCode());
    }

    @Test
    @DisplayName("equals() returns true for a SortedSet and a HashSet with the same content")
    void testEqualsWithUnorderedImplementation() {
      SortedSet<Integer> set1 = AVLSet.of(2, 1, 3);
      Set<Integer> set2 = HashSet.of(1, 2, 3);
      assertEquals(set1, set2);
      assertEquals(set1.hashCode(), set2.hashCode());
    }

    @Test
    @DisplayName("equals() returns false for sets with different sizes")
    void testEqualsWithDifferentSizes() {
      SortedSet<Integer> set1 = AVLSet.of(1, 2);
      SortedSet<Integer> set2 = AVLSet.of(1, 2, 3);
      assertNotEquals(set1, set2);
    }

    @Test
    @DisplayName("equals() returns false for sets with different content")
    void testEqualsWithDifferentContent() {
      SortedSet<Integer> set1 = AVLSet.of(1, 2, 3);
      SortedSet<Integer> set2 = AVLSet.of(1, 2, 4);
      assertNotEquals(set1, set2);
    }
  }
}