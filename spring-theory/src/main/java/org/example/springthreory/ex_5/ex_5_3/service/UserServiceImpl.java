package org.example.springthreory.ex_5.ex_5_3.service;

import org.example.springthreory.ex_5.ex_5_3.dao.Level;
import org.example.springthreory.ex_5.ex_5_3.dao.UserDAO;
import org.example.springthreory.ex_5.ex_5_3.domain.User;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService{

    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    private UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // 신규가입
    @Override
    public void add(User user) throws  ClassNotFoundException, SQLException {
        user.setLevel( Level.BASIC );
        userDAO.add(user);
    }

    // 업그레이드 담당
    //트랜잭션 경계 처리는 UserServiceTx가 처리, UserServiceImpl은 비즈니스로직만 구현
       @Override
    public void upgradeLevels() throws SQLException, ClassNotFoundException {
        try{
            List<User> users = userDAO.getAll();
            for ( User user : users ) {
                if ( canUpgrade(user) ) {
                    upgradeLevel(user);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("레벨 업그레이드 중 오류가 발생해 롤백했습니다");
        }

    }

    // '올릴 수 있는가'
    private boolean canUpgrade(User user) {
        Level curLevel = user.getLevel();
        switch (curLevel) {
            case BASIC:
                return user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER;
            case SILVER:
                return user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD;
            case GOLD:
                return false;
            default:
                throw new IllegalStateException("Unexpected value: " + curLevel);
        }
    }

    // 실제 업그레드
    protected void upgradeLevel(User user) throws SQLException, ClassNotFoundException {
        user.upgradeLevel();
        userDAO.update(user);
    }

}

