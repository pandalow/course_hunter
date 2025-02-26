import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

export default function CourseDetail() {
    const { id } = useParams(); // 获取 URL 参数中的 course ID
    const navigate = useNavigate();
    const [course, setCourse] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchCourseDetail = async () => {
            try {
                const response = await fetch(`/course/${id}`);
                const result = await response.json();
                if (result.code === 1 && result.data) {
                    setCourse(result.data);
                } else {
                    throw new Error("Course not found.");
                }
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchCourseDetail();
    }, [id]);

    if (loading) return <div className="text-center text-white text-lg mt-6">Loading...</div>;
    if (error) return <div className="text-center text-red-500 text-lg mt-6">{error}</div>;

    return (
        <div className="bg-black text-white min-h-screen flex flex-col items-center p-6">
            <div className="container mx-auto max-w-3xl p-6 bg-gray-900 rounded-lg shadow-md">
                {/* 课程标题 */}
                <h1 className="text-3xl font-extrabold text-blue-400">{course.title}</h1>
                <p className="text-gray-400 mt-1">{course.institutionName} - {course.countryName}</p>
                
                {/* 课程基本信息 */}
                <div className="mt-4 space-y-2">
                    <p><span className="font-bold">Course Code:</span> {course.code}</p>
                    <p><span className="font-bold">Semester:</span> {course.semester}</p>
                    <p><span className="font-bold">Credits:</span> {course.credits}</p>
                </div>

                {/* 课程大纲 */}
                <div className="mt-6">
                    <h2 className="text-xl font-bold text-blue-300">Course Outline</h2>
                    <p className="text-gray-300 mt-2">{course.outline}</p>
                </div>

                {/* 评估方式 */}
                <div className="mt-6">
                    <h2 className="text-xl font-bold text-blue-300">Assessment</h2>
                    <p className="text-gray-300 mt-2">{course.assessments}</p>
                </div>

                {/* 课程学习成果 */}
                <div className="mt-6">
                    <h2 className="text-xl font-bold text-blue-300">Learning Outcomes</h2>
                    <ul className="text-gray-300 mt-2 list-disc list-inside">
                        {course.outcomes.split(';').map((outcome, index) => (
                            <li key={index}>{outcome}</li>
                        ))}
                    </ul>
                </div>

                {/* 授课教师 */}
                <div className="mt-6">
                    <h2 className="text-xl font-bold text-blue-300">Instructor</h2>
                    {course.teachers.map((teacher) => (
                        <div key={teacher.id} className="mt-2">
                            <p className="text-lg font-semibold">{teacher.name}</p>
                            <p className="text-gray-400">📧 <a href={`mailto:${teacher.email}`} className="text-blue-400 hover:underline">{teacher.email}</a></p>
                            <p className="text-gray-400">🔗 <a href={teacher.profileLink} target="_blank" rel="noopener noreferrer" className="text-blue-400 hover:underline">Profile</a></p>
                        </div>
                    ))}
                </div>

                {/* 返回按钮 */}
                <button 
                    onClick={() => navigate(-1)} 
                    className="mt-6 px-4 py-2 bg-blue-500 hover:bg-blue-600 text-white rounded-lg transition duration-300"
                >
                    ← Back to Courses
                </button>
            </div>
        </div>
    );
}
