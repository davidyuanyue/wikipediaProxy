package service;

import remote.WikiClient;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


public class WikipediaServiceMultiThreadImpl implements WikipediaService {

    ExecutorService pool;

    public WikipediaServiceMultiThreadImpl() {
        pool = Executors.newFixedThreadPool(10);

    }
    @Override
    public Map<String, String> query(List<String> keywords) throws ExecutionException, InterruptedException {
        if (keywords == null || keywords.size() ==0) {
            return Collections.emptyMap();
        }

         Map<String, Future<String>> futures = new HashMap<>();
        for (String keyword: keywords) {
            Callable<String> callable = new WikipediaRemoteCallable(keyword);
            Future<String> future = pool.submit(callable);
            futures.put(keyword, future);
        }
        Map<String, String> output = new HashMap<>();
        for (Map.Entry<String, Future<String>> entry :
             futures.entrySet()) {
            output.put(entry.getKey(), entry.getValue().get());
        }

        return output;
    }

    public static class WikipediaRemoteCallable implements Callable {

        private String keyword;

        public WikipediaRemoteCallable(String keyword) {
            this.keyword = keyword;
        }
        public String call() {
            WikiClient wikiClient = new WikiClient();

            return wikiClient.getResponse(keyword);
        }
    }
}
