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

@DisplayName("Test cases for class LinearProbingHashTable")
class LinearProbingHashTableTest {

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
  @DisplayName("A LinearProbingHashTable is created")
  class CreationTests {
    @Test @DisplayName("by calling the default constructor")
    void defaultConstructor() {
      HashTable<Integer> ht = new LinearProbingHashTable<>();
      assertNotNull(ht);
      assertTrue(ht.isEmpty());
    }

    @Test @DisplayName("by calling the constructor with capacity")
    void constructorWithCapacity() {
      HashTable<Integer> ht = new LinearProbingHashTable<>(50, 0.75);
      assertNotNull(ht);
      assertTrue(ht.isEmpty());
    }

    @Test @DisplayName("by calling the empty() factory method")
    void emptyFactory() {
      HashTable<Integer> ht = LinearProbingHashTable.empty();
      assertTrue(ht.isEmpty());
    }

    @Test @DisplayName("by calling the withCapacity() factory method")
    void withCapacityFactory() {
      HashTable<Integer> ht = LinearProbingHashTable.withCapacity(100);
      assertTrue(ht.isEmpty());
    }
  }

  @Nested
  @DisplayName("Core operations: insert(), contains(), search(), delete()")
  class CoreOperationsTests {
    private HashTable<Integer> ht;

    @BeforeEach void setup() { ht = LinearProbingHashTable.empty(); }

    @Test @DisplayName("insert() adds an element and contains() finds it")
    void insertAndContains() {
      assertFalse(ht.contains(10));
      ht.insert(10);
      assertTrue(ht.contains(10));
      assertEquals(1, ht.size());
    }

    @Test @DisplayName("insert() replaces an existing element")
    void insertReplaces() {
      // Using a wrapper to distinguish objects that are equal but not the same instance
      HashTable<HashableInteger> htCustom = LinearProbingHashTable.empty();
      HashableInteger val1 = new HashableInteger(10, 5);
      HashableInteger val2 = new HashableInteger(10, 5);

      htCustom.insert(val1);
      assertSame(val1, htCustom.search(val1));

      htCustom.insert(val2);
      assertEquals(1, htCustom.size());
      assertSame(val2, htCustom.search(val1)); // search by val1 should now return val2
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

    @Test @DisplayName("search() returns the element if found, null otherwise")
    void searchTest() {
      ht.insert(10);
      assertEquals(10, ht.search(10));
      assertNull(ht.search(99));
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
      HashTable<Integer> ht = LinearProbingHashTable.empty();
      assertFalse(ht.iterator().hasNext());
      assertThrows(NoSuchElementException.class, () -> ht.iterator().next());
    }

    @Test @DisplayName("iterates over all elements in the table")
    void iteratorAllElements() {
      HashTable<Integer> ht = LinearProbingHashTable.empty();
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
    private int getSize(LinearProbingHashTable<?> ht) throws ReflectiveOperationException {
      Field sizeField = LinearProbingHashTable.class.getDeclaredField("size");
      sizeField.setAccessible(true);
      return (int) sizeField.get(ht);
    }

    private Object[] getKeysArray(LinearProbingHashTable<?> ht) throws ReflectiveOperationException {
      Field keysField = LinearProbingHashTable.class.getDeclaredField("keys");
      keysField.setAccessible(true);
      return (Object[]) keysField.get(ht);
    }

    /**
     * Validates that every non-null element in the internal array is reachable
     * from its ideal hash position, and that cluster integrity is maintained.
     */
    private <T> void validateClusterIntegrity(LinearProbingHashTable<T> ht) throws ReflectiveOperationException {
      Object[] keys = getKeysArray(ht);
      int capacity = keys.length;

      for (int i = 0; i < capacity; i++) {
        if (keys[i] != null) {
          @SuppressWarnings("unchecked")
          T key = (T) keys[i];

          int hashIndex = (key.hashCode() & 0x7fffffff) % capacity;

          // Probe from the ideal hash position to find the key.
          int probeCount = 0;
          boolean found = false;
          for (int j = 0; j < capacity; j++) {
            int currentIndex = (hashIndex + j) % capacity;
            if (keys[currentIndex] == null) {
              // If we hit a null before finding the key, the cluster is broken.
              fail("Cluster integrity violated. Found null at index " + currentIndex + " before finding key " + key + " (should be at " + i + ")");
            }
            if (keys[currentIndex].equals(key)) {
              found = true;
              break;
            }
            probeCount++;
          }
          assertTrue(found, "Key " + key + " at index " + i + " is unreachable from its hash index " + hashIndex);
        }
      }
    }

    @Test
    @DisplayName("insert() with collisions should maintain cluster integrity")
    void insertWithCollisions() throws ReflectiveOperationException {
      LinearProbingHashTable<HashableInteger> ht = new LinearProbingHashTable<>(11, 0.9); // Small table to force collisions
      ht.insert(new HashableInteger(0, 0)); // a
      ht.insert(new HashableInteger(11, 0)); // b, collides with a
      ht.insert(new HashableInteger(22, 0)); // c, collides with a, b
      ht.insert(new HashableInteger(1, 1));  // d

      validateClusterIntegrity(ht);

      Object[] keys = getKeysArray(ht);
      assertNotNull(keys[0]); // a
      assertNotNull(keys[1]); // d
      assertNotNull(keys[2]); // b (probed)
      assertNotNull(keys[3]); // c (probed)
    }

    @Test
    @DisplayName("delete() should maintain cluster integrity")
    void deleteMaintainsClusterIntegrity() throws ReflectiveOperationException {
      LinearProbingHashTable<HashableInteger> ht = new LinearProbingHashTable<>(11, 0.9);
      HashableInteger h0 = new HashableInteger(0, 0);
      HashableInteger h11 = new HashableInteger(11, 0);
      HashableInteger h22 = new HashableInteger(22, 0);

      ht.insert(h0);
      ht.insert(h11);
      ht.insert(h22);

      // Cluster should be [h0, h11, h22, ...] at indices 0, 1, 2
      assertTrue(ht.contains(h22));

      // Delete the middle element of the cluster
      ht.delete(h11);

      // The key h22 must still be findable
      assertTrue(ht.contains(h22), "Deleting an element broke the cluster, making other elements unreachable.");
      assertFalse(ht.contains(h11));
      assertEquals(2, ht.size());

      // Verify the entire structure
      validateClusterIntegrity(ht);
    }

    @Test
    @DisplayName("rehashing should correctly rebuild the table and maintain invariants")
    void rehashingMaintainsInvariants() throws ReflectiveOperationException {
      // Use a small table and high load factor to force rehashing quickly.
      LinearProbingHashTable<Integer> ht = new LinearProbingHashTable<>(5, 0.5);

      ht.insert(1); // size=1
      ht.insert(2); // size=2
      ht.insert(3); // size=3, load=0.6 -> rehash! new capacity should be 11 after next insertion
      ht.insert(4); // size=4

      Object[] keys = getKeysArray(ht);
      assertEquals(11, keys.length, "Table did not rehash to the expected new prime capacity.");
      assertEquals(4, ht.size());
      assertTrue(ht.contains(1));
      assertTrue(ht.contains(2));
      assertTrue(ht.contains(3));

      validateClusterIntegrity(ht);
    }
  }
}