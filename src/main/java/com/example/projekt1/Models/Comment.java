package com.example.projekt1.Models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity(name = "comment")
@Table(name = "comment")
@Data
public class Comment {
    @Id
    private int id;
    @NotNull(message = "This field is required!")
    @Size(min = 4, max = 20, message = "Username should be between 4 to 20 characters long!")
    @Pattern(regexp = "^[a-zA-Z0-9]+", message = "Only letters and numbers are allowed!")
    private String username;
    @NotNull(message = "This field is required!")
    @Size(min = 4, message = "Comment content should be at least 4 characters long!")
    @Column(length = 2500)
    private String comment_content;
}
