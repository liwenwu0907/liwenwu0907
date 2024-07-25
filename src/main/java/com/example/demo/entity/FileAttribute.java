package com.example.demo.entity;

import lombok.Data;

@Data
public class FileAttribute {

    private int visits;

    private Long timestamp;

    private String fileName;

    private int size;
}
