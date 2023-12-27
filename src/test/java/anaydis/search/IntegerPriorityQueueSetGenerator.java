package anaydis.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IntegerPriorityQueueSetGenerator {

    public void putRandom(int qty,PriorityQueue<Integer> queue){
        Random rn = new Random(1);

        for(int i = 30 ; queue.size()<qty; i =rn.nextInt(queue.size()*10)){
            queue.insert(i);
        }
    }
    public void putAscending(int qty,PriorityQueue<Integer> queue){
        Random rn = new Random(1);

        for(int i = 30 ; queue.size()<qty; i +=rn.nextInt(queue.size()*10)){
            queue.insert(i);
        }
    }
    public void putDescending(int qty,PriorityQueue<Integer> queue){
        Random rn = new Random(1);

        for(int i = 30 ; queue.size()<qty; i -=rn.nextInt(queue.size()*10)){
            queue.insert(i);
        }
    }

}
