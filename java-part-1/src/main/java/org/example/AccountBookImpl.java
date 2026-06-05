package org.example;

import java.util.*;

public class AccountBookImpl implements AccountBook{

    private Map<String, List<Item>> data  = new HashMap<>();
    private Scanner sc = new Scanner(System.in);


    @Override
    public void addAccount() {

        System.out.println("날짜 입력 (예: 2024-09-04)");
        String date = sc.nextLine();

        while(true){
            //항목 이름, 금액 입력칸
            System.out.println("항목 이름");
            String name = sc.nextLine();
            System.out.println("금액");
            int price =0;

            //물품 금액 입력
            try{
                String input = sc.nextLine();
                price = Integer.parseInt(input);

            }catch(NumberFormatException e){
                System.out.println("숫자만 입력이 가능합니다.");
                continue;
            }

            //Item 생성
            Item newItem = new Item(name,price);

            //해당 날짜의 기록이 없으면 새로 추가 및 물품 등록
            if(!data.containsKey(date)){
                data.put(date,new ArrayList<Item>());
                data.get(date).add(newItem);
            }
            else{
                data.get(date).add(newItem);
            }

            //물품 추가 등록 여부
            System.out.println("더 추가할까요? (y/n)");
            String nxt= sc.nextLine();

            switch(nxt){
                //y 입력 시, 다음 항목 입력
                case "y":
                    continue;

                //n 입력 시, 등록한 물품 및 합계 출력
                case "n":
                    int total=0;
                    System.out.println("["+date+"] 등록 완료");
                    List<Item> itemList = data.get(date);
                    for(Item item : itemList){
                        System.out.println(item.getName()+ " : "+item.getPrice()+"원");
                        total+=item.getPrice();
                    }
                    System.out.println("합계 : "+total);
                    break;

                //다른 문자 입력 시, 에러 메시지 출력
                default:
                    System.out.println("잘못된 입력입니다. 등록을 종료합니다.");
                    break;
            }
            //종료
            return;
        }
    }

    @Override
    public void showAccount() {

        //가계부가 비어있을 경우 출력 후 종료
        if(data.isEmpty()){
            System.out.println("기록이 없습니다.");
            return;
        }

        showDates();

        //조회할 날짜 입력 및 해당 날짜의 항목 출력
        System.out.println("조회할 날짜 입력 > ");
        String date = sc.nextLine();
        if(!data.containsKey(date)){
            System.out.println("그런 날짜가 없습니다.");
        }
        else{
            int total=0;
            List<Item> itemList = data.get(date);
            for(Item item : itemList){
                System.out.println(item.getName()+ " : "+item.getPrice()+"원");
                total+=item.getPrice();
            }
        }

    }

    @Override
    public void deleteAll() {

        showDates();

        //삭제할 날짜 입력
        System.out.println("날짜를 입력해주세요 > ");
        String date = sc.nextLine();

        //해당 날짜가 있으면 모든 항목 삭제
        if(!data.containsKey(date)){
            System.out.println("해당 날짜가 존재하지 않습니다.");
        }
        else{
            data.remove(date);
            System.out.println("삭제되었습니다.");
        }
    }

    @Override
    public void deleteItem() {

        showDates();

        //삭제할 날짜 입력
        System.out.println("날짜를 입력해주세요 > ");
        String date = sc.nextLine();

        if(!data.containsKey(date)){
            System.out.println("해당 날짜가 존재하지 않습니다.");
        }
        else{
            List<Item> itemList = new ArrayList<Item>(data.get(date));
            for(int i=0;i<itemList.size();i++){
                String name = itemList.get(i).getName();
                int price = itemList.get(i).getPrice();
                System.out.println((i+1) + ". "+ name + " : "+price + "원");
            }

            //삭제할 항목 번호 입력
            System.out.println("삭제할 항목의 번호를 입력해주세요 > ");
            int del = 0;

            try{
                String input = sc.nextLine();
                del = Integer.parseInt(input);

            }catch(NumberFormatException e){
                System.out.println("숫자만 입력이 가능합니다.");
                return;
            }

            //항목이 1개면 날짜까지 삭제
            if(data.get(date).size()==1){
                data.remove(date);
            }
            else{
                data.get(date).remove(del-1);
            }

            System.out.println("삭제되었습니다.");
        }

    }

    //가계부에 기록된 모든 날짜 출력
    public void showDates(){
        //기록한 날짜를 내림차순 출력
        System.out.println("== 기록된 날짜 ==");
        List<String> dates = new ArrayList<>(data.keySet());
        Collections.sort(dates,Collections.reverseOrder());
        for(String date : dates){
            System.out.println(date);
        }
    }

}
