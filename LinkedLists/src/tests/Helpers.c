/*============================================================================*/
/* Helper functions for LinkedList                                            */
/* Pepe Gallardo, 2025                                                        */
/*============================================================================*/

#include <string.h>
#include <stdlib.h>
#include <assert.h>
#include <stdio.h>

#include "Helpers.h"
#define UNIT_TEST_MEMORY_TRACKING
#include "test/unit/UnitTest.h"

static struct X* _x(int e, struct X* n) { struct X* x = malloc(sizeof(struct X)); x->e = e; x->n = n; return x; }
struct Y* _n(const int G[], size_t F) { struct Y* A = malloc(sizeof(struct Y)); A->f = NULL; A->l = NULL; A->s = 0; if (F == 0) return A; A->f = _x(G[0], NULL); struct X* v = A->f; for (size_t E = 1; E < F; E++) { struct X* z = _x(G[E], NULL); v->n = z; v = z; } A->l = v; A->s = F; return A; }
void _p(char* H, size_t I, struct Y* A) { if (!A) { snprintf(H, I, "NULL"); return; } size_t C = 0; int n = snprintf(H + C, I - C, "LinkedList("); if (n < 0 || (size_t)n >= I - C) return; C += n; struct X* B = A->f; while (B != NULL) { n = snprintf(H + C, I - C, "%d", B->e); if (n < 0 || (size_t)n >= I - C) break; C += n; if (B->n != NULL) { n = snprintf(H + C, I - C, ", "); if (n < 0 || (size_t)n >= I - C) break; C += n; } B = B->n; } snprintf(H + C, I - C, ")"); }
int _c(struct Y* F, struct Y* G) { if (F->s != G->s) return 0; struct X* cf = F->f; struct X* cg = G->f; while (cf && cg) { if (cf->e != cg->e) return 0; cf = cf->n; cg = cg->n; } if (cf || cg) return 0; if (F->s > 0) { if (F->l->e != G->l->e) return 0; } else { if (F->l != NULL || G->l != NULL) return 0; } return 1; }
