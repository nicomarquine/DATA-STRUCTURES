package org.uma.ed.datastructures.bag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.uma.ed.datastructures.dictionary.Dictionary;
import org.uma.ed.datastructures.dictionary.JDKHashDictionary;
import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test cases for class HashBag")
class HashBagTest {

  @Nested
  @DisplayName("A HashBag is created")
  class CreationTests {

    @Test
    @DisplayName("by calling the default constructor")
    void defaultConstructor() {
      Bag<Integer> bag = new HashBag<>();
      assertNotNull(bag);
      assertTrue(bag.isEmpty());
    }

    @Test
    @DisplayName("by calling the constructor with capacity and load factor")
    void constructorWithCapacity() {
      Bag<Integer> bag = new HashBag<>(50, 0.75);
      assertNotNull(bag);
      assertTrue(bag.isEmpty());
    }

    @Test
    @DisplayName("by calling the empty() factory method")
    void emptyFactory() {
      Bag<Integer> bag = HashBag.empty();
      assertTrue(bag.isEmpty());
    }

    @Test
    @DisplayName("by calling the withCapacity() factory method")
    void withCapacityFactory() {
      Bag<Integer> bag = HashBag.withCapacity(100);
      assertTrue(bag.isEmpty());
    }

    @Test
    @DisplayName("from a sequence of values using the of() method")
    void fromOfFactory() {
      Bag<String> bag = HashBag.of("apple", "banana", "apple");
      assertNotNull(bag);
      assertEquals(3, bag.size());
      assertEquals(2, bag.occurrences("apple"));
    }

    @Test
    @DisplayName("from an iterable of values using the from() method")
    void fromIterableFactory() {
      List<Integer> initialValues = JDKArrayList.of(10, 20, 10, 30);
      Bag<Integer> bag = HashBag.from(initialValues);
      assertNotNull(bag);
      assertEquals(4, bag.size());
      assertEquals(2, bag.occurrences(10));
    }
  }

  @Nested
  @DisplayName("The copyOf() method")
  class CopyOfTests {

    @Test
    @DisplayName("works correctly with an empty HashBag")
    void copyOfEmpty() {
      HashBag<Integer> bag = HashBag.empty();
      HashBag<Integer> copiedBag = HashBag.copyOf(bag);
      assertTrue(copiedBag.isEmpty());
      assertEquals(bag, copiedBag);
    }

    @Test
    @DisplayName("works correctly with a non-empty HashBag")
    void copyOfNonEmpty() {
      HashBag<String> bag = HashBag.of("C", "A", "B", "A");
      HashBag<String> copiedBag = HashBag.copyOf(bag);
      assertEquals(bag, copiedBag);
      assertNotSame(bag, copiedBag);
    }

    @Test
    @DisplayName("works correctly with another Bag implementation (e.g., SortedLinkedBag)")
    void copyOfAnotherBagType() {
      Bag<Integer> sortedBag = SortedLinkedBag.of(10, 20, 5, 10);
      Bag<Integer> hashBag = HashBag.copyOf(sortedBag);
      assertEquals(sortedBag, hashBag);
      assertEquals(4, hashBag.size());
      assertEquals(2, hashBag.occurrences(10));
    }
  }

  @Nested
  @DisplayName("Basic properties: isEmpty(), size(), clear()")
  class BasicPropertiesTests {
    private Bag<Integer> bag;

    @BeforeEach
    void setup() {
      bag = HashBag.empty();
    }

    @Test
    @DisplayName("isEmpty() is true for new bag and false after insert")
    void testIsEmpty() {
      assertTrue(bag.isEmpty());
      bag.insert(1);
      assertFalse(bag.isEmpty());
    }

    @Test
    @DisplayName("size() correctly reflects the total number of elements")
    void testSize() {
      assertEquals(0, bag.size());
      bag.insert(10);
      bag.insert(20);
      bag.insert(10); // Duplicate
      assertEquals(3, bag.size());
      bag.delete(10);
      assertEquals(2, bag.size());
    }

