# Api Specification

## Entity

- Admin
- CategoryPet
- Employee
- MedicinePet
- Pet
- User

### baseurl -> localhost:8080/api/v1

## Api Spec Entity

1. ## Admin
    - Post
        - {baseurl}/secured/admin/add
    ```json
           { 
           "username" : "string.uniq",
           "password" : "string",
           "address"  : "string",
           "email"    : "string.uniq" 
           } 
    ```
   - Delete
     - {baseurl}/secured/admin/delete
   ```json
           { 
           "key" : "integer"
           } 
    ```
   - Put
   - {baseurl}/secured/admin/update
   ```json
           { 
           "id"       : "integer",
           "username" : "string.uniq",
           "password" : "string",
           "address"  : "string",
           "email"    : "string.uniq" 
           } 
    ```
   - Get
   - {baseurl}/secured/admin/all
2. CategoryPet
3. Employee
4. MedicinePet
5. Pet
6. User


### start at feb 23 2023