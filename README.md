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

### ⚡ Performance & Reliability
- **Pagination**: Efficiently handle large datasets with customizable page size
- **Rate Limiting**: Protect API from abuse with request throttling
- Request validation and error recovery
- Optimized database queries

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
│       ├── exception/         # Exception Handlers
│       └── config/            # Rate Limiting & Pagination Config
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

## Advanced Features

### 📄 Pagination

Pagination enables efficient handling of large datasets by breaking them into manageable chunks.

#### Pagination Parameters

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `page` | Integer | 0 | Zero-indexed page number |
| `size` | Integer | 20 | Number of records per page (max: 100) |
| `sort` | String | `id,desc` | Sort criteria (format: `field,direction`) |

#### Pagination Examples

**Basic Pagination**
```bash
GET /api/records?page=0&size=10
Authorization: Bearer {token}
```

**With Sorting**
```bash
GET /api/records?page=0&size=15&sort=date,desc&sort=amount,asc
Authorization: Bearer {token}
```

**With Filtering and Pagination**
```bash
GET /api/records?category=EXPENSE&type=UTILITIES&startDate=2026-01-01&endDate=2026-04-05&page=0&size=20&sort=date,desc
Authorization: Bearer {token}
```

#### Pagination Response

```json
{
  "content": [
    {
      "id": 1,
      "category": "EXPENSE",
      "type": "UTILITIES",
      "amount": 150.00,
      "date": "2026-04-05",
      "description": "Monthly electricity bill"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20,
    "totalElements": 150,
    "totalPages": 8
  },
  "hasNext": true,
  "hasPrevious": false
}
```

#### Pagination Headers

The following headers are included in pagination responses:

| Header | Description |
|--------|-------------|
| `X-Total-Count` | Total number of records |
| `X-Total-Pages` | Total number of pages |
| `X-Page-Number` | Current page number |
| `X-Page-Size` | Current page size |

---

### 🛡️ Rate Limiting

Rate limiting protects the API from abuse by restricting the number of requests per time window.

#### Rate Limit Configuration

| Role | Requests Per Minute | Requests Per Hour | Requests Per Day |
|------|--------|--------|--------|
| **Viewer** | 30 | 500 | 5,000 |
| **Analyst** | 60 | 1,000 | 10,000 |
| **Admin** | 120 | 2,000 | 20,000 |

#### Rate Limit Headers

Every API response includes the following headers:

| Header | Description |
|--------|-------------|
| `X-RateLimit-Limit` | Maximum requests allowed in the window |
| `X-RateLimit-Remaining` | Remaining requests in current window |
| `X-RateLimit-Reset` | Unix timestamp when the limit resets |
| `Retry-After` | Seconds to wait before retrying (on 429 response) |

#### Rate Limit Response Example

**Request Headers**
```
Authorization: Bearer {token}
```

**Response Headers (On Success - 200)**
```
X-RateLimit-Limit: 30
X-RateLimit-Remaining: 28
X-RateLimit-Reset: 1712420460
```

**Rate Limit Exceeded Response (429)**
```json
{
  "timestamp": "2026-04-06T10:30:00Z",
  "status": 429,
  "error": "Too Many Requests",
  "message": "Rate limit exceeded. Maximum 30 requests per minute allowed.",
  "path": "/api/records",
  "retryAfter": 45
}
```

#### Rate Limiting Examples

**Check Rate Limit Status**
```bash
curl -i -H "Authorization: Bearer {token}" \
  https://localhost:8080/api/records?page=0&size=10
```

**Handle Rate Limit Gracefully**
```bash
# If you receive a 429 response with X-RateLimit-Reset header
# Wait until the timestamp or use Retry-After header to determine when to retry

GET /api/records
Authorization: Bearer {token}

# Response: 429 Too Many Requests
# Retry-After: 45 (wait 45 seconds)
# X-RateLimit-Reset: 1712420460 (Unix timestamp)
```

---

## Configuration

### Key Configuration Options

Update these properties in `application.properties` or `application.yml`:

```properties
# JWT Configuration
jwt.secret=your_secret_key_here
jwt.expiration=86400000

# Server Configuration
server.port=8080
server.servlet.context-path=/api

# Pagination Configuration
pagination.default.page=0
pagination.default.size=20
pagination.max.size=100

# Rate Limiting Configuration
ratelimit.enabled=true
ratelimit.viewer.requests-per-minute=30
ratelimit.viewer.requests-per-hour=500
ratelimit.viewer.requests-per-day=5000
ratelimit.analyst.requests-per-minute=60
ratelimit.analyst.requests-per-hour=1000
ratelimit.analyst.requests-per-day=10000
ratelimit.admin.requests-per-minute=120
ratelimit.admin.requests-per-hour=2000
ratelimit.admin.requests-per-day=20000

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

### YAML Configuration Alternative

```yaml
pagination:
  default:
    page: 0
    size: 20
  max:
    size: 100

ratelimit:
  enabled: true
  viewer:
    requests-per-minute: 30
    requests-per-hour: 500
    requests-per-day: 5000
  analyst:
    requests-per-minute: 60
    requests-per-hour: 1000
    requests-per-day: 10000
  admin:
    requests-per-minute: 120
    requests-per-hour: 2000
    requests-per-day: 20000
```

---

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

### Common Error Codes

| Status | Error | Description |
|--------|-------|-------------|
| 400 | Bad Request | Invalid pagination parameters (e.g., negative page number) |
| 401 | Unauthorized | Missing or invalid authentication token |
| 403 | Forbidden | Insufficient permissions for the requested resource |
| 429 | Too Many Requests | Rate limit exceeded |
| 500 | Internal Server Error | Unexpected server error |

---

## Best Practices

### Pagination Best Practices
- ✅ Use reasonable page sizes (10-50 records)
- ✅ Cache results when possible to reduce database load
- ✅ Include sorting for consistent pagination
- ❌ Avoid requesting all data at once with large page sizes
- ❌ Don't ignore `totalPages` header for UI pagination controls

### Rate Limiting Best Practices
- ✅ Implement exponential backoff when receiving 429 responses
- ✅ Monitor `X-RateLimit-Remaining` to plan request timing
- ✅ Use `Retry-After` header to determine retry timing
- ✅ Cache API responses to reduce redundant requests
- ❌ Don't ignore rate limit headers
- ❌ Don't implement aggressive retry loops without backoff

---

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contact

For questions or support, please contact [yaminiveerabomma@gmail.com](mailto:yaminiveerabomma@gmail.com)
