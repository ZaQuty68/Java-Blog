package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.Posts_AuthorsInterface;
import com.example.projekt1.Models.Post;
import com.example.projekt1.Models.Posts_Authors;
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
    public void addP_A(Posts_Authors posts_authors){
        pa.add(posts_authors);
    }

    @Override
    public  List<Posts_Authors> getAllPostsAuthors(){ return pa; }
}
