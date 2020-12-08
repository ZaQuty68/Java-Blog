package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.CommentInterface;
import com.example.projekt1.Models.Comment;
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
    public void addComment(Comment comment){
        comments.add(comment);
    }

    @Override
    public List<Comment> getAllComments(){ return comments; }

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
}
