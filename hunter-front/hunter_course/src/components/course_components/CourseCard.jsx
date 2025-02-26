import { Link } from 'react-router-dom';

export default function CourseCard({ course }) {
    return (
        <Link to={`/course/${course.id}`} className="block">
            <div className="bg-gray-900 text-white shadow-md rounded-2xl flex flex-col p-4 gap-4 border border-gray-700 hover:shadow-xl hover:-translate-y-1 transition duration-300 cursor-pointer w-full min-h-[260px]">
                
                {/* 课程图片 */}
                <img 
                    className="w-24 h-24 object-cover rounded-lg mx-auto"
                    src={course.image || '/default_course.png'}  
                    alt={course.title} 
                />

                {/* 课程信息 */}
                <div className="flex flex-col justify-between flex-1">
                    <h2 className="text-lg font-bold text-white text-center">{course.title}</h2>
                    <div className="text-gray-400 text-sm space-y-1 text-center">
                        <p className="font-medium">{course.countryName} / {course.code} / {course.semester || 'Not specified'}</p>
                        <p className="text-gray-500">{course.institutionName || 'Not specified'}</p>
                        <p className="line-clamp-2 text-gray-300">{course.description || 'No description available'}</p>
                        <p className="text-yellow-400 text-lg">⭐ {course.rating}</p>
                        <p className="text-gray-500">{course.comments || 'No comments'}</p>
                    </div>
                </div>
            </div>
        </Link>
    );
}
