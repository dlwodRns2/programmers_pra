package org.example.stream;

public class Product {
    String name;
    int price;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void print(){
        System.out.println("이름: "+this.name+", 가격: "+this.price);
    }
}
