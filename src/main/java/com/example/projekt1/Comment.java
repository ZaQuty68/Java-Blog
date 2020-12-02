package com.example.projekt1;

import lombok.Data;

@Data
public class Comment {
    private int id, id_post;
    private String username, comment_content;
}
