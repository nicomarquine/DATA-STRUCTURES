/*============================================================================*/
/* Test Suites for ArrayList                                                  */
/* Pepe Gallardo, 2025                                                        */
/*                                                                            */
/* NOTE ON TESTING STRATEGY:                                                  */
/* This suite is designed to be comprehensive, employing a three-pronged      */
/* approach for each function:                                                */
/* 1. Functional Tests: Verify the logical correctness of each function in    */
/*    strict isolation across various scenarios (empty, single, multi-item,   */
/*    edge cases like re-allocation).                                         */
/* 2. Memory Tests: Explicitly verify the memory allocation and deallocation  */
/*    behavior, ensuring resources are managed correctly.                     */
/* 3. Assertion Tests: Verify that functions correctly assert on invalid     */
/*    (e.g., NULL pointers, invalid indices) input.                           */
/*============================================================================*/

#include "ArrayList.h"
#include "Helpers.h"

#define UNIT_TEST_DECLARATION
#define UNIT_TEST_IMPLEMENTATION
#include "test/unit/UnitTest.h"

/*============================================================================*/
/* TEST HELPERS (Wrappers)                                                    */
/*============================================================================*/

// Creates a test list from an array of integers, with a specific capacity.
static struct ArrayList* _create_test_list(const int* elements, size_t count, size_t capacity) {
    return (struct ArrayList*)_n(elements, count, capacity);
}

// Compares two ArrayList structures for equality.
static bool _equalLists(const struct ArrayList* l1, const struct ArrayList* l2) {
    return _c((struct Y*)l1, (struct Y*)l2);
}

// Prints an ArrayList structure to a string buffer for test failure messages.
static void __p(char* buf, size_t size, struct ArrayList* l) {
    _p(buf, size, (struct Y*)l);
}

// Custom assertion macro for comparing ArrayLists.
#define EQUAL_LIST(expected, actual) EQUAL_BY(expected, actual, _equalLists, __p)

/*============================================================================*/
/* TEST SUITE: ArrayList_new                                                  */
/*============================================================================*/
// Assertion Test: Ensures the function asserts when initial capacity is zero.
TEST_ASSERTION_FAILURE(ArrayList_new, "Assertion should fail on zero initial capacity") {
    ArrayList_new(0);
}

// Functional Test: Verifies that a new list has the correct initial state (size 0, correct capacity).
TEST_CASE(ArrayList_new, "Creates an empty list with specified capacity") {
    UT_disable_leak_check();
    struct ArrayList* list = ArrayList_new(10);
    REFUTE_NULL(list);
    REFUTE_NULL(list->elements);
    EQUAL_INT(0, list->size);
    EQUAL_INT(10, list->capacity);
}

// Memory Test: Verifies correct allocation for the struct and the internal elements array.
TEST_CASE(ArrayList_new, "Allocates correct memory for struct and elements array") {
    size_t capacity = 8;
    size_t expected_bytes = sizeof(struct ArrayList) + sizeof(int) * capacity;
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        ArrayList_new(capacity);
    }, 2, 0, expected_bytes, 0);
}

/*============================================================================*/
/* TEST SUITE: ArrayList_free                                                 */
/*============================================================================*/
// Assertion Test: Ensures the function asserts on a NULL pointer-to-pointer.
TEST_ASSERTION_FAILURE(ArrayList_free, "Assertion should fail on NULL p_p_list") {
    ArrayList_free(NULL);
}

// Assertion Test: Ensures the function asserts on a pointer to a NULL list.
TEST_ASSERTION_FAILURE(ArrayList_free, "Assertion should fail on pointer to NULL list") {
    struct ArrayList* list = NULL;
    ArrayList_free(&list);
}

// Memory Test: Verifies deallocation of the struct and the internal array for a non-empty list.
TEST_CASE(ArrayList_free, "Frees the struct and the elements array") {
    size_t capacity = 5;
    const int elements[] = {1, 2, 3};
    struct ArrayList* list = _create_test_list(elements, 3, capacity);
    UT_mark_memory_as_baseline();
    size_t expected_bytes_freed = sizeof(struct ArrayList) + sizeof(int) * capacity;
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        ArrayList_free(&list);
    }, 0, 2, 0, expected_bytes_freed);
    ASSERT_NULL(list);
}

