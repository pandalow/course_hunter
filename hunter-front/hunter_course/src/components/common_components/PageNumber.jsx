export default function PageNumber({ page, setPage }) {
    const totalPages = 10;
    const pageNumbers = Array.from({ length: totalPages }, (_, i) => i + 1);

    return (
        <div className="flex flex-wrap justify-center gap-2 mt-8 py-4">
            {pageNumbers.map((num) => (
                <button
                    key={num}
                    className={`w-10 h-10 rounded-lg flex items-center justify-center text-sm font-medium transition-all duration-200 border ${
                        page === num
                            ? 'bg-blue-600 border-blue-600 text-white shadow-lg shadow-blue-500/25'
                            : 'bg-slate-900 border-white/5 text-slate-400 hover:bg-slate-800 hover:text-white hover:border-white/10'
                    }`}
                    onClick={() => setPage(num)}
                >
                    {num}
                </button>
            ))}
        </div>
    );
}
