package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.AuthorIterface;
import com.example.projekt1.Models.Author;
import com.example.projekt1.Models.Post;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorManager implements AuthorIterface {

    private static List<Author> authors;

    public AuthorManager() throws FileNotFoundException {
        Reader reader = new BufferedReader(new FileReader("src/main/java/com/example/projekt1/csv/Authors.csv"));
        CsvToBean<Author> csvReader = new CsvToBeanBuilder(reader)
                .withType(Author.class).withSeparator(',').withIgnoreQuotations(false)
                .withIgnoreLeadingWhiteSpace(true).build();
        authors = csvReader.parse();
    }

    @Override
    public void addAuthor(Author author){
        authors.add(author);
    }

    @Override
    public List<Author> getAllAuthors() { return authors; }

    @Override
    public Author getAuthorById(int id){
        Author authorToReturn = null;
        for (Author author: authors){
            if (author.getId() == id){
                authorToReturn = author;
            }
        }
        return authorToReturn;
    }

    @Override
    public void deleteAuthor(int id){
        Author authorToRemove = null;
        for (Author author: authors){
            if (author.getId() == id){
                authorToRemove = author;
            }
        }
        if (authorToRemove != null){
            authors.remove(authorToRemove);
        }
    }
}
