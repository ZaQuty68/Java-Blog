package com.example.projekt1.Models;

import lombok.Data;

@Data
public class AttachmentDTO {
    private int id, post_id;
    private String filename;
}