    @Test
    @DisplayName("clear() empties a non-empty bag")
    void testClear() {
      bag = HashBag.of(1, 2, 3);
      bag.clear();
      assertTrue(bag.isEmpty());
      assertEquals(0, bag.size());
    }
  }

  @Nested
  @DisplayName("Core operations: insert(), delete(), occurrences(), contains()")
  class CoreOperationsTests {
    private Bag<Integer> bag;

    @BeforeEach
    void setup() {
      bag = HashBag.empty();
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
    @DisplayName("occurrences() returns correct count for various scenarios")
    void testOccurrences() {
      bag = HashBag.of(1, 2, 2, 3, 3, 3);
      assertEquals(0, bag.occurrences(99));
      assertEquals(1, bag.occurrences(1));
      assertEquals(2, bag.occurrences(2));
      assertEquals(3, bag.occurrences(3));
    }
  }

  @Nested
  @DisplayName("Iterator behavior")
  class IteratorTests {
    @Test
    @DisplayName("for an empty bag hasNext() is false")
    void iteratorEmpty() {
      Bag<Integer> bag = HashBag.empty();
      assertFalse(bag.iterator().hasNext());
      assertThrows(NoSuchElementException.class, () -> bag.iterator().next());
    }

    @Test
    @DisplayName("iterates over all elements with correct multiplicity, regardless of order")
    void iteratorAllElementsWithMultiplicity() {
      Integer[] initialData = {5, 1, 4, 2, 3, 3, 1, 5, 5};
      Bag<Integer> bag = HashBag.of(initialData);

      // Use a frequency map to verify content, as order is not guaranteed.
      Dictionary<Integer, Integer> expectedFreq = JDKHashDictionary.empty();
      for (Integer item : initialData) {
        expectedFreq.insert(item, expectedFreq.valueOfOrDefault(item, 0) + 1);
      }

      Dictionary<Integer, Integer> actualFreq = JDKHashDictionary.empty();
      for (Integer item : bag) {
        actualFreq.insert(item, actualFreq.valueOfOrDefault(item, 0) + 1);
      }

      assertEquals(expectedFreq, actualFreq);
    }
  }

  @Nested
  @DisplayName("The equals() and hashCode() methods")
  class EqualityAndHashCodeTests {
    @Test
    @DisplayName("equals() returns true for two identical hash bags")
    void testEqualsWithIdenticalBags() {
      Bag<Integer> bag1 = HashBag.of(1, 2, 2, 3);
      Bag<Integer> bag2 = HashBag.of(3, 2, 1, 2); // Different insertion order
      assertEquals(bag1, bag2);
      assertEquals(bag1.hashCode(), bag2.hashCode());
    }

    @Test
    @DisplayName("equals() returns true for a HashBag and a SortedBag with the same content")
    void testEqualsWithDifferentImplementations() {
      Bag<Integer> bag1 = HashBag.of(1, 2, 3, 1);
      Bag<Integer> bag2 = SortedLinkedBag.of(3, 1, 2, 1);
      assertEquals(bag1, bag2);
      assertEquals(bag1.hashCode(), bag2.hashCode());
    }

    @Test
    @DisplayName("equals() returns false for bags with different sizes")
    void testEqualsWithDifferentSizes() {
      Bag<Integer> bag1 = HashBag.of(1, 2);
      Bag<Integer> bag2 = HashBag.of(1, 2, 2);
      assertNotEquals(bag1, bag2);
    }

    @Test
    @DisplayName("equals() returns false for bags with different content")
    void testEqualsWithDifferentContent() {
      Bag<Integer> bag1 = HashBag.of(1, 2, 3);
      Bag<Integer> bag2 = HashBag.of(1, 2, 4);
      assertNotEquals(bag1, bag2);
    }
  }
}