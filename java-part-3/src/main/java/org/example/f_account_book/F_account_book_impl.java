package org.example.f_account_book;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class F_account_book_impl implements F_account_book {
    private final String DIR = "accountbook";
    private Map<String, List<F_item>> data = new HashMap<>();

    public F_account_book_impl(Scanner sc) {
        File folder = new File(DIR);
        if(!folder.exists()){
            folder.mkdir();
        }
        this.sc = sc;
    }

    private Scanner sc;

    @Override
    public void addAccount() {
//        System.out.println("날짜 입력 (예: 2026-06-18)");
//        String date = sc.nextLine().trim(); //trim() : String 앞뒤 공백 제거

        String date = LocalDate.now().toString();
        File file = new File(DIR, date+".txt");

        int total = 0;
        StringBuilder sb = new StringBuilder();
        //같은 날짜가 이미 있으면 기존 목록에 이어서 추가
        //key가 맵에 있으면 -> 거기 매핑된 값을 반환
        //key가 없으면 -> 두 번째 인자로 넘긴 값을 반환
        List<F_item> list = data.getOrDefault(date, new ArrayList<>());

        while(true){
            System.out.println("항목 이름: ");
            String name= sc.nextLine().trim();
            System.out.println("금액");
            int price = readInt();
            list.add(new F_item(name,price));
            total+=price;

            sb.append(name+" : "+price+"원\n");

            System.out.println(name+" : "+price+"원");
            System.out.println("더 추가할까요? (y/n)");
            if(sc.nextLine().trim().equals("n")){
                break;
            }

            data.put(date,list);
            System.out.println("["+date+"] 등록완료");
            printItem(data.get(date));

        }
        sb.append("합계 : "+total+"원\n");
        System.out.println("합계 : "+total+"원");

        try(FileWriter fw = new FileWriter(file,true)){
            fw.write(sb.toString());
        } catch (IOException e) {
            //throw new RuntimeException(e);
            System.out.println("저장 중 오류: "+e.getMessage());
        }
    }

    @Override
    public void showAccount() {
        File folder = new File(DIR);
        String[] files = folder.list();

        //accountbook 디렉토리에 파일이 아예 존재하지 않으면 종료
        if(files.length==0){
            System.out.println("기록이 없습니다.");
            return;
        }

        //파일 목록 출력(.txt 빼고 출력)
        System.out.println("=== 기록된 날짜 ===");
        for(String name : files){
            if(name.endsWith(".txt")){
                System.out.println(name.replace(".txt",""));
            }
        }

        System.out.println("조회할 날짜를 입력해주세요 예) 2026-06-18");
        String date = sc.nextLine().trim();
        File file = new File(DIR, date+".txt");

        if(file.exists()){
            try(BufferedReader br = new BufferedReader(new FileReader(file))){
                String line;
                while((line=br.readLine())!=null){
                    System.out.println(line);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            System.out.println("해당하는 날짜가 존재하지 않습니다.");
        }

//        if(data.isEmpty()){
//            System.out.println("기록이 없습니다.");
//            return;
//        }
//        ArrayList<String> dates = new ArrayList<>(data.keySet());
//        Collections.sort(dates,Collections.reverseOrder()); //최신순(내림차순) 정
//
//        for(String d: dates){
//            System.out.println(d);
//        }
//
//        System.out.println("조회할 날짜 : ");
//        String d = sc.nextLine().trim();
//        if(!data.containsKey(d)){
//            System.out.println("그런 날짜는 없습니다.");
//            return;
//        }
//        printItem(data.get(d));
    }

    @Override
    public void deleteAll() {
        File folder = new File(DIR);
        String[] files = folder.list();

        //accountbook 디렉토리에 파일이 아예 존재하지 않으면 종료
        if(files.length==0){
            System.out.println("기록이 없습니다.");
            return;
        }

        //파일 목록 출력(.txt 빼고 출력)
        System.out.println("=== 기록된 날짜 ===");
        for(String name : files){
            if(name.endsWith(".txt")){
                System.out.println(name.replace(".txt",""));
            }
        }

        System.out.println("삭제할 날짜를 입력해주세요 예) 2026-06-18");
        String date = sc.nextLine().trim();
        File file = new File(DIR, date+".txt");

        if(file.exists()){
            if(file.delete()){
                System.out.println("삭제되었습니다.");
            }else {
                System.out.println("삭제 실패했습니다.");
            }
        }else{
            System.out.println("그런 날짜가 없습니다.");
        }
//        System.out.println("전체 삭제할 날짜 입력 : ");
//        String date = sc.nextLine().trim();

//        if(data.containsKey(date)){
//            data.remove(date);
//            System.out.println("date: "+" 삭제가 되었습니다.");
//        }else{
//            System.out.println("그런 날짜가 없습니다.");
//        }
    }

    @Override
    public void deleteItem() {
        System.out.println("삭제할 날짜 입력 : ");
        String date = sc.nextLine().trim();

        if(!data.containsKey(date)){
            System.out.println("그런 날짜가 없습니다.");
            return;
        }

        List<F_item> items = data.get(date);
        for(int i=0;i<items.size();i++){
            F_item item = items.get(i);
            System.out.println((i+1)+". "+item.getName()+" : "+ item.getPrice()+"원");
        }
        System.out.println("삭제될 번호");
        int no = readInt();
        if(no<1||no>items.size()){
            System.out.println("잘못된 번호입니다.");
            return;
        }

        F_item removed = items.remove(no-1);
        System.out.println(removed+"삭제되었습니다.");

        if(items.isEmpty()){
            data.remove(date);
            System.out.println("("+date+") 의 항목이 모두 사라져 날짜도 삭제됨");
        }
    }
    private int readInt(){
        while(true){
            try{
                return Integer.parseInt(sc.nextLine());

            }catch(NumberFormatException e){
                System.out.println("숫자로 다시 입력");
            }
        }
    }
    private void printItem(List<F_item> list){
        int sum=0;
        for(F_item item : list){
            System.out.println(item.getName()+" : "+item.getPrice()+"원");
            sum+=item.getPrice();
        }
        System.out.println("합계 : "+sum+"원");
    }
}
