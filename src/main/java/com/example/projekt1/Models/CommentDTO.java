package com.example.projekt1.Models;

import lombok.Data;

@Data
public class CommentDTO {
    private int id, post_id, author_id;
    private String comment_content;
}
