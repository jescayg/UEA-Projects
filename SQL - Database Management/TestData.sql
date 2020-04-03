/*Category Table Testing:*/
INSERT INTO Category VALUES (1,'Fantasy','fiction');
INSERT INTO Category VALUES (2,'Romance','fiction');
INSERT INTO Category VALUES (3,'Mystery','fiction');
INSERT INTO Category VALUES (4,'Biography','Non-fiction');
INSERT INTO Category VALUES (5,'Memoir','Non-fiction');

/*
Using 5 examples as this gives a chance to see if both fiction and Non-fiction
work in the database, i decided to have on extra record be fiction as these types
of books are more common.
*/

INSERT INTO SalesRep VALUES (1,'Naruto Uzamaki');
INSERT INTO SalesRep VALUES (2,'Sasuke Uchiha');
INSERT INTO SalesRep VALUES (3,'Sakura Haruno');
INSERT INTO SalesRep VALUES (4,'Obito Uchiha');
INSERT INTO SalesRep VALUES (5,'Rock Lee');

/*
Also added 5 records for the SalesRep table.
*/

INSERT INTO Shop VALUES (1,'Waterstones');
INSERT INTO Shop VALUES (2,'WHSmith');
INSERT INTO Shop VALUES (3,'Foyles');

/*
As lots of Sales reps work at one store there are comparatively less shops in
my database then Sales reperesentatives.
*/

INSERT INTO Publisher VALUES (1,'Penguin Random House');
INSERT INTO Publisher VALUES (2,'Oxford University Press');
INSERT INTO Publisher VALUES (3,'Pearson Education');
INSERT INTO Publisher VALUES (4,'Bloomsbury');

/*
Again not many examples needed for testing as there is no Foreign key and one 
publisher publishesmany books
*/

INSERT INTO Book VALUES (1,'Twilight',20,2,1);
INSERT INTO Book VALUES (2,'A Story about Me',15,4,2);
INSERT INTO Book VALUES (3,'One Piece',10,1,3);
INSERT INTO Book VALUES (4,'Murder on the Orient Express',10,3,4);
INSERT INTO Book VALUES (5,'First They Killed My Father',15,5,1);

/*
I made sure to test both examples of fiction and Non-fiction books to see if
they can be added to my database successfully
*/

INSERT INTO ShopOrder VALUES (1,'2013-10-22',1,1);
INSERT INTO ShopOrder VALUES (2,'2012-06-04',2,2);
INSERT INTO ShopOrder VALUES (3,'2012-09-17',3,3);
INSERT INTO ShopOrder VALUES (4,'2013-02-07',1,4);
INSERT INTO ShopOrder VALUES (5,'2012-12-12',2,5);
INSERT INTO ShopOrder VALUES (6,'2013-01-21',2,3);

/*
I decided to add an extra record so in testing and querying i can see if one
sales reperesentative can work for multiple stores
*/

INSERT INTO Orderline VALUES (1,1,3,15);
INSERT INTO Orderline VALUES (2,2,3,15);
INSERT INTO Orderline VALUES (3,3,3,15);
INSERT INTO Orderline VALUES (4,5,3,15);
INSERT INTO Orderline VALUES (5,4,3,15);
INSERT INTO Orderline VALUES (6,2,3,15);


/*-------------------------------------------------------------------------------------------------*/

