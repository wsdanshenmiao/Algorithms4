/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item data;
        Node pre = null;
        Node next = null;

        Node(Item data) {
            this.data = data;
        }

        Node(Item data, Node pre, Node next) {
            this.data = data;
            this.pre = pre;
            this.next = next;
        }
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item ret = current.data;
            current = current.next;
            return ret;
        }
    }

    private int size = 0;
    private Node first = null;
    private Node last = null;

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size <= 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node preFirst = first;
        first = new Node(item, null, preFirst);
        if (preFirst != null) {
            preFirst.pre = first;
        }
        else {
            last = first;
        }
        ++size;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node preLast = last;
        last = new Node(item, preLast, null);
        if (preLast != null) {
            preLast.next = last;
        }
        else {
            first = last;
        }
        ++size;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node remove = first;
        first = first.next;
        if (first != null) {
            first.pre = null;
        }
        else {
            last = first;
        }
        remove.pre = null;
        remove.next = null;
        --size;
        return remove.data;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node remove = last;
        last = last.pre;
        if (last != null) {
            last.next = null;
        }
        else {
            first = last;
        }
        remove.pre = null;
        remove.next = null;
        --size;
        return remove.data;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
    }
}
