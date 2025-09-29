/*============================================================================*/
/* Test Suites for LinkedList                                                 */
/* Pepe Gallardo, 2025                                                        */
/*                                                                            */
/* NOTE ON TESTING STRATEGY:                                                  */
/* This suite is designed to be comprehensive, employing a three-pronged      */
/* approach for each function:                                                */
/* 1. Functional Tests: Verify the logical correctness of each function in    */
/*    strict isolation across various scenarios (empty, single, multi-item,   */
/*    edge cases). UT_disable_leak_check() is used to focus only on logic.    */
/* 2. Memory Tests: Explicitly verify the memory allocation and deallocation  */
/*    behavior, ensuring resources are managed correctly.                     */
/* 3. Assertion Tests: Verify that functions correctly assert on invalid     */
/*    (e.g., NULL) input, ensuring contract enforcement.                      */
/*============================================================================*/

#include "LinkedList.h"
#include "Helpers.h"

#define UNIT_TEST_DECLARATION
#define UNIT_TEST_IMPLEMENTATION
#include "test/unit/UnitTest.h"

/*============================================================================*/
/* TEST HELPERS (Wrappers)                                                    */
/*============================================================================*/

// Creates a test list from an array of integers.
static struct LinkedList* _create_test_list(const int* elements, size_t count) {
    return (struct LinkedList*)_n(elements, count);
}

// Compares two LinkedList structures for equality.
static bool _equalLists(const struct LinkedList* l1, const struct LinkedList* l2) {
    return _c((struct Y*)l1, (struct Y*)l2);
}

// Prints a LinkedList structure to a string buffer for test failure messages.
static void __p(char* buf, size_t size, struct LinkedList* l) {
    _p(buf, size, (struct Y*)l);
}

// Custom assertion macro for comparing LinkedLists.
#define EQUAL_LIST(expected, actual) EQUAL_BY(expected, actual, _equalLists, __p)

/*============================================================================*/
/* TEST SUITE: LinkedList_new                                                 */
/*============================================================================*/
// Functional Test: Verifies that a new list is not NULL and its members are initialized to empty/zero state.
TEST_CASE(LinkedList_new, "Creates an empty non-NULL list") {
    UT_disable_leak_check();
    struct LinkedList* list = LinkedList_new();
    REFUTE_NULL(list);
    ASSERT_NULL(list->p_first);
    ASSERT_NULL(list->p_last);
    EQUAL_INT(0, list->size);
}

// Memory Test: Verifies that creating a new list allocates exactly the memory for the main struct.
TEST_CASE(LinkedList_new, "Allocates the correct amount of memory") {
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        LinkedList_new();
    }, 1, 0, sizeof(struct LinkedList), 0);
}

/*============================================================================*/
/* TEST SUITE: LinkedList_free                                                */
/*============================================================================*/
// Assertion Test: Ensures the function asserts when the pointer to the pointer is NULL.
TEST_ASSERTION_FAILURE(LinkedList_free, "Assertion should fail on NULL p_p_list") {
    LinkedList_free(NULL);
}

// Assertion Test: Ensures the function asserts when the list pointer itself is NULL.
TEST_ASSERTION_FAILURE(LinkedList_free, "Assertion should fail if p_p_list is a pointer to a NULL p_list") {
    struct LinkedList* list = NULL;
    LinkedList_free(&list);
}

// Functional Test: Verifies that freeing an empty list results in a NULL pointer.
TEST_CASE(LinkedList_free, "Frees an empty list and sets pointer to NULL") {
    struct LinkedList* list = _create_test_list(NULL, 0);
    LinkedList_free(&list);
    ASSERT_NULL(list);
}

// Functional Test: Verifies that freeing a non-empty list results in a NULL pointer.
TEST_CASE(LinkedList_free, "Freeing a non-empty list sets pointer to NULL") {
    const int elements[] = {10, 20, 30};
    struct LinkedList* list = _create_test_list(elements, 3);
    LinkedList_free(&list);
    ASSERT_NULL(list);
}

