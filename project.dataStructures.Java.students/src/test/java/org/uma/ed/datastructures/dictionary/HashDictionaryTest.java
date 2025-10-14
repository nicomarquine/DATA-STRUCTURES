package org.uma.ed.datastructures.dictionary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.uma.ed.datastructures.dictionary.Dictionary.Entry;
import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;
import org.uma.ed.datastructures.set.HashSet;
import org.uma.ed.datastructures.set.JDKHashSet;
import org.uma.ed.datastructures.set.Set;


import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test cases for class HashDictionary")
class HashDictionaryTest {

  @Nested
  @DisplayName("A HashDictionary is created")
  class CreationTests {

    @Test
    @DisplayName("by calling the default constructor")
    void defaultConstructor() {
      Dictionary<Integer, String> dict = new HashDictionary<>();
      assertNotNull(dict);
      assertTrue(dict.isEmpty());
    }

    @Test
    @DisplayName("by calling the constructor with capacity and load factor")
    void constructorWithCapacity() {
      Dictionary<Integer, String> dict = new HashDictionary<>(50, 0.75);
      assertNotNull(dict);
      assertTrue(dict.isEmpty());
    }

    @Test
    @DisplayName("by calling the empty() factory method")
    void emptyFactory() {
      Dictionary<Integer, String> dict = HashDictionary.empty();
      assertTrue(dict.isEmpty());
    }

    @Test
    @DisplayName("by calling the withCapacity() factory method")
    void withCapacityFactory() {
      Dictionary<Integer, String> dict = HashDictionary.withCapacity(100);
      assertTrue(dict.isEmpty());
    }

    @Test
    @DisplayName("from a sequence of entries using the of() method")
    void fromOfFactory() {
      Dictionary<String, Integer> dict = HashDictionary.of(
          Entry.of("three", 3),
          Entry.of("one", 1),
          Entry.of("two", 2)
      );
      assertNotNull(dict);
      assertEquals(3, dict.size());
      assertEquals(1, dict.valueOf("one"));
    }

    @Test
    @DisplayName("from a sequence with duplicate keys using of(), keeping the last value")
    void fromOfFactoryWithDuplicates() {
      Dictionary<String, Integer> dict = HashDictionary.of(
          Entry.of("one", 1),
          Entry.of("two", 2),
          Entry.of("one", 111)
      );
      assertEquals(2, dict.size());
      assertEquals(111, dict.valueOf("one"));
    }

    @Test
    @DisplayName("from an iterable of entries using the from() method")
    void fromIterableFactory() {
      List<Entry<Integer, String>> initialEntries = JDKArrayList.of(
          Entry.of(1, "one"),
          Entry.of(2, "two"),
          Entry.of(3, "three")
      );
      Dictionary<Integer, String> dict = HashDictionary.from(initialEntries);
      assertNotNull(dict);
      assertEquals(initialEntries.size(), dict.size());
      assertEquals("two", dict.valueOf(2));
    }
  }

  @Nested
  @DisplayName("The copyOf() method")
  class CopyOfTests {

    @Test
    @DisplayName("works correctly with an empty HashDictionary")
    void copyOfEmpty() {
      HashDictionary<Integer, String> dict = HashDictionary.empty();
      HashDictionary<Integer, String> copiedDict = HashDictionary.copyOf(dict);
      assertTrue(copiedDict.isEmpty());
      assertEquals(dict, copiedDict);
    }

    @Test
    @DisplayName("works correctly with a non-empty HashDictionary")
    void copyOfNonEmpty() {
      HashDictionary<String, Integer> dict = HashDictionary.of(Entry.of("C", 3), Entry.of("A", 1));
      HashDictionary<String, Integer> copiedDict = HashDictionary.copyOf(dict);
      assertEquals(dict, copiedDict);
      assertNotSame(dict, copiedDict);
    }

    @Test
    @DisplayName("works correctly with another Dictionary implementation (e.g., AVLDictionary)")
    void copyOfAnotherDictionaryType() {
      Dictionary<Integer, String> sortedDict = AVLDictionary.of(Entry.of(10, "ten"), Entry.of(20, "twenty"));
      Dictionary<Integer, String> hashDict = HashDictionary.copyOf(sortedDict);
      assertEquals(sortedDict, hashDict);
      assertEquals(2, hashDict.size());
    }
  }

  @Nested
  @DisplayName("Core operations: insert(), delete(), valueOf(), isDefinedAt()")
  class CoreOperationsTests {
    private Dictionary<Integer, String> dict;

    @BeforeEach
    void setup() {
      dict = HashDictionary.empty();
    }

    @Test
    @DisplayName("insert() adds a key-value pair and isDefinedAt() finds it")
    void insertAndIsDefinedAt() {
      assertFalse(dict.isDefinedAt(10));
      dict.insert(10, "ten");
      assertTrue(dict.isDefinedAt(10));
      assertEquals(1, dict.size());
    }

    @Test
    @DisplayName("insert() updates the value if the key already exists")
    void insertUpdatesValue() {
      dict.insert(10, "ten");
      dict.insert(10, "diez");
      assertEquals(1, dict.size());
      assertEquals("diez", dict.valueOf(10));
    }

