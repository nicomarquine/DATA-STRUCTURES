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
  struct LinkedList* p_list = malloc(sizeof(struct LinkedList));
  assert(p_list != NULL && "LinkedList_new: out of memory");
  p_list->p_first = NULL;
  p_list->p_last = NULL;
  p_list->size = 0;
  
  return p_list;
}

struct LinkedList* LinkedList_copyOf(const struct LinkedList* p_list) {
  assert(p_list != NULL && "LinkedList_copyOf: invalid list");
  // todo
  return NULL;
}

bool LinkedList_isEmpty(const struct LinkedList* p_list) {
  assert(p_list != NULL && "LinkedList_isEmpty: invalid list");
  // todo
  return p_list -> size == 0;
}

size_t LinkedList_size(const struct LinkedList* p_list) {
  assert(p_list != NULL && "LinkedList_size: invalid list");
  // todo
  return p_list -> size;
}

void LinkedList_prepend(struct LinkedList* p_list, int element) {
  assert(p_list != NULL && "LinkedList_prepend: invalid list");
  
  struct Node* p_node = Node_new(element, p_list -> p_first);
  p_list -> p_first = p_node;
  if(p_list -> size == 0){
    p_list -> p_last;
  }
  p_list -> size++;
  
}

void LinkedList_append(struct LinkedList* p_list, int element) {
  assert(p_list != NULL && "LinkedList_append: invalid list");
  
  struct Node* p_node = Node_new(element, NULL);

  p_list -> p_last -> p_next = p_node;
  p_list -> p_last = p_node;
  p_list -> size++;
}

void LinkedList_insert(struct LinkedList* p_list, size_t index, int element) {
  assert(p_list != NULL && "LinkedList_insert: invalid list");
  if(index == 0){
    LinkedList_prepend(p_list, element);
  } else if (index == p_list -> size){
    LinkedList_append(p_list, element);
  } else {
    validateIndex(p_list, index);
    struct Node *p_current = p_list -> p_first;
    struct Node *p_previous = NULL;
    for (size_t i = 0; i < index; i++){
      p_previous = p_current;
      p_current = p_current -> p_next;
    }
    struct Node* p_node = Node_new(element, p_current);
    p_previous -> p_next = p_node;
    p_list -> size++;
  }
}

int LinkedList_get(const struct LinkedList* p_list, size_t index) {
  assert(p_list != NULL && "LinkedList_get: invalid list");
  validateIndex(p_list, index);
  if(index == p_list -> size - 1){
    return p_list -> p_last -> element;
  }else{
    struct Node* p_current = p_list -> p_first;
    for (int i; i < index; i++){
      p_current = p_current -> p_next;
    }
    return p_current -> element;
  }

  return 0;
}

void LinkedList_set(const struct LinkedList* p_list, size_t index, int element) {
  assert(p_list != NULL && "LinkedList_set: invalid list");
  // todo
}

void LinkedList_delete(struct LinkedList* p_list, size_t index) {
  assert(p_list != NULL && "LinkedList_delete: invalid list");
  validateIndex(p_list, index);

  struct Node* p_current =p_list -> p_first;
  struct Node* p_previous = NULL;
  
  for (size_t i = 0; i < index; i++){
    p_previous = p_current;
    p_current = p_current -> p_next;
  }

  if(p_previous == NULL){
    p_list -> p_first = p_list -> p_first -> p_next;
    if(p_list -> size == 1){
      p_list -> p_last = NULL; 
    }
  }else if(index == p_list -> size - 1){
    p_previous -> p_next = NULL;
    p_list -> p_last = p_previous;
  } else {
    if(index == p_list -> size - 1){
      p_list -> p_last = p_previous;
    }
    p_previous -> p_next = p_current -> p_next;
  }

  Node_free(&p_current);
  p_list -> size--;
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