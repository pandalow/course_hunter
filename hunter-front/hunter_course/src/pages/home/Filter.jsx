import Button from "../../components/common_components/Button";
import { useState } from 'react';

export default function Filter({ setSearch }) {
    const [query, setQuery] = useState('');

    function handleSearch() {
        setSearch(query);
    }

    return (
        <div className="flex items-center justify-center p-4">
            <div className="flex items-center w-full max-w-lg border-2 border-gray-700 rounded-lg p-2 bg-black space-x-4">
                {/* 搜索输入框 */}
                <input 
                    type="text" 
                    placeholder="Search courses..." 
                    className="w-full bg-gray-900 text-white placeholder-gray-500 p-2 rounded-lg focus:outline-none" 
                    onChange={(e) => setQuery(e.target.value)} 
                />

                {/* 搜索按钮 */}
                <Button 
                    text="Search" 
                    onClick={handleSearch} 
                    className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded-lg transition duration-300" 
                />
            </div>
        </div>
    );
}
