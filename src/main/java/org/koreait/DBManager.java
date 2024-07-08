package org.koreait;

import java.sql.*;
import java.util.Arrays;

import static org.koreait.Main.articles;

public class DBManager {
    static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; //드라이버
    static String DB_URL = "jdbc:mysql://localhost:3306/Article"; //접속할 DB 서버

    static String USER_NAME = "root"; //DB에 접속할 사용자 이름
    static String PASSWORD = ""; //사용자의 비밀번호
    static int listId = 0;
    public DBManager(Article article, String cmd) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        Statement state = null;
//        try { //Reflection 방식
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD); //db 내의 데이터를 저장
        state = conn.createStatement(); //sql 문을 실행하기 위해 conn 연결 정보를 state로 생성
        ResultSet rs = state.executeQuery("select * from article");
        if (cmd.equals("write")) {
            conn.setAutoCommit(false);
            PreparedStatement add = conn.prepareStatement("insert into article(title,body) values(?,?)");
            add.setString(1, article.getTitle());
            add.setString(2, article.getBody());
            int result = add.executeUpdate();

            conn.commit();

        } else if (cmd.equals("list")) {

            while (rs.next()) {
                System.out.printf("  %s    /   %s   /   %s   \n", rs.getString(1), rs.getString(2), rs.getString(3));
            }
        }
        state.close();
        conn.close();

    }
}
