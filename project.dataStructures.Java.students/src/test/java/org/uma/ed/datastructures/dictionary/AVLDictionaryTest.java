package org.uma.ed.datastructures.dictionary;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.uma.ed.datastructures.dictionary.Dictionary.Entry;
import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;

@DisplayName("Test cases for class AVLDictionary")
class AVLDictionaryTest {

  @Nested
  @DisplayName("An AVLDictionary is created")
  class TestCasesForCreatingDictionaries {

    @Test
    @DisplayName("by calling the default constructor")
    void whenTheConstructorIsCalledThenAnDictionaryIsCreated() {
      // Arrange
      SortedDictionary<Integer, String> dict = new AVLDictionary<Integer, String>(Comparator.naturalOrder());

      // Assert
      assertNotNull(dict);
      assertTrue(dict.isEmpty());
    }

    @Test
    @DisplayName("by calling the empty() method with a comparator")
    void whenTheEmptyMethodWithComparatorIsCalledThenADictionaryIsCreated() {
      // Arrange
      SortedDictionary<Integer, String> dict = AVLDictionary.<Integer, String>empty(Comparator.naturalOrder());

      // Assert
      assertNotNull(dict);
      assertTrue(dict.isEmpty());
    }

    @Test
    @DisplayName("by calling the empty() method without a comparator (natural order)")
    void whenTheEmptyMethodIsCalledThenADictionaryIsCreated() {
      // Arrange
      SortedDictionary<Integer, String> dict = AVLDictionary.empty();

      // Assert
      assertNotNull(dict);
      assertTrue(dict.isEmpty());
    }

    @Test
    @DisplayName("from a sequence of entries using the of() method")
    void whenTheOfMethodIsCalledThenADictionaryIsCreated() {
      // Arrange
      SortedDictionary<String, Integer> dict = AVLDictionary.of(
          Entry.of("three", 3),
          Entry.of("one", 1),
          Entry.of("two", 2)
      );

      // Assert
      assertNotNull(dict);
      assertEquals(3, dict.size());
      assertEquals(1, dict.valueOf("one"));
    }

    @Test
    @DisplayName("from an iterable of entries using the from() method")
    void givenAnIterableOfEntriesWhenTheFromMethodIsCalledThenADictionaryIsCreated() {
      // Arrange
      List<Entry<Integer, String>> initialEntries = JDKArrayList.of(
          Entry.of(1, "one"),
          Entry.of(2, "two"),
          Entry.of(3, "three")
      );
      SortedDictionary<Integer, String> dict = AVLDictionary.from(initialEntries);

      // Assert
      assertNotNull(dict);
      assertEquals(initialEntries.size(), dict.size());
      assertEquals("two", dict.valueOf(2));
    }

    @Test
    @DisplayName("from an iterable with duplicate keys, keeping the last value")
    void whenFromIsCalledWithDuplicateKeysThenTheLastValueIsKept() {
      // Arrange
      List<Entry<Integer, String>> initialEntries = JDKArrayList.of(
          Entry.of(1, "one"),
          Entry.of(2, "two"),
          Entry.of(1, "uno")
      );
      SortedDictionary<Integer, String> dict = AVLDictionary.from(initialEntries);

      // Assert
      assertEquals(2, dict.size());
      assertEquals("uno", dict.valueOf(1));
    }
  }

  @Nested
  @DisplayName("The isEmpty() and size() methods")
  class TestCasesForIsEmptyAndSize {

    @Test
    @DisplayName("isEmpty() returns true for a new dictionary")
    void givenANewDictionaryThenItIsEmpty() {
      // Arrange
      var dict = AVLDictionary.empty();

      // Assert
      assertTrue(dict.isEmpty());
      assertEquals(0, dict.size());
    }

    @Test
    @DisplayName("isEmpty() returns false after an insertion")
    void givenAnEmptyDictionaryWhenAnElementIsInsertedThenItIsNotEmpty() {
      // Arrange
      var dict = AVLDictionary.<Integer, String>empty();

      // Act
      dict.insert(1, "one");

      // Assert
      assertFalse(dict.isEmpty());
      assertEquals(1, dict.size());
    }
  }

