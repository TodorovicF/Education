DROP DATABASE IF exists bookstore;
CREATE DATABASE bookstore;

USE bookstore;

DROP TABLE IF EXISTS User;
CREATE TABLE User (
  UserID BIGINT NOT NULL AUTO_INCREMENT,
  FirstName VARCHAR(50) DEFAULT '',
  LastName VARCHAR(50) DEFAULT '',
  Passwd VARCHAR(50),
  EmailAddress VARCHAR(50) NOT NULL UNIQUE,
  Address1 VARCHAR(50),
  Address2 VARCHAR(50),
  City VARCHAR(50),
  State VARCHAR(50),
  Zip VARCHAR(50),
  Country VARCHAR(50),
  SignInDate DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00',
  LastLoginDate DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00',

  PRIMARY KEY (UserID)
);

INSERT INTO `User` (`EmailAddress`,`Passwd`) VALUES ("mi@arcu.edu","mauris."),("Curabitur@ligulaelitpretium.org","arcu."),("Donec.fringilla@Quisqueporttitoreros.com","metus"),("convallis@Nuncquisarcu.net","varius."),("a.purus.Duis@euismodurnaNullam.com","elit."),("malesuada@ultrices.ca","purus"),("Nullam.scelerisque.neque@veliteget.net","Donec"),("eu.enim.Etiam@SuspendisseduiFusce.ca","congue."),("lacus@et.com","quis,"),("tellus@quisturpisvitae.ca","justo.");
INSERT INTO `User` (`EmailAddress`,`Passwd`) VALUES ("enim.Mauris.quis@euplacerat.ca","orci."),("tempus@lacusUt.org","massa"),("vestibulum.massa@dictumeleifendnunc.net","varius."),("interdum.enim@nisi.org","a"),("Aliquam@dictumplacerataugue.edu","arcu."),("nibh.Phasellus.nulla@rutrum.edu","aliquam,"),("Quisque.fringilla@lorem.ca","est"),("dui@Nullamfeugiat.edu","tempus"),("at@nibhDonecest.org","eu,"),("odio.tristique@Nulla.com","Proin");
INSERT INTO `User` (`EmailAddress`,`Passwd`) VALUES ("urna@dui.com","dictum"),("Etiam@Integer.co.uk","metus."),("tincidunt@etipsumcursus.edu","Donec"),("vitae@dolor.ca","amet,"),("pede.blandit@tortor.co.uk","natoque"),("orci.luctus@dui.org","turpis"),("nibh.enim.gravida@mi.edu","erat"),("condimentum@atlacusQuisque.net","Nullam"),("laoreet@posuere.co.uk","sed"),("amet.metus@varius.org","tempus");
INSERT INTO `User` (`EmailAddress`,`Passwd`) VALUES ("lectus.justo.eu@dolorFusce.co.uk","Etiam"),("lectus.Nullam@erat.net","aliquam"),("Maecenas.mi.felis@egetmetuseu.edu","vel"),("neque.venenatis@vitaealiquetnec.ca","enim."),("porttitor.vulputate@ipsum.com","Proin"),("aliquet@magnaPraesent.co.uk","at,"),("quis.massa@arcueu.edu","turpis"),("Nullam@anunc.com","iaculis"),("id.mollis@purus.co.uk","nulla"),("augue@odioNaminterdum.net","Suspendisse");
INSERT INTO `User` (`EmailAddress`,`Passwd`) VALUES ("non.arcu@Nullaeuneque.net","Duis"),("Nunc.sollicitudin@nisi.co.uk","sit"),("Ut.semper@ametdiameu.co.uk","iaculis,"),("sagittis.semper.Nam@semperrutrum.edu","rutrum"),("Integer.mollis.Integer@idmagna.edu","a"),("sem@nec.com","et,"),("ut.aliquam.iaculis@fermentumfermentumarcu.co.uk","sociosqu"),("Sed@Loremipsumdolor.net","commodo"),("sed.pede.nec@dictumeleifend.edu","feugiat."),("et.rutrum.eu@orciluctus.net","In");
INSERT INTO `User` (`EmailAddress`,`Passwd`) VALUES ("rutrum@semperduilectus.com","commodo"),("faucibus@ametmassaQuisque.net","dapibus"),("purus@quisaccumsan.ca","lacus"),("arcu@sociisnatoquepenatibus.net","eu,"),("Aliquam.rutrum@urnaconvalliserat.org","sagittis"),("luctus.aliquet.odio@Mauris.co.uk","sapien."),("sagittis.semper.Nam@mollisduiin.org","ac"),("molestie.in.tempus@felisullamcorper.co.uk","mollis"),("pede.Cum@sapienmolestieorci.com","augue,"),("magna@posuere.net","Aliquam");
INSERT INTO `User` (`EmailAddress`,`Passwd`) VALUES ("per.inceptos.hymenaeos@scelerisque.net","mattis."),("sem.magna@sociis.org","sed"),("turpis.egestas@netusetmalesuada.org","ullamcorper"),("dolor@ametluctus.edu","pede,"),("Aenean@utnisia.edu","Nam"),("Aenean@est.org","Donec"),("dictum.augue@lectus.edu","lorem,"),("ac.mattis@seddui.com","Aliquam"),("malesuada.augue.ut@Suspendissedui.edu","felis,"),("mauris@euismodmauris.net","rutrum");
INSERT INTO `User` (`EmailAddress`,`Passwd`) VALUES ("hendrerit.Donec@odioa.co.uk","leo."),("Integer@nonummyut.ca","odio"),("ut@Aliquameratvolutpat.org","rhoncus."),("gravida.Aliquam.tincidunt@rhoncusid.co.uk","aliquet"),("et@scelerisqueloremipsum.net","nec,"),("dolor.sit@euismod.net","Vivamus"),("metus.Vivamus@faucibus.org","mi"),("vestibulum.nec.euismod@Nunc.ca","ipsum"),("at.egestas.a@enimCurabitur.net","sollicitudin"),("volutpat.nunc.sit@Vivamus.com","arcu.");
INSERT INTO `User` (`EmailAddress`,`Passwd`) VALUES ("vel.turpis@nullamagnamalesuada.edu","netus"),("placerat.augue.Sed@IncondimentumDonec.co.uk","Cras"),("non.luctus.sit@mi.ca","egestas."),("fermentum.fermentum.arcu@tortornibh.ca","Nullam"),("felis@MorbivehiculaPellentesque.ca","Aenean"),("molestie.Sed@Maurismolestie.edu","sed"),("tincidunt.Donec@Vivamusnibh.org","eget"),("Curabitur@dictummi.net","et"),("Nunc.lectus@interdumfeugiatSed.ca","ornare"),("semper.pretium@netusetmalesuada.com","malesuada");
INSERT INTO `User` (`EmailAddress`,`Passwd`) VALUES ("libero.Integer.in@semelitpharetra.ca","Donec"),("tincidunt@metus.com","sapien."),("auctor.quis.tristique@nequeMorbiquis.edu","sit"),("feugiat.Sed@nulla.net","sagittis"),("Cum@lobortis.org","luctus"),("dictum@Nullamutnisi.ca","Phasellus"),("consectetuer.euismod.est@nuncid.edu","Aliquam"),("mi@loremvehiculaet.org","a,"),("Integer.urna@montesnascetur.com","venenatis"),("nunc.interdum@ullamcorperviverra.net","arcu.");
INSERT INTO `User` (`EmailAddress`, `Passwd`) VALUES ("test@test.com", "test");

