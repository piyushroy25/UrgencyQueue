package cs1302.p3;

import cs1302.gen.UrgencyQueue;
import cs1302.gen.Node;
import java.util.Comparator;

/**
 * CustomLinkedUrgencyQueue class that extends BaseLinkedUrgencyQueue and uses comparator.
 */
public class CustomLinkedUrgencyQueue<Type> extends BaseLinkedUrgencyQueue<Type> {

    private Comparator<Type> comparator;

    /**
     * Construct a {@code CustomLinkedUrgencyQueue}.
     *
     * @param cmp a function that lets you determine the urgency order
     *     between two items
     * @throws NullPointerException if {@code cmp} is {@code null}
     */
    public CustomLinkedUrgencyQueue(Comparator<Type> cmp) {
        super();
        if (cmp == null) {
            throw new NullPointerException("Comparator is null.");
        }
        comparator = cmp;
    } // CustomLinkedUrgencyQueue

    /**
     * Inserts the specified item into this urgency queue based on urgency.
     *
     * @param item the item to insert
     * @return true if this urgency queue was modified as a result of this call
     * @throws NullPointerException if item is null
     */
    @Override
    public boolean enqueue(Type item) {
        if (item == null) {
            throw new NullPointerException("Item is null.");
        }

        int oldSize = size;
        Node<Type> newNode = new Node<>(item);
        if (head == null || comparator.compare(item, head.getItem()) > 0) {
            newNode.setNext(head);
            head = newNode;
            size++;
        } else {
            Node<Type> current = head;
            while (current.getNext() != null &&
                   comparator.compare(item, current.getNext().getItem()) <= 0) {
                current = current.getNext();
            }
            newNode.setNext(current.getNext());
            current.setNext(newNode);
            size++;
        }

        return size > oldSize;
    } // enqueue

    /**
     * Builds and returns a new urgency queue that contains the most urgent num
     * items dequeued from this UrgencyQueue.
     *
     * @param num the number of items to remove and return
     * @return a new urgency queue object containing the most urgent num items dequeued from queue
     * @throws IllegalArgumentException if num is less than 0
     * @throws IllegalStateException if num is greater than queue size
     */
    @Override
    public UrgencyQueue<Type> dequeueMany(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("Num is less than 0.");
        }
        if (num > size) {
            throw new IllegalStateException("Num is greater than queue size.");
        }

        UrgencyQueue<Type> newQueue = new CustomLinkedUrgencyQueue<>(comparator);

        for (int i = 0; i < num; i++) {
            Type item = head.getItem();
            head = head.getNext();
            size--;
            newQueue.enqueue(item);
        }

        return newQueue;
    } // dequeueMany

    /**
     * Builds and returns a new urgency queue consisting of the items of this urgency queue
     * that pass the test specified by the given predicate.
     *
     * @param cond the predicate used to test items of this queue
     * @return a reference to the filtered queue
     * @throws NullPointerException if the specified predicate is null
     */
    @Override
    public UrgencyQueue<Type> filter(java.util.function.Predicate<Type> cond) {
        if (cond == null) {
            throw new NullPointerException("The specified predicate is null.");
        }

        UrgencyQueue<Type> newQueue = new CustomLinkedUrgencyQueue<>(comparator);

        Node<Type> current = head;
        while (current != null) {
            if (cond.test(current.getItem())) {
                newQueue.enqueue(current.getItem());
            }
            current = current.getNext();
        }

        return newQueue;
    } // filter
} // CustomLinkedUrgencyQueue<Type>