  @Nested
  @DisplayName("The copyOf() method")
  class TestCasesForCopyOf {

    @Test
    @DisplayName("works correctly with an empty AVLDictionary")
    void givenAnEmptyAVLDictionaryWhenCopyOfThenTheNewDictionaryIsAlsoEmpty() {
      // Arrange
      SortedDictionary<Integer, String> dict = AVLDictionary.empty();

      // Act
      SortedDictionary<Integer, String> copiedDict = AVLDictionary.copyOf(dict);

      // Assert
      assertTrue(copiedDict.isEmpty());
      assertEquals(dict, copiedDict);
    }

    @Test
    @DisplayName("works correctly with a non-empty AVLDictionary")
    void givenANonEmptyAVLDictionaryWhenCopyOfThenTheNewDictionaryIsEqual() {
      // Arrange
      SortedDictionary<String, Integer> dict = AVLDictionary.of(
          Entry.of("one", 1), Entry.of("two", 2));

      // Act
      SortedDictionary<String, Integer> copiedDict = AVLDictionary.copyOf(dict);

      // Assert
      assertEquals(dict, copiedDict);
      assertNotSame(dict, copiedDict); // Ensure it's a copy, not the same instance
    }
  }

  @Nested
  @DisplayName("The insert() method")
  class TestCasesForInsert {

    @Test
    @DisplayName("adds a key-value pair to an empty dictionary")
    void whenInsertIntoEmptyDictionaryThenSizeIsOne() {
      // Arrange
      SortedDictionary<Integer, String> dict = AVLDictionary.empty();

      // Act
      dict.insert(1, "one");

      // Assert
      assertEquals(1, dict.size());
      assertEquals("one", dict.valueOf(1));
    }

    @Test
    @DisplayName("updates the value if the key already exists")
    void whenInsertExistingKeyThenValueIsUpdated() {
      // Arrange
      SortedDictionary<Integer, String> dict = AVLDictionary.of(Entry.of(1, "one"));

      // Act
      dict.insert(1, "uno");

      // Assert
      assertEquals(1, dict.size());
      assertEquals("uno", dict.valueOf(1));
    }

    @Test
    @DisplayName("maintains the sorted order of keys")
    void whenInsertElementsThenSortedOrderIsMaintained() {
      // Arrange
      SortedDictionary<Integer, String> dict = AVLDictionary.empty();

      // Act
      dict.insert(3, "three");
      dict.insert(1, "one");
      dict.insert(2, "two");

      // Assert
      assertEquals(1, dict.minimum().key());
      assertEquals(3, dict.maximum().key());
    }
  }

  @Nested
  @DisplayName("The delete() method")
  class TestCasesForDelete {

    @Test
    @DisplayName("does nothing on an empty dictionary")
    void whenDeleteFromEmptyDictionaryThenItRemainsEmpty() {
      // Arrange
      SortedDictionary<Integer, String> dict = AVLDictionary.empty();

      // Act
      dict.delete(1);

      // Assert
      assertTrue(dict.isEmpty());
    }

    @Test
    @DisplayName("does nothing if the key does not exist")
    void whenDeleteNonExistentKeyThenDictionaryIsUnchanged() {
      // Arrange
      SortedDictionary<Integer, String> dict = AVLDictionary.of(Entry.of(1, "one"));
      int initialSize = dict.size();

      // Act
      dict.delete(2);

      // Assert
      assertEquals(initialSize, dict.size());
    }

    @Test
    @DisplayName("removes the key-value pair if the key exists")
    void whenDeleteExistingKeyThenItIsRemoved() {
      // Arrange
      SortedDictionary<Integer, String> dict = AVLDictionary.of(
          Entry.of(1, "one"), Entry.of(2, "two"));

      // Act
      dict.delete(1);

      // Assert
      assertEquals(1, dict.size());
      assertFalse(dict.isDefinedAt(1));
      assertTrue(dict.isDefinedAt(2));
    }
  }

