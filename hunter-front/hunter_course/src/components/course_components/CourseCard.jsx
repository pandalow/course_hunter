import {Link} from 'react-router-dom'
import {useNavigate} from 'react-router-dom'

export default function CourseCard({course}) {
    const navigate = useNavigate();

    const handleNavigate = () => {
        navigate(`/course/${course.id}`);
    }

    return (
            <div className="bg-white shadow-md rounded-lg flex p-6 m-4 w-2/3 hover:bg-gray-100" onClick={handleNavigate}>
                <img className="w-32 h-32 object-cover rounded-lg" src={course.image} alt={course.title} />
                <div className="ml-6 flex flex-col justify-between flex-1">
                    <h2 className="text-xl font-semibold mb-2">{course.title}</h2>
                    <p className="text-gray-600 mb-1">{course.countryName}</p>
                    <p className="text-gray-600 mb-1">{course.code}</p>
                    <p className="text-gray-600 mb-1">{course.semester}</p>
                    <p className="text-gray-600 mb-1">{course.description}</p>
                    <p className="text-gray-600 mb-1">{course.rating}</p>
                    <p className="text-gray-600 mb-1">{course.institutionName}</p>
                    {course.comments ? (
                        <p className="text-gray-600 mb-1">{course.comments}</p>
                    ) : (
                        <p className="text-gray-600 mb-1">No comments</p>
                    )}
                </div>
            </div>
    )   
}