package com.example.projekt1.Models;

import com.example.projekt1.Validators.Tags;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class Post {
    private int id;
    @NotNull(message = "This field is required")
    @Size(min = 3, message = "Minimal postl lenght is 3 characters")
    private String post_content;
    @NotNull(message = "Please add at least one tag")
    @Tags
    private String tags;
}