  @Nested
  @DisplayName("The clear() method")
  class TestCasesForClear {
    @Test
    @DisplayName("empties a non-empty dictionary")
    void whenClearIsCalledThenTheDictionaryIsEmpty() {
      // Arrange
      SortedDictionary<Integer, String> dict = AVLDictionary.of(
          Entry.of(1, "one"), Entry.of(2, "two"));

      // Act
      dict.clear();

      // Assert
      assertTrue(dict.isEmpty());
      assertEquals(0, dict.size());
    }
  }

  @Nested
  @DisplayName("Query methods: valueOf() and isDefinedAt()")
  class TestCasesForQueryMethods {

    @Test
    @DisplayName("isDefinedAt() returns true for an existing key and false for a non-existing one")
    void testIsDefinedAt() {
      // Arrange
      SortedDictionary<Integer, String> dict = AVLDictionary.of(Entry.of(1, "one"));

      // Assert
      assertTrue(dict.isDefinedAt(1));
      assertFalse(dict.isDefinedAt(2));
    }

    @Test
    @DisplayName("valueOf() returns the correct value for an existing key")
    void whenValueOfExistingKeyThenReturnsValue() {
      // Arrange
      SortedDictionary<Integer, String> dict = AVLDictionary.of(Entry.of(1, "one"));

      // Assert
      assertEquals("one", dict.valueOf(1));
    }

    @Test
    @DisplayName("valueOf() returns null for a non-existing key")
    void whenValueOfNonExistingKeyThenReturnsNull() {
      // Arrange
      SortedDictionary<Integer, String> dict = AVLDictionary.of(Entry.of(1, "one"));

      // Assert
      assertNull(dict.valueOf(2));
    }
  }

  @Nested
  @DisplayName("Extremal operations: minimum() and maximum()")
  class TestCasesForExtremalOperations {

    @Test
    @DisplayName("minimum() throws exception on an empty dictionary")
    void whenMinimumOnEmptyDictionaryThenThrowsException() {
      // Arrange
      SortedDictionary<Integer, String> dict = AVLDictionary.empty();

      // Assert
      assertThrows(NoSuchElementException.class, dict::minimum);
    }

    @Test
    @DisplayName("maximum() throws exception on an empty dictionary")
    void whenMaximumOnEmptyDictionaryThenThrowsException() {
      // Arrange
      SortedDictionary<Integer, String> dict = AVLDictionary.empty();

      // Assert
      assertThrows(NoSuchElementException.class, dict::maximum);
    }

    @Test
    @DisplayName("minimum() returns the entry with the smallest key")
    void testMinimum() {
      // Arrange
      SortedDictionary<Integer, String> dict = AVLDictionary.of(
          Entry.of(3, "three"), Entry.of(1, "one"), Entry.of(2, "two"));

      // Assert
      assertEquals(Entry.of(1, "one"), dict.minimum());
    }

    @Test
    @DisplayName("maximum() returns the entry with the largest key")
    void testMaximum() {
      // Arrange
      SortedDictionary<Integer, String> dict = AVLDictionary.of(
          Entry.of(3, "three"), Entry.of(1, "one"), Entry.of(2, "two"));

      // Assert
      assertEquals(Entry.of(3, "three"), dict.maximum());
    }
  }

  @Nested
  @DisplayName("Iterables: keys(), values(), entries()")
  class TestCasesForIterables {

