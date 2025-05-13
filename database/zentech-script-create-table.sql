/* =======================================================================
   ZENTECH_POLYCAFE – Core Schema
   Author : zentech
   Date   : 2025-05-13
   =======================================================================
   Default engine  : InnoDB
   Default charset : utf8mb4_unicode_ci
   ======================================================================= */

-- 0. DATABASE ---------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS zentech_polycafe
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE zentech_polycafe;

-- =======================================================================
-- 1. ROLE – Danh sách quyền
-- =======================================================================
CREATE TABLE IF NOT EXISTS `ROLE` (
    `ID`        INT          NOT NULL AUTO_INCREMENT,
    `RoleName`  VARCHAR(20)  NOT NULL,
    
    CONSTRAINT PK_ROLE PRIMARY KEY (`ID`),
    CONSTRAINT UK_ROLE_RoleName UNIQUE (`RoleName`)
) ENGINE = InnoDB;

-- =======================================================================
-- 2. CARD – Thẻ thông rung
-- =======================================================================
CREATE TABLE IF NOT EXISTS `CARD` (
    `ID`     INT  NOT NULL AUTO_INCREMENT,
    `Status` ENUM ('ACTIVE','WAIT','LOCKED') NOT NULL,
    
    CONSTRAINT PK_CARD PRIMARY KEY (`ID`)
) ENGINE = InnoDB;

-- =======================================================================
-- 3. CATEGORY – Nhóm sản phẩm
-- =======================================================================
CREATE TABLE IF NOT EXISTS `CATEGORY` (
    `ID`            INT          NOT NULL AUTO_INCREMENT,
    `CategoryName`  VARCHAR(20)  NOT NULL,
    
    CONSTRAINT PK_CATEGORY PRIMARY KEY (`ID`),
    CONSTRAINT UK_CATEGORY_Name UNIQUE (`CategoryName`)
) ENGINE = InnoDB;

-- =======================================================================
-- 4. USER – Thông tin người dùng
-- =======================================================================
CREATE TABLE IF NOT EXISTS `USER` (
    `ID`           INT  NOT NULL,
    `Role_ID`      INT           NOT NULL,
    `UserName`     VARCHAR(20)   NOT NULL,
    `Password`     VARCHAR(60)   NOT NULL,
    `Email`        VARCHAR(254)  NOT NULL,
    `FullName`     VARCHAR(50)   NOT NULL,
    `Gender`       ENUM ('MALE','FEMALE','NONE') NOT NULL,
    `Address`      VARCHAR(100)  NOT NULL,
    `DoB`          DATE          NOT NULL,
    `PhoneNumber`  VARCHAR(10)   NOT NULL,
    -- PK & UNIQUE
    CONSTRAINT PK_USER PRIMARY KEY (`ID`),
    CONSTRAINT UK_USER_UserName  UNIQUE (`UserName`),
    CONSTRAINT UK_USER_Email     UNIQUE (`Email`),
    CONSTRAINT UK_USER_Phone     UNIQUE (`PhoneNumber`),
    -- FK
    CONSTRAINT FK_USER_Role
        FOREIGN KEY (`Role_ID`)
        REFERENCES `ROLE` (`ID`)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    INDEX IDX_USER_Role (`Role_ID`)
) ENGINE = InnoDB;

-- =======================================================================
-- 5. PRODUCT – Danh mục sản phẩm
-- =======================================================================
CREATE TABLE IF NOT EXISTS `PRODUCT` (
    `ID`          INT           NOT NULL AUTO_INCREMENT,
    `Category_ID` INT           NOT NULL,
    `Name`        VARCHAR(20)   NOT NULL,
    `Price`       DECIMAL(10,2) NOT NULL,
    `Active`      ENUM ('ACTIVE','LOCKED') NOT NULL,
    `Description` VARCHAR(100),
    `Image_URL`   VARCHAR(100),
    -- PK
    CONSTRAINT PK_PRODUCT PRIMARY KEY (`ID`),
    -- FK
    CONSTRAINT FK_PRODUCT_Category
        FOREIGN KEY (`Category_ID`)
        REFERENCES `CATEGORY` (`ID`)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    INDEX IDX_PRODUCT_Category (`Category_ID`)
) ENGINE = InnoDB;

-- =======================================================================
-- 6. BILL – Hoá đơn
-- =======================================================================
CREATE TABLE IF NOT EXISTS `BILL` (
    `ID`       INT  NOT NULL AUTO_INCREMENT,
    `User_ID`  INT NOT NULL,
    `Card_ID`  INT          NOT NULL,
    `Status`   ENUM ('UNPAID','PAID','CANCELLED') NOT NULL,
    -- PK
    CONSTRAINT PK_BILL PRIMARY KEY (`ID`),
    -- FK
    CONSTRAINT FK_BILL_User
        FOREIGN KEY (`User_ID`)
        REFERENCES `USER` (`ID`)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT FK_BILL_Card
        FOREIGN KEY (`Card_ID`)
        REFERENCES `CARD` (`ID`)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    INDEX IDX_BILL_User (`User_ID`),
    INDEX IDX_BILL_Card (`Card_ID`)
) ENGINE = InnoDB;

-- =======================================================================
-- 7. BILLDETAILS – Dòng chi tiết hoá đơn
-- =======================================================================
CREATE TABLE IF NOT EXISTS `BILLDETAILS` (
    `ID`                 INT           NOT NULL AUTO_INCREMENT,
    `Bill_ID`            INT           NOT NULL,
    `Product_ID`         INT           NOT NULL,
    `Date`               TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `Quantity`           INT           NOT NULL,
    `Discount`           FLOAT,
    `TotalPrice_NoVAT`   DECIMAL(10,2) NOT NULL,
    `TotalPrice_WithVAT` DECIMAL(10,2) NOT NULL,
    -- PK
    CONSTRAINT PK_BILLDETAILS PRIMARY KEY (`ID`),
    -- FK
    CONSTRAINT FK_BILLDETAILS_Bill
        FOREIGN KEY (`Bill_ID`)
        REFERENCES `BILL` (`ID`)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT FK_BILLDETAILS_Product
        FOREIGN KEY (`Product_ID`)
        REFERENCES `PRODUCT` (`ID`)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    INDEX IDX_BILLDETAILS_Bill    (`Bill_ID`),
    INDEX IDX_BILLDETAILS_Product (`Product_ID`)
) ENGINE = InnoDB;