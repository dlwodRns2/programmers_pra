package org.example.springthreory.solidPrac;

import java.util.ArrayList;

interface DiscountPolicy{
    int discount(int price);
}
class BasicDiscount implements DiscountPolicy{
    @Override
    public int discount(int price) {
        return price;
    }
}
class GoldDiscount implements DiscountPolicy{
    @Override
    public int discount(int price) {
        return price*90/100;
    }
}
class VipDiscount implements DiscountPolicy{
    @Override
    public int discount(int price) {
        return price*80/100;
    }
}
class SilverDiscount implements DiscountPolicy{

    @Override
    public int discount(int price) {
        return price*85/100;
    }
}
public class Ocp {
    public static void main(String[] args) {
        ArrayList<DiscountPolicy> arr = new ArrayList<>();
        DiscountPolicy basic = new BasicDiscount(); arr.add(basic);
        DiscountPolicy gold = new GoldDiscount();arr.add(gold);
        DiscountPolicy vip = new VipDiscount();arr.add(vip);
        DiscountPolicy silver = new SilverDiscount();arr.add(silver);

        for(DiscountPolicy dc :arr){
            System.out.println(dc.discount(10000));
        }
    }
}
