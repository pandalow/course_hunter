import api from './axios';

/**
 * Get ratings for a specific course
 * @param {number} courseId - The ID of the course
 * @returns {Promise<Array>} List of ratings
 */
export const getRatings = async (courseId) => {
    const response = await api.get(`/ratings/course/${courseId}`);
    return response.data.data;
};

/**
 * Create a new rating
 * @param {Object} ratingData - { targetId, targetType, score, content }
 * @returns {Promise<Object>} Created rating
 */
export const createRating = async (ratingData) => {
    const response = await api.post('/ratings', ratingData);
    return response.data.data;
};

/**
 * Update an existing rating
 * @param {Object} ratingData - { id, targetId, targetType, score, content }
 * @returns {Promise<Object>} Updated rating
 */
export const updateRating = async (ratingData) => {
    const response = await api.put('/ratings', ratingData);
    return response.data.data;
};

/**
 * Delete a rating by ID
 * @param {number} ratingId - The ID of the rating to delete
 * @returns {Promise<string>} Success message
 */
export const deleteRating = async (ratingId) => {
    const response = await api.delete(`/ratings/${ratingId}`);
    return response.data.data;
};
