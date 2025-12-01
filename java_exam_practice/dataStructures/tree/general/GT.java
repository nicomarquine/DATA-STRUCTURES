
package dataStructures.tree.general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Minimal general tree class with a nested Node<E> type, used for Question D.
 */
public class GT {

    public static class Node<E> {
        private E element;
        private List<Node<E>> children;

        public Node(E element) {
            this.element = element;
            this.children = new ArrayList<>();
        }

        public Node(E element, List<Node<E>> children) {
            this.element = element;
            this.children = new ArrayList<>(children);
        }

        public E getElement() {
            return element;
        }

        public List<Node<E>> getChildren() {
            return Collections.unmodifiableList(children);
        }

        public void addChild(Node<E> child) {
            this.children.add(child);
        }

        public void addChildren(List<Node<E>> children) {
            this.children.addAll(children);
        }
    }

    /** Convenience helper to create a node with given children. */
    @SafeVarargs
    public static <E> Node<E> node(E elem, Node<E>... children) {
        Node<E> n = new Node<>(elem);
        for (Node<E> child : children) {
            n.addChild(child);
        }
        return n;
    }
}
