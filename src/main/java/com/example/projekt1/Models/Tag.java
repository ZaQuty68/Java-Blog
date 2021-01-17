package com.example.projekt1.Models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Tag")
@Table(name = "tag")
@Data
public class Tag {
    @Id
    private int id;
    private String title;

    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts = new HashSet<>();

    @Override
    public int hashCode(){ return Objects.hash(id, title);
    }
}
