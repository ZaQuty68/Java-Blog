package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.Posts_AuthorsInterface;
import com.example.projekt1.Models.Posts_Authors;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Posts_AuthorsManager implements Posts_AuthorsInterface {

    private static final List<Posts_Authors> pa = new ArrayList<>();

    @Override
    public void addP_A(Posts_Authors posts_authors){
        pa.add(posts_authors);
    }

    @Override
    public  List<Posts_Authors> getAllPostsAuthors(){ return pa; }
}
