package anaydis.sort;

import anaydis.sort.gui.SorterListener;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class QuickSorterCutoff extends AbstractSorter{
    private int M=16;

    public void setM(int qty){M = qty;}
    public <T> void sort(@NotNull Comparator<T> cmp, @NotNull List<T> ls) {

        quickSort( ls, 0, ls.size() - 1,cmp);
        insertionSort(cmp,ls,0,ls.size()-1);
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.QUICK_CUT;
    }

    private <T> void quickSort(List<T> ls, int lo,int hi,Comparator<T> cmp){
        if(hi-lo <= M) return;
        int i = partition(ls, lo, hi, cmp);
        quickSort(ls, lo, i - 1, cmp);
        quickSort(ls, i + 1, hi, cmp);

    }

    private  <T> int partition(List<T> ls,int lo,int hi ,Comparator<T> cmp){
        for (SorterListener listener1:listeners){listener1.box(lo,hi);};
        int i = lo - 1;
        int j = hi;
        while(true) {
            while( less(ls.get(++i), ls.get(hi),cmp) )
                if (i == hi) break;
            while( less(ls.get(hi), ls.get(--j),cmp) )
                if (j == lo) break;
            if (i >= j) break;
            exch(ls, i, j);
        }
        exch(ls, i, hi);
        return i;
    }

    private <T> void insertionSort(@NotNull Comparator<T> cmp, @NotNull List<T> list,int lo,int hi) {
        for(int i=lo+1 ; i<=hi;i++){
            for(int j= i ; j > lo; j--){
                if (less(list.get(j),list.get(j-1),cmp)) exch(list,j,j-1);
                else break;
            }
        }
    }
}


