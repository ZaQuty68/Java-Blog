package com.example.projekt1.Interfaces;

import com.example.projekt1.Models.Comment;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.util.List;

public interface CommentInterface {

    void addComment(Comment comment);

    List<Comment> getAllComments();

    void deleteCommentsByPostId(int id);

    Comment getCommentById(int id);

    void deleteComment(int id);

    boolean checkComment(int id);

    List<Comment> getCommentsByUsername(String username);

    boolean checkCommentsByUsername(String username);

    void save() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;

}