// Memory Test: Verifies that all nodes and the list struct itself are deallocated.
TEST_CASE(LinkedList_free, "Frees all nodes in a 3-node non-empty list and the list struct") {
    const int elements[] = {10, 20, 30};
    struct LinkedList* list = _create_test_list(elements, 3);
    UT_mark_memory_as_baseline();
    size_t expected_bytes_freed = sizeof(struct LinkedList) + (3 * sizeof(struct Node));
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        LinkedList_free(&list);
    }, 0, 4, 0, expected_bytes_freed);
    ASSERT_NULL(list);
}

/*============================================================================*/
/* TEST SUITE: LinkedList_copyOf                                              */
/*============================================================================*/
// Assertion Test: Ensures the function asserts when the source list is NULL.
TEST_ASSERTION_FAILURE(LinkedList_copyOf, "Assertion should fail on NULL list") {
    LinkedList_copyOf(NULL);
}

// Functional Test: Verifies that copying an empty list creates a new, distinct, but also empty list.
TEST_CASE(LinkedList_copyOf, "Copies an empty list") {
    UT_disable_leak_check();
    struct LinkedList* list = _create_test_list(NULL, 0);
    struct LinkedList* copy = LinkedList_copyOf(list);
    NON_EQUAL_POINTER(list, copy);
    EQUAL_POINTER(NULL, copy->p_first);
    EQUAL_POINTER(NULL, copy->p_last);
    EQUAL_INT(0, copy->size);
}

// Functional Test: Verifies a deep copy; the new list and its nodes have different memory addresses.
TEST_CASE(LinkedList_copyOf, "Copies a 3-node non-empty list") {
    UT_disable_leak_check();
    const int elements[] = {10, 20, 30};
    struct LinkedList* list = _create_test_list(elements, 3);
    struct LinkedList* copy = LinkedList_copyOf(list);
    NON_EQUAL_POINTER(list, copy);
    NON_EQUAL_POINTER(list->p_first, copy->p_first);
    NON_EQUAL_POINTER(list->p_last, copy->p_last);
    EQUAL_LIST(list, copy);
    EQUAL_INT(30, copy->p_last->element);
}

// Memory Test: Verifies that a new list struct and all its nodes are allocated.
TEST_CASE(LinkedList_copyOf, "Allocates memory for new list and all nodes when copying a 4-node list") {
    const int elements[] = {10, 20, 30, 40};
    struct LinkedList* list = _create_test_list(elements, 4);
    UT_mark_memory_as_baseline();
    size_t expected_bytes = sizeof(struct LinkedList) + 4 * sizeof(struct Node);
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        LinkedList_copyOf(list);
    }, 5, 0, expected_bytes, 0);
}

/*============================================================================*/
/* TEST SUITE: LinkedList_isEmpty                                             */
/*============================================================================*/
// Assertion Test: Ensures the function asserts when the list is NULL.
TEST_ASSERTION_FAILURE(LinkedList_isEmpty, "Assertion should fail on NULL list") {
    LinkedList_isEmpty(NULL);
}

// Functional Test: Verifies that an empty list is correctly identified as empty.
TEST_CASE(LinkedList_isEmpty, "Returns true for an empty list") {
    struct LinkedList* list = _create_test_list(NULL, 0);
    UT_mark_memory_as_baseline();
    ASSERT(LinkedList_isEmpty(list));
}

// Functional Test: Verifies that a non-empty list is correctly identified as not empty.
TEST_CASE(LinkedList_isEmpty, "Returns false for a non-empty list") {
    const int elements[] = {10};
    struct LinkedList* list = _create_test_list(elements, 1);
    UT_mark_memory_as_baseline();
    REFUTE(LinkedList_isEmpty(list));
}

// Memory Test: Verifies that checking if a list is empty does not allocate or deallocate any memory.
TEST_CASE(LinkedList_isEmpty, "Makes no memory allocations or deallocations") {
    const int elements[] = {10, 20, 30};
    struct LinkedList* list = _create_test_list(elements, 3);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        LinkedList_isEmpty(list);
    }, 0, 0, 0, 0);
}

/*============================================================================*/
/* TEST SUITE: LinkedList_size                                                */
/*============================================================================*/
// Assertion Test: Ensures the function asserts when the list is NULL.
TEST_ASSERTION_FAILURE(LinkedList_size, "Assertion should fail on NULL list") {
    LinkedList_size(NULL);
}

