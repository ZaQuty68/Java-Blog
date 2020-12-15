package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.Posts_AuthorsInterface;
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

@Service
public class Posts_AuthorsManager implements Posts_AuthorsInterface {

    private static List<Posts_Authors> pa;

    public Posts_AuthorsManager() throws FileNotFoundException {
        Reader reader = new BufferedReader(new FileReader("src/main/java/com/example/projekt1/csv/Posts_Authors.csv"));
        CsvToBean<Posts_Authors> csvReader = new CsvToBeanBuilder(reader)
                .withType(Posts_Authors.class).withSeparator(',').withIgnoreQuotations(false)
                .withIgnoreLeadingWhiteSpace(true).build();
        pa = csvReader.parse();
    }

    @Override
    public void save() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try(Writer writer = Files.newBufferedWriter(Paths.get("src/main/java/com/example/projekt1/csv/Posts_Authors.csv"));){
            StatefulBeanToCsv<Posts_Authors> beanToCsv = new StatefulBeanToCsvBuilder(writer).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
            beanToCsv.write(pa);
        }
    }

    @Override
    public void addP_A(Posts_Authors posts_authors){
        pa.add(posts_authors);
    }

    @Override
    public  List<Posts_Authors> getAllPostsAuthors(){ return pa; }

    @Override
    public void deleteByPostId(int id){
        List<Posts_Authors> parsToDelete = new ArrayList<>();
        for(Posts_Authors par: pa){
            if(par.getId_post() == id){
                parsToDelete.add(par);
            }
        }
        if(!parsToDelete.isEmpty()){
            for(Posts_Authors par: parsToDelete){
                pa.remove(par);
            }
        }
    }

    @Override
    public List<Posts_Authors> getByPostId(int id){
        List<Posts_Authors> parsToReturn = new ArrayList<>();
        for(Posts_Authors par: pa){
            if(par.getId_post() == id){
                parsToReturn.add(par);
            }
        }
        return parsToReturn;
    }

    @Override
    public List<Posts_Authors> getByAuthors(List<Author> authors){
        List<Posts_Authors> parsToReturn = new ArrayList<>();
        for(Author author: authors){
            for(Posts_Authors par: pa){
                if(par.getId_author() == author.getId()){
                    parsToReturn.add(par);
                }
            }
        }
        return parsToReturn;
    }

    @Override
    public List<Posts_Authors> getByAuthor(Author author){
        List<Posts_Authors> parsToReturn = new ArrayList<>();
        for(Posts_Authors par: pa){
            if(par.getId_author() == author.getId()){
                parsToReturn.add(par);
            }
        }
        return parsToReturn;
    }

    @Override
    public boolean checkForAuthor(int id){
        for(Posts_Authors par: pa){
            if(par.getId_author() == id){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkForPost(int id){
        for(Posts_Authors par: pa){
            if(par.getId_post() == id){
                return true;
            }
        }
        return false;
    }
}
