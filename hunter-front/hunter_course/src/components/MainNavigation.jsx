import { Link, useNavigate } from "react-router-dom";
import { GoogleLogin } from '@react-oauth/google';
import { googleLogin, authMe } from '../api/google_auth'
import User from "./common_components/User";
import { useEffect, useState } from "react";

export default function MainNavigation() {
    const navigate = useNavigate();
    const [userinfo, setUser] = useState(null);

    useEffect(() => {
        const storedUser = localStorage.getItem('user_info');
        if (storedUser) {
            setUser(JSON.parse(storedUser));
        }
        if (localStorage.getItem('app_token')) {

            authMe().then(data => {
                const userData = {
                    id: data.id,
                    name: data.name,
                    email: data.email,
                    avatar: data.avatar
                };
                setUser(userData);
                localStorage.setItem('user_info', JSON.stringify(userData));
            }).catch(error => {
                console.log("Session expired", error);
                localStorage.removeItem('app_token');
                localStorage.removeItem('user_info');
                setUser(null);
            });
        }
    }, []);

    const handleGoogleLogin = async (credentialResponse) => {
        try {
            const response = await googleLogin(credentialResponse);
            const userData = {
                id: response.id,
                name: response.name,
                email: response.email,
                avatar: response.avatar
            };
            setUser(userData);
            localStorage.setItem('user_info', JSON.stringify(userData));
        } catch (error) {
            console.log("Error");
        }
    }

    const handleLogOut = () => {
        localStorage.removeItem('app_token');
        localStorage.removeItem('user_info');
        setUser(null);
        navigate('/');
    }

    return (
        <header className="sticky top-0 z-50 w-full backdrop-blur-lg bg-slate-950/80 border-b border-white/10 supports-[backdrop-filter]:bg-slate-950/60">
            <nav className="container mx-auto flex items-center justify-between h-16 px-4 sm:px-6 lg:px-8 max-w-7xl">
                {/* Logo */}
                <Link to="/" className="flex items-center gap-2 group">
                    <span className="text-2xl font-bold bg-gradient-to-r from-blue-400 to-indigo-400 bg-clip-text text-transparent group-hover:from-blue-300 group-hover:to-indigo-300 transition-all duration-300">
                        CourseHunter
                    </span>
                </Link>

                <div className="flex items-center gap-8">
                    <ul className="flex items-center gap-6">
                        <li>
                            <Link to="/" className="text-sm font-medium text-slate-300 hover:text-white transition-colors duration-200">
                                Home
                            </Link>
                        </li>
                        <li>
                            <Link to="/courses" className="text-sm font-medium text-slate-300 hover:text-white transition-colors duration-200">
                                Courses
                            </Link>
                        </li>
                        <li>
                            <Link to="/about" className="text-sm font-medium text-slate-300 hover:text-white transition-colors duration-200">
                                About
                            </Link>
                        </li>
                    </ul>
                    
                    {/* User / Login */}
                    <div className="flex items-center pl-6 border-l border-white/10">
                        {userinfo ? (
                            <User user={userinfo}
                            logout={handleLogOut} />
                        ) : (
                            <GoogleLogin
                                onSuccess={handleGoogleLogin}
                                onError={() => console.log('Login Failed')}
                                type="icon"
                                shape="circle"
                                theme="filled_black"
                                size="medium"
                            />
                        )}
                    </div>
                </div>
            </nav>
        </header>
    );
}