/*============================================================================*/
/* MAIN FUNCTION                                                              */
/*============================================================================*/

/*============================================================================*/
/* Execution modes:                                                           */
/* Define either RUN_TESTS or RUN_DEMO to run the corresponding functionality.*/
/* Notice that only one mode can be defined (#define) at a time and the other */
/* must be undefined (#undef)                                                 */
/*============================================================================*/

#define RUN_DEMO
#undef RUN_TESTS


#ifdef __GNUC__
#pragma GCC diagnostic push
#pragma GCC diagnostic ignored "-Wunused-parameter"
#endif

// Forward declarations for the main functions of each mode
int runAllTests(int argc, char* argv[]);
int runDemo(void);

int main(int argc, char* argv[]) {
    #if defined(RUN_TESTS) && defined(RUN_DEMO)
    #error "Both test and demo modes cannot be defined at the same time."
    #elif !defined(RUN_TESTS) && !defined(RUN_DEMO)
    #error "No execution mode defined: define either RUN_TESTS or RUN_DEMO."
    #elif defined(RUN_TESTS)
    // Run the comprehensive unit test suite
    return runAllTests(argc, argv);
    #elif defined(RUN_DEMO)
    // Run the simple demonstration program
    return runDemo();
    #endif
}

#ifdef __GNUC__
#pragma GCC diagnostic pop
#endif