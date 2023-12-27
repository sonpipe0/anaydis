package anaydis.sort;

import java.util.ArrayList;
import java.util.Comparator;

public class SorterName {

    private final Sorter sorter;
    SorterName(){
        sorter = new InsertionSorter();
    }
    public void Sort(ArrayList<FullName> names){
        sorter.sort(Comparator.comparing(FullName::getLastname).thenComparing(FullName::getFirstname),names);
    }

}
