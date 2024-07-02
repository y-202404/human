package com.books;

import java.sql.*;
import java.util.Scanner;

public class BookService {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String USERNAME = "books";
    private static final String PASSWORD = "1234";

    private Connection conn = null;

    public BookService() {
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            conn.setAutoCommit(false);
            System.out.println("\n데이터베이스에 연결되었습니다.");
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }
    
    public void detailMember() {
        String sql = "SELECT * FROM members ORDER BY id ASC";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (!rs.isBeforeFirst()) {
                System.out.println("\n등록된 회원이 없습니다.");
            } else {
                while (rs.next()) {
                    System.out.printf("ID: %d, 이름: %s, 전화번호: %s%n",
                            rs.getInt("id"), rs.getString("name"), rs.getString("phone"));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }
    
    public void detailBook() {
        String sql = "SELECT * FROM books ORDER BY id ASC";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (!rs.isBeforeFirst()) {
                System.out.println("\n등록된 도서가 없습니다.");
            } else {
                while (rs.next()) {
                    System.out.printf("ID: %d, 제목: %s, 작가: %s, 출판사: %s, 개수: %d%n",
                            rs.getInt("id"), rs.getString("title"), rs.getString("author"),
                            rs.getString("publisher"), rs.getInt("quantity"));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void detailBorrowMember() {
        String sql = "SELECT m.id, m.name, m.phone, b.title " +
                "FROM members m " +
                "JOIN borrow_books br ON m.id = br.member_id " +
                "JOIN books b ON br.book_id = b.id " +
                "ORDER BY m.id ASC";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (!rs.isBeforeFirst()) {
                System.out.println("\n대여한 회원이 없습니다.");
            } else {
                while (rs.next()) {
                    System.out.printf("회원 ID: %d, 이름: %s, 전화번호: %s, 대여 도서: %s%n",
                            rs.getInt("id"), rs.getString("name"), rs.getString("phone"), rs.getString("title"));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void registerMember(Scanner scanner) {
        String checkPhoneSql = "SELECT phone FROM members WHERE phone = ?";
        String insertMemberSql = "INSERT INTO members (name, phone) VALUES (?, ?)";

        try (PreparedStatement checkStmt = conn.prepareStatement(checkPhoneSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertMemberSql)) {

            System.out.print("회원 이름: ");
            String name = scanner.nextLine();
            System.out.print("회원 전화번호: ");
            String phone = scanner.nextLine();

            if (!phone.matches("\\d{3}-\\d{4}-\\d{4}")) {
                System.out.println("\n전화번호는 000-0000-0000 형식이어야 합니다.");
                return;
            }

            checkStmt.setString(1, phone);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                System.out.println("\n중복된 전화번호 입니다.");
                return;
            }

            insertStmt.setString(1, name);
            insertStmt.setString(2, phone);

            int rowsInserted = insertStmt.executeUpdate();
            if (rowsInserted > 0) {
                conn.commit();
                System.out.println("\n회원이 등록되었습니다.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }
    
    public void updateMember(Scanner scanner) {
        System.out.print("수정할 회원 ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String checkPhoneSql = "SELECT id FROM members WHERE phone = ? AND id != ?";
        String updateSql = "UPDATE members SET name = ?, phone = ? WHERE id = ?";

        try (PreparedStatement checkStmt = conn.prepareStatement(checkPhoneSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            System.out.print("수정할 회원 이름: ");
            String name = scanner.nextLine();
            System.out.print("수정할 회원 전화번호: ");
            String phone = scanner.nextLine();

            if (!phone.matches("\\d{3}-\\d{4}-\\d{4}")) {
                System.out.println("\n전화번호는 000-0000-0000 형식이어야 합니다.");
                return;
            }

            checkStmt.setString(1, phone);
            checkStmt.setInt(2, id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                System.out.println("\n다른 회원의 전화번호입니다.");
                return;
            }

            updateStmt.setString(1, name);
            updateStmt.setString(2, phone);
            updateStmt.setInt(3, id);

            int rowsUpdated = updateStmt.executeUpdate();
            if (rowsUpdated > 0) {
                conn.commit();
                System.out.println("\n회원 정보가 수정되었습니다.");
            } else {
                System.out.println("\n해당하는 ID의 회원이 없습니다.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void deleteMember(Scanner scanner) {
        System.out.print("삭제할 회원 ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String checkBorrowSql = "SELECT * FROM borrow_books WHERE member_id = ?";
        String deleteMemberSql = "DELETE FROM members WHERE id = ?";

        try (PreparedStatement checkStmt = conn.prepareStatement(checkBorrowSql);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteMemberSql)) {

            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                System.out.println("\n회원이 대여한 도서가 있습니다. 반납 후 삭제할 수 있습니다.");
                return;
            }

            deleteStmt.setInt(1, id);
            int rowsDeleted = deleteStmt.executeUpdate();

            if (rowsDeleted > 0) {
                conn.commit();
                System.out.println("\n회원이 삭제되었습니다.");
            } else {
                System.out.println("\n해당하는 ID의 회원이 없습니다.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void registerBook(Scanner scanner) {
        String sql = "INSERT INTO books (title, author, publisher, quantity) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            System.out.print("등록할 도서 제목: ");
            String title = scanner.nextLine();
            System.out.print("등록할 도서 작가: ");
            String author = scanner.nextLine();
            System.out.print("등록할 도서 출판사: ");
            String publisher = scanner.nextLine();
            System.out.print("등록할 도서 개수 (최대 5개): ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            if (quantity < 0 || quantity > 5) {
                System.out.println("\n도서 개수는 0부터 5까지 입니다.");
                return;
            }

            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, publisher);
            pstmt.setInt(4, quantity);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                conn.commit();
                System.out.println("\n도서가 등록되었습니다.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void updateBook(Scanner scanner) {
        System.out.print("수정할 도서 ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String sql = "UPDATE books SET title = ?, author = ?, publisher = ?, quantity = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            System.out.print("수정할 도서 제목: ");
            String title = scanner.nextLine();
            System.out.print("수정할 도서 작가: ");
            String author = scanner.nextLine();
            System.out.print("수정할 도서 출판사: ");
            String publisher = scanner.nextLine();
            System.out.print("수정할 도서 개수 (최대 5개): ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            if (quantity < 0 || quantity > 5) {
                System.out.println("\n도서 개수는 0부터 5까지 입니다.");
                return;
            }

            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, publisher);
            pstmt.setInt(4, quantity);
            pstmt.setInt(5, id);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                conn.commit();
                System.out.println("\n도서가 수정되었습니다.");
            } else {
                System.out.println("\n해당하는 ID의 도서가 없습니다.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void deleteBook(Scanner scanner) {
        System.out.print("삭제할 도서 ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String sql = "DELETE FROM books WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                conn.commit();
                System.out.println("\n도서가 삭제되었습니다.");
            } else {
                System.out.println("\n해당하는 ID의 도서가 없습니다.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void findBookName(Scanner scanner) {
        String sql = "SELECT * FROM books WHERE title = ? ORDER BY id ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            System.out.print("도서 이름: ");
            String title = scanner.nextLine();

            pstmt.setString(1, title);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    System.out.println("\n해당하는 도서가 없습니다.");
                } else {
                    while (rs.next()) {
                        System.out.printf("ID: %d, 제목: %s, 작가: %s, 출판사: %s, 개수: %d%n",
                                rs.getInt("id"), rs.getString("title"), rs.getString("author"),
                                rs.getString("publisher"), rs.getInt("quantity"));
                    }
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void findAuthorName(Scanner scanner) {
        String sql = "SELECT * FROM books WHERE author = ? ORDER BY id ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            System.out.print("도서 작가: ");
            String author = scanner.nextLine();

            pstmt.setString(1, author);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    System.out.println("\n해당하는 작가가 없습니다.");
                } else {
                    while (rs.next()) {
                        System.out.printf("ID: %d, 제목: %s, 작가: %s, 출판사: %s, 개수: %d%n",
                                rs.getInt("id"), rs.getString("title"), rs.getString("author"),
                                rs.getString("publisher"), rs.getInt("quantity"));
                    }
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void findPublisherName(Scanner scanner) {
        String sql = "SELECT * FROM books WHERE publisher = ? ORDER BY id ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            System.out.print("도서 출판사: ");
            String publisher = scanner.nextLine();

            pstmt.setString(1, publisher);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    System.out.println("\n해당하는 출판사가 없습니다.");
                } else {
                    while (rs.next()) {
                        System.out.printf("ID: %d, 제목: %s, 작가: %s, 출판사: %s, 개수: %d%n",
                                rs.getInt("id"), rs.getString("title"), rs.getString("author"),
                                rs.getString("publisher"), rs.getInt("quantity"));
                    }
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void borrowBook(Scanner scanner) {
        System.out.print("대여할 도서 ID: ");
        int bookId = scanner.nextInt();
        System.out.print("회원 ID: ");
        int memberId = scanner.nextInt();
        scanner.nextLine();

        String checkBookSql = "SELECT * FROM books WHERE id = ?";
        String checkMemberSql = "SELECT * FROM members WHERE id = ?";
        String checkBorrowSql = "SELECT * FROM borrow_books WHERE book_id = ? AND member_id = ?";
        String insertBorrowSql = "INSERT INTO borrow_books (book_id, member_id) VALUES (?, ?)";
        String updateBookSql = "UPDATE books SET quantity = quantity - 1 WHERE id = ? AND quantity > 0";

        try (PreparedStatement checkBookStmt = conn.prepareStatement(checkBookSql);
             PreparedStatement checkMemberStmt = conn.prepareStatement(checkMemberSql);
             PreparedStatement checkBorrowStmt = conn.prepareStatement(checkBorrowSql);
             PreparedStatement insertBorrowStmt = conn.prepareStatement(insertBorrowSql);
             PreparedStatement updateBookStmt = conn.prepareStatement(updateBookSql)) {

            checkBookStmt.setInt(1, bookId);
            ResultSet bookRs = checkBookStmt.executeQuery();
            if (!bookRs.next()) {
                System.out.println("\n해당하는 ID의 도서가 없습니다.");
                return;
            }

            checkMemberStmt.setInt(1, memberId);
            ResultSet memberRs = checkMemberStmt.executeQuery();
            if (!memberRs.next()) {
                System.out.println("\n없는 회원 ID입니다.");
                return;
            }
      
            checkBorrowStmt.setInt(1, bookId);
            checkBorrowStmt.setInt(2, memberId);
            ResultSet borrowRs = checkBorrowStmt.executeQuery();
            if (borrowRs.next()) {
                System.out.println("\n이미 대여한 도서입니다.");
                return;
            }
     
            insertBorrowStmt.setInt(1, bookId);
            insertBorrowStmt.setInt(2, memberId);
            int rowsInserted = insertBorrowStmt.executeUpdate();
            if (rowsInserted > 0) {
                
                updateBookStmt.setInt(1, bookId);
                int rowsUpdated = updateBookStmt.executeUpdate();

                if (rowsUpdated > 0) {
                    conn.commit();
                    System.out.println("\n도서를 대여했습니다.");
                } else {
                    conn.rollback();
                    System.out.println("\n도서의 재고가 없습니다.");
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void returnBook(Scanner scanner) {
        System.out.print("반납할 도서 ID: ");
        int bookId = scanner.nextInt();
        System.out.print("회원 ID: ");
        int memberId = scanner.nextInt();
        scanner.nextLine();

        String checkBorrowSql = "SELECT * FROM borrow_books WHERE book_id = ? AND member_id = ?";
        String deleteBorrowSql = "DELETE FROM borrow_books WHERE book_id = ? AND member_id = ?";
        String updateBookSql = "UPDATE books SET quantity = quantity + 1 WHERE id = ?";

        try (PreparedStatement checkStmt = conn.prepareStatement(checkBorrowSql);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteBorrowSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateBookSql)) {

            checkStmt.setInt(1, bookId);
            checkStmt.setInt(2, memberId);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("\n반납할 도서가 없습니다.");
                return;
            }

            deleteStmt.setInt(1, bookId);
            deleteStmt.setInt(2, memberId);
            int rowsDeleted = deleteStmt.executeUpdate();

            if (rowsDeleted > 0) {
                updateStmt.setInt(1, bookId);
                int rowsUpdated = updateStmt.executeUpdate();

                if (rowsUpdated > 0) {
                    conn.commit();
                    System.out.println("\n도서를 반납했습니다.");
                } else {
                    conn.rollback();
                    System.out.println("\n오류가 발생했습니다.");
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private void handleSQLException(SQLException e) {
        e.printStackTrace();
        if (conn != null) {
            try {
                System.err.print("\n오류가 발생했습니다.");
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("\n데이터베이스를 종료하였습니다.");
            } catch (SQLException e) {
                handleSQLException(e);
            }
        }
    }
}