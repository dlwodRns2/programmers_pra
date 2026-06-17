package org.example.collectionsPrac;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static java.lang.Math.max;

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

    //도전과제
    //1. 레벨 순회 : 위에서 아래로, 같은 층을 왼쪽부터 방문
    public void bfs(){
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            Node cur = queue.poll();
            System.out.println(cur.value);
            if(cur.left!=null){
                queue.add(cur.left);
            }
            if(cur.right!=null){
                queue.add(cur.right);
            }
        }
    }
    public int fac(int n){
        if(n==1){
            return 1;
        }
        else{
            return n*fac(n-1);
        }
    }
    //2. 트리 높이
    public int getHeight(){
       return getHeight(root);
    }
    public int getHeight(Node node){
        if(node == null){
            return 0;
        }
        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);
        return max(leftHeight,rightHeight)+1;
    }
    //3. 노드 개수
    public int getNumNode(){
        int cnt=0;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            Node cur = queue.poll();
            cnt++;
            if(cur.left!=null){
                queue.add(cur.left);
            }
            if(cur.right!=null){
                queue.add(cur.right);
            }
        }
        return cnt;
    }
    //3. 잎 개수
    public int getNumLeaf(){
        int cnt=0;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            Node cur = queue.poll();
            if(cur.left==null&&cur.right==null){
                cnt++;
            }
            if(cur.left!=null){
                queue.add(cur.left);
            }
            if(cur.right!=null){
                queue.add(cur.right);
            }
        }
        return cnt;
    }
    //4. 값 탐색 : 해당 값이 트리에 있는지 반환
    public boolean search(int value){
        Node cur = root;
        while(cur!=null){
            if(value>cur.value){
                cur=cur.right;
            }else if(value<cur.value){
                cur=cur.left;
            }else{
                return true;
            }
        }
        return false;
    }
    //5. 트리 그림 출력
    public void draw(){
        Node cur = root;

    }
    //6. 반복문 버전
    public void preOrderStack(){
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        Node cur = root;

        while(cur!=null || !stack.isEmpty()){
            while(cur!=null){
                stack.push(cur);
                System.out.println(cur.value);
                cur=cur.left;
            }
            cur=stack.pop();
            cur=cur.right;
        }
    }

    public void inOrderStack(){
        Stack<Node> stack = new Stack<>();
        Node cur = root;

        while(cur!=null || !stack.isEmpty()){
            while(cur!=null){
                stack.push(cur);
                cur=cur.left;
            }
            cur=stack.pop();
            System.out.println(cur.value);
            cur=cur.right;
        }
    }
}
