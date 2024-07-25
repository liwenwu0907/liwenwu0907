package com.example.demo.entity;

import lombok.Data;

import java.util.List;

@Data
public class DirectoryNode {


    private String value;

    private DirectoryNode prev;

    private List<DirectoryNode> next;


    public DirectoryNode(String value) {
        this.value = value;
    }

    public DirectoryNode(String value, List<DirectoryNode> next) {
        this.value = value;
        this.next = next;
    }
}
