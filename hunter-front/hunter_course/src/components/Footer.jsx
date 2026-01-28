function Footer() {
    return (
        <footer className="w-full bg-slate-950 border-t border-white/5 py-12 mt-auto">
            <div className="container mx-auto px-4 sm:px-6 lg:px-8 max-w-7xl flex flex-col md:flex-row justify-between items-start gap-10">
                
                {/* Brand */}
                <div className="space-y-4">
                    <h1 className="text-2xl font-bold bg-gradient-to-r from-blue-400 to-indigo-400 bg-clip-text text-transparent">
                        CourseHunter
                    </h1>
                    <p className="text-slate-400 text-sm max-w-xs leading-relaxed">
                        Empowering students worldwide to find the best educational resources and opportunities.
                    </p>
                    <p className="text-slate-600 text-xs mt-4">&copy; {new Date().getFullYear()} Course Hunter Team. All rights reserved.</p>
                </div>

                {/* Links */}
                <div className="grid grid-cols-2 gap-12 sm:gap-24">
                    <div className="flex flex-col space-y-3">
                        <h4 className="text-white font-semibold mb-1">Company</h4>
                        <a href="#" className="text-slate-400 hover:text-blue-400 text-sm transition-colors">About Us</a>
                        <a href="#" className="text-slate-400 hover:text-blue-400 text-sm transition-colors">Careers</a>
                        <a href="#" className="text-slate-400 hover:text-blue-400 text-sm transition-colors">Contact</a>
                    </div>
                    <div className="flex flex-col space-y-3">
                        <h4 className="text-white font-semibold mb-1">Legal</h4>
                        <a href="#" className="text-slate-400 hover:text-blue-400 text-sm transition-colors">Privacy Policy</a>
                        <a href="#" className="text-slate-400 hover:text-blue-400 text-sm transition-colors">Terms of Service</a>
                    </div>
                </div>
            </div>
        </footer>
    );
}

export default Footer;
