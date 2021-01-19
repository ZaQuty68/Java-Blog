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
    @Size(min = 4, max=2500, message = "Comment content should be between 4 and 2500 characters long!")
    @Column(length = 2500)
    private String comment_content;
}
