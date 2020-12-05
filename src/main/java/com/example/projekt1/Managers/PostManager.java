package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.PostInterface;
import com.example.projekt1.Models.Post;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class PostManager implements PostInterface {

    private static List<Post> posts;

    public PostManager() throws FileNotFoundException {
        Reader reader = new BufferedReader(new FileReader("src/main/java/com/example/projekt1/csv/Posts.csv"));
        CsvToBean<Post> csvReader = new CsvToBeanBuilder(reader)
                .withType(Post.class).withSeparator(',').withIgnoreQuotations(false)
                .withIgnoreLeadingWhiteSpace(true).build();
        posts = csvReader.parse();
    }

    @Override
    public void addPost(Post post){
        posts.add(post);
    }

    @Override
    public List<Post> getAllPosts(){ return posts; }

    @Override
    public Post getPostById(int id){
        Post postToReturn = null;
        for (Post post: posts){
            if (post.getId() == id){
                postToReturn = post;
            }
        }
        return postToReturn;
    }

    @Override
    public void deletePost(int id){
        Post postToRemove = null;
        for (Post post: posts){
            if (post.getId() == id){
                postToRemove = post;
            }
        }
        if (postToRemove != null){
            posts.remove(postToRemove);
        }
    }
}
