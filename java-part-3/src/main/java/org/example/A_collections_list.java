package org.example;

//* List : 순서가 있는 요소들의 컬렉션을 나타내는 인터페이스
//List 인터페이스를 구현하는 대표적인 클래스 : ArrayList, LinkedList, Vector 등
//List는 중복된 요소를 허용하며, 인덱스를 기반으로 요소에 접근.

//* 주요 특징
//1. 순서 유지 : List는 요소들이 추가된 순서를 유지한다.
//2. 인덱스로 접근 : 각 요소는 인덱스를 통해 접근할 수 있다. 인덱스는 0부터 시작
//3. (값의) 중복 허용 : 동일한 값을 가진 요소가 존재할 수 있음.
//4. 유연한 크기 : 구현체는 동적으로 크기를 조절할 수 있다.

//* 주요 메서드
//1. add(E e) : 리스트에 요소 추가
//2. get(int idx) : 해당 인덱스의 값을 리턴
//3. remove(int idx) : 해당 인덱스의 값을 삭제
//4. size() : 현재 List의 크기를 리턴
//5. contains(Object o) : 리스트에 특정 요소가 포함되어 있는지 확인
//6. clear() : 리스트의 모든 요소 삭제

//* 요약
//1. ArrayList : 배열 기반의 List. 인덱스를 통한 빠른 접근 가능(O(1))하지만, 중간 접근/삭제(O(n))가 느리다.
//2. LinkedList : 노드 기반의 List. 삽입/삭제가 빠르지만, 인덱스를 통한 접근이 느리다.
//3. Stack : 후입선출(LIFO) 구조.

// 배열 -> 시작주소를 알면 나머지 주소를 알 수 있다. -> 물리적으로 연결
//   0   1    2    3    4    5 --------> 10000
// |ㄱ| |ㄴ| |ㄷ| |ㅂ| |ㄹ| |ㅁ|

import java.util.*;

// linkedlist -> 논리적으로 연결
// &0(자기주소) | prev null | a | &4 next| (node)
// &4 | &0 | b | &3 next
// &3 | &4 | f | &5 |
// &3 | &4 | c | &9 next
// &9 | &5 | d | null
// ....
public class A_collections_list {
    //1. ArrayList
    public static void exam1(){
        List<String> list = new ArrayList<>();

        //요소 추가
        list.add("apple");
        list.add("banana");
        list.add("orange");
        list.add("grape");
        list.add("watermelon");

        //특정 인덱스에 요소 추가
        list.add(1,"lemon");

        //리스트의 크기 확인
        System.out.println("List size : "+list.size());

        //인덱스를 사용하여 요소 접근
        System.out.println("Apple : "+list.get(0));

        //요소 제거
        list.remove(2);

        //특정 요소가 리스트에 포함되어 있는지 확인
        if(list.contains("banana")){
            System.out.println("banana is in the list");
        }

        //리스트의 모든 요소 제거
        //list.clear();

        //순회 방법 1 : for 루프 사용
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }

        //순회 방법 2
        for(String fruit : list){
            System.out.println(fruit);
        }

        //순회 방법 3 : Iterator 사용
        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()){
            String element = iterator.next();
            System.out.println(element);
        }

        //순회 방법 4 : ListIterator 사용 (양방항 순회 가능)
        ListIterator<String> stringListIterator = list.listIterator();
        //정방향
        System.out.println("정방향 순회");
        while(stringListIterator.hasNext()){
            String element = stringListIterator.next();
            System.out.println(element);
        }
        //역방향
        System.out.println("역방향 순회");
        while(stringListIterator.hasPrevious()){
            String element = stringListIterator.previous();
            System.out.println(element);
        }
    }
    //2. LinkedList
    public static void exam2(){
        List<String> list = new LinkedList<>();

        //추가
        list.add("apple");
        list.add(1,"banana");

//        list.remove(0);
//        list.removeLast();

        //인덱스로 값 조회
        String str = list.get(0);
        System.out.println(str);

        //순회 방법 1 : for 루프 사용
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }

        //순회 방법 2 : 향상된 for 루프 사용
        for(String fruit : list){
            System.out.println(fruit);
        }

        //순회 방법 3 : Iterator 사용
        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()){
            String element = iterator.next();
            System.out.println(element);
        }

        //순회 방법 4 : ListIterator 사용(양방향 순회 가능)
        ListIterator<String> stringListIterator = list.listIterator();
        //정방향
        System.out.println("LinkedList 정방향 순회");
        while(stringListIterator.hasNext()){
            String element = stringListIterator.next();
            System.out.println(element);
        }

        //역방향
        System.out.println("LinkedList 역방향 순회");
        while(stringListIterator.hasPrevious()){
            String element = stringListIterator.previous();
            System.out.println(element);
        }
    }
    //3. Stack : LIFO 후입선출 구조를 따름
    //Vector 기반 : Stack은 Vector 클래스를 상속받아 구현됨
    //주요 메서드(삽입/삭제) : push(), pop()
    //peek() : 맨 위의 요소를 확인
    //empty() : 스택이 비었는지 확인
    public static void exam3(){
        Stack<String> stack = new Stack<>();

        //추가
        stack.push("apple");
        stack.push("banana");
        stack.push("cherry");
        //삭제
        String topElement = stack.pop();
        System.out.println("topElement : "+topElement);

        //Peek
        String peekElement = stack.peek();
        System.out.println("peekElement : "+peekElement);

        //Empty
        boolean isEmpty = stack.isEmpty();
        System.out.println("isEmpty = " + isEmpty);

        //순회 방법 1 : for 루프 사용
        for(int i=0;i<stack.size();i++){
            System.out.println(stack.get(i));
        }
        //순회 방법 2: 향상된 for 루프 사용
        for(String s : stack){
            System.out.println("element : "+s);
        }

        //순회 방법 3 : Iterator 사용
        Iterator<String> iterator = stack.iterator();
        while(iterator.hasNext()){
            String element = iterator.next();
            System.out.println("element = " + element);
        }

        //순회 방법 4 : ListIterator 사용
        ListIterator<String> stringListIterator = stack.listIterator();
        //정방향 순회
        System.out.println("stack 정방향 순회");
        while(stringListIterator.hasNext()){
            String element = stringListIterator.next();
            System.out.println("element = " + element);
        }
        //역방향 순회
        System.out.println("stack 역방향 순회");
        while(stringListIterator.hasPrevious()){
            String element = stringListIterator.previous();
            System.out.println("element = " + element);
        }

        //순회 방법 5 : pop()을 사용한 순회 (스택의 특성 활용)
        while(!stack.isEmpty()){
            String element = stack.pop();
            System.out.println("element = " + element);
        }
    }
    static void main(String[] args) {
        exam3();
    }
}
