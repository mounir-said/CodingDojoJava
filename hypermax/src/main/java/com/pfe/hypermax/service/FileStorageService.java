package com.pfe.hypermax.service;

import com.pfe.hypermax.model.SearchResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FileStorageService {
    private final Path storageDirectory;
    private final Path resultsFile;

    public FileStorageService(
            @Value("${mcp.storage.path}") String storagePath,
            @Value("${mcp.storage.filename}") String filename) throws IOException {
        this.storageDirectory = Paths.get(storagePath);
        this.resultsFile = storageDirectory.resolve(filename);

        if (!Files.exists(storageDirectory)) {
            Files.createDirectories(storageDirectory);
        }

        if (!Files.exists(resultsFile)) {
            Files.createFile(resultsFile);
        }
    }

    public void saveSearchResults(String query, List<SearchResult> results) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String header = String.format("\n=== Search Query: '%s' (%s) ===\n", query, timestamp);

        StringBuilder content = new StringBuilder(header);
        for (int i = 0; i < results.size(); i++) {
            SearchResult result = results.get(i);
            content.append(String.format("%d. %s\nURL: %s\nDescription: %s\n\n",
                    i + 1, result.getTitle(), result.getUrl(), result.getDescription()));
        }

        Files.write(resultsFile, content.toString().getBytes(), StandardOpenOption.APPEND);
    }

    public String getSavedResults() throws IOException {
        return Files.readString(resultsFile);
    }
}