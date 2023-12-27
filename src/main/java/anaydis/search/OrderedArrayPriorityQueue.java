package anaydis.search;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class OrderedArrayPriorityQueue<K> extends AbstractPriorityQueue<K> implements PriorityQueue<K>{

    private K[] keys;
    private int size;
    private final Comparator<K> cmp;
    @SuppressWarnings("unchecked")
    public OrderedArrayPriorityQueue(Comparator<K> cmp){
        this.cmp = cmp;
        keys = (K[]) new Object[25];
        size = 0;

    }

    @Override
    public void insert(K key) {
        keys[size++] = key;
        for(int i = size-1;i>0;i--){
            if(less(keys[i],keys[i-1],cmp)) exch(i,i-1,keys);
            else break;
        }
        if((int) (keys.length*0.8) <= size) resize(keys.length*2);
    }

    @Override
    public K pop() {
        if((int) (keys.length*0.25) >= size) resize(keys.length /2);
        if(isEmpty()) throw new NoSuchElementException("Empty Queue");
        K temp = keys[--size];
        keys[size] = null;
        return temp;
    }

    @Override
    public K peek() {
        if(isEmpty()) throw new NoSuchElementException("Empty Queue");
        return keys[size-1];
    }

    @Override
    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private void resize(int cap){
        K[] keyResult = (K[]) new Object[cap];
        for(int i=0;i<size;i++){
            keyResult[i] = keys[i];
        }
        keys = keyResult;
    }

    @NotNull
    @Override
    public Iterator<K> iterator() {
        List<K> copy = Arrays.asList(Arrays.copyOfRange(keys,0,size));
        Collections.reverse(copy);
        return copy.iterator();
    }

    public PriorityType getType(){
        return PriorityType.ORDERED;
    }

    public void clear(){
        keys = (K[]) new Object[25];
        size = 0;
    }
}
