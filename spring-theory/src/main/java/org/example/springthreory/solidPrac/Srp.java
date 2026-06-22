package org.example.springthreory.solidPrac;

import java.util.ArrayList;

class Journal{
    private ArrayList<String> entries = new ArrayList<>();
    void add(String text){
        entries.add(text);
    }

    public String getText(){
        StringBuilder sb = new StringBuilder();
        for(String str: entries){
            sb.append("-");
            sb.append(str);
            sb.append("\n");
        }
        return sb.toString();
    }

}
class JournalSaver{
    //Journal의 entries가 int 타입으로 변경되든 그대로든 상관없도록, 데이터 자체도 Journal에서 받아오기
    public void print(Journal j){
        System.out.println(j.getText());
    }
    void saveToFile(Journal j){

    }
}
public class Srp {
    public static void main(String[] args) {
        Journal j = new Journal();
        j.add("오늘은 자바를 배웠다");
        j.add("SOLID는 어렵지만 재밌다");

        JournalSaver js = new JournalSaver();
        js.print(j);
    }

}
