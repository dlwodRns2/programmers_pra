package org.example.member_management_db;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberManager {

    private final int capacity;

    public MemberManager(int capacity) {
        this.capacity = capacity;
    }

    public boolean isFull() {
        return size() >= capacity;
    }

    private Connection connection(){
        String url = "jdbc:mysql://localhost:3306/java_basic";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url,"root","re00");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void add(Member m) {
        String sql = "insert into member (grade, name, email, phone) values (?,?,?,?)";
        try(
                Connection conn = connection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ){
            pstmt.setString(1,m.getGrade());
            pstmt.setString(2,m.getName());
            pstmt.setString(3,m.getEmail());
            pstmt.setString(4,m.getPhone());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Member toMember(ResultSet rs) throws SQLException {
        String grade = rs.getString("grade");
        String name = rs.getString("name");
        String email  = rs.getString("email");
        String phone = rs.getString("phone");

        return grade.equals("VIP")
                ? new VipMember(name,email,phone)
                : new NormalMember(name,email,phone);
    }
    public Member findByEmail(String email) {
        String sql = "select * from member where email=?";

        try(
                Connection conn =connection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ){
            pstmt.setString(1,email);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return toMember(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public Member findByName(String name) {
        String sql = "select * from member where name=?";

        try(
                Connection conn = connection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ){
            pstmt.setString(1,name);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return toMember(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public boolean update(String email, String name, String phone, String newEmail) {
        String sql = "update member set name=?, email=?, phone=? where email=?";

        try(
                Connection conn = connection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ) {
            pstmt.setString(1,name);
            pstmt.setString(2,newEmail);
            pstmt.setString(3,phone);
            pstmt.setString(4,email);
            return pstmt.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(String email) {
        String sql = "delete from member where email=?";

        try(
                Connection conn = connection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ){
            pstmt.setString(1,email);
            return pstmt.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void printAll() {
        String sql = "select * from member";
        try(
                Connection conn = connection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ){

            ResultSet rs = pstmt.executeQuery();
            boolean empty=true;

            while(rs.next()){
                toMember(rs).printInfo();
                empty=false;
            }
            if(empty){
                System.out.println("등록된 회원이 없습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean existsEmail(String email){
        String sql = "select count(*) from member where email=?";

        try(
                Connection conn = connection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ){

            pstmt.setString(1,email);
            try(ResultSet rs = pstmt.executeQuery();){
                if(rs.next()){
                    return rs.getInt(1)>0;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public int size() {
        String sql = "select count(*) from member";
        try(
                Connection conn = connection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {
            if(rs.next()){
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public int capacity() {
        return capacity;
    }

}