package com.example.projekt1.Interfaces;

import com.example.projekt1.Managers.AuthorManager;
import com.example.projekt1.Managers.TagManager;
import com.example.projekt1.Models.Post;

import java.util.List;

public interface PostInterfaceCustom {

    void addPost(Post post, List<Integer> authorId, AuthorManager am, List<Integer> tagId, TagManager ti);

    List<Post> findAll();

    Post findById(int id);

    void deletePost(int id);

    boolean checkPost(int id);

    List<Post> getPostsByContent(String pattern);

    List<Post> getPostsByTags(String pattern);


    void save(Post post);

}
