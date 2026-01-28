import { Outlet } from 'react-router-dom';
import MainNavigation from '../components/MainNavigation';
import Footer from '../components/Footer';

export default function RootLayout() {
    return (
        <div className="bg-slate-950 text-slate-200 min-h-screen flex flex-col font-sans selection:bg-blue-500 selection:text-white">
            <MainNavigation />
            <main className="flex-grow container mx-auto px-4 sm:px-6 lg:px-8 py-8 w-full max-w-7xl self-center">
                <Outlet />
            </main>
            <Footer />
        </div>
    );
}
