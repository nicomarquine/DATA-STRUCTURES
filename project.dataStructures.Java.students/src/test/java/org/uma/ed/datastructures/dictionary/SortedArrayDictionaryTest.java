package org.uma.ed.datastructures.dictionary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.uma.ed.datastructures.dictionary.Dictionary.Entry;
import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;

import java.util.Comparator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test cases for class SortedArrayDictionary")
class SortedArrayDictionaryTest {

  @Nested
  @DisplayName("A SortedArrayDictionary is created")
  class CreationTests {

    @Test
    @DisplayName("by calling the constructor with a comparator")
    void constructorWithComparator() {
      SortedDictionary<Integer, String> dict = new SortedArrayDictionary<Integer, String>(Comparator.naturalOrder());
      assertNotNull(dict);
      assertTrue(dict.isEmpty());
    }

    @Test
    @DisplayName("by calling the empty() method with a comparator")
    void emptyFactoryWithComparator() {
      Comparator<String> reverseOrder = Comparator.reverseOrder();
      SortedDictionary<String, Integer> dict = SortedArrayDictionary.empty(reverseOrder);
      assertNotNull(dict);
      assertTrue(dict.isEmpty());
      assertEquals(reverseOrder, dict.comparator());
    }

    @Test
    @DisplayName("by calling the empty() method without a comparator (natural order)")
    void emptyFactoryNaturalOrder() {
      SortedDictionary<Integer, String> dict = SortedArrayDictionary.empty();
      assertNotNull(dict);
      assertTrue(dict.isEmpty());
      assertEquals(Comparator.naturalOrder(), dict.comparator());
    }

    @Test
    @DisplayName("from a sequence of entries using the of() method")
    void fromOfFactory() {
      SortedDictionary<String, Integer> dict = SortedArrayDictionary.of(
          Comparator.naturalOrder(),
          Entry.of("three", 3),
          Entry.of("one", 1),
          Entry.of("two", 2)
      );
      assertNotNull(dict);
      assertEquals(3, dict.size());
      assertEquals("one", dict.minimum().key());
    }

    @Test
    @DisplayName("from a sequence with duplicate keys using of(), keeping the last value")
    void fromOfFactoryWithDuplicates() {
      SortedDictionary<Integer, String> dict = SortedArrayDictionary.of(
          Entry.of(1, "one"),
          Entry.of(2, "two"),
          Entry.of(1, "uno") // Duplicate key
      );
      assertEquals(2, dict.size());
      assertEquals("uno", dict.valueOf(1));
    }

    @Test
    @DisplayName("from an iterable of entries using the from() method")
    void fromIterableFactory() {
      List<Entry<Integer, String>> initialEntries = new JDKArrayList<>();
      initialEntries.append(Entry.of(10, "ten"));
      initialEntries.append(Entry.of(20, "twenty"));
      initialEntries.append(Entry.of(10, "diez"));

      SortedDictionary<Integer, String> dict = SortedArrayDictionary.from(initialEntries);
      assertNotNull(dict);
      assertEquals(2, dict.size());
      assertEquals("diez", dict.valueOf(10));
    }
  }

  @Nested
  @DisplayName("The copyOf() method")
  class CopyOfTests {
    @Test
    @DisplayName("works correctly with an empty SortedArrayDictionary")
    void copyOfEmpty() {
      SortedArrayDictionary<Integer, String> dict = SortedArrayDictionary.empty();
      SortedArrayDictionary<Integer, String> copiedDict = SortedArrayDictionary.copyOf(dict);
      assertTrue(copiedDict.isEmpty());
      assertEquals(dict, copiedDict);
      assertNotSame(dict, copiedDict);
    }

    @Test
    @DisplayName("works correctly with a non-empty SortedArrayDictionary")
    void copyOfNonEmpty() {
      SortedArrayDictionary<String, Integer> dict = SortedArrayDictionary.of(
          Comparator.naturalOrder(), Entry.of("C", 3), Entry.of("A", 1));
      SortedArrayDictionary<String, Integer> copiedDict = SortedArrayDictionary.copyOf(dict);
      assertEquals(dict, copiedDict);
      assertNotSame(dict, copiedDict);
    }
  }

