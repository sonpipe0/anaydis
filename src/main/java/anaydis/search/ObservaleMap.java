package anaydis.search;

import org.jetbrains.annotations.NotNull;

public interface ObservaleMap<V> extends Map<String,V> {
    //~ Methods ..................................................................................................................

    /**
     * Adds the given SorterListener.
     *
     * @param  listener  to be added
     */
    void addMapListener(@NotNull final MapListener listener);

    /**
     * Removes the given SorterListener.
     *
     * @param  listener  to be removed
     */
    void removeMapListener(@NotNull final MapListener listener);
}
