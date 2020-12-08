package com.example.projekt1.Interfaces;

import com.example.projekt1.Models.Post;

import java.util.List;

public interface PostInterface {

    void addPost(Post post);

    List<Post> getAllPosts();

    Post getPostById(int id);

    void deletePost(int id);

    boolean checkPost(int id);
}
