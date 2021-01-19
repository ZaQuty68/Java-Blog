package com.example.projekt1.Interfaces;

import com.example.projekt1.Models.Author;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.util.List;

public interface AuthorInterfaceCustom {

    boolean checkById(int id);

    boolean checkByUsername(String username);

    int logIn(String username, String password);

    List<Author> findAll();

    Author findById(int id);

    List<Author> findAllByUsername(String username);

    void addAuthor(Author author);

    Author findByUsername(String username);

    void save(Author author);
}
