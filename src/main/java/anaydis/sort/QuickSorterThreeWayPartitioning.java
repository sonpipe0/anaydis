package anaydis.sort;

import anaydis.sort.gui.SorterListener;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class QuickSorterThreeWayPartitioning extends AbstractSorter{

    public <T> void sort(@NotNull Comparator<T> cmp, @NotNull List<T> ls) {

        quickSort( ls, 0, ls.size() - 1,cmp);
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.QUICK_THREE_PARTITION;
    }

    private <T> void quickSort(List<T> ls, int lo,int hi,Comparator<T> cmp){
        if (hi<= lo) return;
        int roof = hi;
        int floor = lo;
        T pivot = ls.get(lo);

        for (int i = lo + 1; i <= roof; i++) {
            if (less(ls.get(i), pivot, cmp)) {
                exch(ls, floor++, i);
            } else if (!less(ls.get(i), pivot, cmp)) {
                exch(ls, i, roof--);
                i--;
            }
        }
        quickSort(ls, lo, floor-1,cmp);
        quickSort(ls, roof+1, hi,cmp);

    }
}
