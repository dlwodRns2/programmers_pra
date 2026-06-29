package org.example.springthreory.exceptionPrac;

import java.awt.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        FileLogger fl = new FileLogger();
        fl.log("INFO","테스트");

        System.out.println("===== 1) 예외 복구: 재시도 (3번째에 성공) =====");
        DataService ds = new DataService(fl);
        System.out.println("최종 결과: "+ds.fetchWithRetry(new FlakyService(2)));

        System.out.println("===== 2) 예외 복구 실패: 재시도 모두 실패 -> 통보 =====");
        try{
            System.out.println(ds.fetchWithRetry(new FlakyService(99)));
        }catch (Exception e){
            System.out.println("실패 통보: "+e.getMessage());
        }

        System.out.println("===== 3) 예외 전환: 아이디 중복 -> 의미 있는 예외 =====");
        try{
            ds.registerUser("kim");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("원인 보존: "+e.getCause());
        }

        System.out.println("===== 로그 파일 위치 =====");
        System.out.println(fl.getLogFilePath());
    }
}
