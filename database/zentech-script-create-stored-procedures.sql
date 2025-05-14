/* =======================================================================
   ZENTECH_POLYCAFE – Stored Procedures
   Author : zentech
   Date   : 2025-05-13
   ======================================================================= */

-- 0. DATABASE ---------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS zentech_polycafe
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE zentech_polycafe;

-- =======================================================================
-- 1. ROLE
-- =======================================================================

-- INSERT
DELIMITER //
CREATE PROCEDURE insert_role(IN p_roleName VARCHAR(100))
BEGIN
    INSERT INTO ROLE (roleName)
    VALUES (p_roleName);
END //
DELIMITER ;

-- UPDATE
DELIMITER //
CREATE PROCEDURE update_role(IN p_id INT, IN p_roleName VARCHAR(100))
BEGIN
    UPDATE ROLE
    SET roleName = p_roleName
    WHERE id = p_id;
END //
DELIMITER ;

-- DELETE
DELIMITER //
CREATE PROCEDURE delete_role(IN p_id INT)
BEGIN
    DELETE FROM ROLE
    WHERE id = p_id;
END //
DELIMITER ;

-- =======================================================================
-- 2. CARD
-- =======================================================================

-- INSERT
DELIMITER //
CREATE PROCEDURE insert_card(IN p_status VARCHAR(50))
BEGIN
    INSERT INTO CARD (status)
    VALUES (p_status);
END // 
DELIMITER ;

-- UPDATE
DELIMITER //
CREATE PROCEDURE update_card(IN p_id INT, IN p_status VARCHAR(50))
BEGIN
    UPDATE CARD
    SET status = p_status
    WHERE id = p_id;
END //
DELIMITER ;

-- DELETE
DELIMITER //
CREATE PROCEDURE delete_card(IN p_id INT)
BEGIN
    DELETE FROM CARD
    WHERE id = p_id;
END //
DELIMITER ;

-- =======================================================================
-- 3. CATEGORY
-- =======================================================================

-- INSERT
DELIMITER //
CREATE PROCEDURE insert_category(IN p_categoryName VARCHAR(255))
BEGIN
    INSERT INTO CATEGORY (categoryName)
    VALUES (p_categoryName);
END //
DELIMITER ;

-- UPDATE
DELIMITER //
CREATE PROCEDURE update_category(IN p_id INT, IN p_categoryName VARCHAR(255))
BEGIN
    UPDATE CATEGORY
    SET categoryName = p_categoryName
    WHERE id = p_id;
END //
DELIMITER ;

-- DELETE
DELIMITER //
CREATE PROCEDURE delete_category(IN p_id INT)
BEGIN
    DELETE FROM CATEGORY
    WHERE id = p_id;
END //
DELIMITER ;

-- =======================================================================
-- 4. USER
-- =======================================================================

-- INSERT
DELIMITER //
CREATE PROCEDURE insert_user(
    IN p_roleId INT,
    IN p_userName VARCHAR(100),
    IN p_password VARCHAR(100),
    IN p_email VARCHAR(100),
    IN p_fullName VARCHAR(100),
    IN p_gender VARCHAR(10),
    IN p_address VARCHAR(255),
    IN p_dob DATE,
    IN p_phoneNumber VARCHAR(20)
)
BEGIN
    INSERT INTO USER (
        roleId, userName, password, email, fullName, gender, address, dob, phoneNumber
    ) VALUES (
        p_roleId, p_userName, p_password, p_email, p_fullName, p_gender, p_address, p_dob, p_phoneNumber
    );
END //
DELIMITER ;

-- UPDATE
DELIMITER //
CREATE PROCEDURE update_user(
    IN p_id INT,
    IN p_roleId INT,
    IN p_userName VARCHAR(100),
    IN p_password VARCHAR(100),
    IN p_email VARCHAR(100),
    IN p_fullName VARCHAR(100),
    IN p_gender VARCHAR(10),
    IN p_address VARCHAR(255),
    IN p_dob DATE,
    IN p_phoneNumber VARCHAR(20)
)
BEGIN
    UPDATE USER
    SET roleId = p_roleId,
        userName = p_userName,
        password = p_password,
        email = p_email,
        fullName = p_fullName,
        gender = p_gender,
        address = p_address,
        dob = p_dob,
        phoneNumber = p_phoneNumber
    WHERE id = p_id;
END //
DELIMITER ;

