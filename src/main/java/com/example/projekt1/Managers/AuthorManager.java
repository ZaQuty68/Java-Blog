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

    public AuthorManager(AuthorIterface ai){
        this.ai = ai;
        //////////////UNLOCK THIS IF U WANT ADMIN TO BE MADE INSIDE PROGRAM
        /*if(ai.findById(0) == null){
            Author admin = new Author();
            admin.setId(0);
            admin.setFirst_name("Sebastian");
            admin.setLast_name("Czyzewski");
            admin.setUsername("Admin");
            admin.setPassword("Admin");
            this.ai.save(admin);
        }

         */
    }

    @Override
    public void addAuthor(Author author){
        Author authorToSave = new Author();
        int id;
        if(ai.findAll().isEmpty()){
            id = 1;
        }
        else{
            id = ai.findAll().get(0).getId();
            for(Author authorId: ai.findAll()){
                if(authorId.getId() > id){
                    id = authorId.getId();
                }
            }
            id++;
        }
        authorToSave.setId(id);
        authorToSave.setUsername(author.getUsername());
        authorToSave.setFirst_name(author.getFirst_name());
        authorToSave.setLast_name(author.getLast_name());
        authorToSave.setPassword(author.getPassword());
        ai.save(authorToSave);
    }

    @Override
    public boolean checkById(int id){
        Author author = ai.findById(id);
        if(author == null){
            return false;
        }
        return true;
    }
    @Override
    public boolean checkByUsername(String username){
        Author author = ai.findByUsername(username);
        if(author == null){
            return false;
        }
        return true;
    }
    @Override
    public int logIn(String username, String password){
        Author author = ai.findByUsername(username);
        if(author == null){
            return -1;
        }
        if(author.getPassword().matches("^" + password + "$")){
            return author.getId();
        }
        return -1;
    }

    @Override
    public Author findByUsername(String username){ return ai.findByUsername(username); }

    @Override
    public List<Author> findAll(){
        return ai.findAll();
    }

    @Override
    public Author findById(int id){
        return ai.findById(id);
    }

    @Override
    public List<Author> getAuthorsByUsername(String usernameInput){
        List<Author> authors = ai.findAll();
        List<Author> authorsToReturn = new ArrayList<>();
        String[] usernames = usernameInput.split(" ");
        Pattern pattern;
        Matcher matcher;
        boolean matchFound;
        for(Author author: authors){
            for(String username: usernames){
                pattern = Pattern.compile(username, Pattern.CASE_INSENSITIVE);
                matcher = pattern.matcher(author.getUsername());
                matchFound = matcher.find();
                if(matchFound){
                    authorsToReturn.add(author);
                }
            }
        }
        return authorsToReturn;
    }

    @Override
    public void save(Author author){ ai.save(author); }

    @Override
    public void deleteById(int id){ ai.deleteById(id); }


}
