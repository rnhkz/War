class RingBuffer<T> {
    private int front;
    private int back;
    private T[] arr;
    private int size;

    RingBuffer(int capacity) {
        front = 0;
        back = 0;
        size = 0;
        arr = (T[])(new Object[capacity]);
    }
    int size()
    {
        return size;
    }

    boolean isEmpty()
    {
        return size == 0;
    }

    boolean isFull()
    {
        return size == arr.length;
    }

    void enqueue(T x) {
        if (isFull()) {
            throw new IllegalStateException("The queue is full.");
        }
        arr[front % arr.length] = x;
        size++;
        front++;
    }

    T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("The queue is empty");
        }
        T temp = arr[back % arr.length];
        //front = (front + 1 ) % arr.length;
        size--;
        back++;
        return temp;
    }

    T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("The queue is empty");
        }
        return arr[front];
    }
}
