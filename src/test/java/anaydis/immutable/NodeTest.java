package anaydis.immutable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static anaydis.immutable.List.cons;

public class NodeTest {
    private List<Integer> lista;
    @Before
    public void setUp(){
        lista = new Node<>(null,List.nil());
    }
    @Test
    public void isEmptyTest(){
        Assert.assertTrue(lista.isEmpty());
        lista = cons(2,lista);
        Assert.assertFalse(lista.isEmpty());
    }
    @Test
    public void isReversed(){
        lista = cons(2,cons(3,cons(7,cons(1,lista))));
        lista = lista.reverse();
        List<Integer> reversed = cons(1,cons(7,cons(3,cons(2,List.nil()))));
        Assert.assertEquals(lista.toString(),reversed.toString());
    }

}
