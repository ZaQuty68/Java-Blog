package com.example.projekt1.Interfaces;

import com.example.projekt1.Models.Author;

import java.util.List;

public interface AuthorIterface {

    void addAuthor(Author author);

    List<Author> getAllAuthors();

    Author getAuthorById(int id);

    void deleteAuthor(int id);
}
