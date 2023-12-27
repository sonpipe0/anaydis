package anaydis.search;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class RWayTrieMap<V> extends AbstractMap<String,V> {

    private Node<V> head = null;
    private int size;
    V prevValue;

    private static class Node<V> {
        V value;
        Node<V>[] next;

        @SuppressWarnings("unchecked")
        private Node(V value) {
            this.value = value;
            this.next = new Node[256];
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean containsKey(@NotNull String key) {
        return find(head, key, 0) != null;
    }

    @Override
    public V get(@NotNull String key) {
        Node<V> result = find(head, key, 0);
        if (result != null) {
            notifyHit();
            return result.value;
        } else {
            notifyMiss();
            return null;
        }
    }

    private Node<V> find(Node<V> node, String key, int level) {
        if (node == null) return null;
        if (level == key.length()) return node;
        int next = key.charAt(level);
        return find(node.next[next], key, level + 1);
    }

    @Override
    public V put(@NotNull String key, V value) {
        head = put(head, key, value, 0);
        V temp = prevValue;
        prevValue = null;
        return temp;
    }

    private Node<V> put(Node<V> node, String key, V value, int level) {
        if (node == null) {
            Node<V> result = new Node<>(null);
            if (level < key.length()) {
                int next = key.charAt(level);
                result.next[next] = put(result.next[next], key, value, level + 1);
            } else {
                result.value = value;
                size++;
            }
            return result;
        } else if (level == key.length()) {
            prevValue = node.value;
            node.value = value;
            return node;
        } else {
            int next = key.charAt(level);
            node.next[next] = put(node.next[next], key, value, level + 1);
            return node;
        }
    }


    @Override
    public void clear() {
        head = null;
        size = 0;

    }

    private Iterable<String> collect() {
        Queue<String> queue = new LinkedList<>();
        StringBuilder word = new StringBuilder();
        inOrder(queue, word, head);
        return queue;

    }

    private void inOrder(Queue<String> queue, StringBuilder word, Node<V> node) {
        if (node == null) return;
        if (node.value != null) {
            queue.add(word.toString());
        }
        for (int i = 0; i < 256; i++) {
            if (node.next[i] != null) {
                char character = (char) i;
                word.append(character);
                inOrder(queue, word, node.next[i]);
                word.deleteCharAt(word.length() - 1);
            }
        }
    }

    @Override
    public Iterator<String> keys() {
        return collect().iterator();
    }


    public List<String> autocomplete(String s) {
        List<String> data = new ArrayList<>();
        autocomplete(head, s, "", 0, data);
        return data;
    }

    private void autocomplete(Node<V> x, String pattern, String match, int level, List<String> list) {
        if (x == null) return;
        if (level < pattern.length()) {
            char c = pattern.charAt(level);
                autocomplete(x.next[c], pattern, match + c, level + 1, list);
        } else {


            if (x.value != null) {
                list.add(match);
            }
            for (int i = 0; i < 256; i++) {
                if (x.next[i] != null) {
                    char chr = (char) i;
                    match += chr;
                    autocomplete(x.next[i], pattern, match, level + 1, list);
                    match = match.substring(0, match.length() - 1);

                }
            }
        }
    }

    public List<String> wildCard(String s) {
        List<String> words = new ArrayList<>();
        wildCard(head, s, "", 0, words);
        return words;
    }

    private void wildCard(Node<V> x, String pattern, String match, int lvl, List<String> result) {
        if (x == null) return;


        if (lvl == pattern.length() && x.value!= null){ result.add(match);return;}
        if(lvl<pattern.length()){
        char c = pattern.charAt(lvl);
        if (pattern.charAt(lvl) != '*') {
            if (x.next[c] != null) wildCard(x.next[c], pattern, match + c, lvl + 1, result);

            }
        else if (pattern.charAt(lvl) == '*') {
            for (int i = 0; i < 256; i++) {
                if (x.next[i] != null) {
                    match = match + (char) i;
                    wildCard(x.next[i], pattern, match, lvl + 1, result);
                    match = match.substring(0, match.length() - 1);
                }
            }
        }
        }
    }
}