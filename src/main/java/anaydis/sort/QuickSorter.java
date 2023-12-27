package anaydis.sort;

import anaydis.sort.gui.SorterListener;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class QuickSorter extends AbstractSorter{
    @Override
    public <T> void sort(@NotNull Comparator<T> cmp, @NotNull List<T> ls) {
        quickSort( ls, 0, ls.size() - 1,cmp);
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.QUICK;
    }

    private <T> void quickSort(List<T> ls, int lo,int hi,Comparator<T> cmp){
        if(hi <= lo) return;
        int i = partition(ls, lo, hi,cmp);
        quickSort(ls, lo, i - 1,cmp);
        quickSort(ls, i + 1, hi,cmp);
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

}
