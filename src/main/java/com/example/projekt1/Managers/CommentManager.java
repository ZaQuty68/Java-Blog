package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.CommentInterface;
import com.example.projekt1.Interfaces.CommentInterfaceCustom;
import com.example.projekt1.Models.Author;
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
    public void addComment(CommentDTO comment, PostManager pm, AuthorManager am){
        Comment commentToSave = new Comment();
        int id;
        if(ci.findAll().isEmpty()){
            id = 1;
        }
        else{
            id = ci.findAll().get(0).getId();
            for(Comment commentId: ci.findAll()){
                if(commentId.getId() > id){
                    id = commentId.getId();
                }
            }
            id++;
        }
        commentToSave.setId(id);
        commentToSave.setComment_content(comment.getComment_content());
        ci.save(commentToSave);
        Author authorToSave = am.findById(comment.getAuthor_id());
        authorToSave.getComments().add(commentToSave);
        am.save(authorToSave);
        Post postToSave = pm.findById(comment.getPost_id());
        postToSave.getComments().add(commentToSave);
        pm.save(postToSave);
    }

    public void editComment(Comment comment, int id){
        Comment commentToSave = ci.findById(id);
        commentToSave.setComment_content(comment.getComment_content());
        ci.save(commentToSave);
    }

    @Override
    public List<Comment> findAll(){ return ci.findAll(); }

    @Override
    public Comment findById(int id){ return ci.findById(id); }

    @Override
    public void deleteById(int id){ ci.deleteById(id); }

    @Override
    public boolean checkById(int id){
        Comment comment = ci.findById(id);
        if(comment == null){
            return false;
        }
        return true;
    }

    @Override
    public boolean checkIfAuthor(int aid, int cid, AuthorManager aum){
        Author author = aum.findById(aid);
        if(!author.getComments().isEmpty()){
            for(Comment comment: author.getComments()){
                if(comment.getId() == cid){
                    return true;
                }
            }
        }
        return false;
    }

}
