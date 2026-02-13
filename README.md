### Schema Diagram
![Schema Diagram](https://github.com/sakshi2215/javaEcommerce/blob/main/SchemaDiagram.png)
### APIS LIST:
![API LIST](https://github.com/sakshi2215/javaEcommerce/blob/main/image.png)
![API LIST](https://github.com/sakshi2215/javaEcommerce/blob/main/Screenshot%202026-02-13%20193512.png)

## Validation & Exception Handling

### Bean Validation

- Implemented using Jakarta Bean Validation.
- Applied on Request DTOs.
- Triggered using `@Valid` in controllers.
- Prevents invalid data from reaching service layer.
- Automatically throws validation exceptions for invalid input.

## Global Exception Handler

- Implemented using `@ControllerAdvice`.
- Centralized error handling.
- Handles:
  - ResourceAlreadyExistsException
  - IllegalArgumentException
  - Validation exceptions
  - Generic exceptions
- Returns consistent structured JSON error responses.
- Removes need for try-catch blocks in controllers.

### HTTP Response Handling

- Controllers use `ResponseEntity` to return proper HTTP status codes along with response bodies.
- This ensures standardized REST responses (e.g., 200 OK, 201 Created, 204 No Content, 400 Bad Request, 404 Not Found).
  
### Currency API LOGIC:

1. **Accepts a base currency**
   Example: `"INR"`
2. **Builds API URL dynamically**
   ```java
   https://apiUrl/apiKey/latest/INR
   ```
3. **Calls external Currency API** using `RestTemplate`
   ```java
   restTemplate.getForObject(url, Map.class);
   ```
4. **Checks if API response is successful**
   ```java
   "result" = "success"
   ```
5. **Extracts conversion rates**
   From:
   ```json
   "conversion_rates": { "USD": 0.012 }
   ```
6. **Gets USD rate** (target currency is always USD)
7. **Returns the rate**
   So you can do:
   ```
   amount_in_usd = amount * rate
   ```

### `registerUser` Method

**Endpoint:** `POST /users/register` 
**Parameter Expecting:**
```json
{
  "name": "string",
  "email": "string",
  "password": "string"
}
```
Logic:
1. Check if email already exists → throw ResourceAlreadyExistsException if yes.
2. Create new User entity from request.
3. Save User to repository.
4. Create and save a Cart for the user.
5. Return UserResponse.

**Response:**
```json
{
  "id": "long",
  "name": "string",
  "email": "string",
  "createdAt": "ISO_LOCAL_DATE_TIME"
}
```
### `createCategory` Method

**Endpoint:** `POST /categories` 
**Parameter Expecting:**

```json
{
  "name": "string",
  "description": "string"
}
```
Logic:
1. Check if category name already exists otherwise throw ResourceAlreadyExistsException if yes.
2. Create new Category entity from request.
3. Save Category to repository.
4. Return CategoryResponse.

**Response:**
```
{
  "id": "long",
  "name": "string",
  "description": "string"
}
```
### `createProduct` Method

**Endpoint:** `POST /products`
**Parameter Expecting:**

```json
{
  "name": "string",
  "description": "string",
  "price": "double",
  "stockQuantity": "int",
  "categoryId": "long"
}
```
Logic:
1. Fetch category by categoryId else throw ResourceNotFoundException if not found.
2. Validate: price must be greater than 0. stockQuantity cannot be negative.
3. Create new Product entity.
4. Save Product to repository.
5. Return ProductResponse.

**Response:**
```{
  "id": "long",
  "name": "string",
  "description": "string",
  "price": "double",
  "stockQuantity": "int",
  "categoryName": "string"
}
```
### `updateStock` Method
**Endpoint:** `PATCH /products/{productId}/stock`

**Parameter Expecting:**
 Path Variable: productId (long)
**Request Body:**
```
{
  "newStockQuantity": "int"
}
```

Logic:
1. Fetch product by productId , throw ResourceNotFoundException if not found.
2. Validate newStockQuantity (cannot be negative).
3. Update product stock.
4. Save updated product.
5. Return updated ProductResponse.

**Response:**
```
{
  "id": "long",
  "name": "string",
  "description": "string",
  "price": "double",
  "stockQuantity": "int",
  "categoryName": "string"
}
```
### `addToCart` Method

**Endpoint:** `POST /users/{userId}/cart` *(example)*
**Parameter Expecting:**

- Path Variable:
  - `userId` (long)
- Request Body:

```json
{
  "productId": "long",
  "quantity": "int"
}
```

Logic:
1. Validate quantity > 0.
2. Fetch user ,throw ResourceNotFoundException if not found.
3. Fetch cart (create if not exists).
4. Fetch product , throw ResourceNotFoundException if not found.
5. Validate stock availability.
6. If product already exists in cart , increase quantity (validate stock again).
Else, create new CartItem.
7. Update cart updatedAt.
8. Return CartResponse.

**Response:**
```
{
  "cartId": "long",
  "userId": "long",
  "items": [
    {
      "cartItemId": "long",
      "productId": "long",
      "productName": "string",
      "quantity": "int",
      "price": "double",
      "subtotal": "double"
    }
  ],
  "totalAmount": "double",
  "updatedAt": "ISO_LOCAL_DATE_TIME"
}
```
### viewCart Method

**Endpoint:** ```GET /users/{userId}/cart``` 

**Parameter Expecting:**
Path Variable: userId (long)

Logic:
1. Fetch user , throw ResourceNotFoundException if not found.
2. Fetch cart , throw ResourceNotFoundException if not found.
3. Return CartResponse.

**Response:**
Same as addToCart response structure.

### removeCartItem Method

**Endpoint:** `DELETE /users/{userId}/cart/{productId}` 
**Parameter Expecting:**
Path Variables: userId (long), productId (long)

Logic:
1. Fetch user , throw ResourceNotFoundException if not found.
2. Fetch cart , throw ResourceNotFoundException if not found.
3. Fetch product , throw ResourceNotFoundException if not found.
4. Find CartItem , throw ResourceNotFoundException if not in cart.
5. Delete cart item.

**Response:** No content (204 No Content).


### `checkout` Method

**Endpoint:** `POST /orders/checkout` 
**Parameter Expecting:**

```json
{
  "userId": "long",
  "currency": "string"
}
```

Logic:
1. Fetch cart by userId, throw ResourceNotFoundException if not found.
2. Validate cart is not empty.
3. Validate stock for each cart item.
4. Deduct product stock.
5. Calculate total amount.
6. Convert total amount using currency conversion service.
7. Create and save Order.
8. Create and save OrderItem entries.
9. Clear cart items.
10. Return OrderResponse.

**Response:**
```
{
  "orderId": "long",
  "orderDate": "ISO_LOCAL_DATE_TIME",
  "status": "string",
  "currency": "string",
  "totalAmount": "double",
  "convertedAmount": "double",
  "items": [
    {
      "productId": "long",
      "productName": "string",
      "quantity": "int",
      "price": "double",
      "subtotal": "double"
    }
  ]
}
```
### getAllOrders Method

**Endpoint:** `GET /users/{userId}/orders`

**Parameter Expecting**: 
Path Variable: userId (long)

Logic:
1. Fetch all orders by userId.
2. Map each order and its items to OrderResponse.
3. Return list of OrderResponse.

**Response:**
```
[
  {
    "orderId": "long",
    "orderDate": "ISO_LOCAL_DATE_TIME",
    "status": "string",
    "currency": "string",
    "totalAmount": "double",
    "convertedAmount": "double",
    "items": [
      {
        "productId": "long",
        "productName": "string",
        "quantity": "int",
        "price": "double",
        "subtotal": "double"
      }
    ]
  }
]
```
