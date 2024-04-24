This project was made in Jakarta EE 10.0 version with REST service template written in java 17


## 1. Used Dependencies

- CDI
- JPA
- EJB
- JAX-RS
- Servlet
- Hibernate
- Mariadb
- Mockito

## 2. Resource Access Points

### POST /api/createPerson

Creates a Person entity, saves it into db and send it back to client as json

#### Sample Request
```json
{
    "name" : "John Doe",
    "gender" : "MALE",
    "birthday" : "05.2.2024"
}
```

#### Sample Response
```json
{
    "birthday": "05.02.2024",
    "gender": "MALE",
    "id": 1,
    "name": "John Doe"
}
```

### GET /api/getAllPeople 

Gets all the Person entities from db and send them as json

#### Sample Response
```json
{
    "people": [
        {
            "birthday": "05.02.2024",
            "gender": "MALE",
            "id": 1,
            "name": "John Doe"
        },
        {
            "birthday": "05.02.2024",
            "gender": "FEMALE",
            "id": 2,
            "name": "Jane Doe"
        }
    ]
}
```

### DELETE /api/deletePerson

Deletes Person entity from db based on provided personId

#### Sample Request
```json
{
   "personId" : 2
}
```

#### Sample Response
```json
{
    "message": "Person was successfully deleted"
}
```

### GET /api/getPersonByName

Gets singular Person from db and send it as json. If there are more people by the same name
use /api/getPeopleByName

#### Sample Request
```json
{
    "name" : "John Doe"
}
```

#### Sample Response
```json
{
    "birthday": "05.02.2024",
    "gender": "MALE",
    "id": 2,
    "name": "John Doe"
}
```

### GET /api/getPeopleByName

Gets all Person entities by the same name and send them as json

#### Sample Request
```json
{
    "name" : "John Doe"
}
```

#### Sample Response
```json
{
    "people": [
        {
            "birthday": "05.02.2024",
            "gender": "MALE",
            "id": 2,
            "name": "John Doe"
        },
        {
            "birthday": "05.02.2024",
            "gender": "MALE",
            "id": 3,
            "name": "John Doe"
        }
    ]
}
```

### PUT /api/updatePerson

Updates name of Person with the provided personId

#### Sample Request
```json
{
    "name" : "John Smith",
    "personId" : 2
}
```

#### Sample Request
```json
{
    "message": "Person was updated successfully"
}
```

## 3. Exceptions

Application also has various validations for incoming requests with custom error messages

### POST /api/createPerson

#### Sample Request
```json
{
    "name" : "John Doe123",
    "gender" : "MALE",
    "birthday" : "05.2.2024"
}
```

#### Sample Response
```json
{
  "errorMessage": "Name cannot contain numbers or symbols"
}
```
