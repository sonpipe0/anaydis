package anaydis.sort;

import anaydis.sort.gui.SorterListener;

public class SorterListenerImpl implements SorterListener {
    long amountOfComparisons = 0;
    long amountOfSwaps = 0;
    long amountOfCopies = 0;

    private long start;

    public void notifyGreater(){
        amountOfComparisons++;
    }

    public void notifySwap(){
        amountOfSwaps++;
    }


    public long getComparisons(){
        return amountOfComparisons;
    }


    public long getSwaps() {
        return amountOfSwaps;
    }

    public void startClock(){
        start = System.currentTimeMillis();
    }
    public double getTime(){
        return (System.currentTimeMillis()-start)/1000.0;
    }

    public  void clear(){amountOfSwaps = 0; amountOfComparisons = 0; amountOfCopies = 0;}

    @Override
    public void box(int from, int to) {

    }

    @Override
    public void copy(int from, int to, boolean copyToAux) {
        amountOfCopies++;
    }

    @Override
    public void equals(int i, int j) {

    }

    @Override
    public void greater(int i, int j) {
        notifyGreater();
    }

    @Override
    public void swap(int i, int j) {
        notifySwap();
    }
}
