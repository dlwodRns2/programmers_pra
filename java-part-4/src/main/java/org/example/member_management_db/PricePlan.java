package org.example.member_management_db;

public enum PricePlan {
    LITE(10),
    BASIC(20),
    PREMIUM(30);

    private int capacity;

    PricePlan(int capacity) {
        this.capacity=capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    //사용자가 고른 번호(1~3)로 요금제 찾기
    public static PricePlan from(int choice){
        switch (choice){
            case 1 :
                return LITE;
            case 2:
                return BASIC;
            case 3:
                return PREMIUM;
            default:
                return null;
        }
    }
}