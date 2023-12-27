package anaydis.sandbox;

public class Node <V>{
    Node<V> next;
    V value;
    public Node(V value,Node<V> next){
        this.value = value;
        this.next = next;
    }

    public  static <V> Node<V> create(V value,Node<V> next){
        return new Node<>(value,next);

    }

    public int size(){
        return size(this);
    }

    private int size(Node<V> node){
        if(node.next == null)
            return 1;
        else return 1+size(node.next);
    }

    public String toString(){
        return "["+value+"]->"+next;
    }
}
