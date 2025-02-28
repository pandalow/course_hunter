import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { signIn } from "../../service/fetch_course";

export default function SignIn() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleSignIn = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError(null);

        try {
            const response = await signIn(email, password);

            const result = await response.data;

            if (response.ok && result.code === 1) {
                localStorage.setItem("token", result.data.token); // Store token
                navigate("/"); // Redirect to home
            } else {
                throw new Error(result.msg || "Invalid credentials.");
            }
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="flex items-center justify-center min-h-screen bg-black">
            <div className="w-full max-w-md p-6 bg-gray-900 text-white rounded-lg shadow-lg">
                <h2 className="text-3xl font-bold text-center text-blue-400">Sign In</h2>

                {error && <p className="mt-3 text-red-400 text-center">{error}</p>}

                <form className="mt-6 space-y-4" onSubmit={handleSignIn}>
                    <div>
                        <label className="block text-gray-300">Email</label>
                        <input
                            type="email"
                            required
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            className="w-full p-2 mt-1 text-black rounded-lg focus:ring focus:ring-blue-400"
                            placeholder="Enter your email"
                        />
                    </div>

                    <div>
                        <label className="block text-gray-300">Password</label>
                        <input
                            type="password"
                            required
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            className="w-full p-2 mt-1 text-black rounded-lg focus:ring focus:ring-blue-400"
                            placeholder="Enter your password"
                        />
                    </div>

                    <button
                        type="submit"
                        className="w-full px-4 py-2 font-bold text-white bg-blue-500 rounded-lg hover:bg-blue-600 transition duration-300"
                        disabled={loading}
                    >
                        {loading ? "Signing In..." : "Sign In"}
                    </button>
                </form>

                <p className="mt-4 text-center text-gray-400">
                    Don't have an account?{" "}
                    <a href="/register" className="text-blue-400 hover:underline">
                        Sign Up
                    </a>
                </p>
            </div>
        </div>
    );
}
