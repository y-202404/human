package test14;

import java.io.*;
import java.text.*;
import java.util.*;

public class BoardService {
    private List<Board> list = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private int counter;

    public BoardService() {
    }

    public void showMenu() throws IOException, ClassNotFoundException {
        loadFile();
        while (true) {
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("1.도서 목록보기 | 2.도서 상세보기 | 3.도서 수정하기 | 4.도서 삭제하기 | 5.도서 등록하기 | 6.종료");
            System.out.println("-------------------------------------------------------------------------------");

            System.out.print("선택: ");
            String selectNo = scanner.nextLine();
            switch (selectNo) {
                case "1":
                    Catalog();
                    break;
                case "2":
                    Detail();
                    break;
                case "3":
                    Modify();
                    break;
                case "4":
                    Delete();
                    break;
                case "5":
                	Save();
                    break;
                case "6":
                    System.out.println("종료");
                    return;
                default:
                    System.out.println("오류");
            }
        }
    }

    public void Catalog() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Board b : list) {
            System.out.println(b.getBoardId() + "\t" + b.getTitle() + "\t" + b.getAuthor() + "\t" + sdf.format(b.getpublish()));
        }
    }
    
    public void Detail() {
        int index = searchIndex();
        if (index != -1) {
            Board target = list.get(index);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("도서 번호: " + target.getBoardId());
            System.out.println("도서 제목: " + target.getTitle());
            System.out.println("도서 작가: " + target.getAuthor());
            System.out.println("도서 출판일: " + sdf.format(target.getpublish()));
        }
    }
    
    public void Modify() {
        int index = searchIndex();
        if (index != -1) {
            Board target = list.get(index);
            System.out.print("도서 제목: ");
            target.setTitle(scanner.nextLine());
            System.out.print("도서 작가: ");
            target.setAuthor(scanner.nextLine());
        }
    }

    public void Delete() {
        int index = searchIndex();
        if (index != -1) {
            list.remove(index);
        }
    }
    
    public void Save() throws IOException {
        System.out.print("도서 제목: ");
        String title = scanner.nextLine();
        System.out.print("도서 작가: ");
        String author = scanner.nextLine();
        System.out.print("도서 출판일 (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine();
        Date publish = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            publish = sdf.parse(dateStr);                                   
        } catch (ParseException e) {
            System.out.println("다시");
            return;
        }

        Board newBoard = new Board(counter++, title, author, publish);
        list.add(newBoard);

        FileOutputStream fos = new FileOutputStream("bin/test14/book.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(list);
        oos.flush();
        oos.close();
        System.out.println("파일저장");
    }
   
    public void loadFile() throws IOException, ClassNotFoundException {
        File file = new File("bin/test14/book.txt");
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            list = (List<Board>) ois.readObject();
            ois.close();
        }
    }
    
    public int searchIndex() {
        int result = -1;
        System.out.print("도서 번호: ");
        int targetId = Integer.parseInt(scanner.nextLine());
        for (Board b : list) {
            if (b.getBoardId() == targetId) {
                result = list.indexOf(b);
                break;
            }
        }
        return result;
    }

    public void addBoard(Board board) {
        for (Board b : list) {
            if (b.getBoardId() == board.getBoardId()) {
                System.out.println("중복");
                return;
            }
        }
        list.add(board);
    }
}
