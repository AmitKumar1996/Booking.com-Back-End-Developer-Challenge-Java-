# Booking.com-Back-End-Developer-Challenge-Java-

# Hotel Inventory API

This project provides APIs for managing and retrieving hotel information.

---

## 1. Get Hotel Details by ID

### 📌 Problem Statement
As a consumer of Hotel inventory, I want to retrieve individual hotel details so I can display them to my customers.

### 📝 API Contract

**Request:**
```
GET /hotel/{id}
```

**Response:**
```json
{
  "id": integer,
  "name": string,
  "rating": decimal,
  "latitude": decimal,
  "longitude": decimal,
  "address": string,
  "city": {
    "id": integer,
    "name": string,
    "cityCentreLatitude": decimal,
    "cityCentreLongitude": decimal
  }
}
```

**Example Request:**
```
GET /hotel/10
```

**Example Response:**
```json
{
  "id": 10,
  "name": "The Bank Hotel Amsterdam",
  "rating": 9.2,
  "latitude": 52.37276,
  "longitude": 4.893604,
  "address": "10 Bank Street, 10110 AB",
  "city": {
    "id": 1,
    "name": "Amsterdam",
    "cityCentreLatitude": 52.36878,
    "cityCentreLongitude": 4.903303
  }
}
```

---

## 2. Delete a Hotel by ID

### 📌 Problem Statement
As a maintainer of Hotel inventory, I want to delete an individual hotel so it's no longer available for display.

> ⚠️ This is a **logical delete**, meaning the hotel is not removed from the database but marked as deleted.

### 📝 API Contract

**Request:**
```
DELETE /hotel/{id}
```

**Response:**
```
HTTP 200 OK
```

**Example Request:**
```
DELETE /hotel/10
```

> 💡 **Tip:** Think about the proper HTTP status codes and how deleting a hotel affects other endpoints like `GET /hotel/{id}` and search functionalities.

---

## 3. Find the 3 Hotels Closest to the City Centre

### 📌 Problem Statement
As a user of Booking.com search functionality, I want to find the top 3 hotels closest to the city centre so I can choose the most convenient option.

> 🔍 Use the **Haversine formula** to calculate the distance between two geographic points. You can use a 3rd party utility/library for this.

### 📝 API Contract

**Request:**
```
GET /search/{cityId}?sortBy=distance
```

**Response:**
Returns up to **3 hotels**, sorted by distance to the city centre.

```json
[
  {
    "id": integer,
    "name": string,
    "rating": decimal,
    "latitude": decimal,
    "longitude": decimal,
    "address": string,
    "city": {
      "id": integer,
      "name": string,
      "cityCentreLatitude": decimal,
      "cityCentreLongitude": decimal
    }
  }
]
```

**Example Request:**
```
GET /search/1?sortBy=distance
```

**Example Response:**
```json
[
  {
    "id": 10,
    "name": "The Bank Hotel Amsterdam",
    "rating": 9.2,
    "latitude": 52.37276,
    "longitude": 4.893604,
    "address": "10 Bank Street, 10110 AB",
    "city": {
      "id": 1,
      "name": "Amsterdam",
      "cityCentreLatitude": 52.36878,
      "cityCentreLongitude": 4.903303
    }
  }
]
```

---

