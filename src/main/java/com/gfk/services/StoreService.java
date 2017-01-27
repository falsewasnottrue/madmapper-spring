package com.gfk.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class StoreService {

    @Value("${working.dir}")
    private String workingDir;

    @Value("${csv.dir}")
    private String csvDir;

    public List<String> listSpecifications() {
        final File folder = new File(workingDir);
        final File[] listOfFiles = folder.listFiles();
        final List<String> specifications = new ArrayList<>();
        for (final File file : listOfFiles) {
            specifications.add(file.getName());
        }

        return specifications;
    }

    public String load(final String name) throws IOException {
        final StringBuilder sb = new StringBuilder();
        final Path path =  FileSystems.getDefault().getPath(workingDir, name);
        Files.lines(path, StandardCharsets.UTF_8).forEach(sb::append); // s -> sb.append(s));
        return sb.toString();
    }

    public void save(final String name, final String data) throws IOException {
        final Path path = FileSystems.getDefault().getPath(workingDir, name);
        Files.write(path, data.getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public List<String> loadCsv(final String name) throws IOException {
        final List<String> result = new ArrayList<>();
        final Path path =  FileSystems.getDefault().getPath(csvDir, name);
        Files.lines(path, StandardCharsets.ISO_8859_1).forEach(result::add);
        return result;
    }
}
