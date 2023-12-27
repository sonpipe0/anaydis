package anaydis.sandbox;

import java.util.Comparator;
import static anaydis.sandbox.Node.create;
public class mergeSorterTopDownNode {
    private static <V> Node<V> merge(Node<V> low, Node<V> middle, Comparator<V> cmp) {
        Node<V> tempA = low;
        Node<V> tempB = middle;
        Node<V> result = null;
        Node<V> last = null;

        while (tempA != null && tempB != null) {
            if (cmp.compare(tempB.value, tempA.value) < 0) {
                if (last == null) {
                    result = tempB;
                    last = result;
                } else {
                    last.next = tempB;
                    last = last.next;
                }
                tempB = tempB.next;
            } else {
                if (last == null) {
                    result = tempA;
                    last = result;
                } else {
                    last.next = tempA;
                    last = last.next;
                }
                tempA = tempA.next;
            }
        }

        if (tempA != null) {
            last.next = tempA;
        } else {
            last.next = tempB;
        }

        return result;
    }

    public <V> Node<V> sort(Node<V> head, Comparator<V> cmp) {
        return mergeSort(head, cmp);
    }


    private static <V> Node<V> mergeSort(Node<V> head, Comparator<V> cmp) {
        if (head == null || head.next == null) {
            return head;
        }

        Node<V> mid = getMiddle(head);
        Node<V> left = head;
        Node<V> right = mid.next;
        mid.next = null;

        left = mergeSort(left, cmp);
        right = mergeSort(right, cmp);

        return merge(left, right, cmp);
    }

    private static <V> Node<V> getMiddle(Node<V> head) {
        if (head == null) {
            return null;
        }
        Node<V> slow = head;
        Node<V> fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }


    public static void main(String[] args) {
        Node<Integer> list = create(3, create(2, create(7, create(1, create(10, create(3, null))))));
        Node<Integer> sorted = new mergeSorterTopDownNode().sort(list, Comparator.naturalOrder());

        System.out.println(sorted);
    }
}