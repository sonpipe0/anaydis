package anaydis.search;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BinaryTrieMap<V> extends AbstractMap<String,V>{

    private Node<V> head = null;
    private int size;
    private V putValue;

    @Override
    public int size() {
        return size;
    }


    private static class Node<V>{
        String key;
        V value;
        Node<V> left;
        Node<V> right;
        Node(String key, V value){
            this.key = key;
            this.value = value;
        }

        private boolean isLeaf(){
            return this.left == null && this.right ==null;
        }
    }

    @Override
    public boolean containsKey(@NotNull String key) {
        return find(head,key,0)!= null;
    }

    @Override
    public V get(@NotNull String key) {
        Node<V> node = find(head,key,0);
        if(node != null){
            notifyHit();
            return node.value;
        }
        else {
            notifyMiss();
            return null;
        }
    }

    private Node<V> find(Node<V> node, String key,int level){
        if(node == null) return null;
        if(node.isLeaf()) return key.equals(node.key)  ? node: null;
        return bitAt(key,level)? find(node.right,key,level+1) : find(node.left,key,level+1);
    }

    private boolean bitAt(String s, int nth) {
        final int pos = nth / 8;
        return pos < s.length() && (s.charAt(pos) >> (nth % 8) & 1) != 0;
    }

    @Override
    public V put(@NotNull String key, V value) {
        head = put(head,new Node<>(key,value),0);
        V var = putValue;
        putValue = null;
        return var;
    }
    private Node<V> put(Node<V> node,@NotNull Node<V> value,int level){
        if(node == null) {
            size++;
            return value;
        }
        if(node.isLeaf()){
            if (node.key.equals(value.key)){
                putValue = node.value;
                node.value = value.value;
                return node;
            }
            else return split(value,node,level);
        }
        else {
            if(bitAt(value.key, level)) node.right = put(node.right,value,level+1);
            else node.left = put(node.left,value,level+1);
            return node;
        }
    }

    private int toInt(boolean a){
        return a? 1:0;
    }

    private Node<V> split(@NotNull Node<V> value,@NotNull Node<V> node, int level) {
        Node<V> result = new Node<>("",null);
        switch (toInt(bitAt(value.key, level))*2+toInt(bitAt(node.key, level))){
            case (0):
                result.left = split(value,node,level+1);
                break;
            case (3):
                result.right = split(value, node, level+1);
                break;
            case (1):
                result.left = value;
                result.right = node;
                size++;
                break;
            case (2):
                result.left = node;
                result.right= value;
                size++;
                break;
        }
        return result;

    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    private Iterable<String> collect(){
        Queue<String> queue = new LinkedList<>();
        inOrder(queue,head);
        return queue;

    }
    private void inOrder(Queue<String> queue, Node<V> node){
        if(node == null) return;

        inOrder(queue,node.left);
        if(!Objects.equals(node.key, "")) queue.add(node.key);
        inOrder(queue,node.right);



    }

    @Override
    public Iterator<String> keys() {
        return collect().iterator();
    }
}