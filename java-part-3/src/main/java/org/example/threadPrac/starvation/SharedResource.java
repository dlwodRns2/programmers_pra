package org.example.threadPrac.starvation;

public class SharedResource {
    private boolean isAvailable=false;
    public synchronized void waitForResource(String threadName){
        while(!isAvailable){
            try {
                System.out.println(threadName+" is waiting for resource...");
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(threadName+" got the resource");
        isAvailable=false;
    }
    public synchronized void makeResourceAvailable(){
        isAvailable=true;
        System.out.println("Resource is now available");
        notifyAll();
    }

    static void main(String[] args) {
        SharedResource resource  = new SharedResource();

        new WorkerThread(resource, "worker1").start();
        new WorkerThread(resource, "worker2").start();
        new WorkerThread(resource, "worker3").start();

        new Thread(()->{
            while(true){
                try{
                    Thread.sleep(2000);
                    resource.makeResourceAvailable();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();


    }
}
