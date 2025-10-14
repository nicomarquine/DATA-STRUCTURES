package org.uma.ed.datastructures.set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test cases for class HashSet")
class HashSetTest {

  @Nested
  @DisplayName("A HashSet is created")
  class CreationTests {

    @Test
    @DisplayName("by calling the default constructor")
    void defaultConstructor() {
      Set<Integer> set = new HashSet<>();
      assertNotNull(set);
      assertTrue(set.isEmpty());
    }

    @Test
    @DisplayName("by calling the constructor with capacity and load factor")
    void constructorWithCapacity() {
      Set<Integer> set = new HashSet<>(50, 0.75);
      assertNotNull(set);
      assertTrue(set.isEmpty());
    }

    @Test
    @DisplayName("by calling the empty() factory method")
    void emptyFactory() {
      Set<Integer> set = HashSet.empty();
      assertTrue(set.isEmpty());
    }

    @Test
    @DisplayName("by calling the withCapacity() factory method")
    void withCapacityFactory() {
      Set<Integer> set = HashSet.withCapacity(100);
      assertTrue(set.isEmpty());
    }

    @Test
    @DisplayName("from a sequence of values using the of() method, ignoring duplicates")
    void fromOfFactory() {
      Set<String> set = HashSet.of("apple", "banana", "apple");
      assertNotNull(set);
      assertEquals(2, set.size());
      assertTrue(set.contains("apple"));
      assertTrue(set.contains("banana"));
    }

    @Test
    @DisplayName("from an iterable of values using the from() method, ignoring duplicates")
    void fromIterableFactory() {
      List<Integer> initialValues = JDKArrayList.of(10, 20, 10, 30);
      Set<Integer> set = JDKHashSet.from(initialValues);
      assertNotNull(set);
      assertEquals(3, set.size());
      assertTrue(set.contains(20));
    }
  }

  @Nested
  @DisplayName("The copyOf() method")
  class CopyOfTests {

    @Test
    @DisplayName("works correctly with an empty HashSet")
    void copyOfEmpty() {
      HashSet<Integer> set = HashSet.empty();
      HashSet<Integer> copiedSet = HashSet.copyOf(set);
      assertTrue(copiedSet.isEmpty());
      assertEquals(set, copiedSet);
      assertNotSame(set, copiedSet);
    }

    @Test
    @DisplayName("works correctly with a non-empty HashSet")
    void copyOfNonEmpty() {
      HashSet<String> set = HashSet.of("C", "A", "B");
      HashSet<String> copiedSet = HashSet.copyOf(set);
      assertEquals(set, copiedSet);
      assertNotSame(set, copiedSet);
    }

    @Test
    @DisplayName("works correctly with another Set implementation (e.g., JDKHashSet)")
    void copyOfAnotherSetType() {
      Set<Integer> sortedSet = JDKHashSet.of(10, 20, 5);
      Set<Integer> hashSet = HashSet.copyOf(sortedSet);
      assertEquals(sortedSet, hashSet);
      assertEquals(3, hashSet.size());
    }
  }

  @Nested
  @DisplayName("Basic properties: isEmpty(), size(), clear()")
  class BasicPropertiesTests {
    private Set<Integer> set;

    @BeforeEach
    void setup() {
      set = HashSet.empty();
    }

    @Test
    @DisplayName("isEmpty() is true for new set and false after insert")
    void testIsEmpty() {
      assertTrue(set.isEmpty());
      set.insert(1);
      assertFalse(set.isEmpty());
    }

    @Test
    @DisplayName("size() correctly reflects the number of unique elements")
    void testSize() {
      assertEquals(0, set.size());
      set.insert(10);
      set.insert(20);
      assertEquals(2, set.size());
      set.insert(10); // Duplicate
      assertEquals(2, set.size());
      set.delete(10);
      assertEquals(1, set.size());
    }

    @Test
    @DisplayName("clear() empties a non-empty set")
    void testClear() {
      set = HashSet.of(1, 2, 3);
      set.clear();
      assertTrue(set.isEmpty());
      assertEquals(0, set.size());
    }
  }

  @Nested
  @DisplayName("Core operations: insert(), delete(), contains()")
  class CoreOperationsTests {
    private Set<Integer> set;

    @BeforeEach
    void setup() {
      set = HashSet.empty();
    }

    @Test
    @DisplayName("insert() adds a new element")
    void insertNewElement() {
      set.insert(42);
      assertEquals(1, set.size());
      assertTrue(set.contains(42));
    }

    @Test
    @DisplayName("insert() has no effect if the element already exists")
    void insertExistingElement() {
      set.insert(10);
      set.insert(10);
      assertEquals(1, set.size());
    }

    @Test
    @DisplayName("delete() removes an existing element")
    void deleteExistingElement() {
      set.insert(10);
      set.insert(20);
      set.delete(10);
      assertFalse(set.contains(10));
      assertTrue(set.contains(20));
      assertEquals(1, set.size());
    }

    @Test
    @DisplayName("delete() has no effect on a non-existent element")
    void deleteNonExistent() {
      set.insert(10);
      set.delete(99);
      assertEquals(1, set.size());
    }
  }

  @Nested
  @DisplayName("Iterator behavior")
  class IteratorTests {
    @Test
    @DisplayName("for an empty set hasNext() is false")
    void iteratorEmpty() {
      Set<Integer> set = HashSet.empty();
      assertFalse(set.iterator().hasNext());
      assertThrows(NoSuchElementException.class, () -> set.iterator().next());
    }

    @Test
    @DisplayName("iterates over all elements, regardless of order")
    void iteratorAllElements() {
      List<Integer> initialData = JDKArrayList.of(5, 1, 4, 2, 3);
      Set<Integer> set = HashSet.from(initialData);

      Set<Integer> expectedElements = JDKHashSet.from(initialData);
      Set<Integer> actualElements = JDKHashSet.empty();
      set.iterator().forEachRemaining(actualElements::insert);

      assertEquals(expectedElements, actualElements);
      assertEquals(initialData.size(), actualElements.size());
    }
  }

  @Nested
  @DisplayName("The equals() and hashCode() methods")
  class EqualityAndHashCodeTests {
    @Test
    @DisplayName("equals() returns true for two identical hash sets")
    void testEqualsWithIdenticalSets() {
      Set<Integer> set1 = HashSet.of(1, 2, 3);
      Set<Integer> set2 = HashSet.of(3, 1, 2); // Different insertion order
      assertEquals(set1, set2);
      assertEquals(set1.hashCode(), set2.hashCode());
    }

    @Test
    @DisplayName("equals() returns true for a HashSet and a SortedSet with the same content")
    void testEqualsWithDifferentImplementations() {
      Set<Integer> set1 = HashSet.of(1, 2, 3);
      Set<Integer> set2 = AVLSet.of(3, 1, 2);
      assertEquals(set1, set2);
      assertEquals(set1.hashCode(), set2.hashCode());
    }

    @Test
    @DisplayName("equals() returns false for sets with different sizes")
    void testEqualsWithDifferentSizes() {
      Set<Integer> set1 = HashSet.of(1, 2);
      Set<Integer> set2 = HashSet.of(1, 2, 3);
      assertNotEquals(set1, set2);
    }

    @Test
    @DisplayName("equals() returns false for sets with different content")
    void testEqualsWithDifferentContent() {
      Set<Integer> set1 = HashSet.of(1, 2, 3);
      Set<Integer> set2 = HashSet.of(1, 2, 4);
      assertNotEquals(set1, set2);
    }
  }
}