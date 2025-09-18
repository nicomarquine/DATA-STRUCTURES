//
// Pepe Gallardo, Data Structures, UMA.
//

#include <assert.h>
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>

#include "LinkedStructure.h"


/// @brief Constructor for a new Node
/// @param element Element to be stored in node
/// @param p_next Pointer to next to be stored in node
/// @return Pointer to new node
static struct Node* Node_new(int element, struct Node* p_next) {
  struct Node* p_node = malloc(sizeof(struct Node));
  assert(p_node != NULL && "Node_new: not enough memory");

  p_node->element = element;
  p_node->p_next = p_next;
  return p_node;
}

/// @brief Destructor for a Node. Frees memory allocated for node and sets pointer to node to NULL
/// @param p_p_node Pointer to pointer to node to be freed 
static void Node_free(struct Node** p_p_node) {
  free(*p_p_node);
  *p_p_node = NULL;
}

struct Node* LinkedStructure_new() {
  return NULL;
}

struct Node* LinkedStructure_copyOf(const struct Node* p_first) {
  // todo
  return NULL;
}

bool LinkedStructure_isEmpty(const struct Node* p_first) {
  // todo
  return false;
}

size_t LinkedStructure_size(const struct Node* p_first) {
  // todo
  return 0;
}

void LinkedStructure_clear(struct Node** p_p_first) {
  // todo
}

void LinkedStructure_prepend(struct Node** p_p_first, int element) {
  // todo
}

void LinkedStructure_append(struct Node** p_p_first, int element) {
  // todo
}

void LinkedStructure_insert(struct Node** p_p_first, size_t index, int element) {
  // todo
}

int LinkedStructure_get(const struct Node* p_first, size_t index) {
  // todo
  return 0;
}

void LinkedStructure_set(struct Node* p_first, size_t index, int element) {
  // todo
}

void LinkedStructure_delete(struct Node** p_p_first, size_t index) {
  // todo
}

void LinkedStructure_print(const struct Node* p_first) {
  printf("LinkedStructure(");
  for (const struct Node* p = p_first; p != NULL; p = p->p_next) {
    printf("%d", p->element);
    if (p->p_next != NULL) {
      printf(", ");
    }
  }
  printf(")");
}

