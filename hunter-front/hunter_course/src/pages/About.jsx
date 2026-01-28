
export default function About(){
    return (
        <div className="max-w-4xl mx-auto py-12 px-4 space-y-12">
            <section className="text-center space-y-6">
                <h1 className="text-4xl font-bold text-white">About CourseHunter</h1>
                <p className="text-lg text-slate-400">
                    We are dedicated to helping students discover the best courses from top universities around the world.
                </p>
            </section>

             <section className="grid gap-8 md:grid-cols-2">
                <div className="bg-slate-900/50 p-8 rounded-2xl border border-white/5">
                    <h2 className="text-2xl font-semibold text-white mb-4">Our Mission</h2>
                    <p className="text-slate-400 leading-relaxed">
                        To simplify the course selection process by providing transparent, up-to-date information and student reviews, enabling you to make informed education decisions.
                    </p>
                </div>
                <div className="bg-slate-900/50 p-8 rounded-2xl border border-white/5">
                    <h2 className="text-2xl font-semibold text-white mb-4">Our Vision</h2>
                    <p className="text-slate-400 leading-relaxed">
                        A world where every student has access to the information they need to shape their educational journey and future career.
                    </p>
                </div>
            </section>
        </div>
    )
}