
function Footer() {
    return (
        <footer className="mt-32 bg-stone-900 flex justify-between items-center pl-48 pr-64 h-60">
            <div className="flex flex-col items-start">
                <ul>
                    <li>
                        <h1 className="text-white text-2xl font-bold">Hunter Course</h1>
                    </li>
                    <li>
                        <p className="text-white">&copy; 2024 Rent Helper Team. All rights reserved</p>
                    </li>
                </ul>
            </div>
            <div className="text-right">
                <ul className="space-y-4">
                    <li>
                        <ul className="space-y-2 mt-2">
                            <li className="text-white text-sm">Privacy Policy</li>
                            <li className="text-white  text-sm">Terms&Conditions</li>
                            <li className="text-white  text-sm pt-2">Contact Us</li>
                            <li className='text-white text-sm'>+355 09221841</li>
                            <li className='text-white text-sm'>94 Donunto, Block4, Galway</li>
                        </ul>
                    </li>
                </ul>
            </div>
        </footer>
    )
}

export default Footer;
