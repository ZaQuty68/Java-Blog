package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.PostInterface;
import com.example.projekt1.Models.Post;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostManager implements PostInterface {

    private static final List<Post> posts = new ArrayList<>();

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
