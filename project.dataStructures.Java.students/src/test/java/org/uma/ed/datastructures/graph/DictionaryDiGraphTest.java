package org.uma.ed.datastructures.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.uma.ed.datastructures.set.JDKHashSet;
import org.uma.ed.datastructures.set.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test cases for class DictionaryDiGraph")
class DictionaryDiGraphTest {

  private DiGraph<Integer> graph;

  @BeforeEach
  void setup() {
    graph = DictionaryDiGraph.empty();
  }

  @Nested
  @DisplayName("A DictionaryDiGraph is created")
  class CreationTests {
    @Test
    @DisplayName("by calling the empty() factory method")
    void emptyFactory() {
      assertNotNull(graph);
      assertTrue(graph.isEmpty());
      assertEquals(0, graph.numberOfVertices());
      assertEquals(0, graph.numberOfEdges());
    }

    @Test
    @DisplayName("from vertices and edges using the of() factory method")
    void fromOfFactory() {
      Set<Integer> vertices = org.uma.ed.datastructures.set.HashSet.of(1, 2, 3);
      Set<DiEdge<Integer>> edges = org.uma.ed.datastructures.set.HashSet.of(DiEdge.of(1, 2), DiEdge.of(2, 3));
      DiGraph<Integer> g = DictionaryDiGraph.of(vertices, edges);

      assertEquals(3, g.numberOfVertices());
      assertEquals(2, g.numberOfEdges());
      assertTrue(g.successors(1).contains(2));
      assertFalse(g.successors(2).contains(1)); // Check directionality
    }

    @Test
    @DisplayName("as a copy of another digraph using copyOf()")
    void copyOfAnotherGraph() {
      graph.addVertex(1);
      graph.addVertex(2);
      graph.addDiEdge(1, 2);

      DiGraph<Integer> copiedGraph = DictionaryDiGraph.copyOf(graph);

      assertEquals(graph.numberOfVertices(), copiedGraph.numberOfVertices());
      assertEquals(graph.numberOfEdges(), copiedGraph.numberOfEdges());
      assertEquals(graph.vertices(), copiedGraph.vertices());
      assertEquals(graph.edges(), copiedGraph.edges());
      assertNotSame(graph, copiedGraph);
    }
  }

  @Nested
  @DisplayName("Vertex operations")
  class VertexTests {
    @Test
    @DisplayName("addVertex() adds a new vertex")
    void addVertex() {
      graph.addVertex(1);
      assertFalse(graph.isEmpty());
      assertEquals(1, graph.numberOfVertices());
      assertTrue(graph.vertices().contains(1));
    }

    @Test
    @DisplayName("addVertex() has no effect if the vertex already exists")
    void addExistingVertex() {
      graph.addVertex(1);
      graph.addVertex(1);
      assertEquals(1, graph.numberOfVertices());
    }

    @Test
    @DisplayName("deleteVertex() removes a vertex and all its incident edges")
    void deleteVertexRemovesIncidentEdges() {
      graph.addVertex(1);
      graph.addVertex(2);
      graph.addVertex(3);
      graph.addDiEdge(1, 2);
      graph.addDiEdge(2, 3);
      graph.addDiEdge(3, 1);

      graph.deleteVertex(2);

      assertEquals(2, graph.numberOfVertices());
      assertEquals(1, graph.numberOfEdges()); // Only 3 -> 1 should remain
      assertFalse(graph.vertices().contains(2));
      assertFalse(graph.successors(1).contains(2));
      assertFalse(graph.predecessors(3).contains(2));
    }
  }

  @Nested
  @DisplayName("Edge operations")
  class EdgeTests {
    @BeforeEach
    void setupVertices() {
      graph.addVertex(1);
      graph.addVertex(2);
      graph.addVertex(3);
    }

    @Test
    @DisplayName("addDiEdge() adds a directed edge")
    void addDiEdge() {
      graph.addDiEdge(1, 2);
      assertEquals(1, graph.numberOfEdges());
      assertTrue(graph.successors(1).contains(2));
      assertFalse(graph.successors(2).contains(1)); // Asymmetric
      assertTrue(graph.predecessors(2).contains(1));
      assertFalse(graph.predecessors(1).contains(2));
    }

