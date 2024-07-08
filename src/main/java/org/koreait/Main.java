package org.koreait;

import com.sun.tools.jconsole.JConsoleContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.sql.*;


public class Main {
    static List<Article> articles = new ArrayList();
    static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; //드라이버
    static String DB_URL = "jdbc:mysql://localhost:3306/Article"; //접속할 DB 서버

    static String USER_NAME = "root"; //DB에 접속할 사용자 이름
    static String PASSWORD = ""; //사용자의 비밀번호

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        Statement state = null;
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD); //db 내의 데이터를 저장
        state = conn.createStatement(); //sql 문을 실행하기 위해 conn 연결 정보를 state로 생성

        Scanner in = new Scanner(System.in);
        System.out.println("==프로그램 시작==");


        while (true) {
            System.out.print("명령어) ");
            String cmd = in.nextLine().trim();
            String[] answer = cmd.split(" ");
            String table = answer[0].trim();
            ResultSet lastId = state.executeQuery("select * from " + table);
            if (cmd.equals("exit")) {
                System.out.println("==종료==");
                break;

            } else if (cmd.equals("article write")) {
                System.out.println("==글쓰기==");
                System.out.print("제목 : ");
                String title = in.nextLine();
                System.out.print("내용 : ");
                String body = in.nextLine();
                LocalDateTime now = LocalDateTime.now();
                String regDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                Article article = new Article(articles.size() + 1, title, body);
                PreparedStatement ps = conn.prepareStatement("insert into " + table + " set title = ?, body = ?");
                ps.setString(1, title);
                ps.setString(2, body);
                ps.executeUpdate();
                lastId = state.executeQuery("select * from " + table);
                int id = 0;
                while (lastId.next()) {
                    id = lastId.getInt(1);
                }
                System.out.println(id + "번 글이 작성되었습니다");
                articles.add(article);

            } else if (cmd.equals("article list")) {
                System.out.println(" id    /   제목   /   내용   ");
                while (lastId.next()) {
                    System.out.printf(" %s   /   %s   /   %s   \n",lastId.getString(1), lastId.getString(2), lastId.getString(3));
                }
            }else if (cmd.startsWith("article delete")) {
                int number = Integer.parseInt(cmd.split(" ")[2]);
                conn.prepareStatement("delete from " + table + " where id = " + number).execute();
            }else if (cmd.startsWith("article update")) {
                int number = Integer.parseInt(cmd.split(" ")[2]);
                System.out.println("==수정하기==");
                System.out.print("제목 : ");
                String title = in.nextLine();
                System.out.print("내용 : ");
                String body = in.nextLine();
                PreparedStatement ps = conn.prepareStatement("update " + table + " set title = ?, body = ? where id = " + number);
                ps.setString(1, title);
                ps.setString(2, body);
                ps.executeUpdate();
            }

        }
    }
}