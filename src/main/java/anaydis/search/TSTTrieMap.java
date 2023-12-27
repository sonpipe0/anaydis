package anaydis.search;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class TSTTrieMap<V> extends AbstractMap<String,V>{
    private int size;
    private Node<V> head;
    private V prevValue;

    private class Node<V>{
        Node<V> left;
        Node<V> middle;
        Node<V> right;
        char chr;

        V val;

        Node(char chr){
            this.chr = chr;
        }


    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean containsKey(@NotNull String key) {
        return find(head,key,0) != null;
    }
    @Override
    public V get(@NotNull String key) {
        Node<V> result = find(head,key,0);
        if(result != null) {
            notifyHit();
            return result.val;
        }
        else {
            notifyMiss();
            return null;
        }
    }

    private Node<V> find(Node<V> node, String key, int level) {
        if (node == null || level >= key.length()) {
            return null;
        }

        char c = key.charAt(level);
        if (c == node.chr) {
            if (key.length() == level + 1) {
                return node;
            } else {
                return find(node.middle, key, level + 1);
            }
        } else {
            Node<V> next = (c > node.chr) ? node.right : node.left;
            return find(next, key, level);
        }
    }


    @Override
    public V put(@NotNull String key, V value) {
        head = put(head, key,value,0);
        V temp = prevValue;
        prevValue = null;
        return temp;
    }
    private Node<V> put(Node<V> node, String key,V value ,int level){
        if (node == null){
            Node<V> result = new Node<>(key.charAt(level));
            if(level+1 < key.length()){
                result.middle = put(result.middle,key,value,level+1);
            }
            else {
                result.val = value;
                size++;
            }
            return result;
        }
        else if (key.charAt(level) == node.chr) {
            prevValue = node.val;
            if (level + 1 == key.length()) {
                if (node.val == null) size++;
                node.val = value;
            } else {
                node.middle = put(node.middle, key, value, level + 1);
            }
            return node;
        }else if (key.charAt(level)> node.chr){
                node.right = put(node.right,key,value,level);
                return node;
            }
            else {
                node.left = put(node.left, key , value, level);
                return node;
            }

    }
    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public Iterator<String> keys() {
        Queue<String> queue = new LinkedList<>();
        inOrder(head, new StringBuilder(),queue);
        return queue.iterator();
    }
    private void inOrder(Node<V> node, StringBuilder currentKey, Queue<String> queue) {
        if (node == null) {
            return;
        }
        inOrder(node.left, currentKey, queue);
        currentKey.append(node.chr);
        if (node.val != null) {
            queue.add(currentKey.toString());
        }
        inOrder(node.middle, currentKey, queue);
        currentKey.deleteCharAt(currentKey.length() - 1);
        inOrder(node.right, currentKey, queue);
    }

    public List<String> autocomplete(String pattern){
        final List<String> result = new ArrayList<>();
        autocomplete(head,pattern,0,"",result);
        return result;
    }

    private void autocomplete(Node<V> x,String pattern, int level, String match, List<String> result){
        if(x == null) return;
        if(level < pattern.length()){
            if(pattern.charAt(level)>x.chr) autocomplete(x.right,pattern,level,match,result);
            else if(pattern.charAt(level)<x.chr) autocomplete(x.right,pattern,level,match,result);
            else  autocomplete(x.middle,pattern,level+1,match+x.chr,result);
        }
        if(level+1 == pattern.length()&& x.val != null)result.add(match+x.chr);
        if(level >= pattern.length()){
            autocomplete(x.left,pattern,level,match,result);
            match = match+x.chr;
            if(x.val!= null)result.add(match);
            autocomplete(x.middle,pattern,level+1,match,result);
            match=match.substring(0,match.length()-1);
            autocomplete(x.right,pattern,level,match,result);
        }
    }


    public List<String> wildCard(String pattern) {
        final List<String> result = new ArrayList<>();
        wildCard(head, pattern, 0, "", result);
        return result;
    }

    private void wildCard(Node<V> x, String pattern, int level, String match, List<String> result) {
        if (x == null) {
            return;
        }

        char currentChar = pattern.charAt(level);

        if (currentChar == '*' || currentChar < x.chr) wildCard(x.left, pattern, level, match, result);
        if (currentChar == '*' || currentChar == x.chr) {
            String newMatch = match + x.chr;
            if (level == pattern.length()-1) {
                if (x.val != null) {
                    result.add(newMatch);
                }
            }
            else wildCard(x.middle, pattern, level+1, newMatch, result);
        }

        if (currentChar == '*' || currentChar > x.chr) wildCard(x.right, pattern, level, match, result);

    }

    public String longestPrefix(String s){
        String result = null;
        result = longestPrefix(head,0,"",s,result);
        return result;
    }

    private String longestPrefix(Node<V> x,int lvl, String match,String pattern,String result){
        if(x == null)return result;
        if(lvl<pattern.length()){
            char c = pattern.charAt(lvl);
            if(c>x.chr) result = longestPrefix(x.right,lvl,match,pattern,result);
            else if(c<x.chr) result = longestPrefix(x.left,lvl,match,pattern,result);
            else result = longestPrefix(x.middle,lvl+1,match+x.chr,pattern,result);
        }
        else {
            result = longestPrefix(x.left,lvl,match,pattern,result);
            match = match+x.chr;
            if(x.val!= null && result.length()<match.length()){
                result = match;
            }
            result = longestPrefix(x.middle,lvl+1,match,pattern,result);
            match = match.substring(0,match.length()-1);
            result = longestPrefix(x.right,lvl,match,pattern,result);
        }
        return result;
    }

}
