package org.example.memberManagement;

import java.io.Serializable;
import java.util.Collections;

public abstract class Member implements Serializable, Comparable<Member> {
    String name;
    String email;
    String phone;

    public Member(String name, String email, String phoneNumber){
        this.name = name;
        this.email = email;
        this.phone=phoneNumber;
    }

    public String getPhoneNumber() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public abstract String getGrade();
    public abstract String getBenefit();
    public abstract int getMonthlyFee();

    public void printInfo(){
        System.out.println("[" + getGrade() + "] " + name + " / " + email
                + " / " + phone + " (혜택: " + getBenefit() + ")");
    }
    public void update(String name, String email, String phone){
        this.name = name;
        this.email = email;
        this.phone=phone;
    }
    //이름의 알파벳 오름차순 정렬
    public int compareTo(Member other){
        return this.getName().compareTo(other.getName()); // 이름순
    }
}