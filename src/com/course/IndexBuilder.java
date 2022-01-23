package com.course;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.course.Helper.*;

public class IndexBuilder {

    private final ConcurrentHashMap<String, Set<Path>> indexMap = new ConcurrentHashMap<>();

    public void build(int threadAmount) throws IOException {
        IndexerThreadModel[] threadArray = new IndexerThreadModel[threadAmount];
        List<Path> pathsList = FileHelper.createPathsList();

        for (int i = 0; i < threadAmount; i++) {
            threadArray[i] = new IndexerThreadModel(indexMap, pathsList, i, threadAmount);
            threadArray[i].start();
        }
        for (int i = 0; i < threadAmount; i++) {
            try {
                threadArray[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String searchIndex(String desiredWord) {
        try {
            String key = FileHelper.normalizeWord(desiredWord);
            Set<Path> result = indexMap.get(key);
            return result.isEmpty() ? "Word not found" : result.toString();
        } catch (NullPointerException npe) {
            return "Word not found";
        }
    }

}
