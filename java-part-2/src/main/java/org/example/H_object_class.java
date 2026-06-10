package org.example;

//* Object 클래스 - 모든 클래스의 조상
//Object 클래스는 모든 클래스 상속계층도의 최상위에 있는 조상 클래스
//모든 클래스는 Object 클래스를 자동으로 상속받음(컴파일러가 자동으로 extends Object를 추가)
//따라서 자바의 모든 클래스가 Object에 정의된 메서드를 사용이 가능
//=> getClass(), toString(), equals()와 같은 메서드를 바로 사용할 수 있는 이유

class Radio{

}
public class H_object_class {
    static void main(String[] args) {
        Radio radio = new Radio();
        System.out.println(radio.getClass());
    }
}
