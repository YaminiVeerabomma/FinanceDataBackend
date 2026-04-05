**Finance Data Backend**

Finance Data Backend is a Spring Boot application for managing financial records with role-based access control and JWT authentication. It allows Admins to manage records, Analysts to view and analyze data, and Viewers to only view dashboards.

#**Features**
- Role-based access:
  -**Viewer** : Can only view dashboard data
  -**Analyst**: Can view records and insights
  -**Admin**  : Full access — create, update, delete records, manage users
-JWT-based authentication
-CRUD operations for financial records
-Filtering records by category, type, and date range
-Pagination support for large datasets
-Global exception handling with friendly JSON responses
-Swagger / OpenAPI API documentation

**Tech Stack**
-Java 17+
-Spring Boot 3.x
-Spring Security & JWT
-MySQL
-Swagger / OpenAPI
-Maven
