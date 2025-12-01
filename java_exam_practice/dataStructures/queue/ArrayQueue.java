package dataStructures.queue;

import java.util.NoSuchElementException;

/**
 * Cola FIFO simple implementada con un array dinámico circular.
 * @param <E> tipo de los elementos almacenados.
 */
public class ArrayQueue<E> {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    /** Array que guarda los elementos. */
    private E[] elements;

    /** Índice del primer elemento (frente de la cola). */
    private int first;

    /** Índice del último elemento (final de la cola). */
    private int last;

    /** Número de elementos en la cola. */
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayQueue(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be > 0");
        }
        elements = (E[]) new Object[initialCapacity];
        size = 0;
        first = 0;
        // Por convenio, cuando está vacía, last apunta al índice anterior a first
        last = elements.length - 1;
    }

    public ArrayQueue() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /** ¿Está vacía la cola? */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Número de elementos en la cola. */
    public int size() {
        return size;
    }

    /** Avanza un índice en el array circular. */
    private int advance(int index) {
        return (index + 1) % elements.length;
    }

    /** Asegura capacidad para un elemento más, redimensionando si hace falta. */
    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        if (size == elements.length) {
            E[] newElements = (E[]) new Object[2 * elements.length];

            int current = first;
            for (int i = 0; i < size; i++) {
                newElements[i] = elements[current];
                current = advance(current);
            }

            elements = newElements;
            first = 0;
            last = size - 1;
        }
    }

    /** Encola un elemento al final. */
    public void enqueue(E element) {
        ensureCapacity();
        last = advance(last);
        elements[last] = element;
        size++;
    }

    /** Devuelve el primero sin quitarlo. */
    public E first() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return elements[first];
    }

    /** Desencola el primero y lo devuelve. */
    public E dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        E elem = elements[first];
        elements[first] = null; // ayuda al GC
        first = advance(first);
        size--;

        // Opcional: normalizar índices cuando se queda vacía
        if (size == 0) {
            first = 0;
            last = elements.length - 1;
        }

        return elem;
    }

    /** Vacía completamente la cola. */
    public void clear() {
        // Si quieres ser fino, puedes poner todo a null, pero para el examen no hace falta.
        size = 0;
        first = 0;
        last = elements.length - 1;
    }
}
