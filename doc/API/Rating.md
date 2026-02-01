**Base URL**: `/ratings`

### 1. Create a New Rating

Submits a user rating (score and review) for a specific target (Course or Teacher). The system automatically identifies the author via the active Security Session (Google ID).

* **URL**: `/`
* **Method**: `POST`
* **Authentication**: **Required** (Bearer Token/Session)
* **Request Body (JSON)**:
  | Field | Type | Required | Description |
  | :--- | :--- | :--- | :--- |
  | `targetId` | Long | Yes | The ID of the Course or Teacher being rated. |
  | `targetType` | String | Yes | The category of the target. Values: `"1"` (Course), `"2"` (Teacher). |
  | `score` | Integer | Yes | Rating score from 1 to 5. |
  | `content` | String | No | Optional review text to accompany the rating. |

* **Sample Request**:
```json
{
  "targetId": 101,
  "targetType": "1",
  "score": 5,
  "content": "Excellent course! The content was well-structured and the professor was very engaging."
}
```

* **Success Response**:
* **Code**: `200 OK`
* **Sample Payload**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 2501,
    "targetId": 101,
    "targetType": "1",
    "score": 5,
    "content": "Excellent course! The content was well-structured and the professor was very engaging.",
    "userId": 15,
    "userName": "John Doe",
    "userAvatar": "https://lh3.googleusercontent.com/a/...",
    "createTime": "2024-05-20T14:30:00Z"
  }
}
```

* **Error Response**:
  * **Code**: `400 Bad Request`
  * **Reason**: Invalid score (not between 1-5) or missing required fields
  * **Code**: `401 Unauthorized`
  * **Reason**: User not authenticated
  * **Code**: `409 Conflict`
  * **Reason**: User has already rated this target (use update instead)

---

### 2. Update an Existing Rating

Updates a user's existing rating for a specific target. Users can only update their own ratings.

* **URL**: `/`
* **Method**: `PUT`
* **Authentication**: **Required**
* **Request Body (JSON)**:
  | Field | Type | Required | Description |
  | :--- | :--- | :--- | :--- |
  | `id` | Long | Yes | The ID of the rating to update. |
  | `targetId` | Long | Yes | The ID of the Course or Teacher. |
  | `targetType` | String | Yes | The category of the target. Values: `"1"` (Course), `"2"` (Teacher). |
  | `score` | Integer | Yes | Updated rating score from 1 to 5. |
  | `content` | String | No | Updated review text. |

* **Sample Request**:
```json
{
  "id": 2501,
  "targetId": 101,
  "targetType": "1",
  "score": 4,
  "content": "Updated review: Still a great course, but the workload was heavy."
}
```

* **Success Response**:
* **Code**: `200 OK`
* **Sample Payload**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 2501,
    "targetId": 101,
    "targetType": "1",
    "score": 4,
    "content": "Updated review: Still a great course, but the workload was heavy.",
    "userId": 15,
    "userName": "John Doe",
    "userAvatar": "https://lh3.googleusercontent.com/a/...",
    "createTime": "2024-05-20T14:30:00Z",
    "updateTime": "2024-05-21T10:15:00Z"
  }
}
```

* **Error Response**:
  * **Code**: `403 Forbidden`
  * **Reason**: User attempting to update another user's rating
  * **Code**: `404 Not Found`
  * **Reason**: Rating with specified ID not found

---

### 3. Delete a Rating

Performs a deletion of a specific rating by its ID. Users can only delete their own ratings.

* **URL**: `/{id}`
* **Method**: `DELETE`
* **Authentication**: **Required**
* **Path Parameters**:
  * `id` (Long, Required): The unique identifier of the rating to be removed.

* **Success Response**:
* **Code**: `200 OK`
* **Sample Payload**:
```json
{
  "code": 1,
  "msg": "success",
  "data": "Delete Success"
}
```

* **Error Response**:
  * **Code**: `403 Forbidden`
  * **Reason**: User attempting to delete another user's rating
  * **Code**: `404 Not Found`
  * **Reason**: Rating with specified ID not found

---

### 4. Get Ratings for a Course

Retrieves all ratings for a specific course, including user information and review content.

* **URL**: `/course/{courseId}`
* **Method**: `GET`
* **Authentication**: Not Required (Public endpoint)
* **Path Parameters**:
  * `courseId` (Long, Required): The unique identifier of the course.

* **Success Response**:
* **Code**: `200 OK`
* **Sample Payload**:
```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "id": 2501,
      "targetId": 101,
      "targetType": "1",
      "score": 5,
      "content": "Excellent course! The content was well-structured and the professor was very engaging.",
      "userId": 15,
      "userName": "John Doe",
      "userAvatar": "https://lh3.googleusercontent.com/a/...",
      "createTime": "2024-05-20T14:30:00Z"
    },
    {
      "id": 2502,
      "targetId": 101,
      "targetType": "1",
      "score": 4,
      "content": "Very informative but quite challenging.",
      "userId": 28,
      "userName": "Jane Smith",
      "userAvatar": "https://lh3.googleusercontent.com/a/...",
      "createTime": "2024-05-19T09:20:00Z"
    }
  ]
}
```

* **Notes**:
  * Results are typically ordered by creation time (newest first)
  * Empty array is returned if no ratings exist for the course
  * This endpoint can be used to calculate average ratings on the client side

---

### Rating Guidelines

**Score Scale:**
* `5` - Excellent
* `4` - Good
* `3` - Average
* `2` - Below Average
* `1` - Poor

**Best Practices:**
* Users should rate courses based on their actual experience
* Review content should be constructive and relevant
* One rating per user per course (use update to change rating)
* Ratings contribute to the course's overall average score

**Target Types:**
* `"1"` - Course
* `"2"` - Teacher

### Usage Example

**Frontend Flow:**
1. User views course details
2. System checks if user has already rated (GET ratings, filter by userId)
3. If rating exists, show "Update Rating" form with current values
4. If no rating, show "Submit Rating" form
5. On submit, use POST (new) or PUT (update) accordingly
6. Update UI with new average rating
