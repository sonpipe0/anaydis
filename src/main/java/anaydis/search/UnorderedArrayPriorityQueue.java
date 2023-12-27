package anaydis.search;

import org.jetbrains.annotations.NotNull;
import anaydis.sort.QuickSorter;

import java.util.*;

public class UnorderedArrayPriorityQueue<K> extends AbstractPriorityQueue<K> implements PriorityQueue<K> {

    private K[] keys;
    private int size;
    private final Comparator<K> cmp;
    @SuppressWarnings("unchecked")
    public UnorderedArrayPriorityQueue( Comparator<K> cmp){
        this.cmp = cmp;
        keys = (K[]) new Object[25];
        size = 0;

    }
    @Override
    public void insert(K key) {
        keys[size++] = key;
        if((int) (keys.length*0.8) <= size) resize(keys.length*2);
    }

    @Override
    public K pop() {
        if((int) (keys.length*0.25) >= size) resize(keys.length /2);
        if(isEmpty()) throw new NoSuchElementException("Empty Queue");
        int max = max();
        exch(max,size-1,keys);
        K value = keys[--size];
        keys[size] = null;
        return value;


    }



    private int max(){
        int max = 0;
        for(int i = 0 ; i<size;i++){
            if(less(keys[max],keys[i],cmp)) max =i;
        }
        return max;
    }
    @SuppressWarnings("unchecked")
    private void resize(int cap){
        K[] keyResult = (K[]) new Object[cap];
        for(int i=0;i<size;i++){
            keyResult[i] = keys[i];
        }
        keys = keyResult;
    }


    @Override
    public K peek() {
        if(isEmpty()) throw new NoSuchElementException("Empty Queue");
        return keys[max()];
    }

    @Override
    public int size() {
        return size;
    }

    @NotNull
    @Override
    public Iterator<K> iterator() {
        QuickSorter quick = new QuickSorter();
        List<K> copy = Arrays.asList(Arrays.copyOfRange(keys,0,size));
        quick.sort(cmp,copy);
        Collections.reverse(copy);
        return copy.iterator();
    }

    public PriorityType getType(){
        return PriorityType.UNORDERED;
    }

    public void clear(){
        keys = (K[]) new Object[25];
        size = 0;
    }
}
