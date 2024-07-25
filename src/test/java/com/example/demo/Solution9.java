package com.example.demo;

import java.util.HashMap;
import java.util.Scanner;

public class Solution9 {

    class TreeNode {
        String curDicName;
        TreeNode father;
        HashMap<String, TreeNode> children;

        TreeNode(String curDicName, TreeNode father) {
            this.curDicName = curDicName;
            this.father = father;
            this.children = new HashMap<>();
        }
    }

    class Tree {
        TreeNode root;
        TreeNode cur;

        Tree() {
            this.root = new TreeNode("/", null);
            this.cur = this.root;
        }

        void mkdir(String dicName) {
            if (!this.cur.children.containsKey(dicName)) {
                this.cur.children.put(dicName, new TreeNode(dicName + "/", this.cur));
            }
        }

        void cd(String dicName) {
            if (dicName.equals("..")) {
                if (this.cur.father != null) {
                    this.cur = this.cur.father;
                }
            } else {
                TreeNode child = this.cur.children.get(dicName);
                if (child != null) {
                    this.cur = child;
                }
            }
        }

        String pwd() {
            StringBuilder path = new StringBuilder();
            TreeNode temp = this.cur;

            while (temp != null) {
                path.insert(0, temp.curDicName);
                temp = temp.father;
            }

            return path.toString();
        }
    }

    public static void main(String[] args) {
        Solution9 solution9 = new Solution9();
        Scanner scanner = new Scanner(System.in);
        Tree tree = solution9.new Tree();
        String lastCommandOutput = "";

        while (true) {
            try {
                String line = scanner.nextLine();

                if (line.isEmpty()) break;

                String[] tmp = line.split(" ");
                String cmdKey = tmp[0];

                if (cmdKey.equals("pwd")) {
                    if (tmp.length != 1) continue;
                    lastCommandOutput = tree.pwd();
                } else if (cmdKey.equals("mkdir") || cmdKey.equals("cd")) {
                    if (tmp.length != 2) continue;

                    String cmdVal = tmp[1];

                    if (!(cmdKey.equals("cd") && cmdVal.equals(".."))) {
                        for (char c : cmdVal.toCharArray()) {
                            if (c < 'a' || c > 'z') {
                                continue;
                            }
                        }
                    }

                    if (cmdKey.equals("mkdir")) {
                        tree.mkdir(cmdVal);
                        lastCommandOutput = "";
                    } else {
                        tree.cd(cmdVal);
                        lastCommandOutput = "";
                    }
                }
            } catch (Exception e) {
                break;
            }
            System.out.println(lastCommandOutput);
        }


    }
}
