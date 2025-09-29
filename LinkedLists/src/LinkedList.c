//
// Pepe Gallardo, Data Structures, UMA.
//

#include <assert.h>
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>

#include "LinkedList.h"
#include "test/unit/UnitTest.h"


/// @brief Creates a new Node with given element and pointer to next Node
/// @param element Element to be stored in Node
/// @param p_next Pointer to next Node to be stored in Node
/// @return Pointer to new Node
static struct Node* Node_new(int element, struct Node* p_next) {
  struct Node* p_node = malloc(sizeof(struct Node));
  assert(p_node != NULL && "Node_new: not enough memory");

  p_node->element = element;
  p_node->p_next = p_next;
  return p_node;
}

/// @brief Frees memory allocated for a Node. Must also set pointer to Node to NULL
/// @param p_p_node Pointer to pointer to Node
static void Node_free(struct Node** p_p_node) {
  free(*p_p_node);
  *p_p_node = NULL;
}

/// @brief Validates index in LinkedList. 
/// @param p_list Pointer to LinkedList
/// @param index Index to be validated
static void validateIndex(const struct LinkedList* p_list, size_t index) {
  assert(index < p_list->size && "validateIndex: invalid index");
}

struct LinkedList* LinkedList_new() {
  // todo
  return NULL;
}

struct LinkedList* LinkedList_copyOf(const struct LinkedList* p_list) {
  assert(p_list != NULL && "LinkedList_copyOf: invalid list");
  // todo
  return NULL;
}

bool LinkedList_isEmpty(const struct LinkedList* p_list) {
  assert(p_list != NULL && "LinkedList_isEmpty: invalid list");
  // todo
  return false;
}

size_t LinkedList_size(const struct LinkedList* p_list) {
  assert(p_list != NULL && "LinkedList_size: invalid list");
  // todo
  return 0;
}

void LinkedList_prepend(struct LinkedList* p_list, int element) {
  assert(p_list != NULL && "LinkedList_prepend: invalid list");
  // todo
}

void LinkedList_append(struct LinkedList* p_list, int element) {
  assert(p_list != NULL && "LinkedList_append: invalid list");
  // todo
}

void LinkedList_insert(struct LinkedList* p_list, size_t index, int element) {
  assert(p_list != NULL && "LinkedList_insert: invalid list");
  // todo
}

int LinkedList_get(const struct LinkedList* p_list, size_t index) {
  assert(p_list != NULL && "LinkedList_get: invalid list");
  // todo
  return 0;
}

void LinkedList_set(const struct LinkedList* p_list, size_t index, int element) {
  assert(p_list != NULL && "LinkedList_set: invalid list");
  // todo
}

void LinkedList_delete(struct LinkedList* p_list, size_t index) {
  assert(p_list != NULL && "LinkedList_delete: invalid list");
  // todo
}

void LinkedList_print(const struct LinkedList* p_list) {
  assert(p_list != NULL && "LinkedList_print: invalid list");
  printf("LinkedList(");
  struct Node* p_node = p_list->p_first;
  while (p_node != NULL) {
    printf("%d", p_node->element); // print element
    if (p_node->p_next != NULL) {
      printf(", "); // print separator
    }
    p_node = p_node->p_next;
  }
  printf(")");
}

void LinkedList_clear(struct LinkedList* p_list) {
  assert(p_list != NULL && "LinkedList_clear: invalid list");
   // todo
}

void LinkedList_free(struct LinkedList** p_p_list) {
  assert(p_p_list != NULL && "LinkedList_free: invalid p_p_list");
  // todo
}