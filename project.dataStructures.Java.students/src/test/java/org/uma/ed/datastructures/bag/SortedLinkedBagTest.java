package org.uma.ed.datastructures.bag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;

import java.util.Comparator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test cases for class SortedLinkedBag")
class SortedLinkedBagTest {

  @Nested
  @DisplayName("A SortedLinkedBag is created")
  class CreationTests {

    @Test
    @DisplayName("by calling the constructor with a comparator")
    void constructorWithComparator() {
      SortedBag<Integer> bag = new SortedLinkedBag<Integer>(Comparator.naturalOrder());
      assertNotNull(bag);
      assertTrue(bag.isEmpty());
    }

    @Test
    @DisplayName("by calling the empty() method with a comparator")
    void emptyFactoryWithComparator() {
      Comparator<String> reverseOrder = Comparator.reverseOrder();
      SortedBag<String> bag = SortedLinkedBag.empty(reverseOrder);
      assertNotNull(bag);
      assertTrue(bag.isEmpty());
      assertEquals(reverseOrder, bag.comparator());
    }

    @Test
    @DisplayName("by calling the empty() method without a comparator (natural order)")
    void emptyFactoryNaturalOrder() {
      SortedBag<Integer> bag = SortedLinkedBag.empty();
      assertNotNull(bag);
      assertTrue(bag.isEmpty());
      assertEquals(Comparator.naturalOrder(), bag.comparator());
    }

    @Test
    @DisplayName("from a sequence of values using the of() method")
    void fromOfFactory() {
      SortedBag<String> bag = SortedLinkedBag.of("orange", "blue", "green", "blue");
      assertNotNull(bag);
      assertEquals(4, bag.size());
      assertEquals(2, bag.occurrences("blue"));
      assertEquals("blue", bag.minimum());
    }

    @Test
    @DisplayName("from an iterable of values using the from() method")
    void fromIterableFactory() {
      List<Integer> initialValues = JDKArrayList.of(10, 20, 10, 30);
      SortedBag<Integer> bag = SortedLinkedBag.from(initialValues);
      assertNotNull(bag);
      assertEquals(4, bag.size());
      assertEquals(2, bag.occurrences(10));
      assertEquals(10, bag.minimum());
    }
  }

  @Nested
  @DisplayName("The copyOf() method")
  class CopyOfTests {
    @Test
    @DisplayName("works correctly with an empty SortedLinkedBag")
    void copyOfEmpty() {
      SortedBag<Integer> bag = SortedLinkedBag.empty();
      SortedBag<Integer> copiedBag = SortedLinkedBag.copyOf(bag);
      assertTrue(copiedBag.isEmpty());
      assertEquals(bag, copiedBag);
    }

    @Test
    @DisplayName("works correctly with a non-empty SortedLinkedBag")
    void copyOfNonEmpty() {
      SortedBag<String> bag = SortedLinkedBag.of("C", "A", "B", "A");
      SortedBag<String> copiedBag = SortedLinkedBag.copyOf(bag);
      assertEquals(bag, copiedBag);
      assertNotSame(bag, copiedBag);
    }
  }

  @Nested
  @DisplayName("Core operations: insert(), delete(), occurrences(), contains()")
  class CoreOperationsTests {
    private SortedBag<Integer> bag;

    @BeforeEach
    void setup() {
      bag = SortedLinkedBag.empty();
    }

    @Test
    @DisplayName("insert() adds an element to an empty bag")
    void insertIntoEmpty() {
      bag.insert(42);
      assertEquals(1, bag.size());
      assertEquals(1, bag.occurrences(42));
      assertTrue(bag.contains(42));
    }

    @Test
    @DisplayName("insert() adds a duplicate element, increasing size and occurrences")
    void insertDuplicate() {
      bag.insert(10);
      bag.insert(10);
      assertEquals(2, bag.size());
      assertEquals(2, bag.occurrences(10));
    }

    @Test
    @DisplayName("delete() removes one instance of an element")
    void deleteOneInstance() {
      bag.insert(10);
      bag.insert(10);
      bag.delete(10);
      assertEquals(1, bag.size());
      assertEquals(1, bag.occurrences(10));
      assertTrue(bag.contains(10));
    }

    @Test
    @DisplayName("delete() removes the last instance of an element")
    void deleteLastInstance() {
      bag.insert(10);
      bag.delete(10);
      assertEquals(0, bag.size());
      assertEquals(0, bag.occurrences(10));
      assertFalse(bag.contains(10));
    }

    @Test
    @DisplayName("delete() has no effect on a non-existent element")
    void deleteNonExistent() {
      bag.insert(10);
      bag.delete(99);
      assertEquals(1, bag.size());
    }

    @Test
    @DisplayName("occurrences() returns 0 for a non-existent element")
    void occurrencesOfNonExistent() {
      assertEquals(0, bag.occurrences(99));
    }
  }

