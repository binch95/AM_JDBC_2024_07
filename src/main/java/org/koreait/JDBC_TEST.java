package org.koreait;

import java.sql.*;

public class JDBC_TEST {
    static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; //드라이버
    static String DB_URL = "jdbc:mysql://localhost:3306/AM_JDBC_2024_07"; //접속할 DB 서버

    static String USER_NAME = "root"; //DB에 접속할 사용자 이름
    static String PASSWORD = ""; //사용자의 비밀번호

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String a = "`JDBC`";
        Connection conn = null;
        Statement state = null;
//        try { //Reflection 방식
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD); //db 내의 데이터를 저장
        state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE); //sql 문을 실행하기 위해 conn 연결 정보를 state로 생성



        PreparedStatement ps = conn.prepareStatement("insert into " + a + " set title = 'ddd', `body` = 'dnksi';");
        ps.executeUpdate();
        ResultSet rs = state.executeQuery("select * from " + a);
        rs.last();
        int lastid = rs.getRow();
        System.out.println(lastid);


        state.close();
        conn.close();
    }
}
