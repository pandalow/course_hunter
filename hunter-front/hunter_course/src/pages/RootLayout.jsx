import { Outlet } from "react-router-dom";
import MainNavigation from "../components/MainNavigation";
import Footer from "../components/Footer"; 

export default function RootLayout() {
    return (  
        <div className="flex flex-col min-h-screen">
            <MainNavigation />
            <div className="flex-grow">
                <Outlet />
            </div>
            <Footer />
        </div>)
}