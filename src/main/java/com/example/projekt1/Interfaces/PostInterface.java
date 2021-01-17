package com.example.projekt1.Interfaces;

import com.example.projekt1.Models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostInterface extends JpaRepository<Post, Integer> {

    List<Post> findAll();

    Post findById(int id);

    void deleteById(int id);
}
