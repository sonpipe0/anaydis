package anaydis.search;

import org.jetbrains.annotations.NotNull;

public interface priorityProvider<K> {
    @NotNull
    Iterable<PriorityQueue<K>> getAllPriorityQueues();

    /**
     * Given the MapType, this method will return the corresponding {@link Map map}.
     * <p>
     * If given type is not yet implemented, an IllegalArgumentException must be thrown.
     * <p>
     * IMPLEMENTATION NOTE: Internally all maps should be held with a map
     * <p>
     * E.g.:
     * `private final Map<SorterType, Sorter> sorters = new EnumMap<>(SorterType.class);`
     *
     * @param type Map's type
     * @return corresponding Map
     */
    @NotNull
    PriorityQueue<K> getQueueForType(@NotNull final PriorityType type);
}
