package org.example.pet;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //여러 마리 : 펫을 배열로 여러 마리 키우기
        ArrayList<Pet> pets = new ArrayList<Pet>();

        Pet pet; //같이 놀 펫
        int menu1; //메뉴 선택 : 펫 생성 / 펫과 놀기

        //선택창 : 펫 생성 / 펫과 놀기
        while(true){
            System.out.println("1. 새로운 펫 생성 2. 같이 놀 펫 선택하기");
            try{
                menu1= Integer.parseInt(sc.nextLine());
            }catch (NumberFormatException e){
                System.out.println("숫자를 입력하세요");
                continue;
            }
            if(menu1<1||menu1>2){
                System.out.println("1~2 사이의 숫자를 입력하세요");
                continue;
            }

            //1. 펫 생성하기
            if(menu1==1) {
                System.out.println("새로운 펫을 생성하세요.");
                System.out.println("1. 반려동물의 이름을 지어주세요");
                String name = sc.nextLine();
                Pet newPet= new Pet(name);
                newPet.showStatus();
                pets.add(newPet);
                continue;

            }
            //2. 같이 놀 펫 선택하기
            else{
                if(pets.size()==0){
                    System.out.println("같이 놀 펫이 없습니다. 펫을 생성해주세요.");
                    continue;
                }
                for(int i=0;i<pets.size();i++){
                    System.out.println((i+1)+"번 펫 "+pets.get(i).getName());
                }
                int selectedNum=-1;
                System.out.println("같이 놀 펫을 선택하세요.");
                System.out.print(">> ");
                while(true){
                    try{
                        selectedNum=Integer.parseInt(sc.nextLine());
                    }catch(NumberFormatException e){
                        System.out.println("숫자를 입력해주세요");
                        continue;
                    }
                    if(selectedNum>pets.size()){
                        System.out.println("올바른 범위의 숫자를 입력하세요.");
                        continue;
                    }
                    selectedNum--;
                    pet = pets.get(selectedNum);
                    pet.showStatus();
                    break;
                }
                if(selectedNum!=-1){
                    break;
                }
            }
        }

        //펫과 놀기
        while(true){
            System.out.println("\n무엇을 할까요? [1]먹이주기 [2]놀아주기 [3]잠자기 [4]상태보기 [5]종료");
            System.out.print("> ");
            int menu;
            try{
                menu = Integer.parseInt(sc.nextLine());
            }catch(NumberFormatException e){
                System.out.println("숫자를 입력해주세요.");
                continue;
            }
            if(menu==1){
                pet.feed();
                pet.showStatus();
            }
            else if(menu==2){
                pet.play();
                pet.showStatus();
            }else if(menu==3){
                pet.sleep();
            }
            else if(menu==4){
                pet.showStatus();
            }else if(menu==5){
                System.out.println("안녕!");
                break;
            }else{
                System.out.println("1~4 중에 골라주세요.");
            }
        }
    }
}
