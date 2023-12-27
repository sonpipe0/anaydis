package anaydis.string;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class StringSearcherProviderImpl implements StringSearcherProvider{

    @Override
    public @NotNull StringSearcher getForType(@NotNull StringSearcherType type, @NotNull String text) {
        switch (type){
            case BRUTE_FORCE:
                return new BruteForce(text);
            case RABIN_KARP:
                return new RabinKarp(text);
        }
        throw new NoSuchElementException("NOT IMPLEMENTED");
    }
    public @NotNull Iterable<StringSearcher> getAllStringSearchers(String text){
        List<StringSearcher> searchers = new ArrayList<>();
        searchers.add(new RabinKarp(text));
        searchers.add(new BruteForce(text));
        return searchers;
    }
}
