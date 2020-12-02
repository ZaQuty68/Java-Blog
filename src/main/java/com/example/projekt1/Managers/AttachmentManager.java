package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.AttachmentInterface;
import com.example.projekt1.Models.Attachment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttachmentManager implements AttachmentInterface {

    private static final List<Attachment> attachments = new ArrayList<>();

    @Override
    public void addAttachment(Attachment attachment){
        attachments.add(attachment);
    }

    @Override
    public List<Attachment> getAllAttachments(){ return attachments; }

    @Override
    public List<Attachment> getAttachmentsById(int id){
        List<Attachment> attachmentsToReturn = new ArrayList<>();
        for (Attachment attachment: attachments){
            if (attachment.getId_post() == id){
                attachmentsToReturn.add(attachment);
            }
        }
        return attachmentsToReturn;
    }

    @Override
    public void deleteAttachments(int id){
        List<Attachment> attachmentsToDelete = new ArrayList<>();
        for (Attachment attachment: attachments){
            if (attachment.getId_post() == id){
                attachmentsToDelete.add(attachment);
            }
        }
        if (!attachmentsToDelete.isEmpty()){
            for (Attachment attachment: attachments){
                attachments.remove(attachment);
            }
        }
    }
}