    @Test
    @DisplayName("addDiEdge() has no effect if the edge already exists")
    void addExistingDiEdge() {
      graph.addDiEdge(1, 2);
      graph.addDiEdge(1, 2);
      assertEquals(1, graph.numberOfEdges());
    }

    @Test
    @DisplayName("deleteDiEdge() removes an existing directed edge")
    void deleteDiEdge() {
      graph.addDiEdge(1, 2);
      graph.deleteDiEdge(1, 2);
      assertEquals(0, graph.numberOfEdges());
      assertFalse(graph.successors(1).contains(2));
      assertFalse(graph.predecessors(2).contains(1));
    }

    @Test
    @DisplayName("deleteDiEdge() does not remove the reverse edge")
    void deleteDiEdgeDoesNotRemoveReverse() {
      graph.addDiEdge(1, 2);
      graph.addDiEdge(2, 1);
      graph.deleteDiEdge(1, 2);
      assertEquals(1, graph.numberOfEdges());
      assertTrue(graph.successors(2).contains(1));
    }
  }

  @Nested
  @DisplayName("Query operations: successors(), predecessors(), inDegree(), outDegree()")
  class QueryTests {
    @BeforeEach
    void setupGraph() {
      graph.addVertex(1);
      graph.addVertex(2);
      graph.addVertex(3);
      graph.addDiEdge(1, 2);
      graph.addDiEdge(1, 3);
      graph.addDiEdge(2, 3);
    }

    @Test
    @DisplayName("successors() returns the correct set of destination vertices")
    void successorsReturnsCorrectSet() {
      Set<Integer> expectedSuccessors = JDKHashSet.of(2, 3);
      Set<Integer> actualSuccessors = JDKHashSet.empty();
      graph.successors(1).iterator().forEachRemaining(actualSuccessors::insert);
      assertEquals(expectedSuccessors, actualSuccessors);
    }

    @Test
    @DisplayName("predecessors() returns the correct set of source vertices")
    void predecessorsReturnsCorrectSet() {
      Set<Integer> expectedPredecessors = JDKHashSet.of(1, 2);
      Set<Integer> actualPredecessors = JDKHashSet.empty();
      graph.predecessors(3).iterator().forEachRemaining(actualPredecessors::insert);
      assertEquals(expectedPredecessors, actualPredecessors);
    }

    @Test
    @DisplayName("outDegree() returns the correct number of outgoing edges")
    void outDegreeReturnsCorrectCount() {
      assertEquals(2, graph.outDegree(1));
      assertEquals(1, graph.outDegree(2));
      assertEquals(0, graph.outDegree(3));
    }

    @Test
    @DisplayName("inDegree() returns the correct number of incoming edges")
    void inDegreeReturnsCorrectCount() {
      assertEquals(0, graph.inDegree(1));
      assertEquals(1, graph.inDegree(2));
      assertEquals(2, graph.inDegree(3));
    }
  }

  @Nested
  @DisplayName("Exception handling")
  class ExceptionTests {
    @Test
    @DisplayName("addDiEdge() throws GraphException if a vertex does not exist")
    void addEdgeWithNonExistingVertex() {
      graph.addVertex(1);
      Exception e1 = assertThrows(GraphException.class, () -> graph.addDiEdge(1, 99));
      assertEquals("addDiEdge: destination vertex 99 is not in the graph.", e1.getMessage());

      Exception e2 = assertThrows(GraphException.class, () -> graph.addDiEdge(99, 1));
      assertEquals("addDiEdge: source vertex 99 is not in the graph.", e2.getMessage());
    }

    @Test
    @DisplayName("successors() throws GraphException if the vertex does not exist")
    void successorsOfNonExistingVertex() {
      assertThrows(GraphException.class, () -> graph.successors(99));
    }

    @Test
    @DisplayName("predecessors() throws GraphException if the vertex does not exist")
    void predecessorsOfNonExistingVertex() {
      assertThrows(GraphException.class, () -> graph.predecessors(99));
    }

    @Test
    @DisplayName("inDegree() throws GraphException if the vertex does not exist")
    void inDegreeOfNonExistingVertex() {
      assertThrows(GraphException.class, () -> graph.inDegree(99));
    }

    @Test
    @DisplayName("outDegree() throws GraphException if the vertex does not exist")
    void outDegreeOfNonExistingVertex() {
      assertThrows(GraphException.class, () -> graph.outDegree(99));
    }
  }
}