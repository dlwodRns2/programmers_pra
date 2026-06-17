package org.example;

import javax.swing.plaf.synth.SynthSliderUI;
import javax.swing.plaf.synth.SynthSpinnerUI;
import java.util.Scanner;

class PrintDash extends Thread{
    public void run(){
        for(int i=0;i<300;i++){
            System.out.println("-");
        }
    }
}
class PrintBar extends Thread{
    public void run(){
        for(int i=0;i<300;i++){
            System.out.println("|");
        }
    }
}
class SleepThread extends Thread{
    public void run(){
        for(int i=0;i<300;i++){
            System.out.println("-");
        }
        try {
            //t1쓰레드가 2초 잠든다
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("종료");
    }
}

//3. interrupt() : 쓰레드에 멈춰달라 요청(강제가 아님!)
class CountThread extends Thread{
    public void run(){
        int i=10;
        while(i!=0&&!isInterrupted()){
            System.out.println(i--);
            for(long x=0;x<2_500_000_000L;x++);
        }
        System.out.println("카운트가 종료되었습니다.");

    }
}

//4. interrupt() : 자고 있는 쓰레드 깨우기
class CountSleepThread extends Thread{
    public void run(){
        int i=10;
        while(i!=0&&!isInterrupted()){
            System.out.println(i--);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                System.out.println("자다가 깨어남! (InterruptedException)");
                break;
            }
        }
        System.out.println("카운트가 종료되었습니다.");
    }
}

//5. yield()
class YieldThread extends Thread{
    private String name;


    public YieldThread(String name) {
        this.name=name;
    }
    public void run(){
        for(int i=0;i<5;i++){
            System.out.println(name+" 실행 중. 반복: "+i);
            //숫자 하나 출력 후, 양보
            Thread.yield();
            //양보 후, 500ms 수면 => sleep 덕분에 순서보장이 이루어짐
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

//6. join()
class ManyprintThread extends Thread{
    private String str;

    public ManyprintThread(String name) {
        this.str=name;
    }

    public void run(){
        for(int i=0;i<300;i++){
            System.out.println(str);
        }
    }
}
public class ThreadPrac {
    static void main(String[] args) throws InterruptedException {
        new PrintDash().start();
        new PrintBar().start();

        //2. static sleep()
        Thread t1 = new SleepThread();
        t1.start();

//      t1 쓰레드가 잠드는 것이 아니라, t1.sleep()을 진행중인 main 쓰레드가 잠든다!!!!
//      sleep은 static 메서드임
//      즉, t1 쓰레드가 총 4초 멈추는 것이 아니라 t1,main 쓰레드가 동시에 2초 잠듦 => 실행시간 2초 대
        t1.sleep(2000);

        //3. interrupt() : Thread 객체의 volatile boolean interrupted
        //해당 interrupted 필드 값을 변경함.
        //플래그 값을 변경만 하기 때문에, 개발자가 필드값 변경 여부로 인터럽트 상황에 대한 처리를 실시한다.
        CountThread t2 = new CountThread();
        t2.start();
        new Scanner(System.in).nextLine();
        t2.interrupt(); //while문의 isInterrupted => false => while문 중간 탈출 => t2 쓰레드 종료

        //4. interrupt() : 자고 있는 쓰레드 깨우기
        //sleep()중에 interrupt()가 오면 즉시 InterruptException 발생
        //즉, 3번 처럼 플래그를 확인하고 종료한 것이 아니라, InterruptException으로 인해
        //catch절의 break;가 실행되어 종료된것임.
        CountSleepThread t3 = new CountSleepThread();
        t3.start();
        new Scanner(System.in).nextLine();
        t3.interrupt();

        //5. yield :
        //양보의 개념. 쓰레드가 CPU에 다른 쓰레드 먼저 실행해달라고
        //전달하는 것. => CPU가 무시할 수 있다.
        new YieldThread("쓰레드1").start();
        new YieldThread("쓰레드2").start();
        //실행 결과 : 1->2->1->2...->1->2 로 완벽히 번갈아가며 쓰레드 수행
        //sleep 덕분에 완벽히 번갈아 가며 수행됨
        //sleep 없이 yield()만 실행 시, 순서 보장이 확실히 이루어지지 않음
        //yield에 대해서 CPU가 yield를 무시할 수 있다.

        //6. join
        ManyprintThread mt1 = new ManyprintThread("-");
        ManyprintThread mt2 = new ManyprintThread("|");
        mt1.start();
        mt2.start();

        long start = System.currentTimeMillis();
        try{
            mt1.join();
            mt2.join();
        }catch(InterruptedException e){
            throw new RuntimeException(e);
        }
        System.out.println("소요 시간 : "+(System.currentTimeMillis()-start)+"ms");
        //* 결과
        //join 시, 소요시간 : 4ms => t1, t2 실행하는 동안 try 절에서 계속 기다림 => t1,t2 다 끝나고 이후 코드 실행
        //join 주석 처리 시 : 0ms => t1, t2 실행하는 동안 main 쓰레드가 이후 코드를 수행
    }
}
