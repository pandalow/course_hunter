# 🎓 Course Hunter - Full Stack Application

## 📝 Project Overview
**Course Hunter** is a full-stack web application that allows students to **browse university courses, view ratings, and get detailed course information**.  

- 📡 **Backend**: Built with **Spring Boot**, providing RESTful APIs for course management, user authentication, and ratings.  
- 🖥️ **Frontend**: Developed with **React (Vite)** and **Tailwind CSS**, offering a modern and responsive user interface.  
- ⚡ **Database & Caching**: **MySQL** for relational data, **Redis** for fast caching.  
- 🔐 **Security**: Uses **Spring Security** and JWT for authentication.  

---
### Demo 
<img width="1440" alt="image" src="https://github.com/user-attachments/assets/2211856e-fb07-40fa-8da1-14dfed35c1de" />
<img width="1440" alt="image" src="https://github.com/user-attachments/assets/c0680ac2-765a-4e58-8fee-fc8311204c6c" />

## 🚀 Tech Stack

| **Technology**       | **Description**                               | **Version**  |
|----------------------|-----------------------------------------------|-------------|
| **Java**            | Backend programming language                  | 21.0        |
| **Spring Boot**     | Web application framework                     | 3.2.5       |
| **Spring Security** | Authentication and authorization              | Latest      |
| **Hibernate**       | ORM for database interaction                  | Latest      |
| **JustAuth**       | Auth sdk               | Latest      |
| **MySQL**          | Relational database                            | Latest      |
| **Redis**          | In-memory database for caching                 | 7.2.5       |
| **Lettuce**        | Scalable Redis client                          | 6.3.2       |
| **React**          | Frontend framework                             | Latest      |
| **Vite**           | Fast build tool for React                      | Latest      |
| **Tailwind CSS**   | Utility-first CSS framework                    | Latest      |
| **React Router**   | Frontend routing and navigation                | Latest      |

---

## 📂 Project Structure
```
course-hunter
│── backend                  # Spring Boot backend
│   ├── course-hunter-server  # Server module (Controllers, Services, DAO)
│   ├── course-hunter-pojo    # Database entity & DTO module
│   ├── course-hunter-common  # Common utilities
│   ├── src/main/resources    # Config files (application.yml)
│── frontend                  # React frontend
│   ├── src
│   │   ├── components        # UI components (Navbar, Footer, Cards)
│   │   ├── pages             # Page views (Home, SignIn, CourseDetail)
│   │   ├── service           # API service handlers
│   │   ├── assets            # Static files (logos, images)
│   ├── public                # Public assets (favicon, index.html)
│   ├── package.json          # Frontend dependencies & scripts
│   ├── tailwind.config.js    # Tailwind CSS configuration
│   ├── vite.config.js        # Vite build configuration
│── README.md                 # Project documentation
```

---

# 🏗️ Installation & Setup

## 🔹 **1️⃣ Backend Setup**
### **Clone the Repository**
```sh
git clone https://github.com/your-repo/course-hunter.git
cd course-hunter/backend
```

### **Set Up the Database**
- Create a **MySQL** database:
```sql
CREATE DATABASE course_hunter DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```
- Configure `application.yml` in `src/main/resources`:
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

### **Start Redis**
```sh
redis-server
```

### **Run the Backend**
```sh
mvn spring-boot:run
```
- Backend will be available at:  
  **http://localhost:8080**

---

## 🔹 **2️⃣ Frontend Setup**
### **Navigate to Frontend**
```sh
cd ../frontend
```

### **Install Dependencies**
```sh
npm install
```

### **Run the Frontend**
```sh
npm run dev
```
- Frontend will be available at:  
  **http://localhost:5173** (Default Vite port)

---

## 📖 API Documentation (Swagger-UI)
After starting the backend, you can access **API documentation** at:
```
http://localhost:8080/swagger-ui/
```

### 🔹 **User Authentication**
- **Sign In** → `POST /user/login`
- **Register** → `POST /user/register`
- **User Info** → `GET /user/userInfo`

### 🔹 **Course Management**
- **Get All Courses** → `GET /api/courses`
- **Get Course Details** → `GET /api/courses/{id}`

---

## 🔗 Frontend Routing & Navigation
This project uses **React Router** for client-side navigation.

| **Path**        | **Component**       | **Description**           |
|----------------|--------------------|--------------------------|
| `/`            | `Home`              | Course browsing page      |
| `/signin`      | `SignIn`            | User authentication       |
| `/course/:id`  | `CourseDetail`      | Course details page       |

---

## 🎨 Styling
- **Tailwind CSS** is used for UI design.
- All global styles are configured in `tailwind.config.js`.

---

## 🛠️ Deployment
### **🔹 Backend Deployment**
- Build the backend:
```sh
mvn clean package
```
- Deploy the generated `.jar` file on **Tomcat, AWS, or Docker**.

### **🔹 Frontend Deployment**
- Build for production:
```sh
npm run build
```
- Serve the `dist/` folder with **Nginx, Vercel, or Netlify**.

---

## 🏆 Contributing
We welcome contributions! 🚀  
1. **Fork this repository**  
2. **Create a feature branch** (`git checkout -b feature-xxx`)  
3. **Commit changes** (`git commit -m 'Added xxx feature'`)  
4. **Push to branch** (`git push origin feature-xxx`)  
5. **Submit a pull request** 🎯  

---

## 📜 License
This project is licensed under the **MIT License**.

---

🎯 **Now you are ready to start developing Course Hunter!** 🚀
