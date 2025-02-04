import {useState} from 'react'

export default function PageNumber({page, setPage}) {
    const pageNumber = [1,2,3,4,5,6,7,8,9,10]
    return (

        <div className="text-white text-xl font-bold hover:text-gray-300">
            {pageNumber.map((page) => (
                <button className="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded-full" key={page} onClick={() => setPage(page)}>
                    <span className="text-gray-800">{page}</span>
                </button>
            ))}
        </div>
    )
}