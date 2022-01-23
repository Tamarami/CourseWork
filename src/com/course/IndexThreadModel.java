package com.course;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import com.course.Helper.*;

public class IndexThreadModel extends Thread {
    ConcurrentHashMap<String, Set<Path>> invertedIndexMap;
    List<Path> filePaths;
    Integer threadNumber, threadsQuantity;

    public IndexThreadModel(ConcurrentHashMap<String, Set<Path>> invertedIndexMap, List<Path> filePaths,
                            Integer threadNumber, Integer maxThreads) {
        this.invertedIndexMap = invertedIndexMap;
        this.filePaths = filePaths;
        this.threadNumber = threadNumber;
        this.threadsQuantity = maxThreads;
    }

    @Override
    public void run() {
        List<Path> files = FileHelper.getPathList(filePaths, threadNumber,threadsQuantity);
        for (Path path : files) {
            Scanner scanner = null;
            try {
                scanner = new Scanner(path.toFile());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (Objects.requireNonNull(scanner).hasNext()) {

                String word = FileHelper.normalizeWord(scanner.next());

                if (!invertedIndexMap.containsKey(word)) {
                    Set<Path> values = new HashSet<>();
                    values.add(path);
                    invertedIndexMap.putIfAbsent(word, values);
                } else {
                    Set<Path> values = invertedIndexMap.get(word);
                    values.add(path);
                    invertedIndexMap.replace(word, values);
                }
            }
        }
    }
}
