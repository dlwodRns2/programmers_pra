package org.example;

// * 형변환
// 변수 또는 상수의 타입을 다른 타입으로 변환하는 것
// 기본형에서 boolean을 제외한 나머지 타입들은 서로 형변환이 가능하다.

// * 암시적(자동) 형변환과 명시적(강제) 형변환이 있다.
// 암시적 형변환 : 작은 -> 큰 자료형으로 변환 시 자동으로 이루어짐.
// 명시적 형변환 : 큰 -> 작은 자료형으로 변환 시 명시적으로 해야함.
public class E_casting {
    static void main(String[] args) {
        double d = 3.14;
        System.out.println(d); //3.14

        int score=(int) d; //3
        System.out.println(score);

        //int -> char
        int n=65;
        System.out.println(n); //65

        char c = (char) n;
        System.out.println(c); //A

        //char -> int(얌시적 형변환)
        char c2 = 'A';
        System.out.println(c2); //A

        int n2 = c2;
        System.out.println(n2); //65

        //float -> int
        float f = 3.14f;
        System.out.println(f); //3.14

        int n3=(int)f;
        System.out.println(n3); //3

        //int -> float(암시적 형변환)
        int n4 = 3;
        System.out.println(n4); //3

        float f2 = n4;
        System.out.println(f2); //3.0


        //암시적 형변환
        byte b=10;
        int i=b; //byte to int

        char cc='A';
        int j=cc; //char to int

        int k=100;
        double dd=k; //int to double

        //명시적 형변환
        int i2=100;
        byte b2 = (byte)i; //int to byte

        double ddd= 9.78;
        int j2 = (int)d; //double to int
    }


}
