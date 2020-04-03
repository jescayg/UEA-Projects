CREATE TABLE Category
(
	CategoryID		INTEGER NOT NULL,
	Name			VARCHAR(50),
	CategoryType 		VARCHAR(20),
    CONSTRAINT CHK_CategoryType CHECK (CategoryType = 'fiction' OR CategoryType = 'Non-fiction'),
    CONSTRAINT PK_Category PRIMARY KEY (CategoryID)
)

CREATE TABLE SalesRep
(
	SalesRepID		INTEGER NOT NULL,
	Name			VARCHAR(50),
    CONSTRAINT PK_SalesRep PRIMARY KEY (SalesRepID)
)

CREATE TABLE Shop
(
	ShopID			INTEGER NOT NULL,
	Name			VARCHAR(50),
    CONSTRAINT PK_ShopID PRIMARY KEY (ShopID)
)

CREATE TABLE Publisher
(
	PublisherID		INTEGER NOT NULL,
	Name			VARCHAR(50),
    CONSTRAINT PK_Publisher PRIMARY KEY (PublisherID)
)

CREATE TABLE Book
(
	BookID			INTEGER NOT NULL,
	Title			VARCHAR(50),
	Price			DECIMAL(10,2),
	CategoryID		INTEGER,
	PublisherID		INTEGER,
    CONSTRAINT PK_BookID PRIMARY KEY (BookID),
    CONSTRAINT FK_bookCategory FOREIGN KEY (CategoryID) REFERENCES Category(CategoryID),
    CONSTRAINT FK_bookPublisher FOREIGN KEY (PublisherID) REFERENCES Publisher(PublisherID)
)

CREATE TABLE ShopOrder
(
	ShopOrderID		INTEGER NOT NULL,
	OrderDate		DATE,
	ShopID			INTEGER,
	SalesRepID		INTEGER,
    CONSTRAINT PK_ShopOrder PRIMARY KEY (ShopOrderID),
    CONSTRAINT FK_shopUsed FOREIGN KEY (ShopID) REFERENCES Shop(ShopID),
    CONSTRAINT FK_salesRep FOREIGN KEY (SalesRepID) REFERENCES SalesRep(SalesRepID)
)
CREATE TABLE Orderline
(
	ShopOrderID		INTEGER REFERENCES ShopOrder(ShopOrderID) NOT NULL,
	BookID			INTEGER REFERENCES Book(BookID) NOT NULL,
	Quantity		INTEGER,
	UnitSellingPrice	DECIMAL (10,2),
    CONSTRAINT pk_ShopOrder1 PRIMARY KEY (ShopOrderID,BookID)
)	
 