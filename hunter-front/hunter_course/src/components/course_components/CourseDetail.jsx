import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { fetchCourseById } from '../../api/fetch_course';
import { getComments, createComment, deleteComment } from '../../api/comment';

export default function CourseDetail() {
    const { id } = useParams(); // Get Course ID
    const navigate = useNavigate();
    const [course, setCourse] = useState(null);
    const [comments, setComments] = useState([]);
    const [newComment, setNewComment] = useState('');
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [currentUser, setCurrentUser] = useState(null);

    useEffect(() => {
        const storedUser = localStorage.getItem('user_info');
        if (storedUser) {
            setCurrentUser(JSON.parse(storedUser));
        }

        const fetchData = async () => {
            try {
                setLoading(true);
                const courseData = await fetchCourseById(id);
                setCourse(courseData);
                
                // TargetType: 2 for Course (based on TargetType enum)
                const commentsData = await getComments(id, "2"); 
                setComments(commentsData);
            } catch (err) {
                console.error(err);
                setError("Failed to load course details.");
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [id]);

    const handleAddComment = async () => {
        if (!newComment.trim()) return;
        if (!currentUser) {
            alert("Please login first.");
            return;
        }
        try {
            const addedComment = await createComment({
                content: newComment,
                targetId: id,
                targetType: "2" // 2 for Course
            });
            setComments([addedComment, ...comments]);
            setNewComment('');
        } catch (err) {
            console.error(err);
            alert("Failed to add comment");
        }
    };

    const handleDeleteComment = async (commentId) => {
        if (!window.confirm("Delete this comment?")) return;
        try {
            await deleteComment(commentId);
            setComments(comments.filter(c => c.id !== commentId));
        } catch (err) {
            console.error(err);
            alert("Failed to delete comment");
        }
    };

    if (loading) return <div className="text-center text-white text-lg mt-6">Loading...</div>;
    if (error) return <div className="text-center text-red-500 text-lg mt-6">{error}</div>;
    if (!course) return null;

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
                        {course.outcomes && course.outcomes.split(';').map((outcome, index) => (
                            <li key={index}>{outcome}</li>
                        ))}
                    </ul>
                </div>

                {/* 授课教师 */}
                <div className="mt-6">
                    <h2 className="text-xl font-bold text-blue-300">Instructor</h2>
                    {course.teachers && course.teachers.map((teacher) => (
                        <div key={teacher.id} className="mt-2">
                            <p className="text-lg font-semibold">{teacher.name}</p>
                            <p className="text-gray-400">📧 <a href={`mailto:${teacher.email}`} className="text-blue-400 hover:underline">{teacher.email}</a></p>
                            <p className="text-gray-400">🔗 <a href={teacher.profileLink} target="_blank" rel="noopener noreferrer" className="text-blue-400 hover:underline">Profile</a></p>
                        </div>
                    ))}
                </div>

                {/* Comments Section */}
                <div className="mt-8 border-t border-gray-700 pt-6">
                    <h2 className="text-2xl font-bold text-blue-300 mb-4">Comments</h2>
                    
                    {/* Add Comment */}
                    <div className="mb-6">
                        <textarea 
                            className="w-full p-3 bg-gray-800 text-white rounded-md border border-gray-700 focus:border-blue-500 outline-none"
                            rows="3"
                            placeholder={currentUser ? "Add a comment..." : "Please login to comment"}
                            value={newComment}
                            onChange={(e) => setNewComment(e.target.value)}
                            disabled={!currentUser}
                        />
                        <div className="mt-2 flex justify-end">
                            <button 
                                onClick={handleAddComment}
                                disabled={!currentUser || !newComment.trim()}
                                className="px-4 py-2 bg-blue-600 hover:bg-blue-700 disabled:bg-gray-600 rounded-lg transition"
                            >
                                Post Comment
                            </button>
                        </div>
                    </div>

                    {/* Comment List */}
                    <div className="space-y-4">
                        {comments.length === 0 ? (
                            <p className="text-gray-500 text-center">No comments yet.</p>
                        ) : (
                            comments.map(comment => (
                                <div key={comment.id} className="bg-gray-800 p-4 rounded-lg relative border border-gray-700">
                                    <div className="flex items-center gap-3 mb-2">
                                        <img src={comment.userAvatar || '/default_avatar.png'} alt="User" className="w-8 h-8 rounded-full object-cover"/>
                                        <div>
                                            <p className="font-semibold text-sm text-blue-200">{comment.userName}</p>
                                            <p className="text-xs text-gray-400">{new Date(comment.createTime).toLocaleString()}</p>
                                        </div>
                                    </div>
                                    <p className="text-gray-300 whitespace-pre-wrap">{comment.content}</p>
                                    
                                    {currentUser && currentUser.id === comment.userId && (
                                        <button 
                                            onClick={() => handleDeleteComment(comment.id)}
                                            className="absolute top-2 right-2 text-red-500 hover:text-red-400 text-xs px-2 py-1 rounded hover:bg-red-500/10 transition"
                                        >
                                            Delete
                                        </button>
                                    )}
                                </div>
                            ))
                        )}
                    </div>
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
