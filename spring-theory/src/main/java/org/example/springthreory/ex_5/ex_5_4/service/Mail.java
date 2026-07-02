package org.example.springthreory.ex_5.ex_5_4.service;

public class Mail {
    private String to;
    private String subject;
    private String text;

    public Mail(String to, String text, String subject) {
        this.to = to;
        this.text = text;
        this.subject = subject;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }
}
