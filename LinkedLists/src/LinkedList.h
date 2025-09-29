//
// Pepe Gallardo, Data Structures, UMA.
//

#ifndef LINKEDLIST_H // Conditional inclusion
#define LINKEDLIST_H // Avoids multiple inclusion

#include <stdbool.h>
#include <stddef.h>

// Node in a LinkedList
struct Node {
  int element;
  struct Node* p_next;
};

// LinkedList implementation
struct LinkedList {
  struct Node* p_first;  // pointer to first node
  struct Node* p_last;   // pointer to last node
  size_t size;           // number of elements in list  
};

/// @brief Creates a new empty LinkedList. Allocates struct LinkedList and initializes it
/// @return Pointer to new LinkedList
struct LinkedList* LinkedList_new();

/// @brief Frees all memory allocated for a LinkedList (all nodes and struct LinkedList). Must also set pointer to LinkedList to NULL
/// @param p_p_list Pointer to pointer to LinkedList 
void LinkedList_free(struct LinkedList** p_p_list);

/// @brief Creates a new LinkedList as a copy of an existing one
/// @param p_list Pointer to LinkedList to be copied
/// @return Pointer to new LinkedList 
struct LinkedList* LinkedList_copyOf(const struct LinkedList* p_list);

/// @brief Checks if a LinkedList is empty 
/// @param p_list Pointer to LinkedList 
/// @return true if LinkedList is empty, false otherwise 
bool LinkedList_isEmpty(const struct LinkedList* p_list);

/// @brief Returns number of elements in a LinkedList 
/// @param p_list Pointer to LinkedList 
/// @return Number of elements in LinkedList 
size_t LinkedList_size(const struct LinkedList* p_list);

/// @brief Removes all elements from a LinkedList making it empty 
/// @param p_list Pointer to LinkedList 
void LinkedList_clear(struct LinkedList* p_list);

/// @brief Appends an element to the end of a LinkedList 
/// @param p_list Pointer to LinkedList 
/// @param element Element to be appended 
void LinkedList_append(struct LinkedList* p_list, int element);

/// @brief Prepends an element to the beginning of a LinkedList
/// @param p_list Pointer to LinkedList
/// @param element Element to be prepended
void LinkedList_prepend(struct LinkedList* p_list, int element);

/// @brief Inserts an element at a given position in a LinkedList
/// @param p_list Pointer to LinkedList
/// @param index Position where element is to be inserted (0 is before first element, 1 is between first and second elements, etc.)
/// @param element Element to be inserted
void LinkedList_insert(struct LinkedList* p_list, size_t index, int element);

/// @brief Returns element at a given position in a LinkedList
/// @param p_list Pointer to LinkedList
/// @param index Position of element to be returned (0 is first element, 1 is second element, etc.)
/// @return Element at given position
int LinkedList_get(const struct LinkedList* p_list, size_t index);

/// @brief Sets element at a given position in a LinkedList
/// @param p_list Pointer to LinkedList
/// @param index Position of element to be set (0 is first element, 1 is second element, etc.)
/// @param element Element to be set
void LinkedList_set(const struct LinkedList* p_list, size_t index, int element);

/// @brief Deletes element at a given position in a LinkedList
/// @param p_list Pointer to LinkedList
/// @param index Position of element to be deleted (0 is first element, 1 is second element, etc.)
void LinkedList_delete(struct LinkedList* p_list, size_t index);

/// @brief  Prints a all elements in a LinkedList
/// @param p_list Pointer to LinkedList 
void LinkedList_print(const struct LinkedList* p_list);

#endif //LINKEDLIST_H
