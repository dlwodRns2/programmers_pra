package org.example.memberManagement;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
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
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("[1]Lite:10 [2]Basic:20 [3]Premium:30");
        int plan;
        while(true){
            try{
                plan=Integer.parseInt(sc.nextLine());
            }catch(NumberFormatException e){
                System.out.println("1~3사이 숫자를 입력하세요.");
                continue;
            }
            if(plan<1||plan>3){
                System.out.println("1~3사이 숫자를 입력하세요.");
                continue;
            }
            break;
        }
        MemberManager manager = new MemberManager(plan * 10,loadFromFile());

        while(true) {
            int menu;
            System.out.println("[수행할 업무를 선택하세요 - 현재 회원수 : " + manager.getCount() + "/" + manager.getCapacity());
            System.out.println("[1]회원추가 [2]회원조회(메일) [3]회원조회(이름)");
            System.out.println("[4]전체조회 [5]수정 [6]회원삭제 [7]종료");

            while (true) {
                try {
                    System.out.println("입력 > ");
                    menu = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("1~7 사이의 숫자를 입력해주세요.");
                    continue;
                }
                if (menu < 1 || menu > 7) {
                    System.out.println("1~7 사이의 숫자를 입력해주세요.");
                    continue;
                }
                break;
            }
            switch (menu) {
                //멤버 추가
                case 1: {
                    if (manager.isFull()) {
                        System.out.println("회원이 꽉 찼습니다.");
                    } else {
                        System.out.println("등급 [1]일반 [2]VIP [3]VVIP");
                        int grade;
                        while (true) {
                            try {
                                grade = Integer.parseInt(sc.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("1~3 사이의 숫자를 입력하세요.");
                                continue;
                            }
                            if (grade < 1 || grade > 3) {
                                System.out.println("1~3 사이의 숫자를 입력하세요.");
                                continue;
                            }
                            break;
                        }

                        System.out.println("이름 > ");
                        String name = sc.nextLine();
                        System.out.println("이메일 > ");
                        String email = sc.nextLine();
                        System.out.println("연락처 > ");
                        String phone = sc.nextLine();

                        if (manager.existsEmail(email)) {
                            System.out.println("이미 존재하는 회원입니다.");
                        } else {
                            Member m;
                            if(grade==1){
                                m = new NormalMember(name,email,phone);
                            }
                            else if(grade==2){
                                m = new VipMember(name,email,phone);
                            }else{
                                m = new VvipMember(name,email,phone);
                            }
                            manager.add(m);
                        }
                    }
                }
                manager.sort(); //입력 후, 정렬
                break;
                //이메일로 회원 조회
                case 2: {
                    System.out.println("이메일 > ");
                    String email = sc.nextLine();
                    Member m = manager.findByEmail(email);

                    if (m == null) {
                        System.out.println("존재하지 않는 회원입니다.");
                    } else {
                        m.printInfo();
                    }
                }
                break;
                //이름으로 회원 조회
                case 3: {
                    System.out.println("이름 > ");
                    String name = sc.nextLine();
                    Member m = manager.findByName(name);

                    if (m == null) {
                        System.out.println("존재하지 않는 회원입니다.");
                    } else {
                        m.printInfo();
                    }
                }
                break;
                //전체 회원 조회
                case 4:
                    manager.printAll();
                    break;
                //회원 정보 수정
                case 5: {
                    System.out.println("현재 사용중인 이메일 > ");
                    String email = sc.nextLine();
                    System.out.println("새로운 이름 > ");
                    String name = sc.nextLine();
                    System.out.println("새로운 이메일 > ");
                    String newEmail = sc.nextLine();
                    System.out.println("새로운 연락처 > ");
                    String phone = sc.nextLine();

                    if (manager.update(email, name, newEmail, phone)) {
                        System.out.println("업데이트 완료했습니다.");
                    } else {
                        System.out.println("업데이트 실패했습니다.");
                    }
                }
                break;
                //회원 정보 삭제
                case 6: {
                    System.out.println("삭제할 회원의 이메일 > ");
                    String email = sc.nextLine();
                    if (manager.delete(email)) {
                        System.out.println("삭제 완료했습니다.");
                    } else {
                        System.out.println("삭제 실패했습니다.");
                    }
                }
                break;
                case 7:
                    System.out.println("프로그램을 종료합니다.");
                    saveToFile(manager.getMembers());
                    return;
            }
        }
    }
}
