//
// Pepe Gallardo, Data Structures, UMA.
//

#ifndef ARRAYLIST_H // Conditional inclusion
#define ARRAYLIST_H // Avoids multiple inclusion

#include <stdbool.h>
#include <stddef.h>

// Structure corresponding to an ArrayList
struct ArrayList {
  int* elements;    // heap allocated array of elements
  size_t capacity;  // capacity of array
  size_t size;      // number of elements in array
};


/// @brief Creates a new empty ArrayList. Allocates memory for array and structure and initializes it.
/// @param initialCapacity Initial capacity of array
struct ArrayList* ArrayList_new(size_t initialCapacity);

/// @brief Destroys an ArrayList, freeing memory allocated for array and structure and setting pointer to NULL.
/// @param p_p_list Pointer to pointer to ArrayList
void ArrayList_free(struct ArrayList** p_p_list);

/// @brief Creates a new ArrayList as a copy of another one. Must allocate memory for array and structure and copy data from original.
/// @param p_list Pointer to ArrayList to copy
struct ArrayList* ArrayList_copyOf(const struct ArrayList* p_list);

/// @brief Checks if an ArrayList is empty.
/// @param p_list Pointer to ArrayList
/// @return true if ArrayList is empty, false otherwise
bool ArrayList_isEmpty(const struct ArrayList* p_list);

/// @brief Returns number of elements in an ArrayList.
/// @param p_list Pointer to ArrayList
/// @return Number of elements in ArrayList
size_t ArrayList_size(const struct ArrayList* p_list);

/// @brief Makes an ArrayList empty without freeing memory.
/// @param p_list Pointer to ArrayList
void ArrayList_clear(struct ArrayList* p_list);

/// @brief Appends an element to the end of an ArrayList. Must reallocate memory if necessary.
/// @param p_list Pointer to ArrayList
/// @param element Element to append
void ArrayList_append(struct ArrayList* p_list, int element);

/// @brief Prepends an element to the beginning of an ArrayList. Must reallocate memory if necessary.
/// @param p_list Pointer to ArrayList
/// @param element Element to prepend
void ArrayList_prepend(struct ArrayList* p_list, int element);

/// @brief Inserts an element at a given position in an ArrayList. Must reallocate memory if necessary.
/// @param p_list Pointer to ArrayList
/// @param index Position where to insert element (0 at beginning, 1 after first element, etc.)
/// @param element Element to insert
void ArrayList_insert(struct ArrayList* p_list, size_t index, int element);

/// @brief Returns element at a given position in an ArrayList. Must check that index is valid.
/// @param p_list Pointer to ArrayList
/// @param index Position of element to return (0 first element, 1 second element, etc.)
int ArrayList_get(const struct ArrayList* p_list, size_t index);

/// @brief Sets element at a given position in an ArrayList. Must check that index is valid.
/// @param p_list Pointer to ArrayList
/// @param index Position of element to set (0 first element, 1 second element, etc.)
/// @param element Element to set
void ArrayList_set(const struct ArrayList* p_list, size_t index, int element);

/// @brief Deletes element at a given position in an ArrayList, moving elements to the left to fill gap.
/// @param p_list Pointer to ArrayList
/// @param index Position of element to delete (0 first element, 1 second element, etc.)
void ArrayList_delete(struct ArrayList* p_list, size_t index);

/// @brief Prints an ArrayList to standard output.
/// @param p_list Pointer to ArrayList
void ArrayList_print(const struct ArrayList* p_list);

#endif //ARRAYLIST_H
