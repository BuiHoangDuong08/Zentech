
/* =======================================================================
   ZENTECH_POLYCAFE – Stored Procedures
   Author : Zentech
   Date   : 2025-05-20
   ======================================================================= */
USE zentech_polycafe;

DELIMITER //


/* =======================================================================
   1. ROLE
   ======================================================================= */
DROP PROCEDURE IF EXISTS insert_role //
CREATE PROCEDURE insert_role(
    IN p_RoleName VARCHAR(20)
)
BEGIN
    INSERT INTO ROLE (RoleName)
    VALUES (p_RoleName);
END //

DROP PROCEDURE IF EXISTS update_role //
CREATE PROCEDURE update_role(
    IN p_ID INT,
    IN p_RoleName VARCHAR(20)
)
BEGIN
    UPDATE ROLE
    SET RoleName = p_RoleName
    WHERE ID = p_ID;
END //

DROP PROCEDURE IF EXISTS delete_role //
CREATE PROCEDURE delete_role(
    IN p_ID INT
)
BEGIN
    DELETE FROM ROLE
    WHERE ID = p_ID;
END //

/* =======================================================================
   2. CARD
   ======================================================================= */
DROP PROCEDURE IF EXISTS insert_card //
CREATE PROCEDURE insert_card(
    IN p_Status ENUM('ACTIVE','WAIT','LOCKED')
)
BEGIN
    INSERT INTO CARD (Status)
    VALUES (p_Status);
END //

DROP PROCEDURE IF EXISTS update_card //
CREATE PROCEDURE update_card(
    IN p_ID INT,
    IN p_Status ENUM('ACTIVE','WAIT','LOCKED')
)
BEGIN
    UPDATE CARD
    SET Status = p_Status
    WHERE ID = p_ID;
END //

DROP PROCEDURE IF EXISTS delete_card //
CREATE PROCEDURE delete_card(
    IN p_ID INT
)
BEGIN
    DELETE FROM CARD
    WHERE ID = p_ID;
END //

/* =======================================================================
   3. CATEGORY
   ======================================================================= */
DROP PROCEDURE IF EXISTS insert_category //
CREATE PROCEDURE insert_category(
    IN p_CategoryName VARCHAR(20)
)
BEGIN
    INSERT INTO CATEGORY (CategoryName)
    VALUES (p_CategoryName);
END //

DROP PROCEDURE IF EXISTS update_category //
CREATE PROCEDURE update_category(
    IN p_ID INT,
    IN p_CategoryName VARCHAR(20)
)
BEGIN
    UPDATE CATEGORY
    SET CategoryName = p_CategoryName
    WHERE ID = p_ID;
END //

DROP PROCEDURE IF EXISTS delete_category //
CREATE PROCEDURE delete_category(
    IN p_ID INT
)
BEGIN
    DELETE FROM CATEGORY
    WHERE ID = p_ID;
END //

/* =======================================================================
   4. USER
   ======================================================================= */
DROP PROCEDURE IF EXISTS insert_user //
CREATE PROCEDURE insert_user(
    IN p_Role_ID INT,
    IN p_UserName VARCHAR(20),
    IN p_Password VARCHAR(60),
    IN p_Email VARCHAR(254),
    IN p_FullName VARCHAR(50),
    IN p_Gender ENUM('MALE','FEMALE','NONE'),
    IN p_Address VARCHAR(100),
    IN p_DoB DATE,
    IN p_PhoneNumber VARCHAR(10)
)
BEGIN
    INSERT INTO USER (
        Role_ID, UserName, Password, Email, FullName, Gender, Address, DoB, PhoneNumber
    ) VALUES (
        p_Role_ID, p_UserName, p_Password, p_Email, p_FullName, p_Gender, p_Address, p_DoB, p_PhoneNumber
    );
END //

DROP PROCEDURE IF EXISTS update_user //
CREATE PROCEDURE update_user(
    IN p_ID INT,
    IN p_Role_ID INT,
    IN p_UserName VARCHAR(20),
    IN p_Password VARCHAR(60),
    IN p_Email VARCHAR(254),
    IN p_FullName VARCHAR(50),
    IN p_Gender ENUM('MALE','FEMALE','NONE'),
    IN p_Address VARCHAR(100),
    IN p_DoB DATE,
    IN p_PhoneNumber VARCHAR(10)
)
BEGIN
    UPDATE USER
    SET Role_ID     = p_Role_ID,
        UserName    = p_UserName,
        Password    = p_Password,
        Email       = p_Email,
        FullName    = p_FullName,
        Gender      = p_Gender,
        Address     = p_Address,
        DoB         = p_DoB,
        PhoneNumber = p_PhoneNumber
    WHERE ID = p_ID;
END //

DROP PROCEDURE IF EXISTS delete_user //
CREATE PROCEDURE delete_user(
    IN p_ID INT
)
BEGIN
    DELETE FROM USER
    WHERE ID = p_ID;
END //

