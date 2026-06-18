package org.example.threadPrac.waitAndNotify;

class QuestionThread extends Thread{
    private Chat chat;
    private String[] questions = { "Hi", "How are you?", "What are you doing?" };

    public QuestionThread(Chat chat){
        this.chat = chat;
    }
    public void run(){
        for(String q: questions){
            chat.question(q);
            try{
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

class AnswerThread extends Thread{
    private Chat chat;
    private String[] answers = { "Hello", "I'm fine, thank you!", "I'm coding in Java" };

    public AnswerThread(Chat chat){
        this.chat = chat;
    }

    public void run(){
        for(String a: answers){
            chat.answer(a);
            try{
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

public class Chat {
    private boolean flag=false;
    public void question(String msg){
        synchronized (this){
            while(flag){
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Question : "+msg);
            flag=true;
            notify();
        }
    }
    public void answer(String msg){
        synchronized (this){
            while(!flag){
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Answer : "+msg);
            flag=false;
            notify();
        }
    }

    static void main(String[] args) {
        Chat chat = new Chat();
        QuestionThread qt = new QuestionThread(chat);
        AnswerThread at = new AnswerThread(chat);

        qt.start();
        at.start();
    }
}