// Functional Test: Verifies that the size of an empty list is 0.
TEST_CASE(LinkedList_size, "Returns 0 for an empty list") {
    struct LinkedList* list = _create_test_list(NULL, 0);
    UT_mark_memory_as_baseline();
    EQUAL_INT(0, LinkedList_size(list));
}

// Functional Test: Verifies that the size of a non-empty list is reported correctly.
TEST_CASE(LinkedList_size, "Returns correct size for a non-empty list with 5 elements") {
    const int elements[] = {10, 20, 30, 40, 50};
    struct LinkedList* list = _create_test_list(elements, 5);
    UT_mark_memory_as_baseline();
    EQUAL_INT(5, LinkedList_size(list));
}

// Memory Test: Verifies that checking the size does not allocate or deallocate any memory.
TEST_CASE(LinkedList_size, "Makes no memory allocations or deallocations") {
    const int elements[] = {10, 20, 30};
    struct LinkedList* list = _create_test_list(elements, 3);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        LinkedList_size(list);
    }, 0, 0, 0, 0);
}

/*============================================================================*/
/* TEST SUITE: LinkedList_clear                                               */
/*============================================================================*/
// Assertion Test: Ensures the function asserts when the list is NULL.
TEST_ASSERTION_FAILURE(LinkedList_clear, "Assertion should fail on NULL list") {
    LinkedList_clear(NULL);
}

// Functional Test: Verifies that a non-empty list becomes empty after clearing.
TEST_CASE(LinkedList_clear, "Clears a non-empty list") {
    UT_disable_leak_check();
    const int elements[] = {10, 20, 30};
    struct LinkedList* list = _create_test_list(elements, 3);
    LinkedList_clear(list);
    EQUAL_POINTER(NULL, list->p_first);
    EQUAL_POINTER(NULL, list->p_last);
    EQUAL_INT(0, list->size);
}

// Functional Test: Verifies that clearing an already empty list doesn't change its state.
TEST_CASE(LinkedList_clear, "Clearing an empty list has no effect") {
    UT_disable_leak_check();
    struct LinkedList* list = _create_test_list(NULL, 0);
    LinkedList_clear(list);
    EQUAL_POINTER(NULL, list->p_first);
    EQUAL_POINTER(NULL, list->p_last);
    EQUAL_INT(0, list->size);
}

// Memory Test: Verifies that clearing deallocates all nodes, but not the list struct itself.
TEST_CASE(LinkedList_clear, "Frees memory for all nodes in a 3-node non-empty list") {
    const int elements[] = {10, 20, 30};
    struct LinkedList* list = _create_test_list(elements, 3);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        LinkedList_clear(list);
    }, 0, 3, 0, 3 * sizeof(struct Node));
}

/*============================================================================*/
/* TEST SUITE: LinkedList_append                                              */
/*============================================================================*/
// Assertion Test: Ensures the function asserts when the list is NULL.
TEST_ASSERTION_FAILURE(LinkedList_append, "Assertion should fail on NULL list") {
    LinkedList_append(NULL, 100);
}

// Functional Test: Verifies appending an element to an empty list works correctly.
TEST_CASE(LinkedList_append, "Appends to an empty list") {
    UT_disable_leak_check();
    struct LinkedList* list = _create_test_list(NULL, 0);
    const int expected_elements[] = {100};
    struct LinkedList* expected = _create_test_list(expected_elements, 1);
    LinkedList_append(list, 100);
    EQUAL_LIST(expected, list);
    EQUAL_INT(100, list->p_last->element);
}

// Functional Test: Verifies appending an element to a non-empty list works correctly.
TEST_CASE(LinkedList_append, "Appends to a non-empty list") {
    UT_disable_leak_check();
    const int initial_elements[] = {10, 20};
    struct LinkedList* list = _create_test_list(initial_elements, 2);
    const int expected_elements[] = {10, 20, 30};
    struct LinkedList* expected = _create_test_list(expected_elements, 3);
    LinkedList_append(list, 30);
    EQUAL_LIST(expected, list);
    EQUAL_INT(30, list->p_last->element);
}

