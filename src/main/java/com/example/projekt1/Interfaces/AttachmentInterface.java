package com.example.projekt1.Interfaces;

import com.example.projekt1.Models.Attachment;

import java.util.List;

public interface AttachmentInterface {

    void addAttachment(Attachment attachment);

    List<Attachment> getAllAttachments();

    List<Attachment> getAttachmentsById(int id);

    void deleteAttachments(int id);

    boolean checkAttachment(int id);
}
