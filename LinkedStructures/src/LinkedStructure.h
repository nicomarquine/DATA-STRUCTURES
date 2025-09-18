//
// Pepe Gallardo, Data Structures, UMA.
//

#ifndef LINKEDSTRUCTURE_H // Conditional inclusion
#define LINKEDSTRUCTURE_H // Avoids multiple inclusion

#include <stdbool.h>
#include <stddef.h>

// A Node in the linked structure storing integer elements
struct Node {
  int element;         // Element stored in node   
  struct Node* p_next; // Pointer to next node in linked structure
};


/// @brief Creates a new empty linked structure
/// @return NULL pointer because linked structure is empty
struct Node* LinkedStructure_new();

/// @brief Creates a new linked structure with a copy of the elements in given list
/// @param p_first Pointer to first node in linked structure to be copied
/// @return Pointer to first node in new linked structure
struct Node* LinkedStructure_copyOf(const struct Node* p_first);

/// @brief Checks if linked structure is empty
/// @param p_first Pointer to first node in linked structure 
/// @return true if linked structure is empty, false otherwise 
bool LinkedStructure_isEmpty(const struct Node* p_first);

/// @brief Computes the number of elements in linked structure
/// @param p_first Pointer to first node in linked structure 
/// @return Number of elements in linked structure 
size_t LinkedStructure_size(const struct Node* p_first);

/// @brief Frees all nodes in linked structure and sets pointer pointed by p_p_first to NULL
/// @param p_p_first Pointer to pointer to first node in linked structure  
void LinkedStructure_clear(struct Node** p_p_first);

/// @brief Appends a new element at the end of linked structure. Modifies pointer to first node if needed
/// @param p_p_first Pointer to pointer to first node in linked structure 
/// @param element Element to be appended 
void LinkedStructure_append(struct Node** p_p_first, int element);

/// @brief Prepends a new element at the beginning of linked structure. Modifies pointer to first node if needed
/// @param p_p_first Pointer to pointer to first node in linked structure 
/// @param element Element to be prepended 
void LinkedStructure_prepend(struct Node** p_p_first, int element);

/// @brief Inserts a new element at given index in linked structure (0 is before first element, 1 is after first element, etc.). Modifies pointer to first node if needed 
/// @param p_p_first Pointer to pointer to first node in linked structure 
/// @param index Index where new element is to be inserted 
/// @param element Element to be inserted 
void LinkedStructure_insert(struct Node** p_p_first, size_t index, int element);

/// @brief Gets element at given index in linked structure (0 is first element, 1 is second element, etc.) 
/// @param p_first Pointer to first node in linked structure 
/// @param index Index of element to be retrieved 
/// @return 
int LinkedStructure_get(const struct Node* p_first, size_t index);

/// @brief Sets element at given index in linked structure (0 is first element, 1 is second element, etc.) 
/// @param p_first Pointer to first node in linked structure  
/// @param index Index of element to be set 
/// @param element Element to be set 
void LinkedStructure_set(struct Node* p_first, size_t index, int element);

/// @brief Deletes element at given index in linked structure (0 is first element, 1 is second element, etc.). Modifies pointer to first node if needed 
/// @param p_p_first Pointer to pointer to first node in linked structure 
/// @param index Index of element to be deleted 
void LinkedStructure_delete(struct Node** p_p_first, size_t index);

/// @brief Prints all elements in linked structure to standard output 
/// @param p_first Pointer to first node in linked structure 
void LinkedStructure_print(const struct Node* p_first);

#endif //LINKEDSTRUCTURE_H
