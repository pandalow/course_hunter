import Filter from "./home/Filter";
import CourseList from "./home/CourseList";
import {useState} from 'react';

export default function Home() {
    const [search, setSearch] = useState('');

    return(
        <>
            <div className="flex flex-col items-center justify-center mt-8">
                    <Filter setSearch={setSearch} />
                    <CourseList search={search} />
            </div>
        </>
    )
}