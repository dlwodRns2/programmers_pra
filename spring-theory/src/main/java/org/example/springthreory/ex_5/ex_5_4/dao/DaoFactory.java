package org.example.springthreory.ex_5.ex_5_4.dao;

import org.example.springthreory.ex_5.ex_5_4.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

//@Configuration을 붙인 순간 부터 스프링 컨테이너의 관리 대상으로 들어감
//DaoFactory를 스프링 빈 팩토리가 사용할 수 있는 설정정보로 리팩토링
@Configuration //ApplicationContext 또는 빈 팩토리가 사용할 설정 정보라는 표시
public class DaoFactory {
    // * 메일 발송 구현을 여기서 결정한다(추상화의 교체 지점).
    //  - 지금은 실제 SMTP 서버가 없으므로 DummyMailSender(아무것도 안 함)를 꽂는다.
    //  - 운영에서는 JavaMail 기반 실제 발송 구현으로 '이 한 줄만' 바꾸면 된다.
    //    UserServiceImpl 코드는 전혀 손대지 않는다.
    @Bean public MailSender mailSender(){
        return new DummyMailSender();
    }
    @Bean public UserService userService(){
        return new UserServiceTx(transactionManager(),userServiceImpl());
    }
    @Bean public UserServiceImpl userServiceImpl(){
        return new UserServiceImpl(userDAO(),mailSender());
    }
    @Bean //오브젝트 생성을 담당하는 IoC용 메서드라는 표시
    public UserDAO userDAO(){
        return new UserDAO(jdbcContext());
    }
    @Bean
    public JdbcContext jdbcContext(){
        return new JdbcContext(dataSource());
    }


    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/springtheory");
        dataSource.setUsername("root");
        dataSource.setPassword("re00");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }
}
