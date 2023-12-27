package anaydis.string;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;

class RabinKarp implements StringSearcher {

    private String text;
    static final int MODULE = 3355439;
    static final int RADIX = 256;

    public RabinKarp(String text) {
        this.text = text;
    }

    int search(String pattern) {
        int textHash = 0;
        int M = pattern.length();
        int N = text.length();
        int radixMod = 1;
        int patternHash = 0;

        for (int i = 1; i < M; i++) {
            radixMod = (RADIX * radixMod) % MODULE;
        }

        for (int i = 0; i < M; i++) {
            patternHash = (patternHash * RADIX + pattern.charAt(i)) % MODULE;
            textHash = (textHash * RADIX + text.charAt(i)) % MODULE;
        }

        // Main search loop
        for (int i = M; i < N; i++) {
            if (textHash == patternHash) {
                return i - M;
            }
            textHash = (textHash + RADIX * MODULE - text.charAt(i - M) * radixMod) % MODULE;
            textHash = (textHash * RADIX + text.charAt(i)) % MODULE;
        }

        return -1;
    }

    @Override
    public int count(@NotNull String pattern) {
        int textHash = 0;
        int M = pattern.length();
        int N = text.length();
        int radixMod = 1;
        int patternHash = 0;
        int count = 0;

        for (int i = 1; i < M; i++) {
            radixMod = (RADIX * radixMod) % MODULE;
        }

        for (int i = 0; i < M; i++) {
            patternHash = (patternHash * RADIX + pattern.charAt(i)) % MODULE;
            textHash = (textHash * RADIX + text.charAt(i)) % MODULE;
        }

        // Main search loop
        for (int i = M; i < N; i++) {
            if (textHash == patternHash) {
                count++;
            }
            textHash = (textHash + RADIX * MODULE - text.charAt(i - M) * radixMod) % MODULE;
            textHash = (textHash * RADIX + text.charAt(i)) % MODULE;
        }

        return count;

    }

    @Override
    public int first(@NotNull String pattern) {
        return search(pattern);
    }

    @Override
    public @NotNull Iterator<Integer> all(@NotNull String pattern) {
        int textHash = 0;
        int M = pattern.length();
        int N = text.length();
        int radixMod = 1;
        int patternHash = 0;
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 1; i < M; i++) {
            radixMod = (RADIX * radixMod) % MODULE;
        }

        for (int i = 0; i < M; i++) {
            patternHash = (patternHash * RADIX + pattern.charAt(i)) % MODULE;
            textHash = (textHash * RADIX + text.charAt(i)) % MODULE;
        }

        // Main search loop
        for (int i = M; i < N; i++) {
            if (textHash == patternHash) {
                list.add(i - M);
            }
            textHash = (textHash + RADIX * MODULE - text.charAt(i - M) * radixMod) % MODULE;
            textHash = (textHash * RADIX + text.charAt(i)) % MODULE;
        }

        return list.iterator();
    }

    @Override
    public @NotNull StringSearcherType getType() {
        return StringSearcherType.RABIN_KARP;
    }

    @Override
    public @NotNull String text() {
        return text;
    }
}
