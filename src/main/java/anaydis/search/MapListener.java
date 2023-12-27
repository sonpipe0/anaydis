package anaydis.search;

public interface MapListener {

     void notifyHit();
     void notifyMiss();

     long getHits();
     long getMisses();

     void startClock();

     double getTime();

     void clear();


}
