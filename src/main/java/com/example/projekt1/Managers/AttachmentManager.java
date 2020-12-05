package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.AttachmentInterface;
import com.example.projekt1.Models.Attachment;
import com.example.projekt1.Models.Post;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttachmentManager implements AttachmentInterface {

    private static List<Attachment> attachments;

    public AttachmentManager() throws FileNotFoundException {
        Reader reader = new BufferedReader(new FileReader("src/main/java/com/example/projekt1/csv/Attachments.csv"));
        CsvToBean<Attachment> csvReader = new CsvToBeanBuilder(reader)
                .withType(Attachment.class).withSeparator(',').withIgnoreQuotations(false)
                .withIgnoreLeadingWhiteSpace(true).build();
        attachments = csvReader.parse();
    }

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
