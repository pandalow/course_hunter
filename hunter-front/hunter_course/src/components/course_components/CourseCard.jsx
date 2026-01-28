import { Link } from 'react-router-dom';

export default function CourseCard({ course }) {
    return (
        <Link to={`/course/${course.id}`} className="block group h-full">
            <div className="h-full bg-slate-900/50 backdrop-blur-sm border border-white/5 rounded-2xl p-5 flex flex-col gap-4 transform transition-all duration-300 hover:scale-[1.02] hover:bg-slate-800/80 hover:shadow-xl hover:shadow-blue-500/10 hover:border-blue-500/30">
                
                <div className="flex items-start justify-between gap-4">
                    {/* Course Image */}
                    <div className="bg-white/5 rounded-xl p-2 shrink-0">
                         <img 
                            className="w-16 h-16 object-cover rounded-lg"
                            src={course.image || '/default_course.png'}  
                            alt={course.title} 
                        />
                    </div>
                   
                    {/* Rating Badge */}
                    <div className="flex items-center gap-1 bg-yellow-500/10 px-2 py-1 rounded-full border border-yellow-500/20">
                        <span className="text-sm">⭐</span>
                        <span className="text-sm font-semibold text-yellow-500">{course.rating}</span>
                    </div>
                </div>

                <div className="flex flex-col flex-1 gap-2">
                    <h2 className="text-lg font-bold text-white group-hover:text-blue-400 transition-colors line-clamp-1">{course.title}</h2>
                    
                    <div className="text-xs font-medium uppercase tracking-wider text-slate-500 flex flex-wrap gap-2">
                         <span className="bg-slate-800 px-2 py-1 rounded">{course.countryName}</span>
                         <span className="bg-slate-800 px-2 py-1 rounded">{course.code}</span>
                    </div>

                    <p className="text-slate-400 text-sm line-clamp-2 mt-1 min-h-[2.5rem]">
                        {course.description || 'No description available for this course.'}
                    </p>
                </div>

                <div className="mt-auto pt-4 border-t border-white/5 flex items-center justify-between text-xs text-slate-500 font-medium">
                     <span>{course.institutionName || 'Unknown Institution'}</span>
                     <span className="text-slate-600">{course.comments || 0} Comments</span>
                </div>
            </div>
        </Link>
    );
}
