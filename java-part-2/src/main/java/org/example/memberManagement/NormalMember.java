package org.example.memberManagement;

public class NormalMember extends Member{
    public NormalMember(String name, String email, String phoneNumber) {
        super(name, email, phoneNumber);
    }

    @Override
    public String getGrade() {
        return "일반";
    }

    @Override
    public String getBenefit() {
        return "기본 서비스";
    }


    @Override
    public int getMonthlyFee() {
        return 0;
    }

    @Override
    public int compareTo(Member o) {
        return 0;
    }
}
