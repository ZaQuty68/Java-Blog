package com.example.projekt1.Interfaces;

import com.example.projekt1.Models.Posts_Authors;

import java.util.List;

public interface Posts_AuthorsInterface {

    void addP_A(Posts_Authors posts_authors);

    List<Posts_Authors> getAllPostsAuthors();

    List<Posts_Authors> getByPostId(int id);

    List<Posts_Authors> getByAuthorId(int id);

    boolean checkForAuthor(int id);

    boolean checkForPost(int id);
}
