package org.example.memberManagement;

public class VvipMember extends Member{
    public VvipMember(String name, String email, String phoneNumber) {
        super(name, email, phoneNumber);
    }

    @Override
    public String getGrade() {
        return "VVIP";
    }

    @Override
    public String getBenefit() {
        return "20% 할인 + 무료배송";
    }

    @Override
    public int getMonthlyFee() {
        return 50000;
    }

    @Override
    public int compareTo(Member o) {
        return 0;
    }
}
