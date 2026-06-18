package org.example.f_account_book;

public class F_item {
    private String name;
    private int price;

    public F_item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
