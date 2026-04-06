# 💰 Finance Data Backend

> **Secure Backend System for Financial Data Processing & Access Control**

---

## 🚀 Project Overview

This project is a Spring Boot backend application built to manage financial records with secure access control.

It demonstrates:

- Clean backend architecture
- Role-based access control
- Secure authentication
- Scalable API design

The system allows different users to interact with financial data based on their roles.

---

## 🎯 Objective

To design and develop a backend system that:

- Manages financial records
- Controls access using roles
- Provides dashboard insights
- Ensures security and performance

---

## 🧱 Tech Stack

| Technology | Purpose |
|-----------|---------|
| Java 17 | Core programming language |
| Spring Boot | Backend framework |
| Spring Security | Authentication & authorization |
| JWT | Secure token-based authentication |
| MySQL | Database |
| Maven | Build tool |
| Swagger/OpenAPI | API documentation |

---

## 👥 Roles & Responsibilities

### 👁 Viewer
- View dashboard data only
- Cannot access or modify records

### 📊 Analyst
- View financial records
- Access insights and summaries

### 🛠 Admin
Full access:
- Create records
- Update records
- Delete records
- Manage users

---

## 🔐 Authentication & Authorization

- Users login using email & password
- JWT token is generated after login
- Token must be passed in every request
- `Authorization: Bearer <token>`
- Access is controlled using roles (RBAC)

---

## 📊 Features

### Financial Records
- Create, read, update, delete records
- Filter by category, type, date
- Clean API structure

### 📈 Dashboard
- Total income
- Total expenses
- Net balance
- Summary insights

### 📄 Pagination
- Used for handling large datasets
- Improves performance
- Example: `GET /api/records?page=0&size=10`

### ⚡ Rate Limiting
- Prevents API abuse
- Protects login from brute-force attacks

| API | Limit | Block Time |
|-----|-------|-----------|
| Login | 5 attempts | 15 min |
| Records | 25 requests | 1 min |
| Dashboard | 25 requests | 1 min |

---

## ��� Swagger API Docs

- Interactive API testing
- Easy to explore endpoints
- Access at: `http://localhost:8080/swagger-ui.html`

---

## 🧠 Architecture
