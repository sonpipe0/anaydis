package anaydis.sort;

import anaydis.sort.gui.SorterListener;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class InsertionSorter extends AbstractSorter{
    @Override
    public <T> void sort(@NotNull Comparator<T> cmp, @NotNull List<T> list) {
        for(int i=0 ; i<list.size();i++){
            for(int j= i ; j > 0; j--){
                if (less(list.get(j),list.get(j-1),cmp)) exch(list,j,j-1);
                else break;
            }
        }
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.INSERTION;
    }


}
