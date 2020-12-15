package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.AttachmentInterface;
import com.example.projekt1.Models.Attachment;
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
    public void save() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try(Writer writer = Files.newBufferedWriter(Paths.get("src/main/java/com/example/projekt1/csv/Attachments.csv"));){
            StatefulBeanToCsv<Attachment> beanToCsv = new StatefulBeanToCsvBuilder(writer).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
            beanToCsv.write(attachments);
        }
    }

    @Override
    public void addAttachment(Attachment attachment){
        attachments.add(attachment);
    }

    @Override
    public List<Attachment> getAllAttachments(){ return attachments; }

    @Override
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
}
