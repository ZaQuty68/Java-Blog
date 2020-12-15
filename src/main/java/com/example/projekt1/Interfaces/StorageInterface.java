package com.example.projekt1.Interfaces;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageInterface {

    void init();

    void store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();
}
