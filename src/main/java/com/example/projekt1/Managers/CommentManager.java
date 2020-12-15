package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.CommentInterface;
import com.example.projekt1.Models.Comment;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CommentManager implements CommentInterface {

    private static List<Comment> comments;

    public CommentManager() throws FileNotFoundException {
        Reader reader = new BufferedReader(new FileReader("src/main/java/com/example/projekt1/csv/Comments2.csv"));
        CsvToBean<Comment> csvReader = new CsvToBeanBuilder(reader)
                .withType(Comment.class).withSeparator(',').withIgnoreQuotations(false)
                .withIgnoreLeadingWhiteSpace(true).build();
        comments = csvReader.parse();
    }

    @Override
    public void save() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try(Writer writer = Files.newBufferedWriter(Paths.get("src/main/java/com/example/projekt1/csv/Comments2.csv"));){
            StatefulBeanToCsv<Comment> beanToCsv = new StatefulBeanToCsvBuilder(writer).withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
            beanToCsv.write(comments);
        }
    }

    @Override
    public void addComment(Comment comment){
        comments.add(comment);
    }

    @Override
    public List<Comment> getAllComments(){ return comments; }

    @Override
    public void deleteCommentsByPostId(int id){
        List<Comment> commentsToDelete = new ArrayList<>();
        for(Comment comment: comments){
            if(comment.getId_post() == id){
                commentsToDelete.add(comment);
            }
        }
        if(!commentsToDelete.isEmpty()){
            for(Comment comment: commentsToDelete){
                comments.remove(comment);
            }
        }
    }

    @Override
    public Comment getCommentById(int id){
        Comment commentToReturn = null;
        for (Comment comment: comments){
            if (comment.getId() == id){
                commentToReturn = comment;
            }
        }
        return commentToReturn;
    }

    @Override
    public void deleteComment(int id){
        Comment commentToRemove = null;
        for (Comment comment: comments){
            if (comment.getId() == id){
                commentToRemove = comment;
            }
        }
        if (commentToRemove != null){
            comments.remove(commentToRemove);
        }
    }

    @Override
    public boolean checkComment(int id){
        for(Comment comment: comments){
            if(comment.getId() == id){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Comment> getCommentsByUsername(String username){
        List<Comment> commentsToReturn = new ArrayList<>();
        Pattern pattern = Pattern.compile(username, Pattern.CASE_INSENSITIVE);
        Matcher matcher;
        boolean matchFound;
        for(Comment comment: comments){
            matcher = pattern.matcher(comment.getUsername());
            matchFound = matcher.find();
            if(matchFound){
                commentsToReturn.add(comment);
            }
        }
        return commentsToReturn;
    }

    @Override
    public boolean checkCommentsByUsername(String username){
        Pattern pattern = Pattern.compile("^" + username + "$");
        Matcher matcher;
        boolean matchFound, flag=false;
        for(Comment comment: comments){
            matcher = pattern.matcher(comment.getUsername());
            matchFound = matcher.find();
            if(matchFound){
                flag=true;
            }
        }
        return flag;
    }
}
