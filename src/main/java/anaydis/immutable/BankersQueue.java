package anaydis.immutable;

import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;

public class BankersQueue<T> implements Queue<T>{
    List<T> inputQueue;
    List<T> popQueue;
    public BankersQueue(List<T> tail,List<T> popQueue){
        this.inputQueue = tail;
        this.popQueue = popQueue;
    }

    public BankersQueue(){
        this.inputQueue = List.nil();
        this.popQueue = List.nil();
    }
    @Override
    public @NotNull Queue<T> enqueue(@NotNull T value) {
        List<T> temp = List.cons(value,inputQueue);
        return new BankersQueue<>(temp,popQueue);
    }

    @Override
    public @NotNull Result<T> dequeue() {
        if(popQueue.isEmpty()){
            if(inputQueue.isEmpty()) throw new NoSuchElementException();
            List<T> newPopQueue = inputQueue.reverse();
            List<T> newTail = List.nil();
            return new Result<>(newPopQueue.head(),new BankersQueue<>(newTail,newPopQueue.tail()));
        }
        else return new Result<>(popQueue.head(),new BankersQueue<>(inputQueue,popQueue.tail()));
    }


    @Override
    public boolean isEmpty() {
        return popQueue.isEmpty() && inputQueue.isEmpty();
    }
}

