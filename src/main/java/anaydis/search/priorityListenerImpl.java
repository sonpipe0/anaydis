package anaydis.search;

public class priorityListenerImpl implements PriorityQueueListener{
    long start;
    long compares;
    @Override
    public void startclock() {
        start = System.currentTimeMillis();
    }

    @Override
    public void notifyCompare() {
        compares++;
    }

    @Override
    public double getTime() {
        return (System.currentTimeMillis()-start)/1000.0;
    }

    @Override
    public long getCompares() {
        return compares;
    }
}
