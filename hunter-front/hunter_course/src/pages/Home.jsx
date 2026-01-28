
import { Link } from 'react-router-dom';

export default function Home() {
    return (
        <div className="space-y-20 pb-20">
            {/* Hero Section */}
            <section className="relative text-center py-20 px-4">
                <div className="absolute inset-0 -z-10 bg-[radial-gradient(ellipse_at_center,_var(--tw-gradient-stops))] from-blue-900/20 via-slate-950 to-slate-950"></div>
                <h1 className="text-5xl md:text-7xl font-bold tracking-tight text-white mb-6">
                    Master Your Future with <br />
                    <span className="bg-gradient-to-r from-blue-400 to-indigo-500 bg-clip-text text-transparent">CourseHunter</span>
                </h1>
                <p className="text-xl text-slate-400 max-w-2xl mx-auto mb-10">
                    Discover top-rated courses from leading universities and institutions. Search by semester, code, or country.
                </p>
                <Link to="/courses" className="inline-flex items-center justify-center h-12 px-8 font-medium tracking-wide text-white transition duration-200 bg-blue-600 rounded-full hover:bg-blue-500 focus:shadow-outline focus:outline-none shadow-lg shadow-blue-500/30">
                    Get Started
                </Link>
            </section>

        </div>
    );
}
