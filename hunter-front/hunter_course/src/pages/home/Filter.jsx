import Button from "../../components/common_components/Button";
import {useState} from 'react';

export default function Filter({setSearch}) {
    const [query, setQuery] = useState('');

    // handleSearch is used to set the search
    function handleSearch(){
        setSearch(query);
    }

    return <div>
        <div className="flex flex-row items-center justify-center border-2 border-gray-300 rounded-lg p-2 hover:border-gray-400 hover:bg-gray-100">
            <input 
            type="text" 
            placeholder="Key Words" 
            className="w-full h-full p-2" 
            onChange={(e) => setQuery(e.target.value)} 
            />

            <Button 
            text="Search" 
            onClick={handleSearch} 
            className="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded-full" />
        </div>
    </div>
}