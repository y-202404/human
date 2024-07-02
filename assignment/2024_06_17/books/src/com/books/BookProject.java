package com.books;

import java.sql.*;
import java.util.Scanner;

public class BookProject {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            BookService bookService = new BookService();

            while (true) {
                System.out.println("\n          <<<도서 관리 프로그램>>>    ");
                System.out.println("     /\\                         /\\    ");
                System.out.println(" )  ( ')                       (' )  (");
                System.out.println("(  /  )                         (  \\  )");
                System.out.println(" \\(__)|                         |(__)/");
                System.out.println("---------------------------------------");
                System.out.println("---------------------------------------");
                System.out.println("1. 회원 상세 보기");
                System.out.println("2. 도서 상세 보기");
                System.out.println("3. 도서 대여 회원 조회");
                System.out.println("4. 회원 등록");
                System.out.println("5. 회원 수정");
                System.out.println("6. 회원 삭제");
                System.out.println("7. 도서 등록");
                System.out.println("8. 도서 수정");
                System.out.println("9. 도서 삭제");
                System.out.println("10. 도서 제목 조회");
                System.out.println("11. 도서 작가 조회");
                System.out.println("12. 도서 출판사 조회");
                System.out.println("13. 도서 대여");
                System.out.println("14. 도서 반납");
                System.out.println("15. 종료");
                System.out.println("---------------------------------------");
                System.out.println("---------------------------------------");
                System.out.print("선택:");

                if (!scanner.hasNextInt()) {
                    System.out.println("\n오류가 발생했습니다.");
                    scanner.next();
                    continue;
                }

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                	case 1:
                		bookService.detailMember();
                		break;
                	case 2:
                        bookService.detailBook();
                        break;
                	case 3:
                        bookService.detailBorrowMember();
                        break;
                    case 4:
                        bookService.registerMember(scanner);
                        break;
                    case 5:
                        bookService.updateMember(scanner);
                        break;
                 	case 6:
                        bookService.deleteMember(scanner);
                        break;
                    case 7:
                        bookService.registerBook(scanner);
                        break;
                    case 8:
                        bookService.updateBook(scanner);
                        break;
                    case 9:
                        bookService.deleteBook(scanner);
                        break;                    
                    case 10:
                        bookService.findBookName(scanner);
                        break;
                    case 11:
                        bookService.findAuthorName(scanner);
                        break;
                    case 12:
                        bookService.findPublisherName(scanner);
                        break;
                    case 13:
                        bookService.borrowBook(scanner);
                        break;
                    case 14:
                        bookService.returnBook(scanner);
                        break;
                    case 15:
                    	System.out.println("---------------------------------------");
                        bookService.closeConnection();
                        return;
                    default:
                        System.out.println("\n오류가 발생했습니다.");
                }
            }
        }
    }
}