/*Testing integrity*/
INSERT INTO Category VALUES (2,'Horror','fiction');
/*When trying the above statement i get an error saying categoryid=2 already exists, this is good
because it means that my primary key for the categories table is working properly*/
INSERT INTO SalesRep VALUES (1,'Minato Namikaze');
/*entity integrity is also maintained in the SalesRep table as well (the same error message
is given*/
INSERT INTO Shop VALUES (1,'Bobs books');
/*shopid=1 already exists is the error messag ereturned therefore entity inegrity is kept
here as well*/
INSERT INTO Publisher VALUES (4,'Elies Publishing');
INSERT INTO Publisher VALUES (3,'Elies Publishing');
/*Tested seperate values for the publisher table and entity integrity is maintained and both
return an error message saying that both 4 and 3 are used primary keys in this table*/
INSERT INTO Book VALUES (5,'Othello',14,3,5);
/*Like the previous tables entity integrity is maintained in the Book table, the error message stated
that bookid=5 already exists as expected, and to make sure the foreign keys worked properly i made
sure to change them for this test data as well*/
INSERT INTO ShopOrder VALUES (1,'2014-11-22',3,2);
/*As expected the ShopOrder table also maintains entity integrity and the primary key works fine
the error message recieved was shoporderid=1 already exists*/
INSERT INTO Orderline VALUES (1,3,3,15);
/*Despite the data havind the same shoporder id as another record entity integrity is still maintained
in this database, this is due to the fact that on the same shop order multiple books can be purchased
so this entry into the database makes sense and is not an error*/
INSERT INTO Orderline VALUES (5,2,3,15);
/*This test data was also accepted into the database, it is natural to also test the opposite of what
was tested above. I can also add a new book to an existing shop order and this record in the orderline
database will still be seen as unique. These tests also show that the Orderline database has good
referential integrity as there is no issue with referencing the information from the other tables*/


/*---------------------------------------------------------------------------------------------------------*/

/*Queries*/
/*1*/SELECT * FROM Category;

/*2*/DELETE FROM Category WHERE Name='Story';

/*3*/SELECT Category.Name, count(Book.Title) AS Title_Count, Cast(AVG(Book.Price) AS DECIMAL(10,2)) AS Average_Price 
/*3*/FROM Category JOIN Book ON Category.CategoryID=Book.CategoryID GROUP BY Category.Name
/*3*/UNION ALL
/*3*/SELECT 'TOTAL', count(Book.Title), SUM(Book.Price)
/*3*/FROM Book JOIN Category ON Book.CategoryID=Category.CategoryID

/*4*/SELECT Book.BookID,Title,Publisher.Name,
/*4*/ShopOrder.OrderDate, Orderline.UnitSellingPrice, Book.Price,
/*4*/SUM(Orderline.Quantity), SUM(Orderline.UnitSellingPrice*Orderline.Quantity)
/*4*/FROM Book JOIN Publisher ON Book.PublisherID = Publisher.PublisherID JOIN Orderline ON Book.BookID = Orderline.BookID
/*4*/JOIN ShopOrder ON Orderline.ShopOrderID = ShopOrder.ShopOrderID WHERE Publisher.Name = 'Oxford University Press'
/*4*/GROUP BY Book.BookID, Title, Publisher.Name,ShopOrder.OrderDate, Orderline.UnitSellingPrice, Book.Price
/*4*/ORDER BY ShopOrder.OrderDate DESC

/*5*/SELECT Orderline.BookID, Book.Title, Book.Price, Orderline.ShopOrderID, Orderline.Quantity, Orderline.UnitSellingPrice, ShopOrder.Orderdate, Shop.Name, ShopOrder.ShopOrderID
/*5*/FROM Orderline JOIN Book ON Orderline.BookID = Book.BookID JOIN ShopOrder ON Orderline.ShopOrderID = ShopOrder.ShopOrderID JOIN Shop ON ShopOrder.ShopID = Shop.ShopID
/*5*/WHERE Book.BookID = 3
/*5*/UNION ALL
/*5*/SELECT NULL, 'TOTAL', SUM(Book.Price), NULL, SUM(Quantity), NULL, NULL, NULL, NULL
/*5*/FROM Orderline
/*5*/JOIN Book ON Orderline.BookID = Book.BookID JOIN ShopOrder ON Orderline.ShopOrderID = ShopOrder.ShopOrderID JOIN Shop ON ShopOrder.ShopID = Shop.ShopID
/*5*/Where Book.BookID = 3