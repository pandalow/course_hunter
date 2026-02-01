import api from './axios';

/**
 * Get comments for a specific target (Course or Teacher)
 * @param {number} targetId - The ID of the target
 * @param {string} targetType - "1" for Course, "2" for Teacher
 * @returns {Promise<Array>} List of comments
 */
export const getComments = async (targetId, targetType) => {
    const response = await api.get('/comment', {
        params: {
            targetId,
            targetType
        }
    });
    return response.data.data;
};

/**
 * Create a new comment
 * @param {Object} commentData - { content, targetId, targetType }
 * @returns {Promise<Object>} Created comment
 */
export const createComment = async (commentData) => {
    const response = await api.post('/comment', commentData);
    return response.data.data;
};

/**
 * Delete a comment by ID
 * @param {number} commentId - The ID of the comment to delete
 * @returns {Promise<string>} Success message
 */
export const deleteComment = async (commentId) => {
    const response = await api.delete(`/comment/${commentId}`);
    return response.data.data;
};
