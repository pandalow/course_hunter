import api from './axios'

// Google Register and login api, Transfer google credential, return the token and user info;
const googleLogin = async (resp) => {
    try {
        const response = await api.post(
            '/auth/login/google', {
            credential: resp.credential
        }, {
            headers: {
                'Content-Type': 'application/json',
            }
        })

        const { token, user } = response.data;
        localStorage.setItem('app_token', token);
        return response.data;
    } catch (error) {
        console.log("Google login error", error);
        throw error;
    }
}

export default googleLogin;