  @Nested
  @DisplayName("Extremal operations: minimum() and maximum()")
  class ExtremalOperationsTests {
    @Test
    @DisplayName("minimum() and maximum() throw NoSuchElementException on an empty bag")
    void extremalOnEmptyBag() {
      SortedBag<Integer> bag = SortedLinkedBag.empty();
      assertThrows(NoSuchElementException.class, bag::minimum);
      assertThrows(NoSuchElementException.class, bag::maximum);
    }

    @Test
    @DisplayName("minimum() returns the smallest element")
    void testMinimum() {
      SortedBag<Integer> bag = SortedLinkedBag.of(30, 10, 20);
      assertEquals(10, bag.minimum());
    }

    @Test
    @DisplayName("maximum() returns the largest element")
    void testMaximum() {
      SortedBag<Integer> bag = SortedLinkedBag.of(30, 10, 20);
      assertEquals(30, bag.maximum());
    }

    @Test
    @DisplayName("minimum() and maximum() respect a custom comparator")
    void testMinMaxWithCustomComparator() {
      SortedBag<String> bag = SortedLinkedBag.of(Comparator.reverseOrder(), "C", "A", "B");
      assertEquals("C", bag.minimum());
      assertEquals("A", bag.maximum());
    }
  }

  @Nested
  @DisplayName("Iterator behavior")
  class IteratorTests {
    @Test
    @DisplayName("for an empty bag hasNext() is false")
    void iteratorEmpty() {
      SortedBag<Integer> bag = SortedLinkedBag.empty();
      assertFalse(bag.iterator().hasNext());
    }

    @Test
    @DisplayName("iterates over all elements in sorted order and with correct multiplicity")
    void iteratorAllElementsSortedWithMultiplicity() {
      SortedBag<Integer> bag = SortedLinkedBag.of(5, 1, 4, 2, 3, 3, 1);
      List<Integer> expected = JDKArrayList.of(1, 1, 2, 3, 3, 4, 5);

      List<Integer> actual = JDKArrayList.empty();
      bag.iterator().forEachRemaining(actual::append);

      assertEquals(expected, actual);
    }
  }

  @Nested
  @DisplayName("Special set-like operations")
  class SetLikeOperationsTests {
    private SortedLinkedBag<Integer> bag1;
    private SortedLinkedBag<Integer> bag2;

    @BeforeEach
    void setup() {
      bag1 = SortedLinkedBag.of(1, 2, 2, 3, 5);
      bag2 = SortedLinkedBag.of(2, 3, 3, 4);
    }

    @Test
    @DisplayName("union() correctly combines two bags")
    void testUnion() {
      bag1.union(bag2);
      SortedBag<Integer> expected = SortedLinkedBag.of(1, 2, 2, 2, 3, 3, 3, 4, 5);
      assertEquals(expected, bag1);
      assertEquals(9, bag1.size());
      assertEquals(3, bag1.occurrences(2));
      assertEquals(3, bag1.occurrences(3));
    }

    @Test
    @DisplayName("intersection() correctly finds common elements")
    void testIntersection() {
      bag1.intersection(bag2);
      SortedBag<Integer> expected = SortedLinkedBag.of(2, 3);
      assertEquals(expected, bag1);
      assertEquals(2, bag1.size());
      assertEquals(1, bag1.occurrences(2)); // min(2, 1) = 1
      assertEquals(1, bag1.occurrences(3)); // min(1, 2) = 1
    }

    @Test
    @DisplayName("difference() correctly subtracts elements")
    void testDifference() {
      bag1.difference(bag2);
      SortedBag<Integer> expected = SortedLinkedBag.of(1, 2, 5);
      assertEquals(expected, bag1);
      assertEquals(3, bag1.size());
      assertEquals(1, bag1.occurrences(2)); // 2 - 1 = 1
      assertEquals(0, bag1.occurrences(3)); // 1 - 2 <= 0, so 0
    }
  }

  @Nested
  @DisplayName("The equals() and hashCode() methods")
  class EqualityAndHashCodeTests {
    @Test
    @DisplayName("equals() returns true for two identical bags")
    void testEqualsWithIdenticalBags() {
      SortedBag<Integer> bag1 = SortedLinkedBag.of(1, 2, 2, 3);
      SortedBag<Integer> bag2 = SortedLinkedBag.of(3, 2, 1, 2);
      assertEquals(bag1, bag2);
      assertEquals(bag1.hashCode(), bag2.hashCode());
    }

    @Test
    @DisplayName("equals() returns true for a sorted bag and an unordered bag with the same content")
    void testEqualsWithUnorderedImplementation() {
      SortedBag<Integer> bag1 = SortedLinkedBag.of(2, 1, 3, 1);
      Bag<Integer> bag2 = HashBag.of(1, 3, 1, 2);
      assertEquals(bag1, bag2);
      assertEquals(bag1.hashCode(), bag2.hashCode());
    }

    @Test
    @DisplayName("equals() returns false for bags with different content")
    void testEqualsWithDifferentContent() {
      SortedBag<Integer> bag1 = SortedLinkedBag.of(1, 2, 3);
      SortedBag<Integer> bag2 = SortedLinkedBag.of(1, 2, 4);
      assertNotEquals(bag1, bag2);
    }
  }
}