/*============================================================================*/
/* Test Suites for PlayList                                                   */
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

#include "PlayList.h"
#include "Helpers.h"

#define UNIT_TEST_DECLARATION
#define UNIT_TEST_IMPLEMENTATION
#include "test/unit/UnitTest.h"

/*============================================================================*/
/* TEST HELPERS (Wrappers)                                                    */
/*============================================================================*/

// Creates a test playlist from an array of strings, with the song at `playing_idx` marked as playing.
static struct PlayList* _create_test_playlist(const char* songs[], size_t count, size_t playing_idx) {
    return (struct PlayList*)_n(songs, count, playing_idx);
}

// Compares two PlayList structures for equality.
static bool _equalPlaylists(const struct PlayList* pl1, const struct PlayList* pl2) {
    return _c((struct Y*)pl1, (struct Y*)pl2);
}

// Prints a PlayList structure to a string buffer for test failure messages.
static void __p(char* buf, size_t size, struct PlayList* pl) {
    _p(buf, size, (struct Y*)pl);
}

static bool _check_integrity(struct PlayList* pl) {
    return _i((struct Y*)pl);
}

// Custom assertion macro for comparing PlayLists.
#define EQUAL_PLAYLIST(expected, actual) EQUAL_BY(expected, actual, _equalPlaylists, __p)

/*============================================================================*/
/* TEST SUITE: PlayList_new                                                   */
/*============================================================================*/
// Functional Test: Verifies that a new playlist is created, is not NULL, and has empty pointers.
TEST_CASE(PlayList_new, "Creates an empty non NULL playlist") {
    UT_disable_leak_check();
    struct PlayList* pl = PlayList_new();
    REFUTE_NULL(pl);
    ASSERT_NULL(pl->p_first);
    ASSERT_NULL(pl->p_playing);
}

// Memory Test: Verifies that PlayList_new allocates exactly enough memory for the playlist struct.
TEST_CASE(PlayList_new, "Allocates the correct amount of memory") {
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        PlayList_new();
    }, 1, 0, sizeof(struct PlayList), 0);
}

/*============================================================================*/
/* TEST SUITE: PlayList_insertAtFront                                         */
/*============================================================================*/
// Assertion Test: Ensures the function asserts when given a NULL playlist.
TEST_ASSERTION_FAILURE(PlayList_insertAtFront, "Assertion failure on NULL playlist") {
    PlayList_insertAtFront(NULL, "Some Song");
}

// Functional Test: Checks that inserting into an empty list works correctly.
TEST_CASE(PlayList_insertAtFront, "Inserts into an empty list") {
    UT_disable_leak_check();
    struct PlayList* pl = _create_test_playlist(NULL, 0, 0);
    const char* songs[] = {"Purple Rain"};
    struct PlayList* expected = _create_test_playlist(songs, 1, 0);
    PlayList_insertAtFront(pl, "Purple Rain");
    EQUAL_PLAYLIST(expected, pl);
    EQUAL_POINTER(NULL, pl->p_first->p_next);
    EQUAL_POINTER(NULL, pl->p_first->p_previous);
}

// Functional Test: Checks that inserting into a non-empty list places the new song at the beginning.
TEST_CASE(PlayList_insertAtFront, "Inserts into a non empty list") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"Bohemian Rhapsody", "Purple Rain"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 2, 0);
    const char* expected_songs[] = {"A Kind of Magic", "Bohemian Rhapsody", "Purple Rain"};
    struct PlayList* expected = _create_test_playlist(expected_songs, 3, 1);
    PlayList_insertAtFront(pl, "A Kind of Magic");
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}

// Memory Test: Verifies that inserting at the front allocates memory for exactly one new node.
TEST_CASE(PlayList_insertAtFront, "Allocates memory for one node inserting in empty list") {
    struct PlayList* pl = _create_test_playlist(NULL, 0, 0);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        PlayList_insertAtFront(pl, "A Kind of Magic");
    }, 1, 0, sizeof(struct Node), 0);
}

