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
public class A_collections_list {
    static void main(String[] args) {

    }
}
