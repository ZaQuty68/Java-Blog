package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.TagInterface;
import com.example.projekt1.Interfaces.TagInterfaceCustom;
import com.example.projekt1.Models.Tag;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            id = ti.findAll().get(ti.findAll().size()-1).getId() + 1;
        }
        tagToSave.setId(id);
        tagToSave.setTitle(tag.getTitle());
        ti.save(tag);
    }

    @Override
    public List<Tag> findAll(){ return ti.findAll(); }

    @Override
    public Tag findById(int id) { return ti.findById(id); }

    @Override
    public List<Integer> getTagsId(String tags){
        List<Integer> tagsId = new ArrayList<>();
        String[] titles = tags.split(" ");
        for(String title: titles){
            Tag tag = ti.findByTitle(title);
            if(tag == null){
                tag = new Tag();
                tag.setTitle(title);
                addTag(tag);
            }
            tagsId.add(ti.findByTitle(title).getId());
        }
        return tagsId;
    }

    @Override
    public Tag findByTitle(String title){ return ti.findByTitle(title); }
}
