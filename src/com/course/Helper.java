package com.course;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Helper {

    public static class FileHelper {

        private static final String basePath = "D:\\JavaLabs\\CourseWParallel\\aclImdb\\";
        private static final String[] pathParts = {"test\\neg","test\\pos","train\\neg","train\\pos","train\\unsup"};

        private static String getBasePath() {
            return basePath;
        }

        private static String[] getPathParts() {
            return pathParts;
        }

        public static int getFileIdentifier(Path path) {
            String fileName = path.getFileName().toString();
            return Integer.parseInt(fileName.substring(0, fileName.indexOf("_")));
        }

        public static List<Path> createPathsList() throws IOException {
            List<Path> filePaths = new ArrayList<>();
            for (String pathPart : getPathParts()) {
                if (Objects.equals(pathPart, "train\\unsup")) {
                    filePaths.addAll(Files.list(Paths.get(getBasePath() + pathPart))
                            .filter(filePath -> getFileIdentifier(filePath) >= 0 && getFileIdentifier(filePath) < 1000)
                            .collect(Collectors.toList()));
                } else {
                    filePaths.addAll(Files.list(Paths.get(getBasePath() + pathPart))
                            .filter(filePath -> getFileIdentifier(filePath) >= 0 && getFileIdentifier(filePath) < 250)
                            .collect(Collectors.toList()));
                }
            }
            return filePaths;
        }

        public static List<Path> getPathList(List<Path> pathList, int threadNumber, int threadsQuantity) {
            int butchSize = pathList.size() / threadsQuantity;
            int from = threadNumber * butchSize;
            int to = threadNumber == (threadsQuantity - 1) ? (pathList.size() - 1) : (threadNumber + 1) * butchSize;
            return pathList.subList(from, to);
        }

        public static String normalizeWord(String word) {
            return word.toLowerCase().replaceAll("[^a-zA-Z]", "");
        }

    }

}