/*============================================================================*/
/* TEST SUITE: ArrayList_copyOf                                               */
/*============================================================================*/
// Assertion Test: Ensures the function asserts when the source list is NULL.
TEST_ASSERTION_FAILURE(ArrayList_copyOf, "Assertion should fail on NULL list") {
    ArrayList_copyOf(NULL);
}

// Functional Test: Verifies a deep copy is made with distinct memory addresses but identical content.
TEST_CASE(ArrayList_copyOf, "Copies a non-empty list") {
    UT_disable_leak_check();
    const int elements[] = {10, 20, 30};
    struct ArrayList* list = _create_test_list(elements, 3, 5);
    struct ArrayList* copy = ArrayList_copyOf(list);
    
    NON_EQUAL_POINTER(list, copy);
    NON_EQUAL_POINTER(list->elements, copy->elements);
    EQUAL_LIST(list, copy);
}

// Memory Test: Verifies correct memory allocation for the new struct and its internal array.
TEST_CASE(ArrayList_copyOf, "Allocates memory for the new struct and elements array") {
    const int elements[] = {10, 20};
    struct ArrayList* list = _create_test_list(elements, 2, 4);
    UT_mark_memory_as_baseline();
    size_t expected_bytes = sizeof(struct ArrayList) + sizeof(int) * 4;
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        ArrayList_copyOf(list);
    }, 2, 0, expected_bytes, 0);
}

/*============================================================================*/
/* TEST SUITE: ArrayList_isEmpty                                              */
/*============================================================================*/
// Assertion Test: Ensures the function asserts when the list pointer is NULL.
TEST_ASSERTION_FAILURE(ArrayList_isEmpty, "Assertion should fail on NULL list") {
    ArrayList_isEmpty(NULL);
}

// Functional Test: Verifies that a newly created list is correctly identified as empty.
TEST_CASE(ArrayList_isEmpty, "Returns true for a new empty list") {
    struct ArrayList* list = _create_test_list(NULL, 0, 5);
    UT_mark_memory_as_baseline();
    ASSERT(ArrayList_isEmpty(list));
}

// Functional Test: Verifies that a list with elements is correctly identified as not empty.
TEST_CASE(ArrayList_isEmpty, "Returns false for a non-empty list") {
    const int elements[] = {10, 20};
    struct ArrayList* list = _create_test_list(elements, 2, 5);
    UT_mark_memory_as_baseline();
    REFUTE(ArrayList_isEmpty(list));
}

// Memory Test: Verifies that checking if a list is empty is a read-only operation with no memory changes.
TEST_CASE(ArrayList_isEmpty, "Checking if a list is empty does not change memory usage") {
    struct ArrayList* list = _create_test_list(NULL, 0, 5);
    UT_mark_memory_as_baseline();
    ASSERT(ArrayList_isEmpty(list));
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        ArrayList_isEmpty(list);
    }, 0, 0, 0, 0);
}

/*============================================================================*/
/* TEST SUITE: ArrayList_size                                                 */
/*============================================================================*/
// Assertion Test: Ensures the function asserts when the list pointer is NULL.
TEST_ASSERTION_FAILURE(ArrayList_size, "Assertion should fail on NULL list") {
    ArrayList_size(NULL);
}

// Functional Test: Verifies that the size of a new list is correctly reported as 0.
TEST_CASE(ArrayList_size, "Returns 0 for a new empty list") {
    UT_disable_leak_check();
    struct ArrayList* list = _create_test_list(NULL, 0, 10);
    EQUAL_INT(0, ArrayList_size(list));
}

// Functional Test: Verifies that the size is correctly reported for a list with multiple elements.
TEST_CASE(ArrayList_size, "Returns correct size for a non-empty list with 5 elements") {
    UT_disable_leak_check();
    const int elements[] = {10, 20, 30, 40, 50};
    struct ArrayList* list = _create_test_list(elements, 5, 10);
    EQUAL_INT(5, ArrayList_size(list));
}

// Memory Test: Verifies that checking the size is a read-only operation with no memory changes.
TEST_CASE(ArrayList_size, "Getting the size does not change memory usage") {
    const int elements[] = {10, 20, 30};
    struct ArrayList* list = _create_test_list(elements, 3, 5);
    UT_mark_memory_as_baseline();
    EQUAL_INT(3, ArrayList_size(list));
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        ArrayList_size(list);
    }, 0, 0, 0, 0);
}

