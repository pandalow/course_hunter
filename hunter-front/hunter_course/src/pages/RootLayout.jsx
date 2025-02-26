import { Outlet } from 'react-router-dom';
import MainNavigation from '../components/MainNavigation';
import Footer from '../components/Footer';

export default function RootLayout() {
    return (
        <div className="bg-black text-white min-h-screen flex flex-col">
            <MainNavigation />
            <main className="flex-grow container mx-auto p-6">
                <Outlet />
            </main>
            <Footer />
        </div>
    );
}
