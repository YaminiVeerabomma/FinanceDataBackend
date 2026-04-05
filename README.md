# Finance Data Backend

A robust Spring Boot application for managing financial records with enterprise-grade security, role-based access control, and JWT authentication.

## Overview

Finance Data Backend provides a comprehensive solution for financial data management with granular role-based access control. It allows **Admins** to manage records, **Analysts** to view and analyze data, and **Viewers** to access dashboards only.

## Features

### 🔐 Security & Authentication
- JWT-based authentication and authorization
- Role-based access control (RBAC)
- Spring Security integration
- Secure password management

### 👥 Role-Based Access
- **Viewer**: Can only view dashboard data
- **Analyst**: Can view records and generate insights
- **Admin**: Full access — create, update, delete records, manage users

### 📊 Data Management
- CRUD operations for financial records
- Advanced filtering by category, type, and date range
- Pagination support for large datasets
- Global exception handling with friendly JSON responses

### 📚 Documentation & API
- Swagger/OpenAPI API documentation
- RESTful API endpoints
- Interactive API explorer

## Tech Stack

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Security & JWT**
- **MySQL**
- **Swagger/OpenAPI**
- **Maven**

## Installation & Setup

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/YaminiVeerabomma/FinanceDataBackend.git
   cd FinanceDataBackend
   ```

2. **Configure Database**
   - Create a MySQL database
   - Update `application.properties` or `application.yml` with database credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/finance_db
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```

3. **Build the Application**
   ```bash
   mvn clean install
   ```

4. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```
   The application will start on `http://localhost:8080`

## API Documentation

Once the application is running, access the API documentation at:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

## Project Structure

```
FinanceDataBackend/
├── src/main/java/
│   └── com/finance/
│       ├── controller/        # REST Controllers
│       ├── service/           # Business Logic
│       ├── repository/        # Data Access Layer
│       ├── model/             # Entity Models
│       ├── dto/               # Data Transfer Objects
│       ├── security/          # JWT & Security Config
│       └── exception/         # Exception Handlers
├── src/main/resources/
│   └── application.properties # Configuration
└── pom.xml                    # Maven Dependencies
```

## Usage Examples

### Authentication

**Login**
```bash
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "role": "ADMIN"
}
```

### Financial Records (Admin Only)

**Create Record**
```bash
POST /api/records
Authorization: Bearer {token}
Content-Type: application/json

{
  "category": "EXPENSE",
  "type": "UTILITIES",
  "amount": 150.00,
  "date": "2026-04-05",
  "description": "Monthly electricity bill"
}
```

**Get Records with Filtering**
```bash
GET /api/records?category=EXPENSE&startDate=2026-01-01&endDate=2026-04-05&page=0&size=20
Authorization: Bearer {token}
```

### Dashboard (Viewer Access)

**View Dashboard Data**
```bash
GET /api/dashboard
Authorization: Bearer {token}
```

## Configuration

Key configuration options in `application.properties`:

```properties
# JWT Configuration
jwt.secret=your_secret_key_here
jwt.expiration=86400000

# Server Configuration
server.port=8080
server.servlet.context-path=/api

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

## Error Handling

The application provides standardized error responses:

```json
{
  "timestamp": "2026-04-05T10:30:00Z",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid JWT token",
  "path": "/api/records"
}
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contact

For questions or support, please contact [your-email@example.com](mailto:your-email@example.com)

---

