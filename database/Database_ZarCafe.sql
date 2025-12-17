-- ========================================================
-- Cafe Management System - Database Setup Script
-- Project: ZAR_CAFE
-- Team Members: Abdullah, Zeyad, Rewan, Rana, Rawaa, Rawan
-- ========================================================

-- Drop and recreate database from scratch
DROP DATABASE IF EXISTS ZAR_CAFE;
CREATE DATABASE ZAR_CAFE;
USE ZAR_CAFE;

-- Table 1: Customer Information
CREATE TABLE Customers (
    CUSTOMER_ID INT NOT NULL AUTO_INCREMENT,
    USER_NAME VARCHAR(50) NOT NULL,
    PHONE_NUMBER VARCHAR(15) NOT NULL,
    PASSWORD VARCHAR(30) NOT NULL,
    CUSTOMER_ADDRESS VARCHAR(50),
    ROLE VARCHAR(20) DEFAULT 'User',
    GENDER VARCHAR(10) DEFAULT 'Male',
    PRIMARY KEY (CUSTOMER_ID),
    UNIQUE KEY PHONE_NUMBER_UNIQUE (PHONE_NUMBER)
);

-- Table 2: Menu Items and Prices
CREATE TABLE Menu_Items (
    ITEM_ID INT PRIMARY KEY AUTO_INCREMENT,
    ITEM_NAME VARCHAR(100) UNIQUE NOT NULL,
    PRICE DOUBLE NOT NULL
);

-- Table 3: Orders History
CREATE TABLE Orders (
    ORDER_ID INT PRIMARY KEY AUTO_INCREMENT,
    CUSTOMER_NAME VARCHAR(100),
    PHONE_NUMBER VARCHAR(20),
    ORDER_DATE DATE,
    TOTAL_PRICE DOUBLE
);

-- Table 4: Details of Each Order
CREATE TABLE Order_Items (
    DETAIL_ID INT PRIMARY KEY AUTO_INCREMENT,
    ORDER_ID INT,
    ITEM_NAME VARCHAR(100),
    QUANTITY INT,
    PRICE DOUBLE,
    FOREIGN KEY (ORDER_ID) REFERENCES Orders(ORDER_ID)
);

-- Insert sample data for Customers
INSERT INTO Customers (USER_NAME, PHONE_NUMBER, PASSWORD, CUSTOMER_ADDRESS, ROLE, GENDER) VALUES
('admin', '123', 'admin123', 'Cairo', 'Admin', 'Male'),
('Abdullah Darahem', '01026670191', 'Abdullah', 'Cairo', 'User', 'Male'),
('Zeyad Ahmed', '01032933151', 'Zeyad', 'Monofia', 'User', 'Male'),
('Rewan Reda', '01557006643', 'Rewan', 'Banha', 'User', 'Female'),
('Rana Ehgannam', '01141695872', 'Rana', 'Banha', 'User', 'Female'),
('Rawaa Elsayed', '01096995174', 'Rawaa', 'Banha', 'User', 'Female'),
('Rawan Osama', '01028564793', 'Rawan', 'Banha', 'User', 'Female');
-- Insert menu items
INSERT INTO Menu_Items (ITEM_NAME, PRICE) VALUES
('Coffee', 50.0),
('Latte', 100.0),
('Cake', 80.0),
('Sandwich', 110.0),
('Ice Cream', 30.0),
('Tea', 40.0);

-- Final check
SELECT "Database setup completed successfully!" AS Status;
SELECT * FROM Customers;
SELECT * FROM Menu_Items;
SELECT * FROM Orders;
SELECT * FROM Order_Items;