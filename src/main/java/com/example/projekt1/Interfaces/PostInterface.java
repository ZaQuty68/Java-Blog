package com.example.projekt1.Interfaces;

import com.example.projekt1.Models.Author;
import com.example.projekt1.Models.Post;
import com.example.projekt1.Models.Posts_Authors;

import java.util.List;

public interface PostInterface {

    void addPost(Post post);

    List<Post> getAllPosts();

    Post getPostById(int id);

    void deletePost(int id);

    boolean checkPost(int id);

    List<Post> getPostsByContent(String pattern);

    List<Post> getPostsByTags(String pattern);

    List<Post> getPostsByAuthors(List<Posts_Authors> pa);
}
