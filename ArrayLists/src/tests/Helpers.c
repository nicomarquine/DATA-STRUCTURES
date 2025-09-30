/*============================================================================*/
/* Helper functions for ArrayList                                             */
/* Pepe Gallardo, 2025                                                        */
/*============================================================================*/

#include <string.h>
#include <stdlib.h>
#include <assert.h>
#include <stdio.h>

#include "Helpers.h"
#define UNIT_TEST_MEMORY_TRACKING
#include "test/unit/UnitTest.h"

struct Y* _n(const int G[], size_t F, size_t C) { struct Y* A = malloc(sizeof(struct Y)); A->c = C > F ? C : F; A->s = F; A->es = malloc(sizeof(int) * A->c); for (size_t i = 0; i < F; i++) A->es[i] = G[i]; return A; }
void _p(char* H, size_t I, struct Y* A) { if (!A) { snprintf(H, I, "NULL"); return; } size_t C = 0; int n = snprintf(H + C, I - C, "ArrayList("); if (n < 0 || (size_t)n >= I - C) return; C += n; for (size_t i = 0; i < A->s; i++) { n = snprintf(H + C, I - C, "%d", A->es[i]); if (n < 0 || (size_t)n >= I - C) break; C += n; if (i < A->s - 1) { n = snprintf(H + C, I - C, ", "); if (n < 0 || (size_t)n >= I - C) break; C += n; } } snprintf(H + C, I - C, ")"); }
int _c(struct Y* F, struct Y* G) { if (F->s != G->s) return 0; if (F->c != G->c && G->c != 0) return 0; for(size_t i = 0; i < F->s; i++) { if (F->es[i] != G->es[i]) return 0; } return 1; }