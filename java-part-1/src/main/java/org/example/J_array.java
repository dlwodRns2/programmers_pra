package org.example;

// * 배열
// 같은 타입의 여러 변수를 하나의 묶음으로 다루는 것
// 변수와 달리 배열은 각 저장 공간이 연속적으로 배치되어 있다.
//int num COKE, CIDER
//&1 &4 &9 //변수
//&1 &5 &9 -> 배열은 연속적으로 배치되어 있다.

// * 배열의 생성
// 타입[] 변수 이름 = new int[크기]

// 배열의 첫 번째 주소를 알면 나머지 원소의 주소를 알 수 있다. (첫 번째 주소, 첫 번째 주소 + 자료형 크기 * 1 ...)
public class J_array {
    //1. 배열 선언 및 출력
    public static void exam1(){
        int[] scores = new int[3]; //램에 연속적으로 주소 할당

        scores[0]= 10;
        scores[1]=20;
        scores[2]=30;

        System.out.println(scores);
        System.out.println(scores[0]);
        System.out.println(scores[1]);
        System.out.println(scores[2]);
    }

    //배열 초기화
    public static void exam2(){
        int[] scores = {10,20,30};
        System.out.println("len : "+scores.length);

        for(int i=0;i<scores.length;i++){
            System.out.println(scores[i]);
        }
    }

    //3. 배열의 for문 사용법
    public static void exam3(){
        int[] scores=new int[3];

        //값 초기화
        for(int i=1;i<=scores.length;i++){
            scores[i-1] = i * 10;
        }
        for(int s : scores){
            System.out.println(s);
        }
    }
    //4. char 배열
    public static void exam4(){
        char[] chars = {'a','b','c','d','e'};
        for(char c : chars){
            System.out.println(c);
        }
    }

    //prac1 : char 배열 역으로 출력하기(내장함수 사용X, 인덱스로 접근)
    public static void practice1(){
        char[] chars = {'a','b','c','d','e'};
        for(int i=chars.length-1;i>=0;i--){
            System.out.println(i + " : " + chars[i]);
        }
    }

    //5. String 배열
    public static void exam5(){
        String[] words = {"apple", "banana","orange"};

        for(String word : words){
            System.out.println(word);
        }
    }
    //6. call by Value(num) vs call by Reference(scores)
    public static void exam6(){
        int[] scores = {10,20,30};
        int num = 20;

        for(int i=0;i<scores.length;i++){
            System.out.println(i+" : "+scores[i]);
        }

        System.out.println("==========");
        exam6_sub(scores,num); //num도 변경

        for(int i=0;i<scores.length;i++){
            System.out.println(i+" : "+scores[i]);
        }

        System.out.println("==========");
        System.out.println("num : " + num);
    }

    //int [] -> new -> 참조형 &... => call by reference
    //int num => 기본형 20
    public static void exam6_sub(int[] scores,int num){
        scores[1]= 90;
        num=90;

    }
    static void main(String[] args) {
        exam6();
    }
}
