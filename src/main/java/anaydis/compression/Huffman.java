package anaydis.compression;

import anaydis.bit.Bits;
import anaydis.bit.BitsOutputStream;
import anaydis.search.HeapPriorityQueue;
import anaydis.search.PriorityQueue;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

public class Huffman implements Compressor{

    @Override

    // should put all nodes with frequency,value to a PriorityQueue then build the tree and from the tree extract the table.
    public void encode(@NotNull InputStream input, @NotNull OutputStream output) throws IOException {
        Map<Integer,Bits> table = new HashMap<>();
        Comparator<HuffmanNode<Integer,Integer>> cmp = Comparator.comparing(o -> o.freq);
        PriorityQueue<HuffmanNode<Integer,Integer>> temp = new HeapPriorityQueue<>(cmp.reversed());
        Integer bitSize = addToPriorityQueue(input, temp,0);
        buildTree(temp);
        addBinaryRepresentation(table, temp.peek(),new Bits());
        encodeTree(table,output);
        byte[] bitSizeBytes = ByteBuffer.allocate(4).putInt(bitSize).array();
        output.write(bitSizeBytes);
        encodeText(table,output,input);


    }

    private void encodeText(Map<Integer, Bits> table, OutputStream output, InputStream input) throws IOException {
        BitsOutputStream content = new BitsOutputStream();
        int charByte;
        input.reset();
        while ((charByte = input.read()) != -1) {
            Bits code = table.get(charByte);
            if (code != null) {
                content.write(code);
            }
        }
        output.write(content.toByteArray());
    }
    private void encodeTree(Map<Integer, Bits> table, @NotNull OutputStream output) throws IOException {
        output.write(table.size());
        for(Map.Entry<Integer,Bits> entry  :table.entrySet()){
            output.write(entry.getKey());
            entry.getValue().writeInto(output);
        }
    }

    private void buildTree(PriorityQueue<HuffmanNode<Integer, Integer>> temp) {
        while(temp.size()>1){
            HuffmanNode<Integer,Integer> node = join(temp.pop(),temp.pop());
            temp.insert(node);
        }
    }

    private void addBinaryRepresentation(Map<Integer, Bits> table, HuffmanNode<Integer, Integer> root, Bits code) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            table.put(root.value, code);
        }

        addBinaryRepresentation(table, root.left, code.copy().add(false));
        addBinaryRepresentation(table, root.right, code.copy().add(true));
    }


    private HuffmanNode<Integer,Integer> join(HuffmanNode<Integer,Integer> a, HuffmanNode<Integer,Integer> b){
        HuffmanNode<Integer,Integer> result = new HuffmanNode<>();
        result.right = b;
        result.left = a;
        result.freq = a.freq + b.freq;
        return result;
    }

    private static Integer addToPriorityQueue(@NotNull InputStream input, PriorityQueue<HuffmanNode<Integer, Integer>> temp,Integer bitSize) throws IOException {
        int charByte;
        ArrayList<HuffmanNode<Integer,Integer>> info = new ArrayList<>();
        while ((charByte = input.read()) != -1) {
            bitSize++;
            boolean containsKey = false;
            for (HuffmanNode<Integer, Integer> tuple : info) {
                if (tuple.value.equals(charByte)) {
                    containsKey = true;
                    tuple.freq++;
                    break;
                }
            }
            if (!containsKey) info.add(new HuffmanNode<>(1, charByte));
        }

        for (HuffmanNode<Integer,Integer> tuples: info){
            temp.insert(tuples);
        }
        return bitSize;
    }

    @Override
    public void decode(@NotNull InputStream input, @NotNull OutputStream output) throws IOException {
        Map<String,Integer> table = decodeTable(input);
        decodeText(input,table,output);
    }

    private boolean bitAt(int current, int nth) {
        return (current>>((8-(nth+1))%8)&1)!=0;
    }
    private boolean bitAt(byte[] bytes,int nth){
        final int pos = nth/8;
        return pos<bytes.length&&(bytes[pos]>>((8*(pos+1)-(nth+1))%8)&1)!=0;
    }

    private Map<String,Integer> decodeTable(InputStream input) throws IOException {
        Map<String,Integer> table = new HashMap<>();
        int size = input.read();
        while (size > 0){
            int character = input.read();
            int importantBits = input.read();
            String code = readNBits(importantBits,input).toString();
            table.put(code,character);
            size--;
        }
        return table;
    }

    private Bits readNBits(int importantBits, InputStream input) throws IOException {
        final int length = importantBits;
        final byte[] bytes = new byte[(length-1)/Byte.SIZE+1];
        input.read(bytes);
        Bits result = new Bits();
        for (int bit=0; bit<length;bit++){
            result.add(bitAt(bytes,bit));
        }
        return result;
    }

    private void  decodeText(InputStream input,Map<String,Integer> table,OutputStream output) throws IOException{

        byte[] sizeBytes = new byte[Integer.SIZE/Byte.SIZE];
        input.read(sizeBytes);
        int total= ByteBuffer.wrap(sizeBytes).getInt();

        int pos = 0;
        int current = -1;
        for (int i = 0 ; i <total ; i++){
            Integer character = null;
            Bits bits = new Bits();
            while (character == null){
                pos = pos % 8;
                if (pos == 0) current = input.read();
                bits.add(bitAt(current,pos++));
                character = table.get(bits.toString());
            }
            output.write(character);
        }
    }
}
