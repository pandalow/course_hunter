import Button from "../../components/common_components/Button";
import {useState} from 'react';

export default function Filter({setSearch}) {

    // handleSearch is used to set the search
    function handleSearch(){
        setSearch(search);
    }

    return <div>
        <div className="flex flex-row items-center justify-center border-2 border-gray-300 rounded-lg p-2 hover:border-gray-400 hover:bg-gray-100">
            <input type="text" placeholder="Key Words" className="w-full h-full p-2" />
            <Button text="Search" onClick={() => {}} className="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded-full" />
        </div>
    </div>
}