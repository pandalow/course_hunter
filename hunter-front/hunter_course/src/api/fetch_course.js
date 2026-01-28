import api from './axios'

export const fetchCourses = async (page=1,pageSize=5,search='') => {
    const response = await api.get('/course',{
        params: {
            page: page,
            size: pageSize,
            search: search
        }
    })
    
    const data = response.data.data.records
    return data
}

export const searchCourses = async (query) => {
    const response = await api.get('/course/find',{
        params: {
            query: query
        }
    })
    const data = response.data.data.records
    return data
}


export const fetchCourseById = async (id) => {
    const response = await api.get(`/courses/${id}`)
    return response.data.data
}
