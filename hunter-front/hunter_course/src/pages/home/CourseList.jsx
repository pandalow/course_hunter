import { useState, useEffect } from 'react';
import CourseCard from '../../components/course_components/CourseCard';
import { fetchCourses, searchCourses } from '../../service/fetch_course.js'; // 确保 searchCourses 存在
import PageNumber from '../../components/common_components/PageNumber';

export default function CourseList({ search }) {
    // course list
    const [courses, setCourses] = useState([]);
    // loading state
    const [loading, setLoading] = useState(true);
    // error state
    const [error, setError] = useState(null);
    // paging
    const [page, setPage] = useState(1);
    const [pageSize, setPageSize] = useState(5);

    // listen to `fetchCourses` and `searchCourses`
    useEffect(() => {
        const loadCourses = async () => {
            setLoading(true); // Each time the component is rendered, the loading state is set to true
            try {
                let data;
                if (search) {
                    data = await searchCourses(search); // search mode
                } else {
                    data = await fetchCourses(page, pageSize, search); // normal paging mode
                }
                setCourses(data);
                setError(null); // clear the previous error
            } catch (err) {
                setError('Failed to load courses. Please try again later.');
            } finally {
                setLoading(false);
            }
        };

        loadCourses();
    }, [page, pageSize, search]); // listen to the change of page, pageSize, search

    // render the loading state
    if (loading) {
        return <div>Loading courses...</div>;
    }

            // render the error message
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
            <PageNumber page={page} setPage={setPage} />
        </div>
    );
}
