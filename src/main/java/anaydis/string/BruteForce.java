package anaydis.string;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;

public class BruteForce implements StringSearcher{

    private final String text;

    public BruteForce(String text) {
        this.text = text;
    }


    @Override
    public int count(@NotNull String pattern) {
        int qty = 0;
        for(int i=0;i<text.length();i++){
            boolean found = true;
            for(int j = 0; j< pattern.length(); j++){
                if(text.charAt(i+j) != pattern.charAt(j)){
                    found = false;
                    break;
                }
            }
            if(found) qty++;
        }
        return qty;
    }

    @Override
    public int first(@NotNull String pattern) {
        for(int i=0;i<text.length();i++){
            boolean found = true;
            for(int j = 0; j< pattern.length(); j++){
                if(text.charAt(i+j) != pattern.charAt(j)){
                    found = false;
                    break;
                }
            }
            if(found) return i;
        }
        return -1;
    }

    @Override
    public @NotNull Iterator<Integer> all(@NotNull String pattern) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for(int i=0;i<text.length();i++){
            boolean found = true;
            for(int j = 0; j< pattern.length(); j++){
                if(text.charAt(i+j) != pattern.charAt(j)){
                    found = false;
                    break;
                }
            }
            if(found) indexes.add(i);
        }
        return indexes.iterator();
    }

    @Override
    public @NotNull StringSearcherType getType() {
        return StringSearcherType.BRUTE_FORCE;
    }

    @Override
    public @NotNull String text() {
        return text;
    }
}
