/*============================================================================*/
/* Test Suites for Task and Scheduler                                         */
/* Pepe Gallardo, 2025                                                        */
/*============================================================================*/

#include "Scheduler.h"
#include "Helpers.h"

#define UNIT_TEST_DECLARATION
#define UNIT_TEST_IMPLEMENTATION
#include "test/unit/UnitTest.h"

/*============================================================================*/
/* TEST HELPERS                                                               */
/*============================================================================*/

static struct Task *_create_task(unsigned int id, const char name[], unsigned int quantum)
{
    return (struct Task *)_z(id, name, quantum);
}

static struct Node *_create_test_scheduler(const struct Task values[], size_t count)
{
    return (struct Node *)_n(values, count);
}

static bool _equalSchedulers(const struct Node *s1, const struct Node *s2)
{
    return _c((struct X *)s1, (struct X *)s2);
}

static void __p(char *buf, size_t size, struct Node *scheduler)
{
    _p(buf, size, (struct X *)scheduler);
}

#define EQUAL_SCHEDULER(expected, actual) EQUAL_BY(expected, actual, _equalSchedulers, __p)

/*============================================================================*/
/* TEST SUITE: Task_new                                                       */
/*============================================================================*/
// Ensures the function triggers an assertion if the provided name is NULL.
TEST_ASSERTION_FAILURE(Task_new, "Assertion should fail on NULL name")
{
    Task_new(1, NULL, 10);
}

// Ensures the function triggers an assertion if the provided quantum is zero.
TEST_ASSERTION_FAILURE(Task_new, "Assertion should fail on zero quantum")
{
    Task_new(1, "Task1", 0);
}

// Ensures the function triggers an assertion if the provided name exceeds MAX_NAME_LEN.
TEST_ASSERTION_FAILURE(Task_new, "Assertion should fail on name too long")
{
    Task_new(1, "This name is definitely way too long for the buffer", 10);
}

// Checks that Task_new returns a valid, non-NULL pointer.
TEST_CASE(Task_new, "Creates a non-NULL task")
{
    UT_disable_leak_check();
    struct Task *t = Task_new(1, "Task1", 10);
    REFUTE_NULL(t);
}

// Verifies that the fields of the new task are initialized with the correct values.
TEST_CASE(Task_new, "Initializes fields correctly for Task_new(1, \"Task1\", 10)")
{
    UT_disable_leak_check();
    struct Task *t = Task_new(1, "Task1", 10);
    REFUTE_NULL(t);
    EQUAL_UINT(1, t->id);
    EQUAL_STRING("Task1", t->name);
    EQUAL_UINT(10, t->quantum);
}

// Confirms that Task_new allocates exactly the memory required for one Task structure.
TEST_CASE(Task_new, "Allocates exactly the memory required for one Task structure")
{
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({ Task_new(1, "Task1", 10); }, 1, 0, sizeof(struct Task), 0);
}

/*============================================================================*/
/* TEST SUITE: Task_free                                                      */
/*============================================================================*/
// Ensures the function triggers an assertion if the double pointer itself is NULL.
TEST_ASSERTION_FAILURE(Task_free, "Assertion should fail on NULL p_p_task")
{
    Task_free(NULL);
}

// Ensures the function triggers an assertion if the pointer to the task is NULL.
TEST_ASSERTION_FAILURE(Task_free, "Assertion should fail on p_p_task being a pointer to a NULL task")
{
    struct Task *t = NULL;
    Task_free(&t);
}

// Verifies that Task_free deallocates the task's memory and sets the pointer to NULL.
TEST_CASE(Task_free, "Frees memory and NULLs pointer")
{
    struct Task *t = _create_task(1, "Task1", 10);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({ Task_free(&t); }, 0, 1, 0, sizeof(struct Task));
    ASSERT_NULL(t);
}

/*============================================================================*/
/* TEST SUITE: Task_copyOf                                                    */
/*============================================================================*/
// Ensures the function triggers an assertion if the task to copy is NULL.
TEST_ASSERTION_FAILURE(Task_copyOf, "Assertion should fail on NULL p_task")
{
    Task_copyOf(NULL);
}

