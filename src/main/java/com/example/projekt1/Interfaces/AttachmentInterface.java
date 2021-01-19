package com.example.projekt1.Interfaces;

import com.example.projekt1.Models.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentInterface extends JpaRepository<Attachment, Integer> {

    List<Attachment> findAll();

    Attachment findById(int id);

    void deleteById(int id);

    Attachment findByFilename(String filename);
}
