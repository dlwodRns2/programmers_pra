package org.example.springthreory.ex_5.ex_5_1.dao;

import org.example.springthreory.ex_5.ex_5_1.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//public class UserDAO {
//    //구체 클래스 의존 : 강한 결합 //반드시 필요(connectionMake)
//    //필요에 따라서는 구체클래스를 의존해도 좋다
//    private JdbcContext jdbcContext;
//
//    public UserDAO(JdbcContext jdbcContext) {
//        this.jdbcContext=jdbcContext;
//    }
//
//    private RowMapper<User> userRowMapper = new RowMapper<>(){
//        @Override
//        public User mapRow(ResultSet rs) throws SQLException {
//            User user = new User();
//            user.setId(rs.getString("id"));
//            user.setName(rs.getString("name"));
//            user.setPassword(rs.getString("password"));
//            user.setLevel(Level.valueOf(rs.getInt("level")));
//            user.setLogin(rs.getInt("login"));
//            user.setRecommend(rs.getInt("recommend"));
//            return null;
//        }
//    };
//
//    public void add(User user) throws ClassNotFoundException, SQLException {
//        class UserDAOAdd implements StatementStrategy {
//            private final User user;
//
//            public UserDAOAdd(User user) {
//                this.user = user;
//            }
//            @Override
//            public PreparedStatement makeStatement(Connection conn) throws SQLException {
//                PreparedStatement pstmt = conn.prepareStatement("insert into users (id,name,password, level, login, recommened) values (?,?,?,?,?,?)");
//
//                pstmt.setString(1,user.getId());
//                pstmt.setString(2,user.getName());
//                pstmt.setString(3,user.getPassword());
//                pstmt.setInt(4,user.getLevel().getValue());
//                pstmt.setInt(5,user.getLogin());
//                pstmt.setInt(6,user.getRecommend());
//
//                return pstmt;
//            }
//        }
//        jdbcContextWithStatementStrategy(new UserDAOAdd(user));
//    }
//
//    public void deleteAll() throws SQLException, ClassNotFoundException {
//        //로컬 클래스를 통한 해결
//        StatementStrategy strategy = new StatementStrategy() {
//            @Override
//            public PreparedStatement makeStatement(Connection conn) throws SQLException {
//                return conn.prepareStatement("DELETE from users");
//            }
//        };
//       jdbcContext.workWothStatementStrategy(strategy);
//    }
//
//    public User get(String id) throws SQLException, ClassNotFoundException {
//        StatementStrategy strategy = new StatementStrategy(){
//            @Override
//            public PreparedStatement makeStatement(Connection conn) throws SQLException {
//                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
//                pstmt.setString(1, id);
//                return pstmt;
//            }
//        };
//        return jdbcContext.queryForObject(strategy,userRowMapper);
//    }
//}
public class UserDAO {

    private JdbcContext jdbcContext;

    protected UserDAO() {}

    public UserDAO(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    private RowMapper<User> userRowMapper = new RowMapper<>() {
        @Override
        public User mapRow(ResultSet rs) throws SQLException {
            User user = new User();
            user.setId( rs.getString("id") );
            user.setName( rs.getString("name") );
            user.setPassword( rs.getString("password") );
            user.setLevel( Level.valueOf(rs.getInt("level")) );
            user.setLogin( rs.getInt("login") );
            user.setRecommend( rs.getInt("recommand") );

            return user;
        }
    };

    public void add(User user) throws ClassNotFoundException, SQLException {

        StatementStrategy strategy = new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection conn) throws SQLException {
                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO users(id, name, password, level, login, recommend) VALUES(?, ?, ?, ?, ?, ?)"
                );

                pstmt.setString(1, user.getId());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getPassword());
                pstmt.setInt(4, user.getLevel().getValue());
                pstmt.setInt(5, user.getLogin());
                pstmt.setInt(6, user.getRecommend());

                return pstmt;
            }
        };

        jdbcContext.workWithStatementStrategy(strategy);
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {

        StatementStrategy strategy = new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection conn) throws SQLException {
                return conn.prepareStatement("DELETE FROM users");
            }
        };

        jdbcContext.workWithStatementStrategy(strategy);
    }

    public User get(String id) throws SQLException, ClassNotFoundException {
        StatementStrategy strategy = new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection conn) throws SQLException {
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
                pstmt.setString(1, id);
                return pstmt;
            }
        };

        return jdbcContext.queryForObject( strategy, userRowMapper );
    }

    public List<User> getAll() throws SQLException, ClassNotFoundException {
        StatementStrategy strategy = new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection conn) throws SQLException {
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users ORDER BY id");
                return pstmt;
            }
        };

        return jdbcContext.query(strategy, userRowMapper);
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        StatementStrategy strategy = new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection conn) throws SQLException {
                PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM users");
                return pstmt;
            }
        };

        return jdbcContext.queryForObject(strategy, new RowMapper<>() {
            @Override
            public Integer mapRow(ResultSet rs) throws SQLException {
                rs.next();
                return rs.getInt(1);
            }
        });
    }

    public void update(User user) throws SQLException, ClassNotFoundException {
        StatementStrategy strategy = new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection conn) throws SQLException {
                PreparedStatement pstmt = conn.prepareStatement(
                        "UPDATE users SET name = ?, password = ?, level = ?, login = ?, recommend = ? WHERE id = ?");
                pstmt.setString(1, user.getName());
                pstmt.setString(2, user.getPassword());
                pstmt.setInt(3, user.getLevel().getValue());
                pstmt.setInt(4, user.getLogin());
                pstmt.setInt(5, user.getRecommend());
                pstmt.setString(6, user.getId());

                return pstmt;
            }
        };

        jdbcContext.workWithStatementStrategy(strategy);
    }

}
