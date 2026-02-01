**Base URL**: `/auth`

### 1. Google Login

Authenticates a user via Google OAuth and returns a JWT token along with user information.

* **URL**: `/login/google`
* **Method**: `POST`
* **Authentication**: Not Required
* **Request Body (JSON)**:
  | Field | Type | Required | Description |
  | :--- | :--- | :--- | :--- |
  | `credential` | String | Yes | The ID token obtained from Google after user authentication. |

* **Sample Request**:
```json
{
  "credential": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjFkYzBmM..."
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
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "avatar": "https://lh3.googleusercontent.com/a/...",
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiVXNlciI..."
  }
}
```

* **Error Response**:
  * **Code**: `401 Unauthorized`
  * **Reason**: Invalid Google token or authentication failure

---

### 2. Get Current User

Retrieves the currently authenticated user's information based on the JWT token provided in the Authorization header.

* **URL**: `/me`
* **Method**: `GET`
* **Authentication**: **Required** (Bearer Token)
* **Headers**:
  * `Authorization: Bearer {token}`

* **Success Response**:
* **Code**: `200 OK`
* **Sample Payload**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "avatar": "https://lh3.googleusercontent.com/a/...",
    "googleId": "1234567890",
    "role": "User"
  }
}
```

* **Error Response**:
  * **Code**: `401 Unauthorized`
  * **Reason**: Missing or invalid token
  * **Code**: `403 Forbidden`
  * **Reason**: Token expired or user not found

---

### Authentication Flow

1. User signs in with Google on the frontend
2. Frontend receives Google ID token
3. Frontend sends the token to `/auth/login/google`
4. Backend validates the token with Google
5. Backend creates/updates user record and generates JWT
6. Frontend stores JWT token in localStorage
7. Frontend includes JWT in Authorization header for subsequent requests

### Token Usage

Include the JWT token in the Authorization header for all authenticated requests:

```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiVXNlciI...
```

The token contains:
- User's Google ID (subject)
- User's role
- Expiration time
- Other claims for authorization