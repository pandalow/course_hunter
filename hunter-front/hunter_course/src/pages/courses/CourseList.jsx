import { useState, useEffect } from 'react';
import CourseCard from '../../components/course_components/CourseCard.jsx';
import { fetchCourses, searchCourses } from '../../api/fetch_course.js';
import PageNumber from '../../components/common_components/PageNumber.jsx';

export default function CourseList({ search }) {
    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [page, setPage] = useState(1);
    const [pageSize] = useState(6); // 每页6个课程，适配九宫格

    useEffect(() => {
        const loadCourses = async () => {
            setLoading(true);
            try {
                const data = search ? await searchCourses(search) : await fetchCourses(page, pageSize, search);
                setCourses(data);
                setError(null);
            } catch (err) {
                setError('Failed to load courses. Please try again later.');
            } finally {
                setLoading(false);
            }
        };

        loadCourses();
    }, [page, pageSize, search]);

    if (loading) {
        return (
            <div className="flex flex-col items-center justify-center min-h-screen">
                <div className="w-12 h-12 border-4 border-blue-500 border-t-transparent rounded-full animate-spin"></div>
                <p className="text-white text-lg font-semibold mt-4">Loading courses...</p>
            </div>
        );
    }
    

    if (error) {
        return <div className="text-center text-red-500 text-lg font-semibold mt-6">{error}</div>;
    }

    return (
        <div className="flex flex-col items-center w-full">
            {/* 课程网格 */}
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 w-full mb-12">
                {courses.length === 0 ? (
                    <div className="col-span-full text-center py-12">
                        <p className="text-slate-400 text-lg">No courses found matching your criteria.</p>
                    </div>
                ) : (
                    courses.map((course) => <CourseCard key={course.id} course={course} />)
                )}
            </div>

            {/* 分页组件 */}
            <div className="mt-auto">
                 <PageNumber page={page} setPage={setPage} />
            </div>
        </div>
    );
}