// Memory Test: Verifies that inserting at the front of a non-empty list allocates memory for exactly one new node.
TEST_CASE(PlayList_insertAtFront, "Allocates memory for one node inserting in non empty list") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"Purple Rain", "Bohemian Rhapsody"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 2, 0);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        PlayList_insertAtFront(pl, "A Kind of Magic");
    }, 1, 0, sizeof(struct Node), 0);
}

/*============================================================================*/
/* TEST SUITE: PlayList_insertInOrder                                         */
/*============================================================================*/
// Assertion Test: Ensures the function asserts when given a NULL playlist.
TEST_ASSERTION_FAILURE(PlayList_insertInOrder, "Assertion failure on NULL playlist") {
    PlayList_insertInOrder(NULL, "Some Song");
}

// Functional Test: Inserts into an empty list.
TEST_CASE(PlayList_insertInOrder, "Inserts into an empty list") {
    UT_disable_leak_check();
    struct PlayList* pl = _create_test_playlist(NULL, 0, 0);
    const char* songs[] = {"Hotel California"};
    struct PlayList* expected = _create_test_playlist(songs, 1, 0);
    PlayList_insertInOrder(pl, "Hotel California");
    EQUAL_PLAYLIST(expected, pl);
    EQUAL_POINTER(NULL, pl->p_first->p_next);
    EQUAL_POINTER(NULL, pl->p_first->p_previous);
}

// Functional Test: Inserts at the beginning of a sorted list.
TEST_CASE(PlayList_insertInOrder, "Inserts at the beginning of a sorted list") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"B", "C"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 2, 0);
    const char* expected_songs[] = {"A", "B", "C"};
    struct PlayList* expected = _create_test_playlist(expected_songs, 3, 1);
    PlayList_insertInOrder(pl, "A");
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}

// Functional Test: Inserts into the middle of a sorted list.
TEST_CASE(PlayList_insertInOrder, "Inserts into the middle of a sorted list") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"A", "C"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 2, 0);
    const char* expected_songs[] = {"A", "B", "C"};
    struct PlayList* expected = _create_test_playlist(expected_songs, 3, 0);
    PlayList_insertInOrder(pl, "B");
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}

// Functional Test: Inserts at the end of a sorted list.
TEST_CASE(PlayList_insertInOrder, "Inserts at the end of a sorted list") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"A", "B"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 2, 0);
    const char* expected_songs[] = {"A", "B", "C"};
    struct PlayList* expected = _create_test_playlist(expected_songs, 3, 0);
    PlayList_insertInOrder(pl, "C");
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}

// Memory Test: Verifies that inserting in order allocates memory for one new node.
TEST_CASE(PlayList_insertInOrder, "Allocates memory for just one node") {
    const char* initial_songs[] = {"A", "C", "D"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 3, 0);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        PlayList_insertInOrder(pl, "B");
    }, 1, 0, sizeof(struct Node), 0);
}

/*============================================================================*/
/* TEST SUITE: PlayList_insertAtEnd                                           */
/*============================================================================*/
// Assertion Test: Assertion failure on NULL playlist.
TEST_ASSERTION_FAILURE(PlayList_insertAtEnd, "Assertion failure on NULL playlist") {
    PlayList_insertAtEnd(NULL, "Some Song");
}

// Functional Test: Inserts into an empty list.
TEST_CASE(PlayList_insertAtEnd, "Inserts into an empty list") {
    UT_disable_leak_check();
    struct PlayList* pl = _create_test_playlist(NULL, 0, 0);
    const char* songs[] = {"Song A"};
    struct PlayList* expected = _create_test_playlist(songs, 1, 0);
    PlayList_insertAtEnd(pl, "Song A");
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}

// Functional Test: Appends to a non-empty list.
TEST_CASE(PlayList_insertAtEnd, "Inserts at the end of a non empty list") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"Song A", "Song B", "Song C"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 3, 0);
    const char* expected_songs[] = {"Song A", "Song B", "Song C", "Song D"};
    struct PlayList* expected = _create_test_playlist(expected_songs, 4, 0);
    PlayList_insertAtEnd(pl, "Song D");
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}

