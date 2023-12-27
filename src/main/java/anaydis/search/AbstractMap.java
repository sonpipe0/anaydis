package anaydis.search;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractMap<String,V> implements ObservaleMap<V>{

    List<MapListener> listeners ;

    public AbstractMap(){
        listeners = new ArrayList<>();
    }
    @Override
    public void addMapListener(@NotNull MapListener listener) {
        if(listeners.contains(listener)) return;
        listeners.add(listener);
    }

    @Override
    public void removeMapListener(@NotNull MapListener listener) {
        listeners.remove(listener);
    }

    public void notifyHit(){
        for (MapListener listener:listeners) listener.notifyHit();
    }
    public void notifyMiss(){
        for (MapListener listener:listeners) listener.notifyMiss();
    }


}




