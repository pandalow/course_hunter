**Base URL**: `/comment`

### 1. Create a New Comment

Submits a user comment for a specific target (Course or Teacher). The system automatically identifies the author via the active Security Session (Google ID).

* **URL**: `/`
* **Method**: `POST`
* **Authentication**: **Required** (Bearer Token/Session)
* **Request Body (JSON)**:
  | Field | Type | Required | Description |
  | :--- | :--- | :--- | :--- |
  | `content` | String | Yes | The text content of the comment. Supports `TEXT` length. |
  | `targetId` | Long | Yes | The ID of the Course or Teacher being reviewed. |
  | `targetType` | String | Yes | The category of the target. Values: `1` (Course), `2` (Teacher). |
* **Sample Request**:
```json
{
  "content": "The lecturer explained the complex algorithms very clearly.",
  "targetId": 50,
  "targetType": "2"
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
    "id": 5021,
    "content": "The lecturer explained the complex algorithms very clearly.",
    "createTime": "2024-05-20T14:30:00Z"
  }
}

```



---

### 2. Delete a Comment

Performs a deletion of a specific comment by its ID.

* **URL**: `/{id}`
* **Method**: `DELETE`
* **Authentication**: **Required**
* **Path Parameters**:
* `id` (Long, Required): The unique identifier of the comment to be removed.


* **Success Response**:
* **Code**: `200 OK`
* **Sample Payload**:
```json
{
  "code": 1,
  "msg": "success",
  "data": "Comment deleted successfully"
}

```