/* =======================================================================
   5. PRODUCT
   ======================================================================= */
DROP PROCEDURE IF EXISTS insert_product //
CREATE PROCEDURE insert_product(
    IN p_Category_ID INT,
    IN p_Name VARCHAR(20),
    IN p_Price DECIMAL(10,2),
    IN p_Active ENUM('ACTIVE','LOCKED'),
    IN p_Description VARCHAR(100),
    IN p_Image_URL VARCHAR(100)
)
BEGIN
    INSERT INTO PRODUCT (
        Category_ID, Name, Price, Active, Description, Image_URL
    ) VALUES (
        p_Category_ID, p_Name, p_Price, p_Active, p_Description, p_Image_URL
    );
END //

DROP PROCEDURE IF EXISTS update_product //
CREATE PROCEDURE update_product(
    IN p_ID INT,
    IN p_Category_ID INT,
    IN p_Name VARCHAR(20),
    IN p_Price DECIMAL(10,2),
    IN p_Active ENUM('ACTIVE','LOCKED'),
    IN p_Description VARCHAR(100),
    IN p_Image_URL VARCHAR(100)
)
BEGIN
    UPDATE PRODUCT
    SET Category_ID = p_Category_ID,
        Name        = p_Name,
        Price       = p_Price,
        Active      = p_Active,
        Description = p_Description,
        Image_URL   = p_Image_URL
    WHERE ID = p_ID;
END //

DROP PROCEDURE IF EXISTS delete_product //
CREATE PROCEDURE delete_product(
    IN p_ID INT
)
BEGIN
    DELETE FROM PRODUCT
    WHERE ID = p_ID;
END //

/* =======================================================================
   6. BILL
   ======================================================================= */
DROP PROCEDURE IF EXISTS insert_bill //
CREATE PROCEDURE insert_bill(
    IN p_User_ID INT,
    IN p_Card_ID INT,
    IN p_Status ENUM('UNPAID','PAID','CANCELLED')
)
BEGIN
    INSERT INTO BILL (User_ID, Card_ID, Status)
    VALUES (p_User_ID, p_Card_ID, p_Status);
END //

DROP PROCEDURE IF EXISTS update_bill //
CREATE PROCEDURE update_bill(
    IN p_ID INT,
    IN p_User_ID INT,
    IN p_Card_ID INT,
    IN p_Status ENUM('UNPAID','PAID','CANCELLED')
)
BEGIN
    UPDATE BILL
    SET User_ID = p_User_ID,
        Card_ID = p_Card_ID,
        Status  = p_Status
    WHERE ID = p_ID;
END //

DROP PROCEDURE IF EXISTS delete_bill //
CREATE PROCEDURE delete_bill(
    IN p_ID INT
)
BEGIN
    DELETE FROM BILL
    WHERE ID = p_ID;
END //

/* =======================================================================
   7. BILLDETAILS
   ======================================================================= */
DROP PROCEDURE IF EXISTS insert_billdetails //
CREATE PROCEDURE insert_billdetails(
    IN p_Bill_ID INT,
    IN p_Product_ID INT,
    IN p_Date TIMESTAMP,
    IN p_Quantity INT,
    IN p_Discount FLOAT,
    IN p_TotalPrice_NoVAT DECIMAL(10,2),
    IN p_TotalPrice_WithVAT DECIMAL(10,2)
)
BEGIN
    INSERT INTO BILLDETAILS (
        Bill_ID, Product_ID, `Date`, Quantity, Discount, TotalPrice_NoVAT, TotalPrice_WithVAT
    ) VALUES (
        p_Bill_ID, p_Product_ID, p_Date, p_Quantity, p_Discount, p_TotalPrice_NoVAT, p_TotalPrice_WithVAT
    );
END //

DROP PROCEDURE IF EXISTS update_billdetails //
CREATE PROCEDURE update_billdetails(
    IN p_ID INT,
    IN p_Bill_ID INT,
    IN p_Product_ID INT,
    IN p_Date TIMESTAMP,
    IN p_Quantity INT,
    IN p_Discount FLOAT,
    IN p_TotalPrice_NoVAT DECIMAL(10,2),
    IN p_TotalPrice_WithVAT DECIMAL(10,2)
)
BEGIN
    UPDATE BILLDETAILS
    SET Bill_ID            = p_Bill_ID,
        Product_ID         = p_Product_ID,
        `Date`             = p_Date,
        Quantity           = p_Quantity,
        Discount           = p_Discount,
        TotalPrice_NoVAT   = p_TotalPrice_NoVAT,
        TotalPrice_WithVAT = p_TotalPrice_WithVAT
    WHERE ID = p_ID;
END //

DROP PROCEDURE IF EXISTS delete_billdetails //
CREATE PROCEDURE delete_billdetails(
    IN p_ID INT
)
BEGIN
    DELETE FROM BILLDETAILS
    WHERE ID = p_ID;
END //

/* =======================================================================
   End of Stored Procedures
   ======================================================================= */

DELIMITER ;