// Checks that Task_copyOf creates a new, distinct task with identical content.
TEST_CASE(Task_copyOf, "Creates a correct copy for Task_new(1, \"Task1\", 10)")
{
    UT_disable_leak_check();
    struct Task *original = _create_task(1, "Task1", 10);
    struct Task *copy = Task_copyOf(original);

    REFUTE_NULL(copy);
    NON_EQUAL_POINTER(original, copy); // Ensure it's a different memory address
    EQUAL_UINT(original->id, copy->id);
    EQUAL_STRING(original->name, copy->name);
    EQUAL_UINT(original->quantum, copy->quantum);
}

// Confirms that Task_copy allocates exactly the memory required for one Task structure.
TEST_CASE(Task_copyOf, "Allocates exactly the memory required for one Task structure")
{
    struct Task *original = _create_task(1, "Task1", 10);
    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({ Task_copyOf(original); }, 1, 0, sizeof(struct Task), 0);
}

/*============================================================================*/
/* TEST SUITE: Task_print                                                     */
/*============================================================================*/
// Ensures the function triggers an assertion if the task to print is NULL.
TEST_ASSERTION_FAILURE(Task_print, "Assertion should fail on NULL p_task")
{
    Task_print(NULL);
}

// Verifies that Task_print produces the expected formatted string to standard output.
TEST_CASE(Task_print, "Outputs correctly for Task_new(123, \"PrintMe\", 45)")
{
    UT_disable_leak_check();
    struct Task *t = _create_task(123, "PrintMe", 45);
    ASSERT_STDOUT_EQUAL({ Task_print(t); }, "Task(ID: 123, Name: PrintMe, Quantum: 45)");
}

// Verifies that Task_print does not allocate or free any memory.
TEST_CASE(Task_print, "Task_print does not allocate or free memory")
{
    struct Task *t = _create_task(123, "PrintMe", 45);
    UT_mark_memory_as_baseline();
    SILENT_ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        Task_print(t);
    }, 0, 0, 0, 0);
}

/*============================================================================*/
/* TEST SUITE: Scheduler_new                                                  */
/*============================================================================*/
// Checks that a new, empty scheduler is represented by a NULL pointer.
TEST_CASE(Scheduler_new, "Returns NULL for an empty scheduler")
{
    struct Node *s = Scheduler_new();
    ASSERT_NULL(s);
}

/*============================================================================*/
/* TEST SUITE: Scheduler_size                                                 */
/*============================================================================*/
// Verifies that the size of a newly created scheduler is 0.
TEST_CASE(Scheduler_size, "Size of empty scheduler is 0")
{
    struct Node *s = _create_test_scheduler(NULL, 0);
    EQUAL_SIZE_T(0, Scheduler_size(s));
}

// Verifies that the size is 1 after adding one task.
TEST_CASE(Scheduler_size, "Size of single-task scheduler is 1")
{
    struct Task tasks[] = {{1, "T1", 10}};
    struct Node *s = _create_test_scheduler(tasks, 1);
    UT_mark_memory_as_baseline();
    EQUAL_SIZE_T(1, Scheduler_size(s));
}

// Verifies that the size is correctly reported for a scheduler with multiple tasks.
TEST_CASE(Scheduler_size, "Size of multi-task scheduler with 3 tasks is correct")
{
    struct Task tasks[] = {{1, "T1", 10}, {2, "T2", 20}, {3, "T3", 30}};
    struct Node *s = _create_test_scheduler(tasks, 3);
    UT_mark_memory_as_baseline();
    EQUAL_SIZE_T(3, Scheduler_size(s));
}

