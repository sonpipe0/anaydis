package anaydis.search;

import org.jetbrains.annotations.NotNull;

public interface ObservableQueue<K>  {
    //~ Methods ..................................................................................................................

    /**
     * Adds the given SorterListener.
     *
     * @param  listener  to be added
     */
    void addMapListener(@NotNull final PriorityQueueListener listener);

    /**
     * Removes the given SorterListener.
     *
     * @param  listener  to be removed
     */
    void removeMapListener(@NotNull final PriorityQueueListener listener);
}