// Memory Test: Verifies memory allocation for one new node when list is empty.
TEST_CASE(LinkedList_append, "Allocates memory for appending a new node to an empty list") {
    struct LinkedList* list = _create_test_list(NULL, 0);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        LinkedList_append(list, 100);
    }, 1, 0, sizeof(struct Node), 0);
}

// Memory Test: Verifies memory allocation for one new node when list is not empty.
TEST_CASE(LinkedList_append, "Allocates memory for appending a new node to an non-empty list") {
    const int initial_elements[] = {10, 20, 30};
    struct LinkedList* list = _create_test_list(initial_elements, 3);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        LinkedList_append(list, 40);
    }, 1, 0, sizeof(struct Node), 0);
}

/*============================================================================*/
/* TEST SUITE: LinkedList_prepend                                             */
/*============================================================================*/
// Assertion Test: Ensures the function asserts when the list is NULL.
TEST_ASSERTION_FAILURE(LinkedList_prepend, "Assertion should fail on NULL list") {
    LinkedList_prepend(NULL, 100);
}

// Functional Test: Verifies prepending an element to an empty list works correctly.
TEST_CASE(LinkedList_prepend, "Prepends to an empty list") {
    UT_disable_leak_check();
    struct LinkedList* list = _create_test_list(NULL, 0);
    const int expected_elements[] = {100};
    struct LinkedList* expected = _create_test_list(expected_elements, 1);
    LinkedList_prepend(list, 100);
    EQUAL_LIST(expected, list);
    EQUAL_INT(100, list->p_first->element);
    EQUAL_INT(100, list->p_last->element);
}

// Functional Test: Verifies prepending an element to a non-empty list works correctly.
TEST_CASE(LinkedList_prepend, "Prepends to a non-empty list") {
    UT_disable_leak_check();
    const int initial_elements[] = {20, 30};
    struct LinkedList* list = _create_test_list(initial_elements, 2);
    const int expected_elements[] = {10, 20, 30};
    struct LinkedList* expected = _create_test_list(expected_elements, 3);
    LinkedList_prepend(list, 10);
    EQUAL_LIST(expected, list);
    EQUAL_INT(30, list->p_last->element);
}

// Memory Test: Verifies memory allocation for one new node when list is empty.
TEST_CASE(LinkedList_prepend, "Allocates memory for prepending a new node to an empty list") {
    struct LinkedList* list = _create_test_list(NULL, 0);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        LinkedList_prepend(list, 10);
    }, 1, 0, sizeof(struct Node), 0);
}

// Memory Test: Verifies memory allocation for one new node when list is not empty.
TEST_CASE(LinkedList_prepend, "Allocates memory for prepending a new node to a non-empty list") {
    const int initial_elements[] = {20, 30};
    struct LinkedList* list = _create_test_list(initial_elements, 2);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        LinkedList_prepend(list, 10);
    }, 1, 0, sizeof(struct Node), 0);
}

/*============================================================================*/
/* TEST SUITE: LinkedList_insert                                              */
/*============================================================================*/
// Assertion Test: Ensures the function asserts when the list is NULL.
TEST_ASSERTION_FAILURE(LinkedList_insert, "Assertion should fail on NULL list") {
    LinkedList_insert(NULL, 0, 100);
}

// Assertion Test: Ensures the function asserts on an out-of-bounds index.
TEST_ASSERTION_FAILURE(LinkedList_insert, "Assertion should fail on invalid index") {
    const int elements[] = {10, 20};
    struct LinkedList* list = _create_test_list(elements, 2);
    LinkedList_insert(list, 3, 100);
}

// Functional Test: Verifies insertion at index 0 behaves like prepend.
TEST_CASE(LinkedList_insert, "Inserts at index 0 (prepend)") {
    UT_disable_leak_check();
    const int initial_elements[] = {20, 30};
    struct LinkedList* list = _create_test_list(initial_elements, 2);
    const int expected_elements[] = {10, 20, 30};
    struct LinkedList* expected = _create_test_list(expected_elements, 3);
    LinkedList_insert(list, 0, 10);
    EQUAL_LIST(expected, list);
    EQUAL_INT(30, list->p_last->element);
}

