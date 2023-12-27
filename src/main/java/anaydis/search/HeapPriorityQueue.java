package anaydis.search;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class HeapPriorityQueue<K> extends AbstractPriorityQueue<K> implements PriorityQueue<K>{
    private K[] keys;
    private int size;
    private final Comparator<K> cmp;
    @SuppressWarnings("unchecked")
    public HeapPriorityQueue( Comparator<K> cmp){
        this.cmp = cmp;
        keys = (K[]) new Object[25];
        size = 0;

    }

    @Override
    public void insert(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null key cannot be inserted.");
        }
        keys[++size] = key;
        swim(size);
        if((int) (keys.length*0.8) <= size) resize(keys.length*2);
    }

    @Override
    public K pop() {
        if(isEmpty()) throw new NoSuchElementException("Empty Priority Queue");
        K result = keys[1];
        exch(1, size--,keys);
        keys[size+1] = null;
        sink(1);
        if (size > 0 && size <= keys.length / 4) {
            resize(keys.length / 2);
        }
        return result;
    }

    @Override
    public K peek() {
        if (isEmpty()) throw new NoSuchElementException("Empty priority Queue");
        return keys[1];
    }

    @Override
    public int size() {
        return size;
    }

    private void swim(int k) {
        int pos = k;
        while (pos > 1 && less(keys[pos / 2], keys[pos],cmp)) {
            exch(pos / 2, pos,keys);
            pos /= 2;
        }
    }
    private void sink(int k) {
        int pos = k;
        while (2 * pos <= size) {
            int j = 2 * pos;
            if (j < size && less(keys[j], keys[j + 1],cmp)) j++;
            if (!less(keys[pos], keys[j],cmp)) break;
            exch(pos, j,keys);
            pos = j;
        }
    }
    @SuppressWarnings("unchecked")
    private void resize(int cap){
        K[] keyResult = (K[]) new Object[cap];
        for(int i=0;i<=size;i++){
            keyResult[i] = keys[i];
        }
        keys = keyResult;
    }

    @NotNull
    @Override
    public Iterator<K> iterator() {
        return Arrays.asList(Arrays.copyOfRange(keys, 1, size + 1)).iterator();
    }
    public void clear(){
        keys = (K[]) new Object[25];
        size = 0;
    }
    public PriorityType getType(){
        return PriorityType.HEAP;
    }
}
