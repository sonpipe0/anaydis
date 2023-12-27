package anaydis.sort;

import anaydis.sort.gui.SorterListener;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SelectionSorter extends AbstractSorter {

    @Override
    public <T> void sort(@NotNull Comparator<T> cmp, @NotNull List<T> list) {
        for(int i = 0 ; i< list.size() ; i++){
           int min = i;
           for (int j = i+1 ; j< list.size();j++){
                if(less(list.get(j),list.get(min),cmp)) min = j;
           }
           exch(list,i,min);
        }
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.SELECTION;
    }

}