// Memory Test: Verifies allocation for just one new node.
TEST_CASE(PlayList_insertAtEnd, "Allocates memory for just one node") {
    const char* initial_songs[] = {"Hotel California"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 1, 0);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        PlayList_insertAtEnd(pl, "Sweet Child O' Mine");
    }, 1, 0, sizeof(struct Node), 0);
}

/*============================================================================*/
/* TEST SUITE: PlayList_insertAfter                                           */
/*============================================================================*/
// Assertion Test: Assertion failure on NULL playlist.
TEST_ASSERTION_FAILURE(PlayList_insertAfter, "Assertion failure on NULL playlist") {
    PlayList_insertAfter(NULL, "Target", "New");
}

// Functional Test: Inserts after the first song.
TEST_CASE(PlayList_insertAfter, "Inserts after a song in the middle") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"A", "C", "D"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 3, 0);
    const char* expected_songs[] = {"A", "B", "C", "D"};
    struct PlayList* expected = _create_test_playlist(expected_songs, 4, 0);
    bool result = PlayList_insertAfter(pl, "A", "B");
    ASSERT(result);
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}

// Functional Test: Inserts after a song in the middle.
TEST_CASE(PlayList_insertAfter, "Inserts after a song in the middle") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"A", "B", "D"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 3, 0);
    const char* expected_songs[] = {"A", "B", "C", "D"};
    struct PlayList* expected = _create_test_playlist(expected_songs, 4, 0);
    bool result = PlayList_insertAfter(pl, "B", "C");
    ASSERT(result);
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}

// Functional Test: Inserts after the last song.
TEST_CASE(PlayList_insertAfter, "Inserts after the last song") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"A", "B", "C"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 3, 0);
    const char* expected_songs[] = {"A", "B", "C", "D"};
    struct PlayList* expected = _create_test_playlist(expected_songs, 4, 0);
    bool result = PlayList_insertAfter(pl, "C", "D");
    ASSERT(result);
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}

// Functional Test: Fails when target song is not found.
TEST_CASE(PlayList_insertAfter, "Fails to insert when target song is not found") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"A", "C"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 2, 0);
    bool result = PlayList_insertAfter(pl, "X", "B");
    REFUTE(result);
}

// Memory Test: Allocates just one node on success.
TEST_CASE(PlayList_insertAfter, "Allocates memory for just one node on success") {
    const char* initial_songs[] = {"A"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 1, 0);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        PlayList_insertAfter(pl, "A", "B");
    }, 1, 0, sizeof(struct Node), 0);
}

// Memory Test: Does not change memory on failure.
TEST_CASE(PlayList_insertAfter, "Makes no memory changes on failure") {
    const char* initial_songs[] = {"A"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 1, 0);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        PlayList_insertAfter(pl, "X", "B");
    }, 0, 0, 0, 0);
}

/*============================================================================*/
/* TEST SUITE: PlayList_insertBefore                                          */
/*============================================================================*/
// Assertion Test: Assertion failure on NULL playlist.
TEST_ASSERTION_FAILURE(PlayList_insertBefore, "Assertion failure on NULL playlist") {
    PlayList_insertBefore(NULL, "Target", "New");
}

// Functional Test: Inserts before the last song.
TEST_CASE(PlayList_insertBefore, "Inserts before the last song") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"A", "B", "D"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 3, 2);
    const char* expected_songs[] = {"A", "B", "C", "D"};
    struct PlayList* expected = _create_test_playlist(expected_songs, 4, 3);
    bool result = PlayList_insertBefore(pl, "D", "C");
    ASSERT(result);
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}

// Functional Test: Inserts before a song in the middle.
TEST_CASE(PlayList_insertBefore, "Inserts before a song in the middle") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"A", "C", "D"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 3, 0);
    const char* expected_songs[] = {"A", "B", "C", "D"};
    struct PlayList* expected = _create_test_playlist(expected_songs, 4, 0);
    bool result = PlayList_insertBefore(pl, "C", "B");
    ASSERT(result);
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}

