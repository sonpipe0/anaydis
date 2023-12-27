package anaydis.immutable;

import org.jetbrains.annotations.NotNull;

public class Node<T> implements List<T>{
    private final List<T> tail;
    T value;

    public Node(T head, List<T> tail) {
        this.value = head;
        this.tail = tail;

    }

    @Override
    public T head() {
        return value;
    }

    @Override
    public @NotNull List<T> tail() {
        return tail;
    }

    @Override
    public boolean isEmpty() {
        return tail == List.nil() && value == null;
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        List<T> current = this;

        while (current != List.nil()) {
            stringBuilder.append(current.head());
            current = current.tail();

            if (current != List.nil()) {
                stringBuilder.append(", ");
            }
        }

        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public @NotNull List<T> reverse() {
        return reversed(this,List.nil());
    }

    private List<T> reversed(List<T> node, List<T> prev){
        if(node.isEmpty()) return prev;
        else {
            return reversed(node.tail(),List.cons(node.head(),prev));
        }
    }
}
