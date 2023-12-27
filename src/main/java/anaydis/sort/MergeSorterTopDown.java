package anaydis.sort;

import anaydis.sort.gui.SorterListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class MergeSorterTopDown extends AbstractSorter{
    @Override
    public <T> void sort(@NotNull Comparator<T> cmp, @NotNull List<T> ls) {
        mergeSort(ls,0,ls.size()-1,cmp);
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.MERGE_TOP_DOWN;
    }

    private <T> void mergeSort(@NotNull List<T> ls, int low,int high,Comparator<T> cmp){
        if(low < high) {
            int mid = (low + high) / 2;
            mergeSort(ls, low, mid,cmp);
            mergeSort(ls, mid + 1, high,cmp);
            merge(ls, low, mid, high,cmp);
        }
    }
   private  <T> void merge(@NotNull List<T> ls, int low, int middle, int high, Comparator<T> cmp) {
       for (SorterListener listener:listeners) {
           listener.box(low,high);
       }
        ArrayList<T> aux = new ArrayList<>();

        for (int i = 0; i <= high; i++) {
            aux.add(null);
        }

        for (int i = low; i <= high; i++) {
            aux.set(i, ls.get(i));
            for (SorterListener listener:listeners) {
                listener.copy(i,i,true);
            }
        }
        //copy of ls into aux


        int i = low;
        int j = middle + 1;

        for (int k = low; k <= high; k++) {
            if (i > middle) {
                ls.set(k, aux.get(j));
                for (SorterListener listener:listeners) {
                    listener.copy(j,k,false);
                }
                j++;
            } else if (j > high) {
                ls.set(k, aux.get(i));
                for (SorterListener listener:listeners) {
                    listener.copy(i,k,false);
                }
                i++;
            } else if (less(aux.get(j), aux.get(i), cmp)) {
                ls.set(k, aux.get(j));
                for (SorterListener listener:listeners) {
                    listener.copy(j,k,false);
                }
                j++;
            } else {
                ls.set(k, aux.get(i));
                for (SorterListener listener:listeners) {
                    listener.copy(i,k,false);
                }
                i++;
            }
        }
    }
}