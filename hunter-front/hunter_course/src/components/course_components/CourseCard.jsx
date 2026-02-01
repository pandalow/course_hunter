import { Link } from 'react-router-dom';

export default function CourseCard({ course }) {
    return (
        <Link to={`/course/${course.id}`} className="block group">
            <div className="bg-slate-900/50 backdrop-blur-sm border border-white/5 rounded-xl p-5 flex flex-col sm:flex-row gap-6 transition-all duration-300 hover:bg-slate-800/80 hover:shadow-lg hover:shadow-blue-500/10 hover:border-blue-500/30">
                
                {/* Course Image */}
                <div className="shrink-0">
                    <div className="bg-white/5 rounded-lg p-2 w-20 h-20 sm:w-24 sm:h-24 mx-auto sm:mx-0">
                         <img 
                            className="w-full h-full object-cover rounded shadow-sm"
                            src={course.image || '/default_course.png'}  
                            alt={course.title} 
                        />
                    </div>
                </div>

                {/* Content */}
                <div className="flex flex-col flex-1 min-w-0 gap-2">
                    <div className="flex flex-col sm:flex-row sm:items-start justify-between gap-2">
                         <div>
                            <h2 className="text-lg font-bold text-white group-hover:text-blue-400 transition-colors truncate pr-4">
                                {course.title}
                            </h2>
                             <div className="text-xs font-medium uppercase tracking-wider text-slate-500 flex flex-wrap gap-2 mt-1">
                                <span className="bg-slate-800/80 px-2 py-0.5 rounded border border-white/5">{course.countryName}</span>
                                <span className="bg-slate-800/80 px-2 py-0.5 rounded border border-white/5">{course.code}</span>
                            </div>
                         </div>

                        {/* Rating Badge */}
                        <div className="flex items-center gap-1.5 bg-yellow-500/10 px-2.5 py-1 rounded-full border border-yellow-500/20 self-start sm:self-center shrink-0">
                            <span className="text-sm">⭐</span>
                            <span className="text-sm font-bold text-yellow-500">{course.avgScore || course.rating || 'N/A'}</span>
                        </div>
                    </div>

                    <p className="text-slate-400 text-sm line-clamp-2 mt-1">
                        {course.comments || course.description || 'No description available for this course.'}
                    </p>

                    <div className="mt-auto pt-3 flex flex-wrap items-center justify-between text-xs text-slate-500 font-medium">
                        <div className="flex items-center gap-4">
                            <span className="flex items-center gap-1.5">
                                <span className="w-1.5 h-1.5 rounded-full bg-slate-600"></span>
                                {course.institutionName || 'Unknown Institution'}
                            </span>
                             {course.semester && (
                                <span className="hidden sm:inline text-slate-600">
                                   • {course.semester}
                                </span>
                             )}
                        </div>
                        <span className="group-hover:text-blue-400 transition-colors">
                            {course.commentCount || 0} Comments
                        </span>
                    </div>
                </div>
            </div>
        </Link>
    );
}
