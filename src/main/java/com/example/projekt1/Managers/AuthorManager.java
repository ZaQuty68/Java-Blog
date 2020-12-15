package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.AuthorIterface;
import com.example.projekt1.Models.Attachment;
import com.example.projekt1.Models.Author;
import com.example.projekt1.Models.Post;
import com.example.projekt1.Models.Posts_Authors;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public void save() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try(Writer writer = Files.newBufferedWriter(Paths.get("src/main/java/com/example/projekt1/csv/Authors.csv"));){
            StatefulBeanToCsv<Author> beanToCsv = new StatefulBeanToCsvBuilder(writer).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
            beanToCsv.write(authors);
        }
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

    @Override
    public boolean checkAuthor(int id){
        for(Author author: authors){
            if(author.getId() == id){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Author> getAuthorsByPostId(List<Posts_Authors> pa){
        List<Author> authorsToReturn = new ArrayList<>();
        for(Posts_Authors par: pa){
            for(Author author: authors){
                if(author.getId() == par.getId_author()){
                    authorsToReturn.add(author);
                }
            }
        }
        return authorsToReturn;
    }

    @Override
    public List<Author> getAuthorsByUsername(String usernameInput){
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
}
