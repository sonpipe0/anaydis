package anaydis.sort;

import anaydis.sort.gui.SorterListener;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class TestPractice02 extends SorterTest {

    int[] sizes = {10/**,50,500,1000,5000,10000,15000,30000,50000,100000*/};

    //~ Methods ..................................................................................................................

    /** Test BubbleSorter with String generator. */
    @Test
    public void testBubbleWithStringGenerator() {
        testSorter(createStringDataSetGenerator(), SorterType.BUBBLE, 10);
        testSorter(createStringDataSetGenerator(), SorterType.BUBBLE, 50);
        testSorter(createStringDataSetGenerator(), SorterType.BUBBLE, 100);
    }

    /** Test BubbleSorter with Integer generator. */
    @Test public void testBubbleWithIntegerGenerator() {
        testSorter(createIntegerDataSetGenerator(), SorterType.BUBBLE, 10);
        testSorter(createIntegerDataSetGenerator(), SorterType.BUBBLE, 50);
        testSorter(createIntegerDataSetGenerator(), SorterType.BUBBLE, 100);
    }

    /** Test InsertionSorter with String generator. */
    @Test public void testInsertionWithStringGenerator() {
        testSorter(createStringDataSetGenerator(), SorterType.INSERTION, 10);
        testSorter(createStringDataSetGenerator(), SorterType.INSERTION, 50);
        testSorter(createStringDataSetGenerator(), SorterType.INSERTION, 100);
    }

    /** Test InsertionSorter with Integer generator. */
    @Test public void testInsertionWithIntegerGenerator() {
        testSorter(createIntegerDataSetGenerator(), SorterType.INSERTION, 10);
        testSorter(createIntegerDataSetGenerator(), SorterType.INSERTION, 50);
        testSorter(createIntegerDataSetGenerator(), SorterType.INSERTION, 100);
    }

    /** Test SelectionSorter with String generator. */
    @Test public void testSelectionWithStringGenerator() {
        testSorter(createStringDataSetGenerator(), SorterType.SELECTION, 10);
        testSorter(createStringDataSetGenerator(), SorterType.SELECTION, 50);
        testSorter(createStringDataSetGenerator(), SorterType.SELECTION, 100);
    }

    /** Test SelectionSorter with Integer generator. */
    @Test public void testSelectionWithIntegerGenerator() {
        testSorter(createIntegerDataSetGenerator(), SorterType.SELECTION, 10);
        testSorter(createIntegerDataSetGenerator(), SorterType.SELECTION, 50);
        testSorter(createIntegerDataSetGenerator(), SorterType.SELECTION, 100);
    }
    private <T extends Comparable<T>> void testDataSet(@NotNull final Sorter sorter,
                                                       final int size, @NotNull final List<T> raw, Comparator<T> comparator) {
        assertThat(raw).hasSize(size);

        final List<T> sorted = new AnaydisList<>(raw);
        sorter.sort(comparator, sorted);

        final List<T> copy = new ArrayList<>(raw);
        copy.sort(comparator);

        assertThat(sorted).usingElementComparator(comparator).containsExactlyElementsOf(copy);
    }

    public void testCaseBubble(List<Integer> setGenerator, int qty){

        Stopwatch stopwatch = new Stopwatch();
        AbstractSorter bubble = new BubbleSorter();
        SorterListenerImpl listener = new SorterListenerImpl();
        bubble.addSorterListener(listener);

        testDataSet(bubble, qty,setGenerator,Comparator.naturalOrder());
        System.out.println("seconds = "+stopwatch.elapsedTime());
        System.out.println("compares =" + listener.getComparisons());
        System.out.println("swaps =" + listener.getSwaps());
    }
    public void testCaseInsertion(List<Integer> setGenerator, int qty){

        anaydis.sort.Stopwatch stopwatch = new anaydis.sort.Stopwatch();
        AbstractSorter insertion = new InsertionSorter();
        SorterListenerImpl listener = new SorterListenerImpl();
        insertion.addSorterListener(listener);

        testDataSet(insertion, qty,setGenerator,Comparator.naturalOrder());
        System.out.println("seconds = "+stopwatch.elapsedTime());
        System.out.println("compares =" + listener.getComparisons());
        System.out.println("swaps =" + listener.getSwaps());
    }

    public void testCaseSelection(List<Integer> setGenerator, int qty){


        AbstractSorter selection = new SelectionSorter();
        SorterListenerImpl listener = new SorterListenerImpl();
        selection.addSorterListener(listener);
        anaydis.sort.Stopwatch stopwatch = new anaydis.sort.Stopwatch();
        testDataSet(selection, qty,setGenerator,Comparator.naturalOrder());
        System.out.println("seconds = "+stopwatch.elapsedTime());
        System.out.println("compares =" + listener.getComparisons());
        System.out.println("swaps =" + listener.getSwaps());
    }
    public void testCaseShell(List<Integer> setGenerator, int qty,int sequence){


        ShellSorter shell = new ShellSorter();
        shell.reNumber(sequence);
        SorterListenerImpl listener = new SorterListenerImpl();
        shell.addSorterListener(listener);
        anaydis.sort.Stopwatch stopwatch = new anaydis.sort.Stopwatch();
        testDataSet(shell, qty,setGenerator,Comparator.naturalOrder());
        System.out.println("seconds = "+stopwatch.elapsedTime());
        System.out.println("compares =" + listener.getComparisons());
        System.out.println("swaps =" + listener.getSwaps()+"\n"+"\n");
    }
    public void testCaseQuick(List<Integer> setGenerator, int qty){


        AbstractSorter quick = new QuickSorter();
        SorterListenerImpl listener = new SorterListenerImpl();
        quick.addSorterListener(listener);
        anaydis.sort.Stopwatch stopwatch = new anaydis.sort.Stopwatch();
        testDataSet(quick, qty,setGenerator,Comparator.naturalOrder());
        System.out.println("seconds = "+stopwatch.elapsedTime());
        System.out.println("compares =" + listener.getComparisons());
        System.out.println("swaps =" + listener.getSwaps()+"\n"+"\n");
    }
    public void testCaseQuickNonRecursive(List<Integer> setGenerator, int qty){


        AbstractSorter quick = new QuickSorterNonRecursive();
        SorterListenerImpl listener = new SorterListenerImpl();
        quick.addSorterListener(listener);
        anaydis.sort.Stopwatch stopwatch = new anaydis.sort.Stopwatch();
        testDataSet(quick, qty,setGenerator,Comparator.naturalOrder());
        System.out.println("seconds = "+stopwatch.elapsedTime());
        System.out.println("compares =" + listener.getComparisons());
        System.out.println("swaps =" + listener.getSwaps()+"\n"+"\n");
    }

    @Test
    public void timeReport(){
        for(int size:sizes){
            System.out.println("SIZE         =    "+size);
            List<Integer> random = createIntegerDataSetGenerator().createDescending(size);
            List<Integer> random2 = createIntegerDataSetGenerator().createDescending(size);
            List<Integer> random3 = createIntegerDataSetGenerator().createDescending(size);
            System.out.println("Bubble");
            testCaseBubble(random,size);
            System.out.println("Selection");
            testCaseSelection(random2,size);
            System.out.println("Insertion");
            testCaseInsertion(random3,size);
            System.out.println("--------------------------------------------------");
        }
    }

    @Test
    public void testBubbleCaseRandom(){
        for (int size : sizes) {

            List<Integer> raw = createIntegerDataSetGenerator().createRandom(size);
            System.out.println("TestSize = " + size);
            testCaseBubble(raw, size);
        }
    }
    @Test
    public void testShellCaseRandom(){
        System.out.println("1 8 23 77 281 1073 4193 16577");
        for (int size : sizes) {

            List<Integer> raw = createIntegerDataSetGenerator().createRandom(size);
            System.out.println("TestSize = " + size);
            testCaseShell(raw, size,1);
        }

    }
    @Test
    public void testShellSequence(){
        System.out.println("1 4 13 40 121 364 1093 3280 9841");
        for (int size : sizes) {

            List<Integer> raw = createIntegerDataSetGenerator().createRandom(size);
            System.out.println("TestSize = " + size);
            testCaseShell(raw, size,2);
        }
    }


    @Test
    public void testBubbleCaseAscending(){
        for (int size : sizes) {

            List<Integer> raw = createIntegerDataSetGenerator().createAscending(size);
            System.out.println("TestSize = " + size);
            testCaseBubble(raw, size);
        }
    } @Test
    public void testBubbleCaseDescending(){
        for (int size : sizes) {

            List<Integer> raw = createIntegerDataSetGenerator().createDescending(size);
            System.out.println("TestSize = " + size);
            testCaseBubble(raw, size);
        }
    }
    @Test
    public void testSelectionCaseRandom(){
        for (int size : sizes) {

            List<Integer> raw = createIntegerDataSetGenerator().createRandom(size);
            System.out.println("TestSize = " + size);
            testCaseSelection(raw, size);
        }
    }
    @Test
    public void testSelectionCaseAscendant(){
        for (int size : sizes) {

            List<Integer> raw = createIntegerDataSetGenerator().createAscending(size);
            System.out.println("TestSize = " + size);
            testCaseSelection(raw, size);
        }
    }
    @Test
    public void testSelectionCaseDescendant(){
        for (int size : sizes) {

            List<Integer> raw = createIntegerDataSetGenerator().createDescending(size);
            System.out.println("TestSize = " + size);
            testCaseSelection(raw, size);
        }
    } @Test
    public void testInsertionCaseRandom(){
        for (int size : sizes) {

            List<Integer> raw = createIntegerDataSetGenerator().createRandom(size);
            System.out.println("TestSize = " + size);
            testCaseInsertion(raw, size);
        }
    }
    @Test
    public void testInsertionCaseDescending(){
        for (int size : sizes) {

            List<Integer> raw = createIntegerDataSetGenerator().createDescending(size);
            System.out.println("TestSize = " + size);
            testCaseInsertion(raw, size);
        }
    }
    @Test
    public void testInsertionCaseAscending(){
        for (int size : sizes) {

            List<Integer> raw = createIntegerDataSetGenerator().createAscending(size);
            System.out.println("TestSize = " + size);
            testCaseInsertion(raw, size);
        }
    }
    @Test
    public void testQuickCaseRandom(){
        for (int size : sizes) {

            List<Integer> raw = createIntegerDataSetGenerator().createRandom(size);
            System.out.println("TestSize = " + size);
            testCaseQuick(raw, size);
        }
    }
    @Test
    public void testQuickNonRecursiveCaseRandom(){
        for (int size : sizes) {

            List<Integer> raw = createIntegerDataSetGenerator().createRandom(size);
            System.out.println("TestSize = " + size);
            testCaseQuickNonRecursive(raw, size);
        }
    }


    @Test
    public void testOrderNames(){
        ArrayList<FullName> names = new ArrayList<>();
        FullName a = new FullName("Alonso","Paula");
        FullName b = new FullName("Diaz","Diego");
        FullName c = new FullName("Alonso","Diego");
        names.add(a);
        names.add(b);
        names.add(c);


        ArrayList<FullName> names2 = new ArrayList<>();
        names2.add(c);
        names2.add(a);
        names2.add(b);
        SorterName sorter = new SorterName();
        sorter.Sort(names);
        Assert.assertEquals(names2,names);
    }




    public void testCaseCutoff(List<Integer> setGenerator, int qty,int M) {


        QuickSorterCutoff quick = new QuickSorterCutoff();
        quick.setM(M);
        SorterListenerImpl listener = new SorterListenerImpl();
        quick.addSorterListener(listener);
        listener.startClock();
        testDataSet(quick, qty,setGenerator,Comparator.naturalOrder());
        System.out.println("seconds = "+listener.getTime());
        System.out.println("compares =" + listener.getComparisons());
        System.out.println("swaps =" + listener.getSwaps());
    }
    public void testCaseMedian(List<Integer> setGenerator, int qty) {


        QuickSortMedianThree quick = new QuickSortMedianThree();
        SorterListenerImpl listener = new SorterListenerImpl();
        quick.addSorterListener(listener);
        listener.startClock();
        testDataSet(quick, qty,setGenerator,Comparator.naturalOrder());
        System.out.println("seconds = "+listener.getTime());
        System.out.println("compares =" + listener.getComparisons());
        System.out.println("swaps =" + listener.getSwaps());
    }
    public void testCaseThreeWay(List<Integer> setGenerator, int qty) {


        QuickSorterThreeWayPartitioning quick = new QuickSorterThreeWayPartitioning();
        SorterListenerImpl listener = new SorterListenerImpl();
        quick.addSorterListener(listener);
        listener.startClock();
        testDataSet(quick, qty,setGenerator,Comparator.naturalOrder());
        System.out.println("seconds = "+listener.getTime());
        System.out.println("compares =" + listener.getComparisons());
        System.out.println("swaps =" + listener.getSwaps());
    }

    @Test
    public void topDown(){
        List<Integer> raw = createIntegerDataSetGenerator().createRandom(1000);
        testCaseTopDown(raw,1000);
        System.out.println(raw);
    }
    public void testCaseTopDown(List<Integer> setGenerator, int qty) {

        MergeSorterTopDown merge = new MergeSorterTopDown();
        SorterListenerImpl listener = new SorterListenerImpl();
        merge.addSorterListener(listener);
        listener.startClock();
        testDataSet(merge, qty,setGenerator,Comparator.naturalOrder());
        System.out.println("seconds = "+listener.getTime());
        System.out.println("compares =" + listener.getComparisons());
        System.out.println("swaps =" + listener.getSwaps());
    }
public void testCaseBottomUp(List<Integer> setGenerator, int qty) {

        MergeSorterBottomUp merge = new MergeSorterBottomUp();
        SorterListenerImpl listener = new SorterListenerImpl();
        merge.addSorterListener(listener);
        listener.startClock();
        testDataSet(merge, qty,setGenerator,Comparator.naturalOrder());
        System.out.println("seconds = "+listener.getTime());
        System.out.println("compares =" + listener.getComparisons());
        System.out.println("swaps =" + listener.getSwaps());
    }

    public void testSorter(AbstractSorter[] sorters,int[] sizes){
        try {
            FileWriter csvWriter = new FileWriter("C:/Users/Felipe Cronenbold/Documents/Austral/AlgoritmosyEstructurasDeDatos/runtimes.csv");
            csvWriter.append("Algorithm,dataSet,N,t(n),comps\n");
            for (AbstractSorter sorter:sorters){
                SorterListenerImpl listener = new SorterListenerImpl();
                sorter.addSorterListener(listener);
            for (int size:sizes){

                String randomType = "RANDOM";
                writeLine(sorter, listener, size, randomType,csvWriter);
                }
            }
            csvWriter.flush();
            csvWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testQuickTrace(){
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 7, 3, 9, 4, 1, 2, 3, 10, 6));
        QuickSorter quickSorter = new QuickSorter();
        quickSorter.sort(Comparator.naturalOrder(),list);
    }

    private void writeLine(AbstractSorter sorter, SorterListenerImpl listener,int size, String name, FileWriter csvWriter) throws IOException {
        double time = 0;
        for (int i = 0 ; i<10; i++){
        List<Integer> random = createIntegerDataSetGenerator().createRandom(size);
        listener.startClock();
        sorter.sort(Comparator.naturalOrder(), random);
        time += listener.getTime();
        listener.clear();
        }
        List<Integer> random = createIntegerDataSetGenerator().createRandom(size);
        listener.startClock();
        sorter.sort(Comparator.naturalOrder(),random);
        time = time/10;
        long comps = listener.getComparisons();
        csvWriter.append(String.format("%s,%s,%d,%f,%d\n", sorter.getType(), name, size, time, comps));
        listener.clear();
    }

    public static void main(String[] args){
        TestPractice02 test = new TestPractice02();
        MergeSorterTopDown mergeSorterTopDown = new MergeSorterTopDown();
        MergeSorterBottomUp mergeSorterBottomUp = new MergeSorterBottomUp();
        int[] sizes = {100,500,1000,2500,5000,100000};
        AbstractSorter[] list = {mergeSorterTopDown,mergeSorterBottomUp};
        test.testSorter(list, sizes);
    }
}