// Functional Test: Verifies insertion at the end of the list behaves like append.
TEST_CASE(LinkedList_insert, "Inserts at last position (append)") {
    UT_disable_leak_check();
    const int initial_elements[] = {10, 20};
    struct LinkedList* list = _create_test_list(initial_elements, 2);
    const int expected_elements[] = {10, 20, 30};
    struct LinkedList* expected = _create_test_list(expected_elements, 3);
    LinkedList_insert(list, 2, 30);
    EQUAL_LIST(expected, list);
    EQUAL_INT(30, list->p_last->element);
}

// Functional Test: Verifies insertion at a middle position works correctly.
TEST_CASE(LinkedList_insert, "Inserts in the middle") {
    UT_disable_leak_check();
    const int initial_elements[] = {10, 30};
    struct LinkedList* list = _create_test_list(initial_elements, 2);
    const int expected_elements[] = {10, 20, 30};
    struct LinkedList* expected = _create_test_list(expected_elements, 3);
    LinkedList_insert(list, 1, 20);
    EQUAL_LIST(expected, list);
    EQUAL_INT(30, list->p_last->element);
}

// Functional Test: Verifies insertion into an empty list at index 0 works correctly.
TEST_CASE(LinkedList_insert, "Inserts into an empty list at index 0") {
    UT_disable_leak_check();
    struct LinkedList* list = _create_test_list(NULL, 0);
    const int expected_elements[] = {100};
    struct LinkedList* expected = _create_test_list(expected_elements, 1);
    LinkedList_insert(list, 0, 100);
    EQUAL_LIST(expected, list);
    EQUAL_INT(100, list->p_last->element);
}

// Memory Test: Verifies that insertion allocates exactly one new node.
TEST_CASE(LinkedList_insert, "Allocates memory exactly for one new node") {
    const int initial_elements[] = {10, 30};
    struct LinkedList* list = _create_test_list(initial_elements, 2);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        LinkedList_insert(list, 1, 20);
    }, 1, 0, sizeof(struct Node), 0);
}

/*============================================================================*/
/* TEST SUITE: LinkedList_get                                                 */
/*============================================================================*/
// Assertion Test: Ensures the function asserts when the list is NULL.
TEST_ASSERTION_FAILURE(LinkedList_get, "Assertion should fail on NULL list") {
    LinkedList_get(NULL, 0);
}

// Assertion Test: Ensures the function asserts on an out-of-bounds index.
TEST_ASSERTION_FAILURE(LinkedList_get, "Assertion should fail on invalid index") {
    const int elements[] = {10, 20};
    struct LinkedList* list = _create_test_list(elements, 2);
    LinkedList_get(list, 2);
}

// Verifies that the first element (head) of the list is retrieved correctly.
TEST_CASE(LinkedList_get, "Should get the first element") {
    UT_disable_leak_check();
    // Arrange: Create a list with several elements.
    const int elements[] = {10, 20, 30};
    struct LinkedList* list = _create_test_list(elements, 3);
    
    // Act & Assert: Check if the element at index 0 is correct.
    EQUAL_INT(10, LinkedList_get(list, 0));
}

// Verifies that an element from the middle of the list is retrieved correctly.
TEST_CASE(LinkedList_get, "Should get a middle element") {
    UT_disable_leak_check();
    // Arrange: Create a list with several elements.
    const int elements[] = {10, 20, 30};
    struct LinkedList* list = _create_test_list(elements, 3);
    
    // Act & Assert: Check if the element at index 1 is correct.
    EQUAL_INT(20, LinkedList_get(list, 1));
}

// Verifies that the last element (tail) of the list is retrieved correctly.
TEST_CASE(LinkedList_get, "Should get the last element") {
    UT_disable_leak_check();
    // Arrange: Create a list with several elements.
    const int elements[] = {10, 20, 30};
    struct LinkedList* list = _create_test_list(elements, 3);
    
    // Act & Assert: Check if the element at the last valid index is correct.
    EQUAL_INT(30, LinkedList_get(list, 2));
}

