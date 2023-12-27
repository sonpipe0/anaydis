package anaydis.immutable;
import static org.junit.Assert.*;
import org.junit.Test;

public class BinaryTreeTest {

    @Test
    public void testPutMethod() {
        BinaryTree<Integer, String> tree = new BinaryTree<>(Integer::compare);
        tree = (BinaryTree<Integer, String>) tree.put(1, "One");
        tree = (BinaryTree<Integer, String>) tree.put(2, "Two");
        tree = (BinaryTree<Integer, String>) tree.put(7, "seven");
        tree = (BinaryTree<Integer, String>) tree.put(4, "four");
        tree.keys().forEachRemaining(System.out::println);
        System.out.println(tree.size());
        System.out.println(tree.get(4));
        System.out.println(tree.get(2));
        System.out.println(tree.get(7));
        System.out.println(tree.get(1));
        System.out.println(tree.get(3));
    }
    @Test
    public void emptySize() {
        BinaryTree<Integer, String> tree = new BinaryTree<>(Integer::compare);
        System.out.println(tree.size());
        System.out.println(tree.containsKey(3));
        System.out.println(tree.get(3));
        tree.keys();
    }
}