    @Test
    @DisplayName("delete() removes an existing entry")
    void deleteExisting() {
      dict.insert(10, "ten");
      dict.delete(10);
      assertFalse(dict.isDefinedAt(10));
      assertEquals(0, dict.size());
    }

    @Test
    @DisplayName("delete() does nothing for a non-existing key")
    void deleteNonExisting() {
      dict.insert(10, "ten");
      dict.delete(99);
      assertEquals(1, dict.size());
    }

    @Test
    @DisplayName("valueOf() returns the correct value or null")
    void valueOfTest() {
      dict.insert(10, "ten");
      assertEquals("ten", dict.valueOf(10));
      assertNull(dict.valueOf(99));
    }

    @Test
    @DisplayName("valueOfOrDefault() returns the value or the default")
    void valueOfOrDefaultTest() {
      dict.insert(10, "ten");

      assertEquals("ten", dict.valueOfOrDefault(10, "default"));
      assertEquals("default", dict.valueOfOrDefault(20, "default")); // Non-existing key
      assertEquals("default", dict.valueOfOrDefault(99, "default"));
    }

    @Test
    @DisplayName("clear() empties the dictionary")
    void clearTest() {
      dict.insert(10, "ten");
      dict.insert(20, "twenty");
      dict.clear();
      assertTrue(dict.isEmpty());
      assertEquals(0, dict.size());
    }
  }

  @Nested
  @DisplayName("Iterables: keys(), values(), entries()")
  class IterablesTests {
    private Dictionary<Integer, String> dict;
    private Set<Integer> expectedKeys;
    private Set<String> expectedValues;
    private Set<Entry<Integer, String>> expectedEntries;

    @BeforeEach
    void setup() {
      dict = HashDictionary.of(
          Entry.of(1, "one"),
          Entry.of(2, "two"),
          Entry.of(3, "three")
      );
      expectedKeys = JDKHashSet.of(1, 2, 3);
      expectedValues = JDKHashSet.of("one", "two", "three");
      expectedEntries = JDKHashSet.of(Entry.of(1, "one"), Entry.of(2, "two"), Entry.of(3, "three"));
    }

    @Test
    @DisplayName("entries() iterates over all key-value pairs")
    void entriesIterator() {
      Set<Entry<Integer, String>> actualEntries = new HashSet<>();
      dict.entries().forEach(actualEntries::insert);
      assertEquals(expectedEntries, actualEntries);
    }

    @Test
    @DisplayName("keys() iterates over all keys")
    void keysIterator() {
      Set<Integer> actualKeys = new HashSet<>();
      dict.keys().forEach(actualKeys::insert);
      assertEquals(expectedKeys, actualKeys);
    }

    @SuppressWarnings("unchecked")
    private static <T> void sort(List<T> list, Comparator<T> comparator) {
      T[] array = (T[]) new Object[list.size()];
      int index = 0;
      for (T item : list) {
        array[index++] = item;
      }
      java.util.Arrays.sort(array, comparator);
      list.clear();
      for (T item : array) {
        list.append(item);
      }
    }

    @Test
    @DisplayName("values() iterates over all values")
    void valuesIterator() {
      // Note: values can have duplicates, so we use a List for verification.
      List<String> expectedValueList = JDKArrayList.from(expectedValues);
      List<String> actualValueList = JDKArrayList.empty();
      dict.values().forEach(actualValueList::append);

      // For unordered collections, check if they are permutations of each other.
      sort(expectedValueList, Comparator.naturalOrder());
      sort(actualValueList, Comparator.naturalOrder());
      assertEquals(expectedValueList, actualValueList);
    }
  }

  @Nested
  @DisplayName("The equals() and hashCode() methods")
  class EqualityAndHashCodeTests {
    @Test
    @DisplayName("equals() returns true for two identical hash dictionaries")
    void testEqualsWithIdenticalDictionaries() {
      Dictionary<Integer, String> dict1 = HashDictionary.of(Entry.of(1, "one"), Entry.of(2, "two"));
      Dictionary<Integer, String> dict2 = HashDictionary.of(Entry.of(2, "two"), Entry.of(1, "one")); // Different order
      assertEquals(dict1, dict2);
      assertEquals(dict1.hashCode(), dict2.hashCode());
    }

    @Test
    @DisplayName("equals() returns true for a HashDictionary and a SortedDictionary with the same content")
    void testEqualsWithDifferentImplementations() {
      Dictionary<Integer, String> dict1 = HashDictionary.of(Entry.of(1, "one"), Entry.of(2, "two"));
      Dictionary<Integer, String> dict2 = AVLDictionary.of(Entry.of(2, "two"), Entry.of(1, "one"));
      assertEquals(dict1, dict2);
      assertEquals(dict1.hashCode(), dict2.hashCode());
    }

    @Test
    @DisplayName("equals() returns false for dictionaries with different content")
    void testEqualsWithDifferentContent() {
      Dictionary<Integer, String> dict1 = HashDictionary.of(Entry.of(1, "one"), Entry.of(2, "two"));
      Dictionary<Integer, String> dict2 = HashDictionary.of(Entry.of(1, "one"), Entry.of(3, "three"));
      assertNotEquals(dict1, dict2);
    }
  }
}