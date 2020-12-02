package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.AuthorIterface;
import com.example.projekt1.Models.Author;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorManager implements AuthorIterface {

    private static final List<Author> authors = new ArrayList<>();

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
