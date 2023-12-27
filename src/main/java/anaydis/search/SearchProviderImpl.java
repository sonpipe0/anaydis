package anaydis.search;

import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;

public class SearchProviderImpl<V> implements SearchProvider<String,V> {
    EnumMap<MapType,Map<String,V>> maps = new EnumMap<>(MapType.class);


    public SearchProviderImpl(){
        maps.put(MapType.BINARY, new BinaryTrieMap<>());
        maps.put(MapType.RWAY, new RWayTrieMap<>());
        maps.put(MapType.TST, new TSTTrieMap<>());
    }
    @Override
    public @NotNull Iterable<Map<String, V>> getAllMaps() {
        return maps.values();
    }

    @Override
    public @NotNull Map<String, V> getSorterForType(@NotNull MapType type) {
        return maps.get(type);
    }
}
