package org.example.springthreory.exceptionPrac;

import java.sql.SQLException;

public class DataService {
    private final FileLogger logger;

    public DataService(FileLogger fileLogger) {
        this.logger = fileLogger;
    }
    public String fetchWithRetry(FlakyService flaky) throws SQLException {
       int maxRetry=3;
        for(int i=1;i<=3;i++){
            try{
                String result = flaky.fetch();
                logger.log("INFO",i+"번째 시도 성공: "+result);
                return result;
            }catch(SQLException e){
                logger.log("WARN",i+"번째 시도 실패: "+e.getMessage());
            }
        }
        logger.log("ERROR","재시도 "+maxRetry + "회 모두 실패");
        throw new RuntimeException("재시도 "+maxRetry+" 회 모두 실패했습니다."); //통보
    }

    public void aviodByThrows(FlakyService f) throws SQLException {
        f.fetch();
    }
    public void avoidByRethrow(FlakyService f) throws SQLException {
        try{
            f.fetch();
        }catch (SQLException e) {
            logger.log("WARN", "회피: 여기서 처리하지 않고 호출자에게 넘김 - " + e.getMessage());
            throw e;
        }

    }
    void registerUser(String id){
        try{
            insertUser(id);

        } catch (SQLException e) {
            if("23000".equals(e.getSQLState())){
                logger.log("ERROR","아이디 중복: "+id);
                throw new DuplicateUserIdException(id,e);
            }
            logger.log("ERROR", "회원 저장 중 DB 오류: "+id);
            throw new RuntimeException("회원 저장 중 DB 오류",e);
        }
    }
    void insertUser(String id) throws SQLException {
        throw new SQLException("Duplicated entry","23000");
    }
}

class DuplicateUserIdException extends RuntimeException{
    public DuplicateUserIdException(String id, Throwable cause) {
        super("이미 존재하는 아이디입니다: "+id, cause);
    }
}
