package com.course;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ThreadIndexModel {

    String base_path = "D:\\JavaLabs\\CourseWParallel\\aclImdb\\";
    String[] path_parts = {"test\\neg", "test\\pos", "train\\neg", "train\\pos", "train\\unsup"};

    public int getFileId(Path path) {
        String fileName = path.getFileName().toString();
        return Integer.parseInt(fileName.substring(0, fileName.indexOf("_")));
    }

    public List<Path> getPathsList() throws IOException {
        List<Path> filePaths = new ArrayList<>();
        for (String pathPart : path_parts) {
            if (Objects.equals(pathPart, "train\\unsup")) {
                filePaths.addAll(Files.list(Paths.get(base_path + pathPart))
                        .filter(filePath -> getFileId(filePath) >= 0 && getFileId(filePath) < 1000)
                        .collect(Collectors.toList()));
            } else {
                filePaths.addAll(Files.list(Paths.get(base_path + pathPart))
                        .filter(filePath -> getFileId(filePath) >= 0 && getFileId(filePath) < 250)
                        .collect(Collectors.toList()));
            }
        }
        return filePaths;
    }


}
