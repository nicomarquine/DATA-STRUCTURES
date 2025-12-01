
package dataStructures.list;

import java.util.ArrayList;
import java.util.List;

/**
 * Minimal singly linked list class with a nested Node<E> type,
 * just enough to exercise Question A of the exam.
 */
public class LL {

    public static class Node<E> {
        private E element;
        private Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }

        public E getElement() {
            return element;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    }

    /** Utility: build a list from an array of values and return the first node. */
    public static <E> Node<E> fromArray(E[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        Node<E> first = new Node<>(values[0], null);
        Node<E> current = first;
        for (int i = 1; i < values.length; i++) {
            Node<E> next = new Node<>(values[i], null);
            current.setNext(next);
            current = next;
        }
        return first;
    }

    /** Utility: convert the list starting at first into a java.util.List. */
    public static <E> List<E> toList(Node<E> first) {
        List<E> out = new ArrayList<>();
        Node<E> current = first;
        while (current != null) {
            out.add(current.getElement());
            current = current.getNext();
        }
        return out;
    }
}
