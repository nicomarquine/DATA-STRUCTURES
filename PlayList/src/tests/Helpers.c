/*============================================================================*/
/* Helper functions for PlayList                                              */
/* Pepe Gallardo, 2025                                                        */
/*============================================================================*/

#include <string.h>
#include <stdlib.h>
#include <assert.h>

#include "Helpers.h"
#define UNIT_TEST_MEMORY_TRACKING
#include "test/unit/UnitTest.h"

static struct X* _x(const char* s, struct X* p, struct X* n) { struct X* x = malloc(sizeof(struct X)); strcpy(x->s, s); x->n = n; x->p = p; return x; }
struct Y* _n(const char* G[], size_t F, size_t P) { struct Y* A = malloc(sizeof(struct Y)); A->f = NULL; A->p = NULL; if (!F) { return A; } A->f = _x(G[0], NULL, NULL); struct X* v = A->f; for (size_t E = 1; E < F; E++) { struct X* z = _x(G[E], v, NULL); v->n = z; v = z; } if (P < F) { struct X* t = A->f; for(size_t i = 0; i < P; i++) { t = t->n; } A->p = t; } return A; }
void _p(char* H, size_t I, struct Y* A) { if (!A || !A->f) { snprintf(H, I, "PlayList()"); return; } size_t C = 0; int n = snprintf(H + C, I - C, "PlayList("); if (n < 0 || (size_t)n >= I - C) return; C += n; struct X* B = A->f; while (B != NULL) { const char* pi = (B == A->p) ? " [*]" : ""; n = snprintf(H + C, I - C, "\n  \"%s\"%s", B->s, pi); if (n < 0 || (size_t)n >= I) break; C += n; B = B->n; } snprintf(H + C, I - C, "\n)"); }
int _c(struct Y* F, struct Y* G) { struct X* cf = F->f; struct X* cg = G->f; while (cf && cg) { if (strcmp(cf->s, cg->s)) { return 0; } cf = cf->n; cg = cg->n; } if (cf || cg) { return 0; } const char* pf = F->p ? F->p->s : NULL; const char* pg = G->p ? G->p->s : NULL; if (!pf && !pg) { return 1; } if (!pf || !pg) { return 0; } if (strcmp(pf, pg)) { return 0; } return 1; }
bool _i(struct Y* A) { struct X* c = A->f; struct X* p = NULL; while (c) { if (c->p != p) return false; p = c; c = c->n; } return true; }