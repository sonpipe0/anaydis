package anaydis.search;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AbstractPriorityQueue<K> implements ObservableQueue<K>{
    List<PriorityQueueListener> listeners;
    public AbstractPriorityQueue(){
        listeners = new ArrayList<>();
    }
    @Override
    public void addMapListener(@NotNull PriorityQueueListener listener) {
        if(listeners.contains(listener)) return;
        listeners.add(listener);
    }

    @Override
    public void removeMapListener(@NotNull PriorityQueueListener listener) {
        listeners.remove(listener);
    }

    public void notifyCompare(){
        for (PriorityQueueListener listener:listeners) listener.notifyCompare();
    }
    public boolean less(K a, K b,Comparator<K> cmp){
        notifyCompare();
        return cmp.compare(a,b)<0;
    }
    public void exch(int a,int b,K[] ls){
        K temp = ls[a];
        ls[a] = ls[b];
        ls[b] = temp;
    }

}