// Functional Test: Inserts before the first song.
TEST_CASE(PlayList_insertBefore, "Inserts before the first song") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"B", "C"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 2, 0);
    const char* expected_songs[] = {"A", "B", "C"};
    struct PlayList* expected = _create_test_playlist(expected_songs, 3, 1);
    bool result = PlayList_insertBefore(pl, "B", "A");
    ASSERT(result);
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}

// Memory Test: Allocates just one node on success.
TEST_CASE(PlayList_insertBefore, "Allocates memory for just one node on success") {
    const char* initial_songs[] = {"C"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 1, 0);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        PlayList_insertBefore(pl, "C", "B");
    }, 1, 0, sizeof(struct Node), 0);
}

/*============================================================================*/
/* TEST SUITE: PlayList_deleteFromFront                                       */
/*============================================================================*/
// Assertion Test: Assertion failure on NULL playlist.
TEST_ASSERTION_FAILURE(PlayList_deleteFromFront, "Assertion failure on NULL playlist") {
    PlayList_deleteFromFront(NULL);
}

// Assertion Test: Asserts when the playlist is empty.
TEST_ASSERTION_FAILURE(PlayList_deleteFromFront, "Asserts on empty playlist") {
    struct PlayList* pl = _create_test_playlist(NULL, 0, 0);
    PlayList_deleteFromFront(pl);
}

// Functional Test: Deletes the only element, resulting in an empty list.
TEST_CASE(PlayList_deleteFromFront, "Deletes the only element") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"A"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 1, 0);
    struct PlayList* expected = _create_test_playlist(NULL, 0, 0);
    PlayList_deleteFromFront(pl);
    EQUAL_POINTER(NULL, pl->p_first);
}

// Functional Test: Deletes the first element, resulting in an non-empty list.
TEST_CASE(PlayList_deleteFromFront, "Deletes the first element") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"A", "B", "C"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 3, 0);
    const char* expected_songs[] = {"B", "C"};
    struct PlayList* expected = _create_test_playlist(expected_songs, 2, 0);
    PlayList_deleteFromFront(pl);
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}

// Memory Test: Frees memory for just one node.
TEST_CASE(PlayList_deleteFromFront, "Frees memory for just one node") {
    const char* initial_songs[] = {"A", "B"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 2, 0);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        PlayList_deleteFromFront(pl);
    }, 0, 1, 0, sizeof(struct Node));
}

/*============================================================================*/
/* TEST SUITE: PlayList_deleteSong                                            */
/*============================================================================*/
// Assertion Test: Assertion failure on NULL playlist.
TEST_ASSERTION_FAILURE(PlayList_deleteSong, "Assertion failure on NULL playlist") {
    PlayList_deleteSong(NULL, "Some Song");
}

// Functional Test: Deletes the only song in the list.
TEST_CASE(PlayList_deleteSong, "Deletes the only song") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"A"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 1, 0);
    struct PlayList* expected = _create_test_playlist(NULL, 0, 0);
    bool result = PlayList_deleteSong(pl, "A");
    ASSERT(result);
    EQUAL_PLAYLIST(expected, pl);
    EQUAL_POINTER(NULL, pl->p_first);
}

// Functional Test: Deletes the first song in the list.
TEST_CASE(PlayList_deleteSong, "Deletes the first song") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"A", "B", "C"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 3, 0);
    const char* expected_songs[] = {"B", "C"};
    struct PlayList* expected = _create_test_playlist(expected_songs, 2, 0);
    bool result = PlayList_deleteSong(pl, "A");
    ASSERT(result);
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}


// Functional Test: Deletes a song in the middle of the list.
TEST_CASE(PlayList_deleteSong, "Deletes a song in the middle") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"A", "B", "C"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 3, 0);
    const char* expected_songs[] = {"A", "C"};
    struct PlayList* expected = _create_test_playlist(expected_songs, 2, 0);
    bool result = PlayList_deleteSong(pl, "B");
    ASSERT(result);
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}

