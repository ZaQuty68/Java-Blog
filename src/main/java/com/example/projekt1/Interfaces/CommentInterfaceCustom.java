package com.example.projekt1.Interfaces;

import com.example.projekt1.Managers.AuthorManager;
import com.example.projekt1.Managers.PostManager;
import com.example.projekt1.Models.Comment;
import com.example.projekt1.Models.CommentDTO;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.util.List;

public interface CommentInterfaceCustom {

    void addComment(CommentDTO comment, PostManager pm, AuthorManager am);

    List<Comment> findAll();

    void deleteCommentsByPostId(int id);

    Comment findById(int id);

    void deleteById(int id);

    boolean checkComment(int id);

}
