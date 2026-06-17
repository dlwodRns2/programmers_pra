package org.example.c_thread;

//* 데몬 쓰레드
//메인 쓰레드의 작업을 뒤에서 도와주는 보조 쓰레드
//일반 쓰레드가 전부 끝나면, 데몬 쓰레드도 같이 종료됨.

//* 실제 사용
//가비지 컬렉터, 워드 프로세서(자동 저장 기능)
public class C_thread_4_daemon implements Runnable{
    static boolean autoSave = false;

    //3초마다 한 번씩, 자동저장이 켜져있지 않으면 autoSave()를 실행
    //데몬 쓰레드라서 main이 끝나면 함께 자동 저장도니다.

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(3*1000);
                if(autoSave){
                    autoSave();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void autoSave(){
        System.out.println("작업 파일이 자동 저장되었습니다.");
    }
    static void main(String[] args) {
        Thread thread = new Thread(new C_thread_4_daemon());
        thread.setDaemon(true);
        thread.start();

        for(int i=0;i<10;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(i);
            if(i==5){
                autoSave=true;
            }
        }
        System.out.println("프로그램을 종료합니다");
    }
}
