package org.example;

import java.util.ArrayList;

public class Main {
    static void main(String[] args) {

        //1. MyHashMap Test
//        MyHashMap map = new MyHashMap();
//
//        //1-1. 요소 추가 : put(String key, String value)
//        map.put("apple","사과");
//        map.put("banana","바나나");
//        map.put("grape","포도");
//        map.put("orange","오렌지");
//
//        //1-2. key를 통한 값 조회 : get(String key)
//        System.out.println(map.get("apple")); //사과
//        System.out.println(map.get("cherry")); //null
//
//        //1-3. 요소 제거 : remove(String key)
//        map.remove("apple");
//        System.out.println(map.get("apple")); //null
//
//        //1-4. 키 존재 여부 확인 : containsKey(String key)
//        System.out.println(map.containsKey("orange")); //true
//        System.out.println(map.containsKey("apple")); //false
//
//        //1-5. 크기 확인 : size()
//        System.out.println(map.size()); //3
//
//        //1-6. 모든 key 조회 : keySet()
//        ArrayList<String> keys = map.keySet();
//        for(String key : keys){
//            System.out.print(key+" ");
//        }
//        System.out.println();
//
//        //1-7. 모든 값 조회 : values()
//        ArrayList<String> values = map.values();
//        for(String v : values){
//            System.out.print(v+" ");
//        }


        //2. MyTree Test
        MyTree tree = new MyTree();
        int[] values2 = {50,30,70,20,40,60,80};
        for(int v:values2){
            tree.insert(v);
        }
        System.out.println("pre-order");
        tree.preOrder();
        System.out.println("in-order");
        tree.inOrder();
        System.out.println("post-order");
        tree.postOrder();
        System.out.println("bfs");
        tree.bfs();

        //search
        System.out.println(tree.search(50)); //true
        System.out.println(tree.search(150)); //false

        //노드 개수 탐색 : getNumNode(), bfs로 노드 개수 탐색
        System.out.println("노드 개수 : "+tree.getNumNode()); //7

        //리프 개수 탐색 : getLeafNode(), bfs로 리프 개수 탐색
        System.out.println("리프 개수 : "+tree.getNumLeaf()); //4

        //트리 높이 : getHeight()
        System.out.println("height : "+tree.getHeight());

        //Stack으로 pre/in-order 구현
        System.out.println("pre-order-stack");
        tree.preOrderStack();

        System.out.println("in-order-stack");
        tree.inOrderStack();

        //3. MyTreeMap Test
//        MyTreeMap myTreeMap = new MyTreeMap();
//        myTreeMap.put("apple",1);
//        myTreeMap.put("banana",2);
//        myTreeMap.put("cherry",3);
//        myTreeMap.put("www",4);
//
//        System.out.println(myTreeMap.get("cherry"));
//        System.out.println(myTreeMap.get("watermelon"));
//
//        myTreeMap.printSorted();
//
//        System.out.println(myTreeMap.containsKey("apple"));
//        System.out.println(myTreeMap.containsKey("watermelon"));
//        System.out.println(myTreeMap.firstKey() + " "+ myTreeMap.lastKey());
//
//        System.out.println(myTreeMap.remove("banana"));
//        myTreeMap.printSorted();
    }

}
