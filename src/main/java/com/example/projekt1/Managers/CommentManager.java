package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.CommentInterface;
import com.example.projekt1.Models.Comment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentManager implements CommentInterface {

    private static final List<Comment> comments = new ArrayList<>();

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
}
