package anaydis.sort;

import anaydis.animation.sort.gui.Main;

public interface Animation {
    public static void main(String[] args) {
        Main.animate(new SorterProviderImpl());
    }
}
