package com.example.projekt1;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Post {
    private int id;
    private String post_content;
    private String[] tags;
    private ArrayList<Comment> comments;
    private ArrayList<Attachment> attachments;
}
