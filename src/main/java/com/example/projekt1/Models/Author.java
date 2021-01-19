package com.example.projekt1.Models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

@Entity(name = "Author")
@Table(name = "author")
@Data
public  class Author {
    @Id
    private int id;
    @NotNull(message = "This field is required!")
    @Size(min = 3, max = 20, message = "First name should be between 3 to 20 characters long!")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only letters are allowed!")
    private String first_name;
    @NotNull(message = "This field is required!")
    @Size(min = 3, max = 20, message = "Last name should be between 3 to 20 characters long!")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only letters are allowed!")
    private String last_name;
    @NotNull(message = "This field is required!")
    @Size(min = 4, max = 20, message = "Username should be between 4 to 20 characters long!")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only letters and numbers are allowed!")
    private String username;
    @NotNull(message = "This field is required!")
    @Size(min = 4, max = 20, message = "Username should be between 4 to 20 characters long!")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only letters and numbers are allowed!")
    private String password;

    @ManyToMany(mappedBy = "authors")
    private Set<Post> posts = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="author_id")
    private List<Comment> comments = new ArrayList<>();

    @Override
    public int hashCode(){
        return Objects.hash(id, first_name, last_name, username);
    }
}
