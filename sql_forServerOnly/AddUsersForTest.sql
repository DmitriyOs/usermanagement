Use usermanagement;

Insert into User(login,password,role,fullName) Values('first', 'first', 'admin','First Admin');
Insert into User(login,password,role,fullName,email) Values('second', 'second', 'admin','Second Admin','secondadmin@mysmallcompany.com');
Insert into User(login,password,role,fullName) Values('user1', 'user1', 'user','First User');
Insert into User(login,password,role,fullName,email,mobilePhone) Values('user2', 'user2', 'user', 'Second User','seconduser@mysmallcompany.com','+055331234568');
Insert into User(login,password,role,fullName,mobilePhone) Values('user3', 'user3', 'user','Third User','+055331234567');
