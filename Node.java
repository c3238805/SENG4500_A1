
/*  
    /============================\
    |  SENG4500 Assignment 1     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */
public class Node<T> {

    private T data;       
    private Node<T> next;
    private Node<T> prev;

    Node() {
        next = null;
        prev = null;
        data = null;
    }

    Node(T d) {       
        data = d;
        next = prev = null;
    }

    public void setNext(Node<T> n) {
        next = n;
    }

    public void setPrev(Node<T> p) {
        prev = p;
    }

    public Node<T> getNext() {
        return next;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public void setData(T d) {
        data = d;
    }

    public T getData() {
        return data;
    }

}