/*============================================================================*/
/* TEST SUITE: ArrayList_clear                                                */
/*============================================================================*/
// Assertion Test: Ensures the function asserts when the list pointer is NULL.
TEST_ASSERTION_FAILURE(ArrayList_clear, "Assertion should fail on NULL list") {
    ArrayList_clear(NULL);
}

// Functional Test: Verifies that clearing a non-empty list resets its size to 0 but preserves capacity.
TEST_CASE(ArrayList_clear, "Clears a non-empty list") {
    UT_disable_leak_check();
    const int elements[] = {10, 20, 30};
    struct ArrayList* list = _create_test_list(elements, 3, 5);
    
    ArrayList_clear(list);

    struct ArrayList* expected = _create_test_list(NULL, 0, 5);
    EQUAL_LIST(expected, list);
    EQUAL_INT(0, list->size);
    EQUAL_INT(5, list->capacity); // Capacity should be unchanged.
}

// Functional Test: Verifies that clearing an already empty list has no effect on its state.
TEST_CASE(ArrayList_clear, "Clearing an already empty list has no effect") {
    UT_disable_leak_check();
    struct ArrayList* list = _create_test_list(NULL, 0, 4);
    struct ArrayList* expected = _create_test_list(NULL, 0, 4);

    ArrayList_clear(list);

    EQUAL_LIST(expected, list);
}

// Memory Test: Verifies that clear is an in-place operation that does not allocate or deallocate memory.
TEST_CASE(ArrayList_clear, "Makes no memory allocations or deallocations") {
    const int elements[] = {10, 20};
    struct ArrayList* list = _create_test_list(elements, 2, 5);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        ArrayList_clear(list);
    }, 0, 0, 0, 0);
}

/*============================================================================*/
/* TEST SUITE: ArrayList_append                                               */
/*============================================================================*/
// Assertion Test: Ensures the function asserts on a NULL list.
TEST_ASSERTION_FAILURE(ArrayList_append, "Assertion should fail on NULL list") {
    ArrayList_append(NULL, 10);
}

// Functional Test: Verifies appending without triggering a reallocation.
TEST_CASE(ArrayList_append, "Appends an element to an empty list without reallocation") {
    UT_disable_leak_check();
    struct ArrayList* list = _create_test_list(NULL, 0, 2);
    ArrayList_append(list, 10);
    ArrayList_append(list, 20);

    const int expected_elements[] = {10, 20};
    struct ArrayList* expected = _create_test_list(expected_elements, 2, 2);
    EQUAL_LIST(expected, list);
}

// Functional Test: Verifies appending without triggering a reallocation.
TEST_CASE(ArrayList_append, "Appends an element to a non-empty list without reallocation") {
    UT_disable_leak_check();
    const int initial[] = {10, 20};
    struct ArrayList* list = _create_test_list(initial, 2, 3);
    ArrayList_append(list, 30);

    const int expected_elements[] = {10, 20, 30};
    struct ArrayList* expected = _create_test_list(expected_elements, 3, 3);
    EQUAL_LIST(expected, list);
}


// Functional Test: Verifies appending that triggers a reallocation and capacity increase.
TEST_CASE(ArrayList_append, "Appends an element to a non-empty list with reallocation") {
    UT_disable_leak_check();
    const int initial[] = {10, 20, 30};
    struct ArrayList* list = _create_test_list(initial, 3, 3);
    ArrayList_append(list, 40); // Triggers realloc

    const int expected_elements[] = {10, 20, 30, 40};
    struct ArrayList* expected = _create_test_list(expected_elements, 4, 6);
    EQUAL_LIST(expected, list);
}

// Memory Test: Verifies that realloc is triggered, changing memory allocation correctly.
TEST_CASE(ArrayList_append, "Reallocates memory when capacity is reached") {
    const int initial[] = {10, 20};
    struct ArrayList* list = _create_test_list(initial, 2, 2);
    UT_mark_memory_as_baseline();

    // realloc frees old block (sizeof(int)*2) and allocates new one (sizeof(int)*4)
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        ArrayList_append(list, 20);
    }, 0, 0, 2*sizeof(int), 0); // Net change in allocated bytes
}

// Memory Test: Verifies that realloc is not triggered, not changing memory allocation correctly.
TEST_CASE(ArrayList_append, "Does not reallocate memory when capacity is sufficient") {
    const int initial[] = {10, 20};
    struct ArrayList* list = _create_test_list(initial, 2, 4);
    UT_mark_memory_as_baseline();

    // No realloc should occur
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        ArrayList_append(list, 30);
    }, 0, 0, 0, 0); // Net change in allocated bytes
}

