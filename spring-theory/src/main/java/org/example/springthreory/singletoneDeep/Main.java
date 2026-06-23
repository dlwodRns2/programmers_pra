package org.example.springthreory.singletoneDeep;

public class Main {
    static int badMismatch=0;
    static int goodMismatch=0;
    static int daoMismatch=0;

    public static void main(String[] args) throws InterruptedException {
        int N=30;

        //Step1 : 나쁜 서비스, N개 쓰레드 동시 실행
        Thread[] t1 = new Thread[N];

        for(int i=0;i<N;i++){
            final String myName="손님"+i;
            t1[i]=new Thread(()->{
                String s= GreetingServiceBad.getInstance().greet(myName);
                if(!s.equals(myName)){
                   synchronized (Main.class){
                       badMismatch++;
                   }
                }
            });
        }
        for(Thread t : t1){
            t.start();
        }
        for(Thread t : t1){
            t.join();
        }

        //Step2 : 좋은 서비스, N개 쓰레드 동시 실행
        Thread[] t2 = new Thread[N];

        for(int i=0;i<N;i++){
            final String myName="손님"+i;
            t2[i]=new Thread(()->{
                String s= GreetingServiceGood.getInstance().greet(myName);
                if(!s.equals(myName)){
                    synchronized (Main.class){
                        goodMismatch++;
                    }
                }
            });
        }
        for(Thread t : t2){
            t.start();
        }
        for(Thread t : t2){
            t.join();
        }
        System.out.println("[필드에 저장] 엉킴: " + badMismatch + " / " + N);
        System.out.println("[파라미터로]  엉킴: " + goodMismatch + " / " + N);

        //Step3 : UserDao
        UserDao u = UserDao.getInstance();
        Thread[] t3 = new Thread[N];

        for(int i=0;i<N;i++){
            final String myName="손님"+i;
            t3[i]=new Thread(()->{
                String s= GreetingServiceGood.getInstance().greet(myName);
                if(!s.equals(myName)){
                    synchronized (Main.class){
                        daoMismatch++;
                    }
                }
            });
        }
        for(Thread t : t3){
            t.start();
        }
        for(Thread t : t3){
            t.join();
        }
        System.out.println("dao 엉킴: "+daoMismatch+" / "+N);
    }
}
