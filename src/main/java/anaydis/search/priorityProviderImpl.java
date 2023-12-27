package anaydis.search;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.EnumMap;

public class priorityProviderImpl<K> implements priorityProvider<K>{
    EnumMap<PriorityType,PriorityQueue<K>> queues = new EnumMap<>(PriorityType.class);
    @SuppressWarnings("unchecked")

    public priorityProviderImpl(){
        queues.put(PriorityType.HEAP,new HeapPriorityQueue<>((Comparator<K>) Comparator.naturalOrder()));
        queues.put(PriorityType.ORDERED,new OrderedArrayPriorityQueue<>((Comparator<K>) Comparator.naturalOrder()));
        queues.put(PriorityType.UNORDERED,new UnorderedArrayPriorityQueue<>((Comparator<K>) Comparator.naturalOrder()));
    }

    @Override
    public @NotNull Iterable<PriorityQueue<K>> getAllPriorityQueues() {
        return queues.values();
    }

    @Override
    public @NotNull PriorityQueue<K> getQueueForType(@NotNull PriorityType type) {
        return queues.get(type);
    }
}
