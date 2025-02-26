function Footer() {
    return (
        <footer className="bg-black text-white py-10 border-t border-gray-700">
            <div className="container mx-auto flex flex-col md:flex-row justify-between items-start px-6 gap-8">
                
                {/* 左侧 Logo & 版权信息 */}
                <div>
                    <h1 className="text-2xl font-extrabold">Course<span className="text-blue-400">Hunter</span></h1>
                    <p className="text-gray-400 mt-2">&copy; 2024 Course Hunter Team. All rights reserved.</p>
                </div>

                {/* 右侧 链接 & 联系方式 */}
                <div className="flex flex-col space-y-2">
                    <a href="#" className="text-gray-400 hover:text-blue-400 text-sm">Privacy Policy</a>
                    <a href="#" className="text-gray-400 hover:text-blue-400 text-sm">Terms & Conditions</a>
                    <a href="#" className="text-gray-400 hover:text-blue-400 text-sm">Contact Us</a>
                    <p className="text-gray-400 text-sm mt-2">📞 +355 09221841</p>
                    <p className="text-gray-400 text-sm">📍 94 Donunto, Block 4, Galway</p>
                </div>
            </div>
        </footer>
    );
}

export default Footer;
