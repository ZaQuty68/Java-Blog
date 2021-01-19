package com.example.projekt1.Interfaces;

import com.example.projekt1.Models.Tag;

import java.util.List;

public interface TagInterfaceCustom {

    List<Tag> findAll();

    Tag findById(int id);

    void addTag(Tag tag);

    List<Integer> getTagsId(String tags);

    Tag findByTitle(String title);
}
