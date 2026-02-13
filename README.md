"# javaEcommerce" 
![Schema Diagram](https://github.com/sakshi2215/javaEcommerce/blob/main/SchemaDiagram.png)
![API LIST](https://github.com/sakshi2215/javaEcommerce/blob/main/image.png)
![API LIST](https://github.com/sakshi2215/javaEcommerce/blob/main/Screenshot%202026-02-13%20193512.png)


### `registerUser` Method

**Endpoint:** `POST /users/register` *(example)*
**Parameter Expecting:**
```json
{
  "name": "string",
  "email": "string",
  "password": "string"
}
Logic:
-Check if email already exists → throw ResourceAlreadyExistsException if yes.
-Create new User entity from request.
-Save User to repository.
-Create and save a Cart for the user.
-Return UserResponse.
**Response:**
{
  "id": "long",
  "name": "string",
  "email": "string",
  "createdAt": "ISO_LOCAL_DATE_TIME"
}

### `createCategory` Method

**Endpoint:** `POST /categories` *(example)*
**Parameter Expecting:**

```json
{
  "name": "string",
  "description": "string"
}
Logic:
-Check if category name already exists otherwise throw ResourceAlreadyExistsException if yes.
-Create new Category entity from request.
-Save Category to repository.
-Return CategoryResponse.

Response:

{
  "id": "long",
  "name": "string",
  "description": "string"
}
