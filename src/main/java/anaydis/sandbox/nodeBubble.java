package anaydis.sandbox;

import java.util.Comparator;

import static anaydis.sandbox.Node.create;

public class nodeBubble {
    public <V> Node<V> sorter(Node<V> node, Comparator<V> cmp){
        Node<V> i = node;
        while (i != null || cmp.compare(node.value,node.next.value)>0){
            Node<V> j = node;
            int cmphead =cmp.compare(j.value,j.next.value);
            if (cmphead>0){
                Node<V> temp = j.next;
                j.next = j.next.next;
                temp.next =j;
                node = temp;
            }
            while (j != null){
                if (j.next.next == null)break;
                int val =cmp.compare(j.next.value,j.next.next.value);
                if(val>0)exch(j,j.next,j.next.next);

                j=j.next;
            }
            i = i.next;
        }
        return node;
    }



    private <V> void exch(Node<V> prev,Node<V> a,Node<V> b){
        a.next = b.next;
        prev.next=b;
        b.next = a;
    }

    public static void main(String[] args) {
        Node<Integer> unsorted = create(2,create(1,create(7,create(8,create(3,create(1,null))))));
        System.out.println(unsorted.size());
        nodeBubble sorter = new nodeBubble();
        Node<Integer> sortered = sorter.sorter(unsorted,Comparator.naturalOrder());
        Node<Integer> temp = sortered;
        System.out.println(temp.toString());

    }
}
