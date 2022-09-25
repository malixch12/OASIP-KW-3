## VIEW-EVENT-LIST-ALL
1. There are scheduled events. <br>
2. Display all scheduled events. <br>
3. In each event, display : event date, event start time, event duration (in minutes), eventCategoryName, bookingName <br>
4. The list is sorted in descending order on event date/time. <br>
```
http://localhost:8080/api/events
```
```
{
    "content": [
        {
            "eventNotes": null,
            "eventCategory": "Database Clinic",
            "eventStartTime": "2022-05-23T09:30:00Z",
            "eventDate": "2022-05-23",
            "eventCategoryID": 3,
            "eventDuration": 30,
            "bookingName": "สมเกียรติ ขยันเรียน กลุ่ม TT-4",
            "bookingEmail": "somkiat.kay@mail.kmutt.com",
            "bookingId": 3,
            "eventEndTime": "2022-05-23T10:00:00Z"
        },
        {
            "eventNotes": null,
            "eventCategory": "DevOps/Infra Clinic",
            "eventStartTime": "2022-05-23T06:30:00Z",
            "eventDate": "2022-05-23",
            "eventCategoryID": 2,
            "eventDuration": 30,
            "bookingName": "Somchai Jaidee (OR-7)",
            "bookingEmail": "somchai.jai@mail.kmutt.com",
            "bookingId": 1,
            "eventEndTime": "2022-05-23T07:00:00Z"
        },
        {
            "eventNotes": "ขอปรึกษาปัญหาเพื่อนไม่ช่วยงาน",
            "eventCategory": "Project Management Clinic",
            "eventStartTime": "2022-04-27T02:30:00Z",
            "eventDate": "2022-04-27",
            "eventCategoryID": 1,
            "eventDuration": 30,
            "bookingName": "Somsri Rakdee (SJ-3)",
            "bookingEmail": "somsri.rak@mail.kmutt.com",
            "bookingId": 2,
            "eventEndTime": "2022-04-27T03:00:00Z"
        }
    ],
    "pageable": {
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 8,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalElements": 3,
    "totalPages": 1,
    "size": 8,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "first": true,
    "numberOfElements": 3,
    "empty": false
}
```
