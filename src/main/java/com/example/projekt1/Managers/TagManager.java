package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.TagInterface;
import com.example.projekt1.Interfaces.TagInterfaceCustom;
import com.example.projekt1.Models.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagManager implements TagInterfaceCustom {

    public TagInterface ti;

    public TagManager(TagInterface ti){ this.ti = ti; }

    @Override
    public void addTag(Tag tag){
        Tag tagToSave = new Tag();
        tagToSave.setId(tag.getId());
        tagToSave.setTitle(tag.getTitle());
        ti.save(tag);
    }

    @Override
    public List<Tag> findAll(){ return ti.findAll(); }

    @Override
    public Tag findById(int id) { return ti.findById(id); }
}
