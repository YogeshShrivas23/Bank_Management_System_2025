# 💼 Bank Management System

A robust and secure backend system for managing essential banking operations, developed using **Spring Boot**. This project includes features such as **user authentication (JWT)**, **role-based access control**, and **transaction management**, making it ideal for understanding real-world enterprise application structures.

> 🔗 **Live Repository:** [Bank Management System on GitHub](https://github.com/YogeshShrivas23/Bank_Management_System_2025)  
> 👨‍💻 **My GitHub Profile:** [Yogesh Shrivas](https://github.com/YogeshShrivas23)

---

## 🚀 Tech Stack

- **Java 17**
- **Spring Boot 3.2.3**
- **Spring Security**
- **Spring Data JPA**
- **MySQL**
- **Gradle**
- **JWT Authentication**
- **Swagger / OpenAPI 3.0**

---

## 🔧 Prerequisites

Ensure you have the following installed:

- Java 17+
- MySQL 8.0+
- Gradle 7.0+
- IDE (e.g., IntelliJ IDEA)

---

## ⚙️ Installation & Setup

1. **Clone the Repository**
   ```bash
   git clone https://github.com/YogeshShrivas23/Bank_Management_System_2025.git
   cd Bank_Management_System_2025
   ```

2. **Configure Database**
   - Create a database: `bankdb`
   - Update `application.properties`:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/bankdb
     spring.datasource.username=your_mysql_username
     spring.datasource.password=your_mysql_password
     ```

3. **Build & Run**
   ```bash
   ./gradlew build
   ./gradlew bootRun
   ```
   Access the app at: `http://localhost:8080`

---

## 🔐 Authentication

- **Default Admin User**
  - Email: `admin@bank.com`
  - Password: `admin123`

- **Register New User**
  ```json
  POST /api/auth/register
  {
    "name": "Test User",
    "email": "test@example.com",
    "contact": "1234567890",
    "dob": "1990-01-01",
    "password": "password123"
  }
  ```

- **Login**
  ```json
  POST /api/auth/login
  {
    "email": "test@example.com",
    "password": "password123"
  }
  ```

---

## 📘 API Overview

All secured routes require a valid **JWT Token** in the `Authorization` header.

- **Customer Operations**
  - View all / individual customers
  - Update / delete / freeze accounts

- **Account Operations**
  - Open, view, close accounts
  - Deposit, withdraw, and transfer money

- **Transaction History**
  - Fetch transaction logs by account and filter by type/date

🔎 Full API Docs available at:  
`http://localhost:8080/swagger-ui/index.html`

---

## 🛡️ Security Features

- Encrypted passwords using **BCrypt**
- **JWT-based login system**
- Role-based access: **ADMIN** and **USER**
- Input validation and request sanitization

---

## 🧱 Project Structure

```
com.bankmanagement
├── config/          # Spring configurations
├── controller/      # API controllers
├── dto/             # Request & response DTOs
├── entity/          # JPA entity classes
├── exception/       # Custom error handlers
├── repository/      # Database interaction layers
├── security/        # Security config and filters
└── service/         # Business logic
```

---

## 🧪 Running Tests

```bash
./gradlew test
```

---

## 🤝 Contributing

1. Fork this repository
2. Create your feature branch: `git checkout -b feature/AmazingFeature`
3. Commit your changes: `git commit -m "Add AmazingFeature"`
4. Push to the branch: `git push origin feature/AmazingFeature`
5. Open a Pull Request 🚀

---

## 📩 Contact

If you have questions, suggestions, or want to collaborate:

- ✉️ **Email**: yogeshshrivas7566@gmail.com  
- 🔗 **GitHub**: [YogeshShrivas23](https://github.com/YogeshShrivas23)  
- 📁 **Project**: [Bank Management System Repo](https://github.com/YogeshShrivas23/Bank_Management_System_2025)

---

## 📝 License

This project is licensed under the MIT License.
