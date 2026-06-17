package org.example.collectionsPrac;

public class MyLinkedListTest {
    static void main(String[] args) {
        MyLinkedList list = new MyLinkedList();

        // --- Step 3 + 4 확인 ---
        list.addLast("가");
        list.addLast("나");
        list.addLast("다");
        System.out.print("addLast 후: ");
        list.printLinks();
        // 기대: [null <- 가 -> 나] [가 <- 나 -> 다] [나 <- 다 -> null]

        // --- Step 5 확인 ---
        list.addFirst("앞");
        System.out.print("addFirst 후: ");
        list.printLinks();
        // 기대: [null <- 앞 -> 가] [앞 <- 가 -> 나] [가 <- 나 -> 다] [나 <- 다 -> null]

        // --- Step 6 확인 ---
        System.out.println("get(2) = " + list.get(2));   // 기대: 나

        // --- Step 7 확인 ---
        list.insert(2, "끼움");
        System.out.print("insert 후: ");
        list.printLinks();
        // 기대: [null <- 앞 -> 가] [앞 <- 가 -> 끼움] [가 <- 끼움 -> 나] [끼움 <- 나 -> 다] [나 <- 다 -> null]

        //list.remove(0); list.printLinks(); //맨 앞 데이터 삭제
        //list.remove(list.size()-1); list.printLinks(); //맨 뒤 데이터 삭제
        //list.remove(1); list.printLinks();

        MyLinkedList list2 = new MyLinkedList();
        list2.remove(100);
    }
}
class MyLinkedList{
    static class Node{
        String data;
        Node prev;
        Node next;

        Node(String data){
            this.data=data;
        }
    }
    private Node head;
    private Node tail;
    private int size;


    void addLast(String data){
        Node node = new Node(data);
        if(head==null){
            head=node;
            tail=node;
        }else{
            node.prev=tail;
            tail.next=node;
            tail=node;
        }
        size++;
    }
    void printLinks(){
        Node cur=head;
        while(cur!=null){
            String p = (cur.prev==null)?"null":cur.prev.data;
            String n = (cur.next==null)?"null":cur.next.data;
            System.out.print("["+p+" <-"+cur.data+"-> "+n+"]");
            cur=cur.next;
        }
        System.out.println();
    }
    void addFirst(String data){
        Node node = new Node(data);
        if(head==null){
            head=node;
            tail=node;
        }else{
            node.next=head;
            head.prev=node;
            head=node;
        }
        size++;
    }
    private Node nodeAt(int index){
        Node cur = head;
        for(int i=0;i<index;i++){
            cur=cur.next;
        }
        return cur;
    }
    String get(int index){
        return nodeAt(index).data;
    }
    void insert(int index, String data){
        if(index==0){
            addFirst(data);
        }else if(index==size){
            addLast(data);
        }else{
            Node newNode = new Node(data);
            Node after = nodeAt(index);
            Node before=after.prev;

            newNode.next=after;
            newNode.prev=after.prev;
            after.prev=newNode;
            before.next=newNode;
        }
        size++;
    }
    void remove(int index){
        if(size==0){
            //System.out.println("삭제할 데이터가 없습니다.");
            return;
        }
        if(index==0){
            head=head.next;
            head.prev=null;
        }
        else if(index==size-1){
            tail=tail.prev;
            tail.next=null;
        }else{

            Node cur = nodeAt(index);

            Node before = cur.prev;
            Node after = cur.next;

            before.next=after;
            after.prev=before;
        }
        size--;
    }
    int size(){
        return this.size;
    }
}
