##  CANCEL-EVENT-BASIC-SUCCESS
1. The user can cancel any event. <br>
2. The system must ask user for confirmation. <br>
3. The event is deleted from database after the operation. <br>
```
http://localhost:8080/api/events/{{eventId}}
```
### If you enter ID that does not exist in the database.
```
{
    "timestamp": "2022-09-11T04:26:44.593+00:00",
    "status": 400,
    "error": "Bad Request",
    "message": "ID : 2 Does not exit !!!",
    "path": "/api/events/2"
}
```
### If you enter ID that does exist in the database.
```
{
    "timestamp": "2022-09-11T04:30:31.737+00:00",
    "status": 200,
    "error": "OK",
    "message": "Delete Successful",
    "path": "/api/events/3"
}
```