// Functional Test: Deletes the last song in the list.
TEST_CASE(PlayList_deleteSong, "Deletes the last song") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"A", "B", "C"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 3, 0);
    const char* expected_songs[] = {"A", "B"};
    struct PlayList* expected = _create_test_playlist(expected_songs, 2, 0);
    bool result = PlayList_deleteSong(pl, "C");
    ASSERT(result);
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}

// Functional Test: Fails when song to delete is not found.
TEST_CASE(PlayList_deleteSong, "Returns false if song not found") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"A", "B", "C"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 3, 0);
    bool result = PlayList_deleteSong(pl, "X");
    REFUTE(result);
}

// Memory Test: Frees memory for one node on success.
TEST_CASE(PlayList_deleteSong, "Frees memory for one node on success") {
    const char* initial_songs[] = {"A", "B"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 2, 0);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        PlayList_deleteSong(pl, "B");
    }, 0, 1, 0, sizeof(struct Node));
}

// Memory Test: Makes no memory allocations or deallocations if song not found.
TEST_CASE(PlayList_deleteSong, "Makes no memory allocations or deallocations if song not found") {
    const char* initial_songs[] = {"A"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 1, 0);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        PlayList_deleteSong(pl, "X");
    }, 0, 0, 0, 0);
}

// Memory Test: Does not modify the playlist structure if song not found.
TEST_CASE(PlayList_deleteSong, "Does not modify the playlist structure if song not found") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"A", "B"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 2, 0);
    struct PlayList* expected = _create_test_playlist(initial_songs, 2, 0);
    PlayList_deleteSong(pl, "X");
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}

/*============================================================================*/
/* TEST SUITE: PlayList_print                                                 */
/*============================================================================*/
// Assertion Test: Assertion failure on NULL playlist.
TEST_ASSERTION_FAILURE(PlayList_print, "Assertion failure on NULL playlist") {
    PlayList_print(NULL);
}

// Functional Test: Prints an empty list correctly.
TEST_CASE(PlayList_print, "Prints an empty list") {
    UT_disable_leak_check();
    struct PlayList* pl = _create_test_playlist(NULL, 0, 0);
    ASSERT_STDOUT_EQUAL({
        PlayList_print(pl);
    }, "");
}

// Functional Test: Prints a list with one song.
TEST_CASE(PlayList_print, "Prints a single item list") {
    UT_disable_leak_check();
    const char* songs[] = {"Song1"};
    struct PlayList* pl = _create_test_playlist(songs, 1, 0);
    ASSERT_STDOUT_EQUAL({
        PlayList_print(pl);
    }, "Song1\n");
}

// Functional Test: Prints a list with several songs.
TEST_CASE(PlayList_print, "Prints a multiple item list") {
    UT_disable_leak_check();
    const char* songs[] = {"Song1", "Song2", "Song3"};
    struct PlayList* pl = _create_test_playlist(songs, 3, 0);
    ASSERT_STDOUT_EQUAL({
        PlayList_print(pl);
    }, "Song1\nSong2\nSong3\n");
}

// Memory Test: Makes no memory allocations or deallocations.
TEST_CASE(PlayList_print, "Makes no memory allocations or deallocations") {
    const char* songs[] = {"Song1", "Song2"};
    struct PlayList* pl = _create_test_playlist(songs, 2, 0);
    UT_mark_memory_as_baseline();
    SILENT_ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        PlayList_print(pl);
    }, 0, 0, 0, 0);
}

// Memory Test: does not modify the playlist structure.
TEST_CASE(PlayList_print, "Does not modify the playlist structure") {
    const char* songs[] = {"Song1", "Song2"};
    struct PlayList* pl = _create_test_playlist(songs, 2, 0);
    struct PlayList* copy = _create_test_playlist(songs, 2, 0);
    UT_mark_memory_as_baseline();
    SILENT_ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        PlayList_print(pl);
    }, 0, 0, 0, 0);
    EQUAL_PLAYLIST(copy, pl);
    _check_integrity(pl);
}

