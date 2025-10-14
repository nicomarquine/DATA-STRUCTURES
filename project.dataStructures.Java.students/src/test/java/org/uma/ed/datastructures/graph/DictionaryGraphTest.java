package org.uma.ed.datastructures.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.uma.ed.datastructures.set.JDKHashSet;
import org.uma.ed.datastructures.set.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test cases for class DictionaryGraph")
class DictionaryGraphTest {

  private Graph<Integer> graph;

  @BeforeEach
  void setup() {
    graph = DictionaryGraph.empty();
  }

  @Nested
  @DisplayName("A DictionaryGraph is created")
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
      Set<Integer> vertices = JDKHashSet.of(1, 2, 3);
      Set<Edge<Integer>> edges = JDKHashSet.of(Edge.of(1, 2), Edge.of(2, 3));
      Graph<Integer> g = DictionaryGraph.of(vertices, edges);

      assertEquals(3, g.numberOfVertices());
      assertEquals(2, g.numberOfEdges());
      assertTrue(g.successors(1).contains(2));
      assertTrue(g.successors(2).contains(1));
    }

    @Test
    @DisplayName("as a copy of another graph using copyOf()")
    void copyOfAnotherGraph() {
      graph.addVertex(1);
      graph.addVertex(2);
      graph.addEdge(1, 2);

      Graph<Integer> copiedGraph = DictionaryGraph.copyOf(graph);

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
    @DisplayName("addVertex() adds a new vertex and increases vertex count")
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
    @DisplayName("deleteVertex() removes an existing vertex and decreases vertex count")
    void deleteExistingVertex() {
      graph.addVertex(1);
      graph.addVertex(2);
      graph.deleteVertex(1);
      assertEquals(1, graph.numberOfVertices());
      assertFalse(graph.vertices().contains(1));
    }

    @Test
    @DisplayName("deleteVertex() has no effect if the vertex does not exist")
    void deleteNonExistingVertex() {
      graph.addVertex(1);
      graph.deleteVertex(2);
      assertEquals(1, graph.numberOfVertices());
    }

    @Test
    @DisplayName("deleteVertex() also removes all incident edges")
    void deleteVertexRemovesEdges() {
      graph.addVertex(1);
      graph.addVertex(2);
      graph.addVertex(3);
      graph.addEdge(1, 2);
      graph.addEdge(1, 3);

      graph.deleteVertex(1);

      assertEquals(0, graph.numberOfEdges());
      assertEquals(0, graph.degree(2));
      assertEquals(0, graph.degree(3));
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
    @DisplayName("addEdge() adds an edge between two existing vertices")
    void addEdge() {
      graph.addEdge(1, 2);
      assertEquals(1, graph.numberOfEdges());
      assertTrue(graph.successors(1).contains(2));
      assertTrue(graph.successors(2).contains(1));
      assertEquals(1, graph.degree(1));
      assertEquals(1, graph.degree(2));
    }

    @Test
    @DisplayName("addEdge() has no effect if the edge already exists")
    void addExistingEdge() {
      graph.addEdge(1, 2);
      graph.addEdge(2, 1); // Same edge, different order
      assertEquals(1, graph.numberOfEdges());
    }

    @Test
    @DisplayName("deleteEdge() removes an existing edge")
    void deleteEdge() {
      graph.addEdge(1, 2);
      graph.deleteEdge(1, 2);
      assertEquals(0, graph.numberOfEdges());
      assertFalse(graph.successors(1).contains(2));
      assertFalse(graph.successors(2).contains(1));
    }

    @Test
    @DisplayName("deleteEdge() works with vertices in reverse order")
    void deleteEdgeReverseOrder() {
      graph.addEdge(1, 2);
      graph.deleteEdge(2, 1);
      assertEquals(0, graph.numberOfEdges());
    }

    @Test
    @DisplayName("deleteEdge() has no effect if the edge does not exist")
    void deleteNonExistingEdge() {
      graph.addEdge(1, 2);
      graph.deleteEdge(1, 3);
      assertEquals(1, graph.numberOfEdges());
    }
  }

  @Nested
  @DisplayName("Query operations: successors() and degree()")
  class QueryTests {
    @BeforeEach
    void setupGraph() {
      graph.addVertex(1);
      graph.addVertex(2);
      graph.addVertex(3);
      graph.addEdge(1, 2);
      graph.addEdge(1, 3);
    }

    @Test
    @DisplayName("successors() returns the correct set of neighbors")
    void successorsReturnsNeighbors() {
      Set<Integer> expectedSuccessors = JDKHashSet.of(2, 3);
      assertEquals(expectedSuccessors, graph.successors(1));
    }

    @Test
    @DisplayName("successors() returns an empty set for a vertex with no edges")
    void successorsForIsolatedVertex() {
      graph.addVertex(4);
      assertTrue(graph.successors(4).isEmpty());
    }

    @Test
    @DisplayName("degree() returns the correct number of incident edges")
    void degreeReturnsCorrectCount() {
      assertEquals(2, graph.degree(1));
      assertEquals(1, graph.degree(2));
      assertEquals(1, graph.degree(3));
    }

    @Test
    @DisplayName("degree() returns 0 for an isolated vertex")
    void degreeForIsolatedVertex() {
      graph.addVertex(4);
      assertEquals(0, graph.degree(4));
    }
  }

  @Nested
  @DisplayName("Exception handling")
  class ExceptionTests {
    @Test
    @DisplayName("addEdge() throws GraphException if a vertex does not exist")
    void addEdgeWithNonExistingVertex() {
      graph.addVertex(1);
      Exception e1 = assertThrows(GraphException.class, () -> graph.addEdge(1, 99));
      assertEquals("addEdge: vertex 99 is not in the graph.", e1.getMessage());

      Exception e2 = assertThrows(GraphException.class, () -> graph.addEdge(99, 1));
      assertEquals("addEdge: vertex 99 is not in the graph.", e2.getMessage());
    }

    @Test
    @DisplayName("successors() throws GraphException if the vertex does not exist")
    void successorsOfNonExistingVertex() {
      Exception e = assertThrows(GraphException.class, () -> graph.successors(99));
      assertEquals("successors: vertex 99 is not in the graph.", e.getMessage());
    }

    @Test
    @DisplayName("degree() throws GraphException if the vertex does not exist")
    void degreeOfNonExistingVertex() {
      Exception e = assertThrows(GraphException.class, () -> graph.degree(99));
      assertEquals("degree: vertex 99 is not in the graph.", e.getMessage());
    }
  }
}