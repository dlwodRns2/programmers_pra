package org.example;

import com.mysql.cj.protocol.Resultset;

import javax.xml.transform.Result;
import java.sql.*;

public class A_jdbc {
    public Connection connection(){
        String url = "jdbc:mysql://localhost:3306/java_basic";
        String user = "root";
        String password="re00";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connect Success");

            return connection;

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertData(String name, int age,String phone){
        //sql injection 방식 => preparedStatement로 단점 보완
        String query = "insert into member (name,age,phone) values (?,?,?)";

        try(
                Connection conn = connection();
                PreparedStatement pstmt = conn.prepareStatement(query)
        ){
            pstmt.setString(1,name);
            pstmt.setInt(2,age);
            pstmt.setString(3, phone);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void selectAll(){
        String query = "select id,name,age,phone from member";

        try(
                Connection conn = connection();
                PreparedStatement pstmt = conn.prepareStatement(query);
        ){
            ResultSet resultSet = pstmt.executeQuery();

            while(resultSet.next()){
                int id=resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String phone = resultSet.getString("phone");

                System.out.println(id+" "+name+" "+age+" "+phone);
                System.out.println("==========");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void selectOne(int id){
        String query = "select id,name,age,phone from member where id=?";

        try(
                Connection conn = connection();
                PreparedStatement pstmt = conn.prepareStatement(query)
        ){
            pstmt.setInt(1,id);

            ResultSet resultSet = pstmt.executeQuery();

            if(resultSet.next()){
                int id2 = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String phone = resultSet.getString("phone");

                System.out.println(id2+ " : "+name+" : "+age+" : "+phone+" : ");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateData(int id,String name,int age,String phone){
        String query = "update member set name =?, age =?, phone=? where id=?";

        try(
                Connection conn = connection();
                PreparedStatement pstmt = conn.prepareStatement(query);
        ){

            pstmt.setString(1,name);
            pstmt.setInt(2,age);
            pstmt.setString(3,phone);
            pstmt.setInt(4,id);

            //작업 성공 시 양수, 실패 시 음수 반환
            int result = pstmt.executeUpdate();
            if(result>0){
                System.out.println("update success");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void deleteData(int id){
        String query  = "delete from member where id=?";

        try(
                Connection conn = connection();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ){
            pstmt.setInt(1,id);
            int result = pstmt.executeUpdate();

            if(result>0){
                System.out.println("delete success");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    static void main(String[] args) {
        A_jdbc aJdbc = new A_jdbc();
        //aJdbc.insertData("홍길순",21,"010-1234-5678");
        aJdbc.selectAll();
        //aJdbc.selectOne(1);
        //aJdbc.updateData(2,"홍홍홍",30,"010-3232-4545");
        //aJdbc.deleteData(2);
    }
}
