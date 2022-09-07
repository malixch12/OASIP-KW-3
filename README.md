# CLINIC BOOKING  👩‍💻

This project is a part of INT222 Integrated IT Project II .
Websites for booking clinics by department such as frontend backend database and others.

This website can contain multiple components such as CRUD , router , validate data, time overlap , filter event , Authenticate with jwt tokens , https , user login register, sessionStorage and other features that are coming in the future

### This project made by

| No  | role  | Student Name         |
| --- | ----------- | ------------------   |
| 1.  | backend |    winner  |
| 2.  | frontend |  Nuttawat Rodsomboon |
| 3.  | devop  |  ploy  |


### FEATURE 

| PBI  | PBI NAME  | PBI DESCRIPTION / CONDITIONS         |
| --- | ----------- | ------------------   |
| 1.  | VIEW-EVENT-LIST-EMPTY |   1. There is no event scheduled in any event category. <br>2. Display 'No Scheduled Events'.  |
| 2.  | VIEW-EVENT-LIST-ALL |  1. There are scheduled events.2. Display all scheduled events.3. In each event, display: event date, event start time, event duration (in minutes), eventCategoryName, bookingName (ชื่อการจอง)4. The list is sorted in descending order on event date/time. (เรียงลำดับจากวันเวลาของกิจกรรม จากอนาคตมาอดีต)"  |
| 3.  | VIEW-EVENT-DETAILS-BASE  |  1. Event is not null.2. Display: event date, event start time, event duration (in minutes), eventCategoryName, bookingName (ชื่อการจอง), bookingEmail, eventNotes  |
| 4.  | SCHEDULE-EVENT-BASIC-NO-VALIDATION |  1. The user can schedule a new event by specifying : eventCategory, bookingName, bookingEmail, eventStartTime, eventNotes (optional)2. The system uses eventCategoryDuration of the specified eventCategory as eventDuration.3. No input validation. 4. Assume that the input from user is valid.  |
| 5.  | CANCEL-EVENT-BASIC-SUCCESS  |   1. The user can cancel any event.2. The system must ask user for confirmation.3. The event is deleted from database after the operation. |
| 6.  | RESCHEDULE-EVENT-BASIC-NO-VALIDATION |  1. The user can edit any event.2. The user can edit eventStartTime and eventNotes only.3. No input validation. 4. Assume that the input from user is valid.  |
| 7.  | VALIDATE-INPUT-LENGTH  | 1. Validate compulsory fields that are not empty and the length of text fields do not exceed the field size/length.2. Front-end: check that compulsory fields are not empty/null and limit the form to the specified length of each field.3. Back-end: if a compulsory field is empty/null, response with an error code plus the field that is empty/null. If a POST data-string type is longer than the specified length, response with an error code and state which field has the length exceeded the size.   |
| 8.  | VALIDATE-DATETIME-FUTURE |  1. Validate eventStartTime during scheduling and rescheduling that the start date/time is in the future. Note that if the current time is 10:59, the user can still schedule an event to start at 11:00.2. Front-end: limit date selection to present and future only. Validate eventStartTime before calling back-end to create/update.3. Back-end: if eventStartTime is NOT in the future, response with an error code and state that the eventStartTime is NOT in the future.  |
| 9.  |VALIDATE-EMAIL   |  1. Validate bookingEmail specified during scheduling an event that it is an e-mail.2. Front-end: validate bookingEmail before calling back-end to create.3. Back-end: if bookingEmail is NOT valid, response with an error code and state that bookingEmail is invalid.  |
| 10.  |VALIDATE-DATETIME-NONOVERLAP|1. Validate event date/time during scheduling and rescheduling that the start and end date/time of the event is not overlapped with another event in the same event category. 2. Front-end: limit/validate eventStartTime before calling back-end to create/update. Note that the back-end may still response with overlapped error, must reload events and ask the user to select date/time again.3. Back-end: if the event overlapped with existing events in the same event category, response with an error code and state that the eventStartTime is overlapped."    |
| 11.  |VIEW-EVENT-CATEGORY-LIST   | 1. Display the list of all event categories..3. In each event category, display: eventCategoryName, eventCategoryDescription, eventDuration4. The list is sorted in descending order on the order the event category was inserted.    |
| 12.  | CONFIG-EVENT-CATEGORY | 1. The user can edit any event category.2. The user can edit any event category field.3. The change to eventCategoryDuration is applied to new event only.4. The change to eventCategoryName or eventCategoryDescription is applied to any event under the specified category.5. Validate that all fields are entered and within size ranges.6. The eventCategoryName should be trimmed.7. Front-end and back-end : do the same as stated in 'VALIDATE-INPUT-LENGTH' PBI.   |
| 13.  |  VALIDATE-EVENT-CATEGORY-NAME |  1. Validate eventCategoryName during configuration of event category that it is unique.2. Front-end: validate eventCategoryName before calling back-end to update.3. Back-end: if the eventCategoryName is the same as existing event categories, response with an error code and state that the eventCategoryName is NOT unique.|
| 14.  |VALIDATE-DURATION  |  1. Validate duration specified during configuring of event category that it is an integer between 1 and 480.2. Front-end: limit eventDuration to specified number only.3. Back-end: if the eventDuration is out of range, response with an error code and state that the event duration is out of range.  |
| 15.  |VIEW-EVENT-LIST-CATEGORY   | 1. User can filter events by event category.2. If there is no event scheduled in the specified event category, display 'No Scheduled Events'.3. If there are scheduled events on the specified event category, display each event as in 'VIEW-EVENT-LIST-ALL'.4. The list is sorted in descending order on event date/time. (เรียงลำดับจากวันเวลาของกิจกรรม จากอนาคตมาอดีต)   |
| 16.  |VIEW-EVENT-LIST-UPCOMING  |  1. User can filter events by date/time, show only future and on-going events.2. If there is no on-going or future event, display 'No On-Going or Upcoming Events'.3. If there are on-going or future events, display each event as in 'VIEW-EVENT-LIST-ALL'.4. The list is sorted in ascending order on event time. (เรียงลำดับจากเวลาของกิจกรรม จากน้อยมามาก)  |
| 17.  | VIEW-EVENT-LIST-PAST  |  1. User can filter events by date/time, show only past events.2. If there is no past event, display 'No Past Events'.3. If there are past events, display each event as in 'VIEW-EVENT-LIST-ALL'.4. The list is sorted in descending order on event date/time. (เรียงลำดับจากวันเวลาของกิจกรรม จากใกล้วันเวลาปัจจุบันไปไกล)  |
| 18.  | VIEW-EVENT-LIST-DAY | 1. User can filter events by date/time, show only the events with eventStartTime as specified date.2. If there is no event scheduled on the specified date, display 'No Scheduled Events'.3. If there are scheduled events on the specified date, display: event date, event start time, event duration (in minutes), bookingName (ชื่อการจอง)4. The list is sorted in ascending order on event time. (เรียงลำดับจากเวลาของกิจกรรม จากน้อยมามาก)   |
| 19.  |VIEW-USER-LIST-ALL-NO-AUTHORIZATION-CONTROL   |  1. If there is no user in the system, display 'No Users'.2. If there are users in the system, display all users.3. For each user, display: name, email, role4. The list is sorted in ascending order on user's name. (เรียงลำดับจากชื่อผู้ใช้ จากน้อยไปมาก)  |
| 20.  |VIEW-USER-DETAILS-NO-AUTHORIZATION-CONTROL  |1. Display: name, email, role, createdOn, updatedOn    |
| 21.  |ADD-USER-NO-AUTHORIZATION-CONTROL   |  1. The user can add new user by specifying : 'name', 'email', 'role'2. 'name' and 'email' fields must be trimmed before sending to backend or database3. Frontend and backend do not send out 'createdOn' and 'updatedOn' date/time4. Must validate NOT NULL/LENGTH on 'name' and 'email' as in PBI7 -- Frontend and Backend 5. Must validate email format on 'email' as in PBI9 -- Frontend and Backend 6. Must validate uniqueness of 'name' and 'email' -- Backend. Frontend must inform user when 'name' or 'email' is not unique and allow the user to edit. 7. Must validate 'role' field to be as specified in user attribute -- Frontend and Backend8. In case the data is valid, the system must add new user into database after the operation9. In case the data is invalid, the system must NOT add new user into database after the operation  |
| 22.  | DELETE-USER-NO-AUTHORIZATION-CONTROL | 1. The user can delete any user.2. The system must ask user for confirmation.3. The user is deleted from database after the operation.   |
| 23.  | EDIT-USER-NO-VALIDATION-NO-AUTHORIZATION-CONTROL  |1. The user can edit any user.2. The user can edit name, email, and role only.3. 'name' and 'email' fields must be trimmed before sending to backend or database4. Frontend and backend do not send out 'createdOn' and 'updatedOn' date/time5. Must validate NOT NULL/LENGTH on 'name' and 'email' as in PBI7 -- Frontend and Backend 6. Must validate email format on 'email' as in PBI9 -- Frontend and Backend 7. Must validate uniqueness of 'name' and 'email' -- Backend. Frontend must inform user when 'name' or 'email' is not unique and allow the user to edit. 8. Must validate 'role' field to be as specified in user attribute -- Frontend and Backend9. If all data remain the same, frontend does not sent update request to backend10. In case the new data is valid, the system must update user data and change 'updatedOn' to the current date/time(in DBMS) in database after the operation. createdOn value must be unchanged.11. In case the data is invalid, the system must NOT update user data in database after the operation    |
| 24.  | INCLUDE-PASSWORD | 1. Modify User table to include password field as specified in User table attributes (with password)2. Modify Add User function to include password field as specified in User table attributes (with password)3. MUST NOT show raw password on screen. Use '*' instead.4. Must validate NOT NULL/LENGTH on 'password' as in PBI7 -- Frontend and Backend5. User must confirm the raw password before adding new user. There is no need to send confirm password to the backend.6. If the confirm raw password DOES NOT match the specified raw password, inform the user 'The password DOES NOT match' and let the user enter confirm password again. Do not add new user yet. 7. If all fields are valid and the confirm password matches the specified raw password, new user must be inserted into database.8. MUST NOT store raw password!   |
| 25.  | MATCH-PASSWORD  | 1. The user can check whether the password matched the stored encoded password or not.2. The user specifies 'email' and 'raw-password'. The raw-password MUST NOT be shown on screen.3. If a user with specified email exists and the specified raw-password matches with the stored encoded password, display 'Password Matched'. The backend returns http status 200.4. If a user with specified email exists and the specified raw-password DOES NOT match with the stored encoded password, display 'Password NOT Matched'. The backend returns http status 401.5. If a user with specified email DOES NOT exists, display 'A user with the specified email DOES NOT exist'. The backend returns http status 404.6. Use the endpoint /match with POST method   |
| 26.  | SECURE-CONNECTION | 1. Connections between the client (browsers) and the servers (front-end, back-end) must be secure.2. Un-secure connections must be redirected to the equivalent secure connection."   |
| 27.  | AUTHENTICATE-WITH-JWT-TOKENS |1. The user can authenticate using 'email' and 'raw-password' and the backend returns JWT token.2. The user specifies 'email' and 'raw-password'. The raw-password MUST NOT be shown on screen.3. If a user with specified email exists and the specified raw-password matches with the stored encoded password, the frontend displays 'Login Successful'. The backend returns http status 200.4. If a user with specified email exists and the specified raw-password DOES NOT match with the stored encoded password, the frontend displays ''Password Incorrect"" and allow the user to enter the password again. The backend returns http status 401.5. If a user with specified email DOES NOT exists, the frontend displays 'A user with the specified email DOES NOT exist' and allow the user to edit the email. The backend returns http status 404.6. Use the endpoint /login with POST method    |
| 28.  | VALIDATE-USER-REQUEST-JWT  | 1. The backend validate the requests to /users endpoint2. If the request header contains valid JWT token, response as stated in PBI19-23. If the request header has no JWT token or the token is invalid, response with http status 4014. The frontend must be modified to send the user's JWT token to the backend when fetch5. The frontend must handle http status 4016. There is no authorization. Authenticated user can still perform any actions."    |
| 29.  | STORE-JWT-TOKEN |  1. The user stay logged-in unless the user has signed-out or the 'refresh' token has expired <br>2. The 'access' token expiry time is set to 30 minutes and the 'refresh' token is set to 24 hours <br>3. The front-end must store the JWT token on local storage (user machine)<br>4. The front-end must check the local storage whether the JWT token exists. If it does, try to access the backend using the stored token.  <br>4.1 If the token is valid, use the token to access the backend until this token has expired<br>4.2 If the access token has expired, get a refreshed token at /refresh endpoint and store it in the local-storage<br>4.3 If refreshing of token failed or the token is invalid, the user must authenticate again to use the members' only features"  |
| 30.  |  SIGN-OUT |    1. The user can sign out of the application<br>2. The user token/state must be removed from the session/local storage |
| 31.  |  coming soon |    coming soon |
