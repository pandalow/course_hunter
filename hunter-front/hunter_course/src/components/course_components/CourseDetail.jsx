import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { fetchCourseById } from '../../api/fetch_course';
import { getComments, createComment, deleteComment } from '../../api/comment';
import { getRatings, createRating, updateRating, deleteRating } from '../../api/rating';

export default function CourseDetail() {
    const { id } = useParams(); // Get Course ID
    const navigate = useNavigate();
    const [course, setCourse] = useState(null);
    const [comments, setComments] = useState([]);
    const [ratings, setRatings] = useState([]);
    const [newComment, setNewComment] = useState('');
    const [newRating, setNewRating] = useState({ score: 5, content: '' });
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [currentUser, setCurrentUser] = useState(null);
    const [userRating, setUserRating] = useState(null);

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
                
                // Get comments - targetType "1" for Course
                const commentsData = await getComments(id, "1"); 
                setComments(commentsData ? commentsData.filter(c => c !== null && c !== undefined) : []);

                // Get ratings for the course
                const ratingsData = await getRatings(id);
                const filteredRatings = ratingsData ? ratingsData.filter(r => r !== null && r !== undefined) : [];
                setRatings(filteredRatings);

                // Find current user's rating if exists
                if (storedUser && filteredRatings.length > 0) {
                    const user = JSON.parse(storedUser);
                    const existingRating = filteredRatings.find(r => r && r.userId === user.id);
                    if (existingRating) {
                        setUserRating(existingRating);
                        setNewRating({ score: existingRating.score, content: existingRating.content || '' });
                    }
                }
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
                targetType: "1" // 1 for Course
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

    const handleSubmitRating = async () => {
        if (!currentUser) {
            alert("Please login first.");
            return;
        }
        if (newRating.score < 1 || newRating.score > 5) {
            alert("Rating must be between 1 and 5");
            return;
        }
        try {
            const ratingData = {
                targetId: parseInt(id),
                targetType: "1", // 1 for Course
                score: newRating.score,
                content: newRating.content
            };

            if (userRating) {
                // Update existing rating
                const updated = await updateRating({ ...ratingData, id: userRating.id });
                setRatings(ratings.map(r => r.id === userRating.id ? updated : r));
                setUserRating(updated);
            } else {
                // Create new rating
                const created = await createRating(ratingData);
                setRatings([created, ...ratings]);
                setUserRating(created);
            }
            alert(userRating ? "Rating updated successfully!" : "Rating submitted successfully!");
        } catch (err) {
            console.error(err);
            alert("Failed to submit rating");
        }
    };

    const handleDeleteRating = async (ratingId) => {
        if (!window.confirm("Delete your rating?")) return;
        try {
            await deleteRating(ratingId);
            setRatings(ratings.filter(r => r.id !== ratingId));
            setUserRating(null);
            setNewRating({ score: 5, content: '' });
        } catch (err) {
            console.error(err);
            alert("Failed to delete rating");
        }
    };

    if (loading) return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-slate-950">
            <div className="w-12 h-12 border-4 border-blue-500 border-t-transparent rounded-full animate-spin"></div>
            <p className="text-white text-lg font-semibold mt-4">Loading course details...</p>
        </div>
    );
    if (error) return <div className="text-center text-red-500 text-lg mt-6">{error}</div>;
    if (!course) return null;

    const averageRating = ratings.length > 0 
        ? (ratings.reduce((sum, r) => sum + r.score, 0) / ratings.length).toFixed(1)
        : 'N/A';

    return (
        <div className="bg-slate-950 text-white min-h-screen">
            <div className="container mx-auto max-w-5xl px-4 py-8">
                {/* Course Header */}
                <div className="bg-slate-900/50 backdrop-blur-sm border border-white/5 rounded-xl p-6 mb-6">
                    <div className="flex flex-col md:flex-row justify-between items-start gap-4">
                        <div className="flex-1">
                            <h1 className="text-3xl font-extrabold text-white mb-2">{course.title}</h1>
                            <p className="text-slate-400">{course.institutionName} • {course.countryName}</p>
                            <div className="flex flex-wrap gap-3 mt-3">
                                <span className="bg-slate-800/80 px-3 py-1.5 rounded-full text-sm border border-white/10">
                                    📚 {course.code}
                                </span>
                                <span className="bg-slate-800/80 px-3 py-1.5 rounded-full text-sm border border-white/10">
                                    📅 {course.semester}
                                </span>
                                <span className="bg-slate-800/80 px-3 py-1.5 rounded-full text-sm border border-white/10">
                                    🎓 {course.credits} Credits
                                </span>
                            </div>
                        </div>
                        <div className="bg-gradient-to-br from-yellow-500/20 to-orange-500/20 border border-yellow-500/30 rounded-xl px-6 py-4 text-center">
                            <div className="text-4xl font-bold text-yellow-400">{averageRating}</div>
                            <div className="text-sm text-slate-400 mt-1">Average Rating</div>
                            <div className="text-xs text-slate-500 mt-1">({ratings.length} reviews)</div>
                        </div>
                    </div>
                </div>

                <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
                    {/* Main Content */}
                    <div className="lg:col-span-2 space-y-6">
                        {/* Course Details */}
                        <div className="bg-slate-900/50 backdrop-blur-sm border border-white/5 rounded-xl p-6">
                            <h2 className="text-xl font-bold text-blue-400 mb-4">📖 Course Outline</h2>
                            <p className="text-slate-300 leading-relaxed">{course.outline || 'No outline available.'}</p>
                        </div>

                        {course.assessments && (
                            <div className="bg-slate-900/50 backdrop-blur-sm border border-white/5 rounded-xl p-6">
                                <h2 className="text-xl font-bold text-blue-400 mb-4">📝 Assessment</h2>
                                <p className="text-slate-300 leading-relaxed">{course.assessments}</p>
                            </div>
                        )}

                        {course.outcomes && (
                            <div className="bg-slate-900/50 backdrop-blur-sm border border-white/5 rounded-xl p-6">
                                <h2 className="text-xl font-bold text-blue-400 mb-4">🎯 Learning Outcomes</h2>
                                <ul className="text-slate-300 space-y-2">
                                    {course.outcomes.split(';').filter(o => o.trim()).map((outcome, index) => (
                                        <li key={index} className="flex gap-2">
                                            <span className="text-blue-400 mt-1">•</span>
                                            <span>{outcome.trim()}</span>
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        )}

                        {/* Ratings & Reviews Section */}
                        <div className="bg-slate-900/50 backdrop-blur-sm border border-white/5 rounded-xl p-6">
                            <h2 className="text-2xl font-bold text-blue-400 mb-6">⭐ Ratings & Reviews</h2>
                            
                            {/* Submit Rating Form */}
                            {currentUser && (
                                <div className="mb-8 bg-slate-800/50 border border-blue-500/20 rounded-lg p-6">
                                    <h3 className="text-lg font-semibold mb-4 text-blue-300">
                                        {userRating ? '✏️ Update Your Rating' : '✨ Write a Review'}
                                    </h3>
                                    <div className="space-y-4">
                                        <div>
                                            <label className="block text-sm font-medium text-slate-300 mb-2">
                                                Your Rating
                                            </label>
                                            <div className="flex gap-2 items-center">
                                                {[1, 2, 3, 4, 5].map((star) => (
                                                    <button
                                                        key={star}
                                                        onClick={() => setNewRating({ ...newRating, score: star })}
                                                        className={`text-3xl transition-transform hover:scale-110 ${
                                                            star <= newRating.score ? 'text-yellow-400' : 'text-slate-600'
                                                        }`}
                                                    >
                                                        ⭐
                                                    </button>
                                                ))}
                                                <span className="ml-3 text-lg font-semibold text-yellow-400">
                                                    {newRating.score}/5
                                                </span>
                                            </div>
                                        </div>
                                        <div>
                                            <label className="block text-sm font-medium text-slate-300 mb-2">
                                                Your Review
                                            </label>
                                            <textarea
                                                className="w-full p-3 bg-slate-800 text-white rounded-md border border-slate-700 focus:border-blue-500 outline-none resize-none"
                                                rows="4"
                                                placeholder="Share your experience with this course..."
                                                value={newRating.content}
                                                onChange={(e) => setNewRating({ ...newRating, content: e.target.value })}
                                            />
                                        </div>
                                        <div className="flex gap-3">
                                            <button
                                                onClick={handleSubmitRating}
                                                className="px-5 py-2.5 bg-blue-600 hover:bg-blue-700 rounded-lg transition font-medium"
                                            >
                                                {userRating ? '💾 Update Review' : '📤 Submit Review'}
                                            </button>
                                            {userRating && (
                                                <button
                                                    onClick={() => handleDeleteRating(userRating.id)}
                                                    className="px-5 py-2.5 bg-red-600/20 hover:bg-red-600/30 text-red-400 border border-red-500/30 rounded-lg transition font-medium"
                                                >
                                                    🗑️ Delete
                                                </button>
                                            )}
                                        </div>
                                    </div>
                                </div>
                            )}

                            {!currentUser && (
                                <div className="mb-8 bg-slate-800/30 border border-slate-700 rounded-lg p-4 text-center">
                                    <p className="text-slate-400">🔒 Please login to leave a rating and review</p>
                                </div>
                            )}

                            {/* Ratings List */}
                            <div className="space-y-4">
                                <h3 className="text-lg font-semibold text-slate-200">
                                    All Reviews ({ratings.length})
                                </h3>
                                {ratings.length === 0 ? (
                                    <p className="text-slate-500 text-center py-8">No reviews yet. Be the first to review!</p>
                                ) : (
                                    ratings.filter(rating => rating && rating.id).map(rating => (
                                        <div key={rating.id} className="bg-slate-800/50 border border-slate-700 rounded-lg p-4 relative">
                                            <div className="flex items-start gap-3">
                                                <img 
                                                    src={rating.userAvatar || '/default_avatar.png'} 
                                                    alt="User" 
                                                    className="w-10 h-10 rounded-full object-cover"
                                                />
                                                <div className="flex-1">
                                                    <div className="flex items-center justify-between mb-2">
                                                        <div>
                                                            <p className="font-semibold text-slate-200">{rating.userName || 'Anonymous'}</p>
                                                            <p className="text-xs text-slate-500">
                                                                {rating.createTime ? new Date(rating.createTime).toLocaleDateString() : 'Date unknown'}
                                                            </p>
                                                        </div>
                                                        <div className="flex items-center gap-1 bg-yellow-500/10 px-2.5 py-1 rounded-full border border-yellow-500/20">
                                                            <span className="text-sm">⭐</span>
                                                            <span className="text-sm font-bold text-yellow-400">{rating.score}/5</span>
                                                        </div>
                                                    </div>
                                                    {rating.content && (
                                                        <p className="text-slate-300 leading-relaxed">{rating.content}</p>
                                                    )}
                                                </div>
                                            </div>
                                        </div>
                                    ))
                                )}
                            </div>
                        </div>

                        {/* Comments Section */}
                        <div className="bg-slate-900/50 backdrop-blur-sm border border-white/5 rounded-xl p-6">
                            <h2 className="text-2xl font-bold text-blue-400 mb-6">💬 Comments</h2>
                            
                            {/* Add Comment */}
                            <div className="mb-6">
                                <textarea 
                                    className="w-full p-3 bg-slate-800 text-white rounded-md border border-slate-700 focus:border-blue-500 outline-none resize-none"
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
                                    <p className="text-slate-500 text-center py-8">No comments yet.</p>
                                ) : (
                                    comments.filter(comment => comment && comment.id).map(comment => (
                                        <div key={comment.id} className="bg-slate-800/50 border border-slate-700 rounded-lg p-4 relative">
                                            <div className="flex items-center gap-3 mb-2">
                                                <img src={comment.userAvatar || '/default_avatar.png'} alt="User" className="w-8 h-8 rounded-full object-cover"/>
                                                <div>
                                                    <p className="font-semibold text-sm text-slate-200">{comment.userName || 'Anonymous'}</p>
                                                    <p className="text-xs text-slate-500">
                                                        {comment.createTime ? new Date(comment.createTime).toLocaleString() : 'Date unknown'}
                                                    </p>
                                                </div>
                                            </div>
                                            <p className="text-slate-300 whitespace-pre-wrap">{comment.content}</p>
                                            
                                            {currentUser && currentUser.id === comment.userId && (
                                                <button 
                                                    onClick={() => handleDeleteComment(comment.id)}
                                                    className="absolute top-2 right-2 text-red-400 hover:text-red-300 text-xs px-2 py-1 rounded hover:bg-red-500/10 transition"
                                                >
                                                    Delete
                                                </button>
                                            )}
                                        </div>
                                    ))
                                )}
                            </div>
                        </div>
                    </div>

                    {/* Sidebar */}
                    <div className="lg:col-span-1 space-y-6">
                        {/* Instructors */}
                        {course.teachers && course.teachers.length > 0 && (
                            <div className="bg-slate-900/50 backdrop-blur-sm border border-white/5 rounded-xl p-6 sticky top-6">
                                <h2 className="text-xl font-bold text-blue-400 mb-4">👨‍🏫 Instructors</h2>
                                {course.teachers.filter(teacher => teacher && teacher.id).map((teacher) => (
                                    <div key={teacher.id} className="mb-4 last:mb-0">
                                        <p className="text-lg font-semibold text-white">{teacher.name}</p>
                                        {teacher.email && (
                                            <a href={`mailto:${teacher.email}`} className="text-sm text-blue-400 hover:underline flex items-center gap-1 mt-1">
                                                📧 {teacher.email}
                                            </a>
                                        )}
                                        {teacher.profileLink && (
                                            <a href={teacher.profileLink} target="_blank" rel="noopener noreferrer" className="text-sm text-blue-400 hover:underline flex items-center gap-1 mt-1">
                                                🔗 View Profile
                                            </a>
                                        )}
                                    </div>
                                ))}
                            </div>
                        )}
                    </div>
                </div>

                {/* Back Button */}
                <div className="mt-8 flex justify-center">
                    <button 
                        onClick={() => navigate(-1)} 
                        className="px-6 py-3 bg-slate-800 hover:bg-slate-700 text-white rounded-lg transition duration-300 border border-white/10"
                    >
                        ← Back to Courses
                    </button>
                </div>
            </div>
        </div>
    );
}
