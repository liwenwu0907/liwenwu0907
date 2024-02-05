package com.example.demo.entity;

import java.util.List;

public class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int val) {
        val = val;
    }

    public Node(int val, List<Node> children) {
        val = val;
        children = children;
    }
}
