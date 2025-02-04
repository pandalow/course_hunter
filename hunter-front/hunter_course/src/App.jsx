import {createBrowserRouter, RouterProvider} from 'react-router-dom'
import RootLayout from './pages/RootLayout'
import Home from './pages/Home.jsx'
import './App.css'

const router = createBrowserRouter([
  {
    path: '/',
    element: <RootLayout />,
    children: [
      {
        path: '/',
        element: <Home />,
      },
    ],
  },
])

function App() {
  return <RouterProvider router={router} />
}

export default App
