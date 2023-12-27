package anaydis.immutable;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.Queue;

public class BinaryTree<K,V> implements Map<K,V>{
    private final Comparator<K> cmp;
    private final K key;
    private final V value;
    private final BinaryTree<K, V> left;
    private final BinaryTree<K, V> right;

    public BinaryTree(Comparator<K> cmp) {
        this(cmp, null, null, null, null);
    }

    private BinaryTree(Comparator<K> cmp, K key, V value, BinaryTree<K, V> left, BinaryTree<K, V> right) {
        this.cmp = cmp;
        this.key = key;
        this.value = value;
        this.left = left;
        this.right = right;
    }
    @Override
    public int size() {
        return size(this);
    }
    private int size(BinaryTree<K,V> node){
        if(node == null) return 0;
        if(node.key == null) return 0;
        return 1+size(node.left)+size(node.right);
    }

    @Override
    public boolean containsKey(@NotNull K key) {
        if(isEmpty()) return false;
        return find(this, key) != null;
    }
    private BinaryTree<K,V> find(BinaryTree<K,V> node,K key){
        if (node == null) return null;
        if(cmp.compare(key,node.key)< 0 ) return find(node.left,key);
        else if(cmp.compare(key,node.key)> 0 ) return find(node.right,key);
        else return node;
    }

    @Override
    public V get(@NotNull K key) {
        if(isEmpty()) return null;
        BinaryTree<K,V> node = find(this,key);
        return node != null? node.value:null;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public Map<K, V> put(@NotNull K key, V value) {

        if (this.key == null) {
            return new BinaryTree<>(cmp, key, value, null, null);
        }
        int comp = cmp.compare(key, this.key);

        if (comp < 0) {
            BinaryTree<K, V> newLeft = left != null ? (BinaryTree<K, V>) left.put(key, value) : new BinaryTree<>(cmp, key, value,null,null);
            return new BinaryTree<>(cmp, this.key, this.value, newLeft, right);
        } else if (comp > 0) {
            BinaryTree<K, V> newRight = right != null ? (BinaryTree<K, V>) right.put(key, value) : new BinaryTree<>(cmp, key, value,null,null);
            return new BinaryTree<>(cmp, this.key, this.value, left, newRight);
        } else {
            return new BinaryTree<>(cmp, key, value, left, right);
        }
    }

    @Override
    public Iterator<K> keys() {
        if (isEmpty()) return Collections.emptyIterator();
        ArrayDeque<K> queue = new ArrayDeque<>();
        collect(queue,this);
        return queue.iterator();
    }

    private void collect(ArrayDeque<K> queue, BinaryTree<K,V> node){
        if (node == null) return;
        collect(queue,node.left);
        queue.add(node.key);
        collect(queue,node.right);
    }
}
