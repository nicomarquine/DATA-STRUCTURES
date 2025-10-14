package org.uma.ed.datastructures.hashtable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.uma.ed.datastructures.set.JDKHashSet;
import org.uma.ed.datastructures.set.Set;

import java.lang.reflect.Field;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test cases for class SeparateChainingHashTable")
class SeparateChainingHashTableTest {

  // A simple class with a controllable hash code for predictable collisions.
  static class HashableInteger {
    final int value;
    final int hashCode;

    HashableInteger(int value, int hashCode) { this.value = value; this.hashCode = hashCode; }
    @Override public int hashCode() { return hashCode; }
    @Override public boolean equals(Object obj) {
      return obj instanceof HashableInteger && ((HashableInteger) obj).value == this.value;
    }
    @Override public String toString() { return String.valueOf(value); }
  }

  // ==========================================================
  // BLACK-BOX TESTS (Public API Behavior)
  // ==========================================================

  @Nested
  @DisplayName("A SeparateChainingHashTable is created")
  class CreationTests {
    @Test @DisplayName("by calling the default constructor")
    void defaultConstructor() {
      HashTable<Integer> ht = new SeparateChainingHashTable<>();
      assertNotNull(ht);
      assertTrue(ht.isEmpty());
    }

    @Test @DisplayName("by calling the constructor with capacity")
    void constructorWithCapacity() {
      HashTable<Integer> ht = new SeparateChainingHashTable<>(50, 0.75);
      assertNotNull(ht);
      assertTrue(ht.isEmpty());
    }

    @Test @DisplayName("by calling the empty() factory method")
    void emptyFactory() {
      HashTable<Integer> ht = SeparateChainingHashTable.empty();
      assertTrue(ht.isEmpty());
    }

    @Test @DisplayName("by calling the withCapacity() factory method")
    void withCapacityFactory() {
      HashTable<Integer> ht = SeparateChainingHashTable.withCapacity(100);
      assertTrue(ht.isEmpty());
    }
  }

  @Nested
  @DisplayName("Core operations: insert(), contains(), search(), delete()")
  class CoreOperationsTests {
    private HashTable<Integer> ht;

    @BeforeEach void setup() { ht = SeparateChainingHashTable.empty(); }

    @Test @DisplayName("insert() adds an element and contains() finds it")
    void insertAndContains() {
      assertFalse(ht.contains(10));
      ht.insert(10);
      assertTrue(ht.contains(10));
      assertEquals(1, ht.size());
    }

    @Test @DisplayName("insert() replaces an existing element")
    void insertReplaces() {
      HashTable<HashableInteger> htCustom = SeparateChainingHashTable.empty();
      HashableInteger val1 = new HashableInteger(10, 5);
      HashableInteger val2 = new HashableInteger(10, 5);

      htCustom.insert(val1);
      assertSame(val1, htCustom.search(val1));

      htCustom.insert(val2);
      assertEquals(1, htCustom.size());
      assertSame(val2, htCustom.search(val1));
    }

    @Test @DisplayName("delete() removes an existing element")
    void deleteExisting() {
      ht.insert(10);
      ht.insert(20);
      ht.delete(10);
      assertFalse(ht.contains(10));
      assertTrue(ht.contains(20));
      assertEquals(1, ht.size());
    }

    @Test @DisplayName("delete() does nothing for a non-existing element")
    void deleteNonExisting() {
      ht.insert(10);
      ht.delete(99);
      assertEquals(1, ht.size());
    }

    @Test @DisplayName("clear() empties the table")
    void clearTest() {
      ht.insert(10);
      ht.insert(20);
      ht.clear();
      assertTrue(ht.isEmpty());
      assertEquals(0, ht.size());
    }
  }

  @Nested
  @DisplayName("Iterator behavior")
  class IteratorTests {
    @Test @DisplayName("for an empty table hasNext() is false")
    void iteratorEmpty() {
      HashTable<Integer> ht = SeparateChainingHashTable.empty();
      assertFalse(ht.iterator().hasNext());
      assertThrows(NoSuchElementException.class, () -> ht.iterator().next());
    }

    @Test @DisplayName("iterates over all elements in the table")
    void iteratorAllElements() {
      HashTable<Integer> ht = SeparateChainingHashTable.empty();
      Set<Integer> expectedElements = JDKHashSet.of(10, 20, 30, 40);
      for (int val : expectedElements) {
        ht.insert(val);
      }

      Set<Integer> actualElements = JDKHashSet.empty();
      ht.iterator().forEachRemaining(actualElements::insert);

      assertEquals(expectedElements, actualElements);
    }
  }

