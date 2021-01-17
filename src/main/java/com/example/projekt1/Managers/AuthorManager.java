package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.AuthorInterfaceCustom;
import com.example.projekt1.Interfaces.AuthorIterface;
import com.example.projekt1.Models.Author;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthorManager implements AuthorInterfaceCustom {


    public AuthorIterface ai;

    public AuthorManager(AuthorIterface ai){ this.ai = ai; }

    @Override
    public void addAuthor(Author author){
        Author authorToSave = new Author();
        authorToSave.setId(author.getId());
        authorToSave.setUsername(author.getUsername());
        authorToSave.setFirst_name(author.getFirst_name());
        authorToSave.setLast_name(author.getLast_name());
        ai.save(authorToSave);
    }

    @Override
    public boolean checkAuthor(int id){
        List<Author> authors = ai.findAll();
        for(Author author: authors){
            if(author.getId() == id){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Author> findAll(){
        return ai.findAll();
    }

    @Override
    public Author findById(int id){
        return ai.findById(id);
    }

    @Override
    public List<Author> findAllByUsername(String username){
        List<Author> authors = ai.findAll();
        List<Author> authorsToReturn = new ArrayList<>();
        String[] usernames = username.split(" ");
        Pattern pattern;
        Matcher matcher;
        boolean matchFound;
        for(Author author: authors){
            for(String s: usernames){
                pattern = Pattern.compile(s, Pattern.CASE_INSENSITIVE);
                matcher = pattern.matcher(author.getUsername());
                matchFound = matcher.find();
                if(matchFound){
                    authorsToReturn.add(author);
                }
            }
        }
        return authorsToReturn;
    }




}
