package org.example;

// * String 클래스의 특징
// 불변성(Immutable)
// String 객체가 생성되면 그 객체의 문자열 값은 변경할 수 없다.
// 문자열을 수정하려면 새로운 String 객체를 생성해야한다.
// * 메모리 효율성
// 같은 값을 가진 String 리터럴은 같은 메모리에서 공유된다.
public class L_string {
    //1. charAt(int index) : 문자열에서 특정 위치에 있는 문자(char)을 반환한다.
    public static void exam1(){
        String str = "hello";
        for(int i=0;i<str.length();i++){
            char c = str.charAt(i);
            System.out.println(c);
        }
    }

    //2. subString(int beginIndex, int endIndex) : 문자열의 특정 부분을 추출하여 반환
    public static void exam2(){
        String str = "algorithm";
        String sub = str.substring(0,5);
        System.out.println(sub);
    }

    //3. split(String regex) : 주어진 정규 표현식을 기준으로 문자열을 분리하여 배열로 반환
    public static void exam3(){
        String str = "one,two,three";
        String[] parts = str.split(",");
        for(String s: parts){
            System.out.println(s);
        }
    }
    static void main(String[] args) {
        exam3();
    }
}
