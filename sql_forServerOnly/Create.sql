DROP DATABASE IF EXISTS UserManagement;
CREATE DATABASE UserManagement;
Use UserManagement;
CREATE TABLE Role
(
	role varchar(20) NOT NULL PRIMARY KEY
);
CREATE TABLE User
(
	login varchar(20) NOT NULL PRIMARY KEY,
    password varchar(50) NOT NULL,
    role varchar(20) NOT NULL,
    fullName varchar(50) NOT NULL,
    email varchar(50),
    mobilePhone varchar(20),
    FOREIGN KEY (role) REFERENCES Role(role)
    
);
INSERT INTO Role VALUES ('admin');
INSERT INTO Role VALUES ('user');
COMMIT;