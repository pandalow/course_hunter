import Filter from "./home/Filter";
import CourseList from "./home/CourseList";
import { useState } from 'react';

export default function Home() {
    const [search, setSearch] = useState('');

    return (
        <div className="bg-black text-white min-h-screen flex flex-col items-center">
            <div className="container mx-auto mt-10 space-y-6 px-4">
                {/* 搜索栏 */}
                <Filter setSearch={setSearch} />
                {/* 课程列表 */}
                <CourseList search={search} />
            </div>
        </div>
    );
}
