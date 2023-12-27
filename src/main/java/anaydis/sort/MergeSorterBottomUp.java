package anaydis.sort;

import anaydis.sort.gui.SorterListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergeSorterBottomUp extends AbstractSorter{
    @Override
    public <T> void sort(@NotNull Comparator<T> cmp, @NotNull List<T> ls) {
        mergeSort(ls,0,ls.size()-1,cmp);
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.MERGE_BOTTOM_UP;
    }

    private <T> void mergeSort(@NotNull List<T> ls, int low, int high, Comparator<T> cmp){
        for(int middle = 1; middle <= high - low; middle *= 2) {
            final int middleX2 = middle * 2;
            for (int low1 = low; low1 <= high - middle; low1 += middleX2) {
                final int high1 = Math.min(low1 - low + middleX2 - 1, high);
                merge(ls, low1, low1 + middle - 1, high1,cmp);
            }
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
