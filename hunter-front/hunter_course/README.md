# Course Hunter - Frontend 🎓

## 📝 Project Overview
The **Course Hunter** frontend is a **React-based** web application designed to help students quickly browse university courses, view ratings, and get detailed course information. The project follows a **modern UI design**, integrates with a **Spring Boot backend**, and uses **React Router** for navigation.

---

## 🚀 Tech Stack
| **Technology**       | **Description**                       | **Version** |
|----------------------|---------------------------------------|------------|
| **React**           | Frontend framework                    | Latest     |
| **Vite**            | Fast build tool for React             | Latest     |
| **Tailwind CSS**    | Utility-first CSS framework           | Latest     |
| **React Router**    | Navigation and routing                | Latest     |
| **Fetch API**       | Used for backend communication        | Built-in   |

---

## 📂 Project Structure
```
course-hunter-frontend
│── src
│   ├── components         # Reusable UI components (Navbar, Footer, etc.)
│   ├── pages              # Main application pages (Home, SignIn, CourseDetail)
│   ├── service            # API service handlers
│   ├── assets             # Static files (logos, images)
│   ├── App.jsx            # Root component
│   ├── main.jsx           # Application entry point
│── public                 # Public assets (favicon, index.html)
│── package.json           # Project dependencies & scripts
│── tailwind.config.js     # Tailwind CSS configuration
│── vite.config.js         # Vite build configuration
│── README.md              # Project documentation
```

---

## 🏗️ Installation & Setup

### **1️⃣ Clone the Repository**
```sh
git clone https://github.com/your-repo/course-hunter-frontend.git
cd course-hunter-frontend
```

### **2️⃣ Install Dependencies**
```sh
npm install
```

### **3️⃣ Start the Development Server**
```sh
npm run dev
```
- The project will be available at:  
  **http://localhost:5173/** (Default Vite port)

---

## 📖 API Integration
The frontend interacts with the **Course Hunter Backend** through RESTful APIs.

### **🔹 API Base URL**
Ensure the correct API URL is set in `src/service/api.js`:
```js
const API_BASE_URL = "http://localhost:8080";
export default API_BASE_URL;
```

### **🔹 User Authentication**
- **Sign In API** → `POST /user/login`
- **Register API** → `POST /user/register`
- **User Info API** → `GET /user/userInfo`

### **🔹 Course Management**
- **Get All Courses** → `GET /api/courses`
- **Get Course Details** → `GET /api/courses/{id}`

---

## 🔗 Routing & Navigation
This project uses **React Router** for client-side navigation.

### **Configured Routes (`App.jsx`)**
| **Path**        | **Component**       | **Description**           |
|----------------|--------------------|--------------------------|
| `/`            | `Home`              | Course browsing page      |
| `/signin`      | `SignIn`            | User authentication       |
| `/course/:id`  | `CourseDetail`      | Course details page       |

---

## 🎨 Styling
- **Tailwind CSS** is used for UI design.
- All global styles are in `tailwind.config.js`.

---

## 🛠️ Deployment
### **Build for Production**
```sh
npm run build
```
This will generate an optimized `dist/` folder for deployment.

### **Run Production Build Locally**
```sh
npm run preview
```

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

🎯 **Now you are ready to start developing Course Hunter Frontend!** 🚀
