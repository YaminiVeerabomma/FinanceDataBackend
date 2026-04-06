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

### 👁️ Viewer
- View dashboard data only
- Cannot access or modify records

### 📊 Analyst
- View financial records
- Access insights and summaries

### 🛠️ Admin
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

## 📚 Swagger API Docs

- Interactive API testing
- Easy to explore endpoints
- Access at: `http://localhost:8080/swagger-ui.html`

---

## 🏗️ Architecture

```
Controller → Service → Repository → Database
↓
Security Layer (JWT + Rate Limiting)
```

---

## 📁 Project Structure

```
src/main/java/com/example/FinanceDataBackend/
├── controller/
├── service/
├── repository/
├── entity/
├── dto/
├── security/
└── exception/
```

---

## ⚙️ How to Run Project

### 1️⃣ Clone Repository

```bash
git clone https://github.com/YaminiVeerabomma/FinanceDataBackend.git
cd FinanceDataBackend
```

### 2️⃣ Setup Database

Create MySQL database:

```sql
CREATE DATABASE finance_db;
```

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/finance_db
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3️⃣ Build Project

```bash
mvn clean install
```

### 4️⃣ Run Application

```bash
mvn spring-boot:run
```

---

## 🔑 API Examples

### Login

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "user@example.com",
  "role": "ANALYST"
}
```

### Get Records

```http
GET /api/records?page=0&size=10
Authorization: Bearer <token>
```

### Dashboard

```http
GET /api/dashboard
Authorization: Bearer <token>
```

---

## ⚠️ Error Handling

```json
{
  "status": 429,
  "error": "Too Many Requests",
  "message": "Try again after some time"
}
```

---

## ⭐ Optional Enhancements Implemented

- ✅ JWT Authentication
- ✅ Role-Based Access Control
- ✅ Pagination
- ✅ Rate Limiting
- ✅ Swagger Documentation
- ✅ Global Exception Handling

---

## 🧪 What I Implemented

- Designed REST APIs for financial records
- Implemented JWT authentication & security
- Applied role-based authorization
- Built dashboard summary APIs
- Added pagination for scalability
- Implemented rate limiting for API protection
- Structured project using layered architecture

---

## 📌 Assignment Coverage

- ✔️ User & Role Management
- ✔️ Financial Records CRUD
- ✔️ Dashboard APIs
- ✔️ Access Control
- ✔️ Validation & Error Handling
- ✔️ Data Persistence

---

## 👩‍💻 Author

**Yamini Veerabomma**  
📧 [yaminiveerabomma@gmail.com](mailto:yaminiveerabomma@gmail.com)

---

## 📜 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

## ⭐ Final Note

This project focuses on building a secure, scalable, and well-structured backend system using modern best practices like JWT authentication, RBAC, pagination, and rate limiting.
