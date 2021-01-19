package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.AttachmentInterface;
import com.example.projekt1.Interfaces.AttachmentInterfaceCustom;
import com.example.projekt1.Models.Attachment;
import com.example.projekt1.Models.AttachmentDTO;
import com.example.projekt1.Models.Post;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttachmentManager implements AttachmentInterfaceCustom {

    public AttachmentInterface ai;

    public AttachmentManager(AttachmentInterface ai){ this.ai = ai; }


    @Override
    public void addAttachment(AttachmentDTO attachment, PostManager pm){
        Attachment attachmentToSave = new Attachment();
        int id;
        if(ai.findAll().isEmpty()){
            id = 1;
        }
        else{
            id = ai.findAll().get(0).getId();
            for(Attachment attachmentId: ai.findAll()){
                if(attachmentId.getId() > id){
                    id = attachmentId.getId();
                }
            }
            id++;
        }
        attachmentToSave.setId(id);
        attachmentToSave.setFilename(attachment.getFilename());
        ai.save(attachmentToSave);
        Post postToSave = pm.findById(attachment.getPost_id());
        postToSave.getAttachments().add(attachmentToSave);
        pm.save(postToSave);
    }

    @Override
    public List<Attachment> findAll(){ return ai.findAll(); }

    @Override
    public Attachment findById(int id){ return ai.findById(id); }

    @Override
    public void deleteById(int id){ ai.deleteById(id); }

    @Override
    public Attachment findByFilename(String filename){ return ai.findByFilename(filename); }
}
