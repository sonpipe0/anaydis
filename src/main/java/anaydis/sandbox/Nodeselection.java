package anaydis.sandbox;
import static anaydis.sandbox.Node.create;
import java.util.Comparator;

public class Nodeselection {
    public <T> Node<T> sort(Node<T> list, Comparator<T> comparator) {
        Node<T> result = create(null,list);
        Node<T> out = null;

        while (result.next != null) {
            Node<T> max = fmax(result, comparator);
            Node<T> temp = max.next;
            max.next = temp.next;
            temp.next = out;
            out = temp;
        }

        return out;
    }

    private <T> Node<T> fmax(Node<T> h, Comparator<T> comparator) {
        Node<T> max = h;

        for (Node<T> i = h; i.next != null; i = i.next) {
            int cmp = comparator.compare(i.next.value, max.next.value);
            if (cmp > 0) max = i;
        }

        return max;
    }

    public static void main(String[] args) {
        Node<Integer> list = create(3,create(2,create(7,create(1,create(10,create(3,null))))));
        Node<Integer> sorted = new Nodeselection().sort(list, Comparator.naturalOrder());

        System.out.println(sorted);
    }

}