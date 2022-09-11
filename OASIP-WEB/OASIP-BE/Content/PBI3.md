# RESCHEDULE-EVENT
1. The user can edit any event. <br>
2. The user can edit eventStartTime and eventNotes only.

```
http://localhost:8080/api/events/{{eventId}}
```
```
{
    "eventStartTime": "2023-05-23T06:30:00Z",
    "eventNotes": "event notes test"
}
```
Before you edit your appointment.
```
{
    "eventDate": "2022-05-23",
    "eventCategoryID": 2,
    "eventDuration": 30,
    "eventStartTime": "2022-05-23T06:30:00Z",
    "eventNotes": null,
    "eventCategory": "DevOps/Infra Clinic",
    "bookingEmail": "somchai.jai@mail.kmutt.com",
    "bookingName": "Somchai Jaidee (OR-7)",
    "bookingId": 1,
    "eventEndTime": "2022-05-23T07:00:00Z"
}
```
When you edit, your appointment is
```
{
    "eventDate": "2023-05-23",
    "eventCategoryID": 2,
    "eventDuration": 30,
    "eventNotes": "event notes test",
    "eventStartTime": "2023-05-23T06:30:00Z",
    "eventCategory": "DevOps/Infra Clinic",
    "bookingId": 1,
    "bookingEmail": "somchai.jai@mail.kmutt.com",
    "bookingName": "Somchai Jaidee (OR-7)",
    "eventEndTime": "2023-05-23T07:00:00Z"
}
```
Event start time and event notes has change.
