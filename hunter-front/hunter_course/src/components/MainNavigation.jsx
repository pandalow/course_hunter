import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import Modal from "react-modal";

export default function MainNavigation() {
    const [user, setUser] = useState(null);
    const [modalIsOpen, setModalIsOpen] = useState(false);

    useEffect(() => {
        // 组件加载时，尝试获取当前登录用户信息
        fetch("http://localhost:9999/auth/me")
            .then(response => response.ok ? response.json() : null)
            .then(data => setUser(data))
            .catch(() => setUser(null));
    }, []);

    const handleLogin = () => {
        window.location.href = "http://localhost:9999/oauth2/authorization/google"; // Google OAuth 登录
    };

    const handleLogout = () => {
        fetch("http://localhost:9999/auth/logout", { method: "POST" })
            .then(() => {
                setUser(null);
                window.location.reload();
            });
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

                {/* User Section */}
                {user ? (
                    <div className="relative group">
                        {/* 头像 */}
                        <img 
                            src={user.avatar} 
                            alt="User Avatar" 
                            className="w-10 h-10 rounded-full cursor-pointer"
                        />
                        {/* 用户下拉菜单 */}
                        <div className="absolute right-0 mt-2 w-48 bg-white shadow-lg rounded-lg py-2 hidden group-hover:block">
                            <p className="px-4 py-2 text-gray-700">{user.name}</p>
                            <p className="px-4 py-2 text-sm text-gray-500">{user.email}</p>
                            <hr />
                            <button 
                                onClick={handleLogout} 
                                className="w-full text-left px-4 py-2 text-red-500 hover:bg-gray-100"
                            >
                                Logout
                            </button>
                        </div>
                    </div>
                ) : (
                    <button 
                        onClick={() => setModalIsOpen(true)} 
                        className="bg-blue-500 hover:bg-blue-600 text-white font-semibold rounded-lg px-4 py-2 transition duration-300"
                    >
                        Sign In
                    </button>
                )}
            </nav>

            {/* 登录弹窗 */}
            <Modal
                isOpen={modalIsOpen}
                onRequestClose={() => setModalIsOpen(false)}
                className="bg-white shadow-lg rounded-lg p-6 w-96 mx-auto mt-20"
                overlayClassName="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center"
            >
                <h2 className="text-xl font-semibold mb-4 text-center">Sign in with Google</h2>
                <button 
                    onClick={handleLogin} 
                    className="bg-red-500 hover:bg-red-600 text-white w-full py-2 rounded-lg flex items-center justify-center space-x-2"
                >
                    <img 
                        src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Google_%22G%22_Logo.svg/512px-Google_%22G%22_Logo.svg.png" 
                        alt="Google Logo" 
                        className="w-5 h-5"
                    />
                    <span>Continue with Google</span>
                </button>
            </Modal>
        </header>
    );
}