/*============================================================================*/
/* TEST SUITE: ArrayList_prepend                                              */
/*============================================================================*/
// Assertion Test: Ensures the function asserts on a NULL list.
TEST_ASSERTION_FAILURE(ArrayList_prepend, "Assertion should fail on NULL list") {
    ArrayList_prepend(NULL, 10);
}

// Functional Test: Verifies prepending without triggering a reallocation.
TEST_CASE(ArrayList_prepend, "Prepends an element to a non-empty list without reallocation") {
    UT_disable_leak_check();
    const int initial[] = {20, 30};
    struct ArrayList* list = _create_test_list(initial, 2, 3);
    ArrayList_prepend(list, 10);

    const int expected_elements[] = {10, 20, 30};
    struct ArrayList* expected = _create_test_list(expected_elements, 3, 3);
    EQUAL_LIST(expected, list);
}

// Functional Test: Verifies prepending without triggering a reallocation.
TEST_CASE(ArrayList_prepend, "Prepends an element to an empty list without reallocation") {
    UT_disable_leak_check();
    struct ArrayList* list = _create_test_list(NULL, 0, 2);
    ArrayList_prepend(list, 10);

    const int expected_elements[] = {10};
    struct ArrayList* expected = _create_test_list(expected_elements, 1, 2);
    EQUAL_LIST(expected, list);
}

// Functional Test: Verifies prepending that triggers a reallocation.
TEST_CASE(ArrayList_prepend, "Prepends an element to a non-empty list with reallocation") {
    UT_disable_leak_check();
    const int initial[] = {20, 30};
    struct ArrayList* list = _create_test_list(initial, 2, 2);
    ArrayList_prepend(list, 10);

    const int expected_elements[] = {10, 20, 30};
    struct ArrayList* expected = _create_test_list(expected_elements, 3, 4);
    EQUAL_LIST(expected, list);
}

// Functional Test: Verifies prepending that triggers a reallocation.
TEST_CASE(ArrayList_prepend, "Prepends an element to an empty list with reallocation") {
    UT_disable_leak_check();
    struct ArrayList* list = _create_test_list(NULL, 0, 1);
    ArrayList_prepend(list, 10);
    ArrayList_prepend(list, 5); // Triggers realloc here
    const int expected_elements[] = {5, 10};
    struct ArrayList* expected = _create_test_list(expected_elements, 2, 2);
    EQUAL_LIST(expected, list);
}

// Memory Test: Verifies that realloc is triggered, changing memory allocation correctly.
TEST_CASE(ArrayList_prepend, "Reallocates memory when capacity is reached") {
    const int initial[] = {10, 20};
    struct ArrayList* list = _create_test_list(initial, 2, 2);
    UT_mark_memory_as_baseline();

    // realloc frees old block (sizeof(int)*2) and allocates new one (sizeof(int)*4)
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        ArrayList_prepend(list, 20);
    }, 0, 0, 2*sizeof(int), 0); // Net change in allocated bytes
}

// Memory Test: Verifies that realloc is not triggered, not changing memory allocation correctly.
TEST_CASE(ArrayList_prepend, "Does not reallocate memory when capacity is sufficient") {
    const int initial[] = {10, 20};
    struct ArrayList* list = _create_test_list(initial, 2, 4);
    UT_mark_memory_as_baseline();

    // No realloc should occur
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        ArrayList_prepend(list, 30);
    }, 0, 0, 0, 0); // Net change in allocated bytes
}

/*============================================================================*/
/* TEST SUITE: ArrayList_insert                                               */
/*============================================================================*/
// Assertion Test: Ensures assertion on out-of-bounds index (greater than size).
TEST_ASSERTION_FAILURE(ArrayList_insert, "Assertion should fail on invalid index") {
    struct ArrayList* list = _create_test_list(NULL, 0, 2);
    ArrayList_insert(list, 1, 10);
}

