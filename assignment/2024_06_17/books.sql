--DROP TABLE books CASCADE CONSTRAINTS;
--DROP TABLE members CASCADE CONSTRAINTS;
--DROP TABLE borrow_books CASCADE CONSTRAINTS;

ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;
CREATE USER books IDENTIFIED BY 1234;
GRANT resource, dba, CONNECT TO books;
GRANT CREATE ANY TABLE TO books;

CREATE TABLE books (
    id             NUMBER 			GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title          VARCHAR2(50) 	NOT NULL,
    author         VARCHAR2(50) 	NOT NULL,
    publisher      VARCHAR2(50) 	NOT NULL,
    quantity       NUMBER 			NOT NULL
);

CREATE TABLE members (
    id             NUMBER 			GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name           VARCHAR2(50) 	NOT NULL,
    phone          VARCHAR2(50) 	NOT NULL
);

CREATE TABLE borrow_books (
    member_id      NUMBER 			REFERENCES members(id),
    book_id        NUMBER 			REFERENCES books(id),
    PRIMARY KEY (member_id, book_id)
);

INSERT INTO books (title, author, publisher, quantity)
VALUES ('도서1', '작가a', 'a출판사', 5);

INSERT INTO books (title, author, publisher, quantity) 
VALUES ('도서2', '작가b', 'b출판사', 5);

INSERT INTO books (title, author, publisher, quantity) 
VALUES ('도서3', '작가c', 'c출판사', 5);

INSERT INTO books (title, author, publisher, quantity) 
VALUES ('도서4', '작가a', 'a출판사', 5);

INSERT INTO books (title, author, publisher, quantity) 
VALUES ('도서5', '작가b', 'b출판사', 5);


INSERT INTO members (name, phone)
VALUES ('아무개', '010-0000-0001');

INSERT INTO members (name, phone)
VALUES ('홍길동', '010-0000-0002');

INSERT INTO members (name, phone)
VALUES ('임꺽정', '010-0000-0003');

INSERT INTO members (name, phone)
VALUES ('손오공', '010-0000-0004');

INSERT INTO members (name, phone)
VALUES ('사오정', '010-0000-0005');


SELECT * FROM books;
SELECT * FROM members;
SELECT * FROM borrow_books;

--SELECT id, title, author, quantity FROM books;
--SELECT books_seq.NEXTVAL FROM dual;


