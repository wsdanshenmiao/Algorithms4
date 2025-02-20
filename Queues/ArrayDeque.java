/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDeque<Item> implements Iterable<Item> {

    private class ArrayIterator implements Iterator<Item> {
        private int current = first;

        public boolean hasNext() {
            return current != last;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return data[current++];
        }
    }

    private Item[] data;    // 数组数据
    private int first = 0;
    private int last = 0;

    // construct an empty deque
    public ArrayDeque() {
        data = CreateDeque(1);
    }

    // is the deque empty?
    public boolean isEmpty() {
        return last - first <= 0;
    }

    // return the number of items on the deque
    public int size() {
        return last - first;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        int newSize = size() == 0 ? 1 : 2 * size();
        resize(newSize, false);
        data[--first] = item;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        int newSize = size() == 0 ? 1 : 2 * size();
        resize(newSize, true);
        data[last++] = item;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        shrink();
        Item firstItem = data[first];
        data[first++] = null;
        return firstItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        shrink();
        Item lastItem = data[--last];
        data[last] = null;
        return lastItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private void shrink() {
        if (size() > data.length / 4) return;
        resize(data.length / 2, false);
    }

    // 创建新的队列
    private void resize(int newSize, boolean addLast) {
        if (0 < first && last < data.length) return;
        // 重新调整大小
        Item[] tmp = CreateDeque(newSize);
        int head, tail;
        if (newSize < 4) {
            head = addLast ? 0 : newSize - size();
            tail = addLast ? size() : newSize;
        }
        else {
            // 让所有元素居中
            head = newSize / 4;
            tail = newSize * 3 / 4;
        }
        for (int i = 0; i < size(); ++i) {
            tmp[head + i] = data[first + i];
        }
        first = head;
        last = tail;
        data = tmp;
    }

    private Item[] CreateDeque(int size) {
        return (Item[]) new Object[size];
    }

    // unit testing (required)
    public static void main(String[] args) {
        ArrayDeque<Integer> deque = new ArrayDeque<Integer>();
        for (int i = 0; i < StdRandom.uniformInt(100); ++i) {
            deque.addFirst(i);
        }
        for (int i = 0; i < StdRandom.uniformInt(100); ++i) {
            int item = deque.removeLast();
        }
        for (int i = 0; i < StdRandom.uniformInt(100); ++i) {
            deque.addLast(i + 10);
        }
        for (int i = 0; i < StdRandom.uniformInt(100); ++i) {
            int item = deque.removeFirst();
        }
        for (var item : deque) {
            StdOut.println(item);
        }
    }

}
