package cs1302.p3;

import cs1302.gen.UrgencyQueue;
import cs1302.gen.Node;
import java.util.function.Consumer;

/**
 * BaseLinkedUrgencyQueue class that implements UrgencyQueue.
 */
public abstract class BaseLinkedUrgencyQueue<Type> implements UrgencyQueue<Type> {

    int size;
    Node<Type> head;

    /**
     * Construct a {@code BaseLinkedUrgencyQueue}. This constructor is never
     * intended to be called directly via {@code new}; instead, it should only
     * be called in child class constructors using {@code super()}.
     */
    public BaseLinkedUrgencyQueue() {
        this.size = 0;
        this.head = null;
    } // BaseLinkedUrgencyQueue

    /**
     * Returns number of items in urgency queue.
     *
     * @return the number of items in this urgency queue
     */
    @Override
    public int size() {
        return size;
    } // size

    /**
     * Retrieves, but does not remove, the most urgent item in this urgency queue.
     *
     * @return the most urgent item in the queue
     * @throws IllegalStateException if there are no items in the queue
     */
    @Override
    public Type peek() {
        if (head == null) {
            throw new IllegalStateException("Queue is empty");
        }
        return head.getItem();
    } // peek

    /**
     * Returns a string representation of this urgency queue. The string representation consists
     * of each item in the queue, from most urgent to least urgent.
     *
     * @return the string representation of this urgency queue
     */
    @Override
    public String toString() {
        String output = "[";
        Node<Type> current = head;
        while (current != null) {
            output += current.getItem().toString();
            current = current.getNext();
            if (current != null) {
                output += ", ";
            }
        }

        output += "]";
        return output;
    } // toString

    /**
     * Retrieves and removes the most urgent item in this urgency queue.
     *
     * @return the most urgent item in the queue.
     * @throws IllegalStateException if there are no items in the queue
     */
    @Override
    public Type dequeue() {
        if (head == null) {
            throw new IllegalStateException("There are no items in the queue.");
        }
        Type item = head.getItem();
        head = head.getNext();
        size--;

        return item;
    } // dequeue

    /**
     * Removes the most urgent item in this urgency queue and performs the
     * given action on the removed item.
     *
     * @param action the action to be performed on the removed item
     * @throws NullPointerException if the specified action is null
     * @throws IllegalStateException if there are no items in the queue
     */
    @Override
    public void dequeue(Consumer<Type> action) {
        if (action == null) {
            throw new NullPointerException("Specified action is null.");
        }
        if (head == null) {
            throw new IllegalStateException("There are no items in the queue.");
        }

        Type item = head.getItem();
        head = head.getNext();
        size--;

        action.accept(item);
    } // dequeue

    /**
     * Removes the most urgent num-many items in this urgency queue and performs the
     * given action on each item in the queue.
     *
     * @param num the number of items to remove
     * @param action the action to be performed on the removed items
     * @throws NullPointerException if the specified action is null
     * @throws IllegalArgumentException if num is less than 0
     * @throws IllegalStateException if num is greater than queue size
     */
    @Override
    public void dequeueMany(int num, Consumer<Type> action) {
        if (action == null) {
            throw new NullPointerException("Specified action is null.");
        }
        if (num < 0) {
            throw new IllegalArgumentException("Num cannot be less than 0.");
        }
        if (num > size) {
            throw new IllegalStateException("Num cannot be greater than queue size.");
        }

        for (int i = 0; i < num; i++) {
            Type item = head.getItem();
            head = head.getNext();
            size--;
            action.accept(item);
        }
    } // dequeueMany

    /**
     * Removes all items from this urgency queue.
     */
    @Override
    public void clear() {
        head = null;
        size = 0;
    } // clear

    /**
     * Enqueues the items contained in the specified Iterable into this urgency queue. This method
     * should return false if the specified Iterable is empty. Otherwise, this method either
     * returns true or throws an exception upon failure.
     *
     * @param items the items to add to this queue
     * @return true if this queue is changed as a result of the call; false otherwise
     * @throws NullPointerException if items is null
     * @throws IllegalArgumentException if any of the other items specified in Iterable are null
     */
    @Override
    public <SubType extends Type> boolean enqueueAll(Iterable<SubType> items) {
        if (items == null) {
            throw new NullPointerException("Items is null.");
        }
        int count = 0;
        for (SubType item : items) {
            if (item == null) {
                throw new IllegalArgumentException("Iterable contains a null item.");
            }
            count++;
        }
        if (count == 0) {
            return false;
        }
        boolean ifChanged = false;
        for (SubType item : items) {
            enqueue(item);
            ifChanged = true;
        }
        return ifChanged;
    } // enqueueAll

    /**
     * Returns an array containing all of the objects in this UrgencyQueue in proper sequence (from
     * first to last element, by urgency). The generator parameter accepts a reference to any method
     * that takes an integer, which is the size of the desired array, and produces an array
     * of the desired size.
     *
     * @param generator a function which produces a new array of the desired type and provided size
     * @return an array containing all of the items in this queue in proper sequence
     * @throws NullPointerException if generator is null
     */
    @Override
    public Type[] toArray(java.util.function.IntFunction<Type[]> generator) {
        if (generator == null) {
            throw new NullPointerException("Generator is null.");
        }

        Type[] newArray = generator.apply(size);
        Node<Type> current = head;
        int index = 0;

        while (current != null) {
            newArray[index++] = current.getItem();
            current = current.getNext();
        }

        return newArray;
    } // toArray

} // BaseLinkedUrgencyQueue<Type>
