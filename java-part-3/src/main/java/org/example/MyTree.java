package org.example;

public class MyTree {
    static class Node{
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }
    Node root;

    public void insert(int value){
        root = insertNode(root,value);
    }
    public Node insertNode(Node node, int value){
        if(node==null){
            return new Node(value);
        }
        if(value<node.value){
            node.left = insertNode(node.left, value);
        }
        else if(value>node.value){
            node.right = insertNode(node.right,value);
        }
        return node;
    }
    //전위 순회 : 루트 -> 왼쪽 -> 오른쪽
    public void preOrder(){
        System.out.println("pre-order start");
        preOrder(root);
    }
    public void preOrder(Node node){
        if(node==null){
            return;
        }
        System.out.println(node.value);
        preOrder(node.left);
        preOrder(node.right);
    }
    //중위 순회 : 왼쪽 -> 루트 -> 오른쪽
    public void inOrder(){
        System.out.println("in-order start");
        inOrder(root);
    }
    public void inOrder(Node node){
        if(node ==null){
            return;
        }
        inOrder(node.left);
        System.out.println(node.value+" ");
        inOrder(node.right);
    }
    //후위 순회 : 왼쪽 -> 오른쪽 -> 루트
    public void postOrder(){
        System.out.println("post-order start");
        postOrder(root);
    }
    public void postOrder(Node node){
        if(node==null){
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.value);
    }
}
