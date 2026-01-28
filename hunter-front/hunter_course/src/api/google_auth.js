import api from './axios'

// Google Register and login api, Transfer google credential, return the token and user info;
export const googleLogin = async (resp) => {
    try {
        const response = await api.post(
            '/auth/login/google', {
            credential: resp.credential
        }, {
            headers: {
                'Content-Type': 'application/json',
            }
        })
        console.log(response)
        const data = response.data.data;
        localStorage.setItem('app_token', data.token);
        return data;
    } catch (error) {
        console.log("Google login error", error);
        throw error;
    }
}

export const authMe = async () => {
    try {
        const response = await api.get('/auth/me');
        return response.data.data;
    } catch (error) {
        console.log("Auth me error", error);
        throw error;
    }
}   


