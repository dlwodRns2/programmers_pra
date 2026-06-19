package org.example.member_management_db;

import java.util.Scanner;

public class Main {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 요금제 선택
        System.out.println("[요금제] [1]Lite:10 [2]Basic:20 [3]Premium:30");
        PricePlan plan = null;
        while (plan == null) {
            plan = PricePlan.from( raedInt(sc) );
            if (plan == null) {
                System.out.println("1~3 중에서 선택하세요.");
            }
        }
        MemberManager manager = new MemberManager(plan.getCapacity());
        System.out.println("선택: " + plan + " (정원 " + plan.getCapacity() + ")");

        // 메뉴 루프
        while (true) {
            System.out.println("\n[현재 " + manager.size() + "/" + manager.capacity() + "]");
            System.out.println("[1]추가 [2]메일조회 [3]이름조회 [4]전체 [5]수정 [6]삭제 [7]종료");

            int choice = raedInt(sc);

            switch (choice) {
                case 1:
                    if ( manager.isFull() ) {
                        System.out.println("정원이 꽉 찼습니다.");
                        break;
                    }

                    System.out.println("등급 [1]일반 [2]VIP");
                    int grade = raedInt(sc);
                    System.out.println("이름");
                    String name = sc.nextLine();
                    System.out.println("이메일");
                    String email = sc.nextLine();
                    System.out.println("연락처");
                    String phone = sc.nextLine();

                    if (manager.existsEmail(email)) {
                        System.out.println("이미 있는 회원입니다.");
                        break;
                    }

                    Member member = ( grade == 2 )
                            ? new VipMember(name, email, phone)
                            : new NormalMember(name, email, phone);

                    manager.add(member);
                    System.out.println("추가되었습니다.");
                    break;
                case 2:
                    System.out.println("[조회] 이메일");

                    Member byEmail = manager.findByEmail(sc.nextLine());

                    if (byEmail == null)  System.out.println("찾으시는 정보가 없습니다.");
                    else byEmail.printInfo();

                    break;
                case 3:
                    System.out.println("[조회] 이름");

                    Member byName = manager.findByName(sc.nextLine());

                    if (byName == null)  System.out.println("찾으시는 정보가 없습니다.");
                    else byName.printInfo();

                    break;
                case 4:
                    manager.printAll();
                    break;
                case 5:
                    System.out.println("[수정] 이메일 ");
                    String userEmail = sc.nextLine();
                    System.out.println("새 이름 ");
                    String newName = sc.nextLine();
                    System.out.println("새 이메일 ");
                    String newEmail = sc.nextLine();
                    System.out.println("새 연락처 ");
                    String newPhone = sc.nextLine();

                    if ( manager.update(userEmail, newName, newEmail, newPhone) ) System.out.println("수정되었습니다.");
                    else System.out.println("찾으시는 회원이 없습니다.");

                    break;
                case 6:
                    System.out.println("[삭제] 이메일 ");
                    if (manager.delete(sc.nextLine())) System.out.println("삭제되었습니다.");
                    else System.out.println("찾으시는 회원이 없습니다.");

                    break;
                case 7:
                    System.out.println("이용해주셔서 감사합니다.");
                    return;
                default:
                    System.out.println("1~7번 중에서 선택하세요.");
            }

        }

    }

    // 숫자를 안전하게 읽는다. 숫자가 아니면 -1 반환
    static int raedInt(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}
