package org.example;

// * 연산자
// - 산술 연산자 : +, -, *, /, %
// - 단항 연산자 : ++, --
// - 비교 연산자 : >, <, >=, <=, ==, !=
// - 대입 연산자 : =
// - 기타 : ?:, 복합대입연산자

public class F_operator {
    //산술 연산자
    public static void operExam1(){
        int a=10;
        int b=4;

        System.out.printf("%d + %d = %d\n", a, b, a + b);
        System.out.printf("%d - %d = %d\n", a, b, a - b);
        System.out.printf("%d * %d = %d\n", a, b, a * b);
        System.out.printf("%d / %d = %d\n", a, b, a / b);
        // % 연산자를 출력하기 위해 %%를 사용해야한다.
        // 단일 %는 포맷 지정자로 인식이 안되기 때문
        System.out.printf("%d %% %d = %f\n", a, b, (float)a % b);
    }
    //단항 연산자
    public static void operExam2(){
        //증감 연산자 : 피연산자의 값을 1 증가
        int i=5;
        System.out.println("========= 증감 =========");
        System.out.println("전위형 : " + ++i); // 5 + 1
        System.out.println(i); // 6
        System.out.println("후위형 : " + i++); // 6 -> 6 + 1
        System.out.println(i); // 7
        i=i+1;
        i+=1;
        System.out.println("i = " + i); //9

        //감소 연산자
        i=5;
        System.out.println("========= 감소 =========");
        System.out.println("전위형 : " + --i); //4
        System.out.println(i); //4
        System.out.println("후위형 : " + i--); //4
        System.out.println(i); //3
        i = i - 1;
        i -= 1;
        System.out.println("i = " + i); //1
    }
    //비교 연산자
    public static void operExam3(){
        //자바 Type promotion : 서로 다른 타입을 연산할 때, 더 작은 타입이 큰 타입으로 자동 변환된 후 연산
        System.out.printf("10 == 10.0f \t %b\n", 10 == 10.0f ); //true

        System.out.printf("'0' == 0 \t %b\n", '0' == 0); //false
        System.out.printf("'A' == 65 \t %b\n", 'A' == 65 ); //true
        System.out.printf("'A' > 'B' \t %b\n", 'A' > 'B'); //false
        System.out.printf("'A' + 1 != 'B' \t %b\n", 'A' + 1 != 'B'); //false
    }

    //문자열 비교
    public static void operExam4(){
        //String은 참조형 변수
        //str1,2는 서로 다른 String 객체의 주소를 가짐
        String str1 = new String("Hello");
        String str2 = new String("Hello");
        System.out.println(str1==str2); //false

        //String Constant pool : JVM의 힙 메모리 안에 있는 문자열 전용 저장 공간
        //문자열 리터럴은 String Constant Pool에 저장, 같은 문자열 리터럴은 새로 만들지 않고 재사용
        //str3,4는 같은 문자열 객체를 가리킴
        String str3 = "Hello";
        String str4 = "Hello";
        System.out.println(str3==str4); //true

        //두 문자열을 비교할 때는 비교 연산자 대신 equals() 라는 메서드를 사용해야함
        System.out.println(str1.equals(str2)); //true
    }

    //논리 연산자
    //&& : And, 모두 true면 true
    //|| : OR, 둘 중 하나만 true면 true
    //! : 논리 부정 연산 : 반대
    public static void operExam5(){
        boolean a=true;
        boolean b=false;
        boolean c=true;

        System.out.println("a && b : " + (a && b)); //false
        System.out.println("c && a : " + (c && a)); //true
        System.out.println("a || b : " + (a || b)); //true
        System.out.println("!a : " + !a); //false
    }
    static void main(String[] args) {
        operExam1();
        operExam2();
        operExam3();
        operExam4();
        operExam5();
    }
}
