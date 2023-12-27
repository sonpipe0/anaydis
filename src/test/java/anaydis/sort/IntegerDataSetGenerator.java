package anaydis.sort;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class IntegerDataSetGenerator implements DataSetGenerator<Integer>{
    @Override
    public @NotNull List<Integer> createAscending(int length) {
        Random rn = new Random(1);
        List<Integer> list = new ArrayList<>() ;
        for(int i = 1 ; list.size()<length; i +=1+rn.nextInt(list.size()*10)){
            list.add(i);
        }
        return list;
    }

    @Override
    public @NotNull List<Integer> createDescending(int length) {
        Random rn = new Random(1);
        List<Integer> list = new ArrayList<>() ;
        for(int i = 1 ; list.size()<length; i -=rn.nextInt(list.size()*10)){
            list.add(i);
        }
        return list;
    }

    @Override
    public @NotNull List<Integer> createRandom(int length) {
        Random rn = new Random(1);

        List<Integer> list = new ArrayList<>() ;
        for(int i = 1 ; list.size()<length; i =rn.nextInt(list.size()*10)){
            list.add(i);
        }
        return list;
    }

    @Override
    public @NotNull Comparator<Integer> getComparator() {
        return Comparator.naturalOrder();
    }
}
