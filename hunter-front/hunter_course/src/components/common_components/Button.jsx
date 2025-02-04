import {useState} from 'react'

export default function Button({text, onClick, className}) {
    const [isHovered, setIsHovered] = useState(false);
    const buttonStyle = `bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded-full ${className}`;

    return (
        <button className={buttonStyle} onClick={onClick} 
            onMouseEnter={() => setIsHovered(true)} onMouseLeave={() => setIsHovered(false)}>
            <span className={`text-gray-800 ${isHovered ? 'text-gray-400' : ''}`}>{text}</span>
        </button>
    )
}