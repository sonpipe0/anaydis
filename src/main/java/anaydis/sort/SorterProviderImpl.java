package anaydis.sort;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EnumMap;

public class SorterProviderImpl implements SorterProvider{
    EnumMap<SorterType,Sorter> sorters = new     EnumMap<>(SorterType.class);


   public SorterProviderImpl(){
        sorters.put(SorterType.BUBBLE,new BubbleSorter());
        sorters.put(SorterType.SELECTION,new SelectionSorter());
        sorters.put(SorterType.INSERTION,new InsertionSorter());
        sorters.put(SorterType.SHELL,new ShellSorter());
        sorters.put(SorterType.H,new HSorter());
        sorters.put(SorterType.QUICK,new QuickSorter());
        sorters.put(SorterType.QUICK_NON_RECURSIVE,new QuickSorterNonRecursive());
        sorters.put(SorterType.QUICK_CUT,new QuickSorterCutoff());
        sorters.put(SorterType.QUICK_MED_OF_THREE,new QuickSortMedianThree());
        sorters.put(SorterType.QUICK_THREE_PARTITION,new QuickSorterThreeWayPartitioning());
        sorters.put(SorterType.MERGE_TOP_DOWN,new MergeSorterTopDown());
        sorters.put(SorterType.MERGE_BOTTOM_UP,new MergeSorterBottomUp());
    }
    @Override
    public @NotNull Iterable<Sorter> getAllSorters() {
        return sorters.values();
    }

    @Override
    public @NotNull Sorter getSorterForType(@NotNull SorterType type) {
        for(Sorter sorter: sorters.values()){
            if (sorter.getType().equals(type)) return sorter;
        }
        return new BubbleSorter();
    }
}
