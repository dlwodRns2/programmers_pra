package org.example.d_memberManagement;


import java.util.ArrayList;
import java.util.List;

public class D_member_manager {

    private final List<Member> members = new ArrayList<>();
    private final int capacity;

    public D_member_manager(int capacity) {
        this.capacity = capacity;
    }

    public boolean isFull() {
        return members.size() >= capacity;
    }

    public boolean existsEmail(String email) {
        for (Member member : members) {
            if (member.getEmail().equals(email)) {
                return true;
            }
        }

        return false;
    }

    public void add(Member member) {
        members.add(member);
    }

    public Member findByEmail(String email) {
        for (Member member : members) {
            if (member.getEmail().equals(email)) {
                return member;
            }
        }
        return null;
    }

    public Member findByName(String name) {
        for (Member member : members) {
            if (member.getName().equals(name)) {
                return member;
            }
        }
        return null;
    }

    public boolean update(String email, String name, String phone, String newEmail) {
        Member member = findByEmail(email);
        if (member != null) {
            member.update(name, newEmail, phone);
            return true;
        }

        return false;
    }

    public boolean delete(String email) {
        Member byEmail = findByEmail(email);
        if (byEmail == null) return false;
        members.remove(byEmail);
        return true;
    }

    public void printAll() {
        if (members.isEmpty()) {
            System.out.println("등록된 회원이 없습니다.");
            return;
        }

        for (Member member : members) {
            member.printInfo();
        }
    }

    public int size() {
        return members.size();
    }

    public int capacity() {
        return capacity;
    }
}