package org.example;

import java.util.ArrayList;

public class MyHashMap {
    static class Node{
        String key;
        String value;
        //Integer value;
        Node next;

        Node(String key, String value){
            this.key  = key;
            this.value  = value;
        }
    }
    final int LOAD_FACTOR=75;
    private Node[] buckets;
    private int capacity = 3;
    private int size=0;

    //생성자
    public MyHashMap(){
        buckets = new Node[capacity];
    }
    //getter
    public int getCapacity() {
        return capacity;
    }

    public int getIndex(String key){
        return Math.abs(key.hashCode())%capacity;
    }

    public int size(){
        return this.size;
    }

    //모든 key 출력
    public void printAll(){
        for(int i=0;i<capacity;i++){
            Node cur = buckets[i];
            while(cur!=null){
                System.out.print(cur.key+" ");
                cur=cur.next;
            }
        }
        System.out.println();
    }

    //요소 추가
    public void put(String key,String value){
        if(size>(capacity*LOAD_FACTOR/100)){
            System.out.println("size : "+size+" > "+capacity*LOAD_FACTOR/100);
            System.out.println("resize() 시작");
            resize();
        }
        int idx = getIndex(key);
        Node cur = buckets[idx];
        for(Node n = cur;n!=null;n=n.next){
            if(key.equals(n.key)) {
                n.value=value;
                return;
            }
        }

        Node node =new Node(key,value);
        node.next=cur;
        buckets[idx]=node;
        size++;
    }

    //조회
    public String get(String key){
        int idx=getIndex(key);
        Node cur = buckets[idx];
        for(Node n=cur;n!=null;n=n.next){
            if(key.equals(n.key)){
                return n.value;
            }
        }
        return null;
    }

    //키 존재 여부 확인
    public boolean containsKey(String key){
        int idx=getIndex(key);
        Node cur = buckets[idx];
        for(Node n = cur;n!=null;n=n.next){
            if(key.equals(n.key)){
                return true;
            }
        }
        return false;
    }

    //요소 삭제
    public String remove(String key){
        int idx=getIndex(key);
        Node cur = buckets[idx];
        Node prev=null;
        for(Node n = cur;n!=null;n=n.next){
            if(key.equals(n.key)){
                if(n==buckets[idx]){
                    buckets[idx]=n.next;
                }
                else{
                    prev.next=n.next;
                }
                size--;
                return n.value;

            }
            prev=n;
        }
        return null;
    }

    //데이터가 많아지면 리사이즈
    public void resize(){
        size=0;
        int oldCap = capacity;
        Node[] oldBuckets = buckets;

        capacity*=2;
        buckets=new Node[capacity];

        for(int i=0;i<oldCap;i++){
            Node cur = oldBuckets[i];
            while(cur!=null){
                String key = cur.key;
                String value = cur.value;
                put(key,value);
                cur=cur.next;
            }
        }
    }

    //keySet을 반환
    public ArrayList<String> keySet(){
        ArrayList<String> keySet = new ArrayList<>();
        for(int i=0;i<capacity;i++){
            Node cur = buckets[i];
            while(cur!=null){
                keySet.add(cur.key);
                cur=cur.next;
            }
        }
        return keySet;
    }

    
    //values 반환
    public ArrayList<String> values(){
        ArrayList<String> values = new ArrayList<>();
        for(int i=0;i<capacity;i++){
            Node cur = buckets[i];
            while(cur!=null){
                values.add(cur.value);
                cur=cur.next;
            }
        }
        return values;
    }
}
