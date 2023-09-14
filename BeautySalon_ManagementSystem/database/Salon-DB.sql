CREATE DATABASE SalonTPS;
USE SalonTPS;

CREATE TABLE ACCOUNT (
     id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
     phone VARCHAR(20) NOT NULL UNIQUE,
     password VARCHAR (20) NOT NULL,
     name VARCHAR (50) NOT NULL,
     role VARCHAR (10) NOT NULL
);

CREATE TABLE SERVICE (
     id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
     name VARCHAR(20) NOT NULL,
     price FLOAT NOT NULL
);
CREATE TABLE BookingPayment(
  Booking_Payment_ID INT PRIMARY KEY  NOT NULL AUTO_INCREMENT,
  Cust_Name VARCHAR(20),
  Cust_Status VARCHAR(20),
  Cust_Amount VARCHAR(20),
  Cust_Discount VARCHAR(20),
  Cust_Total VARCHAR(20)
);

CREATE TABLE ReservationPayment(
  Reservation_Payment_ID INT PRIMARY KEY  NOT NULL AUTO_INCREMENT,
  Cust_Name VARCHAR(20),
  Cust_Status VARCHAR(20),
  Cust_Amount VARCHAR(20),
  Cust_Discount VARCHAR(20),
  Cust_Total VARCHAR(20)
);

CREATE TABLE Booking(
  Booking_No INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  Booking_Payment_ID INT NOT NULL,
  Cust_Name VARCHAR (20) NOT NULL,
  Cust_Address VARCHAR (20) NOT NULL,
  Cust_Phone VARCHAR (11) NOT NULL,
  Service_ID INT NOT NULL,
  Services_Name VARCHAR (20) NOT NULL,  
  Employee_Name VARCHAR (20) NOT NULL,  
  Acc_ID INT NOT NULL,
  Booking_Date DATE NOT NULL,
    
  FOREIGN KEY(Booking_Payment_ID) REFERENCES BookingPayment(Booking_Payment_ID),
  FOREIGN KEY(Service_ID) REFERENCES Service(Service_ID),
  FOREIGN KEY(Acc_ID) REFERENCES Account(Acc_ID) 
    
);


CREATE TABLE Reservation(
  Reservation_No INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  Reservation_Payment_ID INT NOT NULL,
  Cust_Name VARCHAR (20) NOT NULL,
  Cust_Address VARCHAR (20) NOT NULL,
  Cust_Phone VARCHAR (11) NOT NULL,
  Service_ID INT  NOT NULL,
  Services_Name VARCHAR (20) NOT NULL,
  Employee_Name VARCHAR (20) NOT NULL,
  Reserve_Time VARCHAR (20) NOT NULL,
  Reserve_Date DATE NOT NULL,
  Acc_ID INT NOT NULL,
    
  FOREIGN KEY(Reservation_Payment_ID) REFERENCES ReservationPayment(Reservation_Payment_ID),
  FOREIGN KEY(Service_ID) REFERENCES Service(Service_ID),
  FOREIGN KEY(Acc_ID) REFERENCES Account(Acc_ID)   
);