// Functional Test: Verifies that inserting at index 0 correctly shifts existing elements.
TEST_CASE(ArrayList_insert, "Inserts at the beginning of the list") {
    UT_disable_leak_check();
    const int initial[] = {10, 20};
    struct ArrayList* list = _create_test_list(initial, 2, 3);
    
    ArrayList_insert(list, 0, 5);

    const int expected_elements[] = {5, 10, 20};
    struct ArrayList* expected = _create_test_list(expected_elements, 3, 3);
    EQUAL_LIST(expected, list);
}

// Functional Test: Verifies that inserting between existing elements works correctly.
TEST_CASE(ArrayList_insert, "Inserts in the middle of the list") {
    UT_disable_leak_check();
    const int initial[] = {10, 30};
    struct ArrayList* list = _create_test_list(initial, 2, 3);
    
    ArrayList_insert(list, 1, 20);

    const int expected_elements[] = {10, 20, 30};
    struct ArrayList* expected = _create_test_list(expected_elements, 3, 3);
    EQUAL_LIST(expected, list);
}

// Functional Test: Verifies that inserting at an index equal to the size behaves like append.
TEST_CASE(ArrayList_insert, "Inserts at the end of the list") {
    UT_disable_leak_check();
    const int initial[] = {10, 20};
    struct ArrayList* list = _create_test_list(initial, 2, 3);

    ArrayList_insert(list, 2, 30); // Insert at index equal to current size

    const int expected_elements[] = {10, 20, 30};
    struct ArrayList* expected = _create_test_list(expected_elements, 3, 3);
    EQUAL_LIST(expected, list);
}

// Functional Test: Verifies insertion that triggers a reallocation.
TEST_CASE(ArrayList_insert, "Inserts with reallocation") {
    UT_disable_leak_check();
    const int initial[] = {10, 30};
    struct ArrayList* list = _create_test_list(initial, 2, 2);
    ArrayList_insert(list, 1, 20); // Triggers realloc

    const int expected_elements[] = {10, 20, 30};
    struct ArrayList* expected = _create_test_list(expected_elements, 3, 4);
    EQUAL_LIST(expected, list);
}

// Memory Test: Verifies that realloc is triggered, changing memory allocation correctly.
TEST_CASE(ArrayList_insert, "Reallocates memory when capacity is reached") {
    const int initial[] = {10, 30};
    struct ArrayList* list = _create_test_list(initial, 2, 2);
    UT_mark_memory_as_baseline();

    // realloc frees old block (sizeof(int)*2) and allocates new one (sizeof(int)*4)
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        ArrayList_insert(list, 1, 20);
    }, 0, 0, 2*sizeof(int), 0); // Net change in allocated bytes
}

// Memory Test: Verifies that realloc is not triggered, not changing memory allocation correctly.
TEST_CASE(ArrayList_insert, "Does not reallocate memory when capacity is sufficient") {
    const int initial[] = {10, 30};
    struct ArrayList* list = _create_test_list(initial, 2, 4);
    UT_mark_memory_as_baseline();

    // No realloc should occur
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        ArrayList_insert(list, 1, 20);
    }, 0, 0, 0, 0); // Net change in allocated bytes
}

/*============================================================================*/
/* TEST SUITE: ArrayList_get                                                  */
/*============================================================================*/
// Assertion Test: Ensures the function asserts when the list pointer is NULL.
TEST_ASSERTION_FAILURE(ArrayList_get, "Assertion should fail on NULL list") {
    ArrayList_get(NULL, 0);
}

// Assertion Test: Ensures get asserts on an index equal to the size (out of bounds).
TEST_ASSERTION_FAILURE(ArrayList_get, "Assertion should fail on index equal to size") {
    const int elements[] = {10, 20};
    struct ArrayList* list = _create_test_list(elements, 2, 2);
    ArrayList_get(list, 2);
}

// Assertion Test: Ensures get asserts on an index greater than the size (out of bounds).
TEST_ASSERTION_FAILURE(ArrayList_get, "Assertion should fail on index greater than size") {
    const int elements[] = {10, 20};
    struct ArrayList* list = _create_test_list(elements, 2, 2);
    ArrayList_get(list, 5);
}

// Functional Test: Verifies that the element at the first position (index 0) is retrieved correctly.
TEST_CASE(ArrayList_get, "Gets the first element") {
    UT_disable_leak_check();
    const int initial[] = {10, 20, 30, 40, 50};
    struct ArrayList* list = _create_test_list(initial, 5, 5);
    
    EQUAL_INT(10, ArrayList_get(list, 0));
}

