package org.example.springthreory.ex_5.ex_5_1.domain;

import org.example.springthreory.ex_5.ex_5_1.dao.Level;

public class User {
    private String id;
    private String name;
    private String password;
    private Level level;
    private int login;
    private int recommend;

    //* 레벨 업그레이드 동작을 User 자신이 갖는다
    //  - 다음 레벨이 없으면(GOLD) 잘못된 호출이므로 예외로 분명히 알린다.
    //  - 이렇게 해두면 UserService는 '언제 올릴지'만 판단하고, '어떻게 올릴지'는 User에 맡길 수 있다.
    public void upgradeLevel(){
        this.level= this.level.nextLevel();
    }
    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

