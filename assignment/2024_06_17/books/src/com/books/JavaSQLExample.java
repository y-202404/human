package com.books;

import java.sql.*;

public class JavaSQLExample {
    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@localhost:1521:orcl";
        String user = "books";
        String password = "1234";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM books ORDER BY id")) {

            System.out.println("\n데이터베이스가 연결되었습니다");

            while (rs.next()) {
                System.out.printf("도서: %d, 책제목: %s, 작가이름: %s, 출판사: %s, 개수: %d%n",
                        rs.getInt("id"), rs.getString("title"), rs.getString("author"), 
                        rs.getString("publisher"), rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            System.err.println("\n오류가 발생했습니다.");
            e.printStackTrace();
        }
    }
}