// Functional Test: Verifies that an element from the middle of the list is retrieved correctly.
TEST_CASE(ArrayList_get, "Gets a middle element") {
    UT_disable_leak_check();
    const int initial[] = {10, 20, 30, 40, 50};
    struct ArrayList* list = _create_test_list(initial, 5, 5);
    
    EQUAL_INT(30, ArrayList_get(list, 2));
}

// Functional Test: Verifies that the element at the last valid position (size - 1) is retrieved correctly.
TEST_CASE(ArrayList_get, "Gets the last element") {
    UT_disable_leak_check();
    const int initial[] = {10, 20, 30, 40, 50};
    struct ArrayList* list = _create_test_list(initial, 5, 5);
    
    EQUAL_INT(50, ArrayList_get(list, 4));
}

// Memory Test: Verifies that getting an element is a read-only operation with no memory changes.
TEST_CASE(ArrayList_get, "Makes no memory allocations or deallocations") {
    const int initial[] = {10, 20, 30};
    struct ArrayList* list = _create_test_list(initial, 3, 3);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        ArrayList_get(list, 1);
    }, 0, 0, 0, 0);
}

/*============================================================================*/
/* TEST SUITE: ArrayList_set                                                  */
/*============================================================================*/
// Assertion Test: Ensures the function asserts when the list pointer is NULL.
TEST_ASSERTION_FAILURE(ArrayList_set, "Assertion should fail on NULL list") {
    ArrayList_set(NULL, 0, 99);
}

// Assertion Test: Ensures set asserts on an index equal to the size (out of bounds).
TEST_ASSERTION_FAILURE(ArrayList_set, "Assertion should fail on index equal to size") {
    const int elements[] = {10, 20};
    struct ArrayList* list = _create_test_list(elements, 2, 2);
    ArrayList_set(list, 2, 99);
}

// Assertion Test: Ensures set asserts on an index greater than the size (out of bounds).
TEST_ASSERTION_FAILURE(ArrayList_set, "Assertion should fail on index greater than size") {
    const int elements[] = {10, 20};
    struct ArrayList* list = _create_test_list(elements, 2, 2);
    ArrayList_set(list, 5, 99);
}

// Functional Test: Verifies updating the first element (index 0) works correctly.
TEST_CASE(ArrayList_set, "Sets the first element") {
    UT_disable_leak_check();
    const int initial[] = {10, 20, 30};
    struct ArrayList* list = _create_test_list(initial, 3, 3);
    
    ArrayList_set(list, 0, 11);

    const int expected_elements[] = {11, 20, 30};
    struct ArrayList* expected = _create_test_list(expected_elements, 3, 3);
    EQUAL_LIST(expected, list);
}

// Functional Test: Verifies updating an element in the middle of the list works correctly.
TEST_CASE(ArrayList_set, "Sets a middle element") {
    UT_disable_leak_check();
    const int initial[] = {10, 20, 30};
    struct ArrayList* list = _create_test_list(initial, 3, 3);

    ArrayList_set(list, 1, 22);

    const int expected_elements[] = {10, 22, 30};
    struct ArrayList* expected = _create_test_list(expected_elements, 3, 3);
    EQUAL_LIST(expected, list);
}

// Functional Test: Verifies updating the last element (at index size - 1) works correctly.
TEST_CASE(ArrayList_set, "Sets the last element") {
    UT_disable_leak_check();
    const int initial[] = {10, 20, 30};
    struct ArrayList* list = _create_test_list(initial, 3, 3);

    ArrayList_set(list, 2, 33);
    
    const int expected_elements[] = {10, 20, 33};
    struct ArrayList* expected = _create_test_list(expected_elements, 3, 3);
    EQUAL_LIST(expected, list);
}

// Memory Test: Verifies that setting an element is an in-place operation with no memory changes.
TEST_CASE(ArrayList_set, "Makes no memory allocations or deallocations") {
    const int initial[] = {10, 20, 30};
    struct ArrayList* list = _create_test_list(initial, 3, 3);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        ArrayList_set(list, 1, 99);
    }, 0, 0, 0, 0);
}

/*============================================================================*/
/* TEST SUITE: ArrayList_delete                                               */
/*============================================================================*/
// Assertion Test: Ensures the function asserts when the list pointer is NULL.
TEST_ASSERTION_FAILURE(ArrayList_delete, "Assertion should fail on NULL list") {
    ArrayList_delete(NULL, 0);
}

