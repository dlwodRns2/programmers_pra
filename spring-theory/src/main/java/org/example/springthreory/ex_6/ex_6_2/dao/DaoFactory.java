package org.example.springthreory.ex_6.ex_6_2.dao;

import org.example.springthreory.ex_6.ex_6_2.service.TransactionAdvice;
import org.example.springthreory.ex_6.ex_6_2.service.UserServiceImpl;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

//@Configuration을 붙인 순간 부터 스프링 컨테이너의 관리 대상으로 들어감
//DaoFactory를 스프링 빈 팩토리가 사용할 수 있는 설정정보로 리팩토링
@Configuration // 애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정 정보라는 표시
public class DaoFactory {

    // UserService 빈 = ProxyFactoryBean이 생산하는 프록시
    //  - target과 advisor만 등록하면, 스프링이 프록시를 알아서 만들어준다.
    //  - 여러 advisor를 addAdvisor로 얹을 수도 있다(부가기능 여러 개 조합).
    @Bean
    public ProxyFactoryBean userService() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget( userServiceImpl() );
        proxyFactoryBean.addAdvisor( transactionAdvisor() );

        return proxyFactoryBean;
    }
    // Advice(무엇을) : 적용할 부가기능 자체(트랜잭션 경계)
    @Bean public TransactionAdvice transactionAdvice(){
        return new TransactionAdvice(transactionManager());
    }
    // Pointcut(어디에) : 어떤 메서드에 부가기능을 적용할지 '선별'한다.
    //  - ex_6_1에서는 핸들러 안에서 startsWith로 직접 걸렀지만, 이제 그 책임이 Pointcut으로 분리됐다.
    //  - NameMatchMethodPointcut: 메서드 이름 패턴으로 매칭. "upgrade*" -> upgradeLevels 등.
    @Bean public NameMatchMethodPointcut transactionPointcut(){
        NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
        nameMatchMethodPointcut.setMappedNames("upgrade*");
        return nameMatchMethodPointcut;
    }
    @Bean
    public DefaultPointcutAdvisor transactionAdvisor(){
        return new DefaultPointcutAdvisor(transactionPointcut(),transactionAdvice());}
    @Bean
    public UserServiceImpl userServiceImpl() {
        return new UserServiceImpl(userDAO());
    }

    @Bean // 오브젝트 생성을 담당하는 IoC용 메서드라는 표시
    public UserDAO userDAO() {
        return new UserDAO(jdbcContext());
    }

    @Bean
    public JdbcContext jdbcContext() {
        return new JdbcContext(dataSource());
    }

    // 커넥션을 우리가 직접 만들던 SimpleConnectionMaker 대신, 스프링 표준 DataSource를 쓴다.
    //  - DriverManagerDataSource: 학습용 DataSource(요청마다 커넥션 생성). 운영은 커넥션 풀을 쓴다.
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/springtheory");
        dataSource.setUsername("root");
        dataSource.setPassword("re00");
        return dataSource;
    }

    // * 트랜잭션 추상화의 '실제 구현'을 여기서 결정한다.
    //  - 반환 타입은 추상화 인터페이스(PlatformTransactionManager).
    //  - JDBC를 쓰므로 DataSourceTransactionManager를 꽂는다.
    //    (JPA면 JpaTransactionManager, 분산이면 JtaTransactionManager로 '이 한 줄만' 바꾸면 된다.
    //     UserService 코드는 전혀 손대지 않는다 -> 이것이 추상화의 이득)
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

}
