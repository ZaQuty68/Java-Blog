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
            id = ai.findAll().get(ai.findAll().size()-1).getId() + 1;
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

    /*@Override
    public void deleteAttachments(int id){
        List<Attachment> attachmentsToDelete = new ArrayList<>();
        for (Attachment attachment: attachments){
            if (attachment.getId_post() == id){
                attachmentsToDelete.add(attachment);
            }
        }
        if (!attachmentsToDelete.isEmpty()){
            for (Attachment attachment: attachmentsToDelete){
                attachments.remove(attachment);
            }
        }
    }

    @Override
    public void deleteAttachment(int id, String filename){
        List<Attachment> attachmentsToDelete = new ArrayList<>();
        for (Attachment attachment: attachments){
            if(attachment.getId_post() == id){
                if(attachment.getFilename().matches("^" + filename + "$")){
                    attachmentsToDelete.add(attachment);
                }
            }
        }
        if(!attachmentsToDelete.isEmpty()){
            for(Attachment attachment: attachmentsToDelete){
                attachments.remove(attachment);
            }
        }
    }

     */
}
