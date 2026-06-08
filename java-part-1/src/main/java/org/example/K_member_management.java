package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class K_member_management {
    static int totalCnt = 0; //이 프로그램에서 저장할 수 있는 회원 수
    static int memberCnt = 0; //실제 회원 수
    static Scanner sc = new Scanner(System.in);
    static  ArrayList<Member> memberList;

    //확장 도전 과제 4: memberList를 member.dat 파일로 저장(프로젝트 루트/data 에 저장)
    static void saveToFile(ArrayList<Member> memberList){
        File dir = new File("data");
        if(!dir.exists()){
            dir.mkdir();
        }
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/member.dat"))){
            oos.writeObject(memberList);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //확장 도전 과제 4: member.dat 파일을 불러와 memberList에 저장
    @SuppressWarnings("unchecked")
    static ArrayList<Member> loadFromFile(){
        File file = new File("data/member.dat");
        if(!file.exists()){
            return new ArrayList<>();
        }

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/member.dat"))){
            return (ArrayList<Member>) ois.readObject();
        }catch(IOException|ClassNotFoundException e){
            return new ArrayList<>();
        }
    }

    //확장 도전 과제 5 : 입력받은 이메일의 형식 검증
    static boolean isValidEmail(String email){
        return email.matches("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]+");
    }
    //확장 도전 과제 5 : 입력받은 전화번호의 형식 검증
    static boolean isValidPhoneNumber(String phoneNumber){
        return phoneNumber.matches("\\d{3}-\\d{3,4}-\\d{4}");
    }

    //1. printPricePlan : 요금제를 사용자한테 받는 함수
    public static int printPricePlan(){
        System.out.println("[요금제를 선택하세요]");
        System.out.println("[1]Lite : 10명 [2]Basic : 20명 [3]Premium : 30명");

        int plan;
        try{
            String input = sc.nextLine();
            plan = Integer.parseInt(input);
        }catch(NumberFormatException e){
            return 10;
        }
        return plan;
    }

    //2. printMenu : 메뉴 출력
    public static int printMenu(){
        System.out.println("[수행할 업무를 선택하세요 - 현재 회원수 : "+memberList.size()+"/"+totalCnt);
        System.out.println("[1]회원추가 [2]회원조회(메일) [3]회원조회(이름)");
        System.out.println("[4]회원전체조회 [5]회원정보 수정 [6]회원삭제");
        System.out.println("[7]프로그램 종료");

        int menu;
        try{
            String input = sc.nextLine();
            menu = Integer.parseInt(input);
        }catch(NumberFormatException e){
            return 10;
        }
        return menu;
    }

    //1. 회원 추가
    //1-1. 회원이 꽉 찼을 때 -> 추가 X
    //1-2. checkEmail : 이메일은 중복 허용 X
    public static void addMember(String[][] members){
        if(memberList.size()>=totalCnt){
            System.out.println("회원이 꽉 찼습니다.");
            return;
        }

        System.out.println("이름을 입력하세요.");
        String name = sc.nextLine();
        System.out.println("이메일을 입력하세요. 예) aaa@naver.com");
        String email = sc.nextLine();
        if(!isValidEmail(email)){
            System.out.println("이메일의 형식이 잘못되었습니다.");
            return;
        }
        System.out.println("연락처를 입력하세요. 예)010-XXXX-XXXX");
        String phoneNumber = sc.nextLine();
        if(!isValidPhoneNumber(phoneNumber)){
            System.out.println("전화번호의 형식이 잘못되었습니다.");
            return;
        }
        Member newMember = new Member(name,email,phoneNumber);

        if(checkEmail(members,email)){
            System.out.println("이미 존재하는 회원입니다.");
            return;
        }

        memberList.add(newMember);

    }
    //1-2. 이메일 중복 확인
    public static boolean checkEmail(String[][] members,String email){
        for(Member m : memberList){
            if(email.equals(m.email)){
                return true;
            }
        }
        return false;
    }
    
    //2. 회원 조회(이메일로 조회)
    public static void selectEmail(String[][] members){
        System.out.println("이메일을 입력해주세요.");
        String email = sc.nextLine();
        for(Member m : memberList){
            if(email.equals(m.email)){
                System.out.println("[이름] " + m.name +
                        ", [이메일] " + m.email +
                        ", [연락처] " + m.phoneNumber);
                return;
            }
        }
        System.out.println("찾으시는 정보가 없습니다.");
    }

    //3. 회원 조회(이름으로 조회) : 동명이인은 모두 출력
    public static void selectName(String[][] members){
        System.out.println("이름을 입력해주세요.");
        String name = sc.nextLine();
        ArrayList<Member> temp = new ArrayList<Member>();

        for(Member m : memberList){
            if(name.equals(m.name)){
                temp.add(m);
            }
        }
        if(!temp.isEmpty()){
            for(Member m : temp){
                System.out.println("[이름] " + m.name +
                        ", [이메일] " + m.email +
                        ", [연락처] " + m.phoneNumber);
            }
        }
        else{
            System.out.println("찾으시는 정보가 없습니다.");
        }
    }
    //4. 회원 전체 조회
    public static void selectAll(String[][] members){

        for(Member m : memberList){
            System.out.println("[이름] " + m.name +
                    ", [이메일] " + m.email +
                    ", [연락처] " + m.phoneNumber);
        }
    }

    //5. 회원 정보 수정
    public static void updateMember(String[][] members){
        System.out.println("[수정] 이메일을 입력해주세요.");
        String email = sc.nextLine();

        int idx=-1;
        for(int i=0;i<memberList.size();i++){
            if(email.equals(memberList.get(i).email)){
                idx=i;
            }
        }

        if(idx==-1){
            System.out.println("찾으시는 회원이 없습니다.");
            return;
        }

        System.out.println("새로운 이름을 입력해주세요.");
        String newName = sc.nextLine();
        System.out.println("새로운 이메일을 입력하세요. 예) aaa@naver.com");
        String newEmail = sc.nextLine();
        if(!isValidEmail(newEmail)){
            System.out.println("이메일의 형식이 잘못되었습니다.");
            return;
        }
        System.out.println("새로운 연락처를 입력하세요. 예)010-XXXX-XXXX");
        String newPhoneNumber = sc.nextLine();
        if(!isValidPhoneNumber(newPhoneNumber)){
            System.out.println("전화번호의 형식이 잘못되었습니다.");
            return;
        }

        memberList.get(idx).name=newName;
        memberList.get(idx).email=newEmail;
        memberList.get(idx).phoneNumber=newPhoneNumber;

        System.out.println("회원정보가 변경되었습니다.");
    }
    //6. 회원 정보 삭제
    public static void deleteMember(String[][] members){
        System.out.println("[삭제] 이메일을 입력해주세요.");
        String email = sc.nextLine();

        int idx=-1;
        for(int i=0;i<memberList.size();i++){
            if(email.equals(memberList.get(i).email)){
                idx=i;
            }
        }
        if(idx==-1){
            System.out.println("찾으시는 회원이 없습니다.");
            return;
        }

        memberList.remove(idx);
    }
    static void main(String[] args) {
        //프로그램 시작 시, member.dat 파일을 불러와 memberList에 저장
        memberList = loadFromFile();

        //1. printPricePlan
        int pricePlan = printPricePlan();
        totalCnt = pricePlan*10;
        String[][] members = new String[totalCnt][3];

        //2. printMenu
        while(true){
            int menu = printMenu();
            switch(menu){
                case 1 :
                    addMember(members);
                    break;
                case 2:
                    selectEmail(members);
                    break;
                case 3 :
                    selectName(members);
                    break;
                case 4 :
                    selectAll(members);
                    break;
                case 5  :
                    updateMember(members);
                    break;
                case 6 :
                    deleteMember(members);
                    break;
                case 7:
                    //프로그램 종료 시, memberList에 저장된 값을 member.dat파일에 저장
                    saveToFile(memberList);
                    System.out.println("이용해주셔서 감사합니다.");
                    return;
                default:
                    System.out.println("1-7번 사이의 숫자를 입력해주세요.");
            }
        }
    }
}
