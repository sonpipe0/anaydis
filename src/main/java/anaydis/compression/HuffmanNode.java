package anaydis.compression;

public class HuffmanNode <K,V>{
    K freq;
    V value;
    HuffmanNode<K,V> right;
    HuffmanNode<K,V> left;


    HuffmanNode(){
        this.freq = null;
        this.value = null;
    }
    HuffmanNode(K freq,V value){
        this.freq = freq;
        this.value = value;
    }
}
