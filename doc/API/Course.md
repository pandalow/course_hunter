**Base URL**: `/course`

### 1. Retrieve Course by ID

Fetches the full details of a specific course entity. Use this for the "Course Detail" page.

* **URL**: `/{id}`
* **Method**: `GET`
* **Path Parameters**:
* `id` (Long, Required): The unique identifier of the course.


* **Success Response**:
* **Code**: `200 OK`
* **Sample Payload**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 101,
    "title": "Artificial Intelligence",
    "code": "CS402",
    "semester": "Autumn",
    "credits": 5,
    "institutionName": "University College Dublin",
    "countryName": "Ireland",
    "avgScore": 4.7,
    "commentCount": 24
  }
}

```





---

### 2. Get Paginated Course List

Retrieves a list of course cards for the home page. Includes aggregated data like average ratings and the latest comment snippets.

* **URL**: `/`
* **Method**: `GET`
* **Query Parameters**:

  | Parameter | Type | Default | Description |
  | :--- | :--- | :--- | :--- |
  | `page` | Integer | 1 | Page number (starts at 1). |
  | `pageSize` | Integer | 12 | Number of records per page. |
  | `sortDirection` | String | "asc" | Sorting order: `asc` or `desc`. |
  | `sortBy` | String | "commentsCount" | Field to sort by (e.g., `avgScore`, `title`). |

* **Success Response**:
* **Code**: `200 OK`
* **Sample Payload**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "totalElements": 150,
    "content": [
      {
        "id": 1,
        "title": "Software Engineering",
        "teachers": ["Dr. Alice", "Prof. Bob"],
        "rating": 4.5,
        "comments": "Very practical and hands-on!",
        "institutionName": "UCD",
        "countryName": "Ireland"
      }
    ]
  }
}

```





---

### 3. Semantic Search

Performs an AI-powered semantic search via the FastAPI integration. Returns courses ranked by relevance to the query string.

* **URL**: `/find`
* **Method**: `GET`
* **Query Parameters**:
* `query` (String, Required): The natural language search term (e.g., "best python coding classes").


* **Success Response**:
* **Code**: `200 OK`
* **Note**: The response structure is identical to the **Paginated Course List**, but results are sorted by relevance rather than database fields.