// Assertion Test: Ensures deletion asserts on an invalid index.
TEST_ASSERTION_FAILURE(ArrayList_delete, "Assertion should fail on invalid index") {
    const int elements[] = {10, 20};
    struct ArrayList* list = _create_test_list(elements, 2, 2);
    ArrayList_delete(list, 2);
}

// Verifies that deleting the first element works correctly.
TEST_CASE(ArrayList_delete, "Should delete the first element") {
    UT_disable_leak_check();
    // Arrange: Create a list with a few elements.
    const int initial[] = {10, 20, 30, 40};
    struct ArrayList* list = _create_test_list(initial, 4, 4);
    
    // Act: Delete the element at the beginning (index 0).
    ArrayList_delete(list, 0);

    // Assert: Check if the list contains the remaining elements in the correct order.
    const int expected_elements[] = {20, 30, 40};
    struct ArrayList* expected = _create_test_list(expected_elements, 3, 4);
    EQUAL_LIST(expected, list);
}

// Verifies that deleting a middle element works correctly.
TEST_CASE(ArrayList_delete, "Should delete a middle element") {
    UT_disable_leak_check();
    // Arrange: Create a list with a few elements.
    const int initial[] = {10, 20, 30, 40};
    struct ArrayList* list = _create_test_list(initial, 4, 4);
    
    // Act: Delete the element at index 1.
    ArrayList_delete(list, 1);

    // Assert: Check if the surrounding elements are now adjacent.
    const int expected_elements[] = {10, 30, 40};
    struct ArrayList* expected = _create_test_list(expected_elements, 3, 4);
    EQUAL_LIST(expected, list);
}

// Verifies that deleting the last element works correctly.
TEST_CASE(ArrayList_delete, "Should delete the last element") {
    UT_disable_leak_check();
    // Arrange: Create a list with a few elements.
    const int initial[] = {10, 20, 30, 40};
    struct ArrayList* list = _create_test_list(initial, 4, 4);
    
    // Act: Delete the element at the end (index 3).
    ArrayList_delete(list, 3);

    // Assert: Check that the list is correctly shortened.
    const int expected_elements[] = {10, 20, 30};
    struct ArrayList* expected = _create_test_list(expected_elements, 3, 4);
    EQUAL_LIST(expected, list);
}

// Memory Test: Verifies that deletion is an in-place operation with no memory changes.
TEST_CASE(ArrayList_delete, "Makes no memory allocations or deallocations") {
    const int initial[] = {10, 20, 30};
    struct ArrayList* list = _create_test_list(initial, 3, 3);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        ArrayList_delete(list, 1);
    }, 0, 0, 0, 0);
}

/*============================================================================*/
/* TEST SUITE: ArrayList_print                                                */
/*============================================================================*/
// Assertion Test: Ensures the function asserts when the list pointer is NULL.
TEST_ASSERTION_FAILURE(ArrayList_print, "Assertion should fail on NULL list") {
    ArrayList_print(NULL);
}

// Functional Test: Verifies the correct output format for an empty list.
TEST_CASE(ArrayList_print, "Prints an empty list") {
    UT_disable_leak_check();
    struct ArrayList* list = _create_test_list(NULL, 0, 1);
    ASSERT_STDOUT_EQUAL({
        ArrayList_print(list);
    }, "ArrayList()");
}

// Functional Test: Verifies the correct output format for a multi-element list.
TEST_CASE(ArrayList_print, "Prints a multiple item list with 3 elements") {
    UT_disable_leak_check();
    const int elements[] = {10, -20, 30};
    struct ArrayList* list = _create_test_list(elements, 3, 3);
    ASSERT_STDOUT_EQUAL({
        ArrayList_print(list);
    }, "ArrayList(10, -20, 30)");
}

// Memory Test: Verifies that printing is a read-only operation with no memory changes.
TEST_CASE(ArrayList_print, "Makes no memory allocations or deallocations") {
    const int initial[] = {10, 20, 30};
    struct ArrayList* list = _create_test_list(initial, 3, 3);
    UT_mark_memory_as_baseline();
    SILENT_ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        ArrayList_print(list);
    }, 0, 0, 0, 0);
}


/*============================================================================*/
/* MAIN FUNCTION                                                              */
/*============================================================================*/
int runAllTests(int argc, char* argv[]) {
    return UT_RUN_ALL_TESTS();
}