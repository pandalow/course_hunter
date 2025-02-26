import { Link, useNavigate } from "react-router-dom";
import Button from "./common_components/Button";

function MainNavigation() {
    const navigate = useNavigate();

    const handleNavigateSignIn = () => {
        navigate('/signin');
    };

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

                {/* Sign In Button */}
                <Button 
                    text="Sign In" 
                    onClick={handleNavigateSignIn} 
                    className="bg-blue-500 hover:bg-blue-600 text-white font-semibold rounded-lg px-4 py-2 transition duration-300"
                />
            </nav>
        </header>
    );
}

export default MainNavigation;
