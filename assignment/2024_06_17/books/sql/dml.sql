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