DROP TABLE IF EXISTS Invoice;
Create Table Invoice(
  InvoiceID int not null auto_increment,
  UserID bigint not null,
  InvoiceDate datetime not null default '0000-00-00 00:00:00',
  TotalAmount float not null default '0',
  IsProcessed enum('y','n') default null,

  Primary Key (InvoiceID),
  Foreign Key (UserID) References User (UserID)
);

INSERT INTO Invoice values
  (1,01,'2015-02-11 11:11:11',18.54,'y'),
  (2,01,'2015-02-12 11:11:11',32.54,'y'),
  (3,01,'2015-02-13 11:11:11',44.54,'y'),
  (4,01,'2015-02-14 11:11:11',23.54,'y'),
  (5,02,'2015-02-15 11:11:11',32.54,'y'),
  (6,02,'2015-03-01 11:11:11',14.54,'y'),
  (7,02,'2015-03-02 11:11:11',8.54,'y'),
  (8,02,'2015-03-03 11:11:11',15.54,'y'),
  (9,02,'2015-03-04 11:11:11',10.54,'y'),
  (10,02,'2015-03-05 11:11:11',9.54,'y'),
  (11,02,'2015-03-06 11:11:11',7.54,'y'),
  (12,02,'2015-03-07 11:11:11',17.54,'y'),
  (13,02,'2015-03-08 11:11:11',11.54,'y'),
  (14,02,'2015-03-09 11:11:11',22.54,'n'),
  (15,01,'2015-03-10 11:11:11',32.54,'n');

