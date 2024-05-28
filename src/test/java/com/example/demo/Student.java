package com.example.demo;

import lombok.Data;

@Data
public class Student {

    String name;

    int[] rank;

    public Student(String name, int[] rank) {
        this.name = name;
        this.rank = rank;
    }
}
