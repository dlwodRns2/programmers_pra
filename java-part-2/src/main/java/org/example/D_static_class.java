package org.example;

class OuterClass{
    private int instanceVariable=10;
    private static int staticVariable=20;

    //자바에서는 클래스 안에 다른 클래스를 정의할 수 있다.
    //이 때, 중첩된 클래스가 static으로 선언되면, 해당 클래스는 정적 중첩 클래스가 된다.
    static class StaticNestedClass{
        void display(){
            //정적 중첩 클래스에서는 바깥 클래스의 static 변수에만 접근 가능
            System.out.println(staticVariable);
            //System.out.println(instanceVariable); //접근 안됨
        }
    }

    //일반 중첩 클래스 : 둘 다 접근 가능
    class InnerClass{
        void display(){
            System.out.println(staticVariable);
            System.out.println(instanceVariable);
        }
    }
}
public class D_static_class {
    //* 정적 클래스
    //- 클래스 소속
    //정적 중첩 클래스는 바깥 클래스의 인스턴스와 독립적이다.
    //비정적 중첩 클래스는 바깥 클래스의 인스턴스 없이는 사용할 수 없다.
    //InnerClass : 즉, 이 클래스는 바깥 클래스의 인스턴스와 연결되며, 바깥 클래스의 인스턴스 없이는 사용할 수 없다.
    //StaticNestedClass : 즉 이 클래스는 바깥 클래스의 인스턴스에 속하지 않으며, 바깥 클래스의 인스턴스 없이도 사용할 수 있다.

    //- 인스턴스화
    //정적 중첩 클래스의 인스턴스를 생성할 때는 바깥 클래스의 인스턴스가 필요하지 않다.
    //바깥 클래스의 이름만 사용하여 직접 인스턴스를 생성할 수 있다.

    //- 메모리 관리와 성능
    //[정적 중첩 클래스]
    //바깥 클래스의 인스턴스와 독립적으로 존재하므로, 메모리 사용이 더 효율적일 수 있다.
    //특히 중첩 클래스가 바깥 클래스의 인스턴스 데이터에 접근할 필요가 없는 경우, 정적 중첩 클래스를 사용하는 것이 적합

        //[비정적 중첩 클래스]
    //바깥 클래스의 인스턴스와 연결되기 때문에, 인스턴스당 추가적인 메모리를 소비할 수 있다.
    //따라서, 이 클래스가 바깥 클래스의 인스턴스 데이터에 접근해야 하는 경우에만 사용하는 것이 좋다.

    static void main(String[] args) {
        //Static Nested Class의 인스턴스를 생성하기 위해서는 바깥 클래스의 인스턴스가 필요하지 않음
        //즉 OuterClass의 객체까지 생성할 필요가 없다.
        OuterClass.StaticNestedClass staticNestedClass = new OuterClass.StaticNestedClass();
        staticNestedClass.display();

        //일반 Nested Class의 경우, OuterClass의 객체를 생성해야 nestsed 객체도 생성이 가능
        OuterClass outerClass = new OuterClass();
        OuterClass.InnerClass innerClass  =outerClass.new InnerClass();
        innerClass.display();
    }
}
