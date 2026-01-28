import { useState, useRef, useEffect } from "react";

export default function User({ user, logout }) {
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const dropdownRef = useRef(null);

    // 点击页面其他位置自动关闭菜单
    useEffect(() => {
        const handleClickOutside = (event) => {
            if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
                setIsMenuOpen(false);
            }
        };
        document.addEventListener("mousedown", handleClickOutside);
        return () => document.removeEventListener("mousedown", handleClickOutside);
    }, []);

    return (
        <div className="relative" ref={dropdownRef}>
            {/* 点击触发区域 */}
            <div 
                className="flex items-center gap-3 cursor-pointer hover:opacity-80 transition-opacity"
                onClick={() => setIsMenuOpen(!isMenuOpen)}
            >
                <div className="flex flex-col items-end hidden sm:flex">
                    <span className="text-sm font-semibold text-slate-200">{user.name}</span>
                    <span className="text-[10px] text-slate-500">{user.email || user.mail}</span>
                </div>
                <img 
                    className="w-10 h-10 object-cover rounded-full ring-2 ring-white/10 active:scale-95 transition-transform"
                    src={user.avatar || '/default_avatar.png'} 
                    alt={user.name} 
                    referrerPolicy="no-referrer"
                />
            </div>

            {/* 下拉菜单主体 */}
            {isMenuOpen && (
                <div className="absolute right-0 mt-3 w-48 bg-slate-900 border border-slate-800 rounded-xl shadow-2xl py-2 z-50 animate-in fade-in zoom-in duration-150">
                    <div className="px-4 py-2 border-b border-slate-800 sm:hidden">
                        <p className="text-sm font-medium text-slate-200 truncate">{user.name}</p>
                    </div>
                    
                    {/* 菜单选项 */}
                    <button 
                    className="w-full text-left px-4 py-2 text-sm text-slate-300 hover:bg-slate-800 transition-colors">
                        My Profile
                    </button>
                    
                    <div className="h-px bg-slate-800 my-1"></div>
                    
                    {/* 登出按钮 */}
                    {logout && (
                        <button 
                            onClick={() => {
                                setIsMenuOpen(false);
                                logout();
                            }}
                            className="w-full text-left px-4 py-2 text-sm font-medium text-red-400 hover:bg-red-500/10 transition-colors flex items-center gap-2"
                        >
                            <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
                            </svg>
                            Logout
                        </button>
                    )}
                </div>
            )}
        </div>
    );
}