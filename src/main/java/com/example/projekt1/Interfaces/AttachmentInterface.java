package com.example.projekt1.Interfaces;

import com.example.projekt1.Models.Attachment;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.util.List;

public interface AttachmentInterface {

    void addAttachment(Attachment attachment);

    List<Attachment> getAllAttachments();

    void deleteAttachments(int id);

    void deleteAttachment(int id, String filename);

    void save() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;
}