DROP TABLE IF EXISTS Product;
Create Table Product(
  ProductISBN13 varchar(13) UNIQUE,
  ProductISBN10 varchar(10),
  ProductDescription varchar(254) not null default '',
  ProductAuthor varchar(254) not null default '',
  ProductPrice decimal(7,2) not null default '0.00',
  ProductCategory VARCHAR(100),
  ProductStock int not null default '0',
  ProductAddedDate DATETIME,

  Primary Key (ProductISBN13)
);

INSERT INTO Product VALUES
  (9780525427209,0525427201,"Bettyville: A Memoir","George Hodgman",16.77,"Biographies & Memoirs", 15, curtime()),
  (9781476772295,1476772290,"Get What's Yours: The Secrets to Maxing Out Your Social Security","Laurence J. Kotlikoff, Philip Moeller, Paul Solman",16.69,"Business & Money", 20, curtime()),
  (9783869307886,3869307889,"Henri Cartier-Bresson: The Decisive Moment","Cartier Bresson",84.99,"Arts & Photography", 20, curtime()),
  (9781449460365,1449460364,"Exploring Calvin and Hobbes: An Exhibition Catalogue","Bill Watterson, Robb Jenny", 12.10, "Arts & Photography", 30, curtime()),
  (9781612184340,1612184340,"The Beginning of the End (Apocalypse Z)", "Manel Loureiro", 9.03, "Literature & Fiction", 30, curtime()),
  (9780451470041,0451470044,"Skin Game: A Novel of the Dresden Files","Jim Butcher",9.91,"Science Fiction & Fantasy",100,curtime()),
  (9781477824986,1477824987,"Blood of Gods (The Breaking World)","David Dalglish, Robert J. Duperre",9.49,"Science Fiction & Fantasy",100,curtime()),
  (9780805098051,0805098054,"The Barefoot Lawyer: A Blind Man's Fight for Justice and Freedom in China","Chen Guangcheng",22.93,"Biographies & Memoirs",20,curtime()),
  (9781476783703,1476783705,"From Jailer to Jailed: My Journey from Correction and Police Commissioner to Inmate #84888-054","Bernard B. Kerik",19.89,"Biographies & Memoirs",20,curtime());

DROP TABLE IF EXISTS Lineitem;
CREATE TABLE LineItem (
  LineItemID INT NOT NULL AUTO_INCREMENT,
  InvoiceID INT NOT NULL DEFAULT '0',
  ProductID varchar(13),
  Quantity INT NOT NULL DEFAULT '0',
  PRIMARY KEY (LineItemID),
  FOREIGN KEY (InvoiceID) REFERENCES Invoice (InvoiceID),
  FOREIGN KEY (ProductID) REFERENCES Product (ProductISBN13)
);

INSERT INTO LineItem VALUES
  (1,1,'9780525427209',2),
  (2,2,'9781476772295',10),
  (3,3,'9783869307886',1),
  (4,4,'9781449460365',2),
  (5,5,'9781612184340',2),
  (6,6,'9780451470041',2),
  (7,7,'9781477824986',3),
  (8,8,'9780805098051',4),
  (9,9,'9781476783703',5),
  (10,10,'9780525427209',2),
  (11,11,'9780525427209',2),
  (12,12,'9780451470041',2),
  (13,13,'9780525427209',2),
  (14,14,'9780525427209',2),
  (15,15,'9781476783703',2)
;

DROP TABLE IF EXISTS Ratings;
Create Table Ratings(
  RatingID INT NOT NULL AUTO_INCREMENT,
  RatingISBN13 varchar(13),
  RatingValue INT NOT NULL default '0',
  RatingEmail VARCHAR(50) NOT NULL,

  RatingDescription VARCHAR(254),

  Primary key (RatingID),
  FOREIGN KEY (RatingISBN13) REFERENCES Product (ProductISBN13),
  FOREIGN KEY (RatingEmail) REFERENCES User (EmailAddress)

);

DROP TABLE IF EXISTS Purchase;
Create Table Purchase(
  PurchaseID int not null auto_increment,
  UserID bigint not null,
  PurchaseDate datetime not null,
  ProductCode varchar(10) not null,

  Primary Key (PurchaseID),
  Foreign Key (UserID) References User (UserID)
);
DROP TABLE IF EXISTS UserPass;
create table UserPass(
  Username varchar(15) not null primary key,
  Password varchar(15) not null
);

insert into UserPass values
  ('filip', 'todorovic'),
  ('chris', 'tran');

DROP TABLE IF EXISTS UserRole;
Create Table UserRole(
  Username varchar(15) not null,
  Rolename varchar(15) not null,

  Primary Key (Username, Rolename)
);

insert into UserRole values
  ('filip','student'),
  ('chris','student');
