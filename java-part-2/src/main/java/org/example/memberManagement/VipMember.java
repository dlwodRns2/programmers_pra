package org.example.memberManagement;

public class VipMember extends Member{
    public VipMember(String name, String email, String phoneNumber) {
        super(name, email, phoneNumber);
    }

    @Override
    public String getGrade() {
        return "VIP";
    }

    @Override
    public String getBenefit() {
        return "10% 할인 + 무료배송";
    }

    @Override
    public int getMonthlyFee() {
        return 10000;
    }

    @Override
    public int compareTo(Member o) {
        return 0;
    }
}
