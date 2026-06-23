package org.example.springthreory.singletoneBasic;

public class Settings {
    private static Settings instance = null;
    private String theme;

    private Settings(){}
    public static Settings getInstance(){
        if(instance==null){
            instance = new Settings();
        }
        return instance;
    }

    String getTheme(){
        return theme;
    }
    public void setTheme(String s){
        this.theme = s;
    }

    public static void main(String[] args) {
        Settings s1 = instance.getInstance();
        Settings s2 = instance.getInstance();

        s1.setTheme("hihi");
        System.out.println(s1.getTheme());
        System.out.println(s2.getTheme());

        s2.setTheme("hello");
        System.out.println(s1.getTheme());
        System.out.println(s2.getTheme());

        System.out.println(s1==s2);

    }
}
