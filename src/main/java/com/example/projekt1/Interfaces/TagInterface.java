package com.example.projekt1.Interfaces;

import com.example.projekt1.Models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagInterface extends JpaRepository<Tag, Integer> {

    List<Tag> findAll();

    Tag findById(int id);

}
