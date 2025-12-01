
package dataStructures.tree.binary;

/**
 * Minimal binary tree utility class with a nested Node<E> type and a size helper.
 * Used by the exam solutions for Questions B and C.
 */
public class BT {

    public static class Node<E> {
        private E element;
        private Node<E> left;
        private Node<E> right;

        public Node(E element, Node<E> left, Node<E> right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }

        public E getElement() {
            return element;
        }

        public Node<E> getLeft() {
            return left;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setLeft(Node<E> left) {
            this.left = left;
        }

        public void setRight(Node<E> right) {
            this.right = right;
        }
    }

    /** Helper method mentioned in Question C: size of a subtree. */
    public static <E> int size(Node<E> node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.getLeft()) + size(node.getRight());
    }

    /** Convenience constructor for tests. */
    public static <E> Node<E> node(E elem, Node<E> left, Node<E> right) {
        return new Node<>(elem, left, right);
    }
}
