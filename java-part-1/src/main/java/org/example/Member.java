package org.example;

import java.io.Serializable;

public class Member implements Serializable {
    String name;
    String email;
    String phoneNumber;

    public Member(String name, String email, String phoneNumber){

        this.name = name;
        this.email = email;
        this.phoneNumber=phoneNumber;
    }

}
