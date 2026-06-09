package org.example;

//* JVM의 메모리 구조
//1. 메서드 영역 : 프로그램 실행 중 어떤 클래스가 사용되면
//JVM은 해당 클래스의 클래스파일을 읽고 분석하여 클래스에 대한 정보를 이곳에 저장한다.
//이 때, 해당 클래스의 클래스 변수도 이 영역에 함께 생성된다.

//2. 힙 영역 : 인스턴스가 생성되는 공간
//프로그램 실행 중 생성되는 인스턴스는 모두 이곳에 생성된다. 즉, 객체가 생성되는 곳이다.

//3. Call Stack : 호출 스택은 메서드의 작업에 필요한 메모리 공간을 제공한다.
//메서드가 호출되면, 호출스택에 호출된 메서드를 위한 메모리가 할당되며,
//이 메모리는 메서드가 작업을 수행하는 동안 지역변수(매개변수 포함)와 연산으 중간 결과 등을 저장하는데 사용
//메서드가 작업을 마치면 할당되었던 메모리 공간은 반환되어 비워진다.

//- 메서드가 호출되면 수행에 필요한 만큼의 메모리를 스택에 할당받는다.
//- 메서드가 수행을 마치고나면 사용했던 메모리를 반환하고 스택에서 제거된다.

class Data{
    int x=5;
}

public class C_jvm_memory {
    static void main(String[] args) {
        exam4();
    }
    public static void exam1(){
        firstMethod();
    }
    public static void firstMethod(){
        secondMethod();
    }
    public static void secondMethod(){
        System.out.println("Second Method");
    }

    public static void exam2(){
        Data d = new Data();
        System.out.println(d.x);
        changeData(d);
        System.out.println(d.x);
    }

    public static void changeData(Data d){
        d.x = 10;
    }

    //얕은 복사 & 깊은 복사
    public static void exam3(){
        Data d1 = new Data();
        Data d2 = d1; //얕은 복사 : 참조주소만 복사함
        Data d3 = copy(d1); //깊은 복사 : 새로운 객체 생성 후, 내용을 복사

        d1.x = 10;
        System.out.println(d1.x); //10
        System.out.println(d2.x); //10
        System.out.println(d3.x); //5
    }

    //깊은 복사
    public static Data copy(Data d){
        Data temp = new Data();
        temp.x=d.x;
        return temp;
    }

    static void exam4(){
        //* 재귀호출(Recursive Call)
        //메서드의 내부에서 메서드 자신을 다시 호출하는 것
        //재귀호출을 하는 메서드를 재귀 메서드라고 함

        int result = factorial(5);
        System.out.println(result);
    }

    static int factorial(int n){
        //필수 : 기저조건(탈출조건), 없으면 무한 호출
        if(n==1){
            return 1;
        }

        return n * factorial(n-1);
    }
}
