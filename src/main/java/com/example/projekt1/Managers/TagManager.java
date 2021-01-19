package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.TagInterface;
import com.example.projekt1.Interfaces.TagInterfaceCustom;
import com.example.projekt1.Models.Tag;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TagManager implements TagInterfaceCustom {

    public TagInterface ti;

    public TagManager(TagInterface ti){ this.ti = ti; }

    @Override
    public void addTag(Tag tag){
        Tag tagToSave = new Tag();
        int id;
        if(ti.findAll().isEmpty()){
            id = 1;
        }
        else{
            id = ti.findAll().get(0).getId();
            for(Tag tagId: ti.findAll()){
                if(tagId.getId() > id){
                    id = tagId.getId();
                }
            }
            id++;
        }
        tagToSave.setId(id);
        tagToSave.setTitle(tag.getTitle());
        ti.save(tagToSave);
    }

    @Override
    public List<Tag> findAll(){ return ti.findAll(); }

    @Override
    public Tag findById(int id) { return ti.findById(id); }

    @Override
    public List<Integer> getTagsId(String tag1, String tag2, String tag3, String tag4){
        List<Integer> tagsId = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        titles.add(tag1);
        if(!tag2.isEmpty()){
            titles.add(tag2);
        }
        if(!tag3.isEmpty()){
            titles.add(tag3);
        }
        if(!tag4.isEmpty()){
            titles.add(tag4);
        }
        for(String title: titles){
            Tag tag = ti.findByTitle(title);
            if(tag == null){
                tag = new Tag();
                tag.setTitle(title);
                addTag(tag);
                tag = ti.findByTitle(title);
            }
            tagsId.add(tag.getId());
        }
        return tagsId;
    }

    @Override
    public Tag findByTitle(String title){ return ti.findByTitle(title); }

    @Override
    public boolean checkByTitle(String title){
        Tag tag= ti.findByTitle(title);
        if(tag == null){
            return false;
        }
        return true;
    }

    @Override
    public List<Tag> getTagsByTitle(String titleInput){
        List<Tag> tags = ti.findAll();
        List<Tag> tagsToReturn = new ArrayList<>();
        String[] titles = titleInput.split(" ");
        Pattern pattern;
        Matcher matcher;
        boolean matchFound;
        for(Tag tag: tags){
            for(String title: titles){
                pattern = Pattern.compile(title, Pattern.CASE_INSENSITIVE);
                matcher = pattern.matcher(tag.getTitle());
                matchFound = matcher.find();
                if(matchFound){
                    tagsToReturn.add(tag);
                }
            }
        }
        return tagsToReturn;
    }
}
