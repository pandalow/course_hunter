import Button from "../../components/common_components/Button";
import { useState } from 'react';

export default function Filter({ setSearch }) {
    const [query, setQuery] = useState('');

    function handleSearch() {
        setSearch(query);
    }

    return (
        <div className="w-full flex justify-center mb-8">
            <div className="relative w-full max-w-2xl flex items-center">
                <input 
                    type="text" 
                    placeholder="Search by course name, code, or professor..." 
                    className="w-full bg-slate-900/50 backdrop-blur-sm border border-white/10 text-white placeholder-slate-500 py-3 pl-5 pr-32 rounded-full focus:outline-none focus:ring-2 focus:ring-blue-500/50 focus:border-blue-500/50 transition-all shadow-lg"
                    onChange={(e) => setQuery(e.target.value)} 
                    onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
                />
                <div className="absolute right-2 top-1.5 bottom-1.5">
                     <Button 
                        text="Search" 
                        onClick={handleSearch} 
                        className="h-full px-6 bg-blue-600 hover:bg-blue-500 text-white font-medium rounded-full transition-colors duration-200" 
                    />
                </div>
            </div>
        </div>
    );
}
