package anaydis.immutable;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Optional;

public class BankersQueueTest {
    private BankersQueue<Integer> emptyQueue;


    @Before
    public void setUp() {
        emptyQueue = new BankersQueue<>();
    }

    @Test
    public void testEnqueue() {
        Queue<Integer> queue = emptyQueue.enqueue(42);
        Assert.assertTrue(queue instanceof BankersQueue);
    }

    @Test
    public void testDequeue() {
        emptyQueue = (BankersQueue<Integer>) emptyQueue.enqueue(1);
        emptyQueue = (BankersQueue<Integer>) emptyQueue.enqueue(2);
        emptyQueue = (BankersQueue<Integer>) emptyQueue.enqueue(3);
        Assert.assertEquals(emptyQueue.dequeue().value,Integer.valueOf(1));

    }

    @Test
    public void testIsEmpty() {
        Assert.assertTrue(emptyQueue.isEmpty());
    }

}

