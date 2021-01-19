package com.example.projekt1.Interfaces;

import com.example.projekt1.Managers.PostManager;
import com.example.projekt1.Models.Attachment;
import com.example.projekt1.Models.AttachmentDTO;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.util.List;

public interface AttachmentInterfaceCustom {

    void addAttachment(AttachmentDTO attachment, PostManager pm);

    List<Attachment> findAll();

    void deleteById(int id);

    Attachment findById(int id);

    Attachment findByFilename(String filename);

}
