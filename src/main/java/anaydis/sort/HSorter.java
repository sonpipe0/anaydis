package anaydis.sort;

import anaydis.sort.gui.SorterListener;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class HSorter extends AbstractSorter
{

    public <T> void sort(@NotNull Comparator<T> comparator, @NotNull List<T> list){
        sort(comparator, list, 1);
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.H;
    }

    public <T> void sort(Comparator<T> cmp, List<T> ls, int h)
    {
        for(int i = h; i < ls.size(); i++){
            for (int j= i ; j >= h && less(ls.get(j),ls.get(j-h),cmp);j-=h){
                exch(ls,j,j-h);
            }
        }
    }


}