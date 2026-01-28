import axios from 'axios';

const api = axios.create({
    baseURL: '/',
    timeout: 5000,
})


api.interceptors.request.use((config) => {
    const token = localStorage.getItem('app_token')

    if (token) {
        config.headers.Authorization = `Bearer ${token}`
    }
    return config;
},
    (error) => {
        return Promise.reject(error)
    }
)

api.interceptors.response.use((response)=>response,
    (error)=>{
        if(error.response && error.response.status === 401){
            console.log("Login Expired")
            localStorage.removeItem('app_token')
            window.location.href = '/login'
        }
        return Promise.reject(error)
    }
)


export default api;