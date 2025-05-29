# Bank Management System

A robust and secure backend Bank Management System built with Spring Boot, featuring comprehensive banking operations, JWT authentication, and role-based access control.

## 🚀 Technologies Used

- Java 17
- Spring Boot 3.2.3
- Spring Security
- Spring Data JPA
- MySQL
- Gradle
- JWT Authentication
- Swagger/OpenAPI 3.0

## 📋 Prerequisites

- JDK 17 or higher
- MySQL 8.0 or higher
- Gradle 7.0 or higher
- IDE (IntelliJ IDEA recommended)

## 🛠️ Setup and Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd bank-management-system
   ```

2. **Configure MySQL**
   - Create a new database named `bankdb`
   - Update `src/main/resources/application.properties` with your MySQL credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/bankdb
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run the application**
   ```bash
   ./gradlew bootRun
   ```
   The application will start on `http://localhost:8080`

## 🔐 Authentication

### Default Admin User
- Email: admin@bank.com
- Password: admin123

### Register New User
```http
POST /api/auth/register
Content-Type: application/json

{
    "name": "Test User",
    "email": "test@example.com",
    "contact": "1234567890",
    "dob": "1990-01-01",
    "password": "password123"
}
```

### Login
```http
POST /api/auth/login
Content-Type: application/json

{
    "email": "test@example.com",
    "password": "password123"
}
```

## 📚 API Documentation

Access the Swagger UI at: `http://localhost:8080/swagger-ui/index.html`

### Customer Management

#### Get All Customers
```http
GET /api/customers
Authorization: Bearer <JWT_TOKEN>
```

#### Get Customer by ID
```http
GET /api/customers/{id}
Authorization: Bearer <JWT_TOKEN>
```

#### Update Customer
```http
PUT /api/customers/{id}
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
    "name": "Updated Name",
    "email": "updated@example.com",
    "contact": "9876543210",
    "dob": "1990-01-01"
}
```

#### Delete Customer
```http
DELETE /api/customers/{id}
Authorization: Bearer <JWT_TOKEN>
```

#### Freeze/Unfreeze Customer
```http
POST /api/customers/{id}/freeze
POST /api/customers/{id}/unfreeze
Authorization: Bearer <JWT_TOKEN>
```

### Account Management

#### Create Account
```http
POST /api/accounts
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
    "customerId": 1,
    "type": "SAVINGS",
    "initialDeposit": 1000.00
}
```

#### Get Account by ID
```http
GET /api/accounts/{id}
Authorization: Bearer <JWT_TOKEN>
```

#### Get Accounts by Customer ID
```http
GET /api/accounts/customer/{customerId}
Authorization: Bearer <JWT_TOKEN>
```

#### Close Account
```http
DELETE /api/accounts/{id}
Authorization: Bearer <JWT_TOKEN>
```

#### Deposit
```http
POST /api/accounts/deposit
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
    "accountNumber": "1234567890",
    "amount": 500.00
}
```

#### Withdraw
```http
POST /api/accounts/withdraw
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
    "accountNumber": "1234567890",
    "amount": 200.00
}
```

#### Transfer
```http
POST /api/accounts/transfer
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
    "fromAccountNumber": "1234567890",
    "toAccountNumber": "0987654321",
    "amount": 200.00
}
```

### Transaction Management

#### Get Transactions by Account ID
```http
GET /api/transactions/account/{accountId}
Authorization: Bearer <JWT_TOKEN>
Query Parameters:
- type: DEPOSIT/WITHDRAWAL/TRANSFER
- startDate: 2023-01-01T00:00:00
- endDate: 2023-12-31T23:59:59
- page: 0
- size: 10
```

#### Get All Transactions (Admin)
```http
GET /api/transactions
Authorization: Bearer <JWT_TOKEN>
Query Parameters:
- page: 0
- size: 10
```

## 🔒 Security Features

- JWT-based authentication
- Role-based access control (USER, ADMIN)
- Password encryption using BCrypt
- Secure session management
- Input validation and sanitization

## 📦 Project Structure

```
src/main/java/com/bankmanagement/
├── config/          # Configuration classes
├── controller/      # REST controllers
├── dto/            # Data Transfer Objects
├── entity/         # JPA entities
├── exception/      # Custom exceptions
├── repository/     # JPA repositories
├── security/       # Security configurations
└── service/        # Business logic
```

## 🧪 Testing

Run tests using Gradle:
```bash
./gradlew test
```

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📧 Contact

For any queries or support, please contact:
- Email: your.email@example.com
- Project Link: [https://github.com/yourusername/bank-management-system](https://github.com/yourusername/bank-management-system) 
