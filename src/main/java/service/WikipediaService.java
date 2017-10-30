package service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface WikipediaService {
    Map<String, String> query(List<String> keywords) throws ExecutionException, InterruptedException;
}
