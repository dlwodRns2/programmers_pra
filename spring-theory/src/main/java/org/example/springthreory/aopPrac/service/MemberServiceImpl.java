package org.example.springthreory.aopPrac.service;


public class MemberServiceImpl implements MemberService {
    @Override
    public String register(String id) {
        sleep(50);
        return "등록완료: "+id;
    }
    private void sleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {

        }
    }
}