  @Nested
  @DisplayName("Core dictionary operations")
  class CoreOperationsTests {
    private SortedDictionary<Integer, String> dict;

    @BeforeEach
    void setup() {
      dict = SortedArrayDictionary.empty();
    }

    @Test
    @DisplayName("insert() adds new key-value pairs and maintains order")
    void testInsert() {
      dict.insert(30, "thirty");
      dict.insert(10, "ten");
      dict.insert(20, "twenty");

      assertEquals(3, dict.size());
      assertEquals(10, dict.minimum().key());
      assertEquals(30, dict.maximum().key());
    }

    @Test
    @DisplayName("insert() updates the value for an existing key")
    void testInsertUpdates() {
      dict.insert(10, "ten");
      dict.insert(10, "diez");

      assertEquals(1, dict.size());
      assertEquals("diez", dict.valueOf(10));
    }

    @Test
    @DisplayName("delete() removes an entry")
    void testDelete() {
      dict.insert(10, "ten");
      dict.insert(20, "twenty");
      dict.delete(10);

      assertFalse(dict.isDefinedAt(10));
      assertEquals(1, dict.size());
    }

    @Test
    @DisplayName("isDefinedAt() and valueOf() work correctly")
    void testQueries() {
      dict.insert(10, "ten");
      assertTrue(dict.isDefinedAt(10));
      assertFalse(dict.isDefinedAt(99));
      assertEquals("ten", dict.valueOf(10));
      assertNull(dict.valueOf(99));
    }
  }

  @Nested
  @DisplayName("Extremal operations")
  class ExtremalOperationsTests {
    @Test
    @DisplayName("minimum() and maximum() throw exceptions on an empty dictionary")
    void extremalOnEmpty() {
      SortedDictionary<Integer, String> dict = SortedArrayDictionary.empty();
      assertThrows(NoSuchElementException.class, dict::minimum);
      assertThrows(NoSuchElementException.class, dict::maximum);
    }

    @Test
    @DisplayName("minimum() and maximum() return correct entries")
    void testMinMax() {
      SortedDictionary<Integer, String> dict = SortedArrayDictionary.of(
          Entry.of(30, "thirty"), Entry.of(10, "ten"), Entry.of(20, "twenty")
      );
      assertEquals(Entry.of(10, "ten"), dict.minimum());
      assertEquals(Entry.of(30, "thirty"), dict.maximum());
    }
  }

  @Nested
  @DisplayName("Iterators")
  class IteratorTests {
    private SortedDictionary<Integer, String> dict;

    @BeforeEach
    void setup() {
      dict = SortedArrayDictionary.of(
          Entry.of(3, "three"), Entry.of(1, "one"), Entry.of(2, "two")
      );
    }

    @Test
    @DisplayName("entries() iterates in sorted key order")
    void entriesIteratorOrder() {
      List<Entry<Integer, String>> expected = JDKArrayList.of(
          Entry.of(1, "one"), Entry.of(2, "two"), Entry.of(3, "three"));

      List<Entry<Integer, String>> actual = new JDKArrayList<>();
      dict.entries().forEach(actual::append);

      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("keys() iterates in sorted order")
    void keysIteratorOrder() {
      List<Integer> expected = JDKArrayList.of(1, 2, 3);
      List<Integer> actual = new JDKArrayList<>();
      dict.keys().forEach(actual::append);
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("values() iterates in sorted key order")
    void valuesIteratorOrder() {
      List<String> expected = JDKArrayList.of("one", "two", "three");
      List<String> actual = new JDKArrayList<>();
      dict.values().forEach(actual::append);
      assertEquals(expected, actual);
    }
  }

  @Nested
  @DisplayName("The equals() and hashCode() methods")
  class EqualityAndHashCodeTests {
    @Test
    @DisplayName("equals() returns true for two identical dictionaries")
    void testEqualsWithIdentical() {
      Dictionary<Integer, String> dict1 = SortedArrayDictionary.of(
          Entry.of(1, "one"), Entry.of(2, "two"));
      Dictionary<Integer, String> dict2 = SortedArrayDictionary.of(
          Entry.of(2, "two"), Entry.of(1, "one"));

      assertEquals(dict1, dict2);
      assertEquals(dict1.hashCode(), dict2.hashCode());
    }
  }
}