package org.koreait;

import java.sql.*;

public class JDBC_Select {

    static String USER_NAME = "root"; //DB에 접속할 사용자 이름
    static String PASSWORD = ""; //사용자의 비밀번호

    public JDBC_Select() {
        String DB_URL = "jdbc:mysql://localhost:3306/Article"; //접속할 DB 서버
        Connection conn = null;
        Statement state = null;

        try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL, "root", ""); //db 내의 데이터를 저장
        state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); //sql 문을 실행하기 위해 conn 연결 정보를 state로 생성 , statement 타입을 행이동 가능으로 설정(기본적으로 next제외 커서이동 불가능함)



        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패" + e);
        } catch (SQLException e) {
            System.out.println("에러 : " + e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