// Verifies that the size function does not modify the scheduler.
TEST_CASE(Scheduler_size, "Scheduler_size does not modify the scheduler")
{
    struct Task tasks[] = {{1, "T1", 10}, {2, "T2", 20}};
    struct Node *s = _create_test_scheduler(tasks, 2);
    struct Node *copy = _create_test_scheduler(tasks, 2); // Create a copy for comparison

    UT_mark_memory_as_baseline();
    EQUAL_SIZE_T(2, Scheduler_size(s));
    EQUAL_SCHEDULER(copy, s); // Ensure they are equal before size call
    EQUAL_STRING("T1", s->p_next->task.name); // Check first task remains unchanged
    EQUAL_STRING("T2", s->p_next->p_next->task.name);
    EQUAL_STRING("T1", s->p_next->p_next->p_next->task.name); // Circular link check
}

// Verifies that calling Scheduler_size does not allocate or free any memory.
TEST_CASE(Scheduler_size, "Scheduler_size does not allocate or free memory")
{
    struct Task tasks[] = {{1, "T1", 10}, {2, "T2", 20}};
    struct Node *s = _create_test_scheduler(tasks, 2);

    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({ Scheduler_size(s); }, 0, 0, 0, 0);
}

/*============================================================================*/
/* TEST SUITE: Scheduler_enqueue                                              */
/*============================================================================*/
// Checks that adding a task to an empty scheduler results in a valid single-node scheduler.
TEST_CASE(Scheduler_enqueue, "Enqueue new_Task(1, \"T1\", 10) to an empty scheduler")
{
    struct Node *s = _create_test_scheduler(NULL, 0);
    struct Task task1 = {1, "T1", 10};
    struct Task expected_tasks[] = {{1, "T1", 10}};
    struct Node *expected = _create_test_scheduler(expected_tasks, 1);

    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({ Scheduler_enqueue(&s, &task1); }, 1, 0, sizeof(struct Node), 0);

    EQUAL_SCHEDULER(expected, s);
    EQUAL_POINTER(s->p_next, s); // Check circular link
}

// Checks that adding a task to a non-empty scheduler correctly appends it to the end.
TEST_CASE(Scheduler_enqueue, "Enqueue new_Task(4, \"T4\", 40) to a non-empty scheduler")
{
    struct Task initial_tasks[] = {{1, "T1", 10}, {2, "T2", 20}, {3, "T3", 30}};
    struct Node *s = _create_test_scheduler(initial_tasks, 3);
    struct Task task2 = {4, "T4", 40};
    struct Task expected_tasks[] = {{1, "T1", 10}, {2, "T2", 20}, {3, "T3", 30}, {4, "T4", 40}};
    struct Node *expected = _create_test_scheduler(expected_tasks, 4);

    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({ Scheduler_enqueue(&s, &task2); }, 1, 0, sizeof(struct Node), 0);

    EQUAL_SCHEDULER(expected, s);
    EQUAL_UINT(4, s->task.id); // Check that p_last is updated correctly
    EQUAL_STRING("T4", s->task.name);
    EQUAL_UINT(40, s->task.quantum);

    EQUAL_UINT(1, s->p_next->task.id); // Check circular link
    EQUAL_STRING("T1", s->p_next->task.name);
    EQUAL_UINT(10, s->p_next->task.quantum);
}

/*============================================================================*/
/* TEST SUITE: Scheduler_first                                                */
/*============================================================================*/
// Ensures the function triggers an assertion if called on an empty scheduler.
TEST_ASSERTION_FAILURE(Scheduler_first, "Assertion should fail on empty scheduler")
{
    struct Node *s = _create_test_scheduler(NULL, 0);
    Scheduler_first(s);
}

// Verifies that the function returns a heap-allocated copy of the first task in the queue.
TEST_CASE(Scheduler_first, "Returns a copy of the correct first task (new_Task(1, \"First\", 10))")
{
    struct Task tasks[] = {{1, "First", 10}, {2, "Second", 20}};
    struct Node *s = _create_test_scheduler(tasks, 2);
    struct Task *first_task;

    UT_mark_memory_as_baseline();
    // Task_copyOf calls Task_new, so 1 allocation is expected.
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({ first_task = Scheduler_first(s); }, 1, 0, sizeof(struct Task), 0);

    NON_EQUAL_POINTER(&(s->task), first_task); // Ensure it's a different memory address
    REFUTE_NULL(first_task);
    EQUAL_UINT(1, first_task->id);
    EQUAL_STRING("First", first_task->name);
    EQUAL_UINT(10, first_task->quantum);
}

