package anaydis.search;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.*;

public class TestTrie {

    public void testMap(File dataset, MapType type) {

        SearchProviderImpl<Integer> searchProvider = new SearchProviderImpl<>();
        Map<String, Integer> map = searchProvider.getSorterForType(type);
        HashMap<String, Integer> values = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dataset));
            String line;


            while ((line = reader.readLine()) != null) {
                String[] words = line.split("[\\s,:;'\"!()$?¿¡.`~<>]+");

                for (String word : words) {
                    word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();

                    if (!word.isEmpty()) {
                        map.put(word, map.get(word) != null ? map.get(word) + 1 : 1);
                        values.put(word, values.get(word) != null ? values.get(word) + 1 : 1);

                    }
                }
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("FILE NOT FOUND"+ e);
            Assert.fail();
            e.printStackTrace();
        }
        map.keys().forEachRemaining(key -> Assert.assertTrue(map.containsKey(key)));
        values.keySet().forEach(key -> Assert.assertEquals(map.get(key), values.get(key)));

    }

    private AbstractMap<String, Integer> addText(File dataset, MapType type, int N) {
        int size = 0;
        SearchProviderImpl<Integer> searchProvider = new SearchProviderImpl<>();
        Map<String, Integer> map = searchProvider.getSorterForType(type);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dataset));
            String line;


            while ((line = reader.readLine()) != null) {
                String[] words = line.split("[\\s,:;'\"!()$?¿¡.`~<>]+");


                for (String word : words) {
                    word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();

                    if (!word.isEmpty()) {
                        map.put(word, map.get(word) != null ? map.get(word) + 1 : 1);
                        size++;
                        if (size == N) break;

                    }
                }
                if (size == N) break;
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("FILE NOT FOUND"+ e);
            Assert.fail();
            e.printStackTrace();
        }
        return (AbstractMap<String, Integer>) map;
    }

    private String reverseString(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    private List<String> saveInverseText(File dataset, int N) {
        int size = 0;
        List<String> reversedKeys = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dataset));
            String line;


            while ((line = reader.readLine()) != null) {
                String[] words = line.split("[\\s,:;'\"!()$?¿¡.`~<>]+");

                for (String word : words) {
                    word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
                    String reversedWord = reverseString(word);
                    if (!word.isEmpty()) {
                        if (reversedKeys.contains(reversedWord)) {
                            size++;
                            if (size == N) break;
                        } else {
                            reversedKeys.add(reversedWord);
                            size++;
                        }
                        if (size == N) break;
                    }
                }
                if (size == N) break;
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("FILE NOT FOUND" + e);
            Assert.fail();
            e.printStackTrace();
        }
        Collections.shuffle(reversedKeys, new Random(1));
        return reversedKeys;
    }



    public void testList(int[] sizes, MapType type, File dataset) {
        for (int size : sizes) {
            MapListener listener = new MapListenerImpl();
            AbstractMap<String, Integer> map = addText(dataset, type, size);
            map.addMapListener(listener);
            List<String> reversedList = saveInverseText(dataset, size);
            double time = 0;
            for (int i = 0; i < 1000; i++) {
                listener.startClock();
                for (String s : reversedList) {
                    map.get(s);
                }
                time += listener.getTime();
            }
            time = time / 1000;
            long hits = listener.getHits() / 1000;
            long misses = listener.getMisses() / 1000;
            System.out.println(type + "," + time + "," + size + "," + hits + "," + misses);
        }
    }

    private void analysisReport(int[] sizes, MapType[] types , File dataset){
        System.out.println("Type,T(N),Size,Hits,Misses");
        for(MapType type: types){
        testList(sizes,type,dataset);
        }
    }

    @Test
    public void testQuijote() {
        File Quijote = new File("./src/main/resources/books/quijote.txt");
        testMap(Quijote, MapType.BINARY);
    }

    @Test
    public void testRwayTrie() {
        File Quijote = new File("./src/main/resources/books/quijote.txt");
        testMap(Quijote, MapType.RWAY);
    }
    @Test
    public void testTSTTrie() {
        File Quijote = new File("./src/main/resources/books/quijote.txt");
        testMap(Quijote, MapType.TST);
    }

    public static void main(String[] args) {
//        int[] sizes = {5000, 50000, 100000, 150000, 200000};
//        TestTrie testTrie = new TestTrie();
//        System.out.println(new File(".").getAbsolutePath());
//        File Quijote = new File("./src/main/resources/books/quijote.txt");
//        MapType binary = MapType.BINARY;
//        MapType tst = MapType.TST;
//        MapType rWay = MapType.RWAY;
//        MapType[] types = new MapType[3];types[0] = binary; types[1] = tst; types[2] = rWay;
//        testTrie.analysisReport(sizes,types,Quijote);

        TSTTrieMap<Integer> map = new TSTTrieMap<>();
        map.put("lupin",2);
        map.put("lupon",2);
        map.put("lura",2);
        map.put("luro",2);
        map.put("luso",2);
        map.put("luros",2);
        map.put("luru",2);
        map.put("arbol",2);
        map.put("gilaso",2);
        map.put("lup",2);
        map.autocomplete("lup").forEach(System.out::println);

    }
}
