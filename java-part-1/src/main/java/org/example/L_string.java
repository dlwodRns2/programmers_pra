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

    //4. toCharArray() : 문자열을 문자 배열로 반환
    public static void exam4(){
        String str = "hello";
        char[] chars = str.toCharArray();
        for(char c : chars){
            System.out.println(c);
        }
    }

    //5. equals(String anotherString) : 두 문자열의 값을 비교하여 true/false 반환
    public static void exam5(){
        String str1="hello";
        String str2="hello";
        System.out.println(str1.equals(str2)); //true
    }

    //6. contains(CharSequence s) : 문자열이 특정 문자열을 포함하고 있는지 확인 true/false 반환
    public static void exam6(){
        String str = "hello";
        System.out.println(str.contains("ell")); //true
    }

    //7. replace(char oldChar, char newChar)
    //replace(CharSequence target, CharSequence replacement)
    //문자열 내의 특정 문자 혹은 문자열을 다른 문자 혹은 문자열로 대체
    public static void exam7(){
        String str = "hello";
        String newStr = str.replace("l", "L");
        System.out.println(newStr); // "heLLo" 출력
    }

    //8. indexOf(String str) 및 lastIndexOf(String str)
    //특정 문자열이 처음 또는 마지막으로 나타나는 위치를 반환
    public static void exam8(){
        String str = "hello";
        int index = str.indexOf("l");
        int lastIndex = str.lastIndexOf("l");
        System.out.println(index); //2
        System.out.println(lastIndex); //3
    }

    //9. StringBuilder 및 StringBuffer
    //StringBuilder와 StringBuffer는 가변(Mutable) 문자열을 다루기 위한 클래스
    //StringBuilder는 성능이 우수하며, StringBuffer는 Thread-safe
    public static void exam9(){
        StringBuilder sb = new StringBuilder();
        sb.append("hello");
        sb.append("world");
        String result = sb.toString();
        System.out.println(result);

    }

    //10. reverse() : 문자열을 뒤집는다.
    //String 자체는 reverse 메서드 없음. StringBuider에서 reverse 가능
    public static void exam10(){
        StringBuilder sb = new StringBuilder("hello");
        String result = sb.reverse().toString();
        System.out.println(result); //"olleh" 출력
    }

    //11. compareTo(String anotherString)
    //두 문자열을 사전적으로 비교하여, 현재 문자열이 더 빠르면 음수, 같으면 0, 더 느리면 양수를 반환
    public static void exam11(){
        String str1 = "apple";
        String str2 = "banana";

        int resultIdx = str1.compareTo(str2);
        System.out.println(resultIdx); //-1 출력
    }

    //12. toLowerCase(), toUpperCase()
    //문자열을 소문자 혹은 대문자로 변환
    public static void exam12(){
        String str = "Hello";
        String lower = str.toLowerCase();
        String upper = str.toUpperCase();
        System.out.println(lower); //"hello" 출력
        System.out.println(upper); //"HELLO" 출력
    }
    static void main(String[] args) {
        exam12();
    }
}
