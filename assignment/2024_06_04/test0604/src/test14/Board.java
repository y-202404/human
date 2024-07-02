package test14;

import java.io.Serializable;
import java.util.Date;

public class Board implements Serializable {
    private int boardId;
    private String title;
    private String author;
    private Date publish;

    public Board(int boardId, String title, String author, Date publish) {
        this.boardId = boardId;
        this.title = title;
        this.author = author;
        this.publish = publish;
    }

    public int getBoardId() 
    { return boardId; }
    public void setBoardId(int boardId) 
    { this.boardId = boardId; }

    public String getTitle()
    { return title; }
    public void setTitle(String title) 
    { this.title = title; }

    public String getAuthor()
    { return author; }
    public void setAuthor(String author) 
    { this.author = author; }

    public Date getpublish()
    { return publish; }
    public void setpublish(Date publish) 
    { this.publish = publish; }
}
