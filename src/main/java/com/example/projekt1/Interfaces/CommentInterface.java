package com.example.projekt1.Interfaces;

import com.example.projekt1.Models.Comment;

import java.util.List;

public interface CommentInterface {

    void addComment(Comment comment);

    List<Comment> getAllComments();

    Comment getCommentById(int id);

    void deleteComment(int id);
}
