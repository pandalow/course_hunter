export default function PageNumber({ page, setPage }) {
    const totalPages = 10;
    const pageNumbers = Array.from({ length: totalPages }, (_, i) => i + 1);

    return (
        <div className="flex space-x-2 mt-6">
            {pageNumbers.map((num) => (
                <button
                    key={num}
                    className={`px-4 py-2 rounded-lg font-semibold transition duration-300 ${
                        page === num
                            ? 'bg-blue-500 text-white shadow-md'
                            : 'bg-gray-800 text-gray-300 hover:bg-gray-700'
                    }`}
                    onClick={() => setPage(num)}
                >
                    {num}
                </button>
            ))}
        </div>
    );
}
