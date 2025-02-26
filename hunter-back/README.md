# Course Hunter - Back End

## 📝 Project Overview
**Course Hunter** is a **Spring Boot-based** backend project designed to help students quickly browse courses available in their institution and obtain useful information such as course descriptions, ratings, and instructors.  
The project provides **RESTful APIs**, supporting **course search, instructor details**, and uses **Redis** for caching optimization to enhance system performance.

---

## 🚀 Technology Stack & Dependencies
| **Dependency**                 | **Description**                                | **Version** | **Official Website** |
|---------------------------------|----------------------------------------------|------------|---------------------|
| **Java**                        | Core programming language                    | 21.0       | [Oracle Java](https://www.oracle.com/java/technologies/javase) |
| **Spring Boot**                 | Web application framework                    | 3.2.5      | [Spring Boot](https://spring.io/projects/spring-boot) |
| **Lombok**                      | Java code enhancement library                | 1.18.32    | [Lombok](https://github.com/rzwitserloot/lombok) |
| **Swagger-UI**                  | API documentation generation tool            | 2.5.0      | [Swagger UI](https://github.com/swagger-api/swagger-ui) |
| **Hibernate**                   | Object-Relational Mapping (ORM)              | Latest     | [Hibernate](http://hibernate.org) |
| **MySQL**                       | Relational database                          | Latest     | [MySQL](https://www.mysql.com/) |
| **Redis**                       | In-memory database for caching               | 7.2.5      | [Redis](https://redis.io/) |
| **Lettuce**                     | Scalable Redis client                        | 6.3.2      | [Lettuce](https://lettuce.io/) |
| **Spring-redis-starter**        | Spring framework Redis client                | 3.3.0      | [Spring Redis](https://spring.io/projects/spring-data-redis) |
| **Gson**                        | Java object to JSON conversion library       | 32.1.2     | [Gson](https://github.com/google/gson) |

---

## 📂 Project Structure
```
course-hunter-backend
│── course-hunter-server      # Server module (Handles VIEW & CONTROLLER)
│   ├── anno                  # Annotation package
│   ├── aspect                # Aspect-Oriented Programming (AOP)
│   ├── config                # Configuration files
│   ├── controller            # REST API controllers
│   ├── handler               # Global exception handling
│   ├── interceptor           # User validation filters
│   ├── dao                   # Data Access Object (DAO) for database interactions
│   ├── service               # Business logic layer (connects Controller and DAO)
│── course-hunter-pojo        # Entity module (Database mapping & Frontend interactions)
│   ├── entity                # Database entities
│   ├── DTO                   # Data Transfer Objects (for HTTP requests)
│   ├── VO                    # View Objects (for frontend responses)
│── course-hunter-common      # Common utilities
│   ├── constant              # Global constants
│   ├── context               # Thread information processing
│   ├── enumeration           # Enum definitions
│   ├── exception             # Custom exception handling
│   ├── properties            # Configuration properties
│   ├── result                # Response result handling (pagination & success/failure messages)
│   ├── utils                 # Utility classes and custom helper functions
```

---

## 🏗️ How to Run Locally
### **1️⃣  Clone the Repository**
```sh
git clone https://github.com/your-repo/course-hunter-backend.git
cd course-hunter-backend
```

### **2️⃣  Configure the Database**
- Create a **MySQL** database:
```sql
CREATE DATABASE course_hunter DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```
- Modify `application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/course_hunter?useSSL=false&serverTimezone=UTC
    username: your_mysql_username
    password: your_mysql_password
  redis:
    host: localhost
    port: 6379
```

### **3️⃣  Start Redis**
```sh
redis-server
```

### **4️⃣  Run the Project**
```sh
mvn spring-boot:run
```
If you are using **IntelliJ IDEA**, you can directly run `CourseHunterApplication.java`.

---

## 📖 API Documentation (Swagger-UI)
After starting the project, visit:
```
http://localhost:8080/swagger-ui/
```
You can test all API endpoints here.

---

## 🛠️ Contribution Guidelines
We welcome contributions to **Course Hunter**! You can:
1. **Fork this repository**
2. **Create a feature branch** (`git checkout -b feature-xxx`)
3. **Commit your changes** (`git commit -m 'Added xxx feature'`)
4. **Push to your branch** (`git push origin feature-xxx`)
5. **Submit a Pull Request (PR)**

---

## 🏆 Contributors
- **[@pandalow](https://github.com/pandalow)**
- **[@fernweh-3](https://github.com/fernweh-3)**

---

## 📜 License
This project is open-sourced under the **MIT License**, meaning you are free to use and modify it.  
Check the full license terms here: [LICENSE](./LICENSE)

---

🎯 **Now you are ready to run the `Course Hunter` backend! 🚀**
