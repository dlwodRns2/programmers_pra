package org.example;

//* super() : 조상 클래스의 생성자
//this()와 마찬가지로 super()역시 생성자이다.
//this()는 같은 클래스의 다른 생성자를 호출하는데 사용되지만,
//super()는 조상 클래스의 생성자를 호출하는데 사용된다.


//자식 클래스의 인스턴스를 생성하면, 자식의 멤버와 부모의 멤버가 모두 합쳐짐
//그래서 자식 클래스의 인스턴스가 부모 클래스의 멤버들을 사용할 수 있는 것
//이 때, 조상 클래스 멤버의 초기화 작업이 수행되어야 하기 때문에,
//자식 클래스의 생성자에서 조상 클래스의 생성자가 실행되어야한다.

//생성자의 첫 줄에서 조상 클래스의 생성자를 호출해야하는 이유는
//자식 클래스의 멤버가 조상 클래스의 멤버를 사용할 수 있으므로
//조상의 멤버들이 먼저 초기화되어 있어야 한다.

//이와 같은 조상 클래스 생성자의 호출은 클래스의 상속관계를 거슬러 올라가면서 계속 반복된다.
//그래서 Object 클래스를 제외한 모든 클래스의 생성자는 첫 줄에 반드시 자신의 다른 생성자 또는 조상의 생성자를 호출해야한다.
//그렇지 않으면 생성자는 첫 줄에 super()을 자동으로 추가한다.
class SuperPoint{
    int x =10;
    int y=20;

    public SuperPoint(int x,int y){
        System.out.println("i'm super");
        this.x  =x;
        this.y=y;
    }
}
class SubPoint3D extends SuperPoint{
    int z=30;

    public SubPoint3D(int x, int y,int z) {
        System.out.println("i'm sub");
        super(x, y);
        this.z=z;
    }
}
public class I_super_constuctor {
    static void main(String[] args) {
        SubPoint3D sub = new SubPoint3D(10,20,30);
    }
}
