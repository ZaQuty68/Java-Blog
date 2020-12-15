package com.example.projekt1.Interfaces;

import com.example.projekt1.Models.Author;
import com.example.projekt1.Models.Posts_Authors;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.util.List;

public interface Posts_AuthorsInterface {

    void addP_A(Posts_Authors posts_authors);

    List<Posts_Authors> getAllPostsAuthors();

    void deleteByPostId(int id);

    List<Posts_Authors> getByAuthors(List<Author> authors);

    List<Posts_Authors> getByAuthor(Author author);

    void save() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;

}
