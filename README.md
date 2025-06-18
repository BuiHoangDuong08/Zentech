# Poly Cafe Management System

 [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) [![Java Version](https://img.shields.io/badge/Java-23-orange)](https://www.oracle.com/java/)

A modern desktop application for cafe management, developed by Zentech team. A comprehensive solution helping cafe owners and staff manage products, orders and improve customer experience.

![ZENTECH Banner](/readme/Zentech-main.jpg)

## Overview

**Poly Cafe Management System** is a Java-based desktop application developed to simulate and support the core operations of a modern cafe. The software provides essential features such as menu management, order processing, billing, sales tracking, role-based user access, activity monitoring, and smart interaction through an integrated AI chatbot. The goal is to enhance operational efficiency while maintaining ease of use for all user roles: Admin, Manager, and Cashier.

This project was developed following the Model-View-Controller (MVC) design pattern using Java Swing for the user interface and MySQL for structured data storage. In recent updates, the system incorporates MongoDB to improve performance for logging activities and storing unstructured AI chat data.

---

## Key Features

### 1. User Management

* Secure login and registration system
* Forgot password functionality using OTP sent via email
* Passwords are encrypted using BCrypt

### 2. Role-Based Access Control

* **Admin**: Full system control, user and product management, analytics, activity logs
* **Manager**: Can manage products and view analytics
* **Cashier**: Handles billing, orders, and receipts

### 3. Product and Menu Management

* Add, update, delete, and search food or beverage items
* Categorized product management with validation

### 4. Billing System

* Create and manage bills and receipts
* Calculate totals, taxes, discounts
* Export receipts to PDF using iTextPDF

### 5. AI Chatbot Integration

* Powered by OpenAI GPT API
* Responds to user queries in natural language
* Chat sessions are stored in MongoDB for history tracking

### 6. Activity Logging (System Audit)

* Records user actions (login, logout, create, delete...)
* Switched from relational database to MongoDB for better speed and flexibility

### 7. Reporting and Export

* Generate and export reports to Excel and PDF
* View sales history, most sold products

### 8. User Interface

* Built with Java Swing and enhanced using FlatLaf
* Supports both Light and Dark themes
* Organized navigation according to user roles

---

## Technology Stack

| Layer                | Technology                                        |
| -------------------- | ------------------------------------------------- |
| Programming Language | Java 17+                                          |
| Frontend             | Java Swing GUI with FlatLaf                       |
| Backend              | Java (Service, DAO, MVC Pattern)                  |
| Database             | MySQL (relational), MongoDB (NoSQL for logs/chat) |
| AI Integration       | OpenAI API (GPT)                                  |
| Email Service        | JavaMail API                                      |
| IDE                  | NetBeans (Apache Edition)                         |

---

## Project Structure

```
src/
├── controller/        # UI control logic
├── dao/               # DAO interfaces
├── daoImpl/           # Implementation of DAO
├── model/             # JavaBeans / Entities
├── service/           # Business logic layer
├── utils/             # Helpers (e.g., password, OTP, MongoDB)
├── view/              # Java Swing GUI forms
└── Main.java          # Entry point
```

---

## Deployment Guide

### 1. Prerequisites

* Java JDK 17+
* NetBeans Apache Edition
* MySQL Server
* MongoDB Server
* OpenAI API Key (for chatbot)

### 2. Setup Steps

#### 2.1. Clone Repository

```bash
git clone https://github.com/your-username/poly-cafe-management.git
```

#### 2.2. Database Configuration

* Import SQL schema for MySQL from `/sql/init_schema.sql`
* Configure `Connector.java` with your MySQL credentials
* Ensure MongoDB is running and available, adjust connection in `MongoConnector.java`

#### 2.3. Email OTP Setup

* Set up sender email, SMTP host and port in `SendEmailSMTP.java`
* Allow less secure apps or use app password if Gmail

#### 2.4. Run Application

* Open project in NetBeans
* Build and run `Main.java`
