package chapter2;

import org.junit.Test;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ExercisesTest {

    @Test
    public void shouldRunParallelForLoop() throws Exception {

        //extract list of words
        List<String> words = splitWords();

        // long count = words.parallelStream().filter(w -> w.length() > 12).count();


        //get number of cores
        int cores = Runtime.getRuntime().availableProcessors();

        //number of words
        int wordCount = (int) words.parallelStream().count();

        System.out.println("cores:" + cores + ", wordCount:" + wordCount);

        List<List<String>> segments = splitWordsListIntoNumberOfCoresSizedList(words, cores, wordCount);


        List<Future<Integer>> futures = createListOfFutures(cores, segments);

        int total = getTotal(futures);

        assertThat(34, is(total));
    }

    private int getTotal(List<Future<Integer>> futures) throws InterruptedException, java.util.concurrent.ExecutionException {
        int total = 0;

        for (Future<Integer> f : futures) {
            total = total + f.get();
        }
        return total;
    }

    private List<Future<Integer>> createListOfFutures(int cores, List<List<String>> segments) {
        ExecutorService executorService = Executors.newFixedThreadPool(cores);

        List<Future<Integer>> futures = new ArrayList<>();

        Callable<Integer> callable = null;
        for (List<String> s : segments) {
            callable = new Callable() {
                @Override
                public Integer call() throws Exception {
                    return (int) s.stream().filter(w -> w.length() > 12).count();
                }
            };
            Future<Integer> futureInt = executorService.submit(callable);
            futures.add(futureInt);
        }
        return futures;
    }

    private List<List<String>> splitWordsListIntoNumberOfCoresSizedList(List<String> words, int cores, int wordCount) {
        List<List<String>> segments = new ArrayList();
        int segmentsSize = wordCount / cores;
        List<String> segment = null;
        int counter = 0;

        for (int i = 0; i < cores; i++) {
            segment = new ArrayList();
            segment.addAll(words.subList(counter, counter + segmentsSize));
            counter = counter + segmentsSize;
            segments.add(segment);
        }
        return segments;
    }


    @Test
    public void shouldNotCallFilterAfter5thTime() throws Exception {
        //extract list of words
        List<String> words = splitWords();
        long count = words.stream().filter(filterLongWords()).limit(5).count();
        System.out.println("Count:" + count);


    }

    private Predicate<String> filterLongWords() {

        return w -> {
            if (w.length() > 12) {
                System.out.println("Log filterLongWords() ");
                return true;
            }
            return false;
        };
    }


    @Test
    public void parallelStreamShouldBeFasterThanSequentialStream() throws Exception {
        long sequentialStart = System.currentTimeMillis();
        List<String> words = splitWords();
        words.stream().filter(filterLongWords()).count();
        long sequentialEnd = System.currentTimeMillis();
        long totalSequentialTime = sequentialEnd - sequentialStart;

        long parallelStart = System.currentTimeMillis();
        words.parallelStream().filter(filterLongWords()).count();
        long parallelEnd = System.currentTimeMillis();
        long totalParallelTime = parallelEnd - parallelStart;

        System.out.println("totalSequentialTime: " + totalSequentialTime + ", totalParallelTime:" + totalParallelTime);

        assertThat(totalSequentialTime > totalParallelTime, is(true));

    }

    private List<String> splitWords() throws Exception {
        URI uri = getClass().getClassLoader().getResource("alice.txt").toURI();
        String contents = new String(Files.readAllBytes(Paths.get(uri)), StandardCharsets.UTF_8); // Read file into string
        return Arrays.asList(contents.split("[\\P{L}]+"));
    }


}