// Memory Test: Verifies that getting an element is an in-place operation with no memory changes.
TEST_CASE(LinkedList_get, "Makes no memory allocations or deallocations") {
    const int elements[] = {10, 20, 30};
    struct LinkedList* list = _create_test_list(elements, 3);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        LinkedList_get(list, 1);
    }, 0, 0, 0, 0);
}

/*============================================================================*/
/* TEST SUITE: LinkedList_set                                                 */
/*============================================================================*/
// Assertion Test: Ensures the function asserts when the list is NULL.
TEST_ASSERTION_FAILURE(LinkedList_set, "Assertion should fail on NULL list") {
    LinkedList_set(NULL, 0, 100);
}

// Assertion Test: Ensures the function asserts on an out-of-bounds index.
TEST_ASSERTION_FAILURE(LinkedList_set, "Assertion should fail on invalid index") {
    const int elements[] = {10, 20};
    struct LinkedList* list = _create_test_list(elements, 2);
    LinkedList_set(list, 2, 100);
}

// Verifies that the value of the first element (head) can be updated.
TEST_CASE(LinkedList_set, "Should update the value of the first element") {
    UT_disable_leak_check();
    // Arrange: Create a list with initial values.
    const int initial_elements[] = {10, 20, 30};
    struct LinkedList* list = _create_test_list(initial_elements, 3);

    // Act: Set a new value at the first position.
    LinkedList_set(list, 0, 100);

    // Assert: Verify the list has been updated correctly.
    const int expected_elements[] = {100, 20, 30};
    struct LinkedList* expected_list = _create_test_list(expected_elements, 3);
    EQUAL_LIST(expected_list, list);
}

// Verifies that the value of a middle element can be updated.
TEST_CASE(LinkedList_set, "Should update the value of a middle element") {
    UT_disable_leak_check();
    // Arrange: Create a list with initial values.
    const int initial_elements[] = {10, 20, 30};
    struct LinkedList* list = _create_test_list(initial_elements, 3);

    // Act: Set a new value at a middle position.
    LinkedList_set(list, 1, 200);

    // Assert: Verify the list has been updated correctly.
    const int expected_elements[] = {10, 200, 30};
    struct LinkedList* expected_list = _create_test_list(expected_elements, 3);
    EQUAL_LIST(expected_list, list);
}

// Verifies that the value of the last element (tail) can be updated.
TEST_CASE(LinkedList_set, "Should update the value of the last element") {
    UT_disable_leak_check();
    // Arrange: Create a list with initial values.
    const int initial_elements[] = {10, 20, 30};
    struct LinkedList* list = _create_test_list(initial_elements, 3);

    // Act: Set a new value at the last position.
    LinkedList_set(list, 2, 300);

    // Assert: Verify the list has been updated correctly.
    const int expected_elements[] = {10, 20, 300};
    struct LinkedList* expected_list = _create_test_list(expected_elements, 3);
    EQUAL_LIST(expected_list, list);
    EQUAL_INT(300, list->p_last->element);
}

// Memory Test: Verifies that setting an element is an in-place operation with no memory changes.
TEST_CASE(LinkedList_set, "Makes no memory allocations or deallocations") {
    const int elements[] = {10, 20, 30};
    struct LinkedList* list = _create_test_list(elements, 3);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        LinkedList_set(list, 1, 200);
    }, 0, 0, 0, 0);
}

/*============================================================================*/
/* TEST SUITE: LinkedList_delete                                              */
/*============================================================================*/
// Assertion Test: Ensures the function asserts when the list is NULL.
TEST_ASSERTION_FAILURE(LinkedList_delete, "Assertion should fail on NULL list") {
    LinkedList_delete(NULL, 0);
}

// Assertion Test: Ensures the function asserts on an out-of-bounds index.
TEST_ASSERTION_FAILURE(LinkedList_delete, "Assertion should fail on invalid index") {
    const int elements[] = {10, 20};
    struct LinkedList* list = _create_test_list(elements, 2);
    LinkedList_delete(list, 2);
}

// Functional Test: Verifies deleting the only element results in an empty list.
TEST_CASE(LinkedList_delete, "Deletes the only element in a single-item list") {
    UT_disable_leak_check();
    const int elements[] = {10};
    struct LinkedList* list = _create_test_list(elements, 1);
    LinkedList_delete(list, 0);
    EQUAL_POINTER(NULL, list->p_first);
    EQUAL_POINTER(NULL, list->p_last);
    EQUAL_INT(0, list->size);
}

