import { Link,useNavigate } from "react-router-dom";
import Button from "./common_components/Button";

function MainNavigation() {
    const navigate = useNavigate();

    const handleNavigateSignIn = () => {
        navigate('/signin');
    }
    const linkStyle = "text-white text-xl font-bold hover:text-gray-300";

    return (
        <main>
            <nav className="flex flex-row items-center justify-between h-16 px-4 bg-gradient-to-r from-black to-gray-300" >
                <ul className="flex flex-row items-center space-x-8">
                    <li>
                        <img src={null} alt="logo" />
                    </li>
                    <li>
                        <Link className={linkStyle} to='/'>Home</Link>
                    </li>
                    <li>
                        <Link className={linkStyle} to='/'>About</Link>
                    </li>
                </ul>
                <Button text="Sign In" onClick={handleNavigateSignIn} />
            </nav>
        </main>
    );
}
export default MainNavigation;
