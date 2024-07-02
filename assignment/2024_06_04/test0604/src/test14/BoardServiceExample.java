package test14;

import java.io.*;
import java.text.*;
import java.util.*;

public class BoardServiceExample {
    public static void main(String[] args) {
        try {
            BoardService bookService = new BoardService();
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            bookService.addBoard(new Board(1, "책01", "책01작가", sdf.parse("2022-01-14")));
            bookService.addBoard(new Board(2, "책02", "책02작가", sdf.parse("2022-01-24")));
            bookService.addBoard(new Board(3, "책03", "책03작가", sdf.parse("2022-02-17")));
            bookService.addBoard(new Board(4, "책04", "책04작가", sdf.parse("2022-02-28")));
            bookService.addBoard(new Board(5, "책05", "책05작가", sdf.parse("2022-03-29")));
            bookService.addBoard(new Board(6, "책06", "책06작가", sdf.parse("2022-03-30")));
            bookService.addBoard(new Board(7, "책07", "책07작가", sdf.parse("2022-04-09")));
            bookService.addBoard(new Board(8, "책08", "책08작가", sdf.parse("2022-04-10")));
            bookService.addBoard(new Board(9, "책09", "책09작가", sdf.parse("2022-05-01")));
            bookService.addBoard(new Board(10, "책10", "책10작가", sdf.parse("2022-05-02")));
            bookService.addBoard(new Board(11, "책11", "책11작가", sdf.parse("2022-06-05")));
            bookService.addBoard(new Board(12, "책12", "책12작가", sdf.parse("2022-06-06")));
            bookService.addBoard(new Board(13, "책13", "책13작가", sdf.parse("2022-07-09")));
            bookService.addBoard(new Board(14, "책14", "책14작가", sdf.parse("2022-07-11")));
            bookService.addBoard(new Board(15, "책15", "책15작가", sdf.parse("2022-08-16")));
            bookService.addBoard(new Board(16, "책16", "책16작가", sdf.parse("2022-08-19")));
            bookService.addBoard(new Board(17, "책17", "책17작가", sdf.parse("2022-09-15")));
            bookService.addBoard(new Board(18, "책18", "책18작가", sdf.parse("2022-09-23")));
            bookService.addBoard(new Board(19, "책19", "책19작가", sdf.parse("2022-10-18")));
            bookService.addBoard(new Board(20, "책20", "책20작가", sdf.parse("2022-11-17")));
            bookService.addBoard(new Board(21, "책21", "책21작가", sdf.parse("2022-12-15")));
            
         
            
            bookService.showMenu();
        } catch (ParseException e) {
            System.err.println("오류: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("오류: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("오류: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("오류: " + e.getMessage());
        }
    }
}


