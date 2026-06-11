package org.example.memberManagement;

import java.util.ArrayList;
import java.util.Collections;

public class MemberManager {
    //private Member[] members;
    private ArrayList<Member> members; //ArrayList<Member>로 변경,  memberCnt => members.size()로 대체
    private int capacity;

    public MemberManager(int capacity, ArrayList<Member> memberList){
        members = memberList;
        this.capacity=capacity;
    }
    //정원이 찼는지 확인
    public boolean isFull(){
        return getCount() == capacity;
    }
    //해당 email이 존재하는지 확인
    public boolean existsEmail(String email){
        for(int i=0;i<members.size();i++){
            if(members.get(i).getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }
    //멤버 추가
    public void add(Member m){
        members.add(m);
    }
    public ArrayList<Member> getMembers(){
        return this.members;
    }
    //현재 멤버 수 반환
    public int getCount(){
        return members.size();
    }
    //최대 인원 반환
    public int getCapacity(){
        return capacity;
    }

    //조회 메서드
    public Member findByEmail(String email){
        for(int i=0;i<members.size();i++){
            if(members.get(i).getEmail().equals(email)){
                return members.get(i);
            }
        }
        return null;
    }

    public Member findByName(String name){
        for(int i=0;i<members.size();i++){
            if(members.get(i).getName().equals(name)){
                return members.get(i);
            }
        }
        return null;
    }
    public void printAll(){
        for(int i=0;i<members.size();i++){
            members.get(i).printInfo();
        }
    }

    //회원 정보 업데이트
    public boolean update(String email, String name, String newEmail, String phone){
        Member m = findByEmail(email);
        if(m==null){
            return false;
        }
        m.update(name,newEmail,phone);
        return true;
    }
    //회원 삭제
    public boolean delete(String email){
        int idx=-1;
        for(int i=0;i<members.size();i++){
            if(members.get(i).getEmail().equals(email)){
                idx=i;
                break;
            }
        }
        if(idx==-1){
            return false;
        }
        members.remove(idx);
        return true;
    }
    public void sort(){
        Collections.sort(members);
    }
}