    @Test
    @DisplayName("entries() iterates in sorted key order")
    void testEntriesIteratorOrder() {
      // Arrange
      SortedDictionary<Integer, String> dict = AVLDictionary.of(
          Entry.of(3, "three"), Entry.of(1, "one"), Entry.of(2, "two"));

      List<Entry<Integer, String>> expected = JDKArrayList.of(
          Entry.of(1, "one"), Entry.of(2, "two"), Entry.of(3, "three"));

      // Act
      List<Entry<Integer, String>> actual = JDKArrayList.empty();
      dict.entries().forEach(actual::append);

      // Assert
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("keys() iterates in sorted order")
    void testKeysIteratorOrder() {
      // Arrange
      SortedDictionary<Integer, String> dict = AVLDictionary.of(
          Entry.of(3, "three"), Entry.of(1, "one"), Entry.of(2, "two"));

      List<Integer> expected = JDKArrayList.of(1, 2, 3);

      // Act
      List<Integer> actual = JDKArrayList.empty();
      dict.keys().forEach(actual::append);

      // Assert
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("values() iterates in sorted key order")
    void testValuesIteratorOrder() {
      // Arrange
      SortedDictionary<Integer, String> dict = AVLDictionary.of(
          Entry.of(3, "three"), Entry.of(1, "one"), Entry.of(2, "two"));

      List<String> expected = JDKArrayList.of("one", "two", "three");

      // Act
      List<String> actual = JDKArrayList.empty();
      dict.values().forEach(actual::append);

      // Assert
      assertEquals(expected, actual);
    }
  }

  @Nested
  @DisplayName("The equals() and hashCode() methods")
  class TestCasesForEqualsAndHashCode {

    @Test
    @DisplayName("equals() returns true for two identical AVLDictionaries")
    void testEqualsWithIdenticalAVLDictionaries() {
      // Arrange
      SortedDictionary<Integer, String> dict1 = AVLDictionary.of(Entry.of(1, "one"), Entry.of(2, "two"));
      SortedDictionary<Integer, String> dict2 = AVLDictionary.of(Entry.of(1, "one"), Entry.of(2, "two"));

      // Assert
      assertEquals(dict1, dict2);
      assertEquals(dict1.hashCode(), dict2.hashCode());
    }

    @Test
    @DisplayName("equals() returns true for an AVLDictionary and a SortedLinkedDictionary with the same content")
    void testEqualsWithDifferentSortedImplementations() {
      // Arrange
      SortedDictionary<Integer, String> dict1 = AVLDictionary.of(Entry.of(1, "one"), Entry.of(2, "two"));
      SortedDictionary<Integer, String> dict2 = SortedLinkedDictionary.of(Entry.of(1, "one"), Entry.of(2, "two"));

      // Assert
      assertEquals(dict1, dict2);
      assertEquals(dict1.hashCode(), dict2.hashCode());
    }

    @Test
    @DisplayName("equals() returns true for a SortedDictionary and a HashDictionary with the same content")
    void testEqualsWithUnorderedImplementation() {
      // Arrange
      SortedDictionary<Integer, String> dict1 = AVLDictionary.of(Entry.of(2, "two"), Entry.of(1, "one"));
      Dictionary<Integer, String> dict2 = HashDictionary.of(Entry.of(1, "one"), Entry.of(2, "two"));

      // Assert
      assertEquals(dict1, dict2);
      assertEquals(dict1.hashCode(), dict2.hashCode());
    }

    @Test
    @DisplayName("equals() returns false for dictionaries with different sizes")
    void testEqualsWithDifferentSizes() {
      // Arrange
      SortedDictionary<Integer, String> dict1 = AVLDictionary.of(Entry.of(1, "one"));
      SortedDictionary<Integer, String> dict2 = AVLDictionary.of(Entry.of(1, "one"), Entry.of(2, "two"));

      // Assert
      assertNotEquals(dict1, dict2);
    }

    @Test
    @DisplayName("equals() returns false for dictionaries with different content")
    void testEqualsWithDifferentContent() {
      // Arrange
      SortedDictionary<Integer, String> dict1 = AVLDictionary.of(Entry.of(1, "one"), Entry.of(2, "two"));
      SortedDictionary<Integer, String> dict2 = AVLDictionary.of(Entry.of(1, "one"), Entry.of(3, "three"));

      // Assert
      assertNotEquals(dict1, dict2);
    }
  }
}