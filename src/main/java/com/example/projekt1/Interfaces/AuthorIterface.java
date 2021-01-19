package com.example.projekt1.Interfaces;

import com.example.projekt1.Models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorIterface extends JpaRepository<Author, Integer> {

    List<Author> findAll();

    Author findById(int id);

    Author findByUsername(String username);

}


