import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import RootLayout from './pages/RootLayout';
import Courses from './pages/Courses';
import Home from './pages/Home';
import About from './pages/About';
import CourseDetail from './components/course_components/CourseDetail'; // 新增详情页面
import './App.css';

const router = createBrowserRouter([
  {
    path: '/',
    element: <RootLayout />,
    children: [
      {
        index: true, // 让 Home 默认显示，不需要重复 path: '/'
        element: <Home />,
      },
      {
        path: 'about', // 关于页面路由
        element: <About />,
      },
      {
        path: 'courses', // 课程列表路由
        element: <Courses />,
      },
      {
        path: 'course/:id', // 课程详情路由
        element: <CourseDetail />,
      },
    ],
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
