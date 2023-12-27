package anaydis.sort;

import anaydis.sort.gui.SorterListener;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

class BubbleSorter extends AbstractSorter{




    @Override
    public <T> void sort(@NotNull Comparator<T> cmp, @NotNull List<T> list) {
        int N = list.size();
        for (int i = 0 ; i < N-1 ; i++){
            for (int j = 0; j<N-i-1; j++){
                compExch(list,j,j+1, cmp );
            }
        }
    }



    @Override
    public @NotNull SorterType getType() {
        return SorterType.BUBBLE;
    }


}