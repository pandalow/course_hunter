import { Link, useNavigate } from "react-router-dom";
import { GoogleLogin } from '@react-oauth/google';
import googleLogin from '../api/google_auth'

export default function MainNavigation() {
    const navigate = useNavigate();

    const handleGoogleLogin = async (credentialResponse) => {
        try {
            const response = await googleLogin(credentialResponse);

            
        }catch(error){
            console.log("Error");
        }
    }
    return (
        <header className="bg-black shadow-md border-b border-gray-700">
            <nav className="container mx-auto flex flex-row items-center justify-between h-16 px-6">
                {/* Logo */}
                <div className="text-white text-2xl font-extrabold tracking-wide">
                    Course<span className="text-blue-400">Hunter</span>
                </div>

                {/* Navigation Links */}
                <ul className="flex flex-row items-center space-x-6">
                    <li>
                        <Link className="text-white text-lg font-semibold hover:opacity-80" to='/'>Home</Link>
                    </li>
                    <li>
                        <Link className="text-white text-lg font-semibold hover:opacity-80" to='/about'>About</Link>
                    </li>
                </ul>

                {/* Google Login */}
                <GoogleLogin
                    onSuccess={handleGoogleLogin}
                    onError={() => {
                        console.log('Login Failed');
                    }}
                />
            </nav>
        </header>
    );
}