package com.example.projekt1.Models;

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity(name = "Author")
@Table(name = "author")
@Data
public  class Author {
    @Id
    private int id;
    private String first_name, last_name, username;

    @ManyToMany(mappedBy = "authors")
    private Set<Post> posts = new HashSet<>();

    @Override
    public int hashCode(){
        return Objects.hash(id, first_name, last_name, username);
    }
}
