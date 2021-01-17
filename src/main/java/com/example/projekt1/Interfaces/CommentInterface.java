package com.example.projekt1.Interfaces;

import com.example.projekt1.Models.Comment;
import com.example.projekt1.Models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentInterface extends JpaRepository<Comment, Integer> {

    List<Comment> findAll();

    Comment findById(int id);

    void deleteById(int id);
}
