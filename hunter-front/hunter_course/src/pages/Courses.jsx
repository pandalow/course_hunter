import Filter from "./courses/Filter";
import CourseList from "./courses/CourseList";
import { useState } from 'react';

export default function Courses() {
    const [search, setSearch] = useState('');

    return (
        <div className="flex flex-col items-center w-full">
            <div className="w-full space-y-8">
                <div className="text-center space-y-2 mb-8">
                     <h1 className="text-3xl font-bold text-white">Explore Courses</h1>
                     <p className="text-slate-400">Find the perfect course to enhance your skills</p>
                </div>

                {/* 搜索栏 */}
                <Filter setSearch={setSearch} />
                {/* 课程列表 */}
                <CourseList search={search} />
            </div>
        </div>
    );
}
