//
// Pepe Gallardo, Data Structures, UMA.
//

#include <assert.h>
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>

#include "ArrayList.h"
#include "test/unit/UnitTest.h"


// Constructor for a new empty struct ArrayList* with the given initial capacity.
struct ArrayList* ArrayList_new(size_t initialCapacity) {
  assert(initialCapacity > 0 && "ArrayList_new: initialCapacity must be greater than 0");

  struct ArrayList* p_list = malloc(sizeof(struct ArrayList));
  assert(p_list != NULL && "ArrayList_new: not enough memory");

  p_list->elements = malloc(sizeof(int) * initialCapacity);
  assert(p_list->elements != NULL && "ArrayList_new: not enough memory");

  p_list->size = 0;
  p_list->capacity = initialCapacity;
  return p_list;
}

// Destructor for given ArrayList.
void ArrayList_free(struct ArrayList** p_p_list) {
  assert(p_p_list != NULL && "ArrayList_free: p_p_list is NULL");
  struct ArrayList* p_list = *p_p_list;
  assert(p_list != NULL && "ArrayList_free: list is NULL");
  free(p_list->elements); // free array of elements
  free(p_list); // free ArrayList memory
  *p_p_list = NULL; // set p_list to NULL
}

// Constructor for a new struct ArrayList* with same elements as given ArrayList.
struct ArrayList* ArrayList_copyOf(const struct ArrayList* p_list) {
  assert(p_list != NULL && "ArrayList_copyOf: list is NULL");
  struct ArrayList* p_list_copy = ArrayList_new(p_list -> capacity);
  p_list_copy -> size = p_list -> size;
  for (size_t i = 0; i < p_list -> size; i++){
    p_list_copy -> elements[i] = p_list -> elements[i];
  }
  return p_list_copy;
}

// Checks if given ArrayList is empty.
bool ArrayList_isEmpty(const struct ArrayList* p_list) {
  assert(p_list != NULL && "ArrayList_isEmpty: list is NULL");
  return p_list -> size == 0;

}

// Returns the number of elements in given ArrayList.
size_t ArrayList_size(const struct ArrayList* p_list) {
  assert(p_list != NULL && "ArrayList_size: list is NULL");
  return p_list -> size;
}

// Clears given ArrayList making it empty.
void ArrayList_clear(struct ArrayList* p_list) {
  assert(p_list != NULL && "ArrayList_clear: list is NULL");
  p_list -> size = 0;
}

// Ensures that given ArrayList has enough capacity to store one more element.
static void ensureCapacity(struct ArrayList* p_list) {
  if (p_list->size == p_list->capacity) {
    p_list->capacity *= 2;
    p_list->elements = realloc(p_list->elements, sizeof(int) * p_list->capacity);
    assert(p_list->elements != NULL && "ensureCapacity: not enough memory");
  }
}

// Appends given element at the end of given ArrayList.
void ArrayList_append(struct ArrayList* p_list, int element) {
  assert(p_list != NULL && "ArrayList_append: list is NULL");
  ensureCapacity(p_list);
  p_list -> elements[p_list -> size] = element;
  p_list -> size++;
}

// Prepends given element at the beginning of given ArrayList.
void ArrayList_prepend(struct ArrayList* p_list, int element) {
  assert(p_list != NULL && "ArrayList_prepend: list is NULL");
  ensureCapacity(p_list);
  for(size_t i = p_list -> size; i > 0; i--){
    p_list -> elements[i] = p_list -> elements [i - 1]; 
  }
  p_list -> elements[0] = element;
  p_list -> size++;
}

// Validates given index for given ArrayList.
static void validateIndex(const struct ArrayList* p_list, size_t index) {
  // notice that index >= 0 is trivially true because index is unsigned
  assert(index < p_list->size && "ArrayList_validateIndex: index out of bounds");
}

// Inserts given element at given index in given ArrayList.
void ArrayList_insert(struct ArrayList* p_list, size_t index, int element) {
  assert(p_list != NULL && "ArrayList_insert: list is NULL");
  ensureCapacity(p_list);
  if(index != p_list -> size){
    validateIndex(p_list, index);
    for(size_t i = p_list -> size; i > index; i--){
    p_list -> elements[i] = p_list -> elements [i - 1]; 
  }
  }
  p_list -> elements[index] = element;
  p_list -> size++;
}

// Returns the element at given index in given ArrayList.
int ArrayList_get(const struct ArrayList* p_list, size_t index) {
  assert(p_list != NULL && "ArrayList_get: list is NULL");
  validateIndex(p_list, index);
  // todo
  return p_list -> elements[index];
}

// Sets the element at given index in given ArrayList to given element.
void ArrayList_set(const struct ArrayList* p_list, size_t index, int element) {
  assert(p_list != NULL && "ArrayList_set: list is NULL");
  validateIndex(p_list, index);
  p_list -> elements[index] = element;
}

// Deletes the element at given index in given ArrayList.
void ArrayList_delete(struct ArrayList* p_list, size_t index) {
  assert(p_list != NULL && "ArrayList_delete: list is NULL");
  validateIndex(p_list, index);
  for(size_t i = index; i < p_list -> size - 1; i++){
    p_list -> elements[i] = p_list -> elements[i + 1];
  }
  p_list -> size--;
}

// Prints given ArrayList to standard output.
void ArrayList_print(const struct ArrayList* p_list) {
  assert(p_list != NULL && "ArrayList_print: list is NULL");

  printf("ArrayList(");
  for (size_t i = 0; i < p_list->size; i++) {
    printf("%d", p_list->elements[i]);
    if (i < p_list->size - 1) {
      printf(", ");
    }
  }
  printf(")");
}