// Verifies that the first function does not modify the scheduler.
TEST_CASE(Scheduler_first, "Scheduler_first does not modify the scheduler")
{
    struct Task tasks[] = {{1, "First", 10}, {2, "Second", 20}};
    struct Node *s = _create_test_scheduler(tasks, 2);
    struct Node *copy = _create_test_scheduler(tasks, 2); // Create a copy for comparison

    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({ Scheduler_first(s); }, 1, 0, sizeof(struct Task), 0);

    EQUAL_SCHEDULER(copy, s); // Ensure they are equal 
    EQUAL_STRING("First", s->p_next->task.name); // Check first task remains unchanged
    EQUAL_STRING("Second", s->p_next->p_next->task.name);
    EQUAL_STRING("First", s->p_next->p_next->p_next->task.name); // Circular link check
}

/*============================================================================*/
/* TEST SUITE: Scheduler_dequeue                                              */
/*============================================================================*/
// Ensures the function triggers an assertion if called on an empty scheduler.
TEST_ASSERTION_FAILURE(Scheduler_dequeue, "Assertion should fail on empty scheduler")
{
    struct Node *s = _create_test_scheduler(NULL, 0);
    Scheduler_dequeue(&s);
}

// Checks that removing the only task from a scheduler makes it empty (NULL).
TEST_CASE(Scheduler_dequeue, "Dequeue from single-task scheduler frees memory and makes it empty")
{
    struct Task tasks[] = {{1, "Only", 10}};
    struct Node *s = _create_test_scheduler(tasks, 1);

    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({ Scheduler_dequeue(&s); }, 0, 1, 0, sizeof(struct Node));

    ASSERT_NULL(s);
}

// Checks that removing from a multi-task scheduler correctly removes the first element.
TEST_CASE(Scheduler_dequeue, "Dequeue from multi-task scheduler removes first")
{
    struct Task tasks[] = {{1, "T1", 10}, {2, "T2", 20}, {3, "T3", 30}};
    struct Node *s = _create_test_scheduler(tasks, 3);

    struct Task expected_tasks[] = {{2, "T2", 20}, {3, "T3", 30}, };
    struct Node *expected = _create_test_scheduler(expected_tasks, 2);

    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({ Scheduler_dequeue(&s); }, 0, 1, 0, sizeof(struct Node));

    EQUAL_SCHEDULER(expected, s);
    EQUAL_UINT(3, s->task.id); // Check p_last is still pointing to the correct node
    EQUAL_STRING("T3", s->task.name);
    EQUAL_UINT(30, s->task.quantum);

    EQUAL_UINT(2, s->p_next->task.id); // Check new first task
    EQUAL_STRING("T2", s->p_next->task.name);
    EQUAL_UINT(20, s->p_next->task.quantum);
}

/*============================================================================*/
/* TEST SUITE: Scheduler_clear                                                */
/*============================================================================*/
TEST_ASSERTION_FAILURE(Scheduler_clear, "Assertion should fail on NULL p_p_last")
{
    Scheduler_clear(NULL);
}

// Verifies that clearing an already empty scheduler does nothing and causes no errors.
TEST_CASE(Scheduler_clear, "Clear an empty scheduler")
{
    struct Node *s = _create_test_scheduler(NULL, 0);
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({ Scheduler_clear(&s); }, 0, 0, 0, 0);
    ASSERT_NULL(s);
}

// Verifies that clearing a scheduler with multiple tasks frees all nodes and makes it empty.
TEST_CASE(Scheduler_clear, "Clear a multi-task scheduler with 3 tasks")
{
    struct Task tasks[] = {{1, "T1", 10}, {2, "T2", 20}, {3, "T3", 30}};
    struct Node *s = _create_test_scheduler(tasks, 3);

    UT_mark_memory_as_baseline();
    ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({ Scheduler_clear(&s); }, 0, 3, 0, 3 * sizeof(struct Node));

    ASSERT_NULL(s);
}

