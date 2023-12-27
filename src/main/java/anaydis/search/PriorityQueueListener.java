package anaydis.search;

public interface PriorityQueueListener {
    void startclock();
    void notifyCompare();
    double getTime();
    long getCompares();
}
