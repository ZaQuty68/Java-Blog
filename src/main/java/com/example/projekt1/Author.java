package com.example.projekt1;

import lombok.Data;

import java.util.ArrayList;

@Data
public  class Author {
    private int id;
    private String first_name, last_name, username;
    private ArrayList<Post> posts;
}
