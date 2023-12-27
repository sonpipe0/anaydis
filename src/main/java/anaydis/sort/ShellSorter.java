package anaydis.sort;

import anaydis.sort.gui.SorterListener;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ShellSorter extends AbstractSorter {


    public static int sequence = 0;
    public void reNumber(int i) {
        sequence = i;
    }
    public <T> void sort(@NotNull Comparator<T> comparator, List<T> list) {
        List<Integer> gaps =select(sequence,list.size());

        for (int gap : gaps) {
            sort(comparator, list, gap);
        }
    }
    public List<Integer> select(int sequence,int size){
        if(sequence == 0) return generateGapSequence(size);
        if(sequence == 1)return generateGapSequence2(size);
        if (sequence== 2) return generateGapSequence3(size);
        else throw new NoSuchElementException();

    }

    private List<Integer> generateGapSequence2(int size) {
        List<Integer> result = new ArrayList<>();
        result.add(1);
        for (int n = 0; n < size; n++) {
            Integer value = (int) (Math.pow(4, n + 1) + 3 * Math.pow(2, n) + 1);
            result.add(value);
        }
        Collections.reverse(result);
        return result;
    }

    private List<Integer> generateGapSequence3(int size) {
        int i=1;
        int iteration = 1 ;
        List<Integer> result = new ArrayList<>();
        while(i < size){
            result.add(i);
            iteration++;
            i = i+ (int) Math.pow(3, iteration);
        }
        Collections.reverse(result);
        return result;
    }

    private List<Integer> generateGapSequence(int size) {
        List<Integer> gapsList = new ArrayList<>();

        int gap = 1;
        while (gap < size) {
            gapsList.add(gap);
            gap = 3 * gap + 1;
        }
        Collections.reverse(gapsList);
        return gapsList;
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.SHELL;
    }

    private  <T> void sort(Comparator<T> cmp, List<T> ls, int h)
    {
        for(int i = h; i < ls.size(); i++){
            for (int j= i ; j >= h && less(ls.get(j),ls.get(j-h),cmp);j-=h){
                exch(ls,j,j-h);
            }
        }
    }

}
