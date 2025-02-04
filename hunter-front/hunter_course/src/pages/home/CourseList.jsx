import { useState, useEffect } from 'react';
import CourseCard from '../../components/course_components/CourseCard';
import { fetchCourses } from '../../service/fetch_course.js';
import PageNumber from '../../components/common_components/PageNumber';

export default function CourseList({search}) {
    // courses is used to store the courses
    const [courses, setCourses] = useState([]);
    // loading is used to store the loading state
    const [loading, setLoading] = useState(true);
    // error is used to store the error state
    const [error, setError] = useState(null);
    const [page, setPage] = useState(1);
    const [pageSize, setPageSize] = useState(5);



    // loadCourses is used to fetch the courses
    const loadCourses = async () => {
        try {
            const data = await fetchCourses(page,pageSize,search);
            setCourses(data);
        } catch (err) {
            setError('Failed to load courses. Please try again later.');
        } finally {
            setLoading(false);
        }
    };

    // useEffect is used to fetch the courses when the page or pageSize changes
    useEffect(() => {
        loadCourses();
    }, [page,pageSize]);

    if (loading) {
        return <div>Loading courses...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    return (
        <div className='flex w-full flex-col items-center justify-center'>
            {courses.length === 0 ? (
                <div>No courses available.</div>
            ) : (
                courses.map((course) => <CourseCard key={course.id} course={course} />)
            )}

            <PageNumber page={page} setPage={setPage}/>

        </div>
    );
}
