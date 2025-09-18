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
  return p_first == NULL;
}

size_t LinkedStructure_size(const struct Node* p_first) {
  // todo
  size_t counter = 0;
  struct Node* p_current = p_first;
  while(p_current != NULL){
    counter++;
    p_current = p_current -> p_next;
  }
  
  return counter;
}

void LinkedStructure_clear(struct Node** p_p_first) {
  // todo
}

void LinkedStructure_prepend(struct Node** p_p_first, int element) {
  // todo
  struct Node* p_node = Node_new(element, *p_p_first);
  *p_p_first = p_node;
}

void LinkedStructure_append(struct Node** p_p_first, int element) {
  // todo
  struct Node* p_node = Node_new(element, NULL);
  if(*p_p_first == NULL){
    *p_p_first = p_node;
  }else{
    struct Node* p_last = *p_p_first;
    while(p_last -> p_next != NULL){
      p_last = p_last -> p_next;
    }
    p_last -> p_next = p_node;
  }
}

void LinkedStructure_insert(struct Node** p_p_first, size_t index, int element) {
  // todo
}

int LinkedStructure_get(const struct Node* p_first, size_t index) {
  // todo
  size_t counter = 0;
  struct Node* p_current = p_first;
  for(size_t i = 0; i < index; i++){
    assert(p_current != NULL && "Invalid index");
    p_current = p_current -> p_next;
  }
  assert(p_current != NULL && "Invalid index");
  return p_current -> element;
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

