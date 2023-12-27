package anaydis.search;
import org.junit.Before;
import org.junit.Test;

import java.security.Key;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.junit.Assert.*;

public class HeapPriorityQueueTest {

    private HeapPriorityQueue<Integer> priorityQueue;

    @Before
    public void setUp() {
        Comparator<Integer> comparator = Comparator.naturalOrder();
        priorityQueue = new HeapPriorityQueue<>(comparator);
    }

    @Test
    public void testInsertAndPop() {
        priorityQueue.insert(3);
        priorityQueue.insert(1);
        priorityQueue.insert(4);

        assertEquals(4, (int) priorityQueue.pop());
        assertEquals(3, (int) priorityQueue.pop());
        assertEquals(1, (int) priorityQueue.pop());
    }

    @Test
    public void testPeek() {
        priorityQueue.insert(5);
        priorityQueue.insert(2);
        priorityQueue.insert(7);

        assertEquals(7, (int) priorityQueue.peek());
        assertEquals(7, (int) priorityQueue.peek());
    }

    @Test(expected = NoSuchElementException.class)
    public void testPopEmptyQueue() {
        priorityQueue.pop();
    }

    @Test
    public void testSize() {
        assertEquals(0, priorityQueue.size());
        priorityQueue.insert(6);
        priorityQueue.insert(8);
        assertEquals(2, priorityQueue.size());
        priorityQueue.pop();
        assertEquals(1, priorityQueue.size());
    }

    public void report(AbstractPriorityQueue<Integer> queue,String dataType,int[] sizes){
        IntegerPriorityQueueSetGenerator setGenerator = new IntegerPriorityQueueSetGenerator();
        priorityListenerImpl listener = new priorityListenerImpl();
        queue.addMapListener(listener);
        double averageTimePut = 0;
        double averageTimePop = 0;
            if (Objects.equals(dataType, "random")){
            for (int size:sizes){
                for(int i = 0 ; i<100 ; i++){
                listener.startclock();
                setGenerator.putRandom(size,(PriorityQueue<Integer>) queue);
                averageTimePut += listener.getTime();
                listener.startclock();
                while (!((PriorityQueue<?>) queue).isEmpty()){
                    ((PriorityQueue<?>) queue).pop();
                }
                averageTimePop += listener.getTime();
                    }
                averageTimePut /= 100;
                averageTimePop /= 100;
                System.out.println(queue.getClass().getTypeName().substring(15)+","+"random,"+size+","+averageTimePut+","+averageTimePop);
                averageTimePut = 0;
                averageTimePop = 0;
                }
            }
            else if (Objects.equals(dataType, "ascendant")){
            for (int size:sizes){
                for(int i = 0 ; i<100 ; i++){
                    listener.startclock();
                    setGenerator.putAscending(size,(PriorityQueue<Integer>) queue);
                    averageTimePut += listener.getTime();
                    listener.startclock();
                    while (!((PriorityQueue<?>) queue).isEmpty()){
                        ((PriorityQueue<?>) queue).pop();
                    }
                    averageTimePop += listener.getTime();
                }
                averageTimePut /= 100;
                averageTimePop /= 100;
                System.out.println(queue.getClass().getTypeName().substring(15)+","+"ascendant,"+size+","+averageTimePut+","+averageTimePop);
                averageTimePut = 0;
                averageTimePop = 0;
                }
            }
            else if  (Objects.equals(dataType, "descendant")){
            for (int size:sizes){
                for(int i = 0 ; i<100 ; i++){
                    listener.startclock();
                    setGenerator.putDescending(size,(PriorityQueue<Integer>) queue);
                    averageTimePut += listener.getTime();
                    listener.startclock();
                    while (!((PriorityQueue<?>) queue).isEmpty()){
                        ((PriorityQueue<?>) queue).pop();
                    }
                    averageTimePop += listener.getTime();
                }
                averageTimePut /= 100;
                averageTimePop /= 100;
                System.out.println(queue.getClass().getTypeName().substring(15)+","+"descendant,"+size+","+averageTimePut+","+averageTimePop);
                averageTimePut = 0;
                averageTimePop = 0;
                }
            }

    }

    public void performanceTest(){
        int[] ls = {100, 1000, 5000, 10000};
        System.out.println("Type,dataSet,size,timePut,TimePop");
        for(PriorityQueue<Integer> queue: new priorityProviderImpl<Integer>().getAllPriorityQueues()){
            report((AbstractPriorityQueue<Integer>) queue,"random",ls);
            report((AbstractPriorityQueue<Integer>) queue,"ascendant",ls);
            report((AbstractPriorityQueue<Integer>) queue,"descendant",ls);

        }
    }

    public static void main(String[] args) {
        HeapPriorityQueueTest test = new HeapPriorityQueueTest();
        test.performanceTest();
    }
}
