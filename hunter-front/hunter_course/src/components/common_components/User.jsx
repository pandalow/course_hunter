

export default function User({ user, logout }) {
    return (
        <div className="flex items-center gap-3 group relative pointer-events-auto">
            <div className="flex flex-col items-end hidden sm:flex">
                <span className="text-sm font-semibold text-slate-200">{user.name}</span>
            </div>
            <img 
                className="w-10 h-10 object-cover rounded-full ring-2 ring-white/10"
                src={user.avatar || '/default_avatar.png'} 
                alt={user.name} 
            />
            
            {/* Simple Logout Button for now since no dropdown logic exists */}
            {logout && (
                <button 
                    onClick={logout}
                    className="ml-2 text-xs font-medium text-slate-400 hover:text-red-400 transition-colors"
                >
                    Log out
                </button>
            )}
        </div>
    )
}