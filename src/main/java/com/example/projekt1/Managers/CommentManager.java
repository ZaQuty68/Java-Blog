package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.CommentInterface;
import com.example.projekt1.Interfaces.CommentInterfaceCustom;
import com.example.projekt1.Models.Comment;
import com.example.projekt1.Models.CommentDTO;
import com.example.projekt1.Models.Post;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CommentManager implements CommentInterfaceCustom {

    public CommentInterface ci;

    public CommentManager(CommentInterface ci){
        this.ci = ci;
    }

    @Override
    public void addComment(CommentDTO comment, PostManager pm){
        Comment commentToSave = new Comment();
        commentToSave.setId(comment.getId());
        commentToSave.setUsername(comment.getUsername());
        commentToSave.setComment_content(comment.getComment_content());
        ci.save(commentToSave);
        Post postToSave = pm.findById(comment.getPost_id());
        postToSave.getComments().add(commentToSave);
        pm.save(postToSave);
    }

    @Override
    public List<Comment> findAll(){ return ci.findAll(); }

    @Override
    public void deleteCommentsByPostId(int id){
        /*List<Comment> commentsToDelete = new ArrayList<>();
        List<Comment> comments = ci.findAll();
        for(Comment comment: comments){
            if(comment.getPost().getId() == id){
                commentsToDelete.add(comment);
            }
        }
        if(!commentsToDelete.isEmpty()){
            for(Comment comment: commentsToDelete){
                comments.remove(comment);
            }
        }

         */
    }

    @Override
    public Comment findById(int id){ return ci.findById(id); }

    @Override
    public void deleteById(int id){ ci.deleteById(id); }

    @Override
    public boolean checkComment(int id){
        List<Comment> comments = ci.findAll();
        for(Comment comment: comments){
            if(comment.getId() == id){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Comment> getCommentsByUsername(String username){
        List<Comment> comments = ci.findAll();
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
        List<Comment> comments = ci.findAll();
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