// Functional Test: Verifies deleting the first element from a multi-element list works correctly.
TEST_CASE(LinkedList_delete, "Deletes the first element") {
    UT_disable_leak_check();
    const int initial[] = {10, 20, 30};
    struct LinkedList* list = _create_test_list(initial, 3);
    const int final[] = {20, 30};
    struct LinkedList* expected = _create_test_list(final, 2);
    LinkedList_delete(list, 0);
    EQUAL_LIST(expected, list);
    EQUAL_INT(30, list->p_last->element);
}

// Functional Test: Verifies deleting the last element from a multi-element list works correctly.
TEST_CASE(LinkedList_delete, "Deletes the last element") {
    UT_disable_leak_check();
    const int initial[] = {10, 20, 30};
    struct LinkedList* list = _create_test_list(initial, 3);
    const int final[] = {10, 20};
    struct LinkedList* expected = _create_test_list(final, 2);
    LinkedList_delete(list, 2);
    EQUAL_LIST(expected, list);
    EQUAL_INT(20, list->p_last->element);
}

// Functional Test: Verifies deleting a middle element works correctly.
TEST_CASE(LinkedList_delete, "Deletes a middle element") {
    UT_disable_leak_check();
    const int initial[] = {10, 20, 30};
    struct LinkedList* list = _create_test_list(initial, 3);
    const int final[] = {10, 30};
    struct LinkedList* expected = _create_test_list(final, 2);
    LinkedList_delete(list, 1);
    EQUAL_LIST(expected, list);
    EQUAL_INT(30, list->p_last->element);
}

// Memory Test: Verifies that deleting an element deallocates exactly one node.
TEST_CASE(LinkedList_delete, "Frees memory for exactly one node") {
    const int elements[] = {10, 20, 30};
    struct LinkedList* list = _create_test_list(elements, 3);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        LinkedList_delete(list, 1);
    }, 0, 1, 0, sizeof(struct Node));
}

/*============================================================================*/
/* TEST SUITE: LinkedList_print                                               */
/*============================================================================*/
// Assertion Test: Ensures the function asserts when the list is NULL.
TEST_ASSERTION_FAILURE(LinkedList_print, "Assertion should fail on NULL list") {
    LinkedList_print(NULL);
}

// Functional Test: Verifies the correct output format for an empty list.
TEST_CASE(LinkedList_print, "Prints an empty list") {
    UT_disable_leak_check();
    struct LinkedList* list = _create_test_list(NULL, 0);
    ASSERT_STDOUT_EQUAL({
        LinkedList_print(list);
    }, "LinkedList()");
}

// Functional Test: Verifies the correct output format for a single-element list.
TEST_CASE(LinkedList_print, "Prints a single item list") {
    UT_disable_leak_check();
    const int elements[] = {100};
    struct LinkedList* list = _create_test_list(elements, 1);
    ASSERT_STDOUT_EQUAL({
        LinkedList_print(list);
    }, "LinkedList(100)");
}

// Functional Test: Verifies the correct output format for a multi-element list.
TEST_CASE(LinkedList_print, "Prints a multiple item list") {
    UT_disable_leak_check();
    const int elements[] = {10, 20, 30};
    struct LinkedList* list = _create_test_list(elements, 3);
    ASSERT_STDOUT_EQUAL({
        LinkedList_print(list);
    }, "LinkedList(10, 20, 30)");
}

// Memory Test: Verifies that printing the list does not allocate or deallocate any memory.
TEST_CASE(LinkedList_print, "Makes no memory allocations or deallocations") {
    const int elements[] = {10, 20, 30};
    struct LinkedList* list = _create_test_list(elements, 3);
    UT_mark_memory_as_baseline();
    SILENT_ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        LinkedList_print(list);
    }, 0, 0, 0, 0);
}

/*============================================================================*/
/* MAIN FUNCTION                                                              */
/*============================================================================*/
int runAllTests(int argc, char* argv[]) {
    return UT_RUN_ALL_TESTS();
}