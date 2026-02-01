import api from './axios'

export const fetchCourses = async (page=1, pageSize=12, sortBy='id', sortDirection='desc') => {
    const response = await api.get('/course', {
        params: {
            page: page,
            pageSize: pageSize,
            sortBy: sortBy,
            sortDirection: sortDirection
        }
    })
    
    const data = response.data.data;
    return data.content || data.records || data;
}

export const searchCourses = async (query) => {
    const response = await api.get('/course/find', {
        params: {
            query: query
        }
    })
    const data = response.data.data;
    return data.content || data.records || data;
}


export const fetchCourseById = async (id) => {
    const response = await api.get(`/course/${id}`)
    return response.data.data
}
