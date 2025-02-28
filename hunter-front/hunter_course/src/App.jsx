import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import RootLayout from './pages/RootLayout';
import Home from './pages/Home';
import CourseDetail from './components/course_components/CourseDetail'; // 新增详情页面
import SignIn from './pages/signin/SignIn';
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
        path: 'course/:id', // 课程详情路由
        element: <CourseDetail />,
      },
      {
        path: 'signin',
        element: <SignIn />,
      },
    ],
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