/*============================================================================*/
/* TEST SUITE: PlayList_sort                                                  */
/*============================================================================*/
// Assertion Test: Assertion failure on NULL playlist.
TEST_ASSERTION_FAILURE(PlayList_sort, "Assertion failure on NULL playlist") {
    PlayList_sort(NULL);
}

// Functional Test: Sorting an empty list does nothing.
TEST_CASE(PlayList_sort, "Sorts an empty list") {
    UT_disable_leak_check();
    struct PlayList* pl = _create_test_playlist(NULL, 0, 0);
    PlayList_sort(pl);    
    EQUAL_POINTER(NULL, pl->p_first);
    EQUAL_POINTER(NULL, pl->p_playing);
}

// Functional Test: Sorting an already sorted list changes nothing.
TEST_CASE(PlayList_sort, "Sorts an already sorted list") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"A", "B", "C"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 3, 0);
    struct PlayList* expected = _create_test_playlist(initial_songs, 3, 0);
    PlayList_sort(pl);
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}

// Functional Test: Sorting an unsorted list orders it correctly.
TEST_CASE(PlayList_sort, "Sorts an unsorted list") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"C", "A", "E", "B", "D"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 5, 0);
    const char* sorted_songs[] = {"A", "B", "C", "D", "E"};
    struct PlayList* expected = _create_test_playlist(sorted_songs, 5, 0);
    PlayList_sort(pl);
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}

// Memory Test: Performs an in-place sort with no memory changes.
TEST_CASE(PlayList_sort, "Makes no memory allocations or deallocations") {
    const char* initial_songs[] = {"Z", "M", "A"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 3, 0);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        PlayList_sort(pl);
    }, 0, 0, 0, 0);
}

/*============================================================================*/
/* TEST SUITE: PlayList_deleteAll                                             */
/*============================================================================*/
// Assertion Test: Assertion failure on NULL playlist.
TEST_ASSERTION_FAILURE(PlayList_deleteAll, "Assertion failure on NULL playlist") {
    PlayList_deleteAll(NULL);
}

// Functional Test: Deleting all from an empty list does nothing.
TEST_CASE(PlayList_deleteAll, "Deletes from an empty list") {
    UT_disable_leak_check();
    struct PlayList* pl = _create_test_playlist(NULL, 0, 0);
    PlayList_deleteAll(pl);
    EQUAL_POINTER(NULL, pl->p_first);
    EQUAL_POINTER(NULL, pl->p_playing);
}

// Functional Test: Deleting all from a non-empty list results in an empty list.
TEST_CASE(PlayList_deleteAll, "Deletes all from a non empty list") {
    UT_disable_leak_check();
    const char* initial_songs[] = {"A", "B", "C"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 3, 0);
    PlayList_deleteAll(pl);    
    EQUAL_POINTER(NULL, pl->p_first);
    EQUAL_POINTER(NULL, pl->p_playing);
}

// Memory Test: Frees memory for all nodes.
TEST_CASE(PlayList_deleteAll, "Frees memory for all nodes") {
    const char* initial_songs[] = {"A", "B", "C"};
    struct PlayList* pl = _create_test_playlist(initial_songs, 3, 0);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        PlayList_deleteAll(pl);
    }, 0, 3, 0, 3 * sizeof(struct Node));
}

/*============================================================================*/
/* TEST SUITE: PlayList_playingSong                                           */
/*============================================================================*/
// Assertion Test: Assertion failure on NULL playlist.
TEST_ASSERTION_FAILURE(PlayList_playingSong, "Assertion failure on NULL playlist") {
    PlayList_playingSong(NULL);
}

// Functional Test: Returns NULL if the playlist is empty.
TEST_CASE(PlayList_playingSong, "Returns NULL if playlist is empty") {
    UT_disable_leak_check();
    struct PlayList* pl = _create_test_playlist(NULL, 0, 0);
    char* playing = PlayList_playingSong(pl);
    ASSERT_NULL(playing);
}

