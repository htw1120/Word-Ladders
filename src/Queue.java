// Code partially comes from geeksforgeeks.org

class Node<E> {
    E value;
    Node<E> next;

    public Node(E value){
        this.value = value;
        this.next = null;
    }
}
    public class Queue<E> {
    Node<E> front, back;
    private int count;

    public Queue(){
        this.front = null;
        this.back = null;
        this.count = 0;
    }
    void enqueue (E value){
        Node<E> newValue = new Node<>(value);
        if (this.back == null){
            this.front = newValue;
            this.back = newValue;
        }
        this.back.next = newValue;
        this.back = newValue;
        count++;
    }

    public E dequeue(){
        Node<E> returnValue = this.front;
        this.front = this.front.next;
        count--;
        return returnValue.value;
    }
    public boolean isEmpty() {
        return count == 0;
    }
    }