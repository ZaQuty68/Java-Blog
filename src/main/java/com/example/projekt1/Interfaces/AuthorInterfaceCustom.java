package com.example.projekt1.Interfaces;

import com.example.projekt1.Models.Author;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.util.List;

public interface AuthorInterfaceCustom {

    boolean checkAuthor(int id);

    List<Author> findAll();

    Author findById(int id);

    List<Author> findAllByUsername(String username);

    void addAuthor(Author author);
}