-- DELETE
DELIMITER //
CREATE PROCEDURE delete_user(IN p_id INT)
BEGIN
    DELETE FROM USER WHERE id = p_id;
END //
DELIMITER ;

-- =======================================================================
-- 5. PRODUCT
-- =======================================================================

-- INSERT
DELIMITER //
CREATE PROCEDURE insert_product(
    IN p_categoryId INT,
    IN p_name VARCHAR(255),
    IN p_price DOUBLE,
    IN p_active VARCHAR(50),
    IN p_description TEXT,
    IN p_imageUrl TEXT
)
BEGIN
    INSERT INTO PRODUCT (categoryId, name, price, active, description, imageUrl)
    VALUES (p_categoryId, p_name, p_price, p_active, p_description, p_imageUrl);
END //
DELIMITER ;

-- UPDATE
DELIMITER //
CREATE PROCEDURE update_product(
    IN p_id INT,
    IN p_categoryId INT,
    IN p_name VARCHAR(255),
    IN p_price DOUBLE,
    IN p_active VARCHAR(50),
    IN p_description TEXT,
    IN p_imageUrl TEXT
)
BEGIN
    UPDATE PRODUCT
    SET categoryId = p_categoryId,
        name = p_name,
        price = p_price,
        active = p_active,
        description = p_description,
        imageUrl = p_imageUrl
    WHERE id = p_id;
END //
DELIMITER ;

-- DELETE
DELIMITER //
CREATE PROCEDURE delete_product(IN p_id INT)
BEGIN
    DELETE FROM PRODUCT WHERE id = p_id;
END //
DELIMITER ;

-- =======================================================================
-- 6. BILL
-- =======================================================================

-- INSERT
DELIMITER //
CREATE PROCEDURE insert_bill(
    IN p_userId INT,
    IN p_cardId INT,
    IN p_status VARCHAR(50)
)
BEGIN
    INSERT INTO BILL (userId, cardId, status)
    VALUES (p_userId, p_cardId, p_status);
END //
DELIMITER ;

-- UPDATE
DELIMITER //
CREATE PROCEDURE update_bill(
    IN p_id INT,
    IN p_userId INT,
    IN p_cardId INT,
    IN p_status VARCHAR(50)
)
BEGIN
    UPDATE BILL
    SET userId = p_userId,
        cardId = p_cardId,
        status = p_status
    WHERE id = p_id;
END //
DELIMITER ;

-- DELETE
DELIMITER //
CREATE PROCEDURE delete_bill(IN p_id INT)
BEGIN
    DELETE FROM BILL WHERE id = p_id;
END //
DELIMITER ;

-- =======================================================================
-- 7. BILLDETAILS
-- =======================================================================

-- INSERT
DELIMITER //
CREATE PROCEDURE insert_billdetails(
    IN p_billId INT,
    IN p_productId INT,
    IN p_date TIMESTAMP,
    IN p_quantity INT,
    IN p_discount FLOAT,
    IN p_totalPriceNoVAT DOUBLE,
    IN p_totalPriceWithVAT DOUBLE
)
BEGIN
    INSERT INTO BILLDETAILS (
        billId, productId, date, quantity, discount, totalPriceNoVAT, totalPriceWithVAT
    ) VALUES (
        p_billId, p_productId, p_date, p_quantity, p_discount, p_totalPriceNoVAT, p_totalPriceWithVAT
    );
END //
DELIMITER ;

-- UPDATE
DELIMITER //
CREATE PROCEDURE update_billdetails(
    IN p_id INT,
    IN p_billId INT,
    IN p_productId INT,
    IN p_date TIMESTAMP,
    IN p_quantity INT,
    IN p_discount FLOAT,
    IN p_totalPriceNoVAT DOUBLE,
    IN p_totalPriceWithVAT DOUBLE
)
BEGIN
    UPDATE BILLDETAILS
    SET billId = p_billId,
        productId = p_productId,
        date = p_date,
        quantity = p_quantity,
        discount = p_discount,
        totalPriceNoVAT = p_totalPriceNoVAT,
        totalPriceWithVAT = p_totalPriceWithVAT
    WHERE id = p_id;
END //
DELIMITER ;

-- DELETE
DELIMITER //
CREATE PROCEDURE delete_billdetails(IN p_id INT)
BEGIN
    DELETE FROM BILLDETAILS WHERE id = p_id;
END //
DELIMITER ;