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

    void addComment(CommentDTO comment, PostManager pm, AuthorManager aum);

    List<Comment> findAll();

    Comment findById(int id);

    void deleteById(int id);

    boolean checkById(int id);

    boolean checkIfAuthor(int aid, int cid, AuthorManager aum);

    void editComment(Comment comment, int id);
}
