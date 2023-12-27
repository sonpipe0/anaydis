package anaydis.sort;


import anaydis.sort.gui.ObservableSorter;
import anaydis.sort.gui.SorterListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Abstract sorter: all sorter implementations should subclass this class.
 */
abstract class AbstractSorter implements Sorter,ObservableSorter {
    List<SorterListener> listeners;
    public AbstractSorter(){
        listeners = new ArrayList<>();
    }

    @Override public void addSorterListener(@NotNull final anaydis.sort.gui.SorterListener listener){
        if(listeners.contains(listener)) return;
        listeners.add(listener);
    }
    public void removeSorterListener(SorterListener listener){
        listeners.remove(listener);
    }

    <T> void compExch(List<T> ls, int a , int b, Comparator<T> cmp){
        if (less(ls.get(b), ls.get(a), cmp)){
            T element = ls.get(a);
            ls.set(a, ls.get(b));
            ls.set(b, element);
            for (SorterListener listener : listeners){
                listener.swap( a, b);
            }
        }
    }

    <T> boolean less(T v, T w, Comparator<T> cmp){
        for(SorterListener listener:listeners){
            listener.greater((Integer) w,(Integer)v);
        }
        return cmp.compare(v,w)<0;

    }

    <T> void exch(List<T> ls, int a , int b){
        T element = ls.get(a);
        ls.set(a,ls.get(b));
        ls.set(b,element);
        for(SorterListener listener:listeners){
            listener.swap(a,b);
        }
    }

}