// Functional Test: Returns the correct song when one is playing.
TEST_CASE(PlayList_playingSong, "Returns the correct song when one is playing") {
    UT_disable_leak_check();
    const char* songs[] = {"Song A", "Song B", "Song C"};
    struct PlayList* pl = _create_test_playlist(songs, 3, 1); // Playing "Song B"
    char* playing = PlayList_playingSong(pl);
    REFUTE_NULL(playing);
    EQUAL_STRING(playing, "Song B");
}

// Memory Test: Allocates memory for the returned string.
TEST_CASE(PlayList_playingSong, "Allocates memory for the returned string") {
    const char* song_names[] = {"A Kind of Magic", "Smells Like Teen Spirit"};
    size_t index_playing = 1;
    struct PlayList* pl = _create_test_playlist(song_names, 2, index_playing); // Playing "Smells Like Teen Spirit"
    UT_mark_memory_as_baseline();
    size_t expected_bytes = strlen(song_names[index_playing]) + 1;
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        PlayList_playingSong(pl);
    }, 1, 0, expected_bytes, 0);
}

// Memory Test: does not modify the playlist structure.
TEST_CASE(PlayList_playingSong, "Does not modify the playlist structure") {
    UT_disable_leak_check();
    const char* songs[] = {"Song A", "Song B"};
    struct PlayList* pl = _create_test_playlist(songs, 2, 0);
    struct PlayList* copy = _create_test_playlist(songs, 2, 0);
    PlayList_playingSong(pl);
    EQUAL_PLAYLIST(copy, pl);
    _check_integrity(pl);
}

/*============================================================================*/
/* TEST SUITE: PlayList_playNext                                              */
/*============================================================================*/
// Assertion Test: Assertion failure on NULL playlist.
TEST_ASSERTION_FAILURE(PlayList_playNext, "Assertion failure on NULL playlist") {
    PlayList_playNext(NULL);
}

// Functional Test: Does nothing at the end of the list.
TEST_CASE(PlayList_playNext, "Does nothing at the end of the list") {
    UT_disable_leak_check();
    const char* songs[] = {"A", "B"};
    struct PlayList* pl = _create_test_playlist(songs, 2, 1); // Playing "B"
    struct PlayList* expected = _create_test_playlist(songs, 2, 1); 
    PlayList_playNext(pl);
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}

// Functional Test: Advances to the next song when not at the end.
TEST_CASE(PlayList_playNext, "Advances to the next song when not at the end") {
    UT_disable_leak_check();
    const char* songs[] = {"A", "B", "C"};
    struct PlayList* pl = _create_test_playlist(songs, 3, 0); // Playing "A"
    const char* expected_songs[] = {"A", "B", "C"};
    struct PlayList* expected = _create_test_playlist(expected_songs, 3, 1); // Now playing "B"
    PlayList_playNext(pl);
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}

// Memory Test: Makes no memory changes.
TEST_CASE(PlayList_playNext, "Makes no memory changes") {
    const char* songs[] = {"A", "B"};
    struct PlayList* pl = _create_test_playlist(songs, 2, 0);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        PlayList_playNext(pl);
    }, 0, 0, 0, 0);
}

// Memory Test: does not modify the playlist linked structure.
TEST_CASE(PlayList_playNext, "Does not modify the playlist linked structure") {
    UT_disable_leak_check();
    const char* songs[] = {"A", "B"};
    struct PlayList* pl = _create_test_playlist(songs, 2, 0);
    struct PlayList* expected = _create_test_playlist(songs, 2, 1);
    PlayList_playNext(pl);
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}

/*============================================================================*/
/* TEST SUITE: PlayList_playPrevious                                          */
/*============================================================================*/
// Assertion Test: Assertion failure on NULL playlist.
TEST_ASSERTION_FAILURE(PlayList_playPrevious, "Assertion failure on NULL playlist") {
    PlayList_playPrevious(NULL);
}

