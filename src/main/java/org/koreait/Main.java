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

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Scanner in = new Scanner(System.in);
        System.out.println("==프로그램 시작==");


        while (true) {
            System.out.print("명령어) ");
            String cmd = in.nextLine().trim();
            String[] answer = cmd.split(" ");
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
                new DBManager(article, answer[1]);
                System.out.println(DBManager.listId + "번 글이 작성되었습니다");
                articles.add(article);

            } else if (cmd.equals("article list")) {

                Article article = null ;
                System.out.println(" id    /   제목   /   내용   ");
                new DBManager(article, answer[1]);
//                if (articles.size() == 0) {
//                    System.out.println("작성된 글이 없습니다.");
//                    continue;
//                }
//                for (int i = articles.size() - 1; i >= 0; i--) {
//                    System.out.printf(" %d   /    %s   /     %s   \n", articles.get(i).getId(), articles.get(i).getTitle(), articles.get(i).getTitle());
//                }
            }

        }
    }
}