/*============================================================================*/
/* TEST SUITE: Scheduler_print                                                */
/*============================================================================*/
// Checks that printing an empty scheduler produces the correct, minimal output.
TEST_CASE(Scheduler_print, "Prints an empty scheduler correctly")
{
    struct Node *s = _create_test_scheduler(NULL, 0);
    ASSERT_STDOUT_EQUAL({ Scheduler_print(s); }, "Scheduler()");
}

// Checks that a scheduler with one task is printed in the correct multi-line format.
TEST_CASE(Scheduler_print, "Prints a single-task scheduler correctly")
{
    UT_disable_leak_check();
    struct Task tasks[] = {{1, "T1", 10}};
    struct Node *s = _create_test_scheduler(tasks, 1);
    const char *expected_output = "Scheduler(\n  Task(ID: 1, Name: T1, Quantum: 10)\n)";
    ASSERT_STDOUT_EQUAL({ Scheduler_print(s); }, expected_output);
}

// Checks that a scheduler with multiple tasks is printed correctly, with commas separating tasks.
TEST_CASE(Scheduler_print, "Prints a multi-task scheduler with 2 tasks correctly")
{
    UT_disable_leak_check();
    struct Task tasks[] = {{1, "T1", 10}, {2, "T2", 20}};
    struct Node *s = _create_test_scheduler(tasks, 2);
    const char *expected_output = "Scheduler(\n  Task(ID: 1, Name: T1, Quantum: 10),\n  Task(ID: 2, Name: T2, Quantum: 20)\n)";
    ASSERT_STDOUT_EQUAL({ Scheduler_print(s); }, expected_output);
}

// Verifies that the print function does not modify the scheduler.
TEST_CASE(Scheduler_print, "Print function does not modify the scheduler and does not allocate/free memory")
{
    struct Task tasks[] = {{1, "T1", 10}, {2, "T2", 20}};
    struct Node *s = _create_test_scheduler(tasks, 2);
    struct Node *copy = _create_test_scheduler(tasks, 2);
    size_t initial_bytes_allocated = UT_total_bytes_allocated;
    size_t initial_bytes_freed = UT_total_bytes_freed;
    UT_mark_memory_as_baseline();
    ASSERT_STDOUT_EQUAL({ Scheduler_print(s); }, "Scheduler(\n  Task(ID: 1, Name: T1, Quantum: 10),\n  Task(ID: 2, Name: T2, Quantum: 20)\n)");
    EQUAL_SIZE_T(0, UT_total_bytes_allocated - initial_bytes_allocated); // No memory change
    EQUAL_SIZE_T(0, UT_total_bytes_freed - initial_bytes_freed);         // No memory change
    EQUAL_SCHEDULER(copy, s); // Ensure they are equal before first call
    EQUAL_STRING("T1", s->p_next->task.name); // Check first task remains unchanged
    EQUAL_STRING("T2", s->p_next->p_next->task.name);
    EQUAL_STRING("T1", s->p_next->p_next->p_next->task.name); // Circular link check
}

TEST_CASE(Scheduler_print, "Print should not allocate or free memory")
{
    struct Task tasks[] = {{1, "T1", 10}, {2, "T2", 20}};
    struct Node *s = _create_test_scheduler(tasks, 2);
    struct Node *copy = _create_test_scheduler(tasks, 2);

    UT_mark_memory_as_baseline();

    SILENT_ASSERT_AND_MARK_MEMORY_CHANGES_BYTES({
        Scheduler_print(s);        
    }, 0, 0, 0, 0); 

    EQUAL_SCHEDULER(copy, s); // Ensure they are equal before first call
    EQUAL_STRING("T1", s->p_next->task.name); // Check first task remains unchanged
    EQUAL_STRING("T2", s->p_next->p_next->task.name);
    EQUAL_STRING("T1", s->p_next->p_next->p_next->task.name); // Circular link check
}

/*============================================================================*/
/* MAIN FUNCTION                                                              */
/*============================================================================*/
int runAllTests(int argc, char *argv[])
{
    return UT_RUN_ALL_TESTS();
}