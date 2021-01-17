package com.example.projekt1.Models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Attachment")
@Table(name = "attachment")
@Data
public class Attachment {
    @Id
    private int id;
    private String filename;
}