// Functional Test: Does nothing at the beginning of the list.
TEST_CASE(PlayList_playPrevious, "Does nothing at the beginning of the list") {
    UT_disable_leak_check();
    const char* songs[] = {"A", "B"};
    struct PlayList* pl = _create_test_playlist(songs, 2, 0); // Playing "A"
    struct PlayList* expected = _create_test_playlist(songs, 2, 0); 
    PlayList_playPrevious(pl);
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}

// Functional Test: Moves to the previous song when not at the beginning.
TEST_CASE(PlayList_playPrevious, "Moves to the previous song when not at the beginning") {
    UT_disable_leak_check();
    const char* songs[] = {"A", "B", "C"};
    struct PlayList* pl = _create_test_playlist(songs, 3, 2); // Playing "C"
    const char* expected_songs[] = {"A", "B", "C"};
    struct PlayList* expected = _create_test_playlist(expected_songs, 3, 1); // Now playing "B"
    PlayList_playPrevious(pl);
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}   

// Memory Test: Makes no memory changes.
TEST_CASE(PlayList_playPrevious, "Makes no memory changes") {
    const char* songs[] = {"A", "B"};
    struct PlayList* pl = _create_test_playlist(songs, 2, 1); // Playing "B"
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        PlayList_playPrevious(pl);
    }, 0, 0, 0, 0);
}

// Memory Test: does not modify the playlist linked structure.
TEST_CASE(PlayList_playPrevious, "Does not modify the playlist linked structure") {
    UT_disable_leak_check();
    const char* songs[] = {"A", "B"};
    struct PlayList* pl = _create_test_playlist(songs, 2, 1); // Playing "B"
    struct PlayList* expected = _create_test_playlist(songs, 2, 0); // Now playing "A"
    PlayList_playPrevious(pl);
    EQUAL_PLAYLIST(expected, pl);
    _check_integrity(pl);
}

/*============================================================================*/
/* TEST SUITE: PlayList_free                                                  */
/*============================================================================*/
// Assertion Test: Asserts on NULL p_p_playList.
TEST_ASSERTION_FAILURE(PlayList_free, "Assertion failure on NULL playlist") {
    PlayList_free(NULL);
}

// Assertion Test: Asserts on NULL *p_p_playList.
TEST_ASSERTION_FAILURE(PlayList_free, "Assertion failure on pointer to NULL playlist") {
    struct PlayList* pl = NULL;
    PlayList_free(&pl);
}

// Functional Test: Frees an empty playlist and sets pointer to NULL.
TEST_CASE(PlayList_free, "Frees an empty playlist and sets pointer to NULL") {
    UT_disable_leak_check();
    struct PlayList* pl = _create_test_playlist(NULL, 0, 0);
    PlayList_free(&pl);
    ASSERT_NULL(pl);
}

// Functional Test: Frees a non-empty playlist and sets pointer to NULL.
TEST_CASE(PlayList_free, "Frees a non empty playlist and sets pointer to NULL") {
    UT_disable_leak_check();
    const char* songs[] = {"A", "B", "C"};
    struct PlayList* pl = _create_test_playlist(songs, 3, 1); // Playing "B"
    PlayList_free(&pl);
    ASSERT_NULL(pl);
}

// Memory Test: Frees all nodes and the playlist struct.
TEST_CASE(PlayList_free, "Frees all nodes and the playlist struct") {
    // For free, we build the list with the SUT's own functions for a realistic test
    const char* songs[] = {"A", "B", "C"};
    struct PlayList* pl = _create_test_playlist(songs, 3, 1); // Playing "B"
    UT_mark_memory_as_baseline();
    size_t expected_bytes_freed = sizeof(struct PlayList) + (3 * sizeof(struct Node));
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        PlayList_free(&pl);
    }, 0, 4, 0, expected_bytes_freed);
    ASSERT_NULL(pl);
}


/*============================================================================*/
/* MAIN FUNCTION                                                              */
/*============================================================================*/
int runAllTests(int argc, char* argv[]) {
    return UT_RUN_ALL_TESTS();
}