  // ==========================================================
  // WHITE-BOX TESTS (Structural Integrity)
  // ==========================================================

  @Nested
  @DisplayName("Structural Integrity Tests (White-Box)")
  class StructuralIntegrityTests {

    // --- Reflection Helper Methods ---
    private Object[] getTableArray(SeparateChainingHashTable<?> ht) throws ReflectiveOperationException {
      Field tableField = SeparateChainingHashTable.class.getDeclaredField("table");
      tableField.setAccessible(true);
      return (Object[]) tableField.get(ht);
    }

    private <T> T getField(Object obj, String fieldName) throws ReflectiveOperationException {
      Field field = obj.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      @SuppressWarnings("unchecked")
      T value = (T) field.get(obj);
      return value;
    }

    /**
     * Validates that every element is in the correct chain according to its hash code.
     */
    private <T> void validateChains(SeparateChainingHashTable<T> ht) throws ReflectiveOperationException {
      Object[] table = getTableArray(ht);
      int capacity = table.length;
      int totalCount = 0;

      for (int i = 0; i < capacity; i++) {
        Object node = table[i];
        while (node != null) {
          totalCount++;
          T key = getField(node, "key");
          int expectedIndex = (key.hashCode() & 0x7fffffff) % capacity;
          assertEquals(i, expectedIndex,
              "Key " + key + " found in chain " + i + ", but its hash code maps to chain " + expectedIndex);
          node = getField(node, "next");
        }
      }
      assertEquals(ht.size(), totalCount, "The number of nodes in all chains does not match the reported size.");
    }

    @Test
    @DisplayName("insert() with collisions correctly builds a chain")
    void insertWithCollisions() throws ReflectiveOperationException {
      SeparateChainingHashTable<HashableInteger> ht = new SeparateChainingHashTable<>(11, 5.0);
      ht.insert(new HashableInteger(0, 0));  // a
      ht.insert(new HashableInteger(11, 0)); // b, collides with a
      ht.insert(new HashableInteger(22, 0)); // c, collides with a, b
      ht.insert(new HashableInteger(1, 1));  // d, in a different chain

      validateChains(ht);
      assertEquals(4, ht.size());

      Object[] table = getTableArray(ht);
      Object chain0 = table[0];
      assertNotNull(chain0);

      // Verify the chain at index 0
      int chainLength = 0;
      Set<Integer> valuesInChain0 = JDKHashSet.empty();
      while (chain0 != null) {
        chainLength++;
        HashableInteger key = getField(chain0, "key");
        valuesInChain0.insert(key.value);
        chain0 = getField(chain0, "next");
      }
      assertEquals(3, chainLength, "Chain at index 0 should have 3 elements.");
      assertTrue(valuesInChain0.contains(0));
      assertTrue(valuesInChain0.contains(11));
      assertTrue(valuesInChain0.contains(22));
    }

    @Test
    @DisplayName("delete() correctly removes elements from a chain")
    void deleteFromChain() throws ReflectiveOperationException {
      SeparateChainingHashTable<HashableInteger> ht = new SeparateChainingHashTable<>(11, 5.0);
      HashableInteger h0 = new HashableInteger(0, 0);
      HashableInteger h11 = new HashableInteger(11, 0);
      HashableInteger h22 = new HashableInteger(22, 0);

      ht.insert(h0);
      ht.insert(h11);
      ht.insert(h22);

      ht.delete(h11); // Delete middle of the chain
      assertEquals(2, ht.size());
      assertTrue(ht.contains(h0));
      assertFalse(ht.contains(h11));
      assertTrue(ht.contains(h22));
      validateChains(ht);

      ht.delete(h0); // Delete head of the chain
      assertEquals(1, ht.size());
      assertFalse(ht.contains(h0));
      assertTrue(ht.contains(h22));
      validateChains(ht);

      ht.delete(h22); // Delete last element
      assertEquals(0, ht.size());
      assertFalse(ht.contains(h22));
      validateChains(ht);
    }

    @Test
    @DisplayName("rehashing should correctly rebuild all chains")
    void rehashingMaintainsInvariants() throws ReflectiveOperationException {
      // Use small table and high load factor to force rehashing
      SeparateChainingHashTable<Integer> ht = new SeparateChainingHashTable<>(5, 1.0);

      for (int i=0; i < 10; i++) {
        ht.insert(i);
      }

      Object[] table = getTableArray(ht);
      assertTrue(table.length > 5, "Table should have rehashed to a larger capacity.");
      assertEquals(10, ht.size());

      for (int i=0; i < 10; i++) {
        assertTrue(ht.contains(i));
      }

      validateChains(ht);
    }
  }
}