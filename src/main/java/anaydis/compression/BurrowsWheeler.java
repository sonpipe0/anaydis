package anaydis.compression;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class BurrowsWheeler implements Compressor{
    Comparator<WheelerNode> cmp = Comparator.comparing(WheelerNode::getF);
    Integer j;

    @Override
    public void encode(@NotNull InputStream input, @NotNull OutputStream output) throws IOException {
        String rotations = toString(input);//linear
        WheelerNode[] nodeList = createNodes(rotations);//linear
        char[] L = sortNodes(nodeList);//nLogn mergeSort
        byte[] num = ByteBuffer.allocate(4).putInt(j).array();//constant
        output.write(num);//constant
        for (char l:L) {output.write(l);}//linear
        //time = linearithmic
    }

    private String toString(InputStream input) throws IOException {
        StringBuilder result = new StringBuilder();
        int z;
        while((z=input.read())!= -1){
            result.append((char) z);
        }
        return result.toString();
    }

    private WheelerNode[] createNodes(String rotations) {
        int n = rotations.length();
        WheelerNode[] result = new WheelerNode[n];

        int nRotations = 0;
        int i = 0;
        int j = n - 1;

        while (nRotations < n) {
            String F = (i>j)?rotations.substring(i,n).concat(rotations.substring(0,j)):rotations.substring(i,j);
            char L = rotations.charAt(j);
            result[nRotations] = new WheelerNode(F, L, nRotations == 1);
            i = (i + 1) % n;
            j = (j + 1) % n;
            nRotations++;
        }
        return result;
    }

    private char[] sortNodes(WheelerNode[] nodeList) {
        Arrays.sort(nodeList,cmp);
        char[] L = new char[nodeList.length];
        int i = 0 ;
        for(WheelerNode node: nodeList) {
            if(node.original) j=i;
            L[i++] = node.L;
        }
        return L;
    }


    @Override
    public void decode(@NotNull InputStream input, @NotNull OutputStream output) throws IOException {
        byte[] num = new byte[4];
        input.read(num);
        Integer j = ByteBuffer.wrap(num).getInt();
        ArrayList<Integer> L = new ArrayList<>();
        int z;
        while ( (z = input.read()) != -1) L.add(z);//linear
        int size = L.size();
        ArrayList<Integer> F = new ArrayList<>(L);//linear
        Collections.sort(F);//linearithmic
        int[] T = new int[size];
        buildT(F, L, T);//quadratic
        decodeText(output, j, size, L, T);//linear
        //O(n2)

    }

    private void decodeText(@NotNull OutputStream output, Integer j, int size, ArrayList<Integer> L, int[] T) throws IOException {
        int index = j;
        int count = 0;
        while (size>count){
            output.write(L.get(index));
            index = T[index];
            count++;
        }
    }

    private void buildT(ArrayList<Integer> F, ArrayList<Integer> L, int[] T) {
        ArrayList<Integer> L2 = new ArrayList<>(L);
        int size = L2.size();
        for(int i = 0; i <size; i++ ){
            int index = 0;
            for(int j = 0; j< size; j++){
                if(j!=i){
                    if(F.get(i).equals(L2.get(j))) {
                        index = j;
                        L2.set(index,null);
                    break;
                    }
                }
            }
            T[i] = index;
        }
    }
}
