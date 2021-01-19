package com.example.projekt1.Interfaces;

import com.example.projekt1.Models.Tag;

import java.util.List;

public interface TagInterfaceCustom {

    List<Tag> findAll();

    Tag findById(int id);

    void addTag(Tag tag);

    List<Integer> getTagsId(String tag1, String tag2, String tag3, String tag4);

    Tag findByTitle(String title);

    boolean checkByTitle(String title);

    List<Tag> getTagsByTitle(String titleInput);
}
