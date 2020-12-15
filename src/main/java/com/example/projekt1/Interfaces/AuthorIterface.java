package com.example.projekt1.Interfaces;

import com.example.projekt1.Models.Author;
import com.example.projekt1.Models.Posts_Authors;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.util.List;

public interface AuthorIterface {

    List<Author> getAllAuthors();

    Author getAuthorById(int id);

    boolean checkAuthor(int id);

    List<Author> getAuthorsByUsername(String pattern);

    void save() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;

}
