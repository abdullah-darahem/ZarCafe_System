-- ========================================================
-- Project name: ZAR_CAFE
-- ========================================================

-- 1. Remove the database if it already exists and create it again
DROP DATABASE IF EXISTS ZAR_CAFE;
CREATE DATABASE ZAR_CAFE;
USE ZAR_CAFE;

-- 2. Customers table
-- This table stores all customer account information
CREATE TABLE Customers (
    CUSTOMER_ID INT NOT NULL AUTO_INCREMENT,
    USER_NAME VARCHAR(50) NOT NULL,
    PHONE_NUMBER VARCHAR(15) NOT NULL,
    PASSWORD VARCHAR(30) NOT NULL,
    CUSTOMER_ADDRESS VARCHAR(50),
    ROLE VARCHAR(20) DEFAULT 'User',
    WALLET_BALANCE DOUBLE DEFAULT 0.0, -- Customer wallet balance
    PRIMARY KEY (CUSTOMER_ID),
    UNIQUE KEY PHONE_NUMBER_UNIQUE (PHONE_NUMBER)
);

-- 3. Menu_Items table
-- This table stores all products available in the cafe
CREATE TABLE Menu_Items (
    ITEM_ID INT PRIMARY KEY AUTO_INCREMENT,
    ITEM_NAME VARCHAR(100) UNIQUE NOT NULL,
    PRICE DOUBLE NOT NULL,
    STOCK_QUANTITY INT DEFAULT 0,
    IS_AVAILABLE BOOLEAN DEFAULT TRUE,
    IMAGE_PATH VARCHAR(255)
);

-- 4. Orders table
-- This table stores order history with subtotal, discount, and total price
CREATE TABLE Orders (
    ORDER_ID INT PRIMARY KEY AUTO_INCREMENT,
    CUSTOMER_ID INT NOT NULL,
    ORDER_DATE DATETIME DEFAULT CURRENT_TIMESTAMP,
    SUBTOTAL DOUBLE NOT NULL,      -- Total before discount
    DISCOUNT DOUBLE DEFAULT 0.0,   -- Discount value
    TOTAL_PRICE DOUBLE,            -- Final total after discount
FOREIGN KEY (CUSTOMER_ID) REFERENCES Customers(CUSTOMER_ID) ON DELETE CASCADE
);

-- 5. Order_Items table
-- This table stores details of each item inside an order
CREATE TABLE Order_Items (
    DETAIL_ID INT PRIMARY KEY AUTO_INCREMENT,
    ORDER_ID INT NOT NULL,
    ITEM_ID INT NOT NULL,
    QUANTITY INT NOT NULL DEFAULT 1,
    PRICE DOUBLE,
    FOREIGN KEY (ORDER_ID) REFERENCES Orders(ORDER_ID) ON DELETE CASCADE,
    FOREIGN KEY (ITEM_ID) REFERENCES Menu_Items(ITEM_ID) ON DELETE RESTRICT
);

-- 6. Wallet_Transactions table
-- This table stores all wallet deposit and deduction operations
-- Transaction date is saved automatically
CREATE TABLE Wallet_Transactions (
    TRANS_ID INT PRIMARY KEY AUTO_INCREMENT,
    CUSTOMER_ID INT,
    AMOUNT DOUBLE,
    TRANS_DATE DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (CUSTOMER_ID) REFERENCES Customers(CUSTOMER_ID) ON DELETE CASCADE
);

-- ==========================================
-- Insert sample data for testing
-- ==========================================

-- Insert sample customers
INSERT INTO Customers (USER_NAME, PHONE_NUMBER, PASSWORD, CUSTOMER_ADDRESS, ROLE, WALLET_BALANCE) VALUES
('admin', '123', 'admin123', 'Cairo', 'Admin', 0.0), 
('Abdullah Darahem', '01026670191', 'Abdullah', 'Cairo', 'User', 5000.0),
('Zeyad Ahmed', '01032933151', 'Zeyad', 'Monofia', 'User', 5000.0),    
('Rana Ehgannam', '01141695872', 'Rana', 'Banha', 'User', 5000.0),
('Rewan Reda', '01557006643', 'Rewan', 'Banha', 'User', 5000.0),        
('Rawaa Elsayed', '01096995174', 'Rawaa', 'Banha', 'User', 5000.0),
('Rawan Osama', '01028564793', 'Rawan', 'Banha', 'User', 5000.0);

-- Insert sample menu items
INSERT INTO Menu_Items (ITEM_NAME, PRICE, STOCK_QUANTITY, IMAGE_PATH) VALUES 
('Coffee', 50.0, 100, 'src/images/coffee.jpg'), 
('Latte', 100.0, 100, 'src/images/latte.jpg'), 
('Cake', 80.0, 50, 'src/images/cake.jpg'), 
('Sandwich', 110.0, 50, 'src/images/sandwich.jpg'), 
('Ice Cream', 30.0, 200, 'src/images/ice_cream.jpg'), 
('Tea', 40.0, 150, 'src/images/tea.jpg');


-- Final check
SELECT "Database setup completed successfully!" AS Status;
SELECT * FROM Customers;
SELECT * FROM Menu_Items;
SELECT * FROM Orders;
SELECT * FROM Order_Items;