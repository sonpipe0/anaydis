package anaydis.search;


public class MapListenerImpl implements MapListener {
    private long hits;
    private long miss;
    private long start;
    @Override
    public void notifyHit() {
        hits++;
    }

    @Override
    public void notifyMiss() {
        miss++;
    }

    @Override
    public long getHits() {
        return hits;
    }

    @Override
    public long getMisses() {
        return miss;
    }

    @Override
    public void startClock() {
        start = System.currentTimeMillis();
    }

    @Override
    public double getTime() {
        return (System.currentTimeMillis()-start)/1000.0;
    }

    @Override
    public void clear(){hits = 0; miss =0;}
}
