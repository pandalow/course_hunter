import axios from 'axios'


export const fetchCourses = async (page=1,pageSize=5,search='') => {
    const response = await axios.get('http://localhost:9999/course',{
        params: {
            page: page,
            size: pageSize,
            search: search
        }
    })
    
    const data = response.data.data.records
    return data
}

// mock data
// export const fetchCourses = async () => {
//     const response = {
//         data: [
//             {
//                 id: 1,
//                 name: 'Course 1',
//                 description: 'Description 1',
//                 rating: 4.5,
//                 comments: 'This is a mock value,This is a mock valueThis ',
//                 institutionName: 'Institution 1',
//                 countryName: 'Country 1',
//                 teachers: ['Teacher 1', 'Teacher 2', 'Teacher 3'],
//                 image: 'https://via.placeholder.com/150',
//             },
//             {
//                 id: 2,
//                 name: 'Course 2',
//                 description: 'Description 2',
//                 rating: 4.5,
//                 comments: 'This is a mock value, This is a mock valueThis is a mock valueThis is a mock valueThis is a mock value',
//                 institutionName: 'Institution 2',
//                 countryName: 'Country 2',
//                 teachers: ['Teacher 1', 'Teacher 2', 'Teacher 3'],
//                 image: 'https://via.placeholder.com/150',
//             }
//         ]
//     }
//     return response.data
// }



export const fetchCourseById = async (id) => {
    const response = await axios.get(`http://localhost:8080/courses/${id}`)
    return response.data
}
