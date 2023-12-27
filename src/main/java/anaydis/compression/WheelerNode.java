package anaydis.compression;

public class WheelerNode {
    public String F;
    public char L;
    boolean original;
    public WheelerNode(String F, char L, boolean original){
        this.F = F;
        this.L = L;
        this.original = original;
    }

    public String getF() {
        return F;
    }

    public char getL() {
        